# 🚀 SplagProj - Projeto Prático do concurso da SEPLAG-MT

## 📝 Apresentação
Projeto desenvolvido como parte da avaliação da prática do concurso para analista desenvolvedor da SEPLAG-MT.

## 🕶️️ Contato 
**Nome:** Victor Hugo Duarte da Silva  
**Email:** victorhugod.s@hotmail.com

## 🛠️ Pré-requisitos
Antes de começar, certifique-se de ter instalado:
- Java 17 
- Maven 
- Docker (para rodar o Minio e banco de dados)
- Git 

## 🎣 Ferramentas Utilizadas
- **Spring Boot**
- **PostgreSQL**
- **Minio**
- **Swagger**
- **Lombok**
- **JPA/Hibernate**
- **Maven**


## 🚀 Como Rodar o Projeto

1. **Clone o repositório**
```bash
git clone https://seu-repositorio/SplagProj.git
cd SplagProj
```

2. **Configure o banco de dados**
```bash
# Inicie o PostgreSQL via Docker
docker run --name splag-db -e POSTGRES_PASSWORD=sua_senha -p 5432:5432 -d postgres
```

3. **Configure o Minio**
```bash
# Inicie o Minio via Docker
docker run -p 9000:9000 -p 9001:9001 --name splag-minio \
  -e "MINIO_ROOT_USER=seu_usuario" \
  -e "MINIO_ROOT_PASSWORD=sua_senha" \
  minio/minio server /data --console-address ":9001"
```

4. **Configure as variáveis de ambiente**
Crie um arquivo `application.properties` com:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/splag
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

minio.endpoint=http://localhost:9000
minio.accessKey=seu_usuario
minio.secretKey=sua_senha
```

5. **Execute o projeto**
```bash
mvn spring-boot:run
```

## 🚀 Rodando com Configurações Padrão
Para rodar o projeto rapidamente com as configurações padrão o projeto utilizará as configurações padrão do `application.properties`:

- Banco de dados: PostgreSQL local na porta 5432
- Minio: local na porta 9000
- Credenciais padrão do banco: usuário 'postgres' e senha 'postgres'
- Credenciais padrão do Minio: usuário 'minioadmin' e senha 'minioadmin'

## 🌟 Exemplos de Endpoints

### 1. Buscar Servidor por Nome
```http
GET /servidor-efetivo/endereco-funcional?nomeParcial=João
```
Retorna o endereço funcional de servidores que contenham "João" no nome.

### 2. Listar Servidores por Unidade
```http
GET /servidor-efetivo/por-unidade/123
```
Retorna todos os servidores lotados na unidade com ID 123, incluindo:
- Nome
- Idade
- Unidade de lotação
- URL da foto

### 3. Criar Novo Servidor
```http
POST /servidor-efetivo
Content-Type: application/json

{
    "pessoa": {
        "nome": "Maria Silva",
        "data": "1985-06-15",
        "sexo": "FEMININO"
    },
    "matricula": "12345"
}
```

### 4. Atualizar Servidor
```http
PUT /servidor-efetivo/123
Content-Type: application/json

{
    "matricula": "54321"
}
```

## 🌟 Exemplos Adicionais de Endpoints

### 5. Buscar Servidor por Matrícula
```http
GET /servidor-efetivo/matricula/12345
```
Retorna os detalhes completos do servidor com a matrícula especificada.

### 6. Listar Todos os Servidores
```http
GET /servidor-efetivo/todos
```
Retorna uma lista paginada de todos os servidores cadastrados no sistema.
