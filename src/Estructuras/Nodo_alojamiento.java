package Estructuras;
import Model.Alojamiento;

public class Nodo_alojamiento {
    private Alojamiento dato;
    private Nodo_alojamiento anterior;
    private Nodo_alojamiento siguiente;
    
    public Nodo_alojamiento(Alojamiento dato) {
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }

    public Alojamiento getDato() {
        return dato;
    }

    public void setDato(Alojamiento dato) {
        this.dato = dato;
    }

    public Nodo_alojamiento getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo_alojamiento anterior) {
        this.anterior = anterior;
    }

    public Nodo_alojamiento getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo_alojamiento siguiente) {
        this.siguiente = siguiente;
    }
    
    
}
