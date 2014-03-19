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
            resultadoConsulta = conexion.consultaPrep("SELECT b.cod_materia,b.nombre_materia,b.unidades_valorativas,a.ciclo,a.num_grupos,a.num_alumnos_grupo FROM carreras_materias AS a INNER JOIN materias AS b ON (b.cod_materia=a.materias_cod_materia) WHERE a.ciclo IN (?,?,?,?,?) ORDER BY a.num_alumnos_grupo DESC;",cicloPar);
            while(resultadoConsulta.next()){
                int numeroGrupos = resultadoConsulta.getInt("num_grupos");
                cont++;
                System.out.println("materias: "+cont);
                for (int i = 1; i <= numeroGrupos; i++) {
                    cont++;
                    System.out.println("materias: "+cont);                    
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
        }
        
        return materias;
    }
    
    
    
}
