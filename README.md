# Estudo de Microserviços e Mensageria
Este repositório consiste no desenvolvimento de uma aplicação utilizando a linguagem Java em uma arquitetura de Microserviços com objetivo de aprendizado e prática sobre Microserviços, Mensageria e +++.
>A aplicação possui os serviços E-mail e User. Através do serviço User o usuário pode se cadastrar, realizar o login, alterar sua senha e seu e-mail, recebendo uma confirmação em seu e-mail cadastrado a cada etapa através do Serviço de E-mails.

### Especificações Técnicas ✔️
- Java (Versão Mínima): 17
- Build: Maven
- Arquitetura: Microserviços
- Message Broker: RabbitMQ através de CloudAMQP
- Conteinerização: Docker
### Bibiliotecas utilizadas ✔️
- Spring Framework
- Spring Security
- Jackson
- Jakarta
- e outras.
### Melhorias futuras ✔️
- Isolar User Service e Authentication implementando um Authentication Service
- Implementar Notification Service para expandir os métodos de comunicação se integrando a APIs externas(SMS etc)
- Implementar um Audit Service para manter log de todas as ações no sistema
- Possívelmente implementar outros serviços como Profile Service
- Testes unitários
