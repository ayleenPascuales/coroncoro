/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_usuarios;
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

    private static final String RUTA_ARCHIVO = "src/json/usuarios.json";
    private final Gson gson;

    public UsuariosDAOImpl() {
        // Gson simple, ya que la clase Usuario solo contiene Strings
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void guardarUsuarios(Lista_usuarios listaDoble) {
        try (FileWriter writer = new FileWriter(RUTA_ARCHIVO)) {
            List<Usuario> datos = listaDoble.getListaParaJson();
            gson.toJson(datos, writer);
            System.out.println("--- Usuarios guardados correctamente ---");
        } catch (IOException e) {
            System.err.println("Error guardando Usuarios: " + e.getMessage());
        }
    }

    @Override
    public Lista_usuarios cargarUsuarios() {
        Lista_usuarios listaRecuperada = new Lista_usuarios();

        try (FileReader reader = new FileReader(RUTA_ARCHIVO)) {
            Type tipoLista = new TypeToken<ArrayList<Usuario>>() {
            }.getType();
            List<Usuario> listaTemporal = gson.fromJson(reader, tipoLista);

            if (listaTemporal != null) {
                for (Usuario usuario : listaTemporal) {
                    listaRecuperada.agregar(usuario);
                }
            }
        } catch (IOException e) {
            System.out.println("No hay archivo de usuarios. Se retorna lista vacía.");
        }

        return listaRecuperada;
    }
}
