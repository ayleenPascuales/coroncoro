package Model;

public class Reseñas {

    private Reseñas reseña;
    private Cuenta_cliente cliente;
    private String calificacion;
    private String critica;
    
    public Reseñas(Reseñas reseña, Cuenta_cliente cliente, String calificacion, String critica) {
        this.reseña = reseña;
        this.cliente = cliente;
        this.calificacion = calificacion;
        this.critica = critica;
    }

    public Reseñas getReseña() {
        return reseña;
    }

    public void setReseña(Reseñas reseña) {
        this.reseña = reseña;
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