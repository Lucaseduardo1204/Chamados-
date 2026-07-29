# SISTEMA DE CHAMADOS - BACKEND

- O módulo de chamados visa solucionar a questão de organização de chamados para os analistas
  vindos de 3 bases de dados distintas.
- O módulo conta com 3 entidades principais sendo elas:
  USUARIOS
  CHAMADOS
  INTERACAO

### Relações:
- um usuário pode ter varios chamados (1 - n)
- um chamado pertence a um usuario (1 - 1)
- um chamado pode ter várias interacoes (1 - n)

### Tecnologias:

- Java 21
- Spring Boot 3.4.1
- Spring Security
- JWT
- PostgreSQL
- Docker

### Como Rodar:

1. Entrar na pasta do projeto


2. subir o container com o banco pelo comando:

docker compose up -d


3. Rodar os arquivos schema.sql e seed.sql pelos comando:

docker compose exec -T db psql -U Lucas -d chamados < arquivo.sql

4. Configurar o jwt.secret na pasta resources > application.properties

### AUTENTICAÇÃO E SEGURANÇA

Fluxo:

Usuario envia POST LoginRequestDTO (email e senha) -> AutenticacaoController chama AuthenticationManager que busca o usuário (via service) e confere  o hash da senha (via BCrypt) -> se for verdadeiro, TokenService assina um JWT e devolve o Token

LoginRequestDTO --> AutenticacaoController --> AuthenticationManager --> AutenticacaoService (implementa UserDetailService) --> Entity (que implementa UserDetails) --> BcryptPasswordEncoder --> TokenService --> LoginResponseDTO (token)

OBS: todos devidamente configurados pelo SecurityConfig

**Após o login**

Cliente envia o token no Header --> JWT filter intercepta antes do controller --> valida a assinatura com o secret --> verifica quem é --> insere no contexto do Spring

Com isso o sistema identifica quem está logado, e a autorização por role decide o que a pessoa pode fazer

BCrypt pois senhas não devem ser armazenadas em texto puro


JWT pois o servidor não armazena a sessão (stateless)

Autorização pois a autenticação não garante o que a pessoa autenticada pode fazer


### Endpoints:

- AutenticacaoController: http://localhost:8080/login
  - /login - autentica o usuário utilizando o Spring Security --> usuario envia o LoginRequestDTO informando email e senha, ambos com a anotação @NotBlank



- UsuarioController: http://localhost:8080/usuarios

  - /usuarios - possui os métodos listarTodos pelo verbo HTTP - GET
  - e o método criar pelo verbo HTTP - POST




- ChamadoController: http://localhost:8080/chamados

  - /chamados - no verbo Http GET -> lista todos os chamados

  - /chamados/{id} - no verbo Http GET -> busca o chamado pelo seu identificador (id)

  - /chamados - no verbo POST -> Cria um novo chamado pegando pelo @RequestBody e validando por meio do @Valid (Método aberto a qualquer usuário autenticado)

  - /chamados/{id}/responsavel - no verbo PATCH -> busca o chamado passado na URL (@PathVariable) e altera para o responsável que foi passado no @RequestBody, validando pela anotação @Valid (rota exclusiva para usuário do tipo ANALISTA)

  - /chamados/{id}/situacao - no verbo PATCH -> busca o chamado passado na URL (@PathVariable) e altera a situação para a nova que foi passado no RequestBody, validando pelo @Valid (rota exclusiva para usuário do tipo ANALISTA)

  - /chamados/{id}/interacoes - no verbo POST -> localiza o chamado pelo id passado na URL, recebe interacaoRequestDTO no RequestBody, e busca o usuário autenticado para colocá-lo como autor da interação, com isso cria uma nova interação no  chamado com o id passado na URL (Usuario analista comenta em qualquer chamado e solicitantes apenas em chamados criados por ele)

  - /chamados/{id}/interacoes - no verbo GET -> Lista todas as interações do chamado do id passado no @PathVariable (rota exclusiva para usuário do tipo ANALISTA)

  - /chamados/{id} - no verbo DELETE -> Realiza o soft delete inativando o chamado com o id igual ao que foi passado na URL


CLASSES E ATRIBUTOS:

- Usuario: id, nome, email, tipoUsuario, fundacao, senha
- Chamado: id, dataHoraCriacao (automatica), dataHoraModificacao (automatica), situacao (padrão ABERTA), prioridade, tipo,
  sistema, resumo, descricao, solicitante, responsavel, ativo com @SQLRestriction("ativo = true")
- Interacao: id, chamado, autor, dataHora (automatica), texto


### REGRAS DE NEGÓCIO:

- **Login**:  O método recebe os dados do LoginRequestDTO, após isso authToken envelopa os dados para carregar email e senha, após isso é definida a variavel auth que utiliza o authenticationManager.authenticate para autenticar o objeto presente em authToken, ao validar, retorna um objeto Authentication totalmente preenchido e validado. Com isso a variável usuario recebe o casting (Usuario) de um objeto com os dados completos do usuário logado (geralmente um UserDetails) pelo método .getPrincipal(), após a variavel token chama o tokenService para gerar um token para esse usuario, e, por fim, o método retorna um novo LoginResponseDTO com o token

- **CRIAR CHAMADO**: Recebe um ChamadoRequestDTO, busca o id do solicitante e armazena na variavel o objeto Usuario, caso não
  encontre lança a exceção de usuário não encontrado, se encontrar, cria um novo chamado utilizando a entity chamado, esse
  ja nasce com a situação ABERTA por padrão (iniciada no service como SituacaoEnum.ABERTA), pega os campos que foram passados pelo requestDTO  e atribui cada atributo ao construtor  do chamado, utiliza o repository para salvar e devolve o chamado convertido de entity para DTO;

- **LISTAR TODOS**: Cria uma lista do tipo Chamado e usa o ChamadoRepository para buscar todos pelo .findAll(), após isso
  percorre os elementos do chamado convertendo item a item de entity para dto e armazena na lista que será retornada pelo
  método;

- **LISTAR POR ID**: Cria a variavel chamado que é resultante da busca do chamado por id, se não encontrado lança exceção
  ChamadoNaoEncontradoException, se encontrado converte para dto e retorna o resultado;

- **ALTERAR RESPONSAVEL**: Metodo com @Transactional ou seja, se em alguma etapa ocorrer um erro, desfaz toda a ação, esse
  recebe o id do chamado e do responsavel busca o chamado e responsavel pelo id, (caso não encontrado lança exceções),
  também há a verificação se o novo responsavel possui o TipoUsuario = ANALISTA não permitindo a atribuição de Usuarios
  solicitantes como responsavel do chamado, caso passe pela verificação seta o novo responsavel no chamado, salva pelo
  repository converte e retorna o DTO do chamado alterado com o novo responsavel

- **ALTERAR SITUACAO** : segue a lógica do alterar responsavel, recebe o id do chamado e a nova situacao, busca o chamado, e
  verifica se a situação atual é igual a FECHADA, se sim, não permite a alteração da situacao, após a verificação, seta a
  situação com a nova situaçao, salva, converte e retorna

- **ADICIONAR INTERACAO**:  Recebe id do chamado, a interação e autor, busca o chamado, e verifica se o tipo do usuario é
  ANALISTA ou se o usuário é o dono do chamado não permitindo que solicitantes adicionem interações em chamados que não o
  pertencem, caso verificado instancia uma nova interacao no chamado, (aqui autor é retirado de quem está autenticado),
  salva a interacao, converte e retorna

- **LISTAR INTERACOES**: busca o chamado e busca as interacoes ordenadas por hora, retorna a lista de interações convertidas
  com todos as interacoes do chamado solicitado

- **DELETAR CHAMADO**: como dito anteriormente, não deleta de fato, apenas inativa o chamado com o id, o método busca o
  chamado, e seta o atributo ativo para false e salva, com isso, por ter a anotacao @SQLRestriction("ativo = true") em
  qualquer outro método como de listar, esse será ignorado

### DECISÕES DE DESIGN

- DTOs = controle de informações, evitando vazamento de dados e ataques de mass assignment, sendo mais um vetor de validação antes de entrar no sistema
- Soft delete = garantir a rastreabilidade e evitar a exclusão de registros importantes
- ENUM = para valores fixos impedindo que sejam inputados valores incompatíveis

### MELHORIAS FUTURAS

- Rate Limiting no Login
- Refresh token
- secret em variável de ambiente
- tratamento de filtro JWt para token expirado ou usuário deletado


