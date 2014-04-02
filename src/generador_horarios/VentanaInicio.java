/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import static generador_horarios.ManejadorAgrupaciones.getAgrupacion;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author alexander
 */
public class VentanaInicio extends javax.swing.JFrame implements MouseListener,ActionListener,ListSelectionListener{

    DefaultTableModel modelo;
    Facultad facultad;
    String aulaSeleccionada;
    String departamentoSeleccionado;
    String carreraSeleccionada;
    ArrayList<Materia> materias;
    int fila,columna; //representan la posición dentro del jTable
    String[][] datosTabla={};
    String[] cabeceraTabla={"Horas","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
    String[] listadoAulas;
    String[] listadoDepartamentos;
    String[] listadoCarreras;
    
    JMenuItem jm_abrir,jm_guardar;
    
    
    DefaultListModel<String> modeloLista;
    
    /**
     * Creates new form VentanaInicio
     */
    public VentanaInicio() {
        initComponents();
        fila =0;
        columna =0;
        
        //Se crea el objeto campus
        facultad = new Facultad(ManejadorAgrupaciones.getAgrupaciones(),ManejadorDepartamentos.getDepartamentos());
        
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
        jTable1.getSelectionModel().addListSelectionListener(this);
        jTable1.getColumnModel().getSelectionModel().addListSelectionListener(this);
        jTable1.setColumnSelectionAllowed(true);
        
        //Se llena la lista de aulas
        ArrayList<Aula> aulas = ManejadorAulas.getTodasAulasOrdenadas("cod_aula");
        jlist_aulas.addItem(null);
        for (int i = 0; i < aulas.size(); i++) {
            Aula aula = aulas.get(i);
            jlist_aulas.addItem(aula.getNombre());
        }
        jlist_aulas.setName("lista_aulas");
        jlist_aulas.addActionListener(this);
        
        aulaSeleccionada = null;
        departamentoSeleccionado = null;
        carreraSeleccionada = null;
        materias = new ArrayList();
        
        //Barra de Menú
        jm_abrir = new JMenuItem("Abrir");
        jm_guardar = new JMenuItem("Guardar");
        jm_abrir.addActionListener(this);
        jm_guardar.addActionListener(this);
        jMenu1.add(jm_abrir);
        jMenu1.add(jm_guardar);
        
    }
    
    
    
    public void imprimir(){
        ArrayList<Aula> aulas;
        aulas = facultad.getAulas();
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
//        if (e.getClickCount() == 1) {
//            JTable target = (JTable)e.getSource();
//            int row = target.getSelectedRow()+1;
//            int column = target.getSelectedColumn();
//            Grupo grupo = ManejadorGrupos.getGrupo(campus.getAulas(), aulaSeleccionada, target.getColumnName(column), row);            
//            
//            String nombreMateria = ManejadorMaterias.getNombreMateria(grupo.getCod_materia());
//            String nombreDepartamento = ManejadorDepartamentos.getNombreDepartamento(grupo.getId_depar());
//            lbl_mensaje.setText("<html>Materia: "+nombreMateria+"<br/>Grupo: "+grupo.getId_grupo()+"<br/>Departamento: "+nombreDepartamento+"</html>");
//            
//        }
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

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.        
        if(e.getSource()==jlist_aulas || e.getSource() == jlist_departamentos || e.getSource() == jlist_carreras){
            JComboBox lista = (JComboBox)e.getSource();
            if(lista!=null){            
                if(lista.getName().equals("lista_aulas")){
                    if(lista.getSelectedItem()!=null){
                        llenarListaDepartamentos();
                        jlist_departamentos.setEnabled(true);
                        btn_filtrar.setEnabled(true);
                    }else{
                        jlist_departamentos.setEnabled(false);
                        btn_filtrar.setEnabled(false);
                    }                
                }else if(lista.getName().equals("lista_departamentos")){
                    if(lista.getSelectedItem()!=null){
                        llenarListaCarreras(lista.getSelectedItem().toString());
                        jlist_carreras.setEnabled(true);
                    }else{
                        jlist_carreras.setEnabled(false);
                    }
                }
            }
        }else if(e.getSource()==jm_abrir){
            //Crear un objeto FileChooser
            JFileChooser fc = new JFileChooser();
            //Mostrar la ventana para abrir archivo y recoger la respuesta
            //En el parámetro del showOpenDialog se indica la ventana
            //  al que estará asociado. Con el valor this se asocia a la
            //  ventana que la abre.
            int respuesta = fc.showOpenDialog(this);
            //Comprobar si se ha pulsado Aceptar
            if (respuesta == JFileChooser.APPROVE_OPTION)
            {
                //Crear un objeto File con el archivo elegido
                File archivoElegido = fc.getSelectedFile();                
                try
                {
                   FileInputStream fileIn = new FileInputStream(archivoElegido.getPath());
                   ObjectInputStream in = new ObjectInputStream(fileIn);
                   facultad = (Facultad) in.readObject();
                   in.close();
                   fileIn.close();
                   jlist_aulas.setEnabled(true);
                }catch(IOException i)
                {
                   i.printStackTrace();
                   return;
                }catch(ClassNotFoundException c)
                {
                   System.out.println("Campus class not found");
                   c.printStackTrace();
                   return;
                }
                JOptionPane.showMessageDialog(null, archivoElegido.getPath());
            }
        }else if(e.getSource()==jm_guardar){
            //Crear un objeto FileChooser
            JFileChooser fc = new JFileChooser();
            //Mostrar la ventana para abrir archivo y recoger la respuesta
            //En el parámetro del showOpenDialog se indica la ventana
            //  al que estará asociado. Con el valor this se asocia a la
            //  ventana que la abre.
            int respuesta = fc.showSaveDialog(this);
            //Comprobar si se ha pulsado Aceptar
            if (respuesta == JFileChooser.APPROVE_OPTION)
            {
                File archivoElegido = fc.getSelectedFile();
                try
                {
                   FileOutputStream fileOut = new FileOutputStream(archivoElegido.getPath());
                   ObjectOutputStream out = new ObjectOutputStream(fileOut);
                   out.writeObject(facultad);
                   out.close();
                   fileOut.close();                   
                }catch(IOException i)
                {
                    i.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Se guardó en: "+archivoElegido.getPath());
                
            }
            //JOptionPane.showMessageDialog(null, "guardar");
            
        }
        
    }
    
    public void llenarListaDepartamentos(){
        //Se llena la lista de departamentos
        ArrayList<String> departamentos= ManejadorDepartamentos.getNombreDepartamentos();
            jlist_departamentos.addItem(null);
        for (int i = 0; i < departamentos.size(); i++) {
            jlist_departamentos.addItem(departamentos.get(i));
        }
        jlist_departamentos.setName("lista_departamentos");
        jlist_departamentos.addActionListener(this);
    }
    
    public void llenarListaCarreras(String nombreDepartamento){
        jlist_carreras.removeAllItems();
        int idDepartamento = ManejadorDepartamentos.getIdDepartamento(nombreDepartamento);
        //Se llena la lista de carreras
        ArrayList<String> carreras= ManejadorCarreras.getNombreTodasCarrerasPorDepartamento(idDepartamento);
        jlist_carreras.addItem(null);
        for (int i = 0; i < carreras.size(); i++) {
            jlist_carreras.addItem(carreras.get(i));
        }
        jlist_carreras.setName("lista_carreras");
        jlist_carreras.addActionListener(this);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        if(!e.getValueIsAdjusting()){
            if (e.getSource() == jTable1.getSelectionModel() && jTable1.getRowSelectionAllowed()) {
                // Column selection changed
                //int first = e.getFirstIndex();
                //fila = e.getLastIndex();
                fila = jTable1.getSelectedRow()+1;
                //System.out.println("fila: "+fila);
            } else if (e.getSource() == jTable1.getColumnModel().getSelectionModel() && jTable1.getColumnSelectionAllowed() ){
                // Row selection changed
                //int first = e.getFirstIndex();
                //columna = e.getLastIndex();
                
                columna = jTable1.getSelectedColumn();
                //System.out.println("columna: "+columna);
            }
            //JTable target = (JTable)e.getSource();
            Grupo grupo = ManejadorGrupos.getGrupo(facultad.getAulas(), aulaSeleccionada, jTable1.getColumnName(columna), fila);            
//            jTable1.getCellRenderer(fila, columna).getTableCellRendererComponent(jTable1, e, rootPaneCheckingEnabled, rootPaneCheckingEnabled, fila, columna).setBackground(Color.GREEN);
            String nombreMateria = ManejadorMaterias.getNombreMateria(grupo.getCod_materia());
            String nombreDepartamento = ManejadorDepartamentos.getNombreDepartamento(grupo.getId_depar());
            lbl_mensaje.setText("<html>Materia: "+nombreMateria+"<br/>Grupo: "+grupo.getId_grupo()+"<br/>Departamento: "+nombreDepartamento+"</html>");
        }
    }
    
    class MyRenderer implements TableCellRenderer {
        Color color = Color.WHITE;
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
            
                JLabel texto = new JLabel();
                texto.setBackground(color);
                if(value != null)
                    texto.setText(value.toString());
                return texto;
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
            
//            
//          JTextArea display = new JTextArea();
//          if (value != null)
//            display.setText(value.toString());
//          display.setEditable(false);
//          display.setBackground((row % 2 == 0) ? Color.white : new Color(211,211,211));
//          return display;
        }
        
        public void cambiarColorFondo(Color color){
            this.color = color;
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

        jMenu1.setText("Archivo");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editar");
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
        
                
        
        boolean cicloPar = true;
        
        facultad.setMaterias(ManejadorMaterias.getTodasMaterias(cicloPar));
        materias = facultad.getMaterias();
        
        Procesador procesador = new Procesador();
        procesador.setFacultad(facultad);
        
        
        for (int i = 0; i < materias.size(); i++) {
            Agrupacion agrup = getAgrupacion(materias.get(i).getCodigo(),materias.get(i).getDepartamento(),facultad.getAgrupaciones());
            while(agrup.getNumGruposAsignados() < agrup.getNum_grupos()){
                try {         
                    procesador.procesarMateria(materias.get(i));
                } catch (Exception ex) {
                    //Se produce cuando ya no hay aulas disponibles
                    System.out.println(ex.getMessage());                                        
                }
                agrup.setNumGruposAsignados(agrup.getNumGruposAsignados()+1);
                //System.out.println("Grupo Asignado: "+agrup.getNumGruposAsignados()+" materia: "+agrup.getPropietario());
            }
        }
        jlist_aulas.setEnabled(true);
    }//GEN-LAST:event_btn_generarActionPerformed

    private void btn_filtrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filtrarActionPerformed
        // TODO add your handling code here:        
        
        if(jlist_aulas.getSelectedItem()!=null && jlist_carreras.getSelectedItem()==null && jlist_departamentos.getSelectedItem()==null){
            System.out.println("aula");
            aulaSeleccionada = jlist_aulas.getSelectedItem().toString();
            jTable1.setModel(ManejadorAulas.getHorarioEnAula(facultad.getAulas(),aulaSeleccionada, modelo));
        }else if(jlist_aulas.getSelectedItem()!=null && jlist_carreras.getSelectedItem()!=null){
            System.out.println("carrera");
            aulaSeleccionada = jlist_aulas.getSelectedItem().toString();
            carreraSeleccionada = jlist_carreras.getSelectedItem().toString();
            ArrayList<Materia> materiasCarrera = ManejadorMaterias.getMateriasDeCarrera(materias, ManejadorCarreras.getCodigoCarrera(carreraSeleccionada));
            jTable1.setModel(ManejadorAulas.getHorarioEnAula_Carrera(facultad.getAulas(), aulaSeleccionada, modelo, facultad.getDepartamentos(), materiasCarrera));
        }else if(jlist_aulas.getSelectedItem()!=null && jlist_departamentos.getSelectedItem()!=null && jlist_carreras.getSelectedItem()==null){
            System.out.println("departamento");
            aulaSeleccionada = jlist_aulas.getSelectedItem().toString();
            departamentoSeleccionado = jlist_departamentos.getSelectedItem().toString();
            departamentoSeleccionado = ""+ManejadorDepartamentos.getIdDepar(departamentoSeleccionado, facultad.getDepartamentos());
            jTable1.setModel(ManejadorAulas.getHorarioEnAula_Depar(facultad.getAulas(), aulaSeleccionada, modelo, Integer.parseInt(departamentoSeleccionado), facultad.getDepartamentos()));
        }
            
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
