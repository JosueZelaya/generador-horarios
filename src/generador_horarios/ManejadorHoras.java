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
public abstract class ManejadorHoras {
    
    public static ArrayList<Hora> getTodasHoras(){
        ArrayList<Hora> horas = new ArrayList();        
        Conexion conexion = new Conexion();        
        ResultSet resultadoConsulta;
        try {
            conexion.conectar();
            resultadoConsulta = conexion.consulta("SELECT * FROM horas");
            while(resultadoConsulta.next()){
                Hora hora = new Hora(resultadoConsulta.getInt("id_hora"));
                hora.setInicio(resultadoConsulta.getString("inicio"));
                hora.setFin(resultadoConsulta.getString("fin"));
                horas.add(hora);
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return horas;
    }
    
    /**
     *
     * @param horas = las horas de las que dispongo para elegir
     * @param desde = el límite inferior para elegir
     * @param hasta = el límite superior para elegir
     * @return      = La hora elegida
     */
    public static Hora elegirHora(ArrayList<Hora> horas,int desde,int hasta){
        int hora = getNumeroAleatorio(desde, hasta);
        return horas.get(hora);
    }
    
    
    /**
     *
     * Elige horas disponibles dentro del array y que sean consecutivas
     * 
     * @param horas = el array de horas de las cuales puede elegir
     * @param cantidadHoras = la cantidad de horas consecutivas que busca elegir
     * @param desde = el límite inferior para elegir
     * @param hasta = el límite superior para elegir
     * @return = si las hay retorna las horas consecutivas que se encuentren disponibles, de lo contrario retorna null
     */
    
    public static ArrayList<Hora> elegirHorasDisponibles(ArrayList<Hora> horas,int cantidadHoras,int desde, int hasta){
        ArrayList<Hora> horasPosibles =new ArrayList();
        ArrayList<Hora> horasDisponibles =new ArrayList();
        Hora hora;        
        
        for (int i = desde; i < hasta; i++) {                   //Verifico si hay horas continuas disponibles en el intervalo requerido
            Boolean hayDisponibles=false;
            
            //Si hay una hora disponible debe verificarse que su indice no sea tal que desborde el array al preguntar por las siguientes
            if(horas.get(i).estaDisponible() && horas.get(i).getIdHora()<=horas.size()-cantidadHoras+1){
                hayDisponibles = true;                
                    for (int j = i; j < i+cantidadHoras; j++) {
                        if(!horas.get(j).estaDisponible()){
                            hayDisponibles=false;
                        }
                    }                                
            }
            //Si hay horas consecutivas disponibles agrego la primera de ellas al array de horas posibles
            if(hayDisponibles){
                horasPosibles.add(horas.get(i));
            }
        }
      
        if(horasPosibles.size()>0){
            //Elijo una hora de las horas posibles que he agregado
            int indice = getNumeroAleatorio(0,horasPosibles.size()-1);
            hora = horasPosibles.get(indice);
            int indice2 = horas.indexOf(hora);
            
            for (int i = indice2; i < indice2+cantidadHoras; i++) {
                horasDisponibles.add(horas.get(i));
            }
            return horasDisponibles;
        }       
        return null;
    }
    
}
