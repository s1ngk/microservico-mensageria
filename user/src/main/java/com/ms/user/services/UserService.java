package com.ms.user.services;

import com.ms.user.controllers.exception.EmailAlreadyExistsException;
import com.ms.user.controllers.exception.EmailFormatInvalidException;
import com.ms.user.controllers.exception.PasswordMatchesException;
import com.ms.user.controllers.exception.UsernameAlreadyExistsException;
import com.ms.user.models.UserModel;
import com.ms.user.producers.UserProducer;
import com.ms.user.repositories.UserRepository;
import org.apache.catalina.User;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    final UserRepository userRepository;
    final UserProducer userProducer;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    public boolean isEmailExists(String email){

        return userRepository.existsByEmail(email);
    }
    public boolean isEmailValid(String email){

        return EmailValidator.getInstance().isValid(email);
    }
    public UserModel findByLogin(String login){
        return (UserModel) userRepository.findByLogin(login);
    }

    @Transactional
    public UserModel save(UserModel userModel){
        if (isEmailExists((userModel.getEmail()))){
            throw new EmailAlreadyExistsException("E-mail já cadastrado!");
        } else if (userRepository.existsByLogin(userModel.getUsername())) {
            throw new UsernameAlreadyExistsException("Usuário '" + userModel.getUsername() + "' já existente.");
            } else if (isEmailValid(userModel.getEmail())) {
                userModel = userRepository.save(userModel);
                userProducer.publishMessageNewEmail(userModel);
                return userModel;
            } else {
            throw new EmailFormatInvalidException("Formato do e-mail inválido. Exemplo correto: usuario@dominio.com");
        }
    }

    @Transactional
    public UserModel updateEmail(UserModel userModel) {
        if (isEmailValid(userModel.getEmail())) {
            // Buscar o usuário atual no banco pelo ID para obter o email original
            UserModel existingUser = userRepository.findById(userModel.getUserID())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String newEmail = userModel.getEmail();
            String currentEmail = existingUser.getEmail();

            // Verificar se o novo email já existe em outro usuário
            if (!newEmail.equals(currentEmail) && isEmailExists(newEmail)) {
                throw new EmailAlreadyExistsException("E-mail já cadastrado!");
            } else {
                userModel = userRepository.save(userModel);
                userProducer.publishMessageEmailUpdate(userModel);
                return userModel;
            }
        } else {
            throw new EmailFormatInvalidException("Formato do e-mail inválido. Exemplo correto: usuario@dominio.com");
        }
    }

    @Transactional
    public UserModel updatePassword(UserModel userModel){
        String novaSenha = passwordEncoder.encode(userModel.getPassword());
        if (!userRepository.findByLogin(userModel.getLogin()).getPassword().equals(novaSenha)) {
            userModel.setPassword(novaSenha);
            userModel = userRepository.save(userModel);
            userProducer.publishMessagePasswordUpdate(userModel);
            return userModel;
        } else {
            throw new PasswordMatchesException("A senha inserida é a mesma senha atual.");
        }
    }
}