/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;

/**
 *
 * @author aylee
 */
public class Favoritos {
    private String id_cliente;
    private String id_alojamiento;
    private LocalDate fecha_guardado;

    public Favoritos(String id_cliente, String id_alojamiento, LocalDate fecha_guardado) {
        this.id_cliente = id_cliente;
        this.id_alojamiento = id_alojamiento;
        this.fecha_guardado = fecha_guardado;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getId_alojamiento() {
        return id_alojamiento;
    }

    public void setId_alojamiento(String id_alojamiento) {
        this.id_alojamiento = id_alojamiento;
    }

    public LocalDate getFecha_guardado() {
        return fecha_guardado;
    }

    public void setFecha_guardado(LocalDate fecha_guardado) {
        this.fecha_guardado = fecha_guardado;
    }
    
    
}
