/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Model.Reservas;

/**
 *
 * @author Boris Jimenez
 */
public class Nodo_reserva {

    private Reservas dato;
    private Nodo_reserva anterior;
    private Nodo_reserva siguiente;

    public Nodo_reserva(Reservas dato) {
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }

    // Getters y Setters
    public Reservas getDato() {
        return dato;
    }

    public void setDato(Reservas dato) {
        this.dato = dato;
    }

    public Nodo_reserva getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo_reserva anterior) {
        this.anterior = anterior;
    }

    public Nodo_reserva getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo_reserva siguiente) {
        this.siguiente = siguiente;
    }
}
