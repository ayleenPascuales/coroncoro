package Model;

import java.time.LocalDate;
import java.util.List;

public class Cuenta_admin extends Usuario {
    
    private boolean estado_cliente;
    private boolean estado_anfitrion;
    private List<Cuenta_cliente> listaClientes;
    private List<Cuenta_anfitrion> listaHosters;

    public Cuenta_admin(boolean estado_cliente, boolean estado_anfitrion, List<Cuenta_cliente> listaClientes, List<Cuenta_anfitrion> listaHosters, String nombre, String apellido, String documento, String edad, String telefono, String email, String Barrio, String direccion, LocalDate fecha_nacimiento, String idiomas, String ciudad_nacimiento, String usuario, String contraseña) {
        super(nombre, apellido, documento, edad, telefono, email, Barrio, direccion, fecha_nacimiento, idiomas, ciudad_nacimiento, usuario, contraseña);
        this.estado_cliente = estado_cliente;
        this.estado_anfitrion = estado_anfitrion;
        this.listaClientes = listaClientes;
        this.listaHosters = listaHosters;
    }

    public boolean isEstado_cliente() {
        return estado_cliente;
    }

    public void setEstado_cliente(boolean estado_cliente) {
        this.estado_cliente = estado_cliente;
    }

    public boolean isEstado_anfitrion() {
        return estado_anfitrion;
    }

    public void setEstado_anfitrion(boolean estado_anfitrion) {
        this.estado_anfitrion = estado_anfitrion;
    }

    public List<Cuenta_cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cuenta_cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public List<Cuenta_anfitrion> getListaHosters() {
        return listaHosters;
    }

    public void setListaHosters(List<Cuenta_anfitrion> listaHosters) {
        this.listaHosters = listaHosters;
    }

    
}
