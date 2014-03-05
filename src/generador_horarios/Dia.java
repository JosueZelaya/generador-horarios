/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.util.ArrayList;

/**
 *
 * @author alexander
 */
public class Dia {
    
    private ArrayList<Hora> horas;
    
    public Dia(){
        
    }

    /**
     * @return the horas
     */
    public ArrayList<Hora> getHoras() {
        return horas;
    }

    /**
     * @param horas the horas to set
     */
    public void setHoras(ArrayList<Hora> horas) {
        this.horas = horas;
    }
    
}
