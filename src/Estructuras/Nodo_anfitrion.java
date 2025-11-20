package Estructuras;
import Model.Cuenta_Anfitrion;

public class Nodo_anfitrion {
    private Cuenta_Anfitrion dato;
    private Nodo_anfitrion siguiente;
    private Nodo_anfitrion anterior;
    public Nodo_anfitrion(Cuenta_Anfitrion dato) {
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }
    public Cuenta_Anfitrion getDato() {
        return dato;
    }
    public void setDato(Cuenta_Anfitrion dato) {
        this.dato = dato;
    }
    public Nodo_anfitrion getSiguiente() {
        return siguiente;
    }
    public void setSiguiente(Nodo_anfitrion siguiente) {
        this.siguiente = siguiente;
    }
    public Nodo_anfitrion getAnterior() {
        return anterior;
    }
    public void setAnterior(Nodo_anfitrion anterior) {
        this.anterior = anterior;
    }
    
    
}
