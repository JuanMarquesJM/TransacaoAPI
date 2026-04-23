#  Transação API

API REST para recebimento e estatísticas de transações financeiras em tempo real, desenvolvida com Spring Boot 4 e Java 21.

---

##  Sobre o projeto

A API permite registrar transações financeiras e consultar estatísticas calculadas dinamicamente com base em um intervalo de tempo configurável. Todos os dados são mantidos em memória sem banco de dados.

> Projeto inspirado no desafio técnico de backend da Itaú Unibanco.

---

##  Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 4.0.2 |
| Gradle | 9.3.0 |
| Lombok | — |
| Swagger | 2.8.3 |

---

##  Arquitetura

O projeto segue uma arquitetura em camadas bem definida, separando responsabilidades de forma clara:

```
src/
└── main/
    └── java/com/graeff/transacao_api/
        ├── controller/          
        │   ├── dtos/           
        │   ├── TransacaoController.java
        │   └── EstatisticasController.java
        ├── business/
        │   └── services/       
        │       ├── TransacaoService.java
        │       └── EstatisticasService.java
        └── infrastructure/
            └── exceptions/      
                └── UnprocessableEntity.java
```

---

##  Como executar

### Pré-requisitos

- Java 21 instalado
- Git

### Clonando e rodando

```bash
# Clone o repositório
git clone https://github.com/JuanMarquesJM/transacao-api.git
cd transacao-api

# Execute com o Gradle Wrapper
./gradlew bootRun        # Linux/macOS
gradlew.bat bootRun      # Windows
```

A aplicação sobe por padrão em `http://localhost:8080`.

---

##  Documentação da API com Swagger

Com a aplicação rodando, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

---

##  Endpoints

### `POST /transacao`
Registra uma nova transação.

**Request Body:**
```json
{
  "valor": 150.75,
  "dataHora": "2025-04-23T10:30:00-03:00"
}
```

| Status | Descrição |
|---|---|
| `201 Created` | Transação registrada com sucesso |
| `422 Unprocessable Entity` | Valor negativo ou data/hora no futuro |
| `400 Bad Request` | Erro na requisição |
| `500 Internal Server Error` | Erro interno |

---

### `DELETE /transacao`
Remove todas as transações da memória.

| Status | Descrição |
|---|---|
| `200 OK` | Transações removidas com sucesso |

---

### `GET /estatistica?intervaloBusca={segundos}`
Retorna estatísticas das transações dentro do intervalo especificado.

| Parâmetro | Tipo | Obrigatório | Padrão | Descrição |
|---|---|---|---|---|
| `intervaloBusca` | `Integer` | Não | `60` | Janela de tempo em segundos |

**Response:**
```json
{
  "count": 3,
  "sum": 450.25,
  "avg": 150.08,
  "min": 50.00,
  "max": 250.25
}
```

| Status | Descrição |
|---|---|
| `200 OK` | Estatísticas calculadas com sucesso |

> Se não houver transações no período, todos os valores retornam `0`.

---

##  Regras de negócio

- O campo `valor` não pode ser negativo.
- O campo `dataHora` não pode ser uma data/hora no futuro.
- As estatísticas consideram apenas transações dentro da janela de tempo informada. Padrão: últimos 60 segundos.
- Os dados são armazenados em memória e perdidos ao reiniciar a aplicação.

---

##  Autor

**Juan Marques**
- LinkedIn: [juanmarques-jm](https://linkedin.com/in/juanmarques-jm)
