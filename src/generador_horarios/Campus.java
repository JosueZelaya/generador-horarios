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
public class Campus {
    
    private ArrayList<nAula> aulas;

    public Campus() {
        this.aulas = new ArrayList();
    }

    /**
     * @return the aulas
     */
    public ArrayList<nAula> getAulas() {
        return aulas;
    }

    /**
     * @param aulas the aulas to set
     */
    public void setAulas(ArrayList<nAula> aulas) {
        this.aulas = aulas;
    }
    
    
    
}
