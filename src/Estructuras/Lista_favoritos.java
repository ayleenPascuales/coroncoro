/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import Model.Favoritos;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aylee
 */
public class Lista_favoritos {
    private Nodo_favoritos cabeza;
    private Nodo_favoritos cola;
    private int tamano;

    public Lista_favoritos() {
        cabeza = null;
        cola = null;
        tamano = 0;
    }

    public void agregar(Favoritos dato) {
        Nodo_favoritos nuevo = new Nodo_favoritos(dato);

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

    public List<Favoritos> getListaParaJson() {
        List<Favoritos> listaJava = new ArrayList<>();
        Nodo_favoritos actual = cabeza;

        while (actual != null) {
            listaJava.add(actual.getDato());
            actual = actual.getSiguiente();
        }
        return listaJava;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public boolean eliminar(String idCliente, String idPublicacion) {
        Nodo_favoritos actual = cabeza;

        while (actual != null) {
            Favoritos f = actual.getDato();
            if (f.getId_cliente().equals(idCliente) && f.getId_alojamiento().equals(idPublicacion)) {

                if (actual == cabeza && actual == cola) { // único
                    cabeza = null;
                    cola = null;
                } else if (actual == cabeza) { // cabeza
                    cabeza = cabeza.getSiguiente();
                    cabeza.setAnterior(null);
                } else if (actual == cola) { // cola
                    cola = cola.getAnterior();
                    cola.setSiguiente(null);
                } else { // en medio
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

    public boolean existeFavorito(String idCliente, String idPublicacion) {
        Nodo_favoritos actual = cabeza;
        while (actual != null) {
            Favoritos f = actual.getDato();
            if (f.getId_cliente().equals(idCliente) && f.getId_alojamiento().equals(idPublicacion)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    public List<Favoritos> buscarPorCliente(String idCliente) {
        List<Favoritos> resultados = new ArrayList<>();
        Nodo_favoritos actual = cabeza;

        while (actual != null) {
            Favoritos f = actual.getDato();
            if (f.getId_cliente().equals(idCliente)) {
                resultados.add(f);
            }
            actual = actual.getSiguiente();
        }
        return resultados;
    }
}
