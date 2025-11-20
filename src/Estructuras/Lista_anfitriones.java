/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Model.Cuenta_Anfitrion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boris Jimenez
 */
public class Lista_anfitriones {

    private Nodo_anfitrion cabeza;
    private Nodo_anfitrion cola;
    private int tamano;

    public Lista_anfitriones() {
        this.cabeza = null;
        this.cola = null;
        this.tamano = 0;
    }

    // AGREGAR (Inserción al final - Doblemente Enlazada)
    public void agregar(Cuenta_Anfitrion dato) {
        Nodo_anfitrion nuevo = new Nodo_anfitrion(dato);

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

    // MÉTODO PARA GSON (Convierte la lista enlazada a un ArrayList)
    public List<Cuenta_Anfitrion> getListaParaJson() {
        List<Cuenta_Anfitrion> listaJava = new ArrayList<>();
        Nodo_anfitrion actual = cabeza;

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
}
