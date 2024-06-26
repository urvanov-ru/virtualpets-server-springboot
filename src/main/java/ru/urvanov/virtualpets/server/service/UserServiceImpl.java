package ru.urvanov.virtualpets.server.service;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.stream.StreamSupport;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.service.domain.UserProfile;
import ru.urvanov.virtualpets.shared.domain.LoginArg;
import ru.urvanov.virtualpets.shared.domain.LoginResult;
import ru.urvanov.virtualpets.shared.domain.RefreshUsersOnlineArg;
import ru.urvanov.virtualpets.shared.domain.RefreshUsersOnlineResult;
import ru.urvanov.virtualpets.shared.domain.UserInfo;
import ru.urvanov.virtualpets.shared.domain.UserInformation;
import ru.urvanov.virtualpets.shared.domain.UserInformationArg;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.IncompatibleVersionException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;

@Service("userService")
public class UserServiceImpl implements UserService, ru.urvanov.virtualpets.shared.service.UserService, UserDetailsService  {

    private static final DateTimeFormatter unidDateTimeFormatter
            = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS", Locale.ROOT);
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private ConversionService conversionService;
    
    @Autowired
    private MessageSource messageSource;
    
    @Value("${virtualpets-server-springboot.version}")
    private String version;
    
    @Autowired
    private Clock clock;

    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
    public LoginResult login(LoginArg arg) throws ServiceException, DaoException {
        String clientVersion = arg.getVersion();
        if (!version.equals(clientVersion)) {
            throw new IncompatibleVersionException("", version, clientVersion);
        }
        org.springframework.web.context.request.ServletRequestAttributes sra = (org.springframework.web.context.request.ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder.getRequestAttributes();
        sra.setAttribute("my1", "var1", ServletRequestAttributes.SCOPE_SESSION);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User)authentication.getPrincipal();
        user = userDao.findById(user.getId()).orElseThrow();
        
        byte[] b = new byte[256];
        Random r = new Random();
        r.nextBytes(b);
        String uniqueIdentifier = Base64.encodeBase64String(b);
        
        uniqueIdentifier = uniqueIdentifier
                + unidDateTimeFormatter.format(OffsetDateTime.now(clock));
        user.setUnid(uniqueIdentifier);
        
        LoginResult loginResult = new LoginResult();
        loginResult.setUnid(uniqueIdentifier);
        loginResult.setUserId(user.getId());
        loginResult.setSuccess(true);
        return loginResult;
    }

    @Override
    public RefreshUsersOnlineResult getUsersOnline(
            RefreshUsersOnlineArg argument) {
        OffsetDateTime offsetDateTime = OffsetDateTime.now(clock).minusMinutes(5);
        Iterable<User> users = userDao.findByActiveDateAfter(offsetDateTime);
        UserInfo[] userInfos = StreamSupport.stream(users.spliterator(), false)
                .map(u -> {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setId(u.getId());
                    userInfo.setName(u.getName());
                    return userInfo;
                }).toArray(UserInfo[]::new);
        
        RefreshUsersOnlineResult result = new RefreshUsersOnlineResult();
        result.setSuccess(true);
        result.setUsers(userInfos);
        return result;
    }

    @Override
    public UserInformation getUserInformation(UserInformationArg argument) {
        Integer userId = argument.getUserId();
        User user = userDao.findById(userId).orElseThrow();
        UserInformation result = new UserInformation();
        result.setId(userId);
        result.setName(user.getName());
        result.setBirthdate(user.getBirthdate());
        result.setCity(user.getCity());
        result.setCountry(user.getCountry());
        result.setComment(user.getComment());
        //result.setPhoto(user.getPhoto());
        if (user.getSex() != null) {
            result.setSex(conversionService.convert(user.getSex(), ru.urvanov.virtualpets.shared.domain.Sex.class));
        }
        return result;
    }


    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
    public void closeSession() throws DaoException, ServiceException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User)authentication.getPrincipal();
        user = userDao.findById(user.getId()).orElseThrow();
        user.setUnid(null);
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }



    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }



    @Override
    @Transactional(rollbackFor = {DaoException.class, ServiceException.class})
    public void updateUserInformation(UserInformation arg)
            throws ServiceException, DaoException {
        byte[] photo = arg.getPhoto();
        if (photo!= null) {
            if (photo.length > 100000) {
                throw new ServiceException("Too big file.");
            }
        }
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth= context.getAuthentication();
        User user = (User) auth.getPrincipal();
        Integer id = user.getId();
        if (id.equals(arg.getId())) {
            user = userDao.findById(id).orElseThrow();
            user.setName(arg.getName());
            user.setSex(conversionService.convert(arg.getSex(), ru.urvanov.virtualpets.server.dao.domain.Sex.class));
            user.setBirthdate(arg.getBirthdate());
            user.setCountry(arg.getCountry());
            user.setCity(arg.getCity());
            user.setComment(arg.getComment());
            //user.setPhoto(arg.getPhoto());
        } else {
            throw new ServiceException("Incorrect user id. You can not save this user information.");
        }
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public ConversionService getConversionService() {
        return conversionService;
    }

    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public UserProfile getProfile() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User)authentication.getPrincipal();
        UserProfile userProfile = new UserProfile();
        userProfile.setBirthdate(user.getBirthdate());
        userProfile.setName(user.getName());
        userProfile.setEmail(user.getEmail());
        return userProfile;
    }

    @Override
    public Iterable<User> findLastRegisteredUsers(int start, int limit) {
        return userDao.findLastRegisteredUsers(start, limit);
    }

    @Override
    public Optional<User> findByRecoverPasswordKey(String recoverPasswordKey) {
        return userDao.findByRecoverPasswordKey(recoverPasswordKey);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userDao.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
