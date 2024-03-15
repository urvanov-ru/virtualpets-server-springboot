package ru.urvanov.virtualpets.server.service;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ru.urvanov.virtualpets.server.dao.ChatDao;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.Chat;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.shared.domain.ChatMessage;
import ru.urvanov.virtualpets.shared.domain.RefreshChatArg;
import ru.urvanov.virtualpets.shared.domain.RefreshChatResult;
import ru.urvanov.virtualpets.shared.domain.SendChatMessageArg;
import ru.urvanov.virtualpets.shared.domain.SendChatMessageResult;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;
import ru.urvanov.virtualpets.shared.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatDao chatDao;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private Clock clock;


    /* (non-Javadoc)
     * @see ru.urvanov.virtualpets.shared.service.ChatService#getMessages(ru.urvanov.virtualpets.shared.domain.RefreshChatArg)
     */
    @Override
    public RefreshChatResult getMessages(RefreshChatArg arg)
            throws DaoException, ServiceException {
        
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication  authentication = (Authentication) securityContext.getAuthentication();
        User user = (User) authentication.getPrincipal();
        Integer userId = user.getId();
        
        user = userDao.findById(userId).orElseThrow();
        user.setActiveDate(OffsetDateTime.now(clock));
        userDao.save(user);
        
        
        Integer id = arg.getLastChatMessageId();
        Iterable<Chat> messages;
        if (id == null) {
            messages = chatDao.findLast(20, userId);
        } else {
            messages = chatDao.findFromId(id, userId);
        }
        
        RefreshChatResult result = new RefreshChatResult();
        result.setSuccess(true);
        
        
        ChatMessage[] chatMessages = StreamSupport
                .stream(messages.spliterator(), false)
                .map(c -> {
            ChatMessage chatMessage = new ChatMessage();
            User addressee = c.getAddressee();
            if (addressee != null) {
                chatMessage.setAddresseeId(addressee.getId());
                chatMessage.setAddresseeName(addressee.getName());
            }
            User sender = c.getSender();
            if (sender != null) {
                chatMessage.setSenderId(sender.getId());
                chatMessage.setSenderName(sender.getName());
            }
            chatMessage.setMessage(c.getMessage());
            chatMessage.setSendTime(c.getSendTime());
            chatMessage.setId(c.getId());
            return chatMessage;
        }).toArray(ChatMessage[]::new);
        

        if (chatMessages.length > 0) {
            result.setLastChatMessageId(chatMessages[chatMessages.length-1].getId());
        } else {
            result.setLastChatMessageId(arg.getLastChatMessageId());
        }
        result.setChatMessages(chatMessages);
        return result;
    }

    /* (non-Javadoc)
     * @see ru.urvanov.virtualpets.shared.service.ChatService#sendMessage(ru.urvanov.virtualpets.shared.domain.SendChatMessageArg)
     */
    @Override
    public SendChatMessageResult sendMessage(SendChatMessageArg arg)
            throws DaoException, ServiceException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User)authentication.getPrincipal();
        
        Chat chat = new Chat();
        if (arg.getAddresseeId() != null) {
            chat.setAddressee(userDao.getReferenceById(arg.getAddresseeId()));
        }
        chat.setMessage(arg.getMessage());
        chat.setSender(userDao.getReferenceById(user.getId()));
        chat.setSendTime(OffsetDateTime.now(clock));
        chatDao.save(chat);
        
        SendChatMessageResult result = new SendChatMessageResult();
        result.setSuccess(true);
        return result;
    }

    public ChatDao getChatDao() {
        return chatDao;
    }

    public void setChatDao(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


}
