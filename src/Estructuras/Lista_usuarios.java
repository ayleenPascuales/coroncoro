/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Model.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boris Jimenez
 */
public class Lista_usuarios {

    private Nodo_usuarios cabeza;
    private Nodo_usuarios cola;
    private int tamano;

    public Lista_usuarios() {
        this.cabeza = null;
        this.cola = null;
        this.tamano = 0;
    }

    // AGREGAR (Inserción al final - Doblemente Enlazada)
    public void agregar(Usuario dato) {
        Nodo_usuarios nuevo = new Nodo_usuarios(dato);

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

    // MÉTODO PUENTE PARA GSON
    // Convierte la lista enlazada a un ArrayList de Usuarios (limpio y correcto)
    public List<Usuario> getListaParaJson() {
        List<Usuario> listaJava = new ArrayList<>();
        Nodo_usuarios actual = cabeza;

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
