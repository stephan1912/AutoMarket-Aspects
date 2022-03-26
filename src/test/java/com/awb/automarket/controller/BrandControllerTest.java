package com.awb.automarket.controller;
import com.awb.automarket.dto.ServiceResponseModel;
import com.awb.automarket.dto.bodyStyleDto.BodyStyleDTO;
import com.awb.automarket.dto.brandDto.BrandDTO;
import com.awb.automarket.dto.countryDto.CountryDTO;
import com.awb.automarket.dto.modelDto.ModelDTO;
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
class BrandControllerTest {

    @Autowired
    private WebApplicationContext context;

    MockMvc mvc;

    private final String baseUrl = "/api/v1/brand";

    @BeforeAll
    void setupBefore(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("dbScripts/insertUsers.sql"));
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("dbScripts/insertBrand.sql"));
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("dbScripts/insertModel.sql"));
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
    void tryAndAddBrands() throws Exception {
        BrandDTO brand = new BrandDTO();
        brand.setName("Audi"); brand.setCode("AR");
        mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(brand.toString()))
                .andExpect(status().is(409));

        brand = new BrandDTO();
        brand.setName("A"); brand.setCode("ARASDASDASD");
        mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(brand.toString()))
                .andExpect(status().is(400));

        brand = new BrandDTO();
        brand.setName("A"); brand.setCode("AR");
        mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(brand.toString()))
                .andExpect(status().is(400));

        brand = new BrandDTO();
        brand.setName("Brand"); brand.setCode("ASDASDADSDAR");
        mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(brand.toString()))
                .andExpect(status().is(400));


        brand = new BrandDTO();
        brand.setName("Fiat"); brand.setCode("FI");
        mvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON).content(brand.toString()))
                .andExpect(status().is(200));

    }

    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = {"ADMIN"})
    void deleteBrandsThatExistAndDontExist() throws Exception {
        mvc.perform(delete(baseUrl + "/" + TestUtils.getId(4)))
                .andExpect(status().is(200));

        mvc.perform(delete(baseUrl + "/" + TestUtils.getId(4)))
                .andExpect(status().is(404));
    }


    @Test
    @WithMockUser(username = "admin", password = "adminpass", roles = {"ADMIN"})
    void tryAddAndRemoveModels() throws Exception {
        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setName("A3"); modelDTO.setGeneration("Gen"); modelDTO.setFinalYear(2020); modelDTO.setLaunchYear(2010);
        mvc.perform(post(baseUrl + "/" + TestUtils.getId(9) + "/model").contentType(MediaType.APPLICATION_JSON).content(modelDTO.toString()))
                .andExpect(status().is(404));

        modelDTO = new ModelDTO();
        modelDTO.setName("A1"); modelDTO.setGeneration("Gen"); modelDTO.setFinalYear(1800); modelDTO.setLaunchYear(1800);
        mvc.perform(post(baseUrl +"/" + TestUtils.getId(2) + "/model").contentType(MediaType.APPLICATION_JSON).content(modelDTO.toString()))
                .andExpect(status().is(400));


        modelDTO.setName("A1"); modelDTO.setGeneration("b3"); modelDTO.setFinalYear(2020); modelDTO.setLaunchYear(2015);
        mvc.perform(post(baseUrl +"/" + TestUtils.getId(2) + "/model").contentType(MediaType.APPLICATION_JSON).content(modelDTO.toString()))
                .andExpect(status().is(200));

        mvc.perform(delete(baseUrl +"/" + TestUtils.getId(3) + "/model/" + TestUtils.getId(6)))
                .andExpect(status().is(200));
    }
}
