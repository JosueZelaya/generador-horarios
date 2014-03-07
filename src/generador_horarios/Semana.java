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
        //Inicializamos el arraylist de los días.
        dias = ManejadorDias.getTodosDias();
        for (int i = 0; i < dias.size(); i++) {
            ArrayList<Hora> horas = ManejadorHoras.getTodasHoras();
            for (int j = 0; j < horas.size(); j++) {
                ArrayList<Aula> aulas = ManejadorAulas.getTodasAulas();
                horas.get(j).setAulas(aulas);
            }
            dias.get(i).setHoras(horas);
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
