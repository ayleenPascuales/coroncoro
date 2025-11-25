/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Controllers.alojamientoController;
import Controllers.hostController;
import Controllers.userController;
import Model.Alojamiento;
import Model.Cuenta_Anfitrion;
import Model.Favoritos;
import Model.Usuario;
import Model.dao.AlojamientoDAO;
import Model.dao.AlojamientoDAOImpl;
import Model.dao.FavoritosDAO;
import Model.dao.FavoritosDAOImpl;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.JSONArray;
import org.json.JSONObject;
import view.panel_publicaciones;

/**
 *
 * @author aylee
 */
public class menu_main extends javax.swing.JFrame {
    
    private AlojamientoDAO dao;
    private FavoritosDAO favoritosDAO = new FavoritosDAOImpl();
    private Map<String, String> paises = new HashMap<>();
    private List<String> rutasMultiplesArchivos = new ArrayList<>();
    private File[] fotosSeleccionadas;
    private String idUsuarioLogueado;
    /**
     * Creates new form login_main
     */
    public menu_main(String idUsuario) {
        this.idUsuarioLogueado = idUsuario;
        initComponents();
        setLocationRelativeTo(null);
        dao = new AlojamientoDAOImpl(); // inicializa tu DAO
        
        panel_inicio.setLayout(new BoxLayout(panel_inicio, BoxLayout.Y_AXIS));
        publicaciones_propias.setLayout(new BoxLayout(publicaciones_propias, BoxLayout.Y_AXIS));
        panel_favoritos2.setLayout(new BoxLayout(panel_favoritos2, BoxLayout.Y_AXIS));
        panel_crear2.setPreferredSize(new Dimension(828, 898));
        panel_menu_extendido.setVisible(false);
        panel_menu_recojido.setVisible(true); 
        taDescripcion.setLineWrap(true);
        taDescripcion.setWrapStyleWord(true);
        cargarPublicaciones();
        cargarPublicaciones_propias();
        cargarPaisesDesdeAPI();
        cargarComboTiposVivienda();
        
        
        ///////////////////////////////////////
        // Evento: cuando cambias país, cargar ciudades
        cbPais_vivienda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String paisSeleccionado = (String) cbPais_vivienda.getSelectedItem();
                if (paisSeleccionado != null) {
                    String codigo = paises.get(paisSeleccionado);
                    cargarCiudadesDesdeAPI(codigo);
                }
            }
        });
        setVisible(true);
    }
    
   public void animarPanel(JPanel panel, int widthInicial, int widthFinal) {
    new Thread(() -> {
        try {
            if (widthInicial < widthFinal) {
                // Expandir
                for (int i = widthInicial; i <= widthFinal; i++) {
                    Thread.sleep(2);
                    panel.setSize(i, panel.getHeight());
                }
            } else {
                // Contraer
                for (int i = widthInicial; i >= widthFinal; i--) {
                    Thread.sleep(2);
                    panel.setSize(i, panel.getHeight());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }).start();
    }
   
    private void cargarPublicaciones() {
    panel_inicio.removeAll(); // limpia el panel

    List<Alojamiento> listaDeAlojamientos = dao.cargarAlojamientos();

    for (Alojamiento a : listaDeAlojamientos) {
        panel_publicaciones pub = new panel_publicaciones();

        // Llenar los datos del panel
        pub.setTitulo(a.getDescripcion());
        pub.setPrecio("$" + a.getPrecio_noche());
        pub.setPais(a.getPais());
        pub.setCiudad(a.getCiudad());
        pub.setBarrio(a.getBarrio());
        pub.setDireccion(a.getDireccion());

        if (!a.getFotos().isEmpty()) {
            pub.setImagen(a.getFotos().get(0));
        }

        // Verificar si está en favoritos
        boolean estaFavorito = favoritosDAO.existeFavorito(idUsuarioLogueado, a.getId_alojamiento());
        pub.setFavoritoIcono(estaFavorito);

        // Listener para agregar a favoritos
        pub.addFavoritoListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Favoritos fav = new Favoritos(
                    idUsuarioLogueado,       // usuario logueado
                    a.getId_alojamiento(),   // ID del alojamiento
                    LocalDate.now()          // fecha actual
                );

                favoritosDAO.guardarFavorito(fav);
                JOptionPane.showMessageDialog(null, "Agregado a favoritos ⭐");
            }
        });

        // Listener para abrir detalles al hacer clic en todo el panel
        pub.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Crear y mostrar el panel de detalles en un nuevo JFrame
                Detalles detallesPanel = new Detalles(a); // <-- pasamos el alojamiento actual

                JFrame frame = new JFrame("Detalles del alojamiento");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(detallesPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        // Agregar la publicación al panel principal
        panel_inicio.add(pub);
        panel_inicio.add(Box.createVerticalStrut(10)); // espacio entre publicaciones
    }

    // Refrescar panel
    panel_inicio.revalidate();
    panel_inicio.repaint();
}
                
    
    private void cargarPublicaciones_propias() {
        publicaciones_propias.removeAll(); // limpia el panel

        List<Alojamiento> listaDeAlojamientos = dao.cargarAlojamientos();

        for (Alojamiento a : listaDeAlojamientos) {
            if (!a.getId_alojamiento().equals(idUsuarioLogueado)) {
            continue; // si no es del usuario, no se muestra
        }
             panel_publicaciones pub = new panel_publicaciones();


            pub.setTitulo(a.getDescripcion());
            pub.setPrecio("$" + a.getPrecio_noche());
            pub.setPais(a.getPais());
            pub.setCiudad(a.getCiudad());
            pub.setBarrio(a.getBarrio());
            pub.setDireccion(a.getDireccion());

            if (!a.getFotos().isEmpty()) {
                pub.setImagen(a.getFotos().get(0));
            }
            boolean estaFavorito = favoritosDAO.existeFavorito(idUsuarioLogueado, a.getId_alojamiento());
            pub.setFavoritoIcono(estaFavorito);

            publicaciones_propias.add(pub);
            publicaciones_propias.add(Box.createVerticalStrut(10));
            
            pub.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Crear y mostrar el panel de detalles en un nuevo JFrame
                Detalles detallesPanel = new Detalles(a); // <-- pasamos el alojamiento actual

                JFrame frame = new JFrame("Detalles del alojamiento");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(detallesPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        }
        

        publicaciones_propias.revalidate();
        publicaciones_propias.repaint();
    }
    
    private void cargarFavoritos() {
    panel_favoritos2.removeAll(); 

    List<Favoritos> misFavs = favoritosDAO.obtenerPorCliente(idUsuarioLogueado);
    List<Alojamiento> alojamientos = dao.cargarAlojamientos();

    for (Favoritos fav : misFavs) {

        Alojamiento a = alojamientos.stream()
                .filter(x -> x.getId_alojamiento().equals(fav.getId_alojamiento()))
                .findFirst()
                .orElse(null);

        if (a == null) continue;

        panel_publicaciones pub = new panel_publicaciones();

        pub.setTitulo(a.getDescripcion());
        pub.setPrecio("$" + a.getPrecio_noche());
        pub.setPais(a.getPais());
        pub.setCiudad(a.getCiudad());
        pub.setBarrio(a.getBarrio());
        pub.setDireccion(a.getDireccion());

        if (!a.getFotos().isEmpty()) {
            pub.setImagen(a.getFotos().get(0));
        }
        pub.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Crear y mostrar el panel de detalles en un nuevo JFrame
                Detalles detallesPanel = new Detalles(a); // <-- pasamos el alojamiento actual

                JFrame frame = new JFrame("Detalles del alojamiento");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(detallesPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        panel_favoritos2.add(pub);
        panel_favoritos2.add(Box.createVerticalStrut(10));
    }

        panel_favoritos2.revalidate();
        panel_favoritos2.repaint();
    }
    

    private void cargarPaisesDesdeAPI() {
        try {
            // URL de la API para obtener países (ejemplo ficticio)
            URL url = new URL("https://api.countrystatecity.in/v1/countries");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("X-CSCAPI-KEY", "NVdVUXZPcWJrdFJXa1B4VWJXanlBMlFvZUp3YW9jYUlYbERKU2dhaw==");  // si la API requiere clave

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            JSONArray arr = new JSONArray(sb.toString());
            Set<String> paisesUnicos = new HashSet<>();

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String nombre = obj.getString("name");
                String codigo = obj.getString("iso2");
                paisesUnicos.add(nombre);
                paises.put(nombre, codigo);
            }

            List<String> listaPaisesOrdenada = new ArrayList<>(paisesUnicos);
            Collections.sort(listaPaisesOrdenada, String.CASE_INSENSITIVE_ORDER);

            DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
            for (String pais : listaPaisesOrdenada) {
                modelo.addElement(pais);
            }
            cbPais_vivienda.setModel(modelo);
            cbPais_vivienda.setModel(modelo);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar países: " + e.getMessage());
        }
    }

    private void cargarCiudadesDesdeAPI(String codigoPais) {
        try {
            // URL de la API para obtener ciudades del país dado
            URL url = new URL("https://api.countrystatecity.in/v1/countries/" + codigoPais + "/cities");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("X-CSCAPI-KEY", "NVdVUXZPcWJrdFJXa1B4VWJXanlBMlFvZUp3YW9jYUlYbERKU2dhaw==");  // si la API requiere clave

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            JSONArray arr = new JSONArray(sb.toString());
            Set<String> ciudadesUnicas = new HashSet<>();

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String ciudad = obj.getString("name");
                ciudadesUnicas.add(ciudad);
            }

            List<String> listaCiudadesOrdenada = new ArrayList<>(ciudadesUnicas);
            Collections.sort(listaCiudadesOrdenada, String.CASE_INSENSITIVE_ORDER);

            DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
            for (String ciudad : listaCiudadesOrdenada) {
                modelo.addElement(ciudad);
            }
            cbCiudad_vivienda.setModel(modelo);
            cbCiudad_vivienda.setModel(modelo);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar ciudades: " + e.getMessage());
        }
    }

    public void guardarFotos(Alojamiento alojamiento, File[] fotosSeleccionadas) {
        if (fotosSeleccionadas == null || fotosSeleccionadas.length == 0) {
            return;
        }

        File carpeta = new File("Fotografias");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        for (File foto : fotosSeleccionadas) {
            try {
                File destino = new File(carpeta, UUID.randomUUID() + "_" + foto.getName());
                Files.copy(foto.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Asociar la ruta con el alojamiento
                alojamiento.getFotos().add(destino.getPath());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Guardar los cambios en JSON usando tu DAO
        AlojamientoDAO dao = new AlojamientoDAOImpl();
        dao.modificarAlojamiento(alojamiento);
    }

    private Map<String, List<String>> obtenerTiposDeVivienda() {
        Map<String, List<String>> datos = new LinkedHashMap<>();

        datos.put("Viviendas Clásicas", Arrays.asList(
                "Casa", "Apartamento", "Apartaestudio", "Estudio",
                "Habitación", "Loft", "Duplex", "Triplex",
                "Penthouse", "Mansión", "Villa", "Residencia"
        ));

        datos.put("Viviendas Turísticas", Arrays.asList(
                "Cabaña", "Casa de playa", "Casa campestre", "Finca",
                "Chalet", "Bungalow", "Carpa", "Glamping",
                "Eco Lodge", "Resort", "Casa rural", "Hacienda"
        ));

        datos.put("Viviendas Especiales", Arrays.asList(
                "Tiny House", "Houseboat", "Barco casa", "Yate",
                "Caravana", "Motorhome", "Trailer",
                "Container Home", "Modular Home", "Prefabricada"
        ));

        datos.put("Urbanas", Arrays.asList(
                "Condominio", "Townhouse", "Torre residencial",
                "Suite", "Microapartamento", "Miniloft"
        ));

        datos.put("Exóticas", Arrays.asList(
                "Igloo", "Castillo", "Casa en el árbol",
                "Tipi", "Yurta"
        ));

        return datos;
    }

    private void cargarComboTiposVivienda() {

        Map<String, List<String>> datos = obtenerTiposDeVivienda();
        cbTipo_vivienda.removeAllItems(); // Limpia el combo
        cbTipo_vivienda.addItem("Seleccione un tipo de vivienda");

        for (String categoria : datos.keySet()) {
            // Agregar categoría como separador visual
            cbTipo_vivienda.addItem("----- " + categoria.toUpperCase() + " -----");
            // Agregar tipos reales
            for (String tipo : datos.get(categoria)) {
                cbTipo_vivienda.addItem(tipo);
            }
        }
    }
    
    public void crear_publicacion(){
        String alojPais = (cbPais_vivienda.getSelectedItem() != null) ? cbPais_vivienda.getSelectedItem().toString() : "";
        String alojCiudad = (cbCiudad_vivienda.getSelectedItem() != null) ? cbCiudad_vivienda.getSelectedItem().toString() : "";
        String alojBarrio = txtBarrio_vivienda.getText().trim();
        String alojDireccion = txtDireccion_vivienda.getText().trim();
        String alojDescripcion = taDescripcion.getText().trim();
        String titulo = txtTitulo_vivienda.getText().trim();

        // Características (Valores Numéricos de Spinners)
        int valCapacidad = (Integer) jsCapacidad.getValue();
        int valHabitaciones = (Integer) jsHabitaciones.getValue();
        int valBanos = (Integer) jsBaños.getValue();

        // Convertimos a String para el Objeto Alojamiento final
        String capacidad = String.valueOf(valCapacidad);
        String numHabitaciones = String.valueOf(valHabitaciones);
        String numBanos = String.valueOf(valBanos);

        String tipoVivienda = ((String) cbTipo_vivienda.getSelectedItem());

        // Precio
        String moneda = txtMoneda.getText().trim();
        double alojPrecio = 0;
        try {
            alojPrecio = Double.parseDouble(txtPrecio.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un valor numérico válido.");
            return ;
        }

        // Servicios (Desde ComboBox usando método auxiliar esSi)
        boolean tienePiscina = esSi(cbPiscina);
        boolean tieneParrilla = esSi(cbParrilla);
        boolean tieneMascotas = esSi(cbMascotas);
        boolean tieneParques = esSi(cbParques);
        boolean tieneBalcon = esSi(cbBalcon);
        boolean tieneAguaCaliente = esSi(cbAgua);
        boolean tienePocaMovilidad = esSi(cb_movilidad);
        boolean tieneVigilancia = esSi(cb_vigilancia);
        boolean tieneConjunto = esSi(cbConjunto);

        boolean disponible = true;

        // =================================================================================
        // 5. VALIDACIONES LÓGICAS DEL ALOJAMIENTO (NUEVAS)
        // =================================================================================
        // Ubicación Completa
        if (alojPais.isEmpty() || alojPais.equals("Seleccione") || alojCiudad.isEmpty() || alojDireccion.isEmpty() || alojBarrio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar la ubicación del alojamiento (País, Ciudad, Barrio y Dirección).", "Ubicación Incompleta", JOptionPane.WARNING_MESSAGE);
            return ;
        }

        if (!alojBarrio.matches("[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\\s.-]+")) {
            JOptionPane.showMessageDialog(this, "El nombre del barrio contiene caracteres no válidos.", "Error en Barrio", JOptionPane.WARNING_MESSAGE);
            return ;
        }

        if (!alojDireccion.matches("[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\\s#.,\\-/()]+")) {
            JOptionPane.showMessageDialog(this, "La dirección contiene caracteres no válidos.", "Error en Dirección", JOptionPane.WARNING_MESSAGE);
        return ;
        }

        //VALIDAR DESCRIPCION
        if (alojDescripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La descripción no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
            return ;
        }
        if (alojDescripcion.length() < 20 && alojDescripcion.length() > 500) {
            JOptionPane.showMessageDialog(this, "La descripción debe tener como minimo 20 caracteres y como maximo 500 caracteres", "Error en la descripción", JOptionPane.WARNING_MESSAGE);
            return ;
        }

        // Cantidades Positivas (Capacidad, Habitaciones, Baños)
        if (valCapacidad <= 0) {
            JOptionPane.showMessageDialog(this, "La capacidad máxima debe ser al menos 1 persona.", "Error en Capacidad", JOptionPane.WARNING_MESSAGE);
            return ;
        }
        if (valHabitaciones <= 0) {
            JOptionPane.showMessageDialog(this, "El número de habitaciones no puede ser 0 ni negativo.", "Error en Habitaciones", JOptionPane.WARNING_MESSAGE);
            return ;
        }
        if (valBanos <= 0) {
            JOptionPane.showMessageDialog(this, "El número de baños no puede ser 0 ni negativo.", "Error en Baños", JOptionPane.WARNING_MESSAGE);
            return ;
        }

        // K. Restricción de Precio (> 0 y <= 100.000)
        if (alojPrecio <= 0) {
            JOptionPane.showMessageDialog(this, "El precio por noche no puede ser gratuito ni negativo.", "Precio Inválido", JOptionPane.WARNING_MESSAGE);
            return ;
        }
        if (alojPrecio > 100000) {
            JOptionPane.showMessageDialog(this, "El precio máximo permitido es de $100,000 COP.", "Precio Excedido", JOptionPane.WARNING_MESSAGE);
            return ;
        }
        
        if (titulo == null || titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El título no puede estar vacío.", "Título inválido", JOptionPane.WARNING_MESSAGE);
        return ;
        }

        if (titulo.length() < 3 || titulo.length() > 90) {
            JOptionPane.showMessageDialog(null,"El título debe tener entre 3 y 90 caracteres.", "Longitud inválida", JOptionPane.WARNING_MESSAGE);
        return ;   
        }

        if (!titulo.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ0-9 .,!?-]+")) {
            JOptionPane.showMessageDialog(null, "El título contiene caracteres no permitidos.", "Caracteres inválidos", JOptionPane.WARNING_MESSAGE);
        return ;
        }
        
        if (cbTipo_vivienda == null || cbTipo_vivienda.equals("Seleccione un tipo de vivienda")) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un tipo de vivienda válido.",
                    "Error en Tipo de Vivienda",
                    JOptionPane.WARNING_MESSAGE);
            return ;
        }
        DecimalFormat df = new DecimalFormat("#,###.##");
        String precioCompleto = df.format(alojPrecio) + " " + moneda;

        // =================================================================================
        // 6. GUARDADO Y PERSISTENCIA
        // =================================================================================
        try {
            alojamientoController alojCtrl = new alojamientoController();
            // 3. Crear Alojamiento
            Alojamiento nuevoAlojamiento = new Alojamiento(
                    this.idUsuarioLogueado, titulo, // ID Dueño
                    alojPais, alojCiudad, alojBarrio, alojDireccion, alojDescripcion,
                    capacidad, numHabitaciones, numBanos, tipoVivienda,
                    rutasMultiplesArchivos,
                    disponible,
                    tienePiscina, tieneParrilla, tieneMascotas, tieneParques,
                    tieneBalcon, tieneAguaCaliente, tienePocaMovilidad,
                    tieneVigilancia, tieneConjunto,
                    precioCompleto
            );

            alojCtrl.guardarAlojamiento(nuevoAlojamiento);

            JOptionPane.showMessageDialog(this, "¡Registro de Alojamiento Exitoso!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            return ;

        }catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error crítico al guardar: " + e.getMessage(), "Error Interno", JOptionPane.ERROR_MESSAGE);
            return ;
        }
    }
    
    //METODO PARA LOS COMBO DE TRUE O FALSE
    private boolean esSi(javax.swing.JComboBox<String> combo) {
        if (combo.getSelectedItem() == null) {
            return false;
        }
        String valor = combo.getSelectedItem().toString();
        return valor.equalsIgnoreCase("Si") || valor.equalsIgnoreCase("Sí");
    }
    
    public void limpiarCampos() {

        txtTitulo_vivienda.setText("");
        taDescripcion.setText("");
        cbPais_vivienda.setSelectedIndex(0);
        cbCiudad_vivienda.setSelectedIndex(0);
        cbTipo_vivienda.setSelectedItem(0);
        txtBarrio_vivienda.setText("");
        txtDireccion_vivienda.setText("");
        jsCapacidad.setValue(0);
        jsHabitaciones.setValue(0);
        jsBaños.setValue(0);
        cbTipo_vivienda.setSelectedIndex(0);
        txtPrecio.setText("");

    }


   
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        boton_menu = new javax.swing.JLabel();
        panel_menu_recojido = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        crear_publicacion = new javax.swing.JLabel();
        bandeja_entrada = new javax.swing.JLabel();
        mis_publicaciones = new javax.swing.JLabel();
        favs = new javax.swing.JLabel();
        reservas = new javax.swing.JLabel();
        favs1 = new javax.swing.JLabel();
        panel_menu_extendido = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panel_cbandeja = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        panel_inicio = new javax.swing.JPanel();
        panel_crear = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panel_crear2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        cbPais_vivienda = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cbCiudad_vivienda = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txtBarrio_vivienda = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtTitulo_vivienda = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jsCapacidad = new com.toedter.components.JSpinField();
        jLabel31 = new javax.swing.JLabel();
        jsHabitaciones = new com.toedter.components.JSpinField();
        jLabel30 = new javax.swing.JLabel();
        jsBaños = new com.toedter.components.JSpinField();
        jLabel29 = new javax.swing.JLabel();
        cbTipo_vivienda = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        taDescripcion = new javax.swing.JTextArea();
        Fotos = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        lbVista_Previa = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        cbPiscina = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        cbParrilla = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        cbMascotas = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        cbParques = new javax.swing.JComboBox<>();
        jLabel42 = new javax.swing.JLabel();
        cbBalcon = new javax.swing.JComboBox<>();
        jLabel43 = new javax.swing.JLabel();
        cbAgua = new javax.swing.JComboBox<>();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        cb_movilidad = new javax.swing.JComboBox<>();
        jLabel47 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        cb_vigilancia = new javax.swing.JComboBox<>();
        jLabel46 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        cbConjunto = new javax.swing.JComboBox<>();
        guardar_publicacion = new javax.swing.JButton();
        txtDireccion_vivienda = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtMoneda = new javax.swing.JTextField();
        panel_mis_publicaciones = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        panel_reservas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        publicaciones_propias = new javax.swing.JPanel();
        panel_favoritos = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        panel_favoritos2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu.setBackground(new java.awt.Color(255, 255, 255));
        menu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        boton_menu.setBackground(new java.awt.Color(255, 255, 255));
        boton_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/menu.png"))); // NOI18N
        boton_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boton_menuMouseClicked(evt);
            }
        });
        menu.add(boton_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 60, 40));

        jPanel1.add(menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 40));

        panel_menu_recojido.setBackground(new java.awt.Color(255, 255, 255));
        panel_menu_recojido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel_menu_recojido.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panel_menu_recojido.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 30));

        crear_publicacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/boton-mas (2).png"))); // NOI18N
        crear_publicacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                crear_publicacionMouseClicked(evt);
            }
        });
        panel_menu_recojido.add(crear_publicacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 60));

        bandeja_entrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hogar (3).png"))); // NOI18N
        bandeja_entrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bandeja_entradaMouseClicked(evt);
            }
        });
        panel_menu_recojido.add(bandeja_entrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, -1, 50));

        mis_publicaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/llave (1)_1.png"))); // NOI18N
        mis_publicaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mis_publicacionesMouseClicked(evt);
            }
        });
        panel_menu_recojido.add(mis_publicaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 70, 60));

        favs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/corazon.png"))); // NOI18N
        favs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                favsMouseClicked(evt);
            }
        });
        panel_menu_recojido.add(favs, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, -1, 50));

        reservas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/calendario (1).png"))); // NOI18N
        reservas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reservasMouseClicked(evt);
            }
        });
        panel_menu_recojido.add(reservas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, -1, 50));

        favs1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/corazon.png"))); // NOI18N
        favs1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                favs1MouseClicked(evt);
            }
        });
        panel_menu_recojido.add(favs1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, -1, 50));

        jPanel1.add(panel_menu_recojido, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 70, 550));

        panel_menu_extendido.setBackground(new java.awt.Color(255, 255, 255));
        panel_menu_extendido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel_menu_extendido.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/boton-mas (2).png"))); // NOI18N
        jLabel2.setText("CREAR PUBLICACION");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        panel_menu_extendido.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, 60));

        jLabel5.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hogar (3).png"))); // NOI18N
        jLabel5.setText("INICIO");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        panel_menu_extendido.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 210, 50));

        jLabel6.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/llave (1)_1.png"))); // NOI18N
        jLabel6.setText("PUBLICACIONES");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        panel_menu_extendido.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 210, 60));

        jLabel3.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/corazon.png"))); // NOI18N
        jLabel3.setText("FAVORITOS");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        panel_menu_extendido.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 210, 50));

        jLabel8.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/calendario (1).png"))); // NOI18N
        jLabel8.setText("RESERVAS");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        panel_menu_extendido.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 210, 50));

        jPanel1.add(panel_menu_extendido, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 220, 550));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        panel_cbandeja.setBackground(new java.awt.Color(255, 255, 255));
        panel_cbandeja.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_inicio.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panel_inicioLayout = new javax.swing.GroupLayout(panel_inicio);
        panel_inicio.setLayout(panel_inicioLayout);
        panel_inicioLayout.setHorizontalGroup(
            panel_inicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 828, Short.MAX_VALUE)
        );
        panel_inicioLayout.setVerticalGroup(
            panel_inicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 558, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(panel_inicio);

        panel_cbandeja.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 560));

        jTabbedPane1.addTab("tab1", panel_cbandeja);

        panel_crear.setBackground(new java.awt.Color(255, 255, 255));
        panel_crear.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_crear2.setBackground(new java.awt.Color(255, 255, 255));
        panel_crear2.setPreferredSize(new java.awt.Dimension(828, 775));
        panel_crear2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jLabel4.setText("CREAR PUBLICACION");
        panel_crear2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 210, 30));

        jLabel27.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel27.setText("Pais donde esta ubicada:");
        panel_crear2.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 180, 20));

        panel_crear2.add(cbPais_vivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 180, -1));

        jLabel12.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel12.setText("Ciudad donde esta ubicada");
        panel_crear2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 190, 20));

        panel_crear2.add(cbCiudad_vivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 340, 180, -1));

        jLabel17.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel17.setText("Barrio donde esta ubicada:");
        panel_crear2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, 190, 20));

        txtBarrio_vivienda.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        panel_crear2.add(txtBarrio_vivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, 180, -1));

        jLabel21.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel21.setText("Titulo de la publicacion:");
        panel_crear2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 180, 20));

        txtTitulo_vivienda.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        panel_crear2.add(txtTitulo_vivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 660, -1));

        jLabel7.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel7.setText("Capacidad maxima:");
        panel_crear2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 230, 180, 20));
        panel_crear2.add(jsCapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, 170, 30));

        jLabel31.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel31.setText("numero de habitaciones:");
        panel_crear2.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 310, 180, 20));
        panel_crear2.add(jsHabitaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 340, 170, 30));

        jLabel30.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel30.setText("numero de baños:");
        panel_crear2.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 390, 180, 20));
        panel_crear2.add(jsBaños, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 420, 170, 30));

        jLabel29.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel29.setText("Tipo de vivienda:");
        panel_crear2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 470, 130, 20));

        panel_crear2.add(cbTipo_vivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 500, 170, 30));

        jLabel32.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel32.setText("Precio por noche:");
        panel_crear2.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 230, 190, 20));

        txtPrecio.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        panel_crear2.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 260, 170, -1));

        jLabel38.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel38.setText("Pequeña descripcion:");
        panel_crear2.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 320, 180, 20));

        taDescripcion.setColumns(20);
        taDescripcion.setRows(5);
        jScrollPane6.setViewportView(taDescripcion);

        panel_crear2.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 350, 220, 100));

        Fotos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mas.png"))); // NOI18N
        Fotos.setText("jLabel2");
        Fotos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        Fotos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FotosMouseClicked(evt);
            }
        });
        panel_crear2.add(Fotos, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 500, 70, 70));

        jLabel34.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel34.setText("Añadir fotos");
        panel_crear2.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 460, 90, 20));

        lbVista_Previa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panel_crear2.add(lbVista_Previa, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 480, 130, 100));

        jLabel37.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel37.setText("¿Tiene piscina?");
        panel_crear2.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 470, 130, 20));

        cbPiscina.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbPiscina.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        panel_crear2.add(cbPiscina, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 500, 170, -1));

        jLabel39.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel39.setText("¿Tiene parrilla?");
        panel_crear2.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 550, 130, 20));

        cbParrilla.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbParrilla.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        panel_crear2.add(cbParrilla, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 580, 170, -1));

        jLabel40.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel40.setText("¿Se permiten mascotas?");
        panel_crear2.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 560, 170, -1));

        cbMascotas.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbMascotas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        panel_crear2.add(cbMascotas, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 590, 170, -1));

        jLabel48.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel48.setText("¿Tiene parque ");
        panel_crear2.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 630, 110, 20));

        jLabel41.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel41.setText("o zonas verdes?");
        panel_crear2.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 650, 110, 20));

        cbParques.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbParques.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        panel_crear2.add(cbParques, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 680, 170, -1));

        jLabel42.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel42.setText("¿Tiene balcon?");
        panel_crear2.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 720, 130, 20));

        cbBalcon.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbBalcon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        panel_crear2.add(cbBalcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 750, 170, -1));

        jLabel43.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel43.setText("¿Tiene calefaccion?");
        panel_crear2.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 640, 160, 20));

        cbAgua.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbAgua.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        panel_crear2.add(cbAgua, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 670, 170, -1));

        jLabel50.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel50.setText("¿Tiene acceso para ");
        panel_crear2.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 720, 140, -1));

        jLabel51.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel51.setText("personas con movilidad");
        panel_crear2.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 740, 170, -1));

        jLabel49.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel49.setText("reducida o dificultada?");
        panel_crear2.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 760, 170, -1));

        cb_movilidad.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cb_movilidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        panel_crear2.add(cb_movilidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 790, 170, 30));

        jLabel47.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel47.setText("¿Cuenta con vigilancia ");
        panel_crear2.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 610, -1, 20));

        jLabel44.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel44.setText("o zona de porteria?");
        panel_crear2.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 630, 140, -1));

        cb_vigilancia.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cb_vigilancia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        panel_crear2.add(cb_vigilancia, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 660, 170, 30));

        jLabel46.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel46.setText("¿Esta dentro de un");
        panel_crear2.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 700, 140, -1));

        jLabel45.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel45.setText("conjunto cerrado?");
        panel_crear2.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 720, 140, -1));

        cbConjunto.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbConjunto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        panel_crear2.add(cbConjunto, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 750, 170, 30));

        guardar_publicacion.setFont(new java.awt.Font("Ebrima", 3, 17)); // NOI18N
        guardar_publicacion.setText("PUBLICAR");
        guardar_publicacion.setBorder(null);
        guardar_publicacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guardar_publicacionMouseClicked(evt);
            }
        });
        panel_crear2.add(guardar_publicacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 850, 120, 30));

        txtDireccion_vivienda.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        panel_crear2.add(txtDireccion_vivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 660, -1));

        jLabel22.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel22.setText("Direccion:");
        panel_crear2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 180, 20));

        txtMoneda.setEditable(false);
        txtMoneda.setBackground(new java.awt.Color(255, 255, 255));
        txtMoneda.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        txtMoneda.setText("USD");
        panel_crear2.add(txtMoneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 260, 50, -1));

        jScrollPane2.setViewportView(panel_crear2);

        panel_crear.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 560));

        jTabbedPane1.addTab("tab2", panel_crear);

        panel_mis_publicaciones.setBackground(new java.awt.Color(255, 255, 255));
        panel_mis_publicaciones.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jScrollPane4.setViewportView(jPanel8);

        panel_mis_publicaciones.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 560));

        jTabbedPane1.addTab("tab3", panel_mis_publicaciones);

        panel_reservas.setBackground(new java.awt.Color(255, 255, 255));
        panel_reservas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        publicaciones_propias.setBackground(new java.awt.Color(255, 255, 255));
        publicaciones_propias.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jScrollPane1.setViewportView(publicaciones_propias);

        panel_reservas.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 560));

        jTabbedPane1.addTab("tab4", panel_reservas);

        panel_favoritos.setBackground(new java.awt.Color(255, 255, 255));
        panel_favoritos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_favoritos2.setBackground(new java.awt.Color(255, 255, 255));
        panel_favoritos2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jScrollPane5.setViewportView(panel_favoritos2);

        panel_favoritos.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 560));

        jTabbedPane1.addTab("tab5", panel_favoritos);

        jPanel2.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 600));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 830, 590));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boton_menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boton_menuMouseClicked
        if (panel_menu_extendido.isVisible()) {
        
        animarPanel(panel_menu_extendido, 220, 220);

        panel_menu_extendido.setVisible(false);
        panel_menu_recojido.setVisible(true);

    } else {
        panel_menu_extendido.setVisible(true);
        panel_menu_recojido.setVisible(false);

        animarPanel(panel_menu_extendido, 70, 220);
    }
    }//GEN-LAST:event_boton_menuMouseClicked

    private void crear_publicacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_crear_publicacionMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_crear_publicacionMouseClicked

    private void bandeja_entradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bandeja_entradaMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
        cargarPublicaciones();
    }//GEN-LAST:event_bandeja_entradaMouseClicked

    private void mis_publicacionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mis_publicacionesMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(3);
        cargarPublicaciones_propias();
    }//GEN-LAST:event_mis_publicacionesMouseClicked

    private void favsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_favsMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(4);
        cargarFavoritos();
    }//GEN-LAST:event_favsMouseClicked

    private void guardar_publicacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardar_publicacionMouseClicked
        // TODO add your handling code here:
        crear_publicacion();
    }//GEN-LAST:event_guardar_publicacionMouseClicked

    private void FotosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FotosMouseClicked
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);

        // Filtro para imágenes 
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                "Imágenes (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filtro);

        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {

            // Guardamos los archivos seleccionados en la variable global
            fotosSeleccionadas = fileChooser.getSelectedFiles();

            rutasMultiplesArchivos.clear();

            for (int i = 0; i < fotosSeleccionadas.length; i++) {
                File archivo = fotosSeleccionadas[i];
                rutasMultiplesArchivos.add(archivo.getAbsolutePath());

                // Solo vista previa de la primera foto
                if (i == 0) {
                    try {
                        ImageIcon iconoOriginal = new ImageIcon(archivo.getAbsolutePath());

                        int ancho = lbVista_Previa.getWidth();
                        int alto = lbVista_Previa.getHeight();

                        Image imagenEscalada = iconoOriginal.getImage()
                                .getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

                        lbVista_Previa.setIcon(new ImageIcon(imagenEscalada));

                    } catch (Exception ex) {
                        System.err.println("Error mostrando vista previa: " + ex.getMessage());
                    }
                }
            }

            JOptionPane.showMessageDialog(this,
                    fotosSeleccionadas.length + " fotos seleccionadas.");
        }
    }//GEN-LAST:event_FotosMouseClicked

    private void reservasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reservasMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_reservasMouseClicked

    private void favs1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_favs1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_favs1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
        cargarPublicaciones();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(3);
        cargarPublicaciones_propias();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(4);
        cargarFavoritos();
    }//GEN-LAST:event_jLabel3MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(menu_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menu_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menu_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menu_main("").setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fotos;
    private javax.swing.JLabel bandeja_entrada;
    private javax.swing.JLabel boton_menu;
    private javax.swing.JComboBox<String> cbAgua;
    private javax.swing.JComboBox<String> cbBalcon;
    private javax.swing.JComboBox<String> cbCiudad_vivienda;
    private javax.swing.JComboBox<String> cbConjunto;
    private javax.swing.JComboBox<String> cbMascotas;
    private javax.swing.JComboBox<String> cbPais_vivienda;
    private javax.swing.JComboBox<String> cbParques;
    private javax.swing.JComboBox<String> cbParrilla;
    private javax.swing.JComboBox<String> cbPiscina;
    private javax.swing.JComboBox<String> cbTipo_vivienda;
    private javax.swing.JComboBox<String> cb_movilidad;
    private javax.swing.JComboBox<String> cb_vigilancia;
    private javax.swing.JLabel crear_publicacion;
    private javax.swing.JLabel favs;
    private javax.swing.JLabel favs1;
    private javax.swing.JButton guardar_publicacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.components.JSpinField jsBaños;
    private com.toedter.components.JSpinField jsCapacidad;
    private com.toedter.components.JSpinField jsHabitaciones;
    private javax.swing.JLabel lbVista_Previa;
    private javax.swing.JPanel menu;
    private javax.swing.JLabel mis_publicaciones;
    private javax.swing.JPanel panel_cbandeja;
    private javax.swing.JPanel panel_crear;
    private javax.swing.JPanel panel_crear2;
    private javax.swing.JPanel panel_favoritos;
    private javax.swing.JPanel panel_favoritos2;
    private javax.swing.JPanel panel_inicio;
    private javax.swing.JPanel panel_menu_extendido;
    private javax.swing.JPanel panel_menu_recojido;
    private javax.swing.JPanel panel_mis_publicaciones;
    private javax.swing.JPanel panel_reservas;
    private javax.swing.JPanel publicaciones_propias;
    private javax.swing.JLabel reservas;
    private javax.swing.JTextArea taDescripcion;
    private javax.swing.JTextField txtBarrio_vivienda;
    private javax.swing.JTextField txtDireccion_vivienda;
    private javax.swing.JTextField txtMoneda;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtTitulo_vivienda;
    // End of variables declaration//GEN-END:variables
}
