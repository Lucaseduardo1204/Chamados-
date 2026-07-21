## ddl-auto= 
    - create / create-drop: ele apaga e recria as tabelas ao subir, nunca usar com um banco que já foi modelado e populado
    - update: ele tenta alterar o schema para bater com o código. É conveniente mas faz mudanças silenciosas da qual não tem controle
    - validate: ele só confere se o código bate com as tabelas que já existem e reclama se não bater. Não altera o schema
