package com.glushkov.shop.web.controller;

import com.glushkov.shop.RootApplicationContext;
import com.glushkov.shop.dao.jdbc.TestConfiguration;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(value = {TestConfiguration.class, RootApplicationContext.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerTest {
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
    @DisplayName("Returns registration page")
    void getRegistrationPage() throws Exception {
        //when
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/registration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        //then
        assertEquals("text/html;charset=UTF-8", response.getContentType());
        assertTrue(response.getContentAsString().contains("Stylo-Registration"));
    }

    @Test
    @DisplayName("Returns login page")
    void getLoginPage() throws Exception {
        //when
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        //then
        assertEquals("text/html;charset=UTF-8", response.getContentType());
        assertTrue(response.getContentAsString().contains("Stylo-Login"));
    }

    @Test
    @DisplayName("Removes session from session list if exists")
    void logout() throws Exception {
        //prepare
        Session session = Session.builder()
                .build();
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/logout")
                .requestAttr("session", session))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
                .andReturn().getResponse();
    }

    @Test
    @DisplayName("Registration user")
    void registration() throws Exception {
        //when
        MockHttpServletResponse response = mockMvc.perform(post("/registration").param("login", "Alexander")
                .param("password", "11111111"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/home"))
                .andReturn().getResponse();

        assertNotNull(response.getCookie("user-token"));
    }

    @Test
    @DisplayName("Registration if login already exists user")
    void registrationIfLoginAlreadyExists() throws Exception {
        //when
        MockHttpServletResponse response = mockMvc.perform(post("/registration").param("login", "Alexander")
                .param("password", "11111111"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/registration?message=Sorry, login Alexander " +
                        "already exists. Please try again with another login."))
                .andReturn().getResponse();

        assertNull(response.getCookie("user-token"));
    }

    @Test
    @DisplayName("Login user")
    void login() throws Exception {
        //when
        MockHttpServletResponse response = mockMvc.perform(post("/login").param("login", "Alex")
                .param("password", "1111"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/home"))
                .andReturn().getResponse();

        assertNotNull(response.getCookie("user-token"));
    }

    @Test
    @DisplayName("Login user with wrong credentials")
    void loginWithWrongCredentials() throws Exception {
        //when
        MockHttpServletResponse response = mockMvc.perform(post("/login").param("login", "Alex")
                .param("password", "11111111"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?message=Access denied. Please login and try again."))
                .andReturn().getResponse();

        assertNull(response.getCookie("user-token"));
    }
}
