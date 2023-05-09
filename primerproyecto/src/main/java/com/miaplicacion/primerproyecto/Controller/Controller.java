
package com.miaplicacion.primerproyecto.Controller;

import java.util.List;
import com.miaplicacion.primerproyecto.model.Persona;
import com.miaplicacion.primerproyecto.service.IPersonaService;
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


@RestController
public class Controller {
    
    @Autowired
    private IPersonaService persoServi ;
    
    @PostMapping("/new/persona")
    public void agregarPersona(@RequestBody Persona pers){
        persoServi.crearPersona(pers);
    }
    
    @GetMapping("/ver/personas")
    @ResponseBody
    public List<Persona> verPersona(){
      return persoServi.verPersonas() ;
    }
    @DeleteMapping("/eliminar/{id}")
    public void borrarPersona(@PathVariable Long id){
        persoServi.borrarPersona(id);
    }
    
    @PutMapping("/personas/editar/{id}")
   public Persona editarPersona(@PathVariable Long id,
                             @RequestParam ("nombre") String nuevoNombre,
                             @RequestParam ("apellido") String nuevoApellido
                             ){
      return persoServi.editarPersona(id, nuevoNombre, nuevoApellido);
   }
}
/*
@PutMapping("/cambiar/{id}")
   public void editarPersona(@PathVariable Long id,@RequestBody Persona pers){
       persoServi.crearPersona(pers);
   }
*/