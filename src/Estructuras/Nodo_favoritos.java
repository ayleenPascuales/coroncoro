/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Model.Favoritos;

/**
 *
 * @author aylee
 */
public class Nodo_favoritos {
    private Favoritos dato;
    private Nodo_favoritos siguiente;
    private Nodo_favoritos anterior;

    public Nodo_favoritos(Favoritos dato) {
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }

    // Getters y Setters
    public Favoritos getDato() {
        return dato;
    }

    public void setDato(Favoritos dato) {
        this.dato = dato;
    }

    public Nodo_favoritos getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo_favoritos siguiente) {
        this.siguiente = siguiente;
    }

    public Nodo_favoritos getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo_favoritos anterior) {
        this.anterior = anterior;
    }
}
