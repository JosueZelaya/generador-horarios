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
            resultadoConsulta = conexion.consulta("SELECT * FROM materias");
            while(resultadoConsulta.next()){
                Materia materia = new Materia();
                materia.setCodigo(resultadoConsulta.getString("cod_materia"));
                materia.setNombre(resultadoConsulta.getString("nombre_materia"));
                materia.setCiclo(resultadoConsulta.getInt("ciclo"));
                materia.setUnidadesValorativas(resultadoConsulta.getInt("unidades_valorativas"));
                materias.add(materia);
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return materias;
    }
    
}
