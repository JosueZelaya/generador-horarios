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

/**
 *
 * @author alexander
 */
public abstract class ManejadorDias {
    
    //Devuelve los días de lunes a viernes
    public static ArrayList<Dia> getDias(){
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
        int hasta = dias.size()-2; //Le restamos dos para que no tome encuenta el sábado        
        int dia = getNumeroAleatorio(desde, hasta);                    
        return dias.get(dia);
    }
    
    //Devuelve un día que no aparezca en el array de días ya elegidos
    public static Dia elegirDiaDiferente(ArrayList<Dia> dias,ArrayList<Dia> diasUsados){
        //Si ya se usaron todos los días entonces no seguimos buscando y devolvemos null
        //dias.size()=6 porque incluye al sábado, por eso le restamos 1 para que no tome en cuenta el sábado
        if(dias.size()-1 == diasUsados.size()){
            return null;
        }        
        Dia elegido;
        do {            
            elegido = elegirDia(dias);
        } while (!sonDiferentes(elegido, diasUsados));
        
        return elegido;
    }
    
    public static boolean sonDiferentes(Dia elegido,ArrayList<Dia> dias){        
        for (int i = 0; i < dias.size(); i++) {
            if(esIgual(elegido, dias.get(i)))
                return false;
        }
        return true;
    }
    
    public static boolean esIgual(Dia dia1,Dia dia2){
        return dia1.getNombre().equals(dia2.getNombre());        
    }
    
}
