package com.awb.automarket.controller;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.countryDto.CountryDTO;
import com.awb.automarket.dto.userDto.UserDto;
import com.awb.automarket.entity.Feature;
import com.awb.automarket.entity.User;
import com.awb.automarket.repository.FeatureRepository;
import com.awb.automarket.repository.UserRepository;
import com.awb.automarket.security.AuthRequest;
import com.awb.automarket.security.CustomUserDetails;
import com.awb.automarket.security.JwtUtil;
import com.awb.automarket.utils.TestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    MockMvc mvc;

    private final String baseUrl = "/api/v1/user";

    @BeforeAll
    void setupBefore(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("dbScripts/insertUsers.sql"));
        }
    }

    @AfterAll
    void setupAfter(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("dbScripts/truncateTables.sql"));
        }
    }

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    void authenticateUserWithValidOrInvalidCredentials() throws Exception {
        AuthRequest authRequest = new AuthRequest("user", "userpass");
        mvc.perform(post(baseUrl + "/session").contentType(MediaType.APPLICATION_JSON).content(authRequest.toString()))
                .andExpect(status().is(200));


        authRequest = new AuthRequest("user", "userpass123");
        mvc.perform(post(baseUrl + "/session").contentType(MediaType.APPLICATION_JSON).content(authRequest.toString()))
                .andExpect(status().is(401));
    }

    @Test
    void createUserAndThanAuthenticate() throws Exception {
        UserDto user = new UserDto();
        user.setUsername("testUser1"); user.setEmail("ttest1@Email.com"); user.setFirstName("testUser"); user.setLastName("testUser");
        user.setPassword("testpass");

        mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(user.toString()))
                .andExpect(status().is(200));

        AuthRequest authRequest = new AuthRequest(user.getUsername(), user.getPassword());
        mvc.perform(post(baseUrl + "/session").contentType(MediaType.APPLICATION_JSON).content(authRequest.toString()))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = {"ADMIN"})
    void tryAndAddUsers() throws Exception {
        UserDto user = new UserDto();
        user.setUsername("username1"); user.setEmail("user3@email.com"); user.setFirstName("testUser"); user.setLastName("testUser");
        user.setPassword("testUser");

        mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(user.toString()))
                .andExpect(status().is(409));

        user = new UserDto();
        user.setUsername("testUser"); user.setEmail("t"); user.setFirstName("testUser"); user.setLastName("testUser");
        user.setPassword("testUser");

        mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(user.toString()))
                .andExpect(status().is(400));


        user = new UserDto();
        user.setUsername("testUser"); user.setEmail("ttest@Email.com"); user.setFirstName("tes"); user.setLastName("testUser");
        user.setPassword("testUser");

        mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(user.toString()))
                .andExpect(status().is(400));


        user = new UserDto();
        user.setUsername("testUser"); user.setEmail("ttest@Email.com"); user.setFirstName("testUser"); user.setLastName("testUser");
        user.setPassword("t");

        mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(user.toString()))
                .andExpect(status().is(400));

        user = new UserDto();
        user.setUsername("testUser"); user.setEmail("ttest@Email.com"); user.setFirstName("testUser"); user.setLastName("testUser");
        user.setPassword("testpass");

        mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(user.toString()))
                .andExpect(status().is(200));

    }

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = {"ADMIN"})
    void deleteAnExistingAndANonExistingUser() throws Exception {


        mvc.perform(delete(baseUrl + "/" + TestUtils.getId(9)))
                .andExpect(status().is(404));

        mvc.perform(delete(baseUrl + "/" + TestUtils.getId(4)))
                .andExpect(status().is(200));

    }
}
