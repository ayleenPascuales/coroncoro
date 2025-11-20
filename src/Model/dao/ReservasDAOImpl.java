/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_reservas;
import Model.Reservas;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aylee
 */
public class ReservasDAOImpl implements ReservasDAO {

    private static final String RUTA_ARCHIVO = "src/json/reservas.json";
    private final Gson gson;

    public ReservasDAOImpl() {
        // Formateador para LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        // CONFIGURACIÓN ESPECIAL PARA LOCALDATE y LOCALDATETIME
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                // 1. Manejo de LocalDate (para dia_entrada/dia_salida)
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context)
                        -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context)
                        -> LocalDate.parse(json.getAsString()))
                // 2. Manejo de LocalDateTime (para hora_entrada/hora_salida)
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context)
                        -> new JsonPrimitive(src.format(formatter)))
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context)
                        -> LocalDateTime.parse(json.getAsString(), formatter))
                .create();
    }

    @Override
    public void guardarReservas(Lista_reservas listaDoble) {
        try (FileWriter writer = new FileWriter(RUTA_ARCHIVO)) {
            List<Reservas> datos = listaDoble.getListaParaJson();
            gson.toJson(datos, writer);
            System.out.println("--- Reservas guardadas correctamente ---");
        } catch (IOException e) {
            System.err.println("Error guardando Reservas: " + e.getMessage());
        }
    }

    @Override
    public Lista_reservas cargarReservas() {
        Lista_reservas listaRecuperada = new Lista_reservas();

        try (FileReader reader = new FileReader(RUTA_ARCHIVO)) {
            Type tipoLista = new TypeToken<ArrayList<Reservas>>() {
            }.getType();
            List<Reservas> listaTemporal = gson.fromJson(reader, tipoLista);

            if (listaTemporal != null) {
                for (Reservas reserva : listaTemporal) {
                    listaRecuperada.agregar(reserva);
                }
            }
        } catch (IOException e) {
            System.out.println("No hay archivo de reservas. Se retorna lista vacía.");
        }

        return listaRecuperada;
    }
}
