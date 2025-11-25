/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_clientes;
import Model.Cuenta_cliente;
import Model.JsonUtil.JsonLocalDateApadter;
import Model.JsonUtil.JsonLocalTimeAdapter;
import Model.JsonUtil.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aylee
 */
public class ClienteDAOImpl implements ClienteDAO {

    private static final String ARCHIVO_CLIENTES = "src/json/clientes.json";
    private final Lista_clientes lista = new Lista_clientes();
    private final Gson gson;

    public ClienteDAOImpl() {
        gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new JsonLocalDateApadter())
            .registerTypeAdapter(LocalTime.class, new JsonLocalTimeAdapter())
            .setPrettyPrinting()
            .create();

    cargarDesdeJson();
    }
    
    private void cargarDesdeJson() {

        Type tipoLista = new TypeToken<List<Cuenta_cliente>>() {}.getType();
        List<Cuenta_cliente> datos = JsonUtil.leerJson(ARCHIVO_CLIENTES, tipoLista);

        if (datos != null) {
            for (Cuenta_cliente c : datos) {
                lista.agregar(c);
            }
        }
    }
    
    private void guardarEnJson() {
        JsonUtil.guardarJson(lista.getListaParaJson(), ARCHIVO_CLIENTES);
    }
    
    @Override
    public void guardarClientes(Cuenta_cliente cliente) {
        lista.agregar(cliente);
        guardarEnJson();
    }

    @Override
    public List<Cuenta_cliente> cargarClientes() {
        return lista.getListaParaJson();
    }

    @Override
    public Cuenta_cliente buscarPorId(String id) {
        return lista.buscarPorId(id);
    }

    @Override
    public boolean eliminarCliente(String id) {
        boolean eliminado = lista.eliminar(id);
        if (eliminado) {
            guardarEnJson();
        }
        return eliminado;
    }

    @Override
    public boolean modificarCliente(Cuenta_cliente cliente) {
        boolean mod = lista.modificar(cliente);
        if (mod) {
            guardarEnJson();
        }
        return mod;
    }
    
    public Lista_clientes getLista() {
    return lista;
    }
}
