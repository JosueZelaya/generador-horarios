/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexander
 */
public class Main {
    
    public static void main(String[] args){
        Conexion conexion = new Conexion();        
        ResultSet resultadoConsulta;
        try {
            conexion.conectar();
            resultadoConsulta = conexion.consulta("SELECT * FROM aulas");
            while(resultadoConsulta.next()){
                System.out.println("Aula: "+resultadoConsulta.getString("cod_aula")+" Capacidad: "+resultadoConsulta.getString("capacidad"));
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }       
        
    }
    
}
