package com.glushkov.shop.web.controller;

import com.glushkov.shop.RootApplicationContext;
import com.glushkov.shop.dao.jdbc.TestConfiguration;
import com.glushkov.shop.entity.Product;
import com.glushkov.shop.entity.Role;
import com.glushkov.shop.entity.User;
import com.glushkov.shop.security.Session;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(value = {TestConfiguration.class, RootApplicationContext.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private Flyway flyway;

    @Mock
    private Model model;

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
    @DisplayName("Returns home page")
    void getHomePage() throws Exception {
        //when
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        //then
        assertEquals("text/html;charset=UTF-8", response.getContentType());
        assertTrue(response.getContentAsString().contains("Stylo-Home"));

    }

    @Test
    @DisplayName("Returns add page")
    void getAddPage() throws Exception {
        //when
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        //then
        assertEquals("text/html;charset=UTF-8", response.getContentType());
        assertTrue(response.getContentAsString().contains("Stylo-Add"));
    }

    @Test
    @DisplayName("Returns edit page")
    void getEditPage() throws Exception {
        //when
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/edit/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        //then
        assertEquals("text/html;charset=UTF-8", response.getContentType());
        assertTrue(response.getContentAsString().contains("Stylo-Edit"));
    }

    @Test
    @DisplayName("Returns product view page")
    void getProductPage() throws Exception {
        //prepare
        User user = User.builder()
                .role(Role.USER)
                .build();

        Session session = Session.builder()
                .user(user)
                .build();
        //when
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/view/1")
                .requestAttr("session", session))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        //then
        assertEquals("text/html;charset=UTF-8", response.getContentType());
        assertTrue(response.getContentAsString().contains("Stylo-Product"));
    }

    @Test
    @DisplayName("Returns product view page")
    void getProductPageIfRoleIsNotPresent() throws Exception {
        //prepare
        User user = User.builder().build();

        Session session = Session.builder()
                .user(user)
                .build();
        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/view/1")
                .requestAttr("session", session))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?message=Access denied. Please login and try again."))
                .andReturn().getResponse();
    }

    @Test
    @DisplayName("Returns page with products")
    void search() throws Exception {
        //when
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/search?enteredName=v"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        //then
        assertEquals("text/html;charset=UTF-8", response.getContentType());
        assertTrue(response.getContentAsString().contains("Stylo-Search"));
    }

    @Test
    @DisplayName("Adding new product")
    void add() throws Exception {
        //when + then
        mockMvc.perform(post("/add")
                .param("name", "Test pen")
                .param("image", "image")
                .param("price", "2002.20"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/home"))
                .andReturn().getResponse();
    }

    @Test
    @DisplayName("Editing existing product")
    void edit() throws Exception {
        //when + then
        mockMvc.perform(post("/edit")
                .param("id", "1")
                .param("name", "Test pen")
                .param("image", "image")
                .param("price", "2002.20"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/home"))
                .andReturn().getResponse();
    }

    @Test
    @DisplayName("Deleting product")
    void delete() throws Exception {
        //when + then
        mockMvc.perform(post("/delete")
                .param("id", "2"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/home"))
                .andReturn().getResponse();
    }

    @Test
    @DisplayName("Put products fields into model")
    void putProductFieldsIntoModel() {
        //prepare
        Product product = Product.builder()
                .id(10)
                .name("Test product")
                .image("image")
                .price(1000)
                .build();
        //when
        ProductController productController = (ProductController) context.getBean("productController");
        productController.putProductFieldsIntoModel(product, model);

        //then
        verify(model).addAttribute("id", 10);
        verify(model).addAttribute("name", "Test product");
        verify(model).addAttribute("image", "image");
        verify(model).addAttribute("price", 1000.0d);
    }
}