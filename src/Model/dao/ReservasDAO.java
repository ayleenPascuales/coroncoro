/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_reservas;
import Model.Reservas;
import java.util.List;

/**
 *
 * @author aylee
 */
public interface ReservasDAO {

    void guardarReserva(Reservas reserva);   
    List<Reservas> cargarReservas();    
    Reservas buscarPorId(String id);    
    boolean eliminarReserva(String id); 
    boolean modificarReserva(Reservas reserva);
}
