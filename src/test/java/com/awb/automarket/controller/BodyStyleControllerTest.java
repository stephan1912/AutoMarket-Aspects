package com.awb.automarket.controller;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.bodyStyleDto.BodyStyleDTO;
import com.awb.automarket.entity.Feature;
import com.awb.automarket.entity.User;
import com.awb.automarket.repository.FeatureRepository;
import com.awb.automarket.repository.UserRepository;
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
class BodyStyleControllerTest {

    @Autowired
    private WebApplicationContext context;

    MockMvc mvc;

    private final String baseUrl = "/api/v1/bodyStyle";

    @BeforeAll
    void setupBefore(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("dbScripts/insertUsers.sql"));
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("dbScripts/insertBodyStyle.sql"));
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
    @WithMockUser(username = "admin", password = "adminpass", roles = {"ADMIN"})
    void updateBodyStyleWithNameThatAlreadyExists() throws Exception {
        BodyStyleDTO bs = new BodyStyleDTO();
        bs.setBs_id(TestUtils.getId(3));
        bs.setName("Coupe");
        bs.setDescription("Some description");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedJson = ow.writeValueAsString(ServiceResponseModel.Conflict("O caroserie cu acest nume exista deja!").errorResponse);


        mvc.perform(put(baseUrl).contentType(MediaType.APPLICATION_JSON).content(bs.toString()))
                .andExpect(status().is(409))
                .andExpect(content().json(expectedJson));

    }

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = {"ADMIN"})
    void createBodyStyleWithDescriptionOutOfRange() throws Exception {
        BodyStyleDTO bs = new BodyStyleDTO();
        bs.setName("Test");
        bs.setDescription("So");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedJson = ow.writeValueAsString(ServiceResponseModel.NotValid("Campul 'Descriere' trebuie sa aiba intre 10 si 200 caractere.").errorResponse);


        mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(bs.toString()))
                .andExpect(status().is(400))
                .andExpect(content().json(expectedJson));

    }


    @Test
    @WithMockUser(username = "user", password = "userpass", roles = {"USER"})
    void findBodyStyleByIdAsUSER() throws Exception {

        mvc.perform(get(baseUrl + "/" + TestUtils.getId(1)))
                .andExpect(status().is(200));

    }

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = {"ADMIN"})
    void tryAndDeleteABodyStyle() throws Exception {

        mvc.perform(delete(baseUrl + "/" + TestUtils.getId(4)))
                .andExpect(status().is(200));

        mvc.perform(delete(baseUrl + "/" + TestUtils.getId(9)))
                .andExpect(status().is(404));

    }
}
