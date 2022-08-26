package com.example.explore.service.impl;

import com.example.explore.model.entity.Role;
import com.example.explore.model.entity.User;
import com.example.explore.model.entity.enums.RoleNameEnum;
import com.example.explore.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

    private UserDetailsService serviceToTest;
    private User testUser;

    private Role adminRole, userRole;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void init(){

        serviceToTest = new UserDetailsService(mockUserRepository);

        adminRole = new Role();
        adminRole.setRole(RoleNameEnum.ADMIN);
        userRole = new Role();
        userRole.setRole(RoleNameEnum.USER);

        testUser = new User();
        testUser
                .setUsername("ivan")
                .setEmail("ivan@ivan.com")
                .setPassword("12345")
                .setRoles(Set.of(adminRole, userRole));

    }

    @Test
    void testUserNotFound(){
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("invalid_user_email@not-exist.com")
        );
    }

    @Test
    void testUserFound(){

        //Arrange
        Mockito.when(mockUserRepository.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.of(testUser));

        //Act
        var actual = serviceToTest.loadUserByUsername(testUser.getEmail());

        //Assert
        String expectedRoles = "ROLE_ADMIN, ROLE_USER";

        String actualRoles = actual.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        Assertions.assertEquals(actual.getUsername(), testUser.getEmail());
        Assertions.assertEquals(expectedRoles, actualRoles);
    }
}
