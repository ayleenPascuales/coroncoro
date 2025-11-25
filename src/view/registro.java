/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Controllers.alojamientoController;
import Controllers.clientesController;
import Controllers.hostController;
import Controllers.userController;
import Model.Alojamiento;
import Model.Cuenta_Anfitrion;
import Model.Cuenta_cliente;
import Model.Usuario;
import Model.dao.AlojamientoDAO;
import Model.dao.AlojamientoDAOImpl;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.*;

/**
 *
 * @author aylee
 */
public class registro extends javax.swing.JFrame {

    private Map<String, String> paises = new HashMap<>();
    private List<String> rutasMultiplesArchivos = new ArrayList<>();
    private File[] fotosSeleccionadas;

    /**
     * Creates new form registro
     */
    public registro() {
        initComponents();
        setLocationRelativeTo(null);
        cargarIdiomas();
        cargarPaisesDesdeAPI();
        cargarComboTiposVivienda();
        actualizarCodigoTelefonoYValidar();

        //////////////////////////////////
        
        datosPersonales.setVisible(false);
        datosVivienda.setVisible(false);
        extrasVivienda2.setVisible(false);
        extrasVivienda.setVisible(false);
        Continuar.setVisible(false);
        Volver_tipo.setVisible(false);
        JTextFieldDateEditor editor = (JTextFieldDateEditor) jdNacimiento.getDateEditor();
        editor.setEditable(false);

        taDescripcion.setLineWrap(true);
        taDescripcion.setWrapStyleWord(true);

        estilizarCampo(txtDocumento);
        estilizarCampo(txtNombre);
        estilizarCampo(txtApellido);
        estilizarCampo(codigo_telefono);
        estilizarCampo(txtEmail);
        estilizarCampo(txtBarrio);
        estilizarCampo(txtUsuario);
        estilizarCampo(txtContraseña);
        estilizarCampo(txtMoneda);
        estilizarCampo(txtBarrio_vivienda);
        estilizarCampo(txtDireccion_vivienda);
        estilizarCampo(txtTelefono);
        estilizarCampo(txtDireccion);
        estilizarCampo(txtPrecio);
        estilizarCampo(txtTitulo_vivienda);

        pintarImagenEnPanel(jPanel8, "C:src\\img\\Huespedes.png");
        pintarImagenEnPanel(jPanel9, "C:src\\img\\anfitrion.jpg");
        pintarImagenEnPanel(volver_alogin, "src\\img\\flecha-hacia-atras.png");

        // Evento: cuando cambias país, cargar ciudades
        cbPais.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String paisSeleccionado = (String) cbPais.getSelectedItem();
                if (paisSeleccionado != null) {
                    String codigo = paises.get(paisSeleccionado);
                    cargarCiudadesDesdeAPI(codigo);
                }
            }
        });
        setVisible(true);
    }

    private void cargarIdiomas() {
        Locale[] locales = Locale.getAvailableLocales();
        Set<String> idiomasSet = new HashSet<>();

        // evitar duplicados
        for (Locale loc : locales) {
            String idioma = loc.getDisplayLanguage();
            if (idioma != null && !idioma.isEmpty()) {
                idiomasSet.add(idioma);
            }
        }
        // Convertir y ordenar 
        List<String> idiomasOrdenados = new ArrayList<>(idiomasSet);
        Collections.sort(idiomasOrdenados, String.CASE_INSENSITIVE_ORDER);

        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
        for (String idioma : idiomasOrdenados) {
            modelo.addElement(idioma);
        }

        cbIdioma.setModel(modelo);
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
            cbPais.setModel(modelo);
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
            cbCiudad.setModel(modelo);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDayChooser1 = new com.toedter.calendar.JDayChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Tipo = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        volver_alogin = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtContraseña = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jsEdad = new com.toedter.components.JSpinField();
        txtDocumento = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtBarrio = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();
        cbIdioma = new javax.swing.JComboBox<>();
        cbPais = new javax.swing.JComboBox<>();
        jdNacimiento = new com.toedter.calendar.JDateChooser();
        cbCiudad = new javax.swing.JComboBox<>();
        codigo_telefono = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        T_selected = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taDescripcion = new javax.swing.JTextArea();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        Fotos = new javax.swing.JLabel();
        lbVista_Previa = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        cbConjunto = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        cbPiscina = new javax.swing.JComboBox<>();
        cbParrilla = new javax.swing.JComboBox<>();
        cbMascotas = new javax.swing.JComboBox<>();
        cbParques = new javax.swing.JComboBox<>();
        cbBalcon = new javax.swing.JComboBox<>();
        cbAgua = new javax.swing.JComboBox<>();
        cb_movilidad = new javax.swing.JComboBox<>();
        cb_vigilancia = new javax.swing.JComboBox<>();
        jLabel57 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taDescripcion1 = new javax.swing.JTextArea();
        Fotos1 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        lbVista_Previa1 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        cbPiscina1 = new javax.swing.JComboBox<>();
        jLabel60 = new javax.swing.JLabel();
        cbParrilla1 = new javax.swing.JComboBox<>();
        jLabel61 = new javax.swing.JLabel();
        cbMascotas1 = new javax.swing.JComboBox<>();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        cbParques1 = new javax.swing.JComboBox<>();
        jLabel64 = new javax.swing.JLabel();
        cbBalcon1 = new javax.swing.JComboBox<>();
        cbAgua1 = new javax.swing.JComboBox<>();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        cb_movilidad1 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtBarrio_vivienda = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtDireccion_vivienda = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jsCapacidad = new com.toedter.components.JSpinField();
        jsHabitaciones = new com.toedter.components.JSpinField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jsBaños = new com.toedter.components.JSpinField();
        jLabel31 = new javax.swing.JLabel();
        cbTipo_vivienda = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        txtMoneda = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        cbPais_vivienda = new javax.swing.JComboBox<>();
        cbCiudad_vivienda = new javax.swing.JComboBox<>();
        txtTitulo_vivienda = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        Volver_tipo = new javax.swing.JLabel();
        datosPersonales = new javax.swing.JLabel();
        datosVivienda = new javax.swing.JLabel();
        extrasVivienda2 = new javax.swing.JLabel();
        extrasVivienda = new javax.swing.JLabel();
        Continuar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        datosPersonales1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 698, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 700, 10));

        Tipo.setBackground(new java.awt.Color(255, 255, 255));
        Tipo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel69.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jLabel69.setText("cualquier rincón del mundo .");
        Tipo.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, 280, -1));

        jLabel70.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jLabel70.setText("ANFITRION");
        Tipo.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, 120, -1));

        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Tipo.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, 290, 200));

        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Tipo.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 280, 200));

        jLabel71.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jLabel71.setText("HÚESPED");
        Tipo.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 100, -1));

        jLabel72.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jLabel72.setText("SELECCIONE EL TIPO DE USUARIO");
        Tipo.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 320, -1));

        jLabel73.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jLabel73.setText("estadía una historia para recordar");
        Tipo.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 410, 330, -1));

        jLabel74.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jLabel74.setText(" Tu próxima aventura te espera,");
        Tipo.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 310, -1));

        jLabel75.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jLabel75.setText("encuentra tu hogar temporal en");
        Tipo.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 320, -1));

        jLabel76.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jLabel76.setText("Explora sin límites.");
        Tipo.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 190, -1));

        jLabel77.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jLabel77.setText("Sé parte de la aventura. ");
        Tipo.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 320, 300, -1));

        jLabel78.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jLabel78.setText("Brinda comodidad y seguridad a");
        Tipo.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 350, 310, -1));

        jLabel79.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jLabel79.setText("quienes buscan explorar, y haz de su ");
        Tipo.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 380, 360, -1));

        volver_alogin.setToolTipText("Volver al login");
        volver_alogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                volver_aloginMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout volver_aloginLayout = new javax.swing.GroupLayout(volver_alogin);
        volver_alogin.setLayout(volver_aloginLayout);
        volver_aloginLayout.setHorizontalGroup(
            volver_aloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        volver_aloginLayout.setVerticalGroup(
            volver_aloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        Tipo.add(volver_alogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 40, 40));

        jTabbedPane1.addTab("Tipo", Tipo);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel5.setText("Ingrese su documento:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 160, 20));

        txtContraseña.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel2.add(txtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 300, 180, -1));

        jLabel7.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel7.setText("Ingrese su nombre:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 160, 20));

        jLabel8.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel8.setText("Ingrese su apellido:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 160, 20));

        jLabel9.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel9.setText("Ingrese su edad:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 160, 20));

        jLabel10.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel10.setText("Ingrese su telefono:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 350, 160, 20));

        jLabel11.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel11.setText("Ingrese email:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 160, 20));

        jLabel13.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel13.setText("Ciudad donde vive:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, 160, 20));

        jLabel14.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel14.setText("Ingrese su Barrio:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, 160, 20));

        jLabel15.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel15.setText("Fecha de nacimiento:");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 160, 20));

        jLabel16.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel16.setText("Seleccione que idiomas habla:");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 130, 210, 20));

        jLabel18.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel18.setText("Pais donde vive:");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 160, 20));

        jLabel19.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel19.setText("Ingrese su usuario:");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 200, 160, 20));

        jLabel20.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel20.setText("Ingrese su contraseña:");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 270, 160, 20));
        jPanel2.add(jsEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 180, -1));

        txtDocumento.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel2.add(txtDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 180, -1));

        txtNombre.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 180, -1));

        txtApellido.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel2.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 180, -1));

        txtBarrio.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel2.add(txtBarrio, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 230, 180, -1));

        txtEmail.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel2.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 180, -1));

        txtUsuario.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel2.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 230, 180, -1));

        jPanel2.add(cbIdioma, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 160, 180, -1));

        cbPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPaisActionPerformed(evt);
            }
        });
        jPanel2.add(cbPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 180, -1));
        jPanel2.add(jdNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 180, -1));

        cbCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCiudadActionPerformed(evt);
            }
        });
        jPanel2.add(cbCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, 180, -1));

        codigo_telefono.setEditable(false);
        codigo_telefono.setBackground(new java.awt.Color(255, 255, 255));
        codigo_telefono.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        codigo_telefono.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel2.add(codigo_telefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 380, 50, -1));

        jLabel24.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jLabel24.setText("DATOS PERSONALES");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 200, 20));

        T_selected.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jPanel2.add(T_selected, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, 180, 20));

        txtTelefono.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        txtTelefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTelefonoFocusLost(evt);
            }
        });
        jPanel2.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 380, 140, -1));

        txtDireccion.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel2.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 300, 180, -1));

        jLabel65.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel65.setText("Ingrese su direccion:");
        jPanel2.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 270, 160, 20));

        jTabbedPane1.addTab("tab1", jPanel2);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel34.setText("Añadir fotos");
        jPanel5.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 90, 20));

        taDescripcion.setColumns(20);
        taDescripcion.setRows(5);
        jScrollPane1.setViewportView(taDescripcion);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 220, 100));

        jLabel35.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jLabel35.setText("DATOS ADICIONALES DE LA VIVIENDA");
        jPanel5.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 380, 20));

        jLabel36.setFont(new java.awt.Font("Ebrima", 2, 11)); // NOI18N
        jLabel36.setText("(LLenar estos campos SOLO en caso de que vaya a alquilar una vivienda)");
        jPanel5.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 360, 20));

        jLabel38.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel38.setText("Pequeña descripcion:");
        jPanel5.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 180, 20));

        Fotos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mas.png"))); // NOI18N
        Fotos.setText("jLabel2");
        Fotos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        Fotos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FotosMouseClicked(evt);
            }
        });
        jPanel5.add(Fotos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 70, 70));

        lbVista_Previa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel5.add(lbVista_Previa, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 130, 100));

        jLabel37.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel37.setText("¿Tiene piscina?");
        jPanel5.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 130, 20));

        cbConjunto.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbConjunto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jPanel5.add(cbConjunto, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 360, 170, 30));

        jLabel39.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel39.setText("¿Tiene parrilla?");
        jPanel5.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, 130, 20));

        jLabel40.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel40.setText("¿Se permiten mascotas?");
        jPanel5.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, 170, -1));

        jLabel41.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel41.setText("o zonas verdes?");
        jPanel5.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 230, 110, 20));

        jLabel42.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel42.setText("¿Tiene balcon?");
        jPanel5.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 300, 130, 20));

        jLabel43.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel43.setText("¿Tiene calefaccion?");
        jPanel5.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 370, 160, 20));

        jLabel44.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel44.setText("o zona de porteria?");
        jPanel5.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 240, 140, -1));

        jLabel48.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel48.setText("¿Tiene parque ");
        jPanel5.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 110, 20));

        jLabel47.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel47.setText("¿Cuenta con vigilancia ");
        jPanel5.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 220, -1, 20));

        jLabel49.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel49.setText("reducida o dificultada?");
        jPanel5.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 150, 170, -1));

        jLabel50.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel50.setText("¿Tiene acceso para ");
        jPanel5.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, 140, -1));

        jLabel51.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel51.setText("personas con movilidad");
        jPanel5.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 170, -1));

        jLabel45.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel45.setText("conjunto cerrado?");
        jPanel5.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 330, 140, -1));

        jLabel46.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel46.setText("¿Esta dentro de un");
        jPanel5.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 310, 140, -1));

        cbPiscina.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbPiscina.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jPanel5.add(cbPiscina, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 170, -1));

        cbParrilla.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbParrilla.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jPanel5.add(cbParrilla, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 170, -1));

        cbMascotas.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbMascotas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jPanel5.add(cbMascotas, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, 170, -1));

        cbParques.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbParques.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jPanel5.add(cbParques, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 260, 170, -1));

        cbBalcon.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbBalcon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jPanel5.add(cbBalcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 330, 170, -1));

        cbAgua.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbAgua.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jPanel5.add(cbAgua, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 400, 170, -1));

        cb_movilidad.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cb_movilidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jPanel5.add(cb_movilidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 170, 30));

        cb_vigilancia.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cb_vigilancia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jPanel5.add(cb_vigilancia, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 270, 170, 30));

        jLabel57.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel57.setText("Pequeña descripcion:");
        jPanel5.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 180, 20));

        taDescripcion1.setColumns(20);
        taDescripcion1.setRows(5);
        jScrollPane2.setViewportView(taDescripcion1);

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 220, 100));

        Fotos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mas.png"))); // NOI18N
        Fotos1.setText("jLabel2");
        Fotos1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        Fotos1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Fotos1MouseClicked(evt);
            }
        });
        jPanel5.add(Fotos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 70, 70));

        jLabel58.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel58.setText("Añadir fotos");
        jPanel5.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 90, 20));

        lbVista_Previa1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel5.add(lbVista_Previa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 130, 100));

        jLabel59.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel59.setText("¿Tiene piscina?");
        jPanel5.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 130, 20));

        cbPiscina1.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbPiscina1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jPanel5.add(cbPiscina1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 170, -1));

        jLabel60.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel60.setText("¿Tiene parrilla?");
        jPanel5.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, 130, 20));

        cbParrilla1.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbParrilla1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jPanel5.add(cbParrilla1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 170, -1));

        jLabel61.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel61.setText("¿Se permiten mascotas?");
        jPanel5.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, 170, -1));

        cbMascotas1.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbMascotas1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jPanel5.add(cbMascotas1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, 170, -1));

        jLabel62.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel62.setText("¿Tiene parque ");
        jPanel5.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 110, 20));

        jLabel63.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel63.setText("o zonas verdes?");
        jPanel5.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 230, 110, 20));

        cbParques1.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbParques1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jPanel5.add(cbParques1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 260, 170, -1));

        jLabel64.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel64.setText("¿Tiene balcon?");
        jPanel5.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 300, 130, 20));

        cbBalcon1.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbBalcon1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jPanel5.add(cbBalcon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 330, 170, -1));

        cbAgua1.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbAgua1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jPanel5.add(cbAgua1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 400, 170, -1));

        jLabel66.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel66.setText("¿Tiene acceso para ");
        jPanel5.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, 140, -1));

        jLabel67.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel67.setText("personas con movilidad");
        jPanel5.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 170, -1));

        jLabel68.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel68.setText("reducida o dificultada?");
        jPanel5.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 150, 170, -1));

        cb_movilidad1.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cb_movilidad1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Si", "No" }));
        jPanel5.add(cb_movilidad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 170, 30));

        jTabbedPane1.addTab("tab3", jPanel5);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel25.setFont(new java.awt.Font("Ebrima", 2, 11)); // NOI18N
        jLabel25.setText("(LLenar estos campos SOLO en caso de que vaya a alquilar una vivienda)");
        jPanel3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, 360, 20));

        jLabel6.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel6.setText("Capacidad maxima:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 240, 180, 20));

        jLabel12.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel12.setText("Ciudad donde esta ubicada");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 190, 20));

        txtBarrio_vivienda.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        txtBarrio_vivienda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBarrio_viviendaActionPerformed(evt);
            }
        });
        jPanel3.add(txtBarrio_vivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 180, -1));

        jLabel17.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel17.setText("Barrio donde esta ubicada:");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 190, 20));

        txtDireccion_vivienda.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        txtDireccion_vivienda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccion_viviendaActionPerformed(evt);
            }
        });
        jPanel3.add(txtDireccion_vivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 630, -1));

        jLabel21.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel21.setText("Direccion:");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 180, 20));

        jLabel27.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel27.setText("Pais donde esta ubicada:");
        jPanel3.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 180, 20));
        jPanel3.add(jsCapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, 170, -1));
        jPanel3.add(jsHabitaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 330, 170, -1));

        jLabel29.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel29.setText("Tipo de vivienda:");
        jPanel3.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 240, 130, 20));

        jLabel30.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel30.setText("numero de baños:");
        jPanel3.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 370, 180, 20));
        jPanel3.add(jsBaños, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 400, 170, -1));

        jLabel31.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel31.setText("numero de habitaciones:");
        jPanel3.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 300, 180, 20));

        cbTipo_vivienda.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        cbTipo_vivienda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipo_viviendaActionPerformed(evt);
            }
        });
        jPanel3.add(cbTipo_vivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 270, 200, -1));

        jLabel32.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel32.setText("Precio por noche:");
        jPanel3.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 300, 190, 20));

        txtMoneda.setEditable(false);
        txtMoneda.setBackground(new java.awt.Color(255, 255, 255));
        txtMoneda.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        txtMoneda.setText("USD");
        jPanel3.add(txtMoneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 330, 50, -1));

        jLabel33.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jLabel33.setText("DATOS DE LA VIVIENDA");
        jPanel3.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 230, 20));

        jPanel3.add(cbPais_vivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 180, -1));

        jPanel3.add(cbCiudad_vivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 180, -1));

        txtTitulo_vivienda.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel3.add(txtTitulo_vivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 630, -1));

        jLabel80.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel80.setText("Titulo de la publicacion:");
        jPanel3.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 180, 20));

        txtPrecio.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel3.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 330, 150, -1));

        jTabbedPane1.addTab("tab2", jPanel3);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 700, 500));

        Volver_tipo.setFont(new java.awt.Font("Ebrima", 3, 19)); // NOI18N
        Volver_tipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/flecha-hacia-atras-peque.png"))); // NOI18N
        Volver_tipo.setText("VOLVER A SELECCIONAR TIPO");
        Volver_tipo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Volver_tipoMouseClicked(evt);
            }
        });
        jPanel1.add(Volver_tipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 310, 30));

        datosPersonales.setFont(new java.awt.Font("Ebrima", 3, 19)); // NOI18N
        datosPersonales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bola_.png"))); // NOI18N
        datosPersonales.setText(" DATOS PERSONALES");
        datosPersonales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datosPersonalesMouseClicked(evt);
            }
        });
        jPanel1.add(datosPersonales, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 250, 30));

        datosVivienda.setFont(new java.awt.Font("Ebrima", 3, 19)); // NOI18N
        datosVivienda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bola_.png"))); // NOI18N
        datosVivienda.setText(" DATOS DE LA VIVIENDA");
        datosVivienda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datosViviendaMouseClicked(evt);
            }
        });
        jPanel1.add(datosVivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, 30));

        extrasVivienda2.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        extrasVivienda2.setText("VIVIENDA");
        extrasVivienda2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                extrasVivienda2MouseClicked(evt);
            }
        });
        jPanel1.add(extrasVivienda2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, 200, -1));

        extrasVivienda.setFont(new java.awt.Font("Ebrima", 3, 19)); // NOI18N
        extrasVivienda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bola_.png"))); // NOI18N
        extrasVivienda.setText(" DATOS EXTRAS DE LA");
        extrasVivienda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                extrasViviendaMouseClicked(evt);
            }
        });
        jPanel1.add(extrasVivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, 30));

        Continuar.setFont(new java.awt.Font("Ebrima", 3, 19)); // NOI18N
        Continuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bola_.png"))); // NOI18N
        Continuar.setText("CONTINUAR");
        Continuar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ContinuarMouseClicked(evt);
            }
        });
        jPanel1.add(Continuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 170, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/registro.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 330, 500));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -40, -1, -1));

        datosPersonales1.setFont(new java.awt.Font("Ebrima", 3, 19)); // NOI18N
        datosPersonales1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bola_.png"))); // NOI18N
        datosPersonales1.setText(" DATOS PERSONALES");
        datosPersonales1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datosPersonales1MouseClicked(evt);
            }
        });
        jPanel1.add(datosPersonales1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 250, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ContinuarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ContinuarMouseClicked
        if (T_selected.getText().equals("(HÚESPED)")) {
            if (registrarHuesped()) {
                login volver = new login();
                volver.setVisible(true);
                this.setVisible(false);
            }
        } else if (T_selected.getText().equals("(ANFITRIÓN)")) {
            if (registrarAnfitrionCompleto()) {
                login volver = new login();
                volver.setVisible(true);
                this.setVisible(false);
            }
        }
    }//GEN-LAST:event_ContinuarMouseClicked

    private boolean registrarHuesped() {

        // --- 1. CAPTURA DE DATOS ---
        String documento = txtDocumento.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String codigo_pais = codigo_telefono.getText().trim();
        String num_telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String barrio = txtBarrio.getText().trim();
        String direccion = txtBarrio.getText().trim();
        String user = txtUsuario.getText().trim();
        String pass = txtContraseña.getText().trim();

        int edadIngresada = jsEdad.getValue();

        LocalDate fechaNacimiento = null;
        try {
            Date date = jdNacimiento.getDate();
            if (date != null) {
                fechaNacimiento = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
        } catch (Exception e) {
        }

        String ciudadVivienda = (cbCiudad.getSelectedItem() != null) ? cbCiudad.getSelectedItem().toString() : "";
        String idiomas = (cbIdioma.getSelectedItem() != null) ? cbIdioma.getSelectedItem().toString() : "";

        // =================================================================================
        // BLOQUE DE VALIDACIONES
        // =================================================================================
        if (documento.isEmpty() || !documento.matches("\\d{7,10}") || documento.matches("0+")) {
            JOptionPane.showMessageDialog(this, "El Documento es obligatorio, debe tener entre 7 y 10 dígitos y no puede ser solo ceros.",
                    "Error en Documento",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (nombre.isEmpty() || !nombre.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+")) {
            JOptionPane.showMessageDialog(this, "El Nombre es obligatorio y no puede contener números ni caracteres especiales.",
                    "Error en Nombre", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (apellido.isEmpty() || !apellido.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+")) {
            JOptionPane.showMessageDialog(this, "El Apellido es obligatorio y no puede contener números ni caracteres especiales.",
                    "Error en Apellido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (edadIngresada <= 0) {
            JOptionPane.showMessageDialog(this,
                    "La edad no puede ser menor o igual a cero.", "Error en Edad", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (edadIngresada > 120) {
            JOptionPane.showMessageDialog(this, "La edad ingresada no es válida.", "Error en Edad", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (jdNacimiento.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fecha de nacimiento.", "Error en Fecha", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        int edadCalculada = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if (edadCalculada < 18) {
            JOptionPane.showMessageDialog(this, "Debe tener al menos 18 años para registrarse.", "Restricción de Edad", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (edadIngresada != edadCalculada) {
            JOptionPane.showMessageDialog(this, "Inconsistencia: La edad ingresada(" + edadIngresada + ")no coincide con la calculada según la fecha de nacimiento "
                    + "(" + edadCalculada + " años).", "Error de Datos", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (num_telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El número de teléfono es obligatorio.", "Error en Teléfono", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!num_telefono.matches("\\d{7,15}")) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido (solo dígitos, entre 7 y 15).", "Error en Teléfono", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (email.isEmpty() || email.contains(" ")) {
            JOptionPane.showMessageDialog(this, "El correo electrónico es obligatorio y el correo electrónico no puede contener espacios.", "Error en Email", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,10}$")) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo electrónico válido (ej: usuario@dominio.com).", "Error en Email", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (email.chars().filter(c -> c == '@').count() != 1 || email.startsWith("@") || email.endsWith("@")) {
            JOptionPane.showMessageDialog(this, "El correo electrónico debe contener exactamente un símbolo '@' y no puede iniciar o terminar con '@'. ", "Error en Email", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (barrio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El barrio es obligatorio.", "Error en Barrio", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!barrio.matches("[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\\s.-]+")) {
            JOptionPane.showMessageDialog(this, "El nombre del barrio contiene caracteres no válidos.", "Error en Barrio", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La dirección es obligatoria.", "Error en Dirección", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!direccion.matches("[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\\s#.,\\-/()]+")) {
            JOptionPane.showMessageDialog(this, "La dirección contiene caracteres no válidos.", "Error en Dirección", JOptionPane.WARNING_MESSAGE);
        return false;
        }

        String passwordRegex = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";
        if (!pass.matches(passwordRegex)) {
            JOptionPane.showMessageDialog(this,
                    "La contraseña debe tener:\n- Mínimo 8 caracteres\n- Al menos un número\n- Al menos un símbolo (!@#$%, etc.).",
                    "Contraseña Insegura", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (user.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de Usuario es obligatorio.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String telefonoCompleto = codigo_pais + num_telefono;

        // =================================================================================
        // VALIDACIÓN DE UNICIDAD Y GUARDADO
        // =================================================================================
        try {
            userController userCtrl = new userController();
            clientesController clienteCtrl = new clientesController();

            // 1. VALIDAR DOCUMENTO DUPLICADO EN CLIENTES
            if (clienteCtrl.buscarCliente(documento) != null) {
                JOptionPane.showMessageDialog(this,
                        "El número de documento " + documento + " ya se encuentra registrado.",
                        "Cliente Existente", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            java.util.List<Model.Cuenta_cliente> listaClientes = clienteCtrl.listarClientes();
            if (listaClientes != null) {
                for (Model.Cuenta_cliente c : listaClientes) {
                    if (c.getEmail().equalsIgnoreCase(email)) {
                        JOptionPane.showMessageDialog(this, "El correo electrónico " + email + " ya está registrado.", "Correo Duplicado", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    if (c.getTelefono().equals(telefonoCompleto)) {
                        JOptionPane.showMessageDialog(this, "El número de teléfono " + telefonoCompleto + " ya está registrado.", "Teléfono Duplicado", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }

            // 2. CREAR Y GUARDAR USUARIO (LOGIN)
            Usuario nuevoUsuario = new Usuario(user, pass, "HUESPED");

            if (!userCtrl.registrarUsuarioCompleto(nuevoUsuario)) {
                JOptionPane.showMessageDialog(this, "El nombre de usuario '" + user + "' ya está en uso.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // 3. CREAR Y GUARDAR CLIENTE (PERFIL)
            Cuenta_cliente nuevoCliente = new Cuenta_cliente(
                    nombre,
                    apellido,
                    documento,
                    String.valueOf(edadIngresada),
                    telefonoCompleto,
                    email,
                    barrio,
                    direccion,
                    fechaNacimiento,
                    idiomas,
                    ciudadVivienda
            );

            clienteCtrl.guardarClientes(nuevoCliente);

            JOptionPane.showMessageDialog(this, "¡Registro Exitoso!", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            return true; // Éxito

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error crítico al guardar. Intente nuevamente.", "Error Interno", JOptionPane.ERROR_MESSAGE);
            return false; // Error en la operación
        }
    }

    private boolean registrarAnfitrionCompleto() {
        // =================================================================================
        // 1. VALIDACIÓN PREVIA DE FOTOS (Obligatorio)
        // =================================================================================
        if (rutasMultiplesArchivos == null || rutasMultiplesArchivos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Es obligatorio subir al menos una foto del alojamiento.",
                    "Faltan Imágenes", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // =================================================================================
        // 2. CAPTURA DE DATOS PERSONALES (Usuario)
        // =================================================================================
        String documento = txtDocumento.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String codigo_pais = codigo_telefono.getText().trim();
        String num_telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String barrio = txtBarrio.getText().trim();
        String direccion = txtBarrio.getText().trim();
        String user = txtUsuario.getText().trim();
        String pass = txtContraseña.getText().trim();

        String ciudadVivienda = (cbCiudad.getSelectedItem() != null) ? cbCiudad.getSelectedItem().toString() : "";
        String idiomas = (cbIdioma.getSelectedItem() != null) ? cbIdioma.getSelectedItem().toString() : "Español";

        // Edad y Fecha Nacimiento
        int edadIngresada = (Integer) jsEdad.getValue();
        LocalDate fechaNacimiento = null;
        try {
            if (jdNacimiento.getDate() != null) {
                fechaNacimiento = jdNacimiento.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
        } catch (Exception e) {
        }

        // =================================================================================
        // 3. VALIDACIONES DE DATOS PERSONALES (Importadas de RegistrarHuesped)
        // =================================================================================
        if (documento.isEmpty() || !documento.matches("\\d{7,10}") || documento.matches("0+")) {
            JOptionPane.showMessageDialog(this, "El Documento es obligatorio, debe tener entre 7 y 10 dígitos y no puede ser solo ceros.",
                    "Error en Documento",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (nombre.isEmpty() || !nombre.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+")) {
            JOptionPane.showMessageDialog(this, "El Nombre es obligatorio y no puede contener números ni caracteres especiales.",
                    "Error en Nombre", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (apellido.isEmpty() || !apellido.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+")) {
            JOptionPane.showMessageDialog(this, "El Apellido es obligatorio y no puede contener números ni caracteres especiales.",
                    "Error en Apellido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (edadIngresada <= 0) {
            JOptionPane.showMessageDialog(this,
                    "La edad no puede ser menor o igual a cero.", "Error en Edad", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (edadIngresada > 120) {
            JOptionPane.showMessageDialog(this, "La edad ingresada no es válida.", "Error en Edad", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (jdNacimiento.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fecha de nacimiento.", "Error en Fecha", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        int edadCalculada = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if (edadCalculada < 18) {
            JOptionPane.showMessageDialog(this, "Debe tener al menos 18 años para registrarse.", "Restricción de Edad", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (edadIngresada != edadCalculada) {
            JOptionPane.showMessageDialog(this, "Inconsistencia: La edad ingresada(" + edadIngresada + ")no coincide con la calculada según la fecha de nacimiento "
                    + "(" + edadCalculada + " años).", "Error de Datos", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (num_telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El número de teléfono es obligatorio.", "Error en Teléfono", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!num_telefono.matches("\\d{7,15}")) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido (solo dígitos, entre 7 y 15).", "Error en Teléfono", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (email.isEmpty() || email.contains(" ")) {
            JOptionPane.showMessageDialog(this, "El correo electrónico es obligatorio y el correo electrónico no puede contener espacios.", "Error en Email", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,10}$")) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo electrónico válido (ej: usuario@dominio.com).", "Error en Email", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (email.chars().filter(c -> c == '@').count() != 1 || email.startsWith("@") || email.endsWith("@")) {
            JOptionPane.showMessageDialog(this, "El correo electrónico debe contener exactamente un símbolo '@' y no puede iniciar o terminar con '@'. ", "Error en Email", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (barrio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El barrio es obligatorio.", "Error en Barrio", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!barrio.matches("[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\\s.-]+")) {
            JOptionPane.showMessageDialog(this, "El nombre del barrio contiene caracteres no válidos.", "Error en Barrio", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La dirección es obligatoria.", "Error en Dirección", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!direccion.matches("[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\\s#.,\\-/()]+")) {
            JOptionPane.showMessageDialog(this, "La dirección contiene caracteres no válidos.", "Error en Dirección", JOptionPane.WARNING_MESSAGE);
        return false;
        }

        String passwordRegex = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";
        if (!pass.matches(passwordRegex)) {
            JOptionPane.showMessageDialog(this,
                    "La contraseña debe tener:\n- Mínimo 8 caracteres\n- Al menos un número\n- Al menos un símbolo (!@#$%, etc.).",
                    "Contraseña Insegura", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (user.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre de Usuario es obligatorio.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String telefonoCompleto = codigo_pais + num_telefono;

        // =================================================================================
        // 4. CAPTURA DE DATOS ALOJAMIENTO
        // =================================================================================
        // Ubicación
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
            return false;
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
            return false;
        }

        if (!alojBarrio.matches("[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\\s.-]+")) {
            JOptionPane.showMessageDialog(this, "El nombre del barrio contiene caracteres no válidos.", "Error en Barrio", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!alojDireccion.matches("[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\\s#.,\\-/()]+")) {
            JOptionPane.showMessageDialog(this, "La dirección contiene caracteres no válidos.", "Error en Dirección", JOptionPane.WARNING_MESSAGE);
        return false;
        }

        //VALIDAR DESCRIPCION
        if (alojDescripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La descripción no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (alojDescripcion.length() < 20 && alojDescripcion.length() > 500) {
            JOptionPane.showMessageDialog(this, "La descripción debe tener como minimo 20 caracteres y como maximo 500 caracteres", "Error en la descripción", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Cantidades Positivas (Capacidad, Habitaciones, Baños)
        if (valCapacidad <= 0) {
            JOptionPane.showMessageDialog(this, "La capacidad máxima debe ser al menos 1 persona.", "Error en Capacidad", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (valHabitaciones <= 0) {
            JOptionPane.showMessageDialog(this, "El número de habitaciones no puede ser 0 ni negativo.", "Error en Habitaciones", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (valBanos <= 0) {
            JOptionPane.showMessageDialog(this, "El número de baños no puede ser 0 ni negativo.", "Error en Baños", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // K. Restricción de Precio (> 0 y <= 100.000)
        if (alojPrecio <= 0) {
            JOptionPane.showMessageDialog(this, "El precio por noche no puede ser gratuito ni negativo.", "Precio Inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (alojPrecio > 100000) {
            JOptionPane.showMessageDialog(this, "El precio máximo permitido es de $100,000 COP.", "Precio Excedido", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (titulo == null || titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El título no puede estar vacío.", "Título inválido", JOptionPane.WARNING_MESSAGE);
        return false;
        }

        if (titulo.length() < 3 || titulo.length() > 90) {
            JOptionPane.showMessageDialog(null,"El título debe tener entre 3 y 90 caracteres.", "Longitud inválida", JOptionPane.WARNING_MESSAGE);
        return false;   
        }

        if (!titulo.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ0-9 .,!?-]+")) {
            JOptionPane.showMessageDialog(null, "El título contiene caracteres no permitidos.", "Caracteres inválidos", JOptionPane.WARNING_MESSAGE);
        return false;
        }
        
        if (cbTipo_vivienda == null || cbTipo_vivienda.equals("Seleccione un tipo de vivienda")) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un tipo de vivienda válido.",
                    "Error en Tipo de Vivienda",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        DecimalFormat df = new DecimalFormat("#,###.##");
        String precioCompleto = df.format(alojPrecio) + " " + moneda;

        // =================================================================================
        // 6. GUARDADO Y PERSISTENCIA
        // =================================================================================
        try {
            userController userCtrl = new userController();
            hostController hostCtrl = new hostController();
            alojamientoController alojCtrl = new alojamientoController();

            // L. Verificar si el Anfitrión (Documento) ya existe
            if (hostCtrl.buscarAnfitrion(documento) != null) {
                JOptionPane.showMessageDialog(this,
                        "El número de documento " + documento + " ya se encuentra registrado como anfitrión.",
                        "Anfitrión Existente", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            List<Model.Cuenta_Anfitrion> listaHosts = hostCtrl.listarAnfitriones();
            if (listaHosts != null) {
                for (Model.Cuenta_Anfitrion host : listaHosts) {
                    // 1. Validar Email Duplicado
                    if (host.getEmail().equalsIgnoreCase(email)) {
                        JOptionPane.showMessageDialog(this,
                                "El correo electrónico " + email + " ya está asociado a otra cuenta de anfitrión.",
                                "Correo Duplicado", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    // 2. Validar Teléfono Duplicado
                    if (host.getTelefono().equals(telefonoCompleto)) {
                        JOptionPane.showMessageDialog(this,
                                "El número de teléfono " + telefonoCompleto + " ya está registrado en el sistema.",
                                "Teléfono Duplicado", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }

            // 1. Crear Usuario
            Usuario nuevoUsuario = new Usuario(user, pass, "ANFITRION");

            // 2. Crear Perfil Anfitrión
            Cuenta_Anfitrion nuevoAnfitrion = new Cuenta_Anfitrion(
                    LocalDate.now(),//fecha inicio
                    true, // estado bloqueado o no
                    "0.0", //calificacion
                    nombre,
                    apellido,
                    documento,
                    String.valueOf(edadIngresada),
                    telefonoCompleto,
                    email,
                    barrio, // Barrio Persona
                    direccion,
                    fechaNacimiento,
                    idiomas,
                    ciudadVivienda
            );

            // 3. Crear Alojamiento
            Alojamiento nuevoAlojamiento = new Alojamiento(
                    documento, titulo, // ID Dueño
                    alojPais, alojCiudad, alojBarrio, alojDireccion, alojDescripcion,
                    capacidad, numHabitaciones, numBanos, tipoVivienda,
                    rutasMultiplesArchivos,
                    disponible,
                    tienePiscina, tieneParrilla, tieneMascotas, tieneParques,
                    tieneBalcon, tieneAguaCaliente, tienePocaMovilidad,
                    tieneVigilancia, tieneConjunto,
                    precioCompleto
            );

            // 4. Transacción de Guardado
            // Intentamos registrar usuario primero para validar nombre de usuario único
            if (!userCtrl.registrarUsuarioCompleto(nuevoUsuario)) {
                JOptionPane.showMessageDialog(this, "El nombre de usuario '" + user + "' ya está en uso.", "Error Usuario", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            hostCtrl.guardarAnfitrion(nuevoAnfitrion);
            alojCtrl.guardarAlojamiento(nuevoAlojamiento);

            JOptionPane.showMessageDialog(this, "¡Registro de Anfitrión y Alojamiento Exitoso!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error crítico al guardar: " + e.getMessage(), "Error Interno", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void estilizarCampo(javax.swing.JTextField campo) {
        campo.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(100, 100, 100)), // Línea inferior
                javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5) // Relleno interno (padding)
        ));
        campo.setBackground(new java.awt.Color(0, 0, 0, 0)); // Fondo transparente si usas panel de color
        campo.setOpaque(false);
        campo.setForeground(java.awt.Color.BLACK); // O negro dependiendo del panel
    }

    //METODO PARA LOS COMBO DE TRUE O FALSE
    private boolean esSi(javax.swing.JComboBox<String> combo) {
        if (combo.getSelectedItem() == null) {
            return false;
        }
        String valor = combo.getSelectedItem().toString();
        return valor.equalsIgnoreCase("Si") || valor.equalsIgnoreCase("Sí");
    }

    // Suponiendo que tu ComboBox de País se llama 'cbPaisDondeVive'
// Y tu campo de texto para el número de teléfono es 'txtTelefono'
// Y el JLabel/JTextField para mostrar el código es 'lblCodigoPais' (o txtCodigoPais si prefieres)
    /**
     * Actualiza el código telefónico según el país seleccionado y valida el
     * número ingresado. Este método debe ser llamado desde el evento
     * ItemStateChanged del cbPaisDondeVive y desde el evento FocusLost o
     * KeyReleased del txtTelefono.
     */
    private void actualizarCodigoTelefonoYValidar() {
        String paisSeleccionado = (cbPais.getSelectedItem() != null) ? cbPais.getSelectedItem().toString() : "";
        String numeroTelefono = txtTelefono.getText().trim();

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        // Si no hay país seleccionado o es la opción por defecto
        if (paisSeleccionado.isEmpty() || paisSeleccionado.equals("Seleccione un país")) {
            codigo_telefono.setText("");
            txtTelefono.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.GRAY));
            return;
        }

        // Obtener el código de país (Ej: "CO" para Colombia)
        // Asumo que el cbPaisDondeVive guarda el nombre del país (ej. "Colombia")
        // Necesitas una forma de mapear el nombre del país a su código ISO 3166-1 alpha-2 (ej. "CO")
        String codigoRegion = obtenerCodigoIsoPais(paisSeleccionado); // <-- FUNCIÓN AUXILIAR NECESARIA

        if (codigoRegion == null || codigoRegion.isEmpty()) {
            codigo_telefono.setText("?"); // Si no encontramos el código ISO, mostrar interrogación
            txtTelefono.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED));
            return;
        }

        // 1. Mostrar el código de país en el JLabel
        int countryCode = phoneUtil.getCountryCodeForRegion(codigoRegion);
        if (countryCode != 0) {
            codigo_telefono.setText("+" + countryCode);
        } else {
            codigo_telefono.setText("?");
        }

        // 2. Validar el número de teléfono ingresado
        if (!numeroTelefono.isEmpty()) {
            try {
                // Intentar parsear el número junto con el código de región
                PhoneNumber phoneNumber = phoneUtil.parse(numeroTelefono, codigoRegion);

                if (phoneUtil.isValidNumber(phoneNumber) && phoneUtil.isValidNumberForRegion(phoneNumber, codigoRegion)) {
                    txtTelefono.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.GREEN)); // Válido
                    // Opcional: formatear el número para mostrarlo estándar
                    // txtTelefono.setText(phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
                } else {
                    txtTelefono.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED)); // Inválido
                    JOptionPane.showMessageDialog(this, "El número de teléfono no es válido para " + paisSeleccionado + ".", "Formato Incorrecto", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberParseException e) {
                txtTelefono.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.RED)); // Error de parseo
                JOptionPane.showMessageDialog(this, "El número de teléfono no tiene un formato reconocido.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            txtTelefono.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.GRAY)); // Vacío, estado normal
        }
    }

    /**
     * Función auxiliar para mapear el nombre del país (mostrado en el
     * JComboBox) a su código ISO 3166-1 alpha-2 (ej: "Colombia" -> "CO"). Debes
     * completar esta función con los países que soportes.
     */
    private String obtenerCodigoIsoPais(String nombrePais) {
        if (paises != null && paises.containsKey(nombrePais)) {
            return paises.get(nombrePais);
        }
        return null; // Si el país no está en nuestro mapa
    }

    private void FotosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FotosMouseClicked
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

            //rutasMultiplesArchivos.clear();

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

    /**
     * Carga una imagen desde una ruta, la ajusta al tamaño del JPanel y la
     * muestra.
     *
     * @param panel El JPanel donde quieres mostrar la imagen (ej:
     * pnlFotoPerfil).
     * @param rutaImagen La ruta del archivo (ej: "C:/Users/Foto.jpg").
     */
    private void pintarImagenEnPanel(javax.swing.JPanel panel, String rutaImagen) {
        try {
            // 1. Verificar que la ruta no sea nula ni vacía
            if (rutaImagen == null || rutaImagen.isEmpty()) {
                return;
            }

            // 2. Limpiar el panel de componentes anteriores (ej. viejas fotos)
            panel.removeAll();

            // 3. Cargar la imagen original
            javax.swing.ImageIcon iconoOriginal = new javax.swing.ImageIcon(rutaImagen);
            java.awt.Image imagen = iconoOriginal.getImage();

            // 4. Obtener dimensiones del panel (con protección si aún no es visible)
            int ancho = panel.getWidth();
            int alto = panel.getHeight();

            if (ancho == 0 || alto == 0) {
                // Si el panel aún no se ha dibujado, le damos un tamaño por defecto
                ancho = 150;
                alto = 150;
            }

            // 5. Escalar la imagen al tamaño del panel
            // SCALE_SMOOTH tarda un milisegundo más pero la calidad es mucho mejor
            java.awt.Image imagenEscalada = imagen.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);

            // 6. Crear un JLabel que contenga la imagen
            javax.swing.JLabel labelImagen = new javax.swing.JLabel(new javax.swing.ImageIcon(imagenEscalada));

            // 7. Configurar el panel para que el Label ocupe todo el espacio
            panel.setLayout(new java.awt.BorderLayout());
            panel.add(labelImagen, java.awt.BorderLayout.CENTER);

            // 8. Actualizar la visualización
            panel.revalidate();
            panel.repaint();

        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + e.getMessage());
            javax.swing.JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen: " + rutaImagen);
        }
    }

    private void datosPersonalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datosPersonalesMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_datosPersonalesMouseClicked

    private void datosViviendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datosViviendaMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_datosViviendaMouseClicked

    private void extrasViviendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_extrasViviendaMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_extrasViviendaMouseClicked

    private void extrasVivienda2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_extrasVivienda2MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_extrasVivienda2MouseClicked

    private void Fotos1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Fotos1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Fotos1MouseClicked

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);

        datosPersonales.setVisible(true);
        Continuar.setVisible(true);
        Volver_tipo.setVisible(true);
        T_selected.setText("(HÚESPED)");
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
            // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);

        datosPersonales.setVisible(true);
        datosVivienda.setVisible(true);
        extrasVivienda2.setVisible(true);
        extrasVivienda.setVisible(true);
        Continuar.setVisible(true);
        Volver_tipo.setVisible(true);
        T_selected.setText("(ANFITRIÓN)");
    }//GEN-LAST:event_jPanel9MouseClicked

    private void txtDireccion_viviendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccion_viviendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccion_viviendaActionPerformed

    private void txtBarrio_viviendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBarrio_viviendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBarrio_viviendaActionPerformed

    private void volver_aloginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_volver_aloginMouseClicked
        // TODO add your handling code here:
        login volver = new login();
        volver.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_volver_aloginMouseClicked

    private void datosPersonales1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datosPersonales1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_datosPersonales1MouseClicked

    private void Volver_tipoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Volver_tipoMouseClicked
        // TODO add your handling code here:
        Limpiar_campos();
    }//GEN-LAST:event_Volver_tipoMouseClicked

    public void Limpiar_campos(){
        jTabbedPane1.setSelectedIndex(0);
        datosPersonales.setVisible(false);
        datosVivienda.setVisible(false);
        extrasVivienda2.setVisible(false);
        extrasVivienda.setVisible(false);
        Continuar.setVisible(false);
        Volver_tipo.setVisible(false);
        
        txtDocumento.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        jdNacimiento.setDate(null);
        jsEdad.setValue(0);
        txtEmail.setText("");
        cbPais.setSelectedIndex(0);
        cbIdioma.setSelectedIndex(0);
        cbCiudad.setSelectedIndex(0);
        txtBarrio.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtUsuario.setText("");
        txtContraseña.setText("");
        
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
    
    private void cbPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPaisActionPerformed
        // TODO add your handling code here:
        actualizarCodigoTelefonoYValidar();
        estilizarCampo(txtTelefono);
    }//GEN-LAST:event_cbPaisActionPerformed

    private void cbCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCiudadActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cbCiudadActionPerformed

    private void txtTelefonoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTelefonoFocusLost
        // TODO add your handling code here:
        actualizarCodigoTelefonoYValidar();
    }//GEN-LAST:event_txtTelefonoFocusLost

    private void cbTipo_viviendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipo_viviendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipo_viviendaActionPerformed

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
            java.util.logging.Logger.getLogger(registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            // Puedes elegir Dark (Oscuro) o Light (Claro)
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Failed to initialize LaF");
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new registro().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Continuar;
    private javax.swing.JLabel Fotos;
    private javax.swing.JLabel Fotos1;
    private javax.swing.JLabel T_selected;
    private javax.swing.JPanel Tipo;
    private javax.swing.JLabel Volver_tipo;
    private javax.swing.JComboBox<String> cbAgua;
    private javax.swing.JComboBox<String> cbAgua1;
    private javax.swing.JComboBox<String> cbBalcon;
    private javax.swing.JComboBox<String> cbBalcon1;
    private javax.swing.JComboBox<String> cbCiudad;
    private javax.swing.JComboBox<String> cbCiudad_vivienda;
    private javax.swing.JComboBox<String> cbConjunto;
    private javax.swing.JComboBox<String> cbIdioma;
    private javax.swing.JComboBox<String> cbMascotas;
    private javax.swing.JComboBox<String> cbMascotas1;
    private javax.swing.JComboBox<String> cbPais;
    private javax.swing.JComboBox<String> cbPais_vivienda;
    private javax.swing.JComboBox<String> cbParques;
    private javax.swing.JComboBox<String> cbParques1;
    private javax.swing.JComboBox<String> cbParrilla;
    private javax.swing.JComboBox<String> cbParrilla1;
    private javax.swing.JComboBox<String> cbPiscina;
    private javax.swing.JComboBox<String> cbPiscina1;
    private javax.swing.JComboBox<String> cbTipo_vivienda;
    private javax.swing.JComboBox<String> cb_movilidad;
    private javax.swing.JComboBox<String> cb_movilidad1;
    private javax.swing.JComboBox<String> cb_vigilancia;
    private javax.swing.JTextField codigo_telefono;
    private javax.swing.JLabel datosPersonales;
    private javax.swing.JLabel datosPersonales1;
    private javax.swing.JLabel datosVivienda;
    private javax.swing.JLabel extrasVivienda;
    private javax.swing.JLabel extrasVivienda2;
    private com.toedter.calendar.JDayChooser jDayChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
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
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JDateChooser jdNacimiento;
    private com.toedter.components.JSpinField jsBaños;
    private com.toedter.components.JSpinField jsCapacidad;
    private com.toedter.components.JSpinField jsEdad;
    private com.toedter.components.JSpinField jsHabitaciones;
    private javax.swing.JLabel lbVista_Previa;
    private javax.swing.JLabel lbVista_Previa1;
    private javax.swing.JTextArea taDescripcion;
    private javax.swing.JTextArea taDescripcion1;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBarrio;
    private javax.swing.JTextField txtBarrio_vivienda;
    private javax.swing.JTextField txtContraseña;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDireccion_vivienda;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMoneda;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTitulo_vivienda;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JPanel volver_alogin;
    // End of variables declaration//GEN-END:variables
}
