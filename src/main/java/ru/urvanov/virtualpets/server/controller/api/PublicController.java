package ru.urvanov.virtualpets.server.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import ru.urvanov.virtualpets.server.auth.UserDetailsImpl;
import ru.urvanov.virtualpets.server.controller.api.domain.LoginArg;
import ru.urvanov.virtualpets.server.controller.api.domain.LoginResult;
import ru.urvanov.virtualpets.server.controller.api.domain.RecoverPasswordArg;
import ru.urvanov.virtualpets.server.controller.api.domain.RegisterArgument;
import ru.urvanov.virtualpets.server.controller.api.domain.ServerTechnicalInfo;
import ru.urvanov.virtualpets.server.dao.domain.Role;
import ru.urvanov.virtualpets.server.service.PublicApiService;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@RestController // (1)
@RequestMapping(value = "api/v1/PublicService",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE) // (2)
public class PublicController extends ControllerBase { // (3)

    @Autowired
    public PublicApiService publicService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private SecurityContextRepository securityContextRepository;

    @ResponseStatus(HttpStatus.NO_CONTENT) // (1)
    @RequestMapping(method = RequestMethod.POST, value = "register")//(2)
    @Operation(summary = "Регистрация нового игрока.")
    public void register(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestBody @Valid RegisterArgument registerArgument) // (3)
                    throws ServiceException {
        publicService.register(registerArgument);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.POST,
            value = "recoverPassword")
    @Operation(summary = "Восстановление пароля.")
    public void recoverPassword(
            @RequestBody @Valid RecoverPasswordArg recoverPasswordArg)
                    throws ServiceException {
        publicService.recoverPassword(recoverPasswordArg);
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "server-technical-info")
    public ServerTechnicalInfo getServerTechnicalInfo(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
            throws ServiceException {
        return publicService.getServerTechnicalInfo();
    }

    @RequestMapping(method = RequestMethod.POST, value = "login")
    @Operation(summary = "Аутентификация пользователя.")
    public LoginResult login(
            //@io.swagger.v3.oas.annotations.parameters.RequestBody(
            //        description = "ТЕСТ", required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = LoginArg.class )))
            @RequestBody @Valid LoginArg loginArg,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse)
                    throws ServiceException {
        
        // UsernamePasswordAuthenticationToken с 
        // неаутентифицированным пользователем.
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        loginArg.login(), loginArg.password());
        
        // Попытка аутентифицировать пользователя
        // при этом будет вызван метод севриса UserDetailsService
        // для получения информации о пользователе из базы данных
        // по логину.
        Authentication authenticationResponse
                = authenticationManager.authenticate(
                        authenticationRequest);
        // Если оказались здесь, значит аутентификация прошла успешно.
        // В противном случае authenticationManager#authenticate
        // бросил бы исключение.
        
        // Вызов логики бизнес-слоя.
        publicService.login(loginArg);
        
        // Текущий контекст безопасности
        SecurityContext securityContext
                = SecurityContextHolder.getContext();
        // Заполнение текущий контекст аутентифицированным пользователем.
        securityContext.setAuthentication(authenticationResponse);
        
        // Сохранение контекста безопасности
        // в requestAttributes и в сессии.
        securityContextRepository.saveContext(
                securityContext,
                httpServletRequest,
                httpServletResponse);
        
        // Возвращение результат в клиент JavaScript.
        UserDetailsImpl userDetailsImpl
                = (UserDetailsImpl) authenticationResponse
                        .getPrincipal();
        return new LoginResult(
                true,
                null,
                userDetailsImpl.getUserId(),
                userDetailsImpl.getUsername(),
                userDetailsImpl.getName());
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "checkSession")
    @Operation(
            summary = "Проверка активной сессии.",
            description = """
                    Если сессия активна, то возвращает результат \
                    с success = true и заполненными полями \
                    о текущем пользователе. \
                    В противном случае в результате \
                    поле success = false.""")
    public LoginResult checkSession(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
                    throws ServiceException {
        if (userDetailsImpl != null && userDetailsImpl.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority()
                        .equals("ROLE_" + Role.USER.name()))) {
            return new LoginResult(true, null, userDetailsImpl.getUserId(), userDetailsImpl.getUsername(), userDetailsImpl.getName());
        }
        return new LoginResult(false, null, null, null, null);
    }
}
