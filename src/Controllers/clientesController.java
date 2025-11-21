/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Model.Cuenta_cliente;
import Model.dao.ClienteDAO;
import Model.dao.ClienteDAOImpl;
import java.util.List;

/**
 *
 * @author aylee
 */
public class clientesController {
    private final ClienteDAO clienteDAO;

    public clientesController() {
        this.clienteDAO = new ClienteDAOImpl(); // conecta con Lista_clientes y JSON
    }

    public void guardarClientes(Cuenta_cliente cliente) {
        clienteDAO.guardarClientes(cliente);
    }

    public List<Cuenta_cliente> listarClientes() {
        return clienteDAO.cargarClientes();
    }

    public Cuenta_cliente buscarCliente(String id) {
        return clienteDAO.buscarPorId(id);
    }

    public boolean eliminarCliente(String id) {
        return clienteDAO.eliminarCliente(id);
    }

    public boolean modificarCliente(Cuenta_cliente cliente) {
        return clienteDAO.modificarCliente(cliente);
    }
}
