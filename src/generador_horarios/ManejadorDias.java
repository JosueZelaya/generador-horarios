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
public abstract class ManejadorDias {
    
    public static ArrayList<Dia> getTodosDias(){
        ArrayList<Dia> dias = new ArrayList();
        Conexion conexion = new Conexion();        
        ResultSet resultadoConsulta;
        try {
            conexion.conectar();
            resultadoConsulta = conexion.consulta("SELECT * FROM dias");
            while(resultadoConsulta.next()){
                Dia dia = new Dia(resultadoConsulta.getString("nombre_dia"));
                dias.add(dia);
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dias;
    }
    
}
