package com.miaplicacion.primerproyecto.service;

import com.miaplicacion.primerproyecto.model.Persona;
import java.util.List;



//@Interface
public interface IPersonaService {
    
  public List<Persona> verPersonas();
    
    public void crearPersona(Persona per);
    
    public void  borrarPersona(Long id);
    
    public Persona buscarPersona(Long id);

    public Persona editarPersona(Long id, String nuevoNombre, String nuevoApellido,String nuevaDescripcion);
}
