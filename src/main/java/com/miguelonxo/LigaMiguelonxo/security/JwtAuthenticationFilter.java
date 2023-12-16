package com.miguelonxo.LigaMiguelonxo.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    /* @Autowired
    private RolRepository rolRepository; */

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        String token = request.getHeader("Authorization");
        
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            System.out.println("Token sin bearer: "+ token);
            System.out.println("Token valido: "+ jwtTokenUtil.validateToken(token));
            if (jwtTokenUtil.validateToken(token)) {
                Claims claims = Jwts.parserBuilder().setSigningKey(jwtTokenUtil.getSecretKey().getBytes()).build().parseClaimsJws(token).getBody();
                String username = claims.getSubject();
                List<String> roles = claims.get("roles", List.class);


                //imprimir lista de roles
                System.out.println("Roles: "+ roles);

                List<GrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    /* @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                if(!hasAuthorizationBearer(request)){
                    filterChain.doFilter(request, response);
                    return;
                }

                String token = getAccessToken(request);

                if(!jwtUtil.validateAccessToken(token)){
                    filterChain.doFilter(request, response);
                    return;
                }

                setAuthenticationContext(token, request);
                filterChain.doFilter(request, response);
    }

    private boolean hasAuthorizationBearer(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")){
            return false;
        }
        return true;
    }

    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        return token;
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
       UserDetails userDetails = getUserDetails(token);

       UsernamePasswordAuthenticationToken
         authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails getUserDetails(String token) {
        System.out.println("Token: " + token);
        User userDetails = new User();
        Claims claims = jwtUtil.parseClaims(token);
        System.out.println("Tipo de claims.get('roles'): " + claims.get("roles").getClass().getName());
        System.out.println("Contenido de roles: " + claims.get("roles"));
        String subject = (String) claims.get(Claims.SUBJECT);
        List<String> roles = (List<String>) claims.get("roles");


        
        

        for (String roleName: roles){
            Rol role = rolRepository.findByNombre(roleName);
            System.out.println("El rol " + roleName + " se encontró en la base de datos.");
            if(role != null){
                UserRol userRole = new UserRol(userDetails, role);
                userDetails.addRol(userRole);
            }else{
                System.out.println("El rol " + roleName + " no se encontró en la base de datos.");
            }
        }

        String[] jwtSubject = subject.split(",");

        
        
        userDetails.setId(Long.parseLong(jwtSubject[0]));
        userDetails.setEmail(jwtSubject[1]);
        return userDetails;
    } */
}
