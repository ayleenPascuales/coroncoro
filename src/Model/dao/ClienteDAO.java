/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_clientes;
import Model.Cuenta_cliente;
import java.util.List;

/**
 *
 * @author aylee
 */
public interface ClienteDAO {
    void guardarClientes(Cuenta_cliente clientes);
    List<Cuenta_cliente> cargarClientes();
    Cuenta_cliente buscarPorId(String id);
    boolean eliminarCliente(String id);
    boolean modificarCliente(Cuenta_cliente clientes);
}
