
package com.miaplicacion.primerproyecto.configuration.Controller;

import com.miaplicacion.primerproyecto.configuration.Entity.Rol;
import com.miaplicacion.primerproyecto.configuration.Entity.Usuario;
import com.miaplicacion.primerproyecto.configuration.Service.RolService;
import com.miaplicacion.primerproyecto.configuration.Service.UsuarioService;
import com.miaplicacion.primerproyecto.configuration.enums.RolNombre;
import com.miaplicacion.primerproyecto.configuration.jwt.JwtProvider;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthController {
 
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsuarioService  usuarioService;
    @Autowired
    RolService rolService ;
    @Autowired
    JwtProvider jwtProvider ;      
    
    
    //para crea un nuevo Usuario
    @PostMapping("/nuevoUs")
    public ResponseEntity<?>nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario,BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email invalid"),HttpStatus.BAD_REQUEST);
        
        if(usuarioService.existsByNombreUsuario(nombreUsuario.getNombreUsuario))
             return new ResponseEntity(new Mensaje("ese nombre ya existe"),HttpStatus.BAD_REQUEST);
        
        if(usuarioService.existsByEmail(nombreUsuario.getEmail))
             return new ResponseEntity(new Mensaje("ese email ya existe"),HttpStatus.BAD_REQUEST);
        
        Usuario usuario = new Usuario(nuevoUsuario.getNombre(),nuevoUsuario.getNombreUsuario(),
                nuevoUsuario.getEmail(),passwordEncoder.encode(nuevoUsuario.getPassqord()));
        
        Set<Rol> roles = new HAset<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        
        if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        
        return new ResponseEntity(new Mensaje("Usuario guardado"),HttpStatus.CREATED);
        
    }
    @PostMapping("/login")
    public ResponseEntity<JwtDTO>login(@Valid @RequestBody LoginUsuario loginUsuario,BindingResult bindingResult){
        if(bindingResult.hasErrors())
             return new ResponseEntity(new Mensaje("Campos mal puestos"),HttpStatus.BAD_REQUEST);
        
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginUsuario.getNombreUsuario(),loginUsuario.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String jwt = jwtProvider.generateToken(authentication);
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        JwtDto jwtDto = new JwtDto (jwt, userDetails.getUsername(), userDetails.getAuthorities());
        
        return new ResponseEntity(jwtDto,HttpStatus.OK);
    }
}
