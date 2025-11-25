package Model;

import java.time.LocalDate;

public class Cuenta_cliente extends Persona {

    private String favoritos;

     public Cuenta_cliente(String id_usuario, String nombre, String apellido, String documento, String edad, String telefono, String email, String Barrio, String direccion, LocalDate fecha_nacimiento, String idiomas, String ciudad_vivienda) {
        super(nombre, apellido, documento, edad, telefono, email, Barrio, direccion, fecha_nacimiento, idiomas, ciudad_vivienda);
        this.id_usuario = id_usuario;
    }

    public String getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(String favoritos) {
        this.favoritos = favoritos;
    }
    

    
}
