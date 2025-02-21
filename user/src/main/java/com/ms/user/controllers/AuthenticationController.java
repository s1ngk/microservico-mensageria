package com.ms.user.controllers;

import com.ms.user.configs.TokenService;
import com.ms.user.controllers.exception.EmailAlreadyExistsException;
import com.ms.user.controllers.exception.UsernameAlreadyExistsException;
import com.ms.user.dtos.AuthenticationDto;
import com.ms.user.dtos.LoginResponseDto;
import com.ms.user.dtos.UserRecordDto;
import com.ms.user.models.UserModel;
import com.ms.user.services.UserService;
import com.ms.user.models.enums.UserRole;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto authenticationDto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDto.login(), authenticationDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserModel)auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserRecordDto userRecordDto) {
        try {
            String encryptedPassword = passwordEncoder.encode(userRecordDto.password());
            UserModel userModel = new UserModel(userRecordDto.login(),
                    encryptedPassword,
                    userRecordDto.name(),
                    userRecordDto.email());
            userModel.setRole(UserRole.USER);
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
        } catch (EmailAlreadyExistsException | UsernameAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Error-Message", ex.getMessage())
                    .build();
        }
    }
}
