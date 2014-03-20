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
public class Agrupacion{
    
    private String propietario;
    private int departamento;
    private int num_grupos;
    private int num_alumnos;
    private int numGruposAsignados;
    
    public Agrupacion(String propietario,int departamento,int num_grupos, int num_alumnos){
        this.propietario = propietario;
        this.departamento = departamento;
        this.num_grupos = num_grupos;
        this.num_alumnos = num_alumnos;
        this.numGruposAsignados = 0;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public int getDepartamento() {
        return departamento;
    }

    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public int getNum_grupos() {
        return num_grupos;
    }

    public void setNum_grupos(int num_grupos) {
        this.num_grupos = num_grupos;
    }

    public int getNum_alumnos() {
        return num_alumnos;
    }

    public void setNum_alumnos(int num_alumnos) {
        this.num_alumnos = num_alumnos;
    }

    public int getNumGruposAsignados() {
        return numGruposAsignados;
    }

    public void setNumGruposAsignados(int numGruposAsignados) {
        this.numGruposAsignados = numGruposAsignados;
    }
    
    
    
}
