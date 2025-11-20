/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_reseñas;

/**
 *
 * @author aylee
 */
public interface ReseñasDAO {
    void guardarReseñas(Lista_reseñas lista);
    Lista_reseñas cargarReseñas();
}
