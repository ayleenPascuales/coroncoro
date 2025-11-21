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
    
    public Usuario buscarPorId(String id) {
        Nodo_usuarios actual = cabeza;

        while (actual != null) {
            if (actual.getDato().getId_usuario().equals(id)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }

        return null;
    }
    
    public boolean eliminar(String id) {
        if (cabeza == null) return false;

        Nodo_usuarios actual = cabeza;

        while (actual != null) {
            if (actual.getDato().getId_usuario().equals(id)) {

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
    
    public boolean modificar(Usuario usuarioNuevo) {
        Nodo_usuarios actual = cabeza;

        while (actual != null) {
            if (actual.getDato().getId_usuario().equals(usuarioNuevo.getId_usuario())) {
                actual.setDato(usuarioNuevo);
                return true;
            }
            actual = actual.getSiguiente();
        }

        return false;
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
    
    public Nodo_usuarios getCabeza() {
        return cabeza;
    }
}
