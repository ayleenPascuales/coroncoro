/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Model.Alojamiento;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boris Jimenez
 */
public class Lista_alojamiento {

    private Nodo_alojamiento cabeza;
    private Nodo_alojamiento cola; // Referencia al último para insertar rápido
    private int tamano;

    public Lista_alojamiento() {
        this.cabeza = null;
        this.cola = null;
        this.tamano = 0;
    }

    // MÉTODO PARA AGREGAR (Inserción al final - Doblemente Enlazada)
    public void agregar(Alojamiento dato) {
        Nodo_alojamiento nuevo = new Nodo_alojamiento(dato);

        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.setSiguiente(nuevo);    // El siguiente del último actual es el nuevo
            nuevo.setAnterior(cola);     // El anterior del nuevo es el último actual
            cola = nuevo;                // Actualizamos la cola
        }
        tamano++;
    }

    public List<Alojamiento> getListaParaJson() {
        List<Alojamiento> listaJava = new ArrayList<>();
        Nodo_alojamiento actual = cabeza;

        while (actual != null) {
            listaJava.add(actual.getDato());
            actual = actual.getSiguiente();
        }
        return listaJava;
    }
    
    public boolean estaVacia() {
        return cabeza == null;
    }

    public void listarEnConsola() {
        Nodo_alojamiento actual = cabeza;
        System.out.println("--- LISTA DE ALOJAMIENTOS ---");
        while (actual != null) {
            System.out.println("Alojamiento en: " + actual.getDato().getCiudad() + " - " + actual.getDato().getBarrio());
            actual = actual.getSiguiente();
        }
    }

    public int getTamano() {
        return tamano;
    }
    
    public Alojamiento buscarPorId(String id) {
    Nodo_alojamiento actual = cabeza;
    while (actual != null) {
        if (actual.getDato().getId_alojamiento().equals(id)) {
            return actual.getDato();
        }
        actual = actual.getSiguiente();
    }
    return null;
}

public boolean eliminar(String id) {
    Nodo_alojamiento actual = cabeza;

    while (actual != null) {
        if (actual.getDato().getId_alojamiento().equals(id)) {

            // caso: único elemento
            if (actual == cabeza && actual == cola) {
                cabeza = null;
                cola = null;
            }
            // caso: es cabeza
            else if (actual == cabeza) {
                cabeza = cabeza.getSiguiente();
                cabeza.setAnterior(null);
            }
            // caso: es cola
            else if (actual == cola) {
                cola = cola.getAnterior();
                cola.setSiguiente(null);
            }
            // caso: en medio
            else {
                actual.getAnterior().setSiguiente(actual.getSiguiente());
                actual.getSiguiente().setAnterior(actual.getAnterior());
            }

            tamano--;
            return true;
        }
        actual = actual.getSiguiente();
    }

    return false;
}

public boolean modificar(Alojamiento nuevo) {
    Nodo_alojamiento actual = cabeza;

    while (actual != null) {
        if (actual.getDato().getId_alojamiento().equals(nuevo.getId_alojamiento())) {
            actual.setDato(nuevo);
            return true;
        }
        actual = actual.getSiguiente();
    }

    return false;
}

    public Nodo_alojamiento getCabeza() {
        return cabeza;
    }
}
