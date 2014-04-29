/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import static generador_horarios.ManejadorAgrupaciones.obtenerIdAgrupacion;
import static generador_horarios.ManejadorAgrupaciones.obtenerNombrePropietario;

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
    
    public static DefaultTableModel obtenerHorarioDeGrupo(ArrayList<Materia> materias, ArrayList<Aula> aulas, String cod_materia, int id_depar, int id_grupo, DefaultTableModel table){
        for(int i=0; i<aulas.size(); i++){
            ArrayList<Dia> dias = aulas.get(i).getDias();
            for(int x=0; x<dias.size(); x++){
                ArrayList<Hora> horas = dias.get(x).getHoras();
                for(int y=0; y<horas.size(); y++){
                    Hora hora = horas.get(y);
                    Grupo grupo = hora.getGrupo();
                    if(obtenerIdAgrupacion(cod_materia,id_depar,materias) == grupo.getId_Agrup() && grupo.getId_grupo() == id_grupo){
                        String texto = obtenerNombrePropietario(grupo.getId_Agrup(),materias)+" GT: "+grupo.getId_grupo();
                        table.setValueAt(texto, y, x+1);
                    }else
                        table.setValueAt("", y, x+1);
                }
            }
        }
        return table;
    }
    
    public static int obtenerNumGruposDocente(int id_docente, int id_agrup){
        int num = 0;
        
        return num;
    }
}
