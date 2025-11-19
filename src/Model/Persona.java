package Model;

import java.time.LocalDate;

public class Persona{
    private String nombre;
    private String apellido;
    private String documento;
    private String edad;
    private String telefono;
    private String email;
    private String Barrio;
    private String direccion;
    private LocalDate fecha_nacimiento;
    private String idiomas;
    private String ciudad_vivienda;

    public Persona(String nombre, String apellido, String documento, String edad, String telefono, String email, String Barrio, String direccion, LocalDate fecha_nacimiento, String idiomas, String ciudad_vivienda) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.edad = edad;
        this.telefono = telefono;
        this.email = email;
        this.Barrio = Barrio;
        this.direccion = direccion;
        this.fecha_nacimiento = fecha_nacimiento;
        this.idiomas = idiomas;
        this.ciudad_vivienda = ciudad_vivienda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBarrio() {
        return Barrio;
    }

    public void setBarrio(String Barrio) {
        this.Barrio = Barrio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public String getCiudad_vivienda() {
        return ciudad_vivienda;
    }

    public void setCiudad_vivienda(String ciudad_vivienda) {
        this.ciudad_vivienda = ciudad_vivienda;
    }

    
}
