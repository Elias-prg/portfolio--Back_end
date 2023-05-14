/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miaplicacion.primerproyecto.configuration.jwt;

import com.miaplicacion.primerproyecto.configuration.Service.UserDetailsImpl;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtTokenFilter  extends OncePerRequestFilter{
    
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserDetailsImpl userDetailsServiceImpl ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try {
       String token  = getToken(request);
       if(token !=null && jwtProvider.validateToken(token)){
           String nombreUsuario = jwtProvider.getNombreUsuarioFromToken(token);
           UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(nombreUsuario);
           UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
           null,userDetails.getAuthorities());
           SecurityContextHolder.getContext().setAuthentication(auth);
          }
       }catch(Exception e){
           logger.error("Fallo el metodo doFilterInternal ");
          }
       filterChain.doFilter(request, response);
    }
    
    private String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Barrer"))
            return header.replace("Barrer","");
           return null;
    }
}
