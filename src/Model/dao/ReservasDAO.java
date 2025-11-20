/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_reservas;

/**
 *
 * @author aylee
 */
public interface ReservasDAO {

    void guardarReservas(Lista_reservas lista);

    Lista_reservas cargarReservas();
}
