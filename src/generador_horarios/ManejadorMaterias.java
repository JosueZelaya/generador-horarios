/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexander
 */
public abstract class ManejadorMaterias {
    
    
    public static ArrayList<Materia> getTodasMaterias(){
        ArrayList<Materia> materias = new ArrayList();
        
        Conexion conexion = new Conexion();        
        ResultSet resultadoConsulta;
        try {
            conexion.conectar();
            //resultadoConsulta = conexion.consulta("SELECT * FROM materias");
            resultadoConsulta = conexion.consulta("SELECT materias.cod_materia,materias.nombre_materia,materias.unidades_valorativas,carreras_materias.ciclo,carreras_materias.num_grupos,carreras_materias.num_alumnos_grupo FROM carreras_materias INNER JOIN materias ON materias.cod_materia=carreras_materias.materias_cod_materia ORDER BY carreras_materias.num_alumnos_grupo DESC;");
            while(resultadoConsulta.next()){
                int numeroGrupos = resultadoConsulta.getInt("num_grupos");
                for (int i = 1; i <= numeroGrupos; i++) {
                    Materia materia = new Materia();
                    materia.setCodigo(resultadoConsulta.getString("cod_materia"));
                    materia.setNombre(resultadoConsulta.getString("nombre_materia"));
                    materia.setCiclo(resultadoConsulta.getInt("ciclo"));
                    materia.setUnidadesValorativas(resultadoConsulta.getInt("unidades_valorativas"));
                    materia.setCantidadAlumnos(resultadoConsulta.getInt("num_alumnos_grupo"));
                    materia.setGrupoID(i);
                    materias.add(materia);                    
                }
                
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return materias;
    }
    
    
    
}
