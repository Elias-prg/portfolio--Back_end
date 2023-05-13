
package com.miaplicacion.primerproyecto.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter @Setter
@Entity
public class Persona {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String apellido;
    private String descripcion;
    private String usuario ;
    private String pass ;
    
    
    public Persona(){
        
    }
    
    public Persona(Long id,String nombre,String apellido, String descripcion,String usuario, String pass){
        this.id = id ;
        this.nombre = nombre ;
        this.apellido = apellido ;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.pass = pass;
    }

}
