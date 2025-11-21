/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Model.Cuenta_admin;
import Model.dao.AdminDAO;
import Model.dao.AdminDAOImpl;
import java.util.List;

/**
 *
 * @author aylee
 */
public class adminController {
    private final AdminDAO adminDAO;

    public adminController() {
        this.adminDAO = new AdminDAOImpl();
    }

    public void guardarAdmin(Cuenta_admin admin) {
        adminDAO.guardarAdmin(admin);
    }

    public List<Cuenta_admin> listarAdmins() {
        return adminDAO.cargarAdmins();
    }

    public Cuenta_admin buscarAdmin(String id) {
        return adminDAO.buscarPorId(id);
    }

    public boolean eliminarAdmin(String id) {
        return adminDAO.eliminarAdmin(id);
    }

    public boolean modificarAdmin(Cuenta_admin admin) {
        return adminDAO.modificarAdmin(admin);
    }
}
