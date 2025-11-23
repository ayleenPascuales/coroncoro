/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_alojamiento;
import Model.Alojamiento;
import Model.JsonUtil.JsonLocalDateApadter;
import Model.JsonUtil.JsonLocalTimeAdapter;
import Model.JsonUtil.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public class AlojamientoDAOImpl implements AlojamientoDAO {

    private static final String ARCHIVO_ALOJAMIENTO = "src/json/alojamientos.json";
    private final Lista_alojamiento lista = new Lista_alojamiento();
    private final Gson gson;

    public AlojamientoDAOImpl() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new JsonLocalDateApadter())
                .registerTypeAdapter(LocalTime.class, new JsonLocalTimeAdapter())
                .setPrettyPrinting()
                .create();
        cargarDesdeJson();     
    }   
    
    private void cargarDesdeJson() {
        Type listType = new TypeToken<List<Alojamiento>>() {}.getType();

        List<Alojamiento> datos = JsonUtil.leerJson(ARCHIVO_ALOJAMIENTO, listType);

        if (datos != null) {
            for (Alojamiento a : datos) {
                lista.agregar(a);
            }
        }
    }

    private void guardarEnJson() {
        JsonUtil.guardarJson(lista.getListaParaJson(), ARCHIVO_ALOJAMIENTO);
    }
    
    @Override
    public void guardarAlojamientos(Alojamiento alojamiento) {
        lista.agregar(alojamiento);
        guardarEnJson();
    }

    @Override
    public List<Alojamiento> cargarAlojamientos() {
        return lista.getListaParaJson();
    }

    @Override
    public Alojamiento BuscarPorId(String id) {
        return lista.buscarPorId(id);
    }

    @Override
    public boolean eliminarAlojamiento(String id) {
        boolean eliminado = lista.eliminar(id);
        if (eliminado) guardarEnJson();
        return eliminado;
    }

    @Override
    public boolean modificarAlojamiento(Alojamiento alojamiento) {
        boolean mod = lista.modificar(alojamiento);
        if (mod) guardarEnJson();
        return mod;
    }
    @Override
    public List<Alojamiento> cargarDisponibles() {
    List<Alojamiento> disponibles = new ArrayList<>();
    for (Alojamiento a : cargarAlojamientos()) {
        if (a.isDisponibilidad()) {
            disponibles.add(a);
        }
    }
    return disponibles;
}
}

