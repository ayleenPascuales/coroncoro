package Model;

public class Alojamiento {

    private String num_casa;
    private String pais;
    private String ciudad;
    private String barrio;
    private String direccion;
    private String descripcion;
    private String capacidad_maxima;
    private String num_habitaciones;
    private String num_baños;
    private String tipo_vivienda;
    private String fotos;
    private boolean disponibilidad;
    private boolean piscina;
    private boolean parrilla;
    private boolean mascotas;
    private boolean parques;
    private boolean balcon;
    private boolean agua_caliente;
    private boolean personas_poca_movilidad;
    private boolean vigilancia;
    private boolean conjunto_cerrado;
    private double precio_noche;

    public Alojamiento(String num_casa, String barrio, String direccion, String descripcion, String capacidad_maxima, String num_habitaciones, String num_baños, String tipo_vivienda, boolean disponibilidad, double precio_noche) {
        this.num_casa = num_casa;
        this.barrio = barrio;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.capacidad_maxima = capacidad_maxima;
        this.num_habitaciones = num_habitaciones;
        this.num_baños = num_baños;
        this.tipo_vivienda = tipo_vivienda;
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

    public String getNum_habitaciones() {
        return num_habitaciones;
    }

    public void setNum_habitaciones(String num_habitaciones) {
        this.num_habitaciones = num_habitaciones;
    }

    public String getNum_baños() {
        return num_baños;
    }

    public void setNum_baños(String num_baños) {
        this.num_baños = num_baños;
    }

    public String getTipo_vivienda() {
        return tipo_vivienda;
    }

    public void setTipo_vivienda(String tipo_vivienda) {
        this.tipo_vivienda = tipo_vivienda;
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
