/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Model.Alojamiento;
import Model.dao.AlojamientoDAO;
import Model.dao.AlojamientoDAOImpl;
import java.util.List;

/**
 *
 * @author aylee
 */
public class alojamientoController {
    private final AlojamientoDAO alojamientoDAO;

    public alojamientoController() {
        this.alojamientoDAO = new AlojamientoDAOImpl(); // conecta con Lista y JSON
    }

    public void guardarAlojamiento(Alojamiento alojamiento) {
        alojamientoDAO.guardarAlojamientos(alojamiento);
    }

    public List<Alojamiento> listarAlojamientos() {
        return alojamientoDAO.cargarAlojamientos();
    }

    public Alojamiento buscarAlojamiento(String id) {
        return alojamientoDAO.BuscarPorId(id);
    }

    public boolean eliminarAlojamiento(String id) {
        return alojamientoDAO.eliminarAlojamiento(id);
    }

    public boolean modificarAlojamiento(Alojamiento alojamiento) {
        return alojamientoDAO.modificarAlojamiento(alojamiento);
    }
}
