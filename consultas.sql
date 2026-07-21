--Join
/*  O JOIN pega cada linha de uma tabela e procura a linha correspondente na outra, usando a condição de casamento
    (o ON)
*/

SELECT  c.chamado_id, c.resumo, u.nome --eu quero da tabela c id do chamado, resumo e da tabela u o nome
from chamados c -- a tabela c é a tabela de chamado
         JOIN usuarios u -- a tabela u é a tabela de usuários
              ON  c.solicitante_id = u.usuario_id; --junta c com u, onde é solicitante_id seja igual a usuario_id

select c.resumo, u.nome --quero o resumo de c e nome de u
from chamados c -- c = chamados
         join usuarios u  -- u = usuarios
              ON  c.solicitante_id = u.usuario_id; -- onde o c.solicitante_id for igual ao u.usuario_id
-- on é onde vai rolar a comparação para achar o usuário na tabela de usuários e retornar o nome
-- então onde o solicitante_id é 1 e usuario_id é 1, retorna nome


/*"eu quero o c.resumo, sol.nome como solicitante, resp.nome como responsável, de chamados (c)
  junto com usuários (sol) onde c.solicitante_id = sol.usuario_id. Left join seria como eu quero
  tudo  onde resp esteja em responsavel id*/
SELECT c.resumo, sol.nome AS solicitante, resp.nome AS responsavel
FROM chamados c
         JOIN usuarios sol ON c.solicitante_id = sol.usuario_id
         LEFT JOIN usuarios resp ON c.responsavel_id = resp.usuario_id;

-- GROUP BY junta linhas que compartilham um valor com as funções de agregação (COUNT, SUM, AVG, MAX)

--1.
select situacao, COUNT(*)
from chamados
group by situacao;

/*
 situacao  | count
-----------+-------
 FECHADA   |     2
 ABERTA    |     2
 RESOLVIDA |     1
(3 rows)
*/

--2.
select tipo_usuario, COUNT(*)
from usuarios
group by tipo_usuario;
/*
tipo_usuario | count
--------------+-------
 SOLICITANTE  |     2
 ANALISTA     |     2
(2 rows)

*/

--3.
SELECT situacao, resumo, COUNT(*)
FROM chamados
GROUP BY situacao;

/*ERROR:  column "chamados.resumo" must appear in the GROUP BY clause or be used in an aggregate function
LINE 1: select situacao, resumo, count(*)

  Ao incluir o resumo no GROUP BY, traz todas as linhas diferentes pois o resumo não é igual para todos, portanto
  count seria sempre 1 e traria todos os chamados
  Quando 2 chamados abertos se colapsam cada um com resumo diferente o banco não saberia qual mostrar então
  ele proibe a "pergunta"
*/

--SOLUÇÃO:
select situacao, count(*)
from chamados
group by situacao;

/*

 situacao  | count
-----------+-------
 FECHADA   |     2
 ABERTA    |     2
 RESOLVIDA |     1
(3 rows)

 */


-- 4. Eu quero o nome, a situação e o número de chamados por responsável

select u.nome, c.situacao, count(*) -- seleciona o nome, e o número
from chamados c -- de chamados c
         join usuarios u  -- junto com usuarios u
              on c.responsavel_id = u.usuario_id -- onde o responsável_id, seja igual ao usuario_id
group by nome, situacao; --agrupe por nome e situação


/*
 nome  | situacao  | count
-------+-----------+-------
 Lucas | RESOLVIDA |     1
 Lucas | ABERTA    |     2
(2 rows)

*/

-- CHAMADOS POR RESPONSÁVEL

select u.nome, count(*)
from chamados c
         join usuarios u
              on c.responsavel_id = u.usuario_id
group by nome;