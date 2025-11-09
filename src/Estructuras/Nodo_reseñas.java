package Estructuras;
import Model.Reseñas;

public class Nodo_reseñas {
    private Reseñas dato;
    private Nodo_reseñas siguiente;
    
    public Nodo_reseñas(Reseñas dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public Reseñas getDato() {
        return dato;
    }

    public void setDato(Reseñas dato) {
        this.dato = dato;
    }

    public Nodo_reseñas getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo_reseñas siguiente) {
        this.siguiente = siguiente;
    }
}
