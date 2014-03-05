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
public class Semana {
    
    private ArrayList<Dia> dias;
    
    public Semana(){
        
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
