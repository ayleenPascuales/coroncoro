/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_reseñas;
import Model.JsonUtil.JsonLocalDateApadter;
import Model.JsonUtil.JsonLocalTimeAdapter;
import Model.JsonUtil.JsonUtil;
import Model.Reseñas;
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
public class ReseñasDAOImpl implements ReseñasDAO {

    private static final String ARCHIVO_RESEÑAS = "src/json/reseñas.json";
    private final Lista_reseñas lista = new Lista_reseñas();
    private final Gson gson;

    public ReseñasDAOImpl() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new JsonLocalDateApadter())
                .registerTypeAdapter(LocalTime.class, new JsonLocalTimeAdapter())
                .setPrettyPrinting()
                .create();
    }

    private void cargarDesdeJson() {
        Type tipoLista = new TypeToken<List<Reseñas>>() {}.getType();
        List<Reseñas> datos = JsonUtil.leerJson(ARCHIVO_RESEÑAS, tipoLista);

        if (datos != null) {
            for (Reseñas r : datos) {
                lista.agregar(r);
            }
        }
    }
    
    private void guardarEnJson() {
        JsonUtil.guardarJson(lista.getListaParaJson(), ARCHIVO_RESEÑAS);
    }
    
    @Override
    public void guardarReseña(Reseñas reseña) {
        lista.agregar(reseña);
        guardarEnJson();
    }

    @Override
    public List<Reseñas> cargarReseñas() {
        return lista.getListaParaJson();
    }

    @Override
    public Reseñas buscarPorId(String id) {
        return lista.buscarPorId(id);
    }

    @Override
    public boolean eliminarReseña(String id) {
        boolean eliminado = lista.eliminar(id);
        if (eliminado) {
            guardarEnJson();
        }
        return eliminado;
    }

    @Override
    public boolean modificarReseña(Reseñas reseña) {
        boolean mod = lista.modificar(reseña);
        if (mod) {
            guardarEnJson();
        }
        return mod;
    }
}
