package Model;

import java.time.*;
import java.util.UUID;

public class Reservas {

    private String num_reserva;
    private String nombre_cliente;
    private String apellido_cliente;
    private String documento_cliente;
    private String telefono_cliente;
    private String email_cliente;
    private LocalDate dia_entrada;
    private LocalDate dia_salida;
    private LocalTime hora_entrada;
    private LocalTime hora_salida;


    public Reservas(String nombre_cliente,
            String apellido_cliente, String documento_cliente, String telefono_cliente, String email_cliente,
            LocalDate dia_entrada, LocalDate dia_salida, LocalTime hora_entrada, LocalTime hora_salida) {
        this.num_reserva = UUID.randomUUID().toString();
        this.nombre_cliente = nombre_cliente;
        this.apellido_cliente = apellido_cliente;
        this.documento_cliente = documento_cliente;
        this.telefono_cliente = telefono_cliente;
        this.email_cliente = email_cliente;
        this.dia_entrada = dia_entrada;
        this.dia_salida = dia_salida;
        this.hora_entrada = hora_entrada;
        this.hora_salida = hora_salida;
    }

    public String getNum_reserva() {
        return num_reserva;
    }

    public void setNum_reserva(String num_reserva) {
        this.num_reserva = num_reserva;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getApellido_cliente() {
        return apellido_cliente;
    }

    public void setApellido_cliente(String apellido_cliente) {
        this.apellido_cliente = apellido_cliente;
    }

    public String getDocumento_cliente() {
        return documento_cliente;
    }

    public void setDocumento_cliente(String documento_cliente) {
        this.documento_cliente = documento_cliente;
    }

    public String getTelefono_cliente() {
        return telefono_cliente;
    }

    public void setTelefono_cliente(String telefono_cliente) {
        this.telefono_cliente = telefono_cliente;
    }

    public String getEmail_cliente() {
        return email_cliente;
    }

    public void setEmail_cliente(String email_cliente) {
        this.email_cliente = email_cliente;
    }

    public LocalDate getDia_entrada() {
        return dia_entrada;
    }

    public void setDia_entrada(LocalDate dia_entrada) {
        this.dia_entrada = dia_entrada;
    }

    public LocalDate getDia_salida() {
        return dia_salida;
    }

    public void setDia_salida(LocalDate dia_salida) {
        this.dia_salida = dia_salida;
    }

    public LocalTime getHora_entrada() {
        return hora_entrada;
    }

    public void setHora_entrada(LocalTime hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public LocalTime getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(LocalTime hora_salida) {
        this.hora_salida = hora_salida;
    }

   

}