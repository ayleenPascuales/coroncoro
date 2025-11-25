/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_reservas;
import Model.JsonUtil.JsonLocalDateApadter;
import Model.JsonUtil.JsonLocalTimeAdapter;
import Model.JsonUtil.JsonUtil;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aylee
 */
public class ReservasDAOImpl implements ReservasDAO {

    private static final String ARCHIVO_RESERVAS = "src/json/reservas.json";
    private final Lista_reservas lista = new Lista_reservas();
    private final Gson gson;

    public ReservasDAOImpl() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new JsonLocalDateApadter())
                .registerTypeAdapter(LocalTime.class, new JsonLocalTimeAdapter())
                .setPrettyPrinting()
                .create();

        cargarDesdeJson();
    }
    
    private void cargarDesdeJson() {

        Type tipoLista = new TypeToken<List<Reservas>>(){}.getType();
        List<Reservas> datos = JsonUtil.leerJson(ARCHIVO_RESERVAS, tipoLista);

        if (datos != null) {
            for (Reservas r : datos) {
                lista.agregar(r);
            }
        }
    }
    
    private void guardarEnJson() {
        JsonUtil.guardarJson(lista.getListaParaJson(), ARCHIVO_RESERVAS);
    }
    
    
    
    @Override
    public void guardarReserva(Reservas reserva) {
        lista.agregar(reserva);
        guardarEnJson();
    }

    @Override
    public List<Reservas> cargarReservas() {
        return lista.getListaParaJson();
    }

    @Override
    public Reservas buscarPorId(String id) {
        return lista.buscarPorId(id);
    }

    @Override
    public boolean eliminarReserva(String id) {
        boolean eliminado = lista.eliminar(id);
        if (eliminado) {
            guardarEnJson();
        }
        return eliminado;
    }

    @Override
    public boolean modificarReserva(Reservas reserva) {
        boolean mod = lista.modificar(reserva);
        if (mod) {
            guardarEnJson();
        }
        return mod;
    }


}
