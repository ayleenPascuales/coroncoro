/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_favoritos;
import Model.Favoritos;
import Model.JsonUtil.JsonLocalDateApadter;
import Model.JsonUtil.JsonLocalTimeAdapter;
import Model.JsonUtil.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author aylee
 */
public class FavoritosDAOImpl implements FavoritosDAO {
    
    private static final String ARCHIVO_FAVORITOS = "src/json/favoritos.json";
    private final Lista_favoritos lista = new Lista_favoritos();
    private final Gson gson;
    

    public FavoritosDAOImpl() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new JsonLocalDateApadter())
                .registerTypeAdapter(LocalTime.class, new JsonLocalTimeAdapter())
                .setPrettyPrinting()
                .create();
        cargarDesdeJson(); 
    }

    private void cargarDesdeJson() {
        List<Favoritos> datos = JsonUtil.leerJson(ARCHIVO_FAVORITOS, List.class);
        if (datos != null) {
            for (Favoritos f : datos) {
                lista.agregar(f);
            }
        }
    }

    private void guardarEnJson() {
        JsonUtil.guardarJson(lista.getListaParaJson(), ARCHIVO_FAVORITOS);
    }

    @Override
    public void guardarFavorito(Favoritos favorito) {
        if (!lista.existeFavorito(favorito.getId_cliente(), favorito.getId_alojamiento())) {
            lista.agregar(favorito);
            guardarEnJson();
        }
    }

    @Override
    public List<Favoritos> cargarFavoritos() {
        return lista.getListaParaJson();
    }

    @Override
    public List<Favoritos> obtenerPorCliente(String idCliente) {
        return lista.buscarPorCliente(idCliente);
    }

    @Override
    public boolean eliminarFavorito(String idCliente, String idPublicacion) {
        boolean eliminado = lista.eliminar(idCliente, idPublicacion);
        if (eliminado) guardarEnJson();
        return eliminado;
    }

    @Override
    public boolean existeFavorito(String idCliente, String idPublicacion) {
        return lista.existeFavorito(idCliente, idPublicacion);
    }
}
