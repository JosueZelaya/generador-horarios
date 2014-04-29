/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author alexander
 */
public class Facultad implements Serializable{
    
    private ArrayList<Aula> aulas;
    public final ArrayList<Agrupacion> agrupaciones;
    public final ArrayList<AsignacionDocente> asignaciones_docs;
    public final ArrayList<Departamento> departamentos;
    private ArrayList<Materia> materias;

    public Facultad(ArrayList<Agrupacion> agrupaciones, ArrayList<Departamento> departamentos, ArrayList<AsignacionDocente> asignaciones_docs) {
        this.aulas = new ArrayList();        
        aulas = ManejadorAulas.getTodasAulas();
        for (int i = 0; i < aulas.size(); i++) {
            ArrayList<Dia> dias = ManejadorDias.getDias();
            for(int j=0; j < dias.size(); j++){
                ArrayList<Hora> horas = ManejadorHoras.getHorasDia(dias.get(j).getNombre());
                dias.get(j).setHoras(horas);
            }
            aulas.get(i).setDias(dias);
        }
        
        this.agrupaciones = agrupaciones;
        this.departamentos = departamentos;
        this.asignaciones_docs = asignaciones_docs;
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

    public ArrayList<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<Materia> materias) {
        this.materias = materias;
    }
}
