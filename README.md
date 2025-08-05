# dio-spring-security-jwt

Este projeto demonstra a implementação de autenticação e autorização utilizando Spring Security com JWT (JSON Web Token) em uma API REST. O objetivo é apresentar um exemplo prático de como proteger rotas e gerenciar usuários em uma aplicação Java utilizando banco de dados H2.

## Proposta

A proposta do projeto é servir como referência para quem deseja aprender ou implementar autenticação baseada em JWT no Spring Boot, incluindo cadastro de usuários, login e proteção de rotas por perfil de acesso.

## Funcionalidades

- Cadastro de usuários no banco H2
- Login e geração de token JWT
- Proteção de rotas por perfil (usuário, gerente)

## Como executar

1. Clone o repositório:
   ```bash
   git clone <url-do-repositorio>
   ```
2. Acesse a pasta do projeto:
   ```bash
   cd dio-spring-security-jwt
   ```
3. Execute o projeto:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Acesse o banco H2 em [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

## Endpoints principais

- `POST /login` - Realiza o login e retorna o token JWT
- `POST /users` - Cadastra um novo usuário
- `GET /users` - Lista usuários (rota protegida)
- `GET /managers` - Lista gerentes (rota protegida)

## Como consumir a API

### 1. Cadastro de usuário

Envie uma requisição POST para `/users` com o corpo JSON contendo os dados do usuário. Certifique-se de incluir o prefixo `ROLE_` no campo de perfil (por exemplo, `ROLE_USERS` ou `ROLE_MANAGERS`). Exemplo:

```json
{
  "name": "Nome",
  "username": "usuario",
  "password": "senha",
  "role": "ROLE_USERS"
}
```

### 2. Login

Envie uma requisição POST para `/login` com o corpo JSON contendo o usuário e senha cadastrados:

```json
{
  "username": "usuario",
  "password": "senha"
}
```

A resposta será um token JWT. Exemplo:

```json
{
  "token": "seu_token_jwt_aqui"
}
```

### 3. Acesso às rotas protegidas

Para acessar rotas protegidas como `/users` ou `/managers`, envie o token JWT no header `Authorization`:

```
Authorization: Bearer seu_token_jwt_aqui
```

Exemplo usando cURL:

```bash
curl -H "Authorization: Bearer seu_token_jwt_aqui" http://localhost:8080/users
```

---

Fique à vontade para contribuir ou relatar problemas.

## Observações

- O banco de dados utilizado é o H2, em memória.
- As rotas protegidas exigem o envio do token JWT no header `Authorization`.
- O projeto está em desenvolvimento e sujeito a melhorias.
