package com.redbird.shopsservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbird.shopsservice.controller.GoodController;
import com.redbird.shopsservice.controller.ShopController;
import com.redbird.shopsservice.model.Category;
import com.redbird.shopsservice.model.GoodDTO;
import com.redbird.shopsservice.model.GoodFromFactory;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper om = new ObjectMapper();

    @Autowired
    private GoodController controller;
    @Autowired
    private ShopController shopController;

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
    public void postFirstGood() throws Exception {
        GoodFromFactory good = new GoodFromFactory();
        good.setName("Chair");
        good.setDescription("So comfortable to sit");
        good.setCost(4999.99);
        good.setAmount(3L);
        good.setShopName("Hoff");
        good.setCategory("furniture");
        String json = om.writeValueAsString(good);

        this.mockMvc.perform(post("/api/v1/goods")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Chair")))
                .andExpect(jsonPath("$.description", Matchers.is("So comfortable to sit")))
                .andExpect(jsonPath("$.cost", Matchers.is(4999.99)))
                .andExpect(jsonPath("$.amount", Matchers.is(3)))
                .andExpect(jsonPath("$.shop.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.category", Matchers.is("furniture")));
    }

    @Test
    @Order(2)
    public void getGoodByName() throws Exception {
        this.mockMvc.perform(get("/api/v1/goods/name/Chair")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", Matchers.is("Chair")))
                .andExpect(jsonPath("$.[0].description", Matchers.is("So comfortable to sit")))
                .andExpect(jsonPath("$.[0].cost", Matchers.is(4999.99)))
                .andExpect(jsonPath("$.[0].amount", Matchers.is(3)))
                .andExpect(jsonPath("$.[0].shop.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.[0].category", Matchers.is("furniture")));
    }

    @Test
    @Order(2)
    public void getGoodByShopName() throws Exception {
        this.mockMvc.perform(get("/api/v1/goods/shop/Hoff")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", Matchers.is("Chair")))
                .andExpect(jsonPath("$.[0].description", Matchers.is("So comfortable to sit")))
                .andExpect(jsonPath("$.[0].cost", Matchers.is(4999.99)))
                .andExpect(jsonPath("$.[0].amount", Matchers.is(3)))
                .andExpect(jsonPath("$.[0].shop.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.[0].category", Matchers.is("furniture")));
    }

    @Test
    @Order(2)
    public void postSecondGood() throws Exception {
        GoodFromFactory good = new GoodFromFactory();
        good.setName("Table");
        good.setDescription("So comfortable to flip");
        good.setCost(11999.99);
        good.setAmount(1L);
        good.setShopName("Hoff");
        good.setCategory("furniture");
        String json = om.writeValueAsString(good);

        this.mockMvc.perform(post("/api/v1/goods")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Table")))
                .andExpect(jsonPath("$.description", Matchers.is("So comfortable to flip")))
                .andExpect(jsonPath("$.cost", Matchers.is(11999.99)))
                .andExpect(jsonPath("$.amount", Matchers.is(1)))
                .andExpect(jsonPath("$.shop.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.category", Matchers.is("furniture")));
    }

    @Test
    @Order(3)
    public void getGoodById() throws Exception {
        this.mockMvc.perform(get("/api/v1/goods")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Chair")))
                .andExpect(jsonPath("$.description", Matchers.is("So comfortable to sit")))
                .andExpect(jsonPath("$.cost", Matchers.is(4999.99)))
                .andExpect(jsonPath("$.amount", Matchers.is(3L)))
                .andExpect(jsonPath("$.shop.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.category", Matchers.is("furniture")));
    }

    @Test
    @Order(3)
    public void postFirstGoodIdentical() throws Exception {
        GoodFromFactory good = new GoodFromFactory();
        good.setName("Chair");
        good.setDescription("So comfortable to sit");
        good.setCost(4999.99);
        good.setAmount(2L);
        good.setShopName("Hoff");
        good.setCategory("furniture");
        String json = om.writeValueAsString(good);

        this.mockMvc.perform(post("/api/v1/goods")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Chair")))
                .andExpect(jsonPath("$.description", Matchers.is("So comfortable to sit")))
                .andExpect(jsonPath("$.cost", Matchers.is(4999.99)))
                .andExpect(jsonPath("$.amount", Matchers.is(5)))
                .andExpect(jsonPath("$.shop.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.category", Matchers.is("furniture")));
    }

    @Test
    @Order(4)
    public void patchFirstGood() throws Exception {
        String json = "{\n" +
                "  \"description\": \"lgbtq backlight\"\n" +
                "}";

        this.mockMvc.perform(patch("/api/v1/goods/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Chair")))
                .andExpect(jsonPath("$.description", Matchers.is("lgbtq backlight")))
                .andExpect(jsonPath("$.cost", Matchers.is(4999.99)))
                .andExpect(jsonPath("$.amount", Matchers.is(5)))
                .andExpect(jsonPath("$.shop.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.category", Matchers.is("furniture")));
    }

    @Test
    @Order(5)
    public void deleteGood() throws Exception {
        this.mockMvc.perform(delete("/api/v1/goods/1"))
                .andExpect(status().isOk());
    }

}
