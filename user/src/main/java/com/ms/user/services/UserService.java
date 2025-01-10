package com.ms.user.services;

import com.ms.user.controllers.exception.EmailAlreadyExistsException;
import com.ms.user.controllers.exception.UsernameAlreadyExistsException;
import com.ms.user.models.UserModel;
import com.ms.user.producers.UserProducer;
import com.ms.user.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    final UserRepository userRepository;
    final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    public boolean isEmailExists(String email){
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public UserModel save(UserModel userModel){
        if (isEmailExists((userModel.getEmail()))){
            throw new EmailAlreadyExistsException("E-mail já cadastrado!");
        } else if (userRepository.existsByLogin(userModel.getUsername())) {
            throw new UsernameAlreadyExistsException("Username '" + userModel.getUsername() + "' already exists.");
            } else {
                userModel = userRepository.save(userModel);
                userProducer.publishMessageEmail(userModel);
                return userModel;
        }
    }
}