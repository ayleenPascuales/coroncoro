package Model;

import java.time.LocalDate;
import java.util.List;

public class Cuenta_admin extends Persona {
    
    private boolean estado_cliente;
    private boolean estado_anfitrion;

    public Cuenta_admin(boolean estado_cliente, boolean estado_anfitrion, String nombre, String apellido, String documento, String edad, String telefono, String email, String Barrio, String direccion, LocalDate fecha_nacimiento, String idiomas, String ciudad_vivienda) {
        super(nombre, apellido, documento, edad, telefono, email, Barrio, direccion, fecha_nacimiento, idiomas, ciudad_vivienda);
        this.estado_cliente = estado_cliente;
        this.estado_anfitrion = estado_anfitrion;
    }

    public boolean isEstado_cliente() {
        return estado_cliente;
    }

    public void setEstado_cliente(boolean estado_cliente) {
        this.estado_cliente = estado_cliente;
    }

    public boolean isEstado_anfitrion() {
        return estado_anfitrion;
    }

    public void setEstado_anfitrion(boolean estado_anfitrion) {
        this.estado_anfitrion = estado_anfitrion;
    }   
}
