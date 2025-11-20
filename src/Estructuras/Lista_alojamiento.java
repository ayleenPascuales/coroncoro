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
            // Caso: Lista vacía
            cabeza = nuevo;
            cola = nuevo;
        } else {
            // Caso: Lista con elementos
            cola.setSiguiente(nuevo);    // El siguiente del último actual es el nuevo
            nuevo.setAnterior(cola);     // El anterior del nuevo es el último actual
            cola = nuevo;                // Actualizamos la cola
        }
        tamano++;
    }

    // MÉTODO PUENTE PARA GSON (Vital para guardar en JSON)
    // Gson no sabe recorrer tus nodos, así que convertimos tu lista enlazada
    // a una lista de Java normal temporalmente solo para guardar.
    public List<Alojamiento> getListaParaJson() {
        List<Alojamiento> listaJava = new ArrayList<>();
        Nodo_alojamiento actual = cabeza;

        while (actual != null) {
            listaJava.add(actual.getDato());
            actual = actual.getSiguiente();
        }
        return listaJava;
    }

    // Método para verificar si está vacía
    public boolean estaVacia() {
        return cabeza == null;
    }

    // Método para mostrar en consola (Debugging)
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
}
