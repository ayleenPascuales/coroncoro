package Model;

public class Cuenta_cliente extends Usuario {

    private String fecha_nacimiento;

    public Cuenta_cliente(String nombre, String apellido, String documento, String edad,
            String telefono, String email, String direccion,
            String usuario, String contraseña, String fecha_nacimiento) {
        super(nombre, apellido, documento, edad, telefono, email, direccion, usuario, contraseña);
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

}
