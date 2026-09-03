package com.aprexter.hospitalmanagementsystem.security;

import com.aprexter.hospitalmanagementsystem.models.User;
import com.aprexter.hospitalmanagementsystem.repositry.UserRepositry;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver exceptionResolver;
    private final UserRepositry userRepositry;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            log.info("incoming request: {}", request.getRequestURI());
            final String authorizationHeader = request.getHeader("Authorization");
            if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
                filterChain.doFilter(request,response);
                return;
            }
            String token = authorizationHeader.substring(7);
            String username=jwtUtil.getUsernameFromToken(token);
            if(username==null && SecurityContextHolder.getContext().getAuthentication()==null){
                User user = userRepositry.findByUsername(username)
                        .orElseThrow();

                if (jwtUtil.isTokenValid(token, user)) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    user.getAuthorities()
                            );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request,response);
        }catch(Exception e){
            exceptionResolver.resolveException(request,response,null,e);
        }
    }
}
