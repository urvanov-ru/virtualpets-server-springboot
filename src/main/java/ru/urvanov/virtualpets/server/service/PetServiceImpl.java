package ru.urvanov.virtualpets.server.service;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.controller.api.domain.CreatePetArg;
import ru.urvanov.virtualpets.server.controller.api.domain.DrinkArg;
import ru.urvanov.virtualpets.server.controller.api.domain.GetPetBooksResult;
import ru.urvanov.virtualpets.server.controller.api.domain.GetPetClothsResult;
import ru.urvanov.virtualpets.server.controller.api.domain.GetPetDrinksResult;
import ru.urvanov.virtualpets.server.controller.api.domain.GetPetFoodsResult;
import ru.urvanov.virtualpets.server.controller.api.domain.GetPetJournalEntriesResult;
import ru.urvanov.virtualpets.server.controller.api.domain.GetPetRucksackInnerResult;
import ru.urvanov.virtualpets.server.controller.api.domain.PetInfo;
import ru.urvanov.virtualpets.server.controller.api.domain.PetListResult;
import ru.urvanov.virtualpets.server.controller.api.domain.SatietyArg;
import ru.urvanov.virtualpets.server.controller.api.domain.SavePetCloths;
import ru.urvanov.virtualpets.server.controller.api.domain.SelectPetArg;
import ru.urvanov.virtualpets.server.dao.ClothDao;
import ru.urvanov.virtualpets.server.dao.LevelDao;
import ru.urvanov.virtualpets.server.dao.PetDao;
import ru.urvanov.virtualpets.server.dao.PetFoodDao;
import ru.urvanov.virtualpets.server.dao.PetJournalEntryDao;
import ru.urvanov.virtualpets.server.dao.RoomDao;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.AchievementId;
import ru.urvanov.virtualpets.server.dao.domain.Book;
import ru.urvanov.virtualpets.server.dao.domain.Bookcase;
import ru.urvanov.virtualpets.server.dao.domain.BookcaseCost;
import ru.urvanov.virtualpets.server.dao.domain.BuildingMaterialId;
import ru.urvanov.virtualpets.server.dao.domain.Cloth;
import ru.urvanov.virtualpets.server.dao.domain.DrinkId;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryId;
import ru.urvanov.virtualpets.server.dao.domain.Level;
import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinks;
import ru.urvanov.virtualpets.server.dao.domain.MachineWithDrinksCost;
import ru.urvanov.virtualpets.server.dao.domain.Pet;
import ru.urvanov.virtualpets.server.dao.domain.PetAchievement;
import ru.urvanov.virtualpets.server.dao.domain.PetBuildingMaterial;
import ru.urvanov.virtualpets.server.dao.domain.PetDrink;
import ru.urvanov.virtualpets.server.dao.domain.PetFood;
import ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;
import ru.urvanov.virtualpets.server.dao.domain.RefrigeratorCost;
import ru.urvanov.virtualpets.server.service.domain.PetDetails;
import ru.urvanov.virtualpets.server.service.domain.PetInformationPageAchievement;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.NotEnoughPetResourcesException;
import ru.urvanov.virtualpets.server.service.exception.PetNotFoundException;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@Service("petService")
public class PetServiceImpl implements PetService, PetApiService {

    @Autowired
    private RoomDao roomDao;
    
    @Autowired
    private PetDao petDao;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private PetFoodDao petFoodDao;
    
    @Autowired
    private LevelDao levelDao;
    
    @Autowired
    private ClothDao clothDao;
    
    @Autowired
    private PetJournalEntryDao petJournalEntryDao;
    
    @Autowired
    private Clock clock;
    
    @Autowired
    private ConversionService conversionService;
    
    /**
     * Добавление опыта питомцу. Обрабатывает переход питомца на 
     * следующий уровень при достижении необходимого порога опыта.
     * @param pet Питомец.
     * @param exp Добавляемый опыт.
     */
    @Override
    public void addExperience(Pet pet, Integer exp) {
        int nextExperience = pet.getExperience() + exp;
        Optional<Level> nextLevelOpt = levelDao.findById(
                pet.getLevel().getId() + 1);
        nextLevelOpt.ifPresentOrElse((nextLevel) -> {
            pet.setExperience(nextExperience);
            if (nextExperience >= nextLevel.getExperience()) {
                pet.setLevel(nextLevel);
            }
        }, () -> {
            Level lastLevel = levelDao.findById(pet.getLevel().getId())
                    .orElseThrow();
            pet.setExperience(Math.min(nextExperience,
                    lastLevel.getExperience()));
        });
    }

    @Override
    public void updatePetsTask() {
        Page<Pet> page = petDao.findAll(Pageable.ofSize(0));
        while (page.hasContent()) {
            List<Pet> changed = new ArrayList<>();
            for (Pet pet : page) {
                pet.setMood(decParameter(pet.getMood()));
                pet.setDrink(decParameter(pet.getDrink()));
                pet.setSatiety(decParameter(pet.getSatiety()));
                pet.setEducation(decParameter(pet.getEducation()));
                changed.add(pet);
            }
            petDao.saveAll(changed);
            page = petDao.findAll(page.nextPageable());
        }
    }
    

    private int decParameter(int parameter) {
        if (parameter > 0) {
            parameter = parameter - 1;
        }
        return parameter;
    }
    
    private void substractPetResources(
            Map<BuildingMaterialId, PetBuildingMaterial> petBuildingMaterials,
            Map<BuildingMaterialId, Integer> costs)
                    throws NotEnoughPetResourcesException {
        for (Entry<BuildingMaterialId, Integer> entry : costs.entrySet()) {
            BuildingMaterialId buildingMaterialId = entry.getKey();
            int resourceCost = entry.getValue();
            PetBuildingMaterial petBuildingMaterial
                    = petBuildingMaterials.get(buildingMaterialId);
            if (petBuildingMaterial == null) {
                throw new NotEnoughPetResourcesException();
            } else {
                int currentResourceCount = petBuildingMaterial
                        .getBuildingMaterialCount();
                int newCount = currentResourceCount - resourceCost;
                if (newCount < 0) {
                    throw new NotEnoughPetResourcesException();
                }
                petBuildingMaterial.setBuildingMaterialCount(newCount);
            }
        }
    }
    
    @Override
    public void substractPetResources(Pet pet, Refrigerator refrigerator)
            throws NotEnoughPetResourcesException {
        Map<BuildingMaterialId, PetBuildingMaterial> petBuildingMaterials
                = pet.getBuildingMaterials();
        Map<BuildingMaterialId, RefrigeratorCost> refrigeratorCosts
                = refrigerator.getRefrigeratorCosts();
        Map<BuildingMaterialId, Integer> resourceCosts
                = refrigeratorCosts.entrySet().stream().collect(
                () -> new HashMap<BuildingMaterialId, Integer>(),
                (map, entry) -> {
                    map.put(entry.getKey(), entry.getValue().getCost());
                }, (map1, map2) -> {
                    map1.putAll(map2);
                });
        substractPetResources(petBuildingMaterials, resourceCosts);
    }
    
    @Override
    public void substractPetResources(Pet pet, Bookcase bookcase)
            throws NotEnoughPetResourcesException {
        Map<BuildingMaterialId, PetBuildingMaterial> petBuildingMaterials
                = pet.getBuildingMaterials();
        Map<BuildingMaterialId, BookcaseCost> refrigeratorCosts
                = bookcase.getBookcaseCosts();
        Map<BuildingMaterialId, Integer> resourceCosts
                = refrigeratorCosts.entrySet().stream().collect(
        () -> new HashMap<BuildingMaterialId, Integer>(),
                (map, entry) -> {
                    map.put(entry.getKey(), entry.getValue().getCost());
                }, (map1, map2) -> {
                    map1.putAll(map2);
                });
        substractPetResources(petBuildingMaterials, resourceCosts);
    }
    
    @Override
    public void substractPetResources(Pet pet,
            MachineWithDrinks machineWithDrinks)
                throws NotEnoughPetResourcesException {
        Map<BuildingMaterialId, PetBuildingMaterial> petBuildingMaterials
                = pet.getBuildingMaterials();
        Map<BuildingMaterialId, MachineWithDrinksCost> machineWithDrinksCosts
                = machineWithDrinks.getMachineWithDrinksCosts();
        Map<BuildingMaterialId, Integer> resourceCosts
                = machineWithDrinksCosts.entrySet().stream().collect(
        () -> new HashMap<BuildingMaterialId, Integer>(),
                (map, entry) -> {
                    map.put(entry.getKey(), entry.getValue().getCost());
                }, (map1, map2) -> {
                    map1.putAll(map2);
                });
        substractPetResources(petBuildingMaterials, resourceCosts);
    }

    /**
     * Возвращает количество новых, ещё непрочитанных записей
     * в дневнике питомца.
     * @param petId Первичный ключ питомца.
     * @return Количество новых записей.
     */
    @Override
    public Long getPetNewJournalEntriesCount(Integer petId) {
        return petDao.getPetNewJournalEntriesCount(petId);
    }

    @Override
    public List<AchievementId> calculateAchievements(Pet pet) {
        List<AchievementId> result = new ArrayList<AchievementId>();
        Map<AchievementId, PetAchievement> map = pet.getAchievements();
        for (PetAchievement pa : map.values()) {
            if (!pa.getWasShown()) {
                pa.setWasShown(true);
                result.add(pa.getAchievement());
            }
        }
        return result;
    }

    @Override
    public Iterable<Pet> findLastCreatedPets(int start, int limit) {
        return petDao.findLastCreatedPets(start, limit);
    }
    
    @Override
    @PreAuthorize("hasRole('USER')")
    public GetPetBooksResult getPetBooks(
            UserPetDetails userPetDetails)
            throws ServiceException {
        Pet pet = petDao.findByIdWithFullBooks(userPetDetails.petId())
                .orElseThrow();
        Set<Book> books = pet.getBooks();
        
        List<ru.urvanov.virtualpets.server.controller.api.domain.Book> resultBooks = books.stream()
                .map(b -> conversionService.convert(
                        b,
                        ru.urvanov.virtualpets.server.controller.api.domain.Book.class))
                .collect(Collectors.toList());
        
        return new GetPetBooksResult(resultBooks);
    }
    

    @Override
    @PreAuthorize("hasRole('USER')")
    public GetPetClothsResult getPetCloths(UserPetDetails userPetDetails) {
        Pet pet = petDao.findByIdWithFullCloths(userPetDetails.petId())
                .orElseThrow();
        Set<Cloth> cloths = pet.getCloths();
        
        List<ru.urvanov.virtualpets.server.controller.api.domain.Cloth> resultCloths
                = cloths.stream()
                        .map(c -> conversionService.convert(
                                c,
                                ru.urvanov.virtualpets.server.controller.api.domain.Cloth.class))
                .collect(Collectors.toList());

        ru.urvanov.virtualpets.server.controller.api.domain.GetPetClothsResult result = new ru.urvanov.virtualpets.server.controller.api.domain.GetPetClothsResult(
                Optional.of(pet).map(Pet::getHat).map(Cloth::getId).orElse(null),
                Optional.of(pet).map(Pet::getCloth).map(Cloth::getId).orElse(null),
                Optional.of(pet).map(Pet::getBow).map(Cloth::getId).orElse(null),
                resultCloths);
        return result;
    }
    
    @Override
    @PreAuthorize("hasRole('USER')")
    @Transactional(rollbackFor = ServiceException.class)
    public void savePetCloths(UserPetDetails userPetDetails,
            SavePetCloths saveClothArg)
            throws ServiceException {
        Pet pet = petDao.findById(userPetDetails.petId()).orElseThrow();
        Cloth hat = null;
        if (saveClothArg.hatId() != null) {
            // Получаем прокси сущности Cloth
            // без лишнего обращения к базе данных.
            hat = clothDao.getReferenceById(saveClothArg.hatId());
        }
        Cloth cloth = null;
        if (saveClothArg.clothId() != null) {
            // Получаем прокси сущности Cloth
            // без лишнего обращения к базе данных.
            cloth = clothDao.getReferenceById(saveClothArg.clothId());
        }
        Cloth bow = null;
        if (saveClothArg.bowId() != null) {
            // Получаем прокси сущности Cloth
            // без лишнего обращения к базе данных.
            bow = clothDao.getReferenceById(saveClothArg.bowId());
        }
        pet.setHat(hat);
        pet.setCloth(cloth);
        pet.setBow(bow);
    }
    
    @Override
    @PreAuthorize("hasRole('USER')")
    public GetPetDrinksResult getPetDrinks(UserPetDetails userPetDetails)
            throws ServiceException {
        Pet pet = petDao.findByIdWithFullDrinks(userPetDetails.petId())
                .orElseThrow();
        Map<DrinkId, PetDrink> drinks = pet.getDrinks();
        List<ru.urvanov.virtualpets.server.controller.api.domain.Drink> resultDrinks
                = drinks
                .values().stream()
                .map(d -> conversionService.convert(
                        d,
                        ru.urvanov.virtualpets.server.controller.api.domain.Drink.class))
                .collect(Collectors.toList());

        return new GetPetDrinksResult(resultDrinks);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public GetPetFoodsResult getPetFoods(UserPetDetails userPetDetails)
            throws ServiceException {
        Iterable<PetFood> petFoods = petDao.findByIdWithFullFoods(
                userPetDetails.petId())
                .map(Pet::getFoods)
                .map(Map::values)
                .orElseThrow();
        List<ru.urvanov.virtualpets.server.controller.api.domain.Food> resultFoods
                = StreamSupport.stream(petFoods.spliterator(), false)
                .map(f -> conversionService.convert(
                        f,
                        ru.urvanov.virtualpets.server.controller.api.domain.Food.class))
                .collect(Collectors.toList());
        return new GetPetFoodsResult(resultFoods);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @Transactional(rollbackFor = ServiceException.class )
    public GetPetJournalEntriesResult getPetJournalEntries(
            UserPetDetails userPetDetails, int count)
            throws ServiceException {
        Iterable<PetJournalEntry> serverPetIterator
                = petJournalEntryDao.findLastByPetId(
                        userPetDetails.petId(), count);
        List<PetJournalEntry> serverPetJournalEntries
                = StreamSupport.stream(serverPetIterator.spliterator(), false).toList();
        
        serverPetJournalEntries.stream().filter(Predicate.not(
                ru.urvanov.virtualpets.server.dao.domain.PetJournalEntry::isReaded))
                .forEach(v -> v.setReaded(true));

        List<ru.urvanov.virtualpets.server.controller.api.domain.PetJournalEntry> apiEntries = serverPetJournalEntries
                .stream()
                .map(serverPetJournalEntry ->
                new ru.urvanov.virtualpets.server.controller.api.domain.PetJournalEntry(
                        serverPetJournalEntry.getCreatedAt(),
                        serverPetJournalEntry.getJournalEntry()))
                .sorted().toList();
        return new GetPetJournalEntriesResult(apiEntries);
    }
    
    @Override
    @PreAuthorize("hasRole('USER')")
    public PetListResult getUserPets(UserPetDetails userPetDetails)
            throws ServiceException {
        List<Pet> pets = petDao.findByUserId(userPetDetails.userId());

        List<PetInfo> petInfos = pets.stream()
                .map(p -> conversionService.convert(p, PetInfo.class))
                .collect(Collectors.toList());
        return new PetListResult(petInfos);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @Transactional(rollbackFor = ServiceException.class )
    public void create(UserPetDetails userPetDetails,
            CreatePetArg createPetArg) throws ServiceException {
        Pet pet = new Pet();
        pet.setName(createPetArg.name());
        pet.setCreatedDate(OffsetDateTime.now(clock));
        pet.setUser(userDao.getReferenceById(userPetDetails.userId()));
        pet.setComment(createPetArg.comment());
        pet.setPetType(createPetArg.petType());
        Level level = levelDao.findById(1).orElseThrow();
        pet.setLevel(level);
        petDao.save(pet);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @Transactional(rollbackFor = ServiceException.class )
    public void select(UserPetDetails userPetDetails,
            SelectPetArg selectPetArg) throws ServiceException {
        int id = selectPetArg.petId();
        Pet pet = petDao.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));
        if (!pet.getUser().getId().equals(userPetDetails.userId())) {
            throw new PetNotFoundException(id);
        }
        OffsetDateTime currentDateTime = OffsetDateTime.now(clock);
        boolean fireAchievement = false;
        if (pet.getEveryDayLoginLast() == null) {
            fireAchievement = true;
        } else if (pet.getEveryDayLoginLast().plusDays(1).minusHours(6)
                .compareTo(currentDateTime) < 0) {
            if (pet.getEveryDayLoginLast().plusDays(2)
                    .compareTo(currentDateTime) > 0) {
                fireAchievement = true;
            } else {
                pet.setEveryDayLoginCount(0);
                pet.setEveryDayLoginLast(currentDateTime);
            }
        }

        if (fireAchievement) {
            pet.setEveryDayLoginCount(pet.getEveryDayLoginCount() + 1);
            pet.setEveryDayLoginLast(currentDateTime);
            this.addAchievementIfNot(pet, AchievementId.valueOf(
                    "EVERY_DAY_LOGIN_" + pet.getEveryDayLoginCount()));
        }

        pet.setLoginDate(currentDateTime);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @Transactional(rollbackFor = ServiceException.class)
    public void drink(UserPetDetails userPetDetails, DrinkArg drinkArg)
            throws ServiceException {
        Pet pet = petDao
                .findByIdWithDrinksAndJournalEntriesAndAchievements(
                        userPetDetails.petId()).orElseThrow();
        Map<DrinkId, PetDrink> drinks = pet.getDrinks();
        PetDrink petDrink = drinks.get(drinkArg.drinkId());
        petDrink.setDrinkCount(petDrink.getDrinkCount() - 1);
        pet.setDrink(100);
        if (pet.getJournalEntries()
                .get(JournalEntryId.BUILD_REFRIGERATOR) == null) {
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(OffsetDateTime.now(clock));
            newPetJournalEntry.setPet(pet);
            newPetJournalEntry
                    .setJournalEntry(JournalEntryId.BUILD_REFRIGERATOR);
            newPetJournalEntry.setReaded(false);
            pet.getJournalEntries().put(
                    newPetJournalEntry.getJournalEntry(),
                    newPetJournalEntry);
        }
        if (pet.getDrinkCount() < Integer.MAX_VALUE)
            pet.setDrinkCount(pet.getDrinkCount() + 1);
        if (pet.getDrinkCount() == 1)
            addAchievementIfNot(pet, AchievementId.DRINK_1);
        if (pet.getDrinkCount() == 10)
            addAchievementIfNot(pet, AchievementId.DRINK_10);
        if (pet.getDrinkCount() == 100)
            addAchievementIfNot(pet, AchievementId.DRINK_100);
        addExperience(pet, 1);
    }

    @Override
    public void addAchievementIfNot(Pet pet, AchievementId achievement) {
        if (!pet.getAchievements().containsKey(achievement)) {
            PetAchievement petAchievement = new PetAchievement();
            petAchievement.setPet(pet);
            petAchievement.setAchievement(achievement);
            pet.getAchievements().put(achievement, petAchievement);
        }
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @Transactional(rollbackFor = ServiceException.class)
    public void satiety(UserPetDetails userPetDetails,
            SatietyArg satietyArg)
            throws ServiceException {
        Pet pet = petDao.findByIdWithFoodsJournalEntriesAndAchievements(
                userPetDetails.petId()).orElseThrow();
        PetFood food = petFoodDao.findByPetIdAndFoodType(pet.getId(),
                satietyArg.foodId()).orElseThrow();
        if (food == null) {
            throw new ServiceException("Food count = 0.");
        } else {
            if (food.getFoodCount() == 0) {
                throw new ServiceException("Food count = 0.");
            }
            food.setFoodCount(food.getFoodCount() - 1);
        }
        pet.setSatiety(100);
        if (pet.getJournalEntries()
                .get(JournalEntryId.BUILD_BOOKCASE) == null) {
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(OffsetDateTime.now(clock));
            newPetJournalEntry.setPet(pet);
            newPetJournalEntry
                    .setJournalEntry(JournalEntryId.BUILD_BOOKCASE);
            newPetJournalEntry.setReaded(false);
            pet.getJournalEntries().put(
                    newPetJournalEntry.getJournalEntry(),
                    newPetJournalEntry);
        }
        if (pet.getEatCount() < Integer.MAX_VALUE)
            pet.setEatCount(pet.getEatCount() + 1);
        if (pet.getEatCount() == 1)
            addAchievementIfNot(pet, AchievementId.FEED_1);
        if (pet.getEatCount() == 10)
            addAchievementIfNot(pet, AchievementId.FEED_10);
        if (pet.getEatCount() == 100)
            addAchievementIfNot(pet, AchievementId.FEED_100);
        addExperience(pet, 1);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @Transactional(rollbackFor = ServiceException.class)
    public void education(UserPetDetails userPetDetails)
            throws ServiceException {
        Pet pet = petDao.findByIdWithJournalEntriesAndAchievements(
                userPetDetails.petId()).orElseThrow();
        pet.setEducation(100);

        if (pet.getJournalEntries()
                .get(JournalEntryId.LEAVE_ROOM) == null) {
            PetJournalEntry newPetJournalEntry = new PetJournalEntry();
            newPetJournalEntry.setCreatedAt(OffsetDateTime.now(clock));
            newPetJournalEntry.setPet(pet);
            newPetJournalEntry
                    .setJournalEntry(JournalEntryId.LEAVE_ROOM);
            newPetJournalEntry.setReaded(false);
            pet.getJournalEntries().put(
                    newPetJournalEntry.getJournalEntry(),
                    newPetJournalEntry);
        }

        if (pet.getTeachCount() < Integer.MAX_VALUE)
            pet.setTeachCount(pet.getTeachCount() + 1);
        if (pet.getTeachCount() == 1)
            addAchievementIfNot(pet, AchievementId.TEACH_1);
        if (pet.getTeachCount() == 10)
            addAchievementIfNot(pet, AchievementId.TEACH_10);
        if (pet.getTeachCount() == 100)
            addAchievementIfNot(pet, AchievementId.TEACH_100);
        addExperience(pet, 1);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @Transactional(rollbackFor = ServiceException.class)
    public void mood(UserPetDetails userPetDetails)
            throws ServiceException {
        Pet pet = petDao.findById(userPetDetails.petId())
                .orElseThrow();
        pet.setMood(100);
        addExperience(petDao.findById(pet.getId()).orElseThrow(), 1);
    }
    
    
    @Override
    @PreAuthorize("hasRole('USER')")
    public GetPetRucksackInnerResult getPetRucksackInner(
            UserPetDetails userPetDetails)
            throws ServiceException {
        Pet pet = petDao.findByIdWithFullBuildingMaterials(
                userPetDetails.petId()).orElseThrow();
        Map<BuildingMaterialId, PetBuildingMaterial> buildingMaterials = pet
                .getBuildingMaterials();

        Map<BuildingMaterialId, Integer> buildingMaterialCounts = buildingMaterials
                .entrySet().stream()
                .<Entry<BuildingMaterialId, Integer>> map(
                        e -> Map.entry(e.getKey(),
                                e.getValue().getBuildingMaterialCount()))
                .collect(Collectors.toMap(Entry::getKey,
                        Entry::getValue));

        return new GetPetRucksackInnerResult(buildingMaterialCounts);
    }

    public PetDetails petInformationPage(Integer id)
            throws PetNotFoundException {
        Pet fullPet = petDao.findByIdWithJournalEntriesAndAchievements(id)
                .orElseThrow(() -> new PetNotFoundException());
        PetDetails result = new PetDetails();
        result.setId(fullPet.getId());
        result.setName(fullPet.getName());
        result.setLevel(fullPet.getLevel().getId());
        result.setExperience(fullPet.getExperience());
        List<PetInformationPageAchievement> achievements = new ArrayList<>();
        result.setAchievements(achievements);
        for (AchievementId achievement : AchievementId.values()) {
            PetInformationPageAchievement petInformationPageAchievement
                    = new PetInformationPageAchievement();
            petInformationPageAchievement.setCode(achievement.name());
            petInformationPageAchievement.setUnlocked(
                    fullPet.getAchievements().containsKey(achievement));
            achievements.add(petInformationPageAchievement);
        }
        return result;
    }
    
    @Override
    @PreAuthorize("hasRole('USER')")
    @Transactional(rollbackFor = ServiceException.class)
    public void delete(UserPetDetails userPetDetails, Integer petId)
            throws ServiceException {
        Pet pet = petDao.findFullById(petId)
                .orElseThrow(() -> new PetNotFoundException(petId));
        if (pet.getUser().getId().equals(userPetDetails.userId())) {
            roomDao.findByPetId(petId).ifPresent(r -> roomDao.delete(r));
            petDao.delete(pet);
        } else {
            throw new PetNotFoundException(petId);
        }
    }
}
