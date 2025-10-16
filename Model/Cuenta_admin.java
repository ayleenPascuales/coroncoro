package Model;

public class Cuenta_admin extends Usuario {
    
    private String permisos;

    public Cuenta_admin(String nombre, String apellido, String documento, String edad, String telefono, String email,
            String direccion, String usuario, String contraseña, String permisos) {
        super(nombre, apellido, documento, edad, telefono, email, direccion, usuario, contraseña);
        this.permisos = permisos;
    }

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }
}
