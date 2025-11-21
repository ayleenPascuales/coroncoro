/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_admins;
import Model.Cuenta_admin;
import java.util.List;

/**
 *
 * @author aylee
 */
public interface AdminDAO {
    void guardarAdmin(Cuenta_admin admin);
    List<Cuenta_admin> cargarAdmins();
    Cuenta_admin buscarPorId(String id);
    boolean eliminarAdmin(String id);
    boolean modificarAdmin(Cuenta_admin admin);
}
