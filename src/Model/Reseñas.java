package Model;
import java.util.UUID;

public class Reseñas {
    
    private String id_reseña;
    private Cuenta_cliente cliente;
    private String calificacion;
    private String critica;

    public Reseñas(String id_reseña, Cuenta_cliente cliente, String calificacion, String critica) {
        this.id_reseña = UUID.randomUUID().toString();
        this.cliente = cliente;
        this.calificacion = calificacion;
        this.critica = critica;
    }

    public String getId_reseña() {
        return id_reseña;
    }

    public void setId_reseña(String id_reseña) {
        this.id_reseña = id_reseña;
    }

    public Cuenta_cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cuenta_cliente cliente) {
        this.cliente = cliente;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getCritica() {
        return critica;
    }

    public void setCritica(String critica) {
        this.critica = critica;
    }

         
}