/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Model.Reseñas;
import Model.dao.ReseñasDAO;
import Model.dao.ReseñasDAOImpl;
import java.util.List;

/**
 *
 * @author aylee
 */
public class reseñasController {
    private final ReseñasDAO reseñasDAO;

    public reseñasController() {
        this.reseñasDAO = new ReseñasDAOImpl();
    }

    public void guardarReseña(Reseñas reseña) {
        reseñasDAO.guardarReseña(reseña);
    }

    public List<Reseñas> listarReseñas() {
        return reseñasDAO.cargarReseñas();
    }

    public Reseñas buscarReseña(String id) {
        return reseñasDAO.buscarPorId(id);
    }

    public boolean eliminarReseña(String id) {
        return reseñasDAO.eliminarReseña(id);
    }

    public boolean modificarReseña(Reseñas reseña) {
        return reseñasDAO.modificarReseña(reseña);
    }
}
