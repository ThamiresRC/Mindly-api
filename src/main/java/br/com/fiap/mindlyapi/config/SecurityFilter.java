package br.com.fiap.mindlyapi.config;

import br.com.fiap.mindlyapi.repository.PacienteRepository;
import br.com.fiap.mindlyapi.repository.PsicologoRepository;
import br.com.fiap.mindlyapi.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final PacienteRepository pacienteRepository;
    private final PsicologoRepository psicologoRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return path.startsWith("/api/auth/")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.equals("/swagger-ui.html")
                || path.startsWith("/actuator/health");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                String email = tokenService.validarToken(token);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    var pacienteOpt = pacienteRepository.findByEmail(email);

                    if (pacienteOpt.isPresent()) {
                        var auth = new UsernamePasswordAuthenticationToken(
                                pacienteOpt.get(),
                                null,
                                List.of()
                        );
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    } else {
                        var psicologoOpt = psicologoRepository.findByEmail(email);
                        if (psicologoOpt.isPresent()) {
                            var auth = new UsernamePasswordAuthenticationToken(
                                    psicologoOpt.get(),
                                    null,
                                    List.of()
                            );
                            SecurityContextHolder.getContext().setAuthentication(auth);
                        }
                    }
                }

            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
