package ru.urvanov.virtualpets.server.controller.api.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.urvanov.virtualpets.server.dao.domain.AchievementId;

public class GetRoomInfoResult implements Serializable {

    private static final long serialVersionUID = -9110703336298015250L;

    @Schema(description = "Настроение питомца.", example = "100")
    private int mood;
    
    @Schema(description = "Сытость питомца.", example = "100")
    private int satiety;
    
    @Schema(description = "Начитанность.", example = "100")
    private int education;
    
    @Schema(description = "Жажда (100 - не хочет пить)", example = "100")
    private int drink;
    
    @Schema(description = "Надетая шляпа", example = "COWBOY_HAT")
    private String hatId;
    
    @Schema(
            description = "Надетая на туловище одежда",
            example = "COLORED_BODY")
    private String clothId;
    
    @Schema(description = "Надетый бантик", example = "BLUE_BOW")
    private String bowId;
    
    @Schema(
            description = "Уровень холодильника. Номерация с 1.",
            example = "1")
    private Integer refrigeratorId;
    
    @Schema(
            description = """
                    Позиция холодильника в тайлах по горизонтали.""",
            example = "1")
    private Integer refrigeratorX;
    
    @Schema(
            description = """
                    Позиция холодильника в тайлах по вертикали.""",
            example = "1")
    private Integer refrigeratorY;
    
    @Schema(
            description = """
                    Лутбоксы новичка. Три штуки. \
                    true - лутбокс ещё не открыт.""",
            example = "[false, false, false]")
    private boolean[] boxesNewbie;
    
    @Schema(description = "Наличие дневника на полу", example = "true")
    private boolean journalOnFloor;
    
    @Schema(
            description = "Уровень книжного шкафа. Номерация с 1.",
            example = "1")
    private Integer bookcaseId;
    
    @Schema(
            description = """
                    Позиция книжного шкафа \
                     в тайлах по горизонтали.""",
            example = "1")
    private Integer bookcaseX;
    
    @Schema(
            description = """
                    Позиция книжного шкафа \
                     в тайлах по вертикали.""",
            example = "1")
    private Integer bookcaseY;
    
    @Schema(
            description = "Уровень машины с напитками. Номерация с 1.",
            example = "1")
    private Integer machineWithDrinksId;
    
    @Schema(
            description = """
                    Позиция машины с напитками \
                     в тайлах по горизонтали.""",
            example = "1")
    private Integer machineWithDrinksX;
    
    @Schema(
            description = """
                    Позиция машины с напитками \
                     в тайлах по вертикали.""",
                     example = "1")
    private Integer machineWithDrinksY;
    
    @Schema(
            description = """
                    Количество новых, непрочитанных \
                    записей в дневнике.""",
            example = "1")
    private long newJournalEntriesCount;
    
    @Schema(description = "Дневник поднят.", example = "false")
    private boolean haveJournal;
    
    @Schema(description = "Отображение рюкзака.", example = "false")
    private boolean haveRucksack;
    
    @Schema(
            description = "Отображение иконки строительства.",
            example = "false")
    private boolean haveHammer;
    
    @Schema(
            description = "Отображение индикаторов состояния питомца.",
            example = "true")
    private boolean haveIndicators;
    
    @Schema(
            description = "Отображение стрелки в город.",
            example = "false")
    private boolean haveToTownArrow;
    
    @Schema(description = "Уровень и опыт питомца.")
    private LevelInfo levelInfo;
    
    @Schema(description = "Новые полученные достижения.")
    private List<AchievementId> achievements = new ArrayList<>();
    
    public int getMood() {
        return mood;
    }
    public void setMood(int mood) {
        this.mood = mood;
    }
    public int getSatiety() {
        return satiety;
    }
    public void setSatiety(int satiety) {
        this.satiety = satiety;
    }
    public int getEducation() {
        return education;
    }
    public void setEducation(int education) {
        this.education = education;
    }
    public int getDrink() {
        return drink;
    }
    public void setDrink(int drink) {
        this.drink = drink;
    }
    public String getHatId() {
        return hatId;
    }
    public void setHatId(String hatId) {
        this.hatId = hatId;
    }
    public String getClothId() {
        return clothId;
    }
    public void setClothId(String clothId) {
        this.clothId = clothId;
    }
    public String getBowId() {
        return bowId;
    }
    public void setBowId(String bowId) {
        this.bowId = bowId;
    }
    public Integer getRefrigeratorId() {
        return refrigeratorId;
    }
    public void setRefrigeratorId(Integer refrigeratorId) {
        this.refrigeratorId = refrigeratorId;
    }
    public Integer getRefrigeratorX() {
        return refrigeratorX;
    }
    public void setRefrigeratorX(Integer refrigeratorX) {
        this.refrigeratorX = refrigeratorX;
    }
    public Integer getRefrigeratorY() {
        return refrigeratorY;
    }
    public void setRefrigeratorY(Integer refrigeratorY) {
        this.refrigeratorY = refrigeratorY;
    }
    public boolean[] getBoxesNewbie() {
        return boxesNewbie;
    }
    public void setBoxesNewbie(boolean[] boxesNewbie) {
        this.boxesNewbie = boxesNewbie;
    }
    public boolean isJournalOnFloor() {
        return journalOnFloor;
    }
    public void setJournalOnFloor(boolean journalOnFloor) {
        this.journalOnFloor = journalOnFloor;
    }
    public Integer getBookcaseId() {
        return bookcaseId;
    }
    public void setBookcaseId(Integer bookcaseId) {
        this.bookcaseId = bookcaseId;
    }
    public Integer getBookcaseX() {
        return bookcaseX;
    }
    public void setBookcaseX(Integer bookcaseX) {
        this.bookcaseX = bookcaseX;
    }
    public Integer getBookcaseY() {
        return bookcaseY;
    }
    public void setBookcaseY(Integer bookcaseY) {
        this.bookcaseY = bookcaseY;
    }
    public Integer getMachineWithDrinksId() {
        return machineWithDrinksId;
    }
    public void setMachineWithDrinksId(Integer machineWithDrinksId) {
        this.machineWithDrinksId = machineWithDrinksId;
    }
    public Integer getMachineWithDrinksX() {
        return machineWithDrinksX;
    }
    public void setMachineWithDrinksX(Integer machineWithDrinksX) {
        this.machineWithDrinksX = machineWithDrinksX;
    }
    public Integer getMachineWithDrinksY() {
        return machineWithDrinksY;
    }
    public void setMachineWithDrinksY(Integer machineWithDrinksY) {
        this.machineWithDrinksY = machineWithDrinksY;
    }
    public long getNewJournalEntriesCount() {
        return newJournalEntriesCount;
    }
    public void setNewJournalEntriesCount(long newJournalEntriesCount) {
        this.newJournalEntriesCount = newJournalEntriesCount;
    }
    public boolean isHaveJournal() {
        return haveJournal;
    }
    public void setHaveJournal(boolean haveJournal) {
        this.haveJournal = haveJournal;
    }
    public boolean isHaveRucksack() {
        return haveRucksack;
    }
    public void setHaveRucksack(boolean haveRucksack) {
        this.haveRucksack = haveRucksack;
    }
    public boolean isHaveHammer() {
        return haveHammer;
    }
    public void setHaveHammer(boolean haveHammer) {
        this.haveHammer = haveHammer;
    }
    public boolean isHaveIndicators() {
        return haveIndicators;
    }
    public void setHaveIndicators(boolean haveIndicators) {
        this.haveIndicators = haveIndicators;
    }
    public boolean isHaveToTownArrow() {
        return haveToTownArrow;
    }
    public void setHaveToTownArrow(boolean haveToTownArrow) {
        this.haveToTownArrow = haveToTownArrow;
    }
    public LevelInfo getLevelInfo() {
        return levelInfo;
    }
    public void setLevelInfo(LevelInfo levelInfo) {
        this.levelInfo = levelInfo;
    }
    public List<AchievementId> getAchievements() {
        return achievements;
    }
    public void setAchievements(List<AchievementId> achievements) {
        this.achievements = achievements;
    }

    
    
}
