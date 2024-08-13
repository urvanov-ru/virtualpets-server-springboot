package ru.urvanov.virtualpets.server.controller.api.domain;

import java.io.Serializable;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.urvanov.virtualpets.server.dao.domain.Sex;

@Schema(description = "Информация о пользователе")
public class UserInformation implements Serializable {

    private static final long serialVersionUID = 7727325715161117786L;
    
    @Schema(description = "Идентификатор пользователя", example = "1")
    private int id;
    
    @Schema(description = "Полное имя пользователя", example = "Tester")
    private String name;
    
    @Schema(description = "Дата рождения", example = "2024-01-01")
    private LocalDate birthdate;
    
    @Schema(description = "Пол", example = "MAN")
    private Sex sex;
    
    @Schema(description = "Страна", example = "Маленькая Чудесная Страна")
    private String country;
    
    @Schema(description = "Город", example = "Городочек")
    private String city;
    
    @Schema(description = "Комментарий", example = "Повелитель мира")
    private String comment;
    
    @Schema(description = "Фотография")
    private byte[] photo;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
    public Sex getSex() {
        return sex;
    }
    public void setSex(Sex sex) {
        this.sex = sex;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public byte[] getPhoto() {
        return photo;
    }
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
   

}
