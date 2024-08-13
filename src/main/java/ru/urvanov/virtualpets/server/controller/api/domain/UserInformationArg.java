package ru.urvanov.virtualpets.server.controller.api.domain;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Запрос на получение информации о пользователе")
public class UserInformationArg  implements Serializable{
    
    private static final long serialVersionUID = -4301503973351649355L;
    private int userId;
    
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

}
