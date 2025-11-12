package Model;

import java.time.LocalDate;
import java.util.List;

public class Cuenta_anfitrion extends Preferencias {

    private LocalDate fecha_inicio; //fecha en que creo su primera publicacion
    private boolean estado;
    private String calificacion;
    private List<Alojamiento> listaArriendos;
    private List<Reservas> reservasArriendos;
    private List<Reseñas> reseñasArriendos;

    public Cuenta_anfitrion(LocalDate fecha_inicio, boolean estado, String calificacion, List<Alojamiento> listaArriendos, List<Reservas> reservasArriendos, List<Reseñas> reseñasArriendos, String monedaPref, String idiomaPref, String nombre, String apellido, String documento, String edad, String telefono, String email, String Barrio, String direccion, LocalDate fecha_nacimiento, String idiomas, String ciudad_nacimiento, String usuario, String contraseña) {
        super(monedaPref, idiomaPref, nombre, apellido, documento, edad, telefono, email, Barrio, direccion, fecha_nacimiento, idiomas, ciudad_nacimiento, usuario, contraseña);
        this.fecha_inicio = fecha_inicio;
        this.estado = estado;
        this.calificacion = calificacion;
        this.listaArriendos = listaArriendos;
        this.reservasArriendos = reservasArriendos;
        this.reseñasArriendos = reseñasArriendos;
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

    public List<Alojamiento> getListaArriendos() {
        return listaArriendos;
    }

    public void setListaArriendos(List<Alojamiento> listaArriendos) {
        this.listaArriendos = listaArriendos;
    }

    public List<Reservas> getReservasArriendos() {
        return reservasArriendos;
    }

    public void setReservasArriendos(List<Reservas> reservasArriendos) {
        this.reservasArriendos = reservasArriendos;
    }

    public List<Reseñas> getReseñasArriendos() {
        return reseñasArriendos;
    }

    public void setReseñasArriendos(List<Reseñas> reseñasArriendos) {
        this.reseñasArriendos = reseñasArriendos;
    }

      
}
