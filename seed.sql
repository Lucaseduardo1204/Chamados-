INSERT INTO  usuarios (nome, email, tipo_usuario, fundacao)
VALUES ('TESTE', 'teste@teste.com', 'ANALISTA', 'PMA'),
       ('Lucas', 'lucas@faec.br', 'ANALISTA', 'FAEC'),
       ('Maria', 'maria@famep.br', 'SOLICITANTE', 'FAMEP'),
       ('João', 'joao@pma.br', 'SOLICITANTE', 'PMA');


/* Chamados ficticios para teste*/
INSERT INTO  chamados (situacao, prioridade, tipo, sistema, resumo, descricao, solicitante_id)
VALUES ('ABERTA', 'CRITICO', 'SISTEMA', 'Contabilidade', 'Divergência de saldo em relatórios',
        'Ao retirar o relatório no sistema o saldo se encontra divergente ao apresentado no sistema',(SELECT usuario_id FROM usuarios WHERE email = 'maria@famep.br')),

       ('ABERTA', 'NORMAL', 'SISTEMA', 'Almoxarifado', 'Dificuldade ao cadastrar item',
        'Ao cadastrar item no sistema de almoxarifado, o item não retorna na solicitação', (SELECT usuario_id FROM usuarios WHERE email = 'lucas@faec.br')),

       ('RESOLVIDA', 'BLOQUEIO', 'TECNICA', 'INFRAESTRUTURA', 'Sem rede',
        'sem rede na fundação desde ontem', (SELECT usuario_id FROM usuarios WHERE nome = 'TESTE')),

       ('FECHADA', 'BAIXA', 'SISTEMA', 'Frotas', 'Baixa de combustivel travada',
        'Fui realizar a requisição mas a baixa esta travando gerar nova requisição', (SELECT usuario_id FROM usuarios WHERE nome = 'João')),

       ('FECHADA', 'TRIVIAL', 'SISTEMA', 'Cadastros Gerais', 'Duvida quanto ao cadastro pessoal',
        'Como devo realizar o cadastro de pessoa no sistema de cadastro geral', (select  usuario_id FROM usuarios WHERE nome = 'Maria'));



/* Atribuindo responsavel em 3 chamados*/
UPDATE chamados
SET responsavel_id = (select usuario_id FROM usuarios WHERE email = 'lucas@faec.br')
WHERE sistema IN ('Almoxarifado', 'INFRAESTRUTURA');

UPDATE chamados
SET responsavel_id = (select usuario_id FROM usuarios WHERE email = 'teste@teste.com')
WHERE sistema = 'Contabilidade';


-- As subqueries são queries dentro dos valores que serão passados como parametro do insert into
/* Inclusão de alterações */
INSERT INTO interacao (chamado_id, autor_id, texto)
VALUES ((select chamado_id from chamados where sistema = 'INFRAESTRUTURA'), (select solicitante_id from chamados where sistema = 'INFRAESTRUTURA'), 'A internet voltou portanto não sera necessária a equipe técnica'),
       ((select chamado_id from chamados where sistema = 'Contabilidade'), (select solicitante_id from chamados where sistema = 'Contabilidade'), 'A divergência está no relatório de Balancete'),
       ((select chamado_id from chamados where sistema = 'Frotas'), (select solicitante_id from chamados where sistema = 'Frotas'), 'O problema não está nas baixas');

INSERT INTO interacao (chamado_id, autor_id, texto)
VALUES ((select chamado_id from chamados where sistema = 'Frotas'), (select solicitante_id from chamados where sistema = 'Frotas'), 'Consegui realizar a requisição!'),
       ((select chamado_id from chamados where sistema = 'Contabilidade'), (select solicitante_id from chamados where sistema = 'Contabilidade'), 'Situação alterada para FECHADA');



