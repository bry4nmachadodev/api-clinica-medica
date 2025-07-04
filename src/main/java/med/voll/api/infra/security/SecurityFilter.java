package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // recupera o token JWT do cabeçalho Authorization da requisição
        var tokenJWT = recuperarToken(request);

        // verifica se o token não é nulo, ou seja, se existe um token na requisição
        if(tokenJWT != null) {
            // extrai o subject (usuário) do token, validando-o internamente
            var subject = tokenService.getSubject(tokenJWT);
            // busca o usuário no banco de dados pelo login extraído do token
            var usuario = repository.findByLogin(subject);

            // cria um objeto de autenticação contendo o usuário e suas permissões
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            // configura o contexto de segurança do Spring com o usuário autenticado
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // continua o fluxo da requisição, chamando o próximo filtro ou controller
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

}


//Mas então, por que criar o authentication e configurar o SecurityContextHolder?
//Porque mesmo o token sendo válido, o Spring Security não sabe disso ainda.
//O token é uma prova de que você está autenticado, mas o Spring só passa a "acreditar" nisso quando você registra explicitamente a autenticação no contexto usando: