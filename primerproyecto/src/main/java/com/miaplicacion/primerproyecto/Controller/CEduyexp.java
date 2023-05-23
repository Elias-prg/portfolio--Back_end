
package com.miaplicacion.primerproyecto.Controller;

import com.miaplicacion.primerproyecto.Dto.DtoEduyexp;
import com.miaplicacion.primerproyecto.configuration.Controller.Mensaje;
import com.miaplicacion.primerproyecto.model.EduyExp;
import com.miaplicacion.primerproyecto.service.Seduyexp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.StringUtils;


@RestController
@RequestMapping("/info")
@CrossOrigin(origins = "hosting-proyectoa.web.app")
public class CEduyexp {
     @Autowired
    Seduyexp sEduyexp;
    
    @GetMapping("/lista")
    public ResponseEntity<List<EduyExp>> list(){
        List<EduyExp> list = sEduyexp.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<EduyExp> getById(@PathVariable("id")int id){
        if(!sEduyexp.existsById(id)){
            return new ResponseEntity(new Mensaje("No existe el ID"), HttpStatus.BAD_REQUEST);
        }
        
        EduyExp eduyexp = sEduyexp.getOne(id).get();
        return new ResponseEntity(eduyexp, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        if(!sEduyexp.existsById(id)){
            return new ResponseEntity(new Mensaje("No existe el ID"), HttpStatus.NOT_FOUND);
        }
        sEduyexp.delete(id);
        return new ResponseEntity(new Mensaje("Educacion eliminada"), HttpStatus.OK);
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DtoEduyexp dtoeduyexp){
        if(StringUtils.isBlank(dtoeduyexp.getNombreE())){
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if(sEduyexp.existsByNombreE(dtoeduyexp.getNombreE())){
            return new ResponseEntity(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
        
        EduyExp educacion = new EduyExp(
                dtoeduyexp.getNombreE(), dtoeduyexp.getDescripcionE()
            );
        sEduyexp.save(educacion);
        return new ResponseEntity(new Mensaje("Educacion creada"), HttpStatus.OK);
                
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody DtoEduyexp dtoeduyexp){
        if(!sEduyexp.existsById(id)){
            return new ResponseEntity(new Mensaje("No existe el ID"), HttpStatus.NOT_FOUND);
        }
        if(sEduyexp.existsByNombreE(dtoeduyexp.getNombreE()) && sEduyexp.getByNombreE(dtoeduyexp.getNombreE()).get().getId() != id){
            return new ResponseEntity(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(dtoeduyexp.getNombreE())){
            return new ResponseEntity(new Mensaje("El campo no puede estar vacio"), HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isBlank(dtoeduyexp.getDescripcionE())){
             return new ResponseEntity(new Mensaje("El campo no puede estar vacio"), HttpStatus.BAD_REQUEST);
        }
        
        EduyExp eduyexp = sEduyexp.getOne(id).get();
        
        eduyexp.setNombreE(dtoeduyexp.getNombreE());
        eduyexp.setDescripcionE(dtoeduyexp.getDescripcionE());
        
        sEduyexp.save(eduyexp);
        
        return new ResponseEntity(new Mensaje("Eduyexp actualizada"), HttpStatus.OK);
    }
}
