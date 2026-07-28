package com.lucas.chamados.infra;

import com.lucas.chamados.repository.UsuarioRepository;
import com.lucas.chamados.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
// OncePerRequestFilter - garante que rode uma vez por requisição
public class JwtFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public JwtFilter(TokenService tokenService, UsuarioRepository usuarioRepository){
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    // Esse método visa olhar o header da requisição, se tiver um token ali, no formato Bearer <token>, extrai ele
    // Valida a assinatura com o secret, confirmando que foi emitido pelo servidor
    // Descobre quem é o dono do token pelo email presente no subject
    // Busca o usuário e coloca a identidade no contexto do Spring
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {

        //procura por Authorization dentro da request e armazena na variavel header
        String header = request.getHeader("Authorization");

        //se header for diferente de null e começar com Bearer
        if (header != null && header.startsWith("Bearer ")){
            // variavel String token recebe header sem o "Bearer "
            String token = header.replace("Bearer ", "");
            //email recebe o resultado da validação do tokenService.validartoken(token)
            String email = tokenService.validartoken(token);

            //usuario recebe o usuario que tem o mesmo email ou lança exceção
            var usuario = usuarioRepository.findByEmail(email).orElseThrow();
            //variavel auth recebe um novo UserNamePasswordAuthenticationToken com usuário, credencial e autorizaçoes
            var auth = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            //SecurityContextHolder.getContext().setAuthentication(auth) - é a linha que avisa quem está logado
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);

    }

}
