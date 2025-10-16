package Estructuras;

import Model.Usuario;

public class Nodo_usuarios {
    private Usuario dato;
    private Nodo_usuarios anterior;
    private Nodo_usuarios siguiente;
    
    public Nodo_usuarios(Usuario dato) {
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }

    public Usuario getDato() {
        return dato;
    }

    public void setDato(Usuario dato) {
        this.dato = dato;
    }

    public Nodo_usuarios getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo_usuarios anterior) {
        this.anterior = anterior;
    }

    public Nodo_usuarios getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo_usuarios siguiente) {
        this.siguiente = siguiente;
    }
   
}
