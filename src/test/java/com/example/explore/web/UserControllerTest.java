package com.example.explore.web;

import com.example.explore.model.entity.User;
import com.example.explore.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    void testOpenRegisterForm() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.
                        get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }



    @Test
    void testRegisterUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .param("username", "pesho@pesho.com")
                .param("fullName", "Pesho Petrov")
                .param("email", "pesho@pesho.com")
                .param("age", "25")
                .param("password", "12345")
                .param("confirmPassword", "12345")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, userRepository.count());

        Optional<User> newlyCreatedUserOpt = userRepository.findByEmail("pesho@pesho.com");

        Assertions.assertTrue(newlyCreatedUserOpt.isPresent());

        User newlyCreatedUser = newlyCreatedUserOpt.get();
        Assertions.assertEquals(25, newlyCreatedUser.getAge());
        Assertions.assertEquals("12345", newlyCreatedUser.getPassword());
        Assertions.assertEquals("Pesho Petrov", newlyCreatedUser.getFullName());
        Assertions.assertEquals("pesho@pesho.com", newlyCreatedUser.getUsername());

    }
}