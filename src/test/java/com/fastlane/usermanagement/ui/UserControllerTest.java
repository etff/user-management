package com.fastlane.usermanagement.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fastlane.usermanagement.application.UserService;
import com.fastlane.usermanagement.application.exception.UserNotFoundException;
import com.fastlane.usermanagement.dto.UserRequestDto;
import com.fastlane.usermanagement.dto.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private static final String GIVEN_PASSWORD = "1234";
    private static final String INVALID_PASSWORD = "";
    private static final long GIVEN_ID = 1L;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserRequestDto userRequestDto;
    private UserRequestDto failRequestDto;

    private UserResponseDto userResponseDto;

    @BeforeEach
    void setUp() {
        userRequestDto = new UserRequestDto(GIVEN_PASSWORD);
        failRequestDto = new UserRequestDto(INVALID_PASSWORD);
        userResponseDto = new UserResponseDto(GIVEN_ID);
    }

    @Test
    void registerUser() throws Exception {
        given(userService.registerUser(any()))
                .willReturn(userResponseDto);

        mockMvc.perform(
                        post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRequestDto))
                )
                .andExpect(header().exists("Location"))
                .andExpect(status().isCreated());
    }

    @Test
    void register_with_invalid_password() throws Exception {

        mockMvc.perform(
                        post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(failRequestDto))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUser() throws Exception {
        given(userService.getUser(any()))
                .willReturn(userResponseDto);

        mockMvc.perform(
                        get("/api/v1/users/{userId}", anyLong())
                )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        objectMapper.writeValueAsString(userResponseDto))
                );
    }

    @Test
    void getNotExistUser() throws Exception {
        given(userService.getUser(any()))
                .willThrow(UserNotFoundException.class);

        mockMvc.perform(
                        get("/api/v1/users/{userId}", anyLong())
                )
                .andExpect(status().isNotFound());
    }

}
