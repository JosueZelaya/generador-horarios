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
            resultadoConsulta = conexion.consultaPrep("SELECT b.cod_materia,b.nombre_materia,b.unidades_valorativas,a.ciclo,a.agrupacion_id_depar FROM carreras_materias AS a INNER JOIN materias AS b ON (b.cod_materia=a.materias_cod_materia) WHERE a.ciclo IN (?,?,?,?,?);",cicloPar);
            while(resultadoConsulta.next()){
                Materia materia = new Materia();
                materia.setCodigo(resultadoConsulta.getString("cod_materia"));
                materia.setNombre(resultadoConsulta.getString("nombre_materia"));
                materia.setCiclo(resultadoConsulta.getInt("ciclo"));
                materia.setUnidadesValorativas(resultadoConsulta.getInt("unidades_valorativas"));
                materia.setDepartamento(resultadoConsulta.getInt("agrupacion_id_depar"));
                materias.add(materia);
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return materias;
    }
    
    
    
}
