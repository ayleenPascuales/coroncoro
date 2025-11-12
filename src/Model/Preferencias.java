/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;

/**
 *
 * @author aylee
 */
public class Preferencias extends Usuario {
    
    private String monedaPref;
    private String idiomaPref;

    public Preferencias(String monedaPref, String idiomaPref, String nombre, String apellido, String documento, String edad, String telefono, String email, String Barrio, String direccion, LocalDate fecha_nacimiento, String idiomas, String ciudad_nacimiento, String usuario, String contraseña) {
        super(nombre, apellido, documento, edad, telefono, email, Barrio, direccion, fecha_nacimiento, idiomas, ciudad_nacimiento, usuario, contraseña);
        this.monedaPref = monedaPref;
        this.idiomaPref = idiomaPref;
    }

    public String getMonedaPref() {
        return monedaPref;
    }

    public void setMonedaPref(String monedaPref) {
        this.monedaPref = monedaPref;
    }

    public String getIdiomaPref() {
        return idiomaPref;
    }

    public void setIdiomaPref(String idiomaPref) {
        this.idiomaPref = idiomaPref;
    }

     
}
