/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import Controllers.reservasController;
import Model.Alojamiento;
import Model.Reservas;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author aylee
 */
public class reservar extends javax.swing.JPanel {

    /**
     * Creates new form reservar
     */
    public reservar() {
        initComponents();
    }
    
    private void realizarReserva() {
        // =========================================================================
        // 1. VALIDACIÓN DE PRE-REQUISITOS
        // =========================================================================

        // =========================================================================
        // 2. CAPTURA Y CONVERSIÓN DE FECHAS (JDateChooser -> LocalDate)
        // =========================================================================
        Date dateEntrada = dcEntrada.getDate();
        Date dateSalida = dcSalida.getDate();

        if (dateEntrada == null || dateSalida == null) {
            JOptionPane.showMessageDialog(this, "Seleccione fechas de entrada y salida.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate diaEntrada = dateEntrada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate diaSalida = dateSalida.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Validación lógica de fechas
        if (!diaSalida.isAfter(diaEntrada)) {
            JOptionPane.showMessageDialog(this, "La fecha de salida debe ser posterior a la entrada.", "Error Fechas", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (diaEntrada.isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(this, "No puede reservar en fechas pasadas.", "Error Fechas", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // =========================================================================
        // 3. CAPTURA Y CONVERSIÓN DE HORAS (ComboBox String -> LocalDateTime)
        // =========================================================================
        // Asumiendo que cbPais_vivienda es Hora Entrada y cbPais_vivienda1 es Hora Salida (según tu imagen)
        String strHoraEntrada = (cbPais_vivienda.getSelectedItem() != null) ? cbPais_vivienda.getSelectedItem().toString() : "";
        String strHoraSalida = (cbPais_vivienda1.getSelectedItem() != null) ? cbPais_vivienda1.getSelectedItem().toString() : "";

        if (strHoraEntrada.isEmpty() || strHoraSalida.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione las horas de entrada y salida.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Convertir el String del ComboBox (ej: "3:00") a LocalTime y luego a LocalDateTime
        // Nota: Necesitamos formatear. Si tu combo dice "3:00", asumiremos formato 24h o AM/PM. 
        // Para simplificar, asumiremos que conviertes "3:00" a 15:00 o lo parseas manualmente.
        LocalTime horaEntrada = LocalTime.parse(strHoraEntrada, DateTimeFormatter.ofPattern("H:mm"));
        LocalTime horaSalida = LocalTime.parse(strHoraSalida, DateTimeFormatter.ofPattern("H:mm"));

        // =========================================================================
        // 4. CREACIÓN DEL OBJETO RESERVAS (Ajustado a tu Constructor)
        // =========================================================================
        try {
            // Tu constructor pide: 
            // (num_reserva, tipo, lugar, nombre, apellido, doc, tel, email, diaIn, diaOut, horaIn, horaOut)

            Reservas nuevaReserva = new Reservas(
                    txtNombre.getText(), // Autocompletado
                    txtApellido.getText(), // Autocompletado
                    txtDocumento.getText(),// Autocompletado
                    txtTelefono.getText(), // Autocompletado
                    txtEmail.getText(), // Autocompletado
                    diaEntrada,
                    diaSalida,
                    horaEntrada,
                    horaSalida
            );

            // =========================================================================
            // 5. GUARDADO
            // =========================================================================
            reservasController controller = new reservasController();
            controller.guardarReserva(nuevaReserva);

            JOptionPane.showMessageDialog(this, "¡Reserva realizada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Opcional: Cerrar o limpiar
            // limpiarCampos();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
        }
    }

// Método auxiliar para convertir el texto del Combo a LocalDateTime
    private LocalDateTime combinarFechaYHora(LocalDate fecha, String horaTexto) {
        // Lógica simple: Si el combo dice "3:00", asumimos 15:00 (Check-in)
        // Si dice "12:00", es 12:00 (Check-out). Ajusta esto según tus items del ComboBox.

        int hora = 0;
        int minuto = 0;

        try {
            String[] partes = horaTexto.split(":");
            hora = Integer.parseInt(partes[0].trim());
            if (partes.length > 1) {
                minuto = Integer.parseInt(partes[1].trim());
            }

            // Ajuste manual simple si tus combos son estilo PM sin decir PM
            if (hora < 12 && hora != 0 && horaTexto.equals("3:00")) {
                hora += 12; // Ejemplo para las 3 PM
            }
        } catch (Exception e) {
            hora = 12; // Valor por defecto ante error
        }

        return LocalDateTime.of(fecha, LocalTime.of(hora, minuto));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel73 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        dcEntrada = new com.toedter.calendar.JDateChooser();
        jLabel81 = new javax.swing.JLabel();
        dcSalida = new com.toedter.calendar.JDateChooser();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        cbPais_vivienda = new javax.swing.JComboBox<>();
        cbPais_vivienda1 = new javax.swing.JComboBox<>();
        jLabel85 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel73.setFont(new java.awt.Font("Ebrima", 3, 24)); // NOI18N
        jLabel73.setText("RESERVAR");
        add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 140, 40));

        jLabel75.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel75.setText("Documento:");
        add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 140, 20));

        jLabel76.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel76.setText("Nombre:");
        add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 140, 20));

        jLabel77.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel77.setText("Apellido:");
        add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 140, 20));

        jLabel78.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel78.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campana.png"))); // NOI18N
        jLabel78.setText("RESERVAR");
        jLabel78.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel78.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel78MouseClicked(evt);
            }
        });
        add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 410, 120, 30));

        jLabel79.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel79.setText("Correo electronico:");
        add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 190, 140, 20));

        jLabel80.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel80.setText("Dia de entrada:");
        add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, 140, 20));
        add(dcEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 310, 230, 30));

        jLabel81.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel81.setText("Dia de salida:");
        add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 280, 140, 20));
        add(dcSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 310, 200, 30));

        jLabel82.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel82.setText("Telefono:");
        add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 140, 20));

        jLabel83.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel83.setText("Hora de entrada:");
        add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 100, 140, 20));

        jLabel84.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel84.setText("Hora de salida:");
        add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 190, 140, 20));

        txtDocumento.setBackground(new java.awt.Color(255, 255, 255));
        txtDocumento.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        add(txtDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 220, -1));

        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 220, -1));

        txtApellido.setBackground(new java.awt.Color(255, 255, 255));
        txtApellido.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, 220, -1));

        txtEmail.setBackground(new java.awt.Color(255, 255, 255));
        txtEmail.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 230, 220, -1));

        txtTelefono.setBackground(new java.awt.Color(255, 255, 255));
        txtTelefono.setFont(new java.awt.Font("Ebrima", 2, 14)); // NOI18N
        add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 220, -1));

        cbPais_vivienda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "3:00", "3:30", "4:00", "4:30", "5:00", "5:30", "6:00", "6:30" }));
        add(cbPais_vivienda, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 140, 180, -1));

        cbPais_vivienda1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "12:00", "12:30", "1:00", "1:30", "2:00" }));
        add(cbPais_vivienda1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 230, 180, -1));

        jLabel85.setFont(new java.awt.Font("Ebrima", 3, 14)); // NOI18N
        jLabel85.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/flecha-hacia-atras-peque.png"))); // NOI18N
        jLabel85.setText("VOLVER");
        jLabel85.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel85.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel85MouseClicked(evt);
            }
        });
        add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 100, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel85MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel85MouseClicked
        // TODO add your handling code here:
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if(frame != null){
        frame.dispose();
    }
    }//GEN-LAST:event_jLabel85MouseClicked

    private void jLabel78MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel78MouseClicked
        // TODO add your handling code here:
        realizarReserva();
    }//GEN-LAST:event_jLabel78MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbPais_vivienda;
    private javax.swing.JComboBox<String> cbPais_vivienda1;
    private com.toedter.calendar.JDateChooser dcEntrada;
    private com.toedter.calendar.JDateChooser dcSalida;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
