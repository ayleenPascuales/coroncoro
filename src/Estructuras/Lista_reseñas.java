/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Model.Reseñas;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boris Jimenez
 */
public class Lista_reseñas {

    private Nodo_reseñas cabeza;
    private Nodo_reseñas cola;
    private int tamano;

    public Lista_reseñas() {
        this.cabeza = null;
        this.cola = null;
        this.tamano = 0;
    }

    // AGREGAR (Inserción al final - Doblemente Enlazada)
    public void agregar(Reseñas dato) {
        Nodo_reseñas nuevo = new Nodo_reseñas(dato);

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
    
    public Reseñas buscarPorId(String id) {
        Nodo_reseñas actual = cabeza;

        while (actual != null) {
            if (actual.getDato().getId_reseña().equals(id)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }

        return null;
    }
    
    public boolean eliminar(String id) {
        if (cabeza == null) return false;

        Nodo_reseñas actual = cabeza;

        while (actual != null) {
            if (actual.getDato().getId_reseña().equals(id)) {

                // CASO 1: es el único nodo
                if (actual == cabeza && actual == cola) {
                    cabeza = null;
                    cola = null;
                }
                // CASO 2: eliminar cabeza
                else if (actual == cabeza) {
                    cabeza = cabeza.getSiguiente();
                    cabeza.setAnterior(null);
                }
                // CASO 3: eliminar cola
                else if (actual == cola) {
                    cola = cola.getAnterior();
                    cola.setSiguiente(null);
                }
                // CASO 4: nodo intermedio
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
    
    public boolean modificar(Reseñas reseñaNueva) {
        Nodo_reseñas actual = cabeza;

        while (actual != null) {
            if (actual.getDato().getId_reseña().equals(reseñaNueva.getId_reseña())) {
                actual.setDato(reseñaNueva);
                return true;
            }
            actual = actual.getSiguiente();
        }

        return false;
    }
    
    // MÉTODO PUENTE PARA GSON
    public List<Reseñas> getListaParaJson() {
        List<Reseñas> listaJava = new ArrayList<>();
        Nodo_reseñas actual = cabeza;

        while (actual != null) {
            listaJava.add(actual.getDato());
            actual = actual.getSiguiente();
        }
        return listaJava;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }
    public int getTamano() {
        return tamano;
    }

    public Nodo_reseñas getCabeza() {
        return cabeza;
    }
}
