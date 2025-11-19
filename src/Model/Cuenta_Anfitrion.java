package Model;

import java.time.LocalDate;
import java.util.List;

public class Cuenta_anfitrion extends Preferencias {

    private LocalDate fecha_inicio; //fecha en que creo su primera publicacion
    private boolean estado;
    private String calificacion;

    public Cuenta_anfitrion(LocalDate fecha_inicio, boolean estado, String calificacion, String monedaPref, String idiomaPref, String nombre, String apellido, String documento, String edad, String telefono, String email, String Barrio, String direccion, LocalDate fecha_nacimiento, String idiomas, String ciudad_vivienda) {
        super(monedaPref, idiomaPref, nombre, apellido, documento, edad, telefono, email, Barrio, direccion, fecha_nacimiento, idiomas, ciudad_vivienda);
        this.fecha_inicio = fecha_inicio;
        this.estado = estado;
        this.calificacion = calificacion;
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