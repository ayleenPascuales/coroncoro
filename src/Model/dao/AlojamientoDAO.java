/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_alojamiento;

/**
 *
 * @author aylee
 */
public interface AlojamientoDAO {

    // Solo definimos los métodos obligatorios
    void guardarAlojamientos(Lista_alojamiento lista);

    Lista_alojamiento cargarAlojamientos();
}
