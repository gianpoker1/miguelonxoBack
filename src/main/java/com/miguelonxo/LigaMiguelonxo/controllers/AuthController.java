package com.miguelonxo.LigaMiguelonxo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miguelonxo.LigaMiguelonxo.entities.Admin;
import com.miguelonxo.LigaMiguelonxo.entities.AuthRequest;
import com.miguelonxo.LigaMiguelonxo.entities.AuthResponse;
import com.miguelonxo.LigaMiguelonxo.entities.CustomUserDetails;
import com.miguelonxo.LigaMiguelonxo.entities.Player;
import com.miguelonxo.LigaMiguelonxo.entities.RegistroRequest;
import com.miguelonxo.LigaMiguelonxo.entities.User;
import com.miguelonxo.LigaMiguelonxo.entities.UserRol;
import com.miguelonxo.LigaMiguelonxo.repositories.UserRepository;
import com.miguelonxo.LigaMiguelonxo.repositories.UserRolRepository;
import com.miguelonxo.LigaMiguelonxo.security.JwtTokenUtil;
import com.miguelonxo.LigaMiguelonxo.services.AdminService;
import com.miguelonxo.LigaMiguelonxo.services.PlayerService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    /* @Autowired
    AuthenticationManager authManager; */

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRolRepository userRolRepository;

    /* @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            Authentication authentication = (Authentication) authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(), authRequest.getPassword())
                );
                User user = (User) authentication.getPrincipal();
                String accessToken = jwtTokenUtil.generateAccessToken(user);
                AuthResponse response = new AuthResponse(user.getUsername(), accessToken);

                return ResponseEntity.ok().body(response);
            
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    } */

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        
        try {
            
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
            String token = jwtTokenUtil.generateToken(userDetails);

            AuthResponse authResponse = new AuthResponse(token);

            return ResponseEntity.ok(authResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    } 

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody RegistroRequest request){
        System.out.println("Request: " + request.toString());

        User usuario = request.getUsuario();
        String tipo = request.getTipo();

        //verificar si el usuario existe
        if(userRepository.existsByEmail(usuario.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya existe");
        }

        //Codificar la contraseña del usuario
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode(usuario.getPassword());
        usuario.setPassword(password);

        //guardar el nuevo usuario en la base de datos
        User savedUser = userRepository.save(usuario);

        //crear Admin
        if(!tipo.isEmpty()){
            Admin admin = new Admin();
            admin.setIdUsuario(savedUser.getId());
            admin.setTipo(tipo);
            adminService.save(admin);
            //Asignamos rol por defecto "ROLE_MANAGER"
            UserRol usuarioRol = new UserRol();
            usuarioRol.setIdUser(savedUser.getId());
            usuarioRol.setRol(1L);
            userRolRepository.save(usuarioRol);
        }//Crear PLAYER
        else{
            Player player = new Player();
            player.setIdUser(savedUser.getId());
            playerService.save(player);
            //Asignamos rol por defecto "ROLE_PLAYER"
            UserRol userRol = new UserRol();
            userRol.setIdUser(savedUser.getId());
            userRol.setRol(2L);
            userRolRepository.save(userRol);
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(savedUser);

        //Generar el token JWT utilizando los detalles de Usuario
        String token = jwtTokenUtil.generateToken(customUserDetails);

        //Crear la respuesta de registro con el token
        AuthResponse authResponse = new AuthResponse(token);

        return ResponseEntity.ok(authResponse);
    }
    
}
