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
    
    private ArrayList<Aula> aulas;
    private ArrayList<Agrupacion> agrupaciones;

    public Campus(ArrayList<Agrupacion> agrupaciones) {
        this.aulas = new ArrayList();        
        aulas = ManejadorAulas.getTodasAulas();
        for (int i = 0; i < aulas.size(); i++) {
            ArrayList<Dia> dias = ManejadorDias.getTodosDias();
            for(int j=0; j < dias.size(); j++){
                ArrayList<Hora> horas = ManejadorHoras.getTodasHoras();
                dias.get(j).setHoras(horas);
            }
            aulas.get(i).setDias(dias);
        }
        
        this.agrupaciones = agrupaciones;
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
    
    public ArrayList<Agrupacion> getAgrupaciones() {
        return agrupaciones;
    }

    public void setAgrupaciones(ArrayList<Agrupacion> agrupaciones) {
        this.agrupaciones = agrupaciones;
    }
    
}
