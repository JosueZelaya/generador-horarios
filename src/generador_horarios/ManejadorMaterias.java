/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alexander
 */
public abstract class ManejadorMaterias {
    
    
    public static ArrayList<Materia> getTodasMaterias(boolean cicloPar){
        ArrayList<Materia> materias = new ArrayList();
        
        Conexion conexion = new Conexion();
        ResultSet resultadoConsulta;
        try {
            conexion.conectar();
            resultadoConsulta = conexion.consultaCiclos("select m.cod_materia,m.nombre_materia,cm.unidades_valorativas,cm.ciclo,c.id_carrera,c.plan_estudio,cm.id_agrupacion,d.id_depar from materias as m join carreras_materias as cm on m.cod_materia=cm.materias_cod_materia join carreras as c on cm.carreras_id_carrera=c.id_carrera and cm.carreras_plan_estudio=c.plan_estudio join departamentos as d on c.id_depar=d.id_depar WHERE cm.ciclo IN (?,?,?,?,?);",cicloPar);
            while(resultadoConsulta.next()){
                Materia materia = new Materia(resultadoConsulta.getString("cod_materia"),resultadoConsulta.getString("nombre_materia"),resultadoConsulta.getInt("ciclo"),resultadoConsulta.getInt("unidades_valorativas"),resultadoConsulta.getInt("id_depar"),resultadoConsulta.getString("id_carrera"),resultadoConsulta.getInt("plan_estudio"),resultadoConsulta.getInt("id_agrupacion"),true);
                materias.add(materia);
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en getTodasMaterias()\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return materias;
    }
    
    public static String getNombreMateria(String codMateria){
        String nombreMateria="";
        Conexion conexion = new Conexion();        
        ResultSet resultadoConsulta;
        try {
            conexion.conectar();
            resultadoConsulta = conexion.consulta("SELECT nombre_materia FROM materias WHERE cod_materia='"+codMateria+"'");
            while(resultadoConsulta.next()){               
                nombreMateria=resultadoConsulta.getString("nombre_materia");
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return nombreMateria;
    }
    
    public static String obtenerCodMateria(String nombre){
        String codigo = "";
        Conexion conexion = new Conexion();        
        ResultSet resultadoConsulta;
        try {
            conexion.conectar();
            resultadoConsulta = conexion.consulta("SELECT cod_materia FROM materias WHERE nombre_materia='"+nombre+"'");
            while(resultadoConsulta.next()){               
                codigo=resultadoConsulta.getString("cod_materia");
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return codigo;
    }
    
    public static ArrayList<Materia> getMateriasDeCarrera(ArrayList<Materia> materias, String idCarrera){
        ArrayList<Materia> materiasCarrera = new ArrayList();
        for(int i=0; i<materias.size(); i++){
            if(materias.get(i).getCodigoCarrera().equals(idCarrera))
                materiasCarrera.add(materias.get(i));
        }
        return materiasCarrera;
    }
    
    public static ArrayList<Materia> obtenerMateriasDeDepartamento(ArrayList<Materia> materias, int id_depar){
        ArrayList<Materia> materiasDepar = new ArrayList();
        for(int i=0; i<materias.size(); i++){
            if(materias.get(i).getDepartamento() == id_depar)
                materiasDepar.add(materias.get(i));
        }
        return materiasDepar;
    }
    
    /** Devuelve la Materia a la que pertenece el grupo
     * 
     * 
     * @param id_agrup
     * @param todas_mats
     * @return 
     */
    public static ArrayList<Materia> getMateriaDeGrupo(int id_agrup, ArrayList<Materia> todas_mats){
        ArrayList <Materia> materias = new ArrayList();
        for(Materia m : todas_mats){
            if(m.getIdAgrupacion() == id_agrup)
                materias.add(m);
        }
        return materias;
    }
    
    public static DefaultTableModel obtenerHorarioDeMateria(ArrayList<Aula> aulas, String cod_materia, int id_depar, DefaultTableModel table, ArrayList<Materia> todas_mats){
        for(int i=0; i<aulas.size(); i++){
            ArrayList<Dia> dias = aulas.get(i).getDias();
            for(int x=0; x<dias.size(); x++){
                ArrayList<Hora> horas = dias.get(x).getHoras();
                for(int y=0; y<horas.size(); y++){
                    Hora hora = horas.get(y);
                    Grupo grupo = hora.getGrupo();
                    ArrayList<Materia> materias = getMateriaDeGrupo(grupo.getId_Agrup(), todas_mats);
                    for(Materia m : materias){
                        if(m.getDepartamento() == id_depar && m.getCodigo().equals(cod_materia)){
                            String texto = m.getNombre()+" GT: "+grupo.getId_grupo();
                            table.setValueAt(texto, y, x+1);
                            break;
                        }else
                            table.setValueAt("", y, x+1);
                    }
                }
            }
        }
        return table;
    }
}
