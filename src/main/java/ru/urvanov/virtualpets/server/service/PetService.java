package ru.urvanov.virtualpets.server.service;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.AchievementId;
import ru.urvanov.virtualpets.server.dao.domain.Bookcase;
import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;
import ru.urvanov.virtualpets.server.service.domain.PetDetails;
import ru.urvanov.virtualpets.server.service.exception.NotEnoughPetResourcesException;
import ru.urvanov.virtualpets.server.service.exception.PetNotFoundException;

public interface PetService {

    public void updatePetsTask();

    void substractPetResources(Pet fullPet, Refrigerator refrigerator)
            throws NotEnoughPetResourcesException;

    void substractPetResources(Pet fullPet, Bookcase bookcase)
            throws NotEnoughPetResourcesException;

    void substractPetResources(Pet fullPet, MachineWithDrinks drink)
            throws NotEnoughPetResourcesException;
    
    public Long getPetNewJournalEntriesCount(Integer petId);

    void addExperience(Pet pet, Integer exp);

    public List<AchievementId> calculateAchievements(Pet pet);

    Iterable<Pet> findLastCreatedPets(int start, int limit);

    void addAchievementIfNot(Pet pet, AchievementId achievement);

    public PetDetails petInformationPage(Integer id) throws PetNotFoundException;
}
