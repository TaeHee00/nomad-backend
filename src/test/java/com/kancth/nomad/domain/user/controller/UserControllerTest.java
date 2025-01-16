package com.kancth.nomad.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kancth.nomad.domain.user.dto.SignUpRequest;
import com.kancth.nomad.domain.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class UserControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("SignUp API 성공")
    @Test
    void signUp() throws Exception {
        SignUpRequest request = SignUpRequest.builder()
                .loginId("testSignUpId")
                .email("testSignUpEmail@nomad.com")
                .name("김철수")
                .nickname("테스터")
                .password("testPw1!")
                .build();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        User.Response response = mapper.readValue(mvcResult.getResponse().getContentAsString(), User.Response.class);

        Assertions.assertThat(response.id()).isNotNull();
        Assertions.assertThat(request.loginId()).isEqualTo(response.loginId());
        Assertions.assertThat(request.email()).isEqualTo(response.email());
        Assertions.assertThat(request.name()).isEqualTo(response.name());
        Assertions.assertThat(request.nickname()).isEqualTo(response.nickname());
        Assertions.assertThat(request.password()).isEqualTo(response.password());
        Assertions.assertThat(response.verified()).isFalse();
    }
}