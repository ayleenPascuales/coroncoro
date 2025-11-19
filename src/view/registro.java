/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
    
    public void guardarFotos() {
        if (fotosSeleccionadas == null || fotosSeleccionadas.length == 0) {
            JOptionPane.showMessageDialog(this, "No hay fotos para guardar.");
            return;
        }

        File carpeta = new File("Fotografias");

        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        for (File foto : fotosSeleccionadas) {
            try {
                File destino = new File(carpeta, foto.getName());
                Files.copy(foto.toPath(), destino.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);

            } catch (Exception e) {
                System.out.println("Error guardando foto: " + e);
            }
        }

        JOptionPane.showMessageDialog(this, "Fotos guardadas correctamente.");
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
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
        txtDireccion = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();
        cbIdioma = new javax.swing.JComboBox<>();
        cbPais = new javax.swing.JComboBox<>();
        jdNacimiento = new com.toedter.calendar.JDateChooser();
        cbCiudad = new javax.swing.JComboBox<>();
        txtTelefono = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtBarrio = new javax.swing.JTextField();
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
        txtPrecio = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        cbPais_vivienda = new javax.swing.JComboBox<>();
        cbCiudad_vivienda = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        cbPais_vivienda1 = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        cbCiudad_vivienda1 = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        txtBarrio1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtDireccion_vivienda1 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jsCapacidad1 = new com.toedter.components.JSpinField();
        jLabel53 = new javax.swing.JLabel();
        jsHabitaciones1 = new com.toedter.components.JSpinField();
        jLabel54 = new javax.swing.JLabel();
        jsBaños1 = new com.toedter.components.JSpinField();
        jLabel55 = new javax.swing.JLabel();
        cbTipo_vivienda1 = new javax.swing.JComboBox<>();
        jLabel56 = new javax.swing.JLabel();
        txtPrecio1 = new javax.swing.JTextField();
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
        datosPersonales = new javax.swing.JLabel();
        datosVivienda = new javax.swing.JLabel();
        extrasVivienda2 = new javax.swing.JLabel();
        extrasVivienda = new javax.swing.JLabel();
        Continuar = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel5.setText("Ingrese su documento:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 160, 20));

        txtContraseña.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel2.add(txtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 160, 180, -1));

        jLabel7.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel7.setText("Ingrese su nombre:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 160, 20));

        jLabel8.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel8.setText("Ingrese su apellido:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 160, 20));

        jLabel9.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel9.setText("Ingrese su edad:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 160, 20));

        jLabel10.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel10.setText("Ingrese su telefono:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 160, 20));

        jLabel11.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel11.setText("Ingrese email:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 160, 20));

        jLabel13.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel13.setText("Ciudad donde vive:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, 160, 20));

        jLabel14.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel14.setText("Ingrese su direccion:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, 160, 20));

        jLabel15.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel15.setText("Fecha de nacimiento:");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 200, 160, 20));

        jLabel16.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel16.setText("Seleccione que idiomas habla:");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 270, 210, 20));

        jLabel18.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel18.setText("Pais donde vive:");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 160, 20));

        jLabel19.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel19.setText("Ingrese su usuario:");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 160, 20));

        jLabel20.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel20.setText("Ingrese su contraseña:");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, 160, 20));
        jPanel2.add(jsEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 180, -1));

        txtDocumento.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel2.add(txtDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 180, -1));

        txtNombre.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 180, -1));

        txtApellido.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel2.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 180, -1));

        txtDireccion.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel2.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 300, 180, -1));

        txtEmail.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel2.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 180, -1));

        txtUsuario.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel2.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 180, -1));

        jPanel2.add(cbIdioma, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 300, 180, -1));

        jPanel2.add(cbPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 180, -1));
        jPanel2.add(jdNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 230, 180, -1));

        jPanel2.add(cbCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 230, 180, -1));

        txtTelefono.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel2.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 180, -1));

        jLabel24.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jLabel24.setText("DATOS PERSONALES");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 200, 20));

        jTabbedPane1.addTab("tab1", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel25.setFont(new java.awt.Font("Ebrima", 2, 11)); // NOI18N
        jLabel25.setText("(LLenar estos campos SOLO en caso de que vaya a alquilar una vivienda)");
        jPanel3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 360, 20));

        jLabel6.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel6.setText("Capacidad maxima:");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 180, 20));

        jLabel12.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel12.setText("Ciudad donde esta ubicada");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 190, 20));

        txtBarrio.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel3.add(txtBarrio, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 180, -1));

        jLabel17.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel17.setText("Barrio donde esta ubicada:");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 190, 20));

        txtDireccion_vivienda.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel3.add(txtDireccion_vivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 180, -1));

        jLabel21.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel21.setText("Direccion:");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 180, 20));

        jLabel27.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel27.setText("Pais donde esta ubicada:");
        jPanel3.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 180, 20));
        jPanel3.add(jsCapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 170, -1));
        jPanel3.add(jsHabitaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 170, -1));

        jLabel29.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel29.setText("Tipo de vivienda:");
        jPanel3.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 350, 130, 20));

        jLabel30.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel30.setText("numero de baños:");
        jPanel3.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, 180, 20));
        jPanel3.add(jsBaños, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 300, 170, 30));

        jLabel31.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel31.setText("numero de habitaciones:");
        jPanel3.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 180, 20));

        jPanel3.add(cbTipo_vivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 380, 170, -1));

        jLabel32.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel32.setText("Precio por noche:");
        jPanel3.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 110, 190, 20));

        txtPrecio.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel3.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 140, 180, -1));

        jLabel33.setFont(new java.awt.Font("Ebrima", 3, 20)); // NOI18N
        jLabel33.setText("DATOS DE LA VIVIENDA");
        jPanel3.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, 230, 20));

        jPanel3.add(cbPais_vivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 180, -1));

        jPanel3.add(cbCiudad_vivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 180, -1));

        jLabel28.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel28.setText("Pais donde esta ubicada:");
        jPanel3.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 180, 20));

        jPanel3.add(cbPais_vivienda1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 180, -1));

        jLabel22.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel22.setText("Ciudad donde esta ubicada");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 190, 20));

        jPanel3.add(cbCiudad_vivienda1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 180, -1));

        jLabel23.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel23.setText("Barrio donde esta ubicada:");
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 190, 20));

        txtBarrio1.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel3.add(txtBarrio1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 180, -1));

        jLabel26.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel26.setText("Direccion:");
        jPanel3.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 180, 20));

        txtDireccion_vivienda1.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel3.add(txtDireccion_vivienda1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 180, -1));

        jLabel52.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel52.setText("Capacidad maxima:");
        jPanel3.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 180, 20));
        jPanel3.add(jsCapacidad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 170, -1));

        jLabel53.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel53.setText("numero de habitaciones:");
        jPanel3.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 180, 20));
        jPanel3.add(jsHabitaciones1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 170, -1));

        jLabel54.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel54.setText("numero de baños:");
        jPanel3.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, 180, 20));
        jPanel3.add(jsBaños1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 300, 170, 30));

        jLabel55.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel55.setText("Tipo de vivienda:");
        jPanel3.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 350, 130, 20));

        jPanel3.add(cbTipo_vivienda1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 380, 170, -1));

        jLabel56.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel56.setText("Precio por noche:");
        jPanel3.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 110, 190, 20));

        txtPrecio1.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        jPanel3.add(txtPrecio1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 140, 180, -1));

        jTabbedPane1.addTab("tab2", jPanel3);

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

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 700, 500));

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

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, -1, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/registro.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 330, 500));

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
        guardarFotos();
    }//GEN-LAST:event_ContinuarMouseClicked

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

    private void datosPersonalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datosPersonalesMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_datosPersonalesMouseClicked

    private void datosViviendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datosViviendaMouseClicked
        // TODO add your handling code here:
       jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_datosViviendaMouseClicked

    private void extrasViviendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_extrasViviendaMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_extrasViviendaMouseClicked

    private void extrasVivienda2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_extrasVivienda2MouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_extrasVivienda2MouseClicked

    private void Fotos1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Fotos1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Fotos1MouseClicked

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new registro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Continuar;
    private javax.swing.JLabel Fotos;
    private javax.swing.JLabel Fotos1;
    private javax.swing.JComboBox<String> cbAgua;
    private javax.swing.JComboBox<String> cbAgua1;
    private javax.swing.JComboBox<String> cbBalcon;
    private javax.swing.JComboBox<String> cbBalcon1;
    private javax.swing.JComboBox<String> cbCiudad;
    private javax.swing.JComboBox<String> cbCiudad_vivienda;
    private javax.swing.JComboBox<String> cbCiudad_vivienda1;
    private javax.swing.JComboBox<String> cbConjunto;
    private javax.swing.JComboBox<String> cbIdioma;
    private javax.swing.JComboBox<String> cbMascotas;
    private javax.swing.JComboBox<String> cbMascotas1;
    private javax.swing.JComboBox<String> cbPais;
    private javax.swing.JComboBox<String> cbPais_vivienda;
    private javax.swing.JComboBox<String> cbPais_vivienda1;
    private javax.swing.JComboBox<String> cbParques;
    private javax.swing.JComboBox<String> cbParques1;
    private javax.swing.JComboBox<String> cbParrilla;
    private javax.swing.JComboBox<String> cbParrilla1;
    private javax.swing.JComboBox<String> cbPiscina;
    private javax.swing.JComboBox<String> cbPiscina1;
    private javax.swing.JComboBox<String> cbTipo_vivienda;
    private javax.swing.JComboBox<String> cbTipo_vivienda1;
    private javax.swing.JComboBox<String> cb_movilidad;
    private javax.swing.JComboBox<String> cb_movilidad1;
    private javax.swing.JComboBox<String> cb_vigilancia;
    private javax.swing.JLabel datosPersonales;
    private javax.swing.JLabel datosVivienda;
    private javax.swing.JLabel extrasVivienda;
    private javax.swing.JLabel extrasVivienda2;
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
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
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
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JDateChooser jdNacimiento;
    private com.toedter.components.JSpinField jsBaños;
    private com.toedter.components.JSpinField jsBaños1;
    private com.toedter.components.JSpinField jsCapacidad;
    private com.toedter.components.JSpinField jsCapacidad1;
    private com.toedter.components.JSpinField jsEdad;
    private com.toedter.components.JSpinField jsHabitaciones;
    private com.toedter.components.JSpinField jsHabitaciones1;
    private javax.swing.JLabel lbVista_Previa;
    private javax.swing.JLabel lbVista_Previa1;
    private javax.swing.JTextArea taDescripcion;
    private javax.swing.JTextArea taDescripcion1;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBarrio;
    private javax.swing.JTextField txtBarrio1;
    private javax.swing.JTextField txtContraseña;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDireccion_vivienda;
    private javax.swing.JTextField txtDireccion_vivienda1;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtPrecio1;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
