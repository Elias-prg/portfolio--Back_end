
package com.miaplicacion.primerproyecto.repository;

import com.miaplicacion.primerproyecto.model.EduyExp;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface REduyExp extends JpaRepository<EduyExp, Integer>{
    public Optional<EduyExp> findByNombreE(String nombreE);
    public boolean existsByNombreE(String nombreE);
    
}

