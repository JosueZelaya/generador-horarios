/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

/**
 *
 * @author dasm
 */
public class Grupo {
    
    private String cod_materia;
    private int id_depar;
    private int id_grupo;
    private int horasAsignadas;
    private boolean incompleto;
    
    public Grupo(){
        incompleto=false;
        this.cod_materia = "";
        this.id_depar = 0;
        this.id_grupo = 0;
        this.horasAsignadas = 0;
    }
    
    public Grupo(String codigo, int id_depar, int id_grupo){
        incompleto=false;
        this.cod_materia = codigo;
        this.id_depar = id_depar;
        this.id_grupo = id_grupo;
        this.horasAsignadas = 0;
    }

    public String getCod_materia() {
        return cod_materia;
    }

    public void setCod_materia(String cod_materia) {
        this.cod_materia = cod_materia;
    }

    public int getId_depar() {
        return id_depar;
    }

    public void setId_depar(int id_depar) {
        this.id_depar = id_depar;
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
    
}
