package com.ms.user.controllers;

import com.ms.user.controllers.exception.EmailAlreadyExistsException;
import com.ms.user.controllers.exception.UsernameAlreadyExistsException;
import com.ms.user.dtos.UserRecordDto;
import com.ms.user.models.UserModel;
import com.ms.user.models.enums.UserRole;
import com.ms.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/users")
//    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserRecordDto userRecordDto) {
//        try {
//            var userModel = new UserModel();
//            BeanUtils.copyProperties(userRecordDto, userModel);
//            userModel.setRole(UserRole.valueOf("USER"));
//            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
//        } catch (EmailAlreadyExistsException | UsernameAlreadyExistsException ex) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .header("Error-Message", ex.getMessage())
//                    .build();
//        }
//    }

}