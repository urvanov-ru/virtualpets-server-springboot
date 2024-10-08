package ru.urvanov.virtualpets.server.service;

import ru.urvanov.virtualpets.server.controller.api.domain.RefreshChatArg;
import ru.urvanov.virtualpets.server.controller.api.domain.RefreshChatResult;
import ru.urvanov.virtualpets.server.controller.api.domain.SendChatMessageArg;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

public interface ChatApiService {
    RefreshChatResult getMessages(RefreshChatArg arg)
            throws ServiceException;

    void sendMessage(SendChatMessageArg arg)
            throws ServiceException;
}
