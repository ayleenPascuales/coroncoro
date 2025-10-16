package Model;

public class Cuenta_Anfitrion extends Usuario {

    private String cargo;
    private String experiencia;
    private String horario;
    
    public Cuenta_Anfitrion(String nombre, String apellido, String documento, String edad, String telefono,
            String email, String direccion, String usuario, String contraseña, String cargo, String experiencia,
            String horario) {
        super(nombre, apellido, documento, edad, telefono, email, direccion, usuario, contraseña);
        this.cargo = cargo;
        this.experiencia = experiencia;
        this.horario = horario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    

    
    
}
