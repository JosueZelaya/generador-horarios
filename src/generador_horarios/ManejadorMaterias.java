/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
            resultadoConsulta = conexion.consultaCiclos("SELECT b.cod_materia,b.nombre_materia,b.unidades_valorativas,a.ciclo,a.agrupacion_id_depar,a.carreras_id_carrera,a.carreras_plan_estudio FROM carreras_materias AS a INNER JOIN materias AS b ON (b.cod_materia=a.materias_cod_materia) WHERE a.ciclo IN (?,?,?,?,?);",cicloPar);
            while(resultadoConsulta.next()){
                Materia materia = new Materia();
                materia.setCodigo(resultadoConsulta.getString("cod_materia"));
                materia.setNombre(resultadoConsulta.getString("nombre_materia"));
                materia.setCiclo(resultadoConsulta.getInt("ciclo"));
                materia.setUnidadesValorativas(resultadoConsulta.getInt("unidades_valorativas"));
                materia.setDepartamento(resultadoConsulta.getInt("agrupacion_id_depar"));
                materia.setCodigoCarrera(resultadoConsulta.getString("carreras_id_carrera"));
                materia.setPlanEstudio(resultadoConsulta.getInt("carreras_plan_estudio"));
                materias.add(materia);
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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
    
    public static ArrayList<Materia> getMateriasDeCarrera(ArrayList<Materia> materias, String idCarrera){
        ArrayList<Materia> materiasCarrera = new ArrayList();
        for(int i=0; i<materias.size(); i++){
            if(materias.get(i).getCodigoCarrera().equals(idCarrera))
                materiasCarrera.add(materias.get(i));
        }
        return materiasCarrera;
    }
    
    /** Devuelve todas las materias para las que es común el grupo
     * 
     * 
     * @param cod_materia
     * @param id_depar
     * @param todas_mats
     * @return 
     */
    public static ArrayList<Materia> getMateriasDeGrupo(String cod_materia, int id_depar, ArrayList<Materia> todas_mats){
        ArrayList <Materia> materias = new ArrayList();
        for(Materia m : todas_mats){
            if(m.getCodigo().equals(cod_materia) && m.getDepartamento() == id_depar)
                materias.add(m);
        }
        return materias;
    }
    
}
