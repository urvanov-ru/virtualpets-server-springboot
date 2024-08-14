package ru.urvanov.virtualpets.server.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import ru.urvanov.virtualpets.server.auth.UserDetailsImpl;
import ru.urvanov.virtualpets.server.controller.api.domain.CreatePetArg;
import ru.urvanov.virtualpets.server.controller.api.domain.DrinkArg;
import ru.urvanov.virtualpets.server.controller.api.domain.GetPetBooksResult;
import ru.urvanov.virtualpets.server.controller.api.domain.GetPetClothsResult;
import ru.urvanov.virtualpets.server.controller.api.domain.GetPetDrinksResult;
import ru.urvanov.virtualpets.server.controller.api.domain.GetPetFoodsResult;
import ru.urvanov.virtualpets.server.controller.api.domain.GetPetJournalEntriesResult;
import ru.urvanov.virtualpets.server.controller.api.domain.GetPetRucksackInnerResult;
import ru.urvanov.virtualpets.server.controller.api.domain.PetListResult;
import ru.urvanov.virtualpets.server.controller.api.domain.SatietyArg;
import ru.urvanov.virtualpets.server.controller.api.domain.SavePetCloths;
import ru.urvanov.virtualpets.server.controller.api.domain.SelectPetArg;
import ru.urvanov.virtualpets.server.controller.api.domain.SelectedPet;
import ru.urvanov.virtualpets.server.service.PetApiService;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@RestController("apiPetController")
@RequestMapping(value = "api/v1/PetService",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class PetController extends ControllerBase {

    @Autowired
    private PetApiService petService;

    @Autowired
    private SelectedPet selectedPet;

    @RequestMapping(value = "getUserPets", method = RequestMethod.GET)
    @Operation(summary = "Получение списка питомцев.")
    public PetListResult getUserPets(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
                    throws ServiceException {
        return petService.getUserPets(
                new UserPetDetails(
                    userDetailsImpl.getUserId(),
                    selectedPet.getPetId()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @Operation(summary = "Создание питомца.")
    public void create(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody @Valid CreatePetArg createPetArg)
                    throws ServiceException {
        petService.create(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                createPetArg);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "select", method = RequestMethod.POST)
    @Operation(summary = "Выбор питомца на текущий сеанс игры.")
    public void select(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody @Valid SelectPetArg selectPetArg)
                    throws ServiceException {
        petService.select(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                selectPetArg);
        selectedPet.setPetId(selectPetArg.petId());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "delete/{petId}",
            method = RequestMethod.DELETE)
    @Operation(summary = "Удаление питомца.")
    public void delete(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @PathVariable("petId") @Min(1) Integer petId)
                    throws ServiceException {
        petService.delete(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                petId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "drink", method = RequestMethod.POST)
    @Operation(summary = "Употребление напитка.")
    public void drink(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody @Valid DrinkArg drinkArg)
                    throws ServiceException {
        petService.drink(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                drinkArg);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "satiety", method = RequestMethod.POST)
    @Operation(summary = "Поедание блюда.")
    public void eat(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody @Valid SatietyArg satietyArg)
                    throws ServiceException {
        petService.satiety(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                satietyArg);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "education", method = RequestMethod.POST)
    @Operation(summary = "Чтение книги.")
    public void education(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
                    throws ServiceException {
        petService.education(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()));
    }

    @GetMapping(value = "getPetBooks")
    @Operation(summary = "Получения содержимого книжного шкафа.")
    public GetPetBooksResult getPetBooks(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
                    throws ServiceException {
        return petService.getPetBooks(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()));
    }

    @GetMapping(value = "getPetCloths")
    @Operation(summary = "Получение содержимого гардеробной.")
    public GetPetClothsResult getPetCloths(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
                    throws ServiceException {
        return petService.getPetCloths(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()));
    }

    @PostMapping(value = "savePetCloths")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Сохранение набора одежды.")
    public void savePetCloth(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody @Valid SavePetCloths saveClothArg)
                    throws ServiceException {
        petService.savePetCloths(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                saveClothArg);
    }

    @GetMapping(value = "getPetDrinks")
    @Operation(summary = "Получение содержимого машины с напитками.")
    public GetPetDrinksResult getPetDrinks(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
                    throws ServiceException {
        return petService.getPetDrinks(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()));
    }

    @GetMapping(value = "getPetFoods")
    @Operation(summary = "Получение содержимого холодильника.")
    public GetPetFoodsResult getPetFoods(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
                    throws ServiceException {
        return petService.getPetFoods(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()));
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "getPetJournalEntries")
    @Operation(summary = "Получение содержимого дневника питомца.")
    public GetPetJournalEntriesResult getPetJournalEntries(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestParam(name = "count") int count)
                    throws ServiceException {
        return petService.getPetJournalEntries(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                count);
    }

    @Operation(summary = "Получение содержимого рюкзака.")
    @GetMapping(value = "getPetRucksackInner")
    public GetPetRucksackInnerResult getPetRucksackInner(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
                    throws ServiceException {
        return petService.getPetRucksackInner(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()));
    }
}
