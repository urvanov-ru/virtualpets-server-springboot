package ru.urvanov.virtualpets.server.controller.api.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Сообщение чата")
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 3870376160365638500L;
    
    @Schema(description = "Идентификатор сообщения", example = "10034")
    private Integer id;
    
    @Schema(description = "Идентификатор отправителя", example = "1")
    private Integer senderId;
    
    @Schema(description = "Полное имя отправителя", example = "Tester")
    private String senderName;
    
    @Schema(description = "Идентификатор получателя", example = "2")
    private Integer addresseeId;
    
    @Schema(description = "Полное имя получателя", example = "Tester")
    private String addresseeName;
    
    @Schema(description = "Текст сообщения", example = "Привет")
    private String message;
    
    @Schema(
            description = "Дата и время отправления",
            example = "2024-08-13T12:55:22.399546Z")
    private OffsetDateTime sendTime;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getSenderId() {
        return senderId;
    }
    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }
    public String getSenderName() {
        return senderName;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    public Integer getAddresseeId() {
        return addresseeId;
    }
    public void setAddresseeId(Integer addresseeId) {
        this.addresseeId = addresseeId;
    }
    public String getAddresseeName() {
        return addresseeName;
    }
    public void setAddresseeName(String addresseeName) {
        this.addresseeName = addresseeName;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public OffsetDateTime getSendTime() {
        return sendTime;
    }
    public void setSendTime(OffsetDateTime sendTime) {
        this.sendTime = sendTime;
    }
    

}
