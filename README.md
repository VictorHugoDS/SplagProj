# Ambiente Docker com PostgreSQL e MinIO

Este projeto configura um ambiente de desenvolvimento com PostgreSQL e MinIO usando Docker Compose.

## Serviços

### PostgreSQL
- Porta: 5432
- Usuário: admin
- Senha: admin123
- Banco de dados: mydb

### MinIO
- API Port: 9000
- Console Port: 9001
- Usuário: minioadmin
- Senha: minioadmin

## Como usar

1. Certifique-se de ter o Docker e Docker Compose instalados
2. Execute o comando:
```bash
docker-compose up -d
```

3. Para parar os serviços:
```bash
docker-compose down
```

## Acessando os serviços

- PostgreSQL: localhost:5432
- MinIO API: http://localhost:9000
- MinIO Console: http://localhost:9001

## Volumes

Os dados são persistidos em volumes Docker:
- postgres_data: dados do PostgreSQL
- minio_data: dados do MinIO 