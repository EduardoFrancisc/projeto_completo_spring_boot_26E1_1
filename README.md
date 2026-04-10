# Projeto Assessment - Sistema de Gestão de Guilda

Este é um sistema desenvolvido em **Spring Boot (Java 21)** para o gerenciamento de uma "Guilda de Aventureiros". O sistema gerencia o cadastro de aventureiros, seus companheiros (pets/familiares), a criação e alocação de missões, relatórios de desempenho e um catálogo de loja de produtos mágicos integrado com **Elasticsearch**.

## 🛠️ Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3** (Web, Data JPA, Data Elasticsearch, Cache)
* **PostgreSQL** (Banco de dados relacional para as operações da Guilda)
* **Elasticsearch** (Motor de busca em texto completo para a loja de itens)
* **Docker & Docker Compose** (Para provisionamento da infraestrutura)
* **Lombok** (Redução de código boilerplate)
* **Maven** (Gerenciador de dependências)

---

## ⚙️ Pré-requisitos

Para rodar este projeto na sua máquina, precisará de ter instalado:
* [Java Development Kit (JDK) 21](https://jdk.java.net/21/)
* [Docker](https://www.docker.com/products/docker-desktop/) e [Docker Compose](https://docs.docker.com/compose/install/)

---

## 🐳 Como subir a infraestrutura (Docker Compose)

O projeto depende de uma base de dados PostgreSQL e de uma instância do Elasticsearch. Ambos estão configurados no ficheiro `docker-compose.yml` utilizando imagens pré-configuradas (com dados e schemas iniciais).

1. Abra o terminal.
2. Navegue até à pasta `docker` na raiz do projeto (onde o arquivo `docker-compose.yml` está localizado):
   ```bash
   cd docker
   ```
3. Suba os container em segundo plano executando:
   ```bash
   docker-compose up -d
   ```
4. Verifique se o containers estão ativo:
   ```bash
   docker ps
   ```
   *Deverá ver dois containers ativos: `postgres-tp2-db` (porta 5432) e `elasticsearch-tp2` (porta 9200).*

---

## ▶️ Como iniciar a aplicação Spring Boot

Com a infraestrutura do Docker ativa, pode iniciar a aplicação Spring Boot.

**Via Linha de Comandos (Maven Wrapper):**
Na raiz do projeto (onde está o ficheiro `pom.xml` e `mvnw`), execute:
```bash
# No Windows:
.\mvnw spring-boot:run

# No Linux/Mac:
./mvnw spring-boot:run
```

**Via IDE (IntelliJ, Eclipse, VS Code):**
Basta localizar a classe principal `AssessmentApplication.java` (em `src/main/java/br/edu/infnet/assessment/`) e executá-la.

A aplicação iniciará o servidor Tomcat na porta padrão `8080`.

---

## 📍 Endpoints da API

Abaixo estão as rotas disponíveis no sistema, divididas por domínio:

### 🗡️ Aventureiros (`/aventureiros`)
Gerencia o registo, recrutamento e perfis dos aventureiros e seus companheiros.

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `GET` | `/aventureiros` | Lista aventureiros com paginação. Permite filtros por `classe`, `ativo` e `nivelMinimo`. |
| `GET` | `/aventureiros/{id}` | Busca os detalhes de um aventureiro específico. |
| `POST` | `/aventureiros` | Regista um novo aventureiro. |
| `PUT` | `/aventureiros/{id}` | Atualiza os dados de um aventureiro existente. |
| `PATCH` | `/aventureiros/{id}/inativar` | Inativa (desliga) um aventureiro da guilda. |
| `PATCH` | `/aventureiros/{id}/recrutar` | Recruta (reativa) um aventureiro. |
| `PUT` | `/aventureiros/{id}/companheiro` | Salva ou atualiza um companheiro (pet) para o aventureiro. |
| `DELETE` | `/aventureiros/{id}/companheiro`| Remove o companheiro de um aventureiro. |
| `GET` | `/aventureiros/busca` | Busca aventureiros por nome (paginado). |
| `GET` | `/aventureiros/{id}/perfil` | Retorna o perfil completo do aventureiro, incluindo estatísticas e última missão. |

### 📜 Missões (`/missoes`)
Gerencia o quadro de missões e as alocações de aventureiros nelas.

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `GET` | `/missoes` | Lista missões com paginação e filtros (`status`, `nivelPerigo`, `dataInicio`, `dataFim`). |
| `GET` | `/missoes/{id}` | Detalhes de uma missão, incluindo os aventureiros participantes. |
| `POST` | `/missoes` | Cria uma nova missão. |
| `POST` | `/missoes/{id}/participacoes` | Inscreve um aventureiro numa missão com um papel específico (ex: LIDER, SUPORTE). |
| `GET` | `/missoes/top15dias` | Retorna o painel tático das Top 10 missões dos últimos 15 dias (Utiliza Cache em memória). |

### 📊 Relatórios (`/relatorios`)
Fornece métricas e rankings de gestão da Guilda.

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `GET` | `/relatorios/ranking` | Retorna o ranking de aventureiros com base em participações, recompensa e MVPs. |
| `GET` | `/relatorios/missoes-metricas`| Retorna métricas das missões (quantidade de participantes, recompensas distribuídas). |

### 💎 Loja / Produtos (Elasticsearch) (`/produtos`)
Motor de busca avançado para encontrar itens mágicos e equipamentos usando o Elasticsearch.

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `GET` | `/produtos/busca/nome` | Busca produto pelo nome exato/parcial. |
| `GET` | `/produtos/busca/descricao` | Busca produto por palavras na descrição. |
| `GET` | `/produtos/busca/frase` | Busca produtos que contenham uma frase exata na descrição. |
| `GET` | `/produtos/busca/fuzzy` | Busca tolerante a erros de digitação (Fuzzy Search). |
| `GET` | `/produtos/busca/multicampos` | Busca um termo tanto no nome como na descrição simultaneamente. |
| `GET` | `/produtos/busca/com-filtro` | Busca textual combinada com filtro exato de categoria. |
| `GET` | `/produtos/busca/faixa-preco` | Busca produtos dentro de uma faixa de preço (`min` e `max`). |
| `GET` | `/produtos/busca/avancada` | Busca complexa envolvendo categoria, raridade e faixa de preço. |

### 📈 Agregações de Produtos (`/produtos/agregacoes`)
Endpoints focados em estatísticas dos produtos catalogados no Elasticsearch.

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `GET` | `/produtos/agregacoes/por-categoria`| Conta a quantidade de itens agrupados por categoria. |
| `GET` | `/produtos/agregacoes/por-raridade` | Conta a quantidade de itens agrupados por raridade. |
| `GET` | `/produtos/agregacoes/preco-medio` | Calcula a média de preço de todos os produtos na loja. |
| `GET` | `/produtos/agregacoes/faixas-preco` | Distribui os itens em faixas (Abaixo de 100, De 100 a 300, etc.). |

---

## 🛑 Como parar a aplicação

Para parar o Spring Boot, basta usar `Ctrl + C` no terminal onde ele está a correr (ou dar Stop na sua IDE).
Para desligar a base de dados e o Elasticsearch, volte para a pasta `docker` e execute:
```bash
docker-compose down
```
*(Se quiser limpar também os volumes/dados gerados, adicione a flag `-v` ao final: `docker-compose down -v`)*
