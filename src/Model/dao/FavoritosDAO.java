/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.dao;

import Model.Favoritos;
import java.util.List;

/**
 *
 * @author aylee
 */
public interface FavoritosDAO {
    
    void guardarFavorito(Favoritos favorito);
    List<Favoritos> cargarFavoritos();
    List<Favoritos> obtenerPorCliente(String idCliente);
    boolean eliminarFavorito(String idCliente, String idPublicacion);
    boolean existeFavorito(String idCliente, String idPublicacion);
}
