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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import ru.urvanov.virtualpets.server.auth.UserDetailsImpl;
import ru.urvanov.virtualpets.server.controller.api.domain.GetRoomInfoResult;
import ru.urvanov.virtualpets.server.controller.api.domain.OpenBoxNewbieResult;
import ru.urvanov.virtualpets.server.controller.api.domain.Point;
import ru.urvanov.virtualpets.server.controller.api.domain.RoomBuildMenuCosts;
import ru.urvanov.virtualpets.server.controller.api.domain.SelectedPet;
import ru.urvanov.virtualpets.server.service.RoomApiService;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@RestController
@RequestMapping(value = "api/v1/RoomService",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomController extends ControllerBase {

    @Autowired
    private RoomApiService roomService;

    @Autowired
    private SelectedPet selectedPet;

    @GetMapping(value = "getRoomInfo")
    @Operation(summary = "Получение состояния игры.")
    public GetRoomInfoResult getRoomInfo(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
                    throws ServiceException {
        return roomService.getRoomInfo(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "pickJournalOnFloor")
    @Operation(summary = "Поднятие дневника с пола.")
    public void pickJournalOnfloor(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
                    throws ServiceException {
        roomService.pickJournalOnFloor(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()));
    }

    @PostMapping(value = "getBuildMenuCosts")
    @Operation(summary = "Получение цен на строительство")
    public RoomBuildMenuCosts getBuildMenuCosts(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
                    throws ServiceException {
        return roomService.getBuildMenuCosts(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "journalClosed")
    @Operation(summary = "Событие закрытия дневника игроком.")
    public void journalClosed(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
                    throws ServiceException {
        roomService.journalClosed(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()));
    }

    @PostMapping(value = "openBoxNewbie/{index}/")
    @Operation(summary = "Открытие лутбокса.")
    public OpenBoxNewbieResult openBoxNewbie(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @PathVariable("index") int index)
                    throws ServiceException {
        return roomService.openBoxNewbie(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                index);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "buildMachineWithDrinks")
    @Operation(summary = "Строительство машины с напитками.")
    public void buildMachineWithDrinks(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody @Valid Point position)
                    throws ServiceException {
        roomService.buildMachineWithDrinks(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "moveMachineWithDrinks")
    @Operation(summary = "Перемещение машины с напитками.")
    public void moveMachineWithDrinks(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody @Valid  Point position)
            throws ServiceException {
        roomService.moveMachineWithDrinks(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "buildRefrigerator")
    @Operation(summary = "Строительство холодильника.")
    public void buildRefrigerator(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody @Valid Point position)
                    throws ServiceException {
        roomService.buildRefrigerator(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "moveRefrigerator")
    @Operation(summary = "Перемещение холодильника.")
    public void moveRefrigerator(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody @Valid Point position)
                    throws ServiceException {
        roomService.moveRefrigerator(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "buildBookcase")
    @Operation(summary = "Строительство книжного шкафа.")
    public void buildBookcase(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody @Valid Point position)
            throws ServiceException {
        roomService.buildBookcase(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "moveBookcase")
    @Operation(summary = "Перемещение книжного шкафа.")
    public void moveBookcase(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody @Valid Point position)
                    throws ServiceException {
        roomService.moveBookcase(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()),
                position);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "upgradeRefrigerator")
    @Operation(summary = "Улучшение холодильника.")
    public void upgradeRefrigerator(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
                    throws ServiceException {
        roomService.upgradeRefrigerator(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "upgradeBookcase")
    @Operation(summary = "Улучшение книжного шкафа.")
    public void upgradeBookcase(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
                    throws ServiceException {
        roomService.upgradeBookcase(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "upgradeMachineWithDrinks")
    @Operation(summary = "Улучшение машины с напитками..")
    public void upgradeMachineWithDrinks(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
                    throws ServiceException {
        roomService.upgradeMachineWithDrinks(
                new UserPetDetails(
                        userDetailsImpl.getUserId(),
                        selectedPet.getPetId()));
    }

}
