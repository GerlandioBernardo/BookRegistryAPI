# 📚 BookRegistryAPI

API REST para cadastro, consulta, atualização e remoção de livros, desenvolvida com Spring Boot, seguindo arquitetura em 4 camadas:

- Interface (API)

- Aplicação (Service)

- Domínio (Model + Regras de Negócio + Repositório)

- Infraestrutura (Banco, JPA, Implementações)

Inclui testes unitários com JUnit + Mockito, e banco H2 para testes.

## 🏗️ Tecnologias Utilizadas

- Java 17
- Spring Boot 3.5.7
- Spring Web
- Spring Data JPA
- H2 Database (banco em memória)
- JUnit 5
- Mockito
- Maven

## 🚀 Como Rodar o Projeto

✔️ Pré-requisitos

Antes de rodar, certifique-se de ter:

- Java 17+ instalado
- Maven 3.6+
- Git
- IDE recomendada: IntelliJ IDEA, Eclipse, VS Code

## ▶️ 4. Rodando a Aplicação
#### 🔧 Passo 1 — Clonar o repositório

```bash
git clone git@github.com:GerlandioBernardo/BookRegistryAPI.git
cd BookRegistryAPI
```

#### 🔧 Passo 2 — Instalar dependências

```bash
mvn clean install
```

#### 🔧 Passo 3 — Rodar a aplicação
##### Opção 1 — Via Maven
```bash
mvn spring-boot:run
```

##### Opção 2 — Via IDE
No Arquivo:
```bash
BookRegistryApiApplication.java
```
Clique no botão Run ▶️

##### A aplicação será iniciada na porta padrão 8080:
```bash
http://localhost:8080
```
## 🧪 5. Rodando os Testes
Rodar todos os testes:
```bash
mvn test
```

## 📖 6. Endpoints

| Método | Endpoint          | Descrição                     |
|--------|-------------------|-------------------------------|
| POST   | /api/book         | Cadastra um novo livro        |
| GET    | /api/book         | Lista todos os livros         |
| GET    | /api/book/{id}    | Busca um livro pelo ID        |
| PUT    | /api/book/{id}    | Atualiza os dados de um livro |
| DELETE | /api/book/{id}    | Remove um livro do sistema    |
| GET    | /api/book/isbn/{isbn} | Busca um livro pelo ISBN      |
