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
public class Semana {
    
    private ArrayList<Dia> dias;
    
    public Semana(){
        //Inicializamos el arraylist de los días.
        dias = ManejadorDias.getTodosDias();
        for (int i = 0; i < dias.size(); i++) {
            ArrayList<Aula> aulas = ManejadorAulas.getTodasAulas();            
            for (int j = 0; j < aulas.size(); j++) {
                ArrayList<Hora> horas = ManejadorHoras.getTodasHoras();
                aulas.get(j).setHoras(horas);
            }
            dias.get(i).setAulas(aulas);
        }
    }

    /**
     * @return the dias
     */
    public ArrayList<Dia> getDias() {        
        return dias;
    }

    /**
     * @param dias the dias to set
     */
    public void setDias(ArrayList<Dia> dias) {
        this.dias = dias;
    }
    
    
    
}
