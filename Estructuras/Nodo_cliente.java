package Estructuras;
import Model.Cuenta_cliente;

public class Nodo_cliente {
    private Cuenta_cliente dato;
    private Nodo_cliente anterior;
    private Nodo_cliente siguiente;
   
    public Nodo_cliente(Cuenta_cliente dato) {
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }

    public Cuenta_cliente getDato() {
        return dato;
    }

    public void setDato(Cuenta_cliente dato) {
        this.dato = dato;
    }

    public Nodo_cliente getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo_cliente anterior) {
        this.anterior = anterior;
    }

    public Nodo_cliente getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo_cliente siguiente) {
        this.siguiente = siguiente;
    }   
}
