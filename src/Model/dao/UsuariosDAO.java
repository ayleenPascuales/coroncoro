/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_usuarios;

/**
 *
 * @author aylee
 */
public interface UsuariosDAO {
    void guardarUsuarios(Lista_usuarios lista);
    Lista_usuarios cargarUsuarios();
}
