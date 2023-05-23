
package com.miaplicacion.primerproyecto.Controller;

import com.miaplicacion.primerproyecto.model.Persona;
import com.miaplicacion.primerproyecto.service.IPersonaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



 

//@RestController
//@RequestMappin("/persona")
//@CrossOrigin(origins ="http://localhost:4200" o "hosting-proyectoa.web.app")
@RestController
public class PersonaController {
    
    @Autowired
    private IPersonaService persoServi ;
    
    //@PreAuthorize( "hasRole('ADMIN')")
    @PostMapping("/new/persona")
    public void agregarPersona(@RequestBody Persona pers){
        persoServi.crearPersona(pers);
    }
    
    @GetMapping("/ver/personas")
    @ResponseBody
    public List<Persona> verPersona(){
      return persoServi.verPersonas() ;
    }
    
   // @PreAuthorize( "hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public void borrarPersona(@PathVariable Integer id){
        persoServi.borrarPersona(id);
    }
    
   // @PreAuthorize( "hasRole('ADMIN') ")
    @PutMapping("/editar/personas/{id}")
   public Persona editarPersona(@PathVariable Integer id,
                             @RequestParam ("nombre") String nuevoNombre,
                             @RequestParam ("apellido") String nuevoApellido,
                             @RequestParam ("descripcion") String nuevaDescripcion
                   //        @RequestParam ("img") String nuevaImg
                             ){
      Persona persona = persoServi.buscarPersona(id);
      
      persona.setNombre(nuevoNombre);
      persona.setApellido(nuevoApellido);
      persona.setDescripcion(nuevaDescripcion);
    //  persona.setImg(nuevaImg);
    persoServi.crearPersona(persona);
    return persona ;
   }
}