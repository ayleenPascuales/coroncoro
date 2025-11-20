/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_anfitriones;

/**
 *
 * @author aylee
 */
public interface AnfitrionDAO {

    void guardarAnfitriones(Lista_anfitriones lista);

    Lista_anfitriones cargarAnfitriones();
}
