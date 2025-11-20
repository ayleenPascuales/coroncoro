/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_reseñas;
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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aylee
 */
public class ReseñasDAOImpl implements ReseñasDAO {

    private static final String RUTA_ARCHIVO = "src/json/reseñas.json";
    private final Gson gson;

    public ReseñasDAOImpl() {
        // CONFIGURACIÓN ESPECIAL PARA LOCALDATE 
        // Es necesaria porque Reseñas contiene Cuenta_cliente, que contiene fechas.
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context)
                        -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context)
                        -> LocalDate.parse(json.getAsString()))
                .create();
    }

    @Override
    public void guardarReseñas(Lista_reseñas listaDoble) {
        try (FileWriter writer = new FileWriter(RUTA_ARCHIVO)) {
            List<Reseñas> datos = listaDoble.getListaParaJson();
            gson.toJson(datos, writer);
            System.out.println("--- Reseñas guardadas correctamente ---");
        } catch (IOException e) {
            System.err.println("Error guardando Reseñas: " + e.getMessage());
        }
    }

    @Override
    public Lista_reseñas cargarReseñas() {
        Lista_reseñas listaRecuperada = new Lista_reseñas();

        try (FileReader reader = new FileReader(RUTA_ARCHIVO)) {
            Type tipoLista = new TypeToken<ArrayList<Reseñas>>() {
            }.getType();
            List<Reseñas> listaTemporal = gson.fromJson(reader, tipoLista);

            if (listaTemporal != null) {
                for (Reseñas reseña : listaTemporal) {
                    listaRecuperada.agregar(reseña);
                }
            }
        } catch (IOException e) {
            System.out.println("No hay archivo de reseñas. Se retorna lista vacía.");
        }

        return listaRecuperada;
    }
}
