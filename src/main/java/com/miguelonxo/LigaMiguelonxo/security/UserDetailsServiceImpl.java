package com.miguelonxo.LigaMiguelonxo.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.miguelonxo.LigaMiguelonxo.entities.Rol;
import com.miguelonxo.LigaMiguelonxo.entities.User;
import com.miguelonxo.LigaMiguelonxo.entities.UserRol;
import com.miguelonxo.LigaMiguelonxo.repositories.RolRepository;
import com.miguelonxo.LigaMiguelonxo.repositories.UserRepository;
import com.miguelonxo.LigaMiguelonxo.repositories.UserRolRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRolRepository userRolRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("Cargando detalles del usuario por correo electrónico: " + username);

        User usuario = userRepository.findByEmail(username);
        System.out.println("Usuario encontrado: " + usuario.getName());

        if(usuario == null){
            throw new UsernameNotFoundException("Usuario no encontrado");        }

        List <UserRol> usuarioRoles =  userRolRepository.findByIdUser(usuario.getId());

        List<Rol> roles = usuarioRoles.stream()
            .map(usuarioRol -> rolRepository.findById(usuarioRol.getRol())
            .orElseThrow(() -> new RuntimeException("Rol no encontrado")))
            .collect(Collectors.toList());

        // Imprimir los roles del usuario
        /* System.out.println("Roles del usuario:");
        for (UserRol usuarioRol : usuarioRoles) {
            Rol rol = rolRepository.findById(usuarioRol.getRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            System.out.println("- " + rol.getNombre());
        } */

        List<GrantedAuthority> authorities = roles.stream()
            .map(rol ->new SimpleGrantedAuthority(rol.getNombre()))
            .collect(Collectors.toList());

            /* List<GrantedAuthority> authorities = usuarioRoles.stream()
            .map(usuarioRol -> {
                Rol rol = rolRepository.findById(usuarioRol.getRol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
                return new SimpleGrantedAuthority(rol.getNombre());
            }) 
            .collect(Collectors.toList());*/

            // Imprimir el rol que se está comparando
        System.out.println("Rol que se está comparando:");
        for (GrantedAuthority authority : authorities) {
            System.out.println("- " + authority.getAuthority());
        }

        return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(), authorities);
        
    }
    
}
