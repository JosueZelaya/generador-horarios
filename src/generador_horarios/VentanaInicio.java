/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import static generador_horarios.ManejadorAgrupaciones.getAgrupacion;
import static generador_horarios.ManejadorAgrupaciones.getAgrupaciones;
import static generador_horarios.ManejadorAgrupaciones.obtenerNombrePropietario;
import static generador_horarios.ManejadorAgrupaciones.obtenerIdDepartamento;
import static generador_horarios.ManejadorAgrupaciones.obtenerAgrupacionesDeCarrera;
import static generador_horarios.ManejadorAsignacionesDocs.obtenerAsignacionesDeAgrup;
import static generador_horarios.ManejadorAsignacionesDocs.obtenerTodasAsignacionesDocs;
import static generador_horarios.ManejadorDepartamentos.getDepartamentos;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alexander
 */
public class VentanaInicio extends javax.swing.JFrame{

    DefaultTableModel modelo;
    Facultad facultad;
    String aulaSeleccionada;
    String departamentoSeleccionado;
    String carreraSeleccionada;
    int fila,columna; //representan la posición dentro del jTable
    String[][] datosTabla={};
    String[] cabeceraTabla={"Horas","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
    
    /**
     * Creates new form VentanaInicio
     */
    public VentanaInicio() {
        initComponents();
        this.setLocationRelativeTo(null);
        cmb_dia.setEditable(false);
        cmb_hora_init.setEditable(false);
        addReserv.setEnabled(true);
        
        fila =0;
        columna =0;
        
        //Se crea el objeto campus
        facultad = new Facultad(getAgrupaciones(),getDepartamentos(),obtenerTodasAsignacionesDocs());
        
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
        tabla_depar.setModel(modelo);
        tabla_aula.setModel(modelo);
        //Evento cuando se selecciona una fila
        tabla_aula.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                jtbl_filaValueChanged(e);
            }
        });
        //Evento cuando se selecciona una columna
        tabla_aula.getColumnModel().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                jtbl_columnaValueChanged(e);
            }
        });
        
        tabla_aula.setColumnSelectionAllowed(true);
        
        //Se llena la lista de aulas
        cmb_aula_aula.setModel(llenarAulas());
        
        aulaSeleccionada = null;
        departamentoSeleccionado = null;
        carreraSeleccionada = null;
    }
    
    private DefaultComboBoxModel llenarAulas(){
        DefaultComboBoxModel modelo_aulas = new DefaultComboBoxModel();
        ArrayList<Aula> aulas = ManejadorAulas.getTodasAulasOrdenadas("cod_aula");
        for(int i=0; i<aulas.size(); i++){
            modelo_aulas.addElement(aulas.get(i).getNombre());
        }
        modelo_aulas.setSelectedItem(null);
        return modelo_aulas;
    }
    
    private void llenarListaDias(){
        ArrayList<Dia> dias = ManejadorDias.getDias();
        DefaultComboBoxModel lista = new DefaultComboBoxModel();
        cmb_dia.removeAllItems();
        for(int i=0; i<dias.size(); i++){
            lista.addElement(dias.get(i).getNombre());
        }
        lista.setSelectedItem(null);
        cmb_dia.setModel(lista);
    }
    
    private DefaultComboBoxModel llenarListaDepartamentos(){
        DefaultComboBoxModel modelo_depars = new DefaultComboBoxModel();
        ArrayList<String> departamentos= ManejadorDepartamentos.getNombreDepartamentos();
        modelo_depars.addElement(null);
        for (int i = 0; i < departamentos.size(); i++) {
            modelo_depars.addElement(departamentos.get(i));
        }
        return modelo_depars;
    }
    
    private void llenarListaCarreras(String nombreDepartamento){
        cmb_carrera_aula.removeAllItems();
        int idDepartamento = ManejadorDepartamentos.getIdDepartamento(nombreDepartamento);
        //Se llena la lista de carreras
        ArrayList<String> carreras= ManejadorCarreras.getNombreTodasCarrerasPorDepartamento(idDepartamento);
        cmb_carrera_aula.addItem(null);
        for (int i = 0; i < carreras.size(); i++) {
            cmb_carrera_aula.addItem(carreras.get(i));
        }
    }
    
    private void llenarListaHorasDia(String dia){
        ArrayList<Hora> horas = ManejadorDias.obtenerHorasDia(dia);
        cmb_hora_init.removeAllItems();
        for(int i=0; i<horas.size(); i++){
            cmb_hora_init.addItem(horas.get(i).getInicio());
        }
    }
    
    private DefaultComboBoxModel listaMateriasDeDepartamento(int id_depar){
        DefaultComboBoxModel modelo_mats = new DefaultComboBoxModel();
        ArrayList<Materia> mats = ManejadorMaterias.obtenerMateriasDeDepartamento(facultad.getMaterias(), id_depar);
        modelo_mats.addElement(null);
        for (int i = 0; i < mats.size(); i++) {
            modelo_mats.addElement(mats.get(i).getNombre());
        }
        return modelo_mats;
    }
    
    private void llenarTablaReservaciones(){
        limpiarTabla(table_reserv);
        DefaultTableModel model = (DefaultTableModel) table_reserv.getModel();
        ArrayList<Reservacion> reservs = ManejadorReservaciones.getTodasReservaciones();
        model.setRowCount(reservs.size());
        for (int i=0;i<reservs.size();i++){
            model.setValueAt(reservs.get(i).nombre_dia, i, 0);
            model.setValueAt(reservs.get(i).inicio, i, 1);
            model.setValueAt(reservs.get(i).fin, i, 2);
            model.setValueAt(reservs.get(i).cod_aula, i, 3);
        }
        table_reserv.setModel(model);
        if(table_reserv.getRowCount() == 0)
            delReserv.setEnabled(false);
        else
            delReserv.setEnabled(true);
    }
    
    private void limpiarTabla(JTable tabla){
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        for(int i=0;i<model.getRowCount();i++){
            model.removeRow(i);
        }
    }
    
    private void mostrarInfoMateria(){
        Grupo grupo = ManejadorGrupos.getGrupo(facultad.getAulas(), aulaSeleccionada, tabla_aula.getColumnName(columna), fila);            
        String nombreMateria = obtenerNombrePropietario(grupo.getId_Agrup(),facultad.getMaterias());
        String nombreDepartamento = ManejadorDepartamentos.getNombreDepartamento(obtenerIdDepartamento(grupo.getId_Agrup(),facultad.agrupaciones),facultad.departamentos);
        lbl_mensaje.setText("<html>Materia: "+nombreMateria+"<br/>Grupo: "+grupo.getId_grupo()+"<br/>Departamento: "+nombreDepartamento+"</html>");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialog_new_reserv = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cmb_aula = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        cmb_dia = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        cmb_hora_init = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        sp_num_horas = new javax.swing.JSpinner();
        ok_reserv = new javax.swing.JButton();
        cancel_reserv = new javax.swing.JButton();
        dialog_reserv = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        addReserv = new javax.swing.JButton();
        delReserv = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_reserv = new javax.swing.JTable();
        panel_pestanias = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btn_generar = new javax.swing.JButton();
        cmb_aula_aula = new javax.swing.JComboBox();
        cmb_depto_aula = new javax.swing.JComboBox();
        cmb_carrera_aula = new javax.swing.JComboBox();
        btn_filtrar_aula = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla_aula = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        lbl_mensaje = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        cmb_depar_depar = new javax.swing.JComboBox();
        cmb_materia_depar = new javax.swing.JComboBox();
        cmb_grupo_depar = new javax.swing.JComboBox();
        btn_filtrar_dep = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_depar = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jm_abrir = new javax.swing.JMenuItem();
        jm_guardar = new javax.swing.JMenuItem();
        jm_reserv = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        dialog_new_reserv.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dialog_new_reserv.setTitle("Nueva Reservacion");
        dialog_new_reserv.setModal(true);
        dialog_new_reserv.setName("dialog_new_reserv"); // NOI18N
        dialog_new_reserv.setResizable(false);

        jLabel4.setText("Aula:");

        cmb_aula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_aulaActionPerformed(evt);
            }
        });

        jLabel5.setText("Dia:");

        cmb_dia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_diaActionPerformed(evt);
            }
        });

        jLabel6.setText("Hora Inicial:");

        jLabel7.setText("Numero de horas:");

        sp_num_horas.setModel(new javax.swing.SpinnerNumberModel(1, 1, 8, 1));

        ok_reserv.setText("Aceptar");
        ok_reserv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ok_reservActionPerformed(evt);
            }
        });

        cancel_reserv.setText("Cancelar");
        cancel_reserv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_reservActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sp_num_horas, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmb_aula, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmb_dia, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmb_hora_init, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(ok_reserv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancel_reserv)
                .addGap(29, 29, 29))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancel_reserv, ok_reserv});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmb_aula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmb_dia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cmb_hora_init, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(sp_num_horas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ok_reserv)
                    .addComponent(cancel_reserv))
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout dialog_new_reservLayout = new javax.swing.GroupLayout(dialog_new_reserv.getContentPane());
        dialog_new_reserv.getContentPane().setLayout(dialog_new_reservLayout);
        dialog_new_reservLayout.setHorizontalGroup(
            dialog_new_reservLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialog_new_reservLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dialog_new_reservLayout.setVerticalGroup(
            dialog_new_reservLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialog_new_reservLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dialog_reserv.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dialog_reserv.setTitle("Reservaciones");
        dialog_reserv.setModal(true);
        dialog_reserv.setResizable(false);

        addReserv.setText("Agregar");
        addReserv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addReservActionPerformed(evt);
            }
        });

        delReserv.setText("Eliminar");
        delReserv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delReservActionPerformed(evt);
            }
        });

        table_reserv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Dia", "Inicio", "Final", "Aula"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(table_reserv);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addReserv)
                    .addComponent(delReserv))
                .addGap(31, 31, 31))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addReserv, delReserv});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(addReserv)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(delReserv))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dialog_reservLayout = new javax.swing.GroupLayout(dialog_reserv.getContentPane());
        dialog_reserv.getContentPane().setLayout(dialog_reservLayout);
        dialog_reservLayout.setHorizontalGroup(
            dialog_reservLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialog_reservLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        dialog_reservLayout.setVerticalGroup(
            dialog_reservLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialog_reservLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Generador de Horarios");

        panel_pestanias.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                panel_pestaniasStateChanged(evt);
            }
        });

        btn_generar.setText(" Generar Horario");
        btn_generar.setName(""); // NOI18N
        btn_generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generarActionPerformed(evt);
            }
        });

        cmb_aula_aula.setEnabled(false);
        cmb_aula_aula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jlist_aulasActionPerformed(evt);
            }
        });

        cmb_depto_aula.setEnabled(false);
        cmb_depto_aula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jlist_departamentosActionPerformed(evt);
            }
        });

        cmb_carrera_aula.setEnabled(false);

        btn_filtrar_aula.setText("Filtrar");
        btn_filtrar_aula.setEnabled(false);
        btn_filtrar_aula.setName(""); // NOI18N
        btn_filtrar_aula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_filtrar_aulaActionPerformed(evt);
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
                .addComponent(cmb_aula_aula, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_depto_aula, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_carrera_aula, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_filtrar_aula)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_generar)
                    .addComponent(cmb_aula_aula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_depto_aula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_carrera_aula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_filtrar_aula)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabla_aula.setModel(new javax.swing.table.DefaultTableModel(
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
        tabla_aula.setRowHeight(27);
        jScrollPane2.setViewportView(tabla_aula);

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
                .addComponent(jScrollPane2)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel_pestanias.addTab("Por Aula", jPanel1);

        cmb_depar_depar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_depar_deparActionPerformed(evt);
            }
        });

        btn_filtrar_dep.setText("FIltrar");

        jLabel8.setText("Departamento");

        jLabel9.setText("Materia");

        jLabel10.setText("Grupo");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_depar_depar, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_materia_depar, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_grupo_depar, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(btn_filtrar_dep)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmb_depar_depar, cmb_grupo_depar, cmb_materia_depar});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_depar_depar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_materia_depar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_grupo_depar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_filtrar_dep)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabla_depar.setModel(new javax.swing.table.DefaultTableModel(
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
        tabla_depar.setColumnSelectionAllowed(true);
        tabla_depar.setRowHeight(27);
        jScrollPane1.setViewportView(tabla_depar);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel_pestanias.addTab("Por Departamento", jPanel6);

        jMenu1.setText("Archivo");

        jm_abrir.setText("Abrir");
        jm_abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_abrirActionPerformed(evt);
            }
        });
        jMenu1.add(jm_abrir);

        jm_guardar.setText("Guardar");
        jm_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_guardarActionPerformed(evt);
            }
        });
        jMenu1.add(jm_guardar);

        jm_reserv.setText("Reservación");
        jm_reserv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jm_reservActionPerformed(evt);
            }
        });
        jMenu1.add(jm_reserv);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editar");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_pestanias)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_pestanias)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtbl_filaValueChanged(ListSelectionEvent e){
        fila = tabla_aula.getSelectedRow()+1;
        mostrarInfoMateria();
    }
    
    private void jtbl_columnaValueChanged(ListSelectionEvent e){
        columna = tabla_aula.getSelectedColumn();
        mostrarInfoMateria();
    }
    
    private void btn_generarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generarActionPerformed
        cmb_aula_aula.setEnabled(false);
        cmb_depto_aula.setEnabled(false);
        cmb_carrera_aula.setEnabled(false);
        btn_filtrar_aula.setEnabled(false);      
        
        boolean cicloPar = false;
        
        facultad.setMaterias(ManejadorMaterias.getTodasMaterias(cicloPar));
        //Marcar horas ocupadas
        ManejadorReservaciones.asignarReservaciones(facultad);
        
        ArrayList<Materia> materias = facultad.getMaterias();
        
        Procesador procesador = new Procesador();
        procesador.asignarDatos(facultad);
        
        for (int i = 0; i < materias.size(); i++) {
            Agrupacion agrup = getAgrupacion(materias.get(i).getIdAgrupacion(),facultad.agrupaciones);
            if(agrup.getNum_grupos() == agrup.getNumGruposAsignados())
                continue;
            ArrayList<AsignacionDocente> asignaciones = obtenerAsignacionesDeAgrup(agrup.getId(),facultad.asignaciones_docs);
            for(AsignacionDocente asignacion : asignaciones){
                for(int j = 0; j < asignacion.getNum_grupos(); j++){
                    try {         
                        procesador.procesarMateria(materias.get(i),asignacion.getId_docente(),agrup);
                    } catch (Exception ex) {
                        //Se produce cuando ya no hay aulas disponibles
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    agrup.setNumGruposAsignados(agrup.getNumGruposAsignados()+1);
                }
            }
        }
        cmb_aula_aula.setEnabled(true);
    }//GEN-LAST:event_btn_generarActionPerformed

    private void btn_filtrar_aulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filtrar_aulaActionPerformed
        if(cmb_aula_aula.getSelectedItem()!=null && cmb_carrera_aula.getSelectedItem()==null && cmb_depto_aula.getSelectedItem()==null){
            aulaSeleccionada = cmb_aula_aula.getSelectedItem().toString();
            tabla_aula.setModel(ManejadorAulas.getHorarioEnAula(facultad.getAulas(),aulaSeleccionada, modelo, facultad.getMaterias()));
        }else if(cmb_aula_aula.getSelectedItem()!=null && cmb_carrera_aula.getSelectedItem()!=null){
            aulaSeleccionada = cmb_aula_aula.getSelectedItem().toString();
            carreraSeleccionada = cmb_carrera_aula.getSelectedItem().toString();
            ArrayList<Materia> materiasCarrera = ManejadorMaterias.getMateriasDeCarrera(facultad.getMaterias(), ManejadorCarreras.getCodigoCarrera(carreraSeleccionada));
            tabla_aula.setModel(ManejadorAulas.getHorarioEnAula_Carrera(facultad.getAulas(), aulaSeleccionada, modelo, obtenerAgrupacionesDeCarrera(carreraSeleccionada), materiasCarrera));
        }else if(cmb_aula_aula.getSelectedItem()!=null && cmb_depto_aula.getSelectedItem()!=null && cmb_carrera_aula.getSelectedItem()==null){
            aulaSeleccionada = cmb_aula_aula.getSelectedItem().toString();
            departamentoSeleccionado = cmb_depto_aula.getSelectedItem().toString();
            departamentoSeleccionado = ""+ManejadorDepartamentos.getIdDepar(departamentoSeleccionado, facultad.departamentos);
            tabla_aula.setModel(ManejadorAulas.getHorarioEnAula_Depar(facultad.getAulas(), aulaSeleccionada, modelo, Integer.parseInt(departamentoSeleccionado), facultad.agrupaciones, facultad.getMaterias()));
        }  
    }//GEN-LAST:event_btn_filtrar_aulaActionPerformed

    private void jm_reservActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_reservActionPerformed
        dialog_reserv.pack();
        dialog_reserv.setLocationRelativeTo(this);
        llenarTablaReservaciones();
        dialog_reserv.setVisible(true);
    }//GEN-LAST:event_jm_reservActionPerformed

    private void ok_reservActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ok_reservActionPerformed
        String aula = cmb_aula.getSelectedItem().toString();
        int id_hora = cmb_hora_init.getSelectedIndex();
        String dia = cmb_dia.getSelectedItem().toString();
        for(int i=0; i<(int)sp_num_horas.getValue();i++){
            id_hora++;
            ManejadorReservaciones.nuevaReservacion(dia, id_hora, aula);
        }
        dialog_new_reserv.dispose();
        llenarTablaReservaciones();
    }//GEN-LAST:event_ok_reservActionPerformed

    private void cmb_aulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_aulaActionPerformed
        if(!cmb_dia.isEnabled()){
            llenarListaDias();
            cmb_dia.setEnabled(true);
        }
    }//GEN-LAST:event_cmb_aulaActionPerformed

    private void cmb_diaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_diaActionPerformed
        if(cmb_dia.getSelectedItem() != null){
            llenarListaHorasDia(cmb_dia.getSelectedItem().toString());
            cmb_hora_init.setSelectedIndex(-1);
            cmb_hora_init.setEnabled(true);
        }
    }//GEN-LAST:event_cmb_diaActionPerformed

    private void jlist_aulasActionPerformed(java.awt.event.ActionEvent evt) {                                            
        if(cmb_aula_aula.getSelectedItem()!=null){
            cmb_depto_aula.setModel(llenarListaDepartamentos());
            cmb_depto_aula.setEnabled(true);
            btn_filtrar_aula.setEnabled(true);
        }else{
            cmb_depto_aula.setSelectedItem(null);
            cmb_carrera_aula.setSelectedItem(null);
            cmb_depto_aula.setEnabled(false);
            cmb_carrera_aula.setEnabled(false);
            btn_filtrar_aula.setEnabled(false);
        }
    }                                           

    private void jlist_departamentosActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        if(cmb_depto_aula.getSelectedItem()!=null){
            llenarListaCarreras(cmb_depto_aula.getSelectedItem().toString());
            cmb_carrera_aula.setEnabled(true);
        }else{
            cmb_carrera_aula.setSelectedItem(null);
            cmb_carrera_aula.setEnabled(false);
        } 
    }                                                   

    private void jm_abrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_abrirActionPerformed
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
               cmb_aula_aula.setEnabled(true);
            }catch(IOException | ClassNotFoundException i)
            {
               JOptionPane.showMessageDialog(this, i.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
               return;
            }
            JOptionPane.showMessageDialog(null, archivoElegido.getPath());
        }
    }//GEN-LAST:event_jm_abrirActionPerformed

    private void jm_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm_guardarActionPerformed
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
                JOptionPane.showMessageDialog(this, i.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "Se guardó en: "+archivoElegido.getPath());

        }
    }//GEN-LAST:event_jm_guardarActionPerformed

    private void cancel_reservActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_reservActionPerformed
        dialog_new_reserv.dispose();
    }//GEN-LAST:event_cancel_reservActionPerformed

    private void addReservActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addReservActionPerformed
        dialog_new_reserv.pack();
        dialog_new_reserv.setLocationRelativeTo(this);
        cmb_aula.setModel(llenarAulas());
        cmb_dia.setEnabled(false);
        cmb_hora_init.setEnabled(false);
        dialog_new_reserv.setVisible(true);
    }//GEN-LAST:event_addReservActionPerformed

    private void delReservActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delReservActionPerformed
        String dia = (String) table_reserv.getValueAt(table_reserv.getSelectedRow(), 0);
        String hora = (String) table_reserv.getValueAt(table_reserv.getSelectedRow(), 1);
        String aula = (String) table_reserv.getValueAt(table_reserv.getSelectedRow(), 3);
        ManejadorReservaciones.eliminarReservacion(dia, hora, aula, facultad.getAulas());
        llenarTablaReservaciones();
    }//GEN-LAST:event_delReservActionPerformed

    private void panel_pestaniasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_panel_pestaniasStateChanged
        if(panel_pestanias.getSelectedIndex() == 0){
            
        }
        else if(panel_pestanias.getSelectedIndex() == 1){
            cmb_depar_depar.setModel(llenarListaDepartamentos());
            cmb_materia_depar.setEnabled(false);
            cmb_grupo_depar.setEnabled(false);
        }
    }//GEN-LAST:event_panel_pestaniasStateChanged

    private void cmb_depar_deparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_depar_deparActionPerformed
        if(cmb_depar_depar.getSelectedItem() != null){
            String depar = cmb_depar_depar.getSelectedItem().toString();
            cmb_materia_depar.setModel(listaMateriasDeDepartamento(ManejadorDepartamentos.getIdDepar(depar, facultad.departamentos)));
            cmb_materia_depar.setEnabled(true);
        } else{
            cmb_materia_depar.setEnabled(false);
            cmb_grupo_depar.setEnabled(false);
        }
    }//GEN-LAST:event_cmb_depar_deparActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                VentanaInicio ventanaInicial = new VentanaInicio();
                ventanaInicial.setLocationRelativeTo(null);
                ventanaInicial.setVisible(true); 
                //new VentanaInicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addReserv;
    private javax.swing.JButton btn_filtrar_aula;
    private javax.swing.JButton btn_filtrar_dep;
    private javax.swing.JButton btn_generar;
    private javax.swing.JButton cancel_reserv;
    private javax.swing.JComboBox cmb_aula;
    private javax.swing.JComboBox cmb_aula_aula;
    private javax.swing.JComboBox cmb_carrera_aula;
    private javax.swing.JComboBox cmb_depar_depar;
    private javax.swing.JComboBox cmb_depto_aula;
    private javax.swing.JComboBox cmb_dia;
    private javax.swing.JComboBox cmb_grupo_depar;
    private javax.swing.JComboBox cmb_hora_init;
    private javax.swing.JComboBox cmb_materia_depar;
    private javax.swing.JButton delReserv;
    private javax.swing.JDialog dialog_new_reserv;
    private javax.swing.JDialog dialog_reserv;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JMenuItem jm_abrir;
    private javax.swing.JMenuItem jm_guardar;
    private javax.swing.JMenuItem jm_reserv;
    private javax.swing.JLabel lbl_mensaje;
    private javax.swing.JButton ok_reserv;
    private javax.swing.JTabbedPane panel_pestanias;
    private javax.swing.JSpinner sp_num_horas;
    private javax.swing.JTable tabla_aula;
    private javax.swing.JTable tabla_depar;
    private javax.swing.JTable table_reserv;
    // End of variables declaration//GEN-END:variables

}
