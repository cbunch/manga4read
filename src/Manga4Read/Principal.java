/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manga4Read;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author tenshi
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    //Variables globales
    static boolean detener;
    private static generador manga;
    String portat;
    public static String Direccion;
    LinkedList cola1;
    
    public Principal() {
        initComponents();
        portat="";
        manga = new generador();
        this.setLocationRelativeTo(null);
        String so = System.getProperty("os.name");
        System.out.println(so);
    }
    public void agregarCola(){
        String enlace = jTextFieldUrl.getText();
        if(enlace.matches("http://submanga.*")){
            System.out.println("Servidor = Submanga");
            manga.infoSubmanga(enlace);
        }
        if(enlace.matches("https://www.fakku.*")){
            System.out.println("Servidor = Fakku");
            manga.infoFakku(enlace);
        }
        if(enlace.matches("http://hakihome.*")){
            System.out.println("Servidor = Fakku");
            manga.infoHaki(enlace);
        }
        llenarTabla();
    }
    
    Timer timer = new Timer (500, new ActionListener () 
    { 
        public void actionPerformed(ActionEvent e) 
        {
            if(!portat.equals(pegarPortapapeles())){
                if(pegarPortapapeles().matches("^htt.*")){
                    portat=pegarPortapapeles();
                    jTextFieldUrl.setText(portat);
                    agregarCola();
                }
            }
        } 
    });
    
    public String pegarPortapapeles(){
        String porta="";
		try {
            // Se obtiene el Clipboard y su contenido
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable t = cb.getContents(cb);
            // Construimos el DataFlavor correspondiente a String.
            DataFlavor dataFlavorStringJava = new DataFlavor("application/x-java-serialized-object; class=java.lang.String");
            // Si el dato se puede obtener como String, lo obtenemos
            if (t.isDataFlavorSupported(dataFlavorStringJava)) {
                String texto = (String) t.getTransferData(dataFlavorStringJava);
                return(texto);
            }
        } catch (HeadlessException | ClassNotFoundException | UnsupportedFlavorException | IOException e) {
            return "";
        }
        return porta;
    }
    
    public void llenarTabla(){ 
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            jTableCola.setModel(modelo);
            Class.forName("org.sqlite.JDBC");//especificamos el driver
            Connection conn = DriverManager.getConnection("jdbc:sqlite:mangas.db"); //creamos la conexión, si la BD no existe la crea
            java.sql.PreparedStatement stat = conn.prepareStatement("select Nombre, Capitulo, Paginas, Estado from cola");
            ResultSet rs = stat.executeQuery();
            ResultSetMetaData rsMd = rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            modelo.addColumn("Nombre");
            modelo.addColumn("Enlace");
            modelo.addColumn("Paginas");
            modelo.addColumn("Estado");
            
            //Creando las filas para el JTable
            while (rs.next()) {
                Object[] fila = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    fila[i]=rs.getObject(i+1);
                }
                modelo.addRow(fila);
            }
            rs.close();
            stat.close();
        } catch (ClassNotFoundException | SQLException ex) {
            //ex.printStackTrace();
        }
        TableColumn columna = jTableCola.getColumn("Nombre");
        columna.setPreferredWidth(150);
        columna = jTableCola.getColumn("Enlace");
        columna.setPreferredWidth(320);
        columna = jTableCola.getColumn("Paginas");
        columna.setPreferredWidth(20);
        columna = jTableCola.getColumn("Estado");
        columna.setPreferredWidth(50);
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldUrl = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCola = new javax.swing.JTable();
        jButtonAgregar = new javax.swing.JButton();
        jButtonDescargar = new javax.swing.JButton();
        jButtonDetener = new javax.swing.JButton();
        jProgressBarCap = new javax.swing.JProgressBar();
        jButtonPegar = new javax.swing.JButton();
        jButtonBorrarItem = new javax.swing.JButton();
        jButtonBorrarCola = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jTextFieldUrl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldUrlActionPerformed(evt);
            }
        });

        jLabel1.setText("Enlace");

        jTableCola.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableCola);

        jButtonAgregar.setText("Agregar");
        jButtonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarActionPerformed(evt);
            }
        });

        jButtonDescargar.setText("Descargar Cola");
        jButtonDescargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDescargarActionPerformed(evt);
            }
        });

        jButtonDetener.setText("Detener");
        jButtonDetener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDetenerActionPerformed(evt);
            }
        });

        jButtonPegar.setText("Pegar");
        jButtonPegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPegarActionPerformed(evt);
            }
        });

        jButtonBorrarItem.setText("Borrar de cola");
        jButtonBorrarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBorrarItemActionPerformed(evt);
            }
        });

        jButtonBorrarCola.setText("Borrar todo");
        jButtonBorrarCola.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBorrarColaActionPerformed(evt);
            }
        });

        jMenu3.setText("Submanga");

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Series Suscritas");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Cargar Faltantes");
        jMenu3.add(jMenuItem3);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Cargar Lista");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuBar1.add(jMenu3);

        jMenu2.setText("Descargas");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Agregar Enlace");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Descarga Automatica");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 807, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonPegar, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jProgressBarCap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonBorrarCola)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonBorrarItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDetener, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDescargar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAgregar)
                    .addComponent(jButtonPegar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonDescargar)
                        .addComponent(jButtonDetener)
                        .addComponent(jButtonBorrarItem)
                        .addComponent(jButtonBorrarCola))
                    .addComponent(jProgressBarCap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButtonDetenerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDetenerActionPerformed
        // TODO add your handling code here:
        detener=false;
    }//GEN-LAST:event_jButtonDetenerActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        int des=JOptionPane.showConfirmDialog(null, "Desea Activar la deteccion automatica de enlaces");
        switch (des){
            case 0:
                JOptionPane.showMessageDialog(null, "Se ha iniciado la deteccion automatica de enlaces");
                timer.start();
                break;
            case 1:
                JOptionPane.showMessageDialog(null, "Se ha detenido la deteccion automatica de enlaces");
                timer.stop();
                break;
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButtonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarActionPerformed
        // TODO add your handling code here:
        agregarCola();
    }//GEN-LAST:event_jButtonAgregarActionPerformed

    private void jButtonBorrarColaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBorrarColaActionPerformed
        // TODO add your handling code here:
        database.insertar("delete from cola");
        llenarTabla();
    }//GEN-LAST:event_jButtonBorrarColaActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        llenarTabla();
        
    }//GEN-LAST:event_formWindowActivated

    private void jButtonPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPegarActionPerformed
        // TODO add your handling code here:
        jTextFieldUrl.setText(pegarPortapapeles());
        
    }//GEN-LAST:event_jButtonPegarActionPerformed

    private void jButtonDescargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDescargarActionPerformed
        // TODO add your handling code here:
        database.insertar("delete from temporal");
        detener=true;
        int mangas=jTableCola.getRowCount();
        System.out.println("Mangas en cola: "+mangas);
        String nombre,enlace;
        int pag;
        boolean b = true;
        for (int c=0;c<mangas;c++){
            pag=Integer.parseInt(jTableCola.getValueAt(c, 2).toString());
            nombre=jTableCola.getValueAt(c, 0).toString();
            enlace=database.consultarUno("select Imagen from cola where Nombre='"+nombre+"'");
            if (!enlace.matches("http://.mg.submanga.*")){
                manga.generarManga(pag, nombre, enlace,1);
            }else{
                manga.generarManga(pag, nombre, enlace,0);
            }
            descargar des = new descargar(this,true);
            if (!detener){
                break;
            }
        }
        JOptionPane.showMessageDialog(null, "Descarga terminada");
    }//GEN-LAST:event_jButtonDescargarActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        String temp = JOptionPane.showInputDialog("Introducir el enlace");
        jTextFieldUrl.setText(temp);
        agregarCola();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        new Suscritos(this, true).show();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jTextFieldUrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldUrlActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldUrlActionPerformed

    private void jButtonBorrarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBorrarItemActionPerformed
        // TODO add your handling code here:
        database.insertar("delete from cola where Capitulo='"+jTableCola.getValueAt(jTableCola.getSelectedRow(), 1).toString()+"'");
        llenarTabla();
    }//GEN-LAST:event_jButtonBorrarItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAgregar;
    private javax.swing.JButton jButtonBorrarCola;
    private javax.swing.JButton jButtonBorrarItem;
    private javax.swing.JButton jButtonDescargar;
    private javax.swing.JButton jButtonDetener;
    private javax.swing.JButton jButtonPegar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JProgressBar jProgressBarCap;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableCola;
    private javax.swing.JTextField jTextFieldUrl;
    // End of variables declaration//GEN-END:variables
}
