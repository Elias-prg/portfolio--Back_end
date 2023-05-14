
package com.miaplicacion.primerproyecto.Controller;

import com.miaplicacion.primerproyecto.model.Persona;
import com.miaplicacion.primerproyecto.service.IPersonaService;
import com.miaplicacion.primerproyecto.service.Roles;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RolesAllowed(Roles.USER)
@RestController
@RequestMapping("/api/persons") // Should be people!!!
public class PersonController {
    
      
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
    public void borrarPersona(@PathVariable Integer id){
        persoServi.borrarPersona(id);
    }
    
    @PutMapping("/personas/editar/{id}")
   public Persona editarPersona(@PathVariable Integer id,
                             @RequestParam ("nombre") String nuevoNombre,
                             @RequestParam ("apellido") String nuevoApellido,
                             @RequestParam ("descripcion") String nuevaDescripcion
                             ){
      return persoServi.editarPersona(id, nuevoNombre, nuevoApellido,nuevaDescripcion);
   }
}

