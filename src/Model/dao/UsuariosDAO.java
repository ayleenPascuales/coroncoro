/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.dao;

import Model.Usuario;
import java.util.List;

/**
 *
 * @author aylee
 */
public interface UsuariosDAO {
    void guardarUsuario(Usuario usuario);
    List<Usuario> cargarUsuarios();
    Usuario buscarPorId(String id);
    boolean eliminarUsuario(String id);
    boolean modificarUsuario(Usuario usuario);
}
