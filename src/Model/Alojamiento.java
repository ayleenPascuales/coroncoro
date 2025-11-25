package Model;

import java.util.ArrayList;
import java.util.List;

public class Alojamiento {
    private String id_alojamiento;
    private String titulo_publicacion;
    private String pais;
    private String ciudad;
    private String barrio;
    private String direccion;
    private String descripcion;
    private String capacidad_maxima;
    private String num_habitaciones;
    private String num_baños;
    private String tipo_vivienda;
    private List<String> fotos;
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
    private String precio_noche;

    public Alojamiento(String id_alojamiento, String titulo_publicacion, String pais, String ciudad, String barrio, String direccion, String descripcion, String capacidad_maxima, String num_habitaciones, String num_baños, String tipo_vivienda, List<String> fotos, boolean disponibilidad, boolean piscina, boolean parrilla, boolean mascotas, boolean parques, boolean balcon, boolean agua_caliente, boolean personas_poca_movilidad, boolean vigilancia, boolean conjunto_cerrado, String precio_noche) {
        this.id_alojamiento = id_alojamiento;
        this.titulo_publicacion = titulo_publicacion;
        this.pais = pais;
        this.ciudad = ciudad;
        this.barrio = barrio;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.capacidad_maxima = capacidad_maxima;
        this.num_habitaciones = num_habitaciones;
        this.num_baños = num_baños;
        this.tipo_vivienda = tipo_vivienda;
        this.fotos = fotos;
        this.disponibilidad = disponibilidad;
        this.piscina = piscina;
        this.parrilla = parrilla;
        this.mascotas = mascotas;
        this.parques = parques;
        this.balcon = balcon;
        this.agua_caliente = agua_caliente;
        this.personas_poca_movilidad = personas_poca_movilidad;
        this.vigilancia = vigilancia;
        this.conjunto_cerrado = conjunto_cerrado;
        this.precio_noche = precio_noche;
    }

    public String getId_alojamiento() {
        return id_alojamiento;
    }

    public void setId_alojamiento(String id_alojamiento) {
        this.id_alojamiento = id_alojamiento;
    }

    public String getTitulo_publicacion() {
        return titulo_publicacion;
    }

    public void setTitulo_publicacion(String titulo_publicacion) {
        this.titulo_publicacion = titulo_publicacion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
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

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public boolean isPiscina() {
        return piscina;
    }

    public void setPiscina(boolean piscina) {
        this.piscina = piscina;
    }

    public boolean isParrilla() {
        return parrilla;
    }

    public void setParrilla(boolean parrilla) {
        this.parrilla = parrilla;
    }

    public boolean isMascotas() {
        return mascotas;
    }

    public void setMascotas(boolean mascotas) {
        this.mascotas = mascotas;
    }

    public boolean isParques() {
        return parques;
    }

    public void setParques(boolean parques) {
        this.parques = parques;
    }

    public boolean isBalcon() {
        return balcon;
    }

    public void setBalcon(boolean balcon) {
        this.balcon = balcon;
    }

    public boolean isAgua_caliente() {
        return agua_caliente;
    }

    public void setAgua_caliente(boolean agua_caliente) {
        this.agua_caliente = agua_caliente;
    }

    public boolean isPersonas_poca_movilidad() {
        return personas_poca_movilidad;
    }

    public void setPersonas_poca_movilidad(boolean personas_poca_movilidad) {
        this.personas_poca_movilidad = personas_poca_movilidad;
    }

    public boolean isVigilancia() {
        return vigilancia;
    }

    public void setVigilancia(boolean vigilancia) {
        this.vigilancia = vigilancia;
    }

    public boolean isConjunto_cerrado() {
        return conjunto_cerrado;
    }

    public void setConjunto_cerrado(boolean conjunto_cerrado) {
        this.conjunto_cerrado = conjunto_cerrado;
    }

    public String getPrecio_noche() {
        return precio_noche;
    }

    public void setPrecio_noche(String precio_noche) {
        this.precio_noche = precio_noche;
    }

            
}
