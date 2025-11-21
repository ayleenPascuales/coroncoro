/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_usuarios;
import Model.JsonUtil.JsonUtil;
import Model.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aylee
 */
public class UsuariosDAOImpl implements UsuariosDAO {

    private static final String ARCHIVO_USUARIOS = "src/json/usuarios.json";
    private final Lista_usuarios lista = new Lista_usuarios();
    private final Gson gson;

    public UsuariosDAOImpl() {
        // Gson simple, ya que la clase Usuario solo contiene Strings
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        
       cargarDesdeJson();
    }
    
    private void cargarDesdeJson() {
        Type tipoLista = new TypeToken<List<Usuario>>() {}.getType();
        List<Usuario> datos = JsonUtil.leerJson(ARCHIVO_USUARIOS, tipoLista);

        if (datos != null) {
            for (Usuario u : datos) {
                lista.agregar(u);
            }
        }
    }
    
    private void guardarEnJson() {
        JsonUtil.guardarJson(lista.getListaParaJson(), ARCHIVO_USUARIOS);
    }
    
    @Override
    public void guardarUsuario(Usuario usuario) {
        lista.agregar(usuario);
        guardarEnJson();
    }

    @Override
    public List<Usuario> cargarUsuarios() {
        return lista.getListaParaJson();
    }

    @Override
    public Usuario buscarPorId(String id) {
        return lista.buscarPorId(id);
    }

    @Override
    public boolean eliminarUsuario(String id) {
        boolean eliminado = lista.eliminar(id);
        if (eliminado) {
            guardarEnJson();
        }
        return eliminado;
    }

    @Override
    public boolean modificarUsuario(Usuario usuario) {
        boolean mod = lista.modificar(usuario);
        if (mod) {
            guardarEnJson();
        }
        return mod;
    }


}
