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
 * @author dasm
 */
public abstract class ManejadorCarreras {
    
    public static ArrayList<Materia> getMateriasDeCarrera(String codCarrera){
        ArrayList<Materia> materias = new ArrayList();
        Conexion con;
        ResultSet resultado;
        try {
            con = new Conexion();
            resultado = con.consultaMateriasDeCarrera("SELECT materias_cod_materia FROM carreras_materias WHERE carreras_id_carrera=?", codCarrera);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return materias;
    }
    
}
