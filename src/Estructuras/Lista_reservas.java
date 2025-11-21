/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Model.Reservas;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boris Jimenez
 */
public class Lista_reservas {

    private Nodo_reserva cabeza;
    private Nodo_reserva cola;
    private int tamano;

    public Lista_reservas() {
        this.cabeza = null;
        this.cola = null;
        this.tamano = 0;
    }

    // AGREGAR (Inserción al final - Doblemente Enlazada)
    public void agregar(Reservas dato) {
        Nodo_reserva nuevo = new Nodo_reserva(dato);

        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.setSiguiente(nuevo);
            nuevo.setAnterior(cola);
            cola = nuevo;
        }
        tamano++;
    }
    
    public Reservas buscarPorId(String id) {
        Nodo_reserva actual = cabeza;

        while (actual != null) {
            if (actual.getDato().getNum_reserva().equals(id)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }

        return null;
    }
    
    public boolean eliminar(String id) {
        if (cabeza == null) return false;

        Nodo_reserva actual = cabeza;

        while (actual != null) {
            if (actual.getDato().getNum_reserva().equals(id)) {

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
    
    public boolean modificar(Reservas reservaNueva) {
        Nodo_reserva actual = cabeza;

        while (actual != null) {
            if (actual.getDato().getNum_reserva().equals(reservaNueva.getNum_reserva())) {
                actual.setDato(reservaNueva);
                return true;
            }
            actual = actual.getSiguiente();
        }

        return false;
    }

    // MÉTODO PUENTE PARA GSON
    public List<Reservas> getListaParaJson() {
        List<Reservas> listaJava = new ArrayList<>();
        Nodo_reserva actual = cabeza;

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
