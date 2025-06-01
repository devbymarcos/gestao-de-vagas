package br.com.devbymarcos.gestao_de_vagas.security;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.devbymarcos.gestao_de_vagas.providers.JWTProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
   
   @Autowired
   private JWTProvider jwtProvider;
   
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // SecurityContextHolder.getContext().setAuthentication(null);       
        String header = request.getHeader("Authorization");
       

         if(header != null && header.startsWith("Bearer ", 0)){
            var token  = header.substring(7);
            var subjectToken = this.jwtProvider.validateToken(token);
           
            if(subjectToken.isEmpty()){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            request.setAttribute("company_id", subjectToken);
            var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subjectToken,null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
         }
        filterChain.doFilter(request, response);
    }
}
 

