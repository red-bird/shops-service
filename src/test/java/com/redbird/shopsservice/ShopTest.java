package com.redbird.shopsservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbird.shopsservice.controller.ShopController;
import com.redbird.shopsservice.model.Category;
import com.redbird.shopsservice.model.ShopDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShopTest {
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper om = new ObjectMapper();

    @Autowired
    private ShopController controller;

    @Test
    @Order(1)
    public void postShop() throws Exception {

        ShopDTO s = new ShopDTO();
        s.setName("Hoff");
        s.setAddress("Moscow");
        s.setNumber("+7 999 999 99 99");
        s.setCategory(Category.furniture);
        String json = om.writeValueAsString(s);

        this.mockMvc.perform(post("/api/v1/shops")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.address", Matchers.is("Moscow")))
                .andExpect(jsonPath("$.number", Matchers.is("+7 999 999 99 99")))
                .andExpect(jsonPath("$.category", Matchers.is("furniture")));
    }

    @Test
    @Order(2)
    public void postShop2() throws Exception {

        ShopDTO s = new ShopDTO();
        s.setName("Hoff");
        s.setAddress("Germany");
        s.setNumber("+7 999 999 99 99");
        s.setCategory(Category.furniture);
        String json = om.writeValueAsString(s);

        this.mockMvc.perform(post("/api/v1/shops")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    @Order(2)
    public void getShopById() throws Exception {
        this.mockMvc.perform(get("/api/v1/shops/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.address", Matchers.is("Moscow")))
                .andExpect(jsonPath("$.number", Matchers.is("+7 999 999 99 99")))
                .andExpect(jsonPath("$.category", Matchers.is("furniture")));
    }

    @Test
    @Order(2)
    public void getShopByName() throws Exception {
        this.mockMvc.perform(get("/api/v1/shops/name/Hoff")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.address", Matchers.is("Moscow")))
                .andExpect(jsonPath("$.number", Matchers.is("+7 999 999 99 99")))
                .andExpect(jsonPath("$.category", Matchers.is("furniture")));
    }

    @Test
    @Order(2)
    public void getShopsByCategory() throws Exception {
        this.mockMvc.perform(get("/api/v1/shops/category/furniture")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.[0].address", Matchers.is("Moscow")))
                .andExpect(jsonPath("$.[0].number", Matchers.is("+7 999 999 99 99")))
                .andExpect(jsonPath("$.[0].category", Matchers.is("furniture")));
    }

    @Test
    @Order(3)
    public void patchShop() throws Exception {
        String json = "{\n" +
                "  \"address\": \"London\"\n" +
                "}";
        this.mockMvc.perform(patch("/api/v1/shops/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.address", Matchers.is("London")))
                .andExpect(jsonPath("$.number", Matchers.is("+7 999 999 99 99")))
                .andExpect(jsonPath("$.category", Matchers.is("furniture")));
    }

    @Test
    @Order(4)
    public void deleteShop() throws Exception {
        this.mockMvc.perform(delete("/api/v1/shops/1"))
                .andExpect(status().isOk());
    }


}
