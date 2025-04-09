package br.insper.produto.security;

import br.insper.produto.login.LoginService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private LoginService loginService;

    @Override
    protected void doFilterInternal(@org.jetbrains.annotations.NotNull HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        String method = request.getMethod();

        // Liberar login sem validação
        if (path.equals("/api/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String role = loginService.validateToken(token);

        // Se for rota POST ou PUT (cadastrar ou alterar), só ADMIN pode
        if ((path.startsWith("/produtos") && (method.equals(HttpMethod.POST.name()) || method.equals(HttpMethod.PUT.name())))
                && !role.equals("ADMIN")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // GET pode qualquer um
        filterChain.doFilter(request, response);
    }
}
