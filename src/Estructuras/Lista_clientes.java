/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Model.Cuenta_cliente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boris Jimenez
 */
public class Lista_clientes {

    private Nodo_cliente cabeza;
    private Nodo_cliente cola;
    private int tamano;

    public Lista_clientes() {
        this.cabeza = null;
        this.cola = null;
        this.tamano = 0;
    }

    // AGREGAR (Inserción al final - Doblemente Enlazada)
    public void agregar(Cuenta_cliente dato) {
        Nodo_cliente nuevo = new Nodo_cliente(dato);

        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            // Conexión doble: 
            cola.setSiguiente(nuevo);
            nuevo.setAnterior(cola);
            cola = nuevo;
        }
        tamano++;
    }
    
    public Cuenta_cliente buscarPorId(String id) {
        Nodo_cliente actual = cabeza;

        while (actual != null) {
            if (actual.getDato().getDocumento().equals(id)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }

        return null;
    }
    
    public boolean eliminar(String id) {
        Nodo_cliente actual = cabeza;

        while (actual != null) {

            if (actual.getDato().getDocumento().equals(id)) {

                Nodo_cliente ant = actual.getAnterior();
                Nodo_cliente sig = actual.getSiguiente();

                // Si el nodo era el primero
                if (ant != null) ant.setSiguiente(sig);
                else cabeza = sig;

                // Si el nodo era el último
                if (sig != null) sig.setAnterior(ant);
                else cola = ant;

                tamano--;
                return true;
            }

            actual = actual.getSiguiente();
        }

        return false;
    }
    
    public boolean modificar(Cuenta_cliente actualizado) {
        Nodo_cliente actual = cabeza;

        while (actual != null) {

            if (actual.getDato().getDocumento().equals(actualizado.getDocumento())) {
                actual.setDato(actualizado);
                return true;
            }

            actual = actual.getSiguiente();
        }

        return false;
    }
    // MÉTODO PUENTE PARA GSON
    public List<Cuenta_cliente> getListaParaJson() {
        List<Cuenta_cliente> listaJava = new ArrayList<>();
        Nodo_cliente actual = cabeza;

        while (actual != null) {
            listaJava.add(actual.getDato());
            actual = actual.getSiguiente();
        }
        return listaJava;   
    }
    
    public int getTamano() {
        return tamano;
    }
    
    public boolean estaVacia() {
        return cabeza == null;
    }
}
