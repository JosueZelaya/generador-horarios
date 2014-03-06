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
public class Main {
    
    public static void main(String[] args){
        Semana semana = new Semana();
        for (int i = 0; i < semana.getDias().size(); i++) {
            Dia dia = semana.getDias().get(i);
            System.out.println("Dia: "+dia.getNombre());
            for (int j = 0; j < dia.getHoras().size(); j++) {
                Hora hora = dia.getHoras().get(j);
                System.out.println("    Hora: "+hora.getIdHora()+"| "+hora.getInicio()+"| "+hora.getFin());
                for (int k = 0; k < hora.getAulas().size(); k++) {
                    Aula aula = hora.getAulas().get(k);
                    System.out.println("        Aula: "+aula.getNombre()+", Capacidad: "+aula.getCapacidad()+", Disponible: "+aula.estaDisponible());
                }
            }
        }
    }
    
}
