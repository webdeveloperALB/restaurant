package al.student.security;

import al.student.model.Perdoruesi;
import al.student.repository.PerdoruesiRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final PerdoruesiRepository perdoruesiRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, PerdoruesiRepository perdoruesiRepository) {
        this.jwtUtil = jwtUtil;
        this.perdoruesiRepository = perdoruesiRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String email = jwtUtil.extractEmail(token);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    Optional<Perdoruesi> perdoruesiOpt = perdoruesiRepository.findByEmail(email);

                    if (perdoruesiOpt.isPresent() && jwtUtil.validateToken(token, email)) {
                        Perdoruesi perdoruesi = perdoruesiOpt.get();
                        String role = perdoruesi.getRole().getEmri();

                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                perdoruesi,
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())));

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }
}
