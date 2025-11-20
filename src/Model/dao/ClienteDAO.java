/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_clientes;

/**
 *
 * @author aylee
 */
public interface ClienteDAO {
    void guardarClientes(Lista_clientes lista);
    Lista_clientes cargarClientes();
}
