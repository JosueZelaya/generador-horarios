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
public class Hora {
    
    private ArrayList<Aula> aulas;
    
    public Hora(){
        
    }

    /**
     * @return the aulas
     */
    public ArrayList<Aula> getAulas() {
        return aulas;
    }

    /**
     * @param aulas the aulas to set
     */
    public void setAulas(ArrayList<Aula> aulas) {
        this.aulas = aulas;
    }
    
}
