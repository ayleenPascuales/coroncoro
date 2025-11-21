/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_admins;
import Model.Cuenta_admin;
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
public class AdminDAOImpl implements AdminDAO {

    private static final String ARCHIVO_ADMIN = "src/json/admins.json";
    private final Lista_admins lista = new Lista_admins();
    private final Gson gson;

    public AdminDAOImpl() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new JsonLocalDateApadter())
                .registerTypeAdapter(LocalTime.class, new JsonLocalTimeAdapter())
                .setPrettyPrinting()
                .create();

        cargarDesdeJson();
    }

   private void cargarDesdeJson() {
        Type tipoLista = new TypeToken<List<Cuenta_admin>>() {}.getType();
        List<Cuenta_admin> datos = JsonUtil.leerJson(ARCHIVO_ADMIN, tipoLista);

        if (datos != null) {
            for (Cuenta_admin a : datos) {
                lista.agregar(a);
            }
        }
    }
   
   private void guardarEnJson() {
        JsonUtil.guardarJson(lista.getListaParaJson(), ARCHIVO_ADMIN);
    }
   
   @Override
    public void guardarAdmin(Cuenta_admin admin) {
        lista.agregar(admin);
        guardarEnJson();
    }

    @Override
    public List<Cuenta_admin> cargarAdmins() {
        return lista.getListaParaJson();
    }

    @Override
    public Cuenta_admin buscarPorId(String id) {
        return lista.buscarPorId(id);
    }

    @Override
    public boolean eliminarAdmin(String id) {
        boolean eliminado = lista.eliminar(id);
        if (eliminado) {
            guardarEnJson();
        }
        return eliminado;
    }

    @Override
    public boolean modificarAdmin(Cuenta_admin admin) {
        boolean mod = lista.modificar(admin);
        if (mod) {
            guardarEnJson();
        }
        return mod;
    }
}
