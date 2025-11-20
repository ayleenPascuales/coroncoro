/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_anfitriones;
import Model.Cuenta_Anfitrion;
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
public class AnfitrionDAOImpl implements AnfitrionDAO {

    private static final String RUTA_ARCHIVO = "src/json/anfitriones.json";
    private final Gson gson;

    public AnfitrionDAOImpl() {
        // CONFIGURACIÓN ESPECIAL PARA LOCALDATE (Necesaria por la herencia de Persona)
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context)
                        -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context)
                        -> LocalDate.parse(json.getAsString()))
                .create();
    }

    @Override
    public void guardarAnfitriones(Lista_anfitriones listaDoble) {
        try (FileWriter writer = new FileWriter(RUTA_ARCHIVO)) {
            List<Cuenta_Anfitrion> datos = listaDoble.getListaParaJson();
            gson.toJson(datos, writer);
            System.out.println("--- Anfitriones guardados correctamente ---");
        } catch (IOException e) {
            System.err.println("Error guardando Anfitriones: " + e.getMessage());
        }
    }

    @Override
    public Lista_anfitriones cargarAnfitriones() {
        Lista_anfitriones listaRecuperada = new Lista_anfitriones();

        try (FileReader reader = new FileReader(RUTA_ARCHIVO)) {
            Type tipoLista = new TypeToken<ArrayList<Cuenta_Anfitrion>>() {
            }.getType();
            List<Cuenta_Anfitrion> listaTemporal = gson.fromJson(reader, tipoLista);

            if (listaTemporal != null) {
                for (Cuenta_Anfitrion anfitrion : listaTemporal) {
                    listaRecuperada.agregar(anfitrion);
                }
            }
        } catch (IOException e) {
            System.out.println("No hay archivo de anfitriones. Se retorna lista vacía.");
        }

        return listaRecuperada;
    }
}
