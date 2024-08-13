package ru.urvanov.virtualpets.server.controller.api.domain;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = """
        Результат запроса на получение пользователей, \
        находящихся онлайн.
        """)
public record RefreshUsersOnlineResult(List<UserInfo> users) {
};
