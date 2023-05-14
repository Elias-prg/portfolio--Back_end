package com.miaplicacion.primerproyecto.service;

import com.miaplicacion.primerproyecto.model.Persona;
import java.util.List;



//@Interface
public interface IPersonaService {
    
  public List<Persona> verPersonas();
    
    public void crearPersona(Persona per);
    
    public void  borrarPersona(Integer id);
    
    public Persona buscarPersona(Integer id);

    public Persona editarPersona(Integer id, String nuevoNombre, String nuevoApellido,String nuevaDescripcion);
}
