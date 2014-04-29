/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.io.Serializable;

/**
 *
 * @author dasm
 */
public class Grupo implements Serializable{
    
    private int id_agrup;
    private int id_grupo;
    private int id_docente;
    private int horasAsignadas;
    private boolean incompleto;
    
    public Grupo(){
        this.incompleto=false;
        this.id_agrup = 0;
        this.id_grupo = 0;
        this.id_docente = 0;
        this.horasAsignadas = 0;
    }
    
    public Grupo(int codigo, int id_grupo, int id_docente){
        this.incompleto=false;
        this.id_agrup = codigo;
        this.id_grupo = id_grupo;
        this.id_docente = id_docente;
        this.horasAsignadas = 0;
    }

    public Grupo(Agrupacion agrupacion){
        this.incompleto = false;
        this.id_agrup = agrupacion.getId();
        this.id_grupo = agrupacion.getNumGruposAsignados()+1;
        this.id_docente = 0;
        this.horasAsignadas = 0;
    }
    
    public int getId_Agrup() {
        return id_agrup;
    }

    public void setId_Agrup(int id_agrup) {
        this.id_agrup = id_agrup;
    }

    public int getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }
    
    /**
     * @return the horasAsignadas
     */
    public int getHorasAsignadas() {
        return horasAsignadas;
    }

    /**
     * @param horasAsignadas the horasAsignadas to set
     */
    public void setHorasAsignadas(int horasAsignadas) {
        this.horasAsignadas = horasAsignadas;
    }

    /**
     * @return the incompleto
     */
    public boolean estaIncompleto() {
        return incompleto;
    }

    /**
     * @param incompleto the incompleto to set
     */
    public void setIncompleto(boolean incompleto) {
        this.incompleto = incompleto;
    }

    public int getId_agrup() {
        return id_agrup;
    }

    public void setId_agrup(int id_agrup) {
        this.id_agrup = id_agrup;
    }

    public int getId_docente() {
        return id_docente;
    }

    public void setId_docente(int id_docente) {
        this.id_docente = id_docente;
    }
    
}
