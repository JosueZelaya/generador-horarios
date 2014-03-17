/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import static generador_horarios.Procesador.getNumeroAleatorio;
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
            System.out.println(ex.getMessage());
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dias;
    }
    
    public static Dia elegirDia(ArrayList<Dia> dias){
        int desde = 0;
        int hasta = dias.size()-1;
        int dia = getNumeroAleatorio(desde, hasta);
        return dias.get(dia);
    }
    
    //Devuelve un día que no aparezca en el array de días ya elegidos
    public static Dia elegirDiaDiferente(ArrayList<Dia> dias,ArrayList<Dia> diasUsados){
        //Si ya se usaron todos los días entonces no seguimos buscando y devolvemos null
        if(dias.size() == diasUsados.size()){
            return null;
        }        
        
        Dia elegido = elegirDia(dias);
        for (int i = 0; i < diasUsados.size(); i++) {
            if (elegido.getNombre().equals(diasUsados.get(i).getNombre())) {                
                elegido = elegirDiaDiferente(dias,diasUsados);
            }            
        }
        return elegido;
    }
    
}
