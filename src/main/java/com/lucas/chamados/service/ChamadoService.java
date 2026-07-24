package com.lucas.chamados.service;

import com.lucas.chamados.dto.AlterarResponsavelDTO;
import com.lucas.chamados.dto.ChamadoRequestDTO;
import com.lucas.chamados.dto.ChamadoResponseDTO;
import com.lucas.chamados.dto.UsuarioResumoDTO;
import com.lucas.chamados.exception.ChamadoNaoEncontradoException;
import com.lucas.chamados.exception.UsuarioDiferenteAnalista;
import com.lucas.chamados.exception.UsuarioNaoEncontradoException;
import com.lucas.chamados.model.entity.Chamado;
import com.lucas.chamados.model.entity.Usuario;
import com.lucas.chamados.model.enums.SituacaoEnum;
import com.lucas.chamados.model.enums.TipoUsuario;
import com.lucas.chamados.repository.ChamadoRepository;
import com.lucas.chamados.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChamadoService {
    private final ChamadoRepository chamadoRepository;
    private final UsuarioRepository usuarioRepository;

    //Injeção de dependencia, para ChamadoService existir, necessita do ChamadoRepository e UsuarioRepository
    public ChamadoService(ChamadoRepository chamadoRepository, UsuarioRepository usuarioRepository){
        this.chamadoRepository = chamadoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // @Transactional- ou da tudo certo, ou tudo errado (8 ou 80)
    @Transactional
    // método público (novoChamado) que retorna um ChamadoResponseDTO, esse deve ter: id, dataHoraCriacao, situacao,
    //  prioridade tipo, sistema, resumo, descricao, solicitante (responsável? eis a questão, atualmente não)

    // Esse método recebe o objeto ChamadoRequest que precisa ter as seguintes informações: prioridade, tipo, sistema,
    //  resumo descricao, solicitanteId
    public ChamadoResponseDTO novoChamado(ChamadoRequestDTO requestDTO){

        //busca o id do solicitante e armazena no solicitanteId o objeto do tipo USUARIO, caso não enconte, lança a
        // exceção
        var solicitante = usuarioRepository.findById(requestDTO.solicitanteId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(requestDTO.solicitanteId()));


        // Se encontrar cria um novoChamado, do tipo Chamado, esse já nasce aberta por padrão pega os campos presentes
        // no objeto (requestDTO) e atribui cada atributo ao chamado respectivamente
        Chamado novoChamado = new Chamado(SituacaoEnum.ABERTA, requestDTO.prioridade(), requestDTO.tipo(),
                requestDTO.sistema(), requestDTO.resumo(), requestDTO.descricao(), solicitante);


        //chamadoRepository salva novoChamado no banco
        Chamado salvo = chamadoRepository.save(novoChamado);

        // Retorna um novo ChamadoResponseDTO com os campos de novo chamado, no solicitante aninha um novo DTO com o
        // resumo do Usuario
        return converterEntityParaDto(salvo);
    }

    private UsuarioResumoDTO converterUsuario(Usuario usuario){
        if (usuario == null) return null;

        return new UsuarioResumoDTO(usuario.getId(), usuario.getNome(), usuario.getFundacao());
    }

    //Pega o parâmetro chamado, e retorna em umm ChamadoResponseDTO se chamado == null, retorna null
    private ChamadoResponseDTO converterEntityParaDto(Chamado chamado){
            return new ChamadoResponseDTO(
                    chamado.getId(),
                    chamado.getDataHoraCriacao(),
                    chamado.getSituacao(),
                    chamado.getPrioridade(),
                    chamado.getTipo(),
                    chamado.getSistema(),
                    chamado.getResumo(),
                    chamado.getDescricao(),
                    converterUsuario(chamado.getSolicitante()),
                   converterUsuario(chamado.getResponsavel())
            );

    }

    public List<ChamadoResponseDTO> listarTodos(){
        List<Chamado>  listaDeChamados = chamadoRepository.findAll();

        return listaDeChamados.stream().map(this::converterEntityParaDto).toList();
    }

    //Recebe o id por parametro (id vem do controller)
    public ChamadoResponseDTO listarPorId(Long id){
        //variavel chamado que armazena o resultado da busca do chamadoRepository pelo id, se não encontrar
        // lança a exceção ChamadoNaoEncontrado que recebe o id para mostrar no log
        var chamado = chamadoRepository.findById(id).orElseThrow(() -> new ChamadoNaoEncontradoException(id));

        // Converte a entity pra DTO e retorna pro controller
        return converterEntityParaDto(chamado);
    }

    @Transactional
    // alterar responsavel é o metodo que devolverá um chamadoResponseDTO, ele recebe por parametro o id do chamado e o
    // id do novoResponsavel presente no AlterarResponsavelDTO
    public ChamadoResponseDTO alterarResponsavel(Long idChamado, AlterarResponsavelDTO novoResponsavel){
        // Variavel chamado busca o chamado pelo id, se não encontrar lança a exception, e se encontrar armazena na variável chamado
        var chamado = chamadoRepository.findById(idChamado).orElseThrow(() -> new ChamadoNaoEncontradoException(idChamado));

        // busca o responsavel pelo id, se encontrar atribui na variavel responsavel atribuido, se não lança exceção
        var responsavelAtribuido = usuarioRepository.findById(novoResponsavel.id()).orElseThrow(() -> new UsuarioNaoEncontradoException(novoResponsavel.id()));

        // Se o tipo do responsavelAtribuido for diferente de analista, lança exception com a mensagem que o responsavel deve ser analista
        if (!responsavelAtribuido.getTipoUsuario().equals(TipoUsuario.ANALISTA)){
            throw new UsuarioDiferenteAnalista(responsavelAtribuido.getId());
        }

        // Somente chama o setResponsavel e altera o responsavel
        chamado.setResponsavel(responsavelAtribuido);

        Chamado chamadoSalvo = chamadoRepository.save(chamado);

        return converterEntityParaDto(chamadoSalvo);
    }



}
