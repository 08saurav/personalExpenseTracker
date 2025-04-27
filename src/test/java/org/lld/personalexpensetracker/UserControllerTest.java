package org.lld.personalexpensetracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.lld.personalexpensetracker.dto.RegisterUserDTO;
import org.lld.personalexpensetracker.dto.UserResponseDTO;
import org.lld.personalexpensetracker.model.User;
import org.lld.personalexpensetracker.service.UserService;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private org.lld.personalexpensetracker.controller.UserController userController;

    @Test
    void testRegisterUser() {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setName("John Doe");
        registerUserDTO.setEmail("john.doe@example.com");
        registerUserDTO.setPassword("password");

        User user = new User();
        user.setId(1L);
        user.setUsername("John Doe");
        user.setEmail("john.doe@example.com");

        when(userService.registerUser(any(User.class))).thenReturn(user);

        ResponseEntity<UserResponseDTO> response = userController.registerUser(registerUserDTO);

        assertEquals(1L, response.getBody().getId());
        assertEquals("John Doe", response.getBody().getName());
        assertEquals("john.doe@example.com", response.getBody().getEmail());
        verify(userService, times(1)).registerUser(any(User.class));
    }
}
