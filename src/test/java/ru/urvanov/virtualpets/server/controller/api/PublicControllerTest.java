package ru.urvanov.virtualpets.server.controller.api;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import ru.urvanov.virtualpets.server.controller.api.domain.ServerTechnicalInfo;
import ru.urvanov.virtualpets.server.service.PublicApiService;

/**
 * Базовый пример теста MockMvc
 */
class PublicControllerTest extends BaseControllerTest {

    @MockBean
    private PublicApiService publicApiService;
    
    @Test
    void getServerTechnicalInfo() throws Exception {
        Map<String, String> info = Map.of("java.version", "some_version");
        ServerTechnicalInfo expected = new ServerTechnicalInfo(info );
        when(publicApiService.getServerTechnicalInfo()).thenReturn(expected);
        mockMvc.perform(get("/api/v1/PublicService/server-technical-info")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(), content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.info").isMap())
                .andExpect(jsonPath("$.info['java.version']").isString());
        ;
    }
}
