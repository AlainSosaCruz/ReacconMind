package com.reacconmind.reacconmind.controller;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reacconmind.reacconmind.model.StatusType;
import com.reacconmind.reacconmind.model.User;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() throws Exception {
        assertThat(userController).isNotNull();
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    public void getAllTest() throws Exception {
        mvc
            .perform(
                get("/ReacconMind/users").accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(
                MockMvcResultMatchers.jsonPath("$", hasSize(greaterThan(0)))
            );
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    public void getAllActiveUsersTest() throws Exception {
        mvc
            .perform(
                get("/ReacconMind/users/usersActive").accept(
                    MediaType.APPLICATION_JSON
                )
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(
                MockMvcResultMatchers.jsonPath("$", hasSize(greaterThan(0)))
            );
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    public void getByIdUserTest() throws Exception {
        mvc
            .perform(
                get("/ReacconMind/users/1").accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.idUser", is(1)));
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    public void getByIdUserNotFoundTest() throws Exception {
        mvc
            .perform(
                get("/ReacconMind/users/0").accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    public void addUserTest() throws Exception {
        User user = new User();
        user.setName("test");
        user.setImageFacade("testImage.example");
        user.setImageProfile("testProfile.exame");
        user.setBiography("Hi this is example");
        user.setUserName("ExampleUsers");

        mvc
            .perform(
                post("/ReacconMind/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(user))
            )
            .andExpect(status().isOk())
            .andExpect(content().string("User added successfully"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    public void updateUserTest() throws Exception {
        User user = new User();
        user.setName("updateTest");
        user.setImageFacade("testImage.example");
        user.setImageProfile("testProfile.exame");
        user.setBiography("Hi this is example update");
        user.setUserName("Update");

        mvc
            .perform(
                put("/ReacconMind/users/update/2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(user))
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("Updated record"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    public void updateUserStatusTest() throws Exception {
        StatusType status = StatusType.Inactive;

        mvc
            .perform(
                put("/ReacconMind/users/updateStatus/3")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(status))
            )
            .andExpect(status().isOk())
            .andExpect(content().string("User status updated successfully"));
    }
}
