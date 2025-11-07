package com.faust0z.BookLibraryAPI.controller;

import com.faust0z.BookLibraryAPI.dto.UpdateUserDTO;
import com.faust0z.BookLibraryAPI.dto.UserDTO;
import com.faust0z.BookLibraryAPI.entity.UserEntity;
import com.faust0z.BookLibraryAPI.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMyProfile(@AuthenticationPrincipal UserEntity currentUser) {
        return ResponseEntity.ok(userService.convertToDto(currentUser));
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDTO> updateMyProfile(@AuthenticationPrincipal UserEntity currentUser,
                                                   @RequestBody UpdateUserDTO userData) {
        UserDTO updatedUser = userService.updateUser(currentUser.getId(), userData);
        return ResponseEntity.ok(updatedUser);
    }
}

