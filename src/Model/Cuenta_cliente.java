package Model;

import java.time.LocalDate;

public class Cuenta_cliente extends Preferencias {

    private LocalDate fecha_registro;

    public Cuenta_cliente(LocalDate fecha_registro, String monedaPref, String idiomaPref, String nombre, String apellido, String documento, String edad, String telefono, String email, String Barrio, String direccion, LocalDate fecha_nacimiento, String idiomas, String ciudad_nacimiento, String usuario, String contraseña) {
        super(monedaPref, idiomaPref, nombre, apellido, documento, edad, telefono, email, Barrio, direccion, fecha_nacimiento, idiomas, ciudad_nacimiento, usuario, contraseña);
        this.fecha_registro = fecha_registro;
    }

    public LocalDate getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDate fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    
}
