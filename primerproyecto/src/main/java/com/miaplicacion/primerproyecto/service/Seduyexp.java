/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miaplicacion.primerproyecto.service;

import com.miaplicacion.primerproyecto.model.EduyExp;
import com.miaplicacion.primerproyecto.repository.REduyExp;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class Seduyexp {
    
     @Autowired
    REduyExp rEduyexp;
    
    public List<EduyExp> list(){
        return rEduyexp.findAll();
    }
    
    public Optional<EduyExp> getOne(int id){
        return rEduyexp.findById(id);
    }
    
    public Optional<EduyExp> getByNombreE(String nombreE){
        return rEduyexp.findByNombreE(nombreE);
    }
    
    public void save(EduyExp eduyexp){
        rEduyexp.save(eduyexp);
    }
    
    public void delete(int id){
        rEduyexp.deleteById(id);
    }
    
    public boolean existsById(int id){
        return rEduyexp.existsById(id);
    }
    
    public boolean existsByNombreE(String nombreE){
        return rEduyexp.existsByNombreE(nombreE);
    }
}
