/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Model.Cuenta_Anfitrion;
import Model.dao.AnfitrionDAO;
import Model.dao.AnfitrionDAOImpl;
import java.util.List;

/**
 *
 * @author aylee
 */
public class hostController {
    private final AnfitrionDAO anfitrionDAO;

    public hostController() {
        this.anfitrionDAO = new AnfitrionDAOImpl();
    }

    public void guardarAnfitrion(Cuenta_Anfitrion anfitrion) {
        anfitrionDAO.guardarAnfitrion(anfitrion);
    }

    public List<Cuenta_Anfitrion> listarAnfitriones() {
        return anfitrionDAO.cargarAnfitriones();
    }

    public Cuenta_Anfitrion buscarAnfitrion(String id) {
        return anfitrionDAO.buscarPorId(id);
    }

    public boolean eliminarAnfitrion(String id) {
        return anfitrionDAO.eliminarAnfitrion(id);
    }

    public boolean modificarAnfitrion(Cuenta_Anfitrion anfitrion) {
        return anfitrionDAO.modificarAnfitrion(anfitrion);
    }
}
