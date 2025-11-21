/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author aylee
 */
public class JsonUtil {
    private static final Gson gson = new GsonBuilder()
    .registerTypeAdapter(LocalDate.class, new JsonLocalDateApadter())
    .registerTypeAdapter(LocalTime.class, new JsonLocalTimeAdapter())         // ← Nuevo
    .setPrettyPrinting()
    .create();

    public static <T> T leerJson(String ruta, Type type) {
        try (FileReader reader = new FileReader(ruta)) {
            return gson.fromJson(reader, type);
        } catch (Exception e) {
            return null;
        }
    }
    
    public static void inicializarArchivoSiNoExiste(String nombreArchivo) {
    File archivo = new File(nombreArchivo);
    if (!archivo.exists()) {
        try (Writer writer = new FileWriter(archivo)) {
            writer.write("[]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

    public static void guardarJson(Object data, String ruta) {
        try (FileWriter writer = new FileWriter(ruta)) {
            gson.toJson(data, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
