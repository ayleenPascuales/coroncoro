/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Estructuras.Lista_alojamiento;
import Estructuras.Lista_anfitriones;
import Estructuras.Lista_clientes;
import Estructuras.Nodo_alojamiento;
import Estructuras.Nodo_anfitrion;
import Estructuras.Nodo_cliente;
import Model.Alojamiento;
import Model.Cuenta_Anfitrion;
import Model.Cuenta_cliente;
import Model.Reservas;
import Model.dao.AlojamientoDAOImpl;
import Model.dao.AnfitrionDAOImpl;
import Model.dao.ClienteDAOImpl;
import Model.dao.ReservasDAOImpl;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aylee
 */
public class menu_admin extends javax.swing.JFrame {
    private String idUsuarioLogueado;
    /**
     * Creates new form menu_admin
     */
    public menu_admin(String idUsuario) {
        this.idUsuarioLogueado = idUsuario;
        initComponents();
        cargarTablaClientes();
        cargarTablaAlojamientos();
        setLocationRelativeTo(null);
    }
    
    private void cargarTablaClientes() {
    ClienteDAOImpl clienteDAO = new ClienteDAOImpl();
    Lista_clientes listaClientes = clienteDAO.getLista();

    AlojamientoDAOImpl alojamientoDAO = new AlojamientoDAOImpl();
    Lista_alojamiento listaAlojamientos = alojamientoDAO.getLista();

    llenarTablaClientesConAlojamientos(listaClientes, listaAlojamientos);

    }
    private void cargarTablaAlojamientos() {
    ClienteDAOImpl clienteDAO = new ClienteDAOImpl();
    Lista_clientes listaClientes = clienteDAO.getLista();

    AnfitrionDAOImpl anfitrionDAO = new AnfitrionDAOImpl();
    Lista_anfitriones listaAnfitriones = anfitrionDAO.getLista();

    AlojamientoDAOImpl alojamientoDAO = new AlojamientoDAOImpl();
    Lista_alojamiento listaAlojamientos = alojamientoDAO.getLista();

    ReservasDAOImpl reservasDAO = new ReservasDAOImpl();
    List<Reservas> listaReservas = reservasDAO.cargarReservas();

    // Llenar la tabla
    llenarTablaAlojamientos(listaAlojamientos, listaReservas, listaClientes, listaAnfitriones);

    }
    
    public void llenarTablaClientesConAlojamientos(Lista_clientes listaClientes, Lista_alojamiento listaAlojamientos) {
    String[] columnas = {"Documento", "Nombre", "Apellido", "Email", "Cantidad de alojamientos"};
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

    Nodo_cliente actualCliente = listaClientes.getCabeza();

    while (actualCliente != null) {
        Cuenta_cliente cliente = actualCliente.getDato();

        // Contar cuántos alojamientos subió este cliente
        int cantidad = 0;
        Nodo_alojamiento actualAlojamiento = listaAlojamientos.getCabeza();
        while (actualAlojamiento != null) {
            if (actualAlojamiento.getDato().getId_alojamiento().equals(cliente.getDocumento())) {
                cantidad++;
            }
            actualAlojamiento = actualAlojamiento.getSiguiente();
        }

        Object[] fila = {
            cliente.getDocumento(),
            cliente.getNombre(),
            cliente.getApellido(),
            cliente.getEmail(),
            cantidad
        };
        modelo.addRow(fila);

        actualCliente = actualCliente.getSiguiente();
    }

        tablaClientes.setModel(modelo); // tablaClientes es tu JTable
    }
    
    public void llenarTablaAlojamientos(Lista_alojamiento listaAlojamientos, List<Reservas> listaReservas, Lista_clientes listaClientes, Lista_anfitriones listaAnfitriones) {
    String[] columnas = {"Documento del dueño", "Nombre", "Apellido", "Cantidad de reservas"};
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

    Nodo_alojamiento actualAloj = listaAlojamientos.getCabeza();

    while (actualAloj != null) {
        Alojamiento alojamiento = actualAloj.getDato();

        // Buscar dueño en clientes
        Nodo_cliente actualCliente = listaClientes.getCabeza();
        Cuenta_cliente dueñoCliente = null;
        while (actualCliente != null) {
            if (actualCliente.getDato().getDocumento().equals(alojamiento.getId_alojamiento())) {
                dueñoCliente = actualCliente.getDato();
                break;
            }
            actualCliente = actualCliente.getSiguiente();
        }

        // Buscar dueño en anfitriones
        Nodo_anfitrion actualAnfitrion = listaAnfitriones.getCabeza();
        Cuenta_Anfitrion dueñoAnfitrion = null;
        while (actualAnfitrion != null) {
            if (actualAnfitrion.getDato().getDocumento().equals(alojamiento.getId_alojamiento())) {
                dueñoAnfitrion = actualAnfitrion.getDato();
                break;
            }
            actualAnfitrion = actualAnfitrion.getSiguiente();
        }

        // Si encontramos un dueño
        if (dueñoCliente != null || dueñoAnfitrion != null) {
            String documento = (dueñoCliente != null) ? dueñoCliente.getDocumento() : dueñoAnfitrion.getDocumento();
            String nombre = (dueñoCliente != null) ? dueñoCliente.getNombre() : dueñoAnfitrion.getNombre();
            String apellido = (dueñoCliente != null) ? dueñoCliente.getApellido() : dueñoAnfitrion.getApellido();

            // Contar cuántas reservas tiene este alojamiento
            long cantidadReservas = listaReservas.stream()
                    .filter(r -> r.getDocumento_cliente().equals(alojamiento.getId_alojamiento()))
                    .count();

            Object[] fila = {documento, nombre, apellido, cantidadReservas};
            modelo.addRow(fila);
        }

        actualAloj = actualAloj.getSiguiente();
    }

    tablaAlojamientos.setModel(modelo);
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
        jPanel5 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        btClientes = new javax.swing.JLabel();
        btAlojamientos = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        jLabel78 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel76 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaAlojamientos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel5.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 13, 40));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, 850, 40));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel73.setFont(new java.awt.Font("Ebrima", 3, 24)); // NOI18N
        jLabel73.setText("RESERVAR");
        jPanel2.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 140, 40));

        jLabel77.setFont(new java.awt.Font("Ebrima", 3, 16)); // NOI18N
        jLabel77.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/blogger.png"))); // NOI18N
        jPanel2.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 70, 80));

        jLabel79.setFont(new java.awt.Font("Ebrima", 3, 16)); // NOI18N
        jLabel79.setText("ADMINISTRADOR");
        jPanel2.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 140, 30));

        btClientes.setFont(new java.awt.Font("Ebrima", 3, 16)); // NOI18N
        btClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clasificacion.png"))); // NOI18N
        btClientes.setText("CLIENTES");
        btClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btClientesMouseClicked(evt);
            }
        });
        jPanel2.add(btClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 140, 40));

        btAlojamientos.setFont(new java.awt.Font("Ebrima", 3, 16)); // NOI18N
        btAlojamientos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/alojamiento.png"))); // NOI18N
        btAlojamientos.setText("ALOJAMIENTOS");
        btAlojamientos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btAlojamientosMouseClicked(evt);
            }
        });
        jPanel2.add(btAlojamientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 160, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 510));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 13, 480));

        tablaClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Docuemento", "Nombre", "Apellido", "Email", "Publicaciones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaClientes);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 680, 350));

        jLabel78.setFont(new java.awt.Font("Ebrima", 3, 24)); // NOI18N
        jLabel78.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clasificacion.png"))); // NOI18N
        jLabel78.setText("CLIENTES");
        jLabel78.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel78MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 200, 40));

        jTabbedPane1.addTab("tab1", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel76.setFont(new java.awt.Font("Ebrima", 3, 24)); // NOI18N
        jLabel76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/alojamiento.png"))); // NOI18N
        jLabel76.setText("ALOJAMIENTOS");
        jLabel76.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel76MouseClicked(evt);
            }
        });
        jPanel4.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 230, 40));

        tablaAlojamientos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tablaAlojamientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Documento", "Nombre", "Apellido", "Reservas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaAlojamientos);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 670, 350));

        jTabbedPane1.addTab("tab2", jPanel4);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 790, 510));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel78MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel78MouseClicked
        // TODO add your handling code here:
 
    }//GEN-LAST:event_jLabel78MouseClicked

    private void jLabel76MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel76MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jLabel76MouseClicked

    private void btClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btClientesMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_btClientesMouseClicked

    private void btAlojamientosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btAlojamientosMouseClicked
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_btAlojamientosMouseClicked

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
            java.util.logging.Logger.getLogger(menu_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menu_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menu_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menu_admin("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btAlojamientos;
    private javax.swing.JLabel btClientes;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tablaAlojamientos;
    private javax.swing.JTable tablaClientes;
    // End of variables declaration//GEN-END:variables
}
