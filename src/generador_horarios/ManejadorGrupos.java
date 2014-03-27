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
public abstract class ManejadorGrupos {
    
    public static Grupo getGrupo(ArrayList<Aula> aulas,String aulaElegida,String diaElegido,int idHora){
        if(aulaElegida != null){
            for(int i=0; i<aulas.size(); i++){
                Aula aula = aulas.get(i);
                if(aula.getNombre().equals(aulaElegida)){
                    ArrayList<Dia> dias = aula.getDias();
                    for (int j = 0; j < dias.size(); j++) {
                        Dia dia = dias.get(j);                        
                        if(dia.getNombre().equals(diaElegido)){
                            ArrayList<Hora> horas = dia.getHoras();
                            for (int k = 0; k < horas.size(); k++) {
                                Hora hora = horas.get(k);
                                if(hora.getIdHora()==idHora){
                                    return hora.getGrupo();
                                }
                            }
                        }
                    }
                }
            }
        }      
        Grupo grupo = new Grupo();
        
        return grupo;
        
    }
    
}
