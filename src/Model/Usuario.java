/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.UUID;

/**
 *
 * @author aylee
 */
public class Usuario {

    private String id_usuario;
    private String usuario;
    private String contraseña;
    private String tipoUsuario; // Nuevo campo para el rol (HUESPED, ADMIN, HOST)

    // Constructor actualizado: Pide usuario, contraseña y el tipo
    public Usuario(String usuario, String contraseña, String tipoUsuario) {
        // Genera ID único automáticamente al crear el objeto
        this.id_usuario = UUID.randomUUID().toString();
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    // Usualmente no se setea el ID manualmente si es autogenerado, 
    // pero lo dejamos por si necesitas cargar desde JSON
    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
