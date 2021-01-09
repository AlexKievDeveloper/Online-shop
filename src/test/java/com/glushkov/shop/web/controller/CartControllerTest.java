package com.glushkov.shop.web.controller;

import com.glushkov.shop.RootApplicationContext;
import com.glushkov.shop.dao.jdbc.TestConfiguration;
import com.glushkov.shop.entity.Product;
import com.glushkov.shop.security.Session;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(value = {TestConfiguration.class, RootApplicationContext.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CartControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private Flyway flyway;

    @BeforeAll
    void dbSetUp() {
        flyway.clean();
        flyway.migrate();
    }

    @BeforeEach
    void setMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).alwaysDo(print()).build();
    }

    @Test
    @DisplayName("Returns cart page with carts")
    void getCartPage() throws Exception {
        //prepare
        Product product = Product.builder()
                .id(1)
                .description("Nice pen")
                .image("image")
                .name("test-pen")
                .price(1000.1)
                .build();

        List<Product> products = List.of(product);

        Session session = Session.builder()
                .cart(products)
                .build();
        //when
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/cart")
                .requestAttr("session", session))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertTrue(response.getContentAsString().contains("Stylo-Cart"));
    }

    @Test
    @DisplayName("Adding product to the cart if cart does not exists")
    void doPostCartDoesNotExists() throws Exception {
        //prepare
        Session session = Session.builder().build();

        //when
        mockMvc.perform(post("/cart").param("id", "1").requestAttr("session", session))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/home"))
                .andReturn().getResponse();
    }
}