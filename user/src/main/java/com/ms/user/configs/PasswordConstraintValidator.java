package com.ms.user.configs;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword arg0) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),              // Comprimento entre 8 e 30 caracteres
                new CharacterRule(EnglishCharacterData.UpperCase, 1), // Pelo menos 1 maiúscula
                new CharacterRule(EnglishCharacterData.LowerCase, 1), // Pelo menos 1 minúscula
                new CharacterRule(EnglishCharacterData.Digit, 1),     // Pelo menos 1 dígito
                new CharacterRule(EnglishCharacterData.Special, 1),   // Pelo menos 1 caractere especial
                new WhitespaceRule()                // Sem espaços em branco
        ));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }

        // Personaliza a mensagem de erro
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                validator.getMessages(result).stream().collect(Collectors.joining(", "))
        ).addConstraintViolation();

        return false;
    }
}