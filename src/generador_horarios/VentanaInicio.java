/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import static generador_horarios.ManejadorAgrupaciones.getAgrupacion;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author alexander
 */
public class VentanaInicio extends javax.swing.JFrame implements MouseListener{

    DefaultTableModel modelo;
    Campus campus;
    String aulaSeleccionada;
    String[][] datosTabla={};
    String[] cabeceraTabla={"Horas","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
    String[] listadoAulas;
    String[] listadoDepartamentos;
    String[] listadoCarreras;
    
    
    DefaultListModel<String> modeloLista;
    
    /**
     * Creates new form VentanaInicio
     */
    public VentanaInicio() {
        initComponents();
        //Se crea el objeto campus
        campus = new Campus(ManejadorAgrupaciones.getAgrupaciones());
        
        //Se llena la tabla de dias y horas
        modelo = new DefaultTableModel(datosTabla, cabeceraTabla){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
        ArrayList<Hora> horas = ManejadorHoras.getTodasHoras();
        for (int i = 0; i < horas.size(); i++) {
            Hora hora = horas.get(i);
            modelo.addRow(datosTabla);
            modelo.setValueAt(hora.getInicio()+" - "+hora.getFin(), i, 0);
        }
        //jTable1.setDefaultRenderer(Object.class, new MyRenderer());
        jTable1.setModel(modelo);
        jTable1.addMouseListener(this);
        
        //Se llena la lista de aulas
        ArrayList<Aula> aulas = ManejadorAulas.getTodasAulasOrdenadas("cod_aula");
        listadoAulas = new String[aulas.size()];
        for (int i = 0; i < aulas.size(); i++) {
            Aula aula = aulas.get(i);
            jlist_aulas.addItem(aula.getNombre());
        }
        
        //Se llena la lista de departamentos
        ArrayList<String> carreras= ManejadorCarreras.getNombreTodasCarreras();
        for (int i = 0; i < carreras.size(); i++) {
            jlist_carreras.addItem(carreras.get(i));
        }
        
        //Se llena la lista de carreras
        ArrayList<String> departamentos= ManejadorDepartamentos.getNombreDepartamentos();
        for (int i = 0; i < departamentos.size(); i++) {
            jlist_departamentos.addItem(departamentos.get(i));
        }
        
        String aulaSeleccionada = null;
        
    }
    
    
    
    public void imprimir(){
        ArrayList<Aula> aulas;
        aulas = campus.getAulas();
        ArrayList<Dia> dias;
        ArrayList<Hora> horas;
        
        for (int i = 0; i < aulas.size(); i++) {
            Aula aula = aulas.get(i);
            System.out.println("Aula: "+aula.getNombre());
            dias = aula.getDias();
            for (int j = 0; j < dias.size(); j++) {
                Dia dia = dias.get(j);
                System.out.println("        Nombre: "+dia.getNombre());
                horas = dia.getHoras();
                for (int k = 0; k < horas.size(); k++) {
                    Hora hora = horas.get(k);
                    System.out.println("            Dia: "+dia.getNombre()+" Aula: "+aula.getNombre()+" Hora: "+hora.getIdHora()+", Disponible: "+hora.estaDisponible() + ", Materia:"+hora.getGrupo().getCod_materia()+", Grupo: "+hora.getGrupo().getId_grupo()+", Departamento"+hora.getGrupo().getId_depar());                    
                }
                
            }
        }
    }
        
    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (e.getClickCount() == 1) {
            JTable target = (JTable)e.getSource();
            int row = target.getSelectedRow()+1;
            int column = target.getSelectedColumn();
            Grupo grupo = ManejadorGrupos.getGrupo(campus.getAulas(), aulaSeleccionada, target.getColumnName(column), row);            
            
            String nombreMateria = ManejadorMaterias.getNombreMateria(grupo.getCod_materia());
            String nombreDepartamento = ManejadorDepartamentos.getNombreDepartamento(grupo.getId_depar());
            lbl_mensaje.setText("<html>Materia: "+nombreMateria+"<br/>Grupo: "+grupo.getId_grupo()+"<br/>Departamento: "+nombreDepartamento+"</html>");
            
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    class MyRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
            
                //JPanel middlePanel = new JPanel ();
                //middlePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Display Area" ) );

                // create the middle panel components

//                JTextArea display = new JTextArea ();
//                if(value != null)
//                    display.setText(value.toString());
//                display.setEditable ( false ); // set textArea non-editable
//                JScrollPane scroll = new JScrollPane ( display );
//                scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
//
//                //Add Textarea in to middle panel
//                middlePanel.add ( scroll );
//                return middlePanel;
            
//            JTextArea display = new JTextArea();
//            display.setEditable(false);
//            if(value != null)
//                display.setText(value.toString());                        
//            JScrollPane panel = new JScrollPane(display);
//            return panel;
            
            
          JTextArea display = new JTextArea();
          if (value != null)
            display.setText(value.toString());
          display.setEditable(false);
          display.setBackground((row % 2 == 0) ? Color.white : new Color(211,211,211));
          return display;
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btn_generar = new javax.swing.JButton();
        jlist_aulas = new javax.swing.JComboBox();
        jlist_departamentos = new javax.swing.JComboBox();
        jlist_carreras = new javax.swing.JComboBox();
        btn_filtrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        lbl_mensaje = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btn_generar.setText(" Generar Horario");
        btn_generar.setName(""); // NOI18N
        btn_generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generarActionPerformed(evt);
            }
        });

        jlist_aulas.setEnabled(false);

        jlist_departamentos.setEnabled(false);

        jlist_carreras.setEnabled(false);

        btn_filtrar.setText("Filtrar");
        btn_filtrar.setEnabled(false);
        btn_filtrar.setName(""); // NOI18N
        btn_filtrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_filtrarActionPerformed(evt);
            }
        });

        jLabel1.setText("Aula");

        jLabel2.setText("Departamento");

        jLabel3.setText("Carrera");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btn_generar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlist_aulas, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlist_departamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlist_carreras, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_filtrar)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_generar)
                    .addComponent(jlist_aulas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlist_departamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlist_carreras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_filtrar)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.setRowHeight(27);
        jScrollPane2.setViewportView(jTable1);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Información"));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_mensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_mensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Por Aula", jPanel1);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_generarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generarActionPerformed
        // TODO add your handling code here:
        jlist_aulas.setEnabled(false);
        jlist_departamentos.setEnabled(false);
        jlist_carreras.setEnabled(false);
        btn_filtrar.setEnabled(false);
        
        ArrayList<Materia> materias;        
        Procesador procesador = new Procesador();
        boolean cicloPar = true;
        
        materias = ManejadorMaterias.getTodasMaterias(cicloPar);
        
        for (int i = 0; i < materias.size(); i++) {
            Agrupacion agrup = getAgrupacion(materias.get(i).getCodigo(),materias.get(i).getDepartamento(),campus.getAgrupaciones());
            while(agrup.getNumGruposAsignados() < agrup.getNum_grupos()){
                try {
                    procesador.procesarMateria(campus, materias.get(i));
                } catch (Exception ex) {
                    //Se produce cuando ya no hay aulas disponibles
                    System.out.println(ex.getMessage());                                        
                }
                agrup.setNumGruposAsignados(agrup.getNumGruposAsignados()+1);
                //System.out.println("Grupo Asignado: "+agrup.getNumGruposAsignados()+" materia: "+agrup.getPropietario());
            }
        }
        jlist_aulas.setEnabled(true);
        jlist_departamentos.setEnabled(true);
        jlist_carreras.setEnabled(true);
        btn_filtrar.setEnabled(true);
    }//GEN-LAST:event_btn_generarActionPerformed

    private void btn_filtrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filtrarActionPerformed
        // TODO add your handling code here:
        aulaSeleccionada = jlist_aulas.getSelectedItem().toString();
        jTable1.setModel(ManejadorAulas.getHorarioEnAula(campus.getAulas(),jlist_aulas.getSelectedItem().toString(), modelo));
        
    }//GEN-LAST:event_btn_filtrarActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaInicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_filtrar;
    private javax.swing.JButton btn_generar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox jlist_aulas;
    private javax.swing.JComboBox jlist_carreras;
    private javax.swing.JComboBox jlist_departamentos;
    private javax.swing.JLabel lbl_mensaje;
    // End of variables declaration//GEN-END:variables

}