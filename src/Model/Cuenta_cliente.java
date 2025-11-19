package Model;

import java.time.LocalDate;

public class Cuenta_cliente extends Preferencias {

    private String favoritos;

    public Cuenta_cliente(String monedaPref, String idiomaPref, String nombre, String apellido, String documento, String edad, String telefono, String email, String Barrio, String direccion, LocalDate fecha_nacimiento, String idiomas, String ciudad_vivienda) {
        super(monedaPref, idiomaPref, nombre, apellido, documento, edad, telefono, email, Barrio, direccion, fecha_nacimiento, idiomas, ciudad_vivienda);
    }

    public String getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(String favoritos) {
        this.favoritos = favoritos;
    }   
}
