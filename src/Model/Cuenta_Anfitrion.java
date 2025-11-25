package Model;

import java.time.LocalDate;
import java.util.List;

public class Cuenta_Anfitrion extends Persona {

    private LocalDate fecha_inicio; //fecha en que creo su primera publicacion
    private boolean estado;
    private String calificacion;

     public Cuenta_Anfitrion(String id_usuario, LocalDate fecha_inicio, boolean estado, String calificacion, String nombre, String apellido, String documento, String edad, String telefono, String email, String Barrio, String direccion, LocalDate fecha_nacimiento, String idiomas, String ciudad_vivienda) {
        super(nombre, apellido, documento, edad, telefono, email, Barrio, direccion, fecha_nacimiento, idiomas, ciudad_vivienda);
        this.fecha_inicio = fecha_inicio;
        this.estado = estado;
        this.calificacion = calificacion;
        this.id_usuario = id_usuario;
    }


    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }
}
