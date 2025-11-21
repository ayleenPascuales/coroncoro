/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Model.Reservas;
import Model.dao.ReservasDAO;
import Model.dao.ReservasDAOImpl;
import java.util.List;

/**
 *
 * @author aylee
 */
public class reservasController {
    private final ReservasDAO reservasDAO;

    public reservasController() {
        this.reservasDAO = new ReservasDAOImpl();
    }

    public void guardarReserva(Reservas reserva) {
        reservasDAO.guardarReserva(reserva);
    }

    public List<Reservas> listarReservas() {
        return reservasDAO.cargarReservas();
    }

    public Reservas buscarReserva(String id) {
        return reservasDAO.buscarPorId(id);
    }

    public boolean eliminarReserva(String id) {
        return reservasDAO.eliminarReserva(id);
    }

    public boolean modificarReserva(Reservas reserva) {
        return reservasDAO.modificarReserva(reserva);
    }
}
