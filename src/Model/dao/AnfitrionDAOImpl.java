/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_anfitriones;
import Model.Cuenta_Anfitrion;
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
public class AnfitrionDAOImpl implements AnfitrionDAO {

    private static final String     ARCHIVO_ANFITRIONES = "src/json/anfitriones.json";
    private final Lista_anfitriones lista = new Lista_anfitriones();
    private final Gson gson;


    public AnfitrionDAOImpl() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new JsonLocalDateApadter())
                .registerTypeAdapter(LocalTime.class, new JsonLocalTimeAdapter())
                .setPrettyPrinting()
                .create();

        cargarDesdeJson();
    }
    
    private void cargarDesdeJson() {

        Type listType = new TypeToken<List<Cuenta_Anfitrion>>() {}.getType();

        List<Cuenta_Anfitrion> datos = JsonUtil.leerJson(ARCHIVO_ANFITRIONES, listType);

        if (datos != null) {
            for (Cuenta_Anfitrion a : datos) {
                lista.agregar(a);
            }
        }
    }
    private void guardarEnJson() {
        JsonUtil.guardarJson(lista.getListaParaJson(), ARCHIVO_ANFITRIONES);
    }

    @Override
    public void guardarAnfitrion(Cuenta_Anfitrion anfitrion) {
        lista.agregar(anfitrion);
        guardarEnJson();
    }

    @Override
    public List<Cuenta_Anfitrion> cargarAnfitriones() {
        return lista.getListaParaJson();
    }

    @Override
    public Cuenta_Anfitrion buscarPorId(String id) {
        return lista.buscarPorId(id);
    }

    @Override
    public boolean eliminarAnfitrion(String id) {
        boolean eliminado = lista.eliminar(id);
        if (eliminado) guardarEnJson();
        return eliminado;
    }

    @Override
    public boolean modificarAnfitrion(Cuenta_Anfitrion anfitrion) {
        boolean mod = lista.modificar(anfitrion);
        if (mod) guardarEnJson();
        return mod;
    }
    public Lista_anfitriones getLista() {
    return lista;
}
}
