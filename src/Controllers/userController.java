/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Model.Usuario;
import Model.dao.UsuariosDAO;
import Model.dao.UsuariosDAOImpl;
import java.util.List;

/**
 *
 * @author aylee
 */
public class userController {

    private final UsuariosDAO usuarioDAO;

    public userController() {
        this.usuarioDAO = new UsuariosDAOImpl(); // conecta con la lista y JSON
    }

    public Usuario login(String username, String password) {
        List<Usuario> usuarios = usuarioDAO.cargarUsuarios();

        for (Usuario u : usuarios) {
            if (u.getUsuario() != null && u.getContraseña() != null) {
            if (username.equals(u.getUsuario())
                    && password.equals(u.getContraseña())) {
                return u;
            }
        }
        }

        return null;
    }

    public boolean registrarUsuario(String username, String password, String tipo_u) {
        // Verificar si ya existe
        List<Usuario> usuarios = usuarioDAO.cargarUsuarios();
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(username)) {
                return false; // usuario ya existe
            }
        }
        Usuario nuevo = new Usuario(username, password, tipo_u);
        usuarioDAO.guardarUsuario(nuevo);
        return true;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.cargarUsuarios();
    }

    public Usuario buscarUsuario(String id) {
        return usuarioDAO.buscarPorId(id);
    }

    public boolean eliminarUsuario(String id) {
        return usuarioDAO.eliminarUsuario(id);
    }

    public boolean modificarUsuario(Usuario usuario) {
        return usuarioDAO.modificarUsuario(usuario);
    }

    // Agrega este método a tu userController.java
    public boolean registrarUsuarioCompleto(Usuario nuevoUsuario) {
        // Busca por Documento (asumiendo que es el ID clave en el DAO)
        if (buscarUsuario(nuevoUsuario.getId_usuario()) != null) {
            return false; // El Documento ya existe
        }
        // Opcional: Verificar si el username ya existe también si son diferentes

        usuarioDAO.guardarUsuario(nuevoUsuario);
        return true;
    }
}
