/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.dao;


import Model.Alojamiento;
import java.util.List;

/**
 *
 * @author aylee
 */
public interface AlojamientoDAO {

    void guardarAlojamientos(Alojamiento alojamientos);
    List<Alojamiento> cargarAlojamientos();
    Alojamiento BuscarPorId(String id);
    boolean eliminarAlojamiento(String id);
    boolean modificarAlojamiento(Alojamiento alojamientos);
    
}
