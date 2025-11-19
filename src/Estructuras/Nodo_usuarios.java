package Estructuras;

import Model.Persona;

public class Nodo_usuarios {
    private Persona dato;
    private Nodo_usuarios anterior;
    private Nodo_usuarios siguiente;
    
    public Nodo_usuarios(Persona dato) {
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }

    public Persona getDato() {
        return dato;
    }

    public void setDato(Persona dato) {
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
