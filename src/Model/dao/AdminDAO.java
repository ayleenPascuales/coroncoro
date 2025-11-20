/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_admins;

/**
 *
 * @author aylee
 */
public interface AdminDAO {
    void guardarAdmins(Lista_admins lista);
    Lista_admins cargarAdmins();
}
