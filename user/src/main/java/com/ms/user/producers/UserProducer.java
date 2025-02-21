package com.ms.user.producers;

import com.ms.user.dtos.EmailDto;
import com.ms.user.models.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageNewEmail(UserModel userModel) {
        var emailDto = new EmailDto();
        emailDto.setUserId(userModel.getUserID());
        emailDto.setEmailTo(userModel.getEmail());
        emailDto.setSubject("Cadastro realizado com sucesso!");
        emailDto.setText(userModel.getName() + ", seja bem vindo(a)! \nAgradecemos o seu cadastro, aproveite agora todos os recursos da nossa plataforma.");

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }
    public void publishMessageEmailUpdate(UserModel userModel) {
        var emailDto = new EmailDto();
        emailDto.setUserId(userModel.getUserID());
        emailDto.setEmailTo(userModel.getEmail());
        emailDto.setSubject("Mudança de endereço de E-mail realizada com sucesso!");
        emailDto.setText(userModel.getName() + ", seu endereço de e-mail foi atualizado para " + userModel.getEmail() + " com sucesso!");

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }
    public void publishMessagePasswordUpdate(UserModel userModel) {
        var emailDto = new EmailDto();
        emailDto.setUserId(userModel.getUserID());
        emailDto.setEmailTo(userModel.getEmail());
        emailDto.setSubject("Mudança de senha realizada com sucesso!");
        emailDto.setText(userModel.getName() + ", sua senha foi atualizada  com sucesso!");

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }
}
