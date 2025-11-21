/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.dao;

import Estructuras.Lista_reseñas;
import Model.Reseñas;
import java.util.List;

/**
 *
 * @author aylee
 */
public interface ReseñasDAO {
    void guardarReseña(Reseñas reseña);
    List<Reseñas> cargarReseñas();
    Reseñas buscarPorId(String id);
    boolean eliminarReseña(String id);
    boolean modificarReseña(Reseñas reseñaActualizada);
}
