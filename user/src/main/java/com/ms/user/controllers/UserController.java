package com.ms.user.controllers;


import com.ms.user.dtos.EmailUpdateDto;
import com.ms.user.dtos.PasswordUpdateDto;
import com.ms.user.models.UserModel;
import com.ms.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/account/email")
    public ResponseEntity<UserModel> updateUser(@RequestBody @Valid EmailUpdateDto updateUserDto, Authentication authentication) {

        String authenticatedUserLogin = authentication.getName();
        if (!authenticatedUserLogin.equals(updateUserDto.login())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        UserModel user = userService.findByLogin(updateUserDto.login());
        user.setEmail(updateUserDto.email());
        System.out.println(updateUserDto.email());
        userService.updateEmail(user);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/account/password")
    public ResponseEntity<UserModel> updatePassword(@RequestBody @Valid PasswordUpdateDto passwordUpdateDto, Authentication authentication) {
        String authenticatedUserLogin = authentication.getName();
        if (!authenticatedUserLogin.equals(passwordUpdateDto.login())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        UserModel user = userService.findByLogin(passwordUpdateDto.login());
        user.setPassword(passwordUpdateDto.password());
        userService.updatePassword(user);
        return ResponseEntity.ok(user);
    }
}

