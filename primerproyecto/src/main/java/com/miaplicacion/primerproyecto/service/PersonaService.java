package com.miaplicacion.primerproyecto.service;

import com.miaplicacion.primerproyecto.model.Persona;
import com.miaplicacion.primerproyecto.repository.PersonaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PersonaService implements IPersonaService {

    
    @Autowired
    public PersonaRepository persoRepo ;
    
    @Override
    public List<Persona> verPersonas() {
       return persoRepo.findAll();
    }

    @Override
    public void crearPersona(Persona per) {
       persoRepo.save( per);
    }

    @Override
    public void borrarPersona(Long id) {
       persoRepo.deleteById(id);
    }

    @Override
    public Persona buscarPersona(Long id) {
        return persoRepo.findById(id).orElse(null);
    }
    
    @Override
    public Persona editarPersona(Long id, String nuevoNombre, String nuevoApellido) {
        // busco a la persona en cuestion
        Persona per = buscarPersona(id);

        // actualizo los datos de la persona
        per.setApellido(nuevoApellido);
        per.setNombre(nuevoNombre);

        // guardo los cambios en la base de datos
       crearPersona(per);

        // retorno la nueva persona
        return per;
    }
    
    
}
