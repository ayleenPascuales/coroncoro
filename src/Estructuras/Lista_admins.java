/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Model.Cuenta_admin;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boris Jimenez
 */
public class Lista_admins {

    private Nodo_admin cabeza;
    private Nodo_admin cola;
    private int tamano;

    public Lista_admins() {
        this.cabeza = null;
        this.cola = null;
        this.tamano = 0;
    }

    // AGREGAR (Inserción al final - Doblemente Enlazada)
    public void agregar(Cuenta_admin dato) {
        Nodo_admin nuevo = new Nodo_admin(dato);

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
    
    public Cuenta_admin buscarPorId(String id) {
        Nodo_admin actual = cabeza;

        while (actual != null) {
            if (actual.getDato().getDocumento().equals(id)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }

        return null;
    }
    
     public boolean eliminar(String id) {
        if (cabeza == null) return false;

        Nodo_admin actual = cabeza;

        while (actual != null) {
            if (actual.getDato().getDocumento().equals(id)) {

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
     
     public boolean modificar(Cuenta_admin adminNuevo) {
        Nodo_admin actual = cabeza;

        while (actual != null) {
            if (actual.getDato().getDocumento().equals(adminNuevo.getDocumento())) {
                actual.setDato(adminNuevo);
                return true;
            }
            actual = actual.getSiguiente();
        }

        return false;
    }
     
     

    // MÉTODO PARA GSON
    public List<Cuenta_admin> getListaParaJson() {
        List<Cuenta_admin> listaJava = new ArrayList<>();
        Nodo_admin actual = cabeza;

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

    public Nodo_admin getCabeza() {
        return cabeza;
    }
}
