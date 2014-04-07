/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import static generador_horarios.ManejadorAgrupaciones.getAgrupacion;
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
    ArrayList<Materia> materias;
    int fila,columna; //representan la posición dentro del jTable
    String[][] datosTabla={};
    String[] cabeceraTabla={"Horas","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
    String[] listadoAulas;
    String[] listadoDepartamentos;
    String[] listadoCarreras;
    
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
        tabla_horario.setModel(modelo);
        //Evento cuando se selecciona una fila
        tabla_horario.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                jtbl_filaValueChanged(e);
            }
        });
        //Evento cuando se selecciona una columna
        tabla_horario.getColumnModel().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                jtbl_columnaValueChanged(e);
            }
        });
        
        tabla_horario.setColumnSelectionAllowed(true);
        
        //Se llena la lista de aulas
        jlist_aulas.setModel(llenarAulas());
        
        aulaSeleccionada = null;
        departamentoSeleccionado = null;
        carreraSeleccionada = null;
        materias = new ArrayList();
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
    
    private void llenarListaDepartamentos(){
        //Se llena la lista de departamentos
        ArrayList<String> departamentos= ManejadorDepartamentos.getNombreDepartamentos();
        jlist_departamentos.addItem(null);
        for (int i = 0; i < departamentos.size(); i++) {
            jlist_departamentos.addItem(departamentos.get(i));
        }
    }
    
    private void llenarListaCarreras(String nombreDepartamento){
        jlist_carreras.removeAllItems();
        int idDepartamento = ManejadorDepartamentos.getIdDepartamento(nombreDepartamento);
        //Se llena la lista de carreras
        ArrayList<String> carreras= ManejadorCarreras.getNombreTodasCarrerasPorDepartamento(idDepartamento);
        jlist_carreras.addItem(null);
        for (int i = 0; i < carreras.size(); i++) {
            jlist_carreras.addItem(carreras.get(i));
        }
    }
    
    private void llenarListaHorasDia(String dia){
        ArrayList<Hora> horas = ManejadorDias.obtenerHorasDia(dia);
        cmb_hora_init.removeAllItems();
        for(int i=0; i<horas.size(); i++){
            cmb_hora_init.addItem(horas.get(i).getInicio());
        }
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
        Grupo grupo = ManejadorGrupos.getGrupo(facultad.getAulas(), aulaSeleccionada, tabla_horario.getColumnName(columna), fila);            
        String nombreMateria = ManejadorMaterias.getNombreMateria(grupo.getCod_materia());
        String nombreDepartamento = ManejadorDepartamentos.getNombreDepartamento(grupo.getId_depar());
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
        tabla_horario = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        lbl_mensaje = new javax.swing.JLabel();
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

        btn_generar.setText(" Generar Horario");
        btn_generar.setName(""); // NOI18N
        btn_generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generarActionPerformed(evt);
            }
        });

        jlist_aulas.setEnabled(false);
        jlist_aulas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jlist_aulasActionPerformed(evt);
            }
        });

        jlist_departamentos.setEnabled(false);
        jlist_departamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jlist_departamentosActionPerformed(evt);
            }
        });

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

        tabla_horario.setModel(new javax.swing.table.DefaultTableModel(
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
        tabla_horario.setRowHeight(27);
        jScrollPane2.setViewportView(tabla_horario);

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

        jTabbedPane1.addTab("Por Aula", jPanel1);

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
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtbl_filaValueChanged(ListSelectionEvent e){
        fila = tabla_horario.getSelectedRow()+1;
        mostrarInfoMateria();
    }
    
    private void jtbl_columnaValueChanged(ListSelectionEvent e){
        columna = tabla_horario.getSelectedColumn();
        mostrarInfoMateria();
    }
    
    private void btn_generarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generarActionPerformed
        // TODO add your handling code here:
        jlist_aulas.setEnabled(false);
        jlist_departamentos.setEnabled(false);
        jlist_carreras.setEnabled(false);
        btn_filtrar.setEnabled(false);      
        
        boolean cicloPar = true;
        
        facultad.setMaterias(ManejadorMaterias.getTodasMaterias(cicloPar));
        //Marcar horas ocupadas
        ManejadorReservaciones.asignarReservaciones(facultad);
        
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
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                agrup.setNumGruposAsignados(agrup.getNumGruposAsignados()+1);
            }
        }
        jlist_aulas.setEnabled(true);
    }//GEN-LAST:event_btn_generarActionPerformed

    private void btn_filtrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filtrarActionPerformed
        if(jlist_aulas.getSelectedItem()!=null && jlist_carreras.getSelectedItem()==null && jlist_departamentos.getSelectedItem()==null){
            //System.out.println("aula");
            aulaSeleccionada = jlist_aulas.getSelectedItem().toString();
            tabla_horario.setModel(ManejadorAulas.getHorarioEnAula(facultad.getAulas(),aulaSeleccionada, modelo));
        }else if(jlist_aulas.getSelectedItem()!=null && jlist_carreras.getSelectedItem()!=null){
            //System.out.println("carrera");
            aulaSeleccionada = jlist_aulas.getSelectedItem().toString();
            carreraSeleccionada = jlist_carreras.getSelectedItem().toString();
            ArrayList<Materia> materiasCarrera = ManejadorMaterias.getMateriasDeCarrera(materias, ManejadorCarreras.getCodigoCarrera(carreraSeleccionada));
            tabla_horario.setModel(ManejadorAulas.getHorarioEnAula_Carrera(facultad.getAulas(), aulaSeleccionada, modelo, materiasCarrera));
        }else if(jlist_aulas.getSelectedItem()!=null && jlist_departamentos.getSelectedItem()!=null && jlist_carreras.getSelectedItem()==null){
            //System.out.println("departamento");
            aulaSeleccionada = jlist_aulas.getSelectedItem().toString();
            departamentoSeleccionado = jlist_departamentos.getSelectedItem().toString();
            departamentoSeleccionado = ""+ManejadorDepartamentos.getIdDepar(departamentoSeleccionado, facultad.getDepartamentos());
            tabla_horario.setModel(ManejadorAulas.getHorarioEnAula_Depar(facultad.getAulas(), aulaSeleccionada, modelo, Integer.parseInt(departamentoSeleccionado)));
        }  
    }//GEN-LAST:event_btn_filtrarActionPerformed

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
        if(jlist_aulas.getSelectedItem()!=null){
            llenarListaDepartamentos();
            jlist_departamentos.setEnabled(true);
            btn_filtrar.setEnabled(true);
        }else{
            jlist_departamentos.setSelectedItem(null);
            jlist_carreras.setSelectedItem(null);
            jlist_departamentos.setEnabled(false);
            jlist_carreras.setEnabled(false);
            btn_filtrar.setEnabled(false);
        }
    }                                           

    private void jlist_departamentosActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        if(jlist_departamentos.getSelectedItem()!=null){
            llenarListaCarreras(jlist_departamentos.getSelectedItem().toString());
            jlist_carreras.setEnabled(true);
        }else{
            jlist_carreras.setSelectedItem(null);
            jlist_carreras.setEnabled(false);
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
               jlist_aulas.setEnabled(true);
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
    private javax.swing.JButton btn_filtrar;
    private javax.swing.JButton btn_generar;
    private javax.swing.JButton cancel_reserv;
    private javax.swing.JComboBox cmb_aula;
    private javax.swing.JComboBox cmb_dia;
    private javax.swing.JComboBox cmb_hora_init;
    private javax.swing.JButton delReserv;
    private javax.swing.JDialog dialog_new_reserv;
    private javax.swing.JDialog dialog_reserv;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox jlist_aulas;
    private javax.swing.JComboBox jlist_carreras;
    private javax.swing.JComboBox jlist_departamentos;
    private javax.swing.JMenuItem jm_abrir;
    private javax.swing.JMenuItem jm_guardar;
    private javax.swing.JMenuItem jm_reserv;
    private javax.swing.JLabel lbl_mensaje;
    private javax.swing.JButton ok_reserv;
    private javax.swing.JSpinner sp_num_horas;
    private javax.swing.JTable tabla_horario;
    private javax.swing.JTable table_reserv;
    // End of variables declaration//GEN-END:variables

}
