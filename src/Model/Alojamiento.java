package Model;

public class Alojamiento {

    private String num_casa;
    private String barrio;
    private String direccion;
    private String descripcion;
    private String capacidad_maxima;
    private boolean disponibilidad;
    private double precio_noche;
    
    public Alojamiento(String num_casa, String barrio, String direccion, String descripcion, String capacidad_maxima,
            boolean disponibilidad, double precio_noche) {
        this.num_casa = num_casa;
        this.barrio = barrio;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.capacidad_maxima = capacidad_maxima;
        this.disponibilidad = disponibilidad;
        this.precio_noche = precio_noche;
    }

    public String getNum_casa() {
        return num_casa;
    }

    public void setNum_casa(String num_casa) {
        this.num_casa = num_casa;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCapacidad_maxima() {
        return capacidad_maxima;
    }

    public void setCapacidad_maxima(String capacidad_maxima) {
        this.capacidad_maxima = capacidad_maxima;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public double getPrecio_noche() {
        return precio_noche;
    }

    public void setPrecio_noche(double precio_noche) {
        this.precio_noche = precio_noche;
    }
   
}
