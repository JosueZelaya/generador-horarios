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
        ArrayList<Aula> aulas = new ArrayList<Aula>();
        aulas = ManejadorAulas.getAulas();
        for (int i = 0; i < aulas.size(); i++) {
            System.out.println(i+ " Aula: "+aulas.get(i).getNombre()+" Capacidad: "+aulas.get(i).getCapacidad());
        }
    }
    
}
