/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_alojamiento;
import Model.Alojamiento;
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
public class AlojamientoDAOImpl implements AlojamientoDAO {

    private static final String RUTA_ARCHIVO = "src/json/alojamientos.json";
    private final Gson gson;

    public AlojamientoDAOImpl() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void guardarAlojamientos(Lista_alojamiento listaDoble) {
        try (FileWriter writer = new FileWriter(RUTA_ARCHIVO)) {
            // Usamos el método puente de tu lista doble para obtener un ArrayList
            List<Alojamiento> datosParaGuardar = listaDoble.getListaParaJson();

            gson.toJson(datosParaGuardar, writer);
            System.out.println("--- JSON guardado correctamente ---");
        } catch (IOException e) {
            System.err.println("Error guardando: " + e.getMessage());
        }
    }

    @Override
    public Lista_alojamiento cargarAlojamientos() {
        Lista_alojamiento listaRecuperada = new Lista_alojamiento();

        try (FileReader reader = new FileReader(RUTA_ARCHIVO)) {
            Type tipoLista = new TypeToken<ArrayList<Alojamiento>>() {
            }.getType();
            List<Alojamiento> listaTemporal = gson.fromJson(reader, tipoLista);

            if (listaTemporal != null) {
                for (Alojamiento a : listaTemporal) {
                    listaRecuperada.agregar(a);
                }
            }
        } catch (IOException e) {
            System.out.println("No existe archivo previo, se retorna lista vacía.");
        }

        return listaRecuperada;
    }
}
