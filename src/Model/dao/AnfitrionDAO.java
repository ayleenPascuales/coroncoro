/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Model.dao;


import Model.Cuenta_Anfitrion;
import java.util.List;

/**
 *
 * @author aylee
 */
public interface AnfitrionDAO {

    void guardarAnfitrion(Cuenta_Anfitrion anfitrion);
    List<Cuenta_Anfitrion> cargarAnfitriones();
    Cuenta_Anfitrion buscarPorId(String id);
    boolean eliminarAnfitrion(String id);
    boolean modificarAnfitrion(Cuenta_Anfitrion anfitrion);
}
