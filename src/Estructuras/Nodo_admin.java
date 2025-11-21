/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Model.Cuenta_admin;

/**
 *
 * @author aylee
 */
public class Nodo_admin {
    private Cuenta_admin dato;
    private Nodo_admin anterior;
    private Nodo_admin siguiente;

    public Nodo_admin(Cuenta_admin dato) {
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }

    public Cuenta_admin getDato() {
        return dato;
    }

    public void setDato(Cuenta_admin dato) {
        this.dato = dato;
    }

    public Nodo_admin getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo_admin anterior) {
        this.anterior = anterior;
    }

    public Nodo_admin getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo_admin siguiente) {
        this.siguiente = siguiente;
    }
    
    
    
    
    
}
