package org.lld.personalexpensetracker.controller;

import org.lld.personalexpensetracker.dto.RegisterUserDTO;
import org.lld.personalexpensetracker.dto.UserResponseDTO;
import org.lld.personalexpensetracker.model.User;
import org.lld.personalexpensetracker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        User user = new User();
        user.setUsername(registerUserDTO.getName());
        user.setEmail(registerUserDTO.getEmail());
        user.setPassword(registerUserDTO.getPassword());
        User savedUser = userService.registerUser(user);

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(savedUser.getId());
        responseDTO.setName(savedUser.getUsername());
        responseDTO.setEmail(savedUser.getEmail());

        return ResponseEntity.ok(responseDTO);
    }
}