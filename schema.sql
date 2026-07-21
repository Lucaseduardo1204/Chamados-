/*
COMANDOS EM SQL:
SUBIR O BANCO: docker compose up -d
docker compose down
ENTRAR NO BANCO VIA DOCKER: docker compose exec db psql -U Lucas -d chamados
        COMANDOS DENTRO DO BANCO:
            // listar bancos		\l
            // listar tabelas		\dt
            // ver estrutura de uma tabela		\d nome_tabela
            // limpar tela			\! clear
            // ou Ctrl+L ajuda dos meta-comandos		\?
            // sair		\q

EXECUTAR COMANDOS ESCRITOS EM ARQUIVOS EXTERNOS: docker compose exec -T db psql -U Lucas -d chamados < arquivo.sql
APAGAR AS INFORMAÇÕES DO BANCO: TRUNCATE interacao, chamados, usuarios RESTART IDENTITY CASCADE;



*/

create table usuarios (
                          usuario_id   BIGSERIAL PRIMARY KEY,
                          nome         VARCHAR(100) NOT NULL,
                          email        VARCHAR(100) NOT NULL UNIQUE,
                          tipo_usuario VARCHAR(20)  NOT NULL  CHECK (tipo_usuario IN ('SOLICITANTE', 'ANALISTA')),
                          fundacao     VARCHAR(20)  NOT NULL  CHECK (fundacao IN ('FAEC', 'FAMEP', 'PMA'))
);


create table chamados (
                          chamado_id              BIGSERIAL PRIMARY KEY,
                          data_hora_criacao       TIMESTAMP DEFAULT NOW() NOT NULL,
                          data_hora_modificacao   TIMESTAMP NOT NULL DEFAULT NOW(),
                          situacao                VARCHAR(20) NOT NULL  CHECK (situacao IN ('ABERTA','FECHADA','RESOLVIDA')),
                          prioridade              VARCHAR(20) NOT NULL  CHECK(prioridade IN ('BLOQUEIO', 'CRITICO', 'NORMAL', 'BAIXA','TRIVIAL')),
                          tipo                    VARCHAR(15) NOT NULL  CHECK(tipo IN ('SISTEMA','TECNICA')),
                          sistema                 VARCHAR(50) NOT NULL,
                          resumo                  VARCHAR(100) NOT NULL,
                          descricao               VARCHAR(500) NOT NULL,
                          solicitante_id          BIGINT NOT NULL REFERENCES usuarios(usuario_id),
                          responsavel_id          BIGINT  REFERENCES usuarios(usuario_id)
);

CREATE TABLE interacao (
                           interacao_id            BIGSERIAL PRIMARY KEY,
                           chamado_id              BIGINT NOT NULL REFERENCES chamados(chamado_id),
                           autor_id                BIGINT NOT NULL REFERENCES usuarios(usuario_id),
                           data_hora               TIMESTAMP DEFAULT NOW(),
                           texto                   VARCHAR(500)
);
