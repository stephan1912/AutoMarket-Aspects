package com.awb.automarket.utils;

import com.awb.automarket.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {


    public static User getAdminUser(){
        User admin = new User();
        admin.setRoles("ADMIN");
        admin.setActive(true);
        admin.setUsername("admin");
        admin.setEmail("admin@email.com");
        admin.setPassword("adminpass");
        admin.setFirstName("adminfn");
        admin.setLastName("adminln");
        return admin;
    }

    public static User getNormalUser(){
        User admin = new User();
        admin.setRoles("USER");
        admin.setActive(true);
        admin.setUsername("user");
        admin.setEmail("user@email.com");
        admin.setPassword("userpass");
        admin.setFirstName("userfn");
        admin.setLastName("userln");
        return admin;
    }

    public static final String baseId = "99999999";

    public static int getId(int id) {
        return 999999990 + id;
    }

    public static <T> T getFromJson(String json, Class<T> type) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, type);
    }
}
