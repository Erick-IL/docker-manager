# 🐳 Docker Container Manager - API Java

Este projeto é uma aplicação Java que permite criar e gerenciar containers Docker por meio de uma API REST. Ele utiliza a biblioteca [Docker Java](https://github.com/docker-java/docker-java) para se comunicar com o Docker Engine.

## Funcionalidades

-  Criar containers Docker com:
    - Nome personalizado
    - Porta do host e do container
-  Buscar logs do container em tempo real
-  Validações automáticas de porta e parâmetros
-  Interface web opcional com tema escuro e design inspirado no Docker

## 🛠️ Tecnologias

- Java 17+
- Spring Boot (se estiver usando)
- Docker Java (`com.github.docker-java:docker-java`)
- HTML + CSS ( tema escuro, responsivo)

## 📦 Instalação

### Pré-requisitos

- Java 17 ou superior
- Docker instalado e rodando no host
- (Opcional) AppArmor desabilitado ou permissivo, caso necessário para acesso a sockets

### Clonando o projeto

```bash
git clone https://github.com/Erick-IL/docker-manager.git
cd docker-container-manager
```

Compilando
```bash
./mvnw clean install
```

Executando
```bash
java -jar target/docker-container-manager.jar

Ou, se estiver usando Spring Boot:

./mvnw spring-boot:run
```

### 🎨 Interface Web
A interface frontend pode ser acessada via navegador em `localhost:8080/containers`


### ⚠️ Permissões

Em distribuições Linux com AppArmor ou SELinux, pode ser necessário ajustar as permissões para que o Docker Java acesse o socket do Docker. Use com responsabilidade.
🧑‍💻 Autor
Desenvolvido por **Erick IL**  
[LinkedIn](https://www.linkedin.com/in/erick-il) | [GitHub](https://github.com/Erick-IL)
