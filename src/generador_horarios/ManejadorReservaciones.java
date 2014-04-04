/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author dasm
 */
public class ManejadorReservaciones {
    
    public static void nuevaReservacion(String dia, int id_hora, String cod_aula){
        Conexion con;
        try{
            con = new Conexion();
            con.conectar();
            con.insertar("INSERT INTO reservaciones values('"+dia+"',"+String.valueOf(id_hora)+",'"+cod_aula+"')");
            con.cierraConexion();
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error en nuevaReservacion()\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
