/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_admins;
import Model.Cuenta_admin;
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
public class AdminDAOImpl implements AdminDAO {

    private static final String RUTA_ARCHIVO = "src/json/admins.json";
    private final Gson gson;

    public AdminDAOImpl() {
        // CONFIGURACIÓN ESPECIAL PARA LOCALDATE
        // Esto permite que Gson entienda el tipo de dato 'LocalDate' de la clase Persona
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context)
                        -> new JsonPrimitive(src.toString())) // Al guardar: convierte fecha a String "2023-11-20"
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context)
                        -> LocalDate.parse(json.getAsString())) // Al cargar: convierte String a fecha
                .create();
    }

    @Override
    public void guardarAdmins(Lista_admins listaDoble) {
        try (FileWriter writer = new FileWriter(RUTA_ARCHIVO)) {
            List<Cuenta_admin> datos = listaDoble.getListaParaJson();
            gson.toJson(datos, writer);
            System.out.println("--- Admins guardados correctamente ---");
        } catch (IOException e) {
            System.err.println("Error guardando Admins: " + e.getMessage());
        }
    }

    @Override
    public Lista_admins cargarAdmins() {
        Lista_admins listaRecuperada = new Lista_admins();

        try (FileReader reader = new FileReader(RUTA_ARCHIVO)) {
            Type tipoLista = new TypeToken<ArrayList<Cuenta_admin>>() {
            }.getType();
            List<Cuenta_admin> listaTemporal = gson.fromJson(reader, tipoLista);

            if (listaTemporal != null) {
                for (Cuenta_admin admin : listaTemporal) {
                    listaRecuperada.agregar(admin);
                }
            }
        } catch (IOException e) {
            System.out.println("No hay archivo de admins. Se retorna lista vacía.");
        }

        return listaRecuperada;
    }
}
