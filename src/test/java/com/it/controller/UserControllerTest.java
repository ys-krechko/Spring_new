package com.it.controller;

import com.it.config.DatabaseConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = {TestWebConfiguration.class, DatabaseConfiguration.class})
@WithMockUser(username = "user.admin", roles = {"ADMIN"})
@WebAppConfiguration
@Transactional
public class UserControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetOneExist() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("user.simple"))
                .andReturn();
    }

    @Test
    public void testGetOneNotExist() throws Exception {
        mockMvc.perform(get("/users/3"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("User doesn't exist!"))
                .andReturn();
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("user.simple"))
                .andExpect(jsonPath("$[1].name").value("user.admin"))
                .andReturn();
    }

    @Test
    public void testSaveRedirect() throws Exception {
        mockMvc.perform(post("/users").contentType(APPLICATION_JSON_UTF8).content("{\"name\":\"user.simple\"}"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost:8081/sp/authentication/signUp"))
                .andReturn();
    }

    @Test
    @WithAnonymousUser
    public void testSaveAccessDenied() throws Exception {
        mockMvc.perform(post("/users").contentType(APPLICATION_JSON_UTF8).content("{\"name\":\"user.simple\"}"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("Access is denied"))
                .andReturn();
    }

    @Test
    public void testUpdateOneExist() throws Exception {
        mockMvc.perform(put("/users/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"name\":\"user.not.simple\",\"password\":125,\"rolesId\":[1]}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("user.not.simple"))
                .andReturn();
    }

    @Test
    public void testUpdateOneNotExist() throws Exception{
        mockMvc.perform(put("/users/3").contentType(APPLICATION_JSON_UTF8).content("{\"id\":3,\"name\":\"user.not.simple\",\"password\":125,\"rolesId\":[1]}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("User doesn't exist!"))
                .andReturn();
    }

    @Test
    public void testUpdateBadRequestWrongUrlId() throws Exception {
        mockMvc.perform(put("/users/2").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"name\":\"user.not.simple\",\"password\":125,\"rolesId\":[1]}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to userId!"))
                .andReturn();
    }

    @Test
    public void testUpdateBadRequestRoleIsNull() throws Exception {
        mockMvc.perform(put("/users/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"name\":\"user.not.simple\",\"password\":125,\"rolesId\":[null]}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("User must have a Role!"))
                .andReturn();
    }

    @Test
    public void testUpdateBadRequestRoleIsEmpty() throws Exception {
        mockMvc.perform(put("/users/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"name\":\"user.not.simple\",\"password\":125,\"rolesId\":[]}"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("User's Role must not be empty!"))
                .andReturn();
    }

    @Test
    public void testUpdateBadRequestUserNameNotUnique() throws Exception{
        mockMvc.perform(put("/users/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"name\":\"user.admin\",\"password\":123,\"rolesId\":[1]}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("User name is not unique!"))
                .andReturn();
    }
}
