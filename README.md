# Empresa Aérea

Este diretório contém a aplicação frontend e os serviços backend do sistema Empresa Aérea, desenvolvido para gerenciar reservas, voos, clientes, funcionários e autenticação.

## Estrutura do Projeto

O projeto é dividido em dois grandes módulos: `backend` e `frontend`.

### Backend

O backend é composto por múltiplos microsserviços independentes, desenvolvidos com Spring Boot, Docker e RabbitMQ. Cada serviço é responsável por uma parte específica do sistema.

# Estrutura de Pastas

O projeto está organizado em dois grandes domínios: o **Backend** e o **Frontend**, cada um com sua própria estrutura, responsabilidades e tecnologias.

## 1. Backend

O backend adota uma arquitetura de **microsserviços**, desenvolvidos utilizando **Spring Boot**. A orquestração e gerenciamento dos serviços são feitos com **Docker**, e a comunicação assíncrona entre eles é facilitada pelo **RabbitMQ**.

-   **`backend/`**: Diretório raiz que agrupa todos os microsserviços do sistema.
    -   Cada subdiretório dentro de `backend/` representa um **microsserviço independente** (ex: um serviço de clientes, um serviço de reservas, um serviço de autenticação, um API Gateway, etc.).
    -   **Dentro de cada microsserviço**: Encontra-se o código-fonte principal, configurações específicas e definições para conteinerização (Dockerfile).

## 2. Frontend

O frontend é uma aplicação **React** desenvolvida para interagir com os serviços do backend. Ele segue o padrão de arquitetura **MVVM (Model-View-ViewModel)** para organizar sua lógica e interface.

-   **`frontend/`**: Diretório raiz da aplicação React.
    -   **`src/`**: Contém o código-fonte da aplicação frontend.
        -   **`modules/`**: Esta é a área central da organização MVVM, onde a aplicação é dividida em **módulos funcionais** (ex: módulo de autenticação, módulo de cliente, módulo de funcionário).
            -   **Dentro de cada módulo funcional**: Você encontrará as **Views** (componentes React que representam a interface), os **ViewModels** (a lógica de apresentação e manipulação de dados para as Views), **Modelos** (definições de dados), e **Serviços** (lógica para comunicação com o backend).
        -   **`shared/`**: Contém elementos reutilizáveis que podem ser compartilhados entre diferentes módulos (ex: componentes de UI genéricos, utilitários, hooks, estilos globais).

Essa organização promove uma clara separação de responsabilidades, facilitando o desenvolvimento, a manutenção e a escalabilidade de ambas as partes do sistema.

#### Serviços (Backend)

1.  **API Gateway (`apigateway`)**
    Atua como um ponto de entrada unificado para todas as requisições do frontend, roteando-as para os microsserviços apropriados.

2.  **Cliente Service (`mscliente`)**
    Gerencia informações de clientes, incluindo cadastro, atualização e gerenciamento de milhas.

3.  **Funcionário Service (`msfuncionario`)**
    Gerencia informações de funcionários, incluindo cadastro e autenticação.

4.  **Reserva Service (`msreserva`)**
    Responsável por gerenciar reservas de voos, incluindo criação, cancelamento e consulta de reservas.

5.  **Saga Orchestration Service (`mssaga`)**
    Orquestra processos distribuídos, como criação e cancelamento de reservas, utilizando RabbitMQ para comunicação assíncrona entre os serviços.

6.  **Voo Service (`msvoos`)**
    Gerencia informações relacionadas a voos, como criação, cancelamento e consulta de voos.

7.  **RabbitMQ (`rabbitmq`)**
    Componente para a comunicação assíncrona entre os microsserviços, fundamental para a orquestração de sagas.

8.  **Auth Service (`wsauth`)**
    Responsável por autenticar usuários (clientes e funcionários) e gerenciar tokens JWT.

### Frontend

O frontend é uma aplicação React que consome os serviços do backend através do API Gateway.

## Como Trabalhar com o Projeto

### Pré-requisitos

-   **Java 17**: Certifique-se de ter o JDK 17 instalado para o backend.
-   **Node.js e npm (ou yarn)**: Para o desenvolvimento do frontend.
-   **Docker e Docker Compose**: Essenciais para rodar os serviços backend e suas dependências (RabbitMQ e PostgreSQL).
-   **Maven**: Para gerenciar dependências e compilar os projetos backend.

### Configuração

1.  **Configuração de Variáveis de Ambiente (Backend)**
    Certifique-se de configurar as variáveis de ambiente necessárias para os serviços backend, como `GATEWAY_URL` e `EMAIL_APP_PASSWORD`. Consulte os arquivos `application.yaml` de cada serviço para detalhes.

2.  **Banco de Dados (Backend)**
    Os serviços backend utilizam PostgreSQL como banco de dados. Certifique-se de que o banco esteja configurado corretamente e que os esquemas correspondam aos definidos nos arquivos `application.yaml` de cada serviço.

3.  **RabbitMQ (Backend)**
    RabbitMQ é usado para comunicação assíncrona entre os serviços. Certifique-se de que ele esteja rodando e configurado conforme os arquivos `RabbitMQConfig.kt` em cada serviço que o utiliza.

### Passos para Rodar os Serviços

#### Backend

1.  **Compilar os Projetos Backend**
    Navegue até o diretório de cada serviço (ex: `backend/mscliente`) e execute:
    ```bash
    mvn clean install
    ```
    Repita este passo para todos os microsserviços.

2.  **Rodar o Backend com Docker Compose**
    No diretório raiz do projeto (onde estão as pastas `backend` e `frontend`), execute:
    ```bash
    docker compose up --build
    ```
    Isso irá iniciar todos os serviços backend, incluindo RabbitMQ e PostgreSQL.

3.  **Acessar os Serviços Backend**
    Após a inicialização, os serviços estarão disponíveis nas seguintes portas (exemplo, portas podem variar dependendo da sua configuração):
    -   API Gateway: `http://localhost:3000` (porta comum para gateways)
    -   Cliente Service: `http://localhost:8081`
    -   Funcionário Service: `http://localhost:9090`
    -   Reserva Service: `http://localhost:8084`
    -   Voo Service: `http://localhost:9091`
    -   Auth Service: `http://localhost:27017`

#### Frontend

1.  **Instalar Dependências do Frontend**
    Navegue até o diretório `frontend` e execute:
    ```bash
    npm install
    # ou
    yarn install
    ```

2.  **Rodar o Frontend**
    No diretório `frontend`, execute:
    ```bash
    npm start
    # ou
    yarn start
    ```
    A aplicação frontend estará disponível em `http://localhost:3000` (porta padrão do React).

### Testando os Serviços

-   Utilize ferramentas como **Postman** ou **cURL** para testar os endpoints do API Gateway e dos microsserviços.
-   Certifique-se de que o API Gateway está configurado corretamente para rotear as requisições para os serviços apropriados.
-   Interaja com a aplicação frontend para testar a integração completa.

### Desenvolvimento

-   **Backend**: Para adicionar novos recursos ou corrigir bugs, edite os arquivos no diretório `src/main/kotlin` do serviço correspondente.
-   **Frontend**: Para desenvolver novas funcionalidades ou ajustar a interface, trabalhe nos arquivos `.tsx` e `.css` dentro do diretório `frontend/src`.
-   Sempre atualize os testes e certifique-se de que o código está coberto antes de fazer o deploy.

## Contato

Para dúvidas ou suporte, entre em contato com a equipe Empresa Aérea.