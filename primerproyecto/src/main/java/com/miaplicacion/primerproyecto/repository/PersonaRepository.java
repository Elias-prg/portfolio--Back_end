
package com.miaplicacion.primerproyecto.repository;

import com.miaplicacion.primerproyecto.model.Persona;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonaRepository extends JpaRepository<Persona , Integer> {
    
    
    
}
