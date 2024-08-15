package com.gyt.managementservice.business.concretes;


import com.gyt.corepackage.business.abstracts.MessageService;
import com.gyt.corepackage.utils.exceptions.types.BusinessException;
import com.gyt.managementservice.business.messages.Messages;
import com.gyt.managementservice.business.rules.UserBusinessRules;
import com.gyt.managementservice.model.entities.User;
import com.gyt.managementservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserManagerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserBusinessRules userBusinessRules;


    @Mock
    private MessageService messageService;

    @InjectMocks
    private UserManager userManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_userFound() {
        // Arrange
        String username = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(username);

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(mockUser));

        // Act
        UserDetails result = userManager.loadUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        verify(userRepository, times(1)).findByEmail(username);
    }

    @Test
    void loadUserByUsername_userNotFound() {
        // Arrange
        String username = "notfound@example.com";
        String errorMessage = "User should exist";

        when(userRepository.findByEmail(username)).thenReturn(Optional.empty());
        when(messageService.getMessage(Messages.UserErrors.UserShouldBeExists)).thenReturn(errorMessage);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> userManager.loadUserByUsername(username));

        assertEquals(errorMessage, exception.getMessage());
        verify(userRepository, times(1)).findByEmail(username);
        verify(messageService, times(1)).getMessage(Messages.UserErrors.UserShouldBeExists);
    }
}