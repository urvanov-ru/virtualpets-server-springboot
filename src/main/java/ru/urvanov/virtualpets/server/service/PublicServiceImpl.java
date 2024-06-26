package ru.urvanov.virtualpets.server.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.config.VirtualpetsServerSpringBootProperties;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.shared.domain.GetServersArg;
import ru.urvanov.virtualpets.shared.domain.LoginResult;
import ru.urvanov.virtualpets.shared.domain.RecoverPasswordArg;
import ru.urvanov.virtualpets.shared.domain.RecoverPasswordResult;
import ru.urvanov.virtualpets.shared.domain.RecoverSessionArg;
import ru.urvanov.virtualpets.shared.domain.RegisterArgument;
import ru.urvanov.virtualpets.shared.domain.ServerInfo;
import ru.urvanov.virtualpets.shared.domain.ServerTechnicalInfo;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.IncompatibleVersionException;
import ru.urvanov.virtualpets.shared.exception.NameIsBusyException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;
import ru.urvanov.virtualpets.shared.service.PublicService;

@Service
public class PublicServiceImpl implements PublicService {

    @Autowired
    private UserDao userDao;

    //@Autowired
    //private MailSender mailSender;

    //@Autowired
    //private SimpleMailMessage templateMessage;

    @Value("${virtualpets-server-springboot.version}")
    private String version;

    @Autowired
    private VirtualpetsServerSpringBootProperties properties;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;
    
    @Autowired
    private Clock clock;

    @Override
    public ServerInfo[] getServers(GetServersArg arg) throws ServiceException,
            DaoException {
        String clientVersion = arg.getVersion();
        if (!version.equals(clientVersion)) {
            throw new IncompatibleVersionException("", version, clientVersion);
        }
        return properties.getServers();
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void register(RegisterArgument arg)
            throws ServiceException, DaoException {
        try {
            String clientVersion = arg.getVersion();
            if (!version.equals(clientVersion)) {
                throw new IncompatibleVersionException("", version,
                        clientVersion);
            }
            User user = userDao.findByLogin(arg.getLogin()).orElseThrow();
            if (user != null) {
                throw new NameIsBusyException();
            }
            if (user == null) {
                throw new jakarta.persistence.NoResultException();
            }
        } catch (jakarta.persistence.NoResultException noResultException) {
            User user = new User();
            user.setLogin(arg.getLogin());
            user.setName(arg.getLogin());
            user.setPassword(bcryptEncoder.encode(arg.getPassword()));
            user.setEmail(arg.getEmail());
            user.setRegistrationDate(OffsetDateTime.now(clock));
            user.setRole(ru.urvanov.virtualpets.server.dao.domain.Role.USER);
            userDao.save(user);
        }
    }


    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public RecoverPasswordResult recoverPassword(RecoverPasswordArg argument)
            throws ServiceException, DaoException {
        String clientVersion = argument.getVersion();
        if (!version.equals(clientVersion)) {
            throw new IncompatibleVersionException("", version, clientVersion);
        }

        String email = argument.getEmail();
        String login = argument.getLogin();

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new ServiceException(ex);
        }
        byte[] hash = digest.digest();

        // converting byte array to Hexadecimal String
        StringBuilder sb = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            sb.append(String.format("%02x", b & 0xff));
        }
        String key = sb.toString();
        OffsetDateTime recoverPasswordValid = OffsetDateTime.now(clock);
        recoverPasswordValid = recoverPasswordValid.minusMonths(1);
        User user = userDao.findByLoginAndEmail(login, email).orElseThrow();
        user.setRecoverPasswordKey(key);
        user.setRecoverPasswordValid(recoverPasswordValid);
        userDao.save(user);

        // Create a thread safe "copy" of the template message and customize it
//        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
//        msg.setTo(email);
//        msg.setText("Dear " + user.getName()
//                + ", follow this link to recover password " + applicationUrl
//                + "/site/recoverPassword?recoverPasswordKey=" + key + " ");
//        try {
//            this.mailSender.send(msg);
//        } catch (MailException ex) {
//            throw new SendMailException("Send mail exception.", ex);
//        }

        RecoverPasswordResult result = new RecoverPasswordResult();

        result.setSuccess(true);
        return result;
    }
//
//    /**
//     * @return the mailSender
//     */
//    public MailSender getMailSender() {
//        return mailSender;
//    }
//
//    /**
//     * @param mailSender
//     *            the mailSender to set
//     */
//    public void setMailSender(MailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    /**
//     * @return the templateMessage
//     */
//    public SimpleMailMessage getTemplateMessage() {
//        return templateMessage;
//    }
//
//    /**
//     * @param templateMessage
//     *            the templateMessage to set
//     */
//    public void setTemplateMessage(SimpleMailMessage templateMessage) {
//        this.templateMessage = templateMessage;
//    }

//    public MailSender getMailSender() {
//        return mailSender;
//    }
//
//    public void setMailSender(MailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    public SimpleMailMessage getTemplateMessage() {
//        return templateMessage;
//    }
//
//    public void setTemplateMessage(SimpleMailMessage templateMessage) {
//        this.templateMessage = templateMessage;
//    }

    @Override
    @Transactional(rollbackFor = ServiceException.class, readOnly = true)
    public LoginResult recoverSession(RecoverSessionArg arg)
            throws ServiceException, DaoException {
        String clientVersion = arg.getVersion();
        if (!version.equals(clientVersion)) {
            throw new IncompatibleVersionException("", version, clientVersion);
        }
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String unid = arg.getUnid();
        User user = userDao.findByUnid(unid).orElseThrow();
        if (user != null) {

            Set<GrantedAuthority> granted = new HashSet<GrantedAuthority>();
            granted.add(new SimpleGrantedAuthority("ROLE_USER"));

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    user, null, granted);
            securityContext.setAuthentication(token);
        }
        Authentication auth = securityContext.getAuthentication();

        LoginResult loginResult = new LoginResult();
        Object principal = auth.getPrincipal();
        if (principal instanceof User) {
            loginResult.setSuccess(true);
            loginResult.setUnid(user.getUnid());
            loginResult.setUserId(user.getId());
        } else {
            loginResult.setSuccess(false);
        }
        return loginResult;
    }

    @Override
    public ServerTechnicalInfo getServerTechnicalInfo()
            throws ServiceException, DaoException {
        try {
            ServerTechnicalInfo info = new ServerTechnicalInfo();
            Properties properties = System.getProperties();
            Map<String, String> infoMap = new HashMap<String, String>();
            for (Entry<Object, Object> entry : properties.entrySet()) {
                infoMap.put(String.valueOf(entry.getKey()),
                        String.valueOf(entry.getValue()));
            }
            info.setInfo(infoMap);
            return info;
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}
