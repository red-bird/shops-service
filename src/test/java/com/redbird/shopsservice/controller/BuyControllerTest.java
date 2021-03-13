package com.redbird.shopsservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbird.shopsservice.model.BuyGoodDTO;
import com.redbird.shopsservice.model.Category;
import com.redbird.shopsservice.model.GoodDTO;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BuyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper om = new ObjectMapper();

    @Autowired
    private BuyController buyController;
    @Autowired
    private GoodController goodController;
    @Autowired
    private ShopController shopController;

    @Test
    @Order(1)
    public void testDataInitialize() throws Exception {

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

        ///
        GoodDTO good = new GoodDTO();
        good.setName("Chair");
        good.setDescription("So comfortable to sit");
        good.setCost(4999.99);
        good.setAmount(10L);
        good.setShopName("Hoff");
        good.setCategory("furniture");
        json = om.writeValueAsString(good);

        this.mockMvc.perform(post("/api/v1/goods")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Chair")))
                .andExpect(jsonPath("$.description", Matchers.is("So comfortable to sit")))
                .andExpect(jsonPath("$.cost", Matchers.is(4999.99)))
                .andExpect(jsonPath("$.amount", Matchers.is(10)))
                .andExpect(jsonPath("$.shop.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.category", Matchers.is("furniture")));

        good = new GoodDTO();
        good.setName("Table");
        good.setDescription("So comfortable to flip");
        good.setCost(11999.99);
        good.setAmount(6L);
        good.setShopName("Hoff");
        good.setCategory("furniture");
        json = om.writeValueAsString(good);

        this.mockMvc.perform(post("/api/v1/goods")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Table")))
                .andExpect(jsonPath("$.description", Matchers.is("So comfortable to flip")))
                .andExpect(jsonPath("$.cost", Matchers.is(11999.99)))
                .andExpect(jsonPath("$.amount", Matchers.is(6)))
                .andExpect(jsonPath("$.shop.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.category", Matchers.is("furniture")));
    }

    @Test
    @Order(2)
    public void buyGoodsFail() throws Exception {
        List<BuyGoodDTO> buyGoodDTOS = new ArrayList<>();

        BuyGoodDTO buyGoodDTO = new BuyGoodDTO();
        buyGoodDTO.setCustomerId(2L);
        buyGoodDTO.getGoodDTO().setName("Chair");
        buyGoodDTO.getGoodDTO().setDescription("So comfortable to sit");
        buyGoodDTO.getGoodDTO().setCost(4999.99);
        buyGoodDTO.getGoodDTO().setAmount(20L);
        buyGoodDTO.getGoodDTO().setShopName("Hoff");
        buyGoodDTO.getGoodDTO().setCategory("furniture");

        buyGoodDTOS.add(buyGoodDTO);

        buyGoodDTO = new BuyGoodDTO();
        buyGoodDTO.setCustomerId(2L);
        buyGoodDTO.getGoodDTO().setName("Table");
        buyGoodDTO.getGoodDTO().setDescription("So comfortable to flip");
        buyGoodDTO.getGoodDTO().setCost(11999.99);
        buyGoodDTO.getGoodDTO().setAmount(30L);
        buyGoodDTO.getGoodDTO().setShopName("Hoff");
        buyGoodDTO.getGoodDTO().setCategory("furniture");

        buyGoodDTOS.add(buyGoodDTO);

        String json = om.writeValueAsString(buyGoodDTOS);

        this.mockMvc.perform(post("/api/v1/buy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    @Order(3)
    public void buyGoodsOk() throws Exception {
        List<BuyGoodDTO> buyGoodDTOS = new ArrayList<>();

        BuyGoodDTO buyGoodDTO = new BuyGoodDTO();
        buyGoodDTO.setCustomerId(2L);
        buyGoodDTO.getGoodDTO().setName("Chair");
        buyGoodDTO.getGoodDTO().setDescription("So comfortable to sit");
        buyGoodDTO.getGoodDTO().setCost(4999.99);
        buyGoodDTO.getGoodDTO().setAmount(4L);
        buyGoodDTO.getGoodDTO().setShopName("Hoff");
        buyGoodDTO.getGoodDTO().setCategory("furniture");

        buyGoodDTOS.add(buyGoodDTO);

        buyGoodDTO = new BuyGoodDTO();
        buyGoodDTO.setCustomerId(2L);
        buyGoodDTO.getGoodDTO().setName("Table");
        buyGoodDTO.getGoodDTO().setDescription("So comfortable to flip");
        buyGoodDTO.getGoodDTO().setCost(11999.99);
        buyGoodDTO.getGoodDTO().setAmount(2L);
        buyGoodDTO.getGoodDTO().setShopName("Hoff");
        buyGoodDTO.getGoodDTO().setCategory("furniture");

        buyGoodDTOS.add(buyGoodDTO);

        String json = om.writeValueAsString(buyGoodDTOS);

        this.mockMvc.perform(post("/api/v1/buy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    @Order(4)
    void findAll() throws Exception {
        this.mockMvc.perform(get("/api/v1/buy")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.[0].name", Matchers.is("Chair")))
                .andExpect(jsonPath("$.[0].description", Matchers.is("So comfortable to sit")))
                .andExpect(jsonPath("$.[0].cost", Matchers.is(4999.99)))
                .andExpect(jsonPath("$.[0].amount", Matchers.is(4)))
                .andExpect(jsonPath("$.[0].shop.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.[0].category", Matchers.is("furniture")))
                .andExpect(jsonPath("$.[0].customerId", Matchers.is(2)))

                .andExpect(jsonPath("$.[1].name", Matchers.is("Table")))
                .andExpect(jsonPath("$.[1].description", Matchers.is("So comfortable to flip")))
                .andExpect(jsonPath("$.[1].cost", Matchers.is(11999.99)))
                .andExpect(jsonPath("$.[1].amount", Matchers.is(2)))
                .andExpect(jsonPath("$.[1].shop.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.[1].category", Matchers.is("furniture")))
                .andExpect(jsonPath("$.[1].customerId", Matchers.is(2)))
        ;
    }

    @Test
    @Order(4)
    void findById() throws Exception {
        this.mockMvc.perform(get("/api/v1/buy/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.name", Matchers.is("Chair")))
                .andExpect(jsonPath("$.description", Matchers.is("So comfortable to sit")))
                .andExpect(jsonPath("$.cost", Matchers.is(4999.99)))
                .andExpect(jsonPath("$.amount", Matchers.is(4)))
                .andExpect(jsonPath("$.shop.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.category", Matchers.is("furniture")))
                .andExpect(jsonPath("$.customerId", Matchers.is(2)))
        ;
    }

    @Test
    @Order(4)
    void findByGoodName() throws Exception {
        this.mockMvc.perform(get("/api/v1/buy/name/Chair")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.[0].name", Matchers.is("Chair")))
                .andExpect(jsonPath("$.[0].description", Matchers.is("So comfortable to sit")))
                .andExpect(jsonPath("$.[0].cost", Matchers.is(4999.99)))
                .andExpect(jsonPath("$.[0].amount", Matchers.is(4)))
                .andExpect(jsonPath("$.[0].shop.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.[0].category", Matchers.is("furniture")))
                .andExpect(jsonPath("$.[0].customerId", Matchers.is(2)))
                ;

    }

    @Test
    @Order(4)
    void findByShopName() throws Exception {
        this.mockMvc.perform(get("/api/v1/buy/shop/Hoff")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.[0].name", Matchers.is("Chair")))
                .andExpect(jsonPath("$.[0].description", Matchers.is("So comfortable to sit")))
                .andExpect(jsonPath("$.[0].cost", Matchers.is(4999.99)))
                .andExpect(jsonPath("$.[0].amount", Matchers.is(4)))
                .andExpect(jsonPath("$.[0].shop.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.[0].category", Matchers.is("furniture")))
                .andExpect(jsonPath("$.[0].customerId", Matchers.is(2)))
        ;
    }

    @Test
    @Order(4)
    void findByCustomerId() throws Exception {
        this.mockMvc.perform(get("/api/v1/buy/customer/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.[0].name", Matchers.is("Chair")))
                .andExpect(jsonPath("$.[0].description", Matchers.is("So comfortable to sit")))
                .andExpect(jsonPath("$.[0].cost", Matchers.is(4999.99)))
                .andExpect(jsonPath("$.[0].amount", Matchers.is(4)))
                .andExpect(jsonPath("$.[0].shop.name", Matchers.is("Hoff")))
                .andExpect(jsonPath("$.[0].category", Matchers.is("furniture")))
                .andExpect(jsonPath("$.[0].customerId", Matchers.is(2)))
        ;
    }
}