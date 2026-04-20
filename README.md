# LiterAlura - Catálogo de Livros 📚
<p align="center">
<img width="461" height="95" alt="image" src="https://github.com/user-attachments/assets/aa70ea6f-5792-4860-a34d-5d365efc6d2f" />
</p>
O LiterAlura é um sistema de catálogo de livros robusto desenvolvido em Java com Spring Boot. O projeto vai além do consumo de APIs, integrando a busca da API Gutendex com um banco de dados relacional para persistir informações sobre obras e autores, permitindo consultas filtradas e análises estatísticas diretamente no console.

## 🚀 Funcionalidades
- Busca de Livros por Título: Consulta a API Gutendex e armazena automaticamente os resultados no banco de dados.
- Listagem de Livros Registrados: Exibe todos os livros que já foram salvos no repositório local.
- Listagem de Autores: Exibe os autores cadastrados no sistema.
- Filtro de Autores Vivos: Consulta autores que estavam vivos em um determinado ano informado pelo usuário.
- Filtro por Idioma: Lista livros de acordo com a sigla do idioma (ex: pt, en, fr).
- Estatísticas de Dados: Integração de consultas JPQL para extrair insights sobre o acervo.

## 🛠️ Tecnologias Utilizadas
- Java 17+
- Spring Boot 3: Framework base para a aplicação.
- Spring Data JPA: Para facilitar a persistência e consultas ao banco de dados.
- PostgreSQL: Banco de dados relacional para armazenamento persistente.
- Jackson: Para o mapeamento de dados JSON vindos da API.
- Gutendex API: Fonte de dados aberta sobre literatura clássica.

## ⚙️ Configuração e Execução
### 1. Pré-requisitos:
- Java 17 ou superior.
- PostgreSQL instalado e em execução.
- Maven (ou utilizar o wrapper ./mvnw).

### 2. Configuração do Banco de Dados
- configurar variaveis de ambientes para acessar o seu banco de dados
- No arquivo src/main/resources/application.properties, configure sua conexão:

   ```
    spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
    spring.datasource.username=${seu_usuario}
    spring.datasource.password=${sua_senha}
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
  ```

## ⚙️ Como executar o projeto
```
# Clone o repositório
git clone https://github.com/Dev-Joao-Medeiros/literAlura.git

# Acesse a pasta do projeto
cd literAlura

# Execute a aplicação
./mvnw spring-boot:run

```
