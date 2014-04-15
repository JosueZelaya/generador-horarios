/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.io.Serializable;

/**
 *
 * @author alexander
 */
public class Materia implements Serializable{

    private String codigo;
    private String nombre;
    private int ciclo;
    private int unidadesValorativas;
    private int departamento;
    private String codigoCarrera;
    private int planEstudio;
    private final int idAgrupacion;
    private boolean incompleta;

    public Materia(String codigo, String nombre, int ciclo, int unidadesValorativas, int id_depar, String codigoCarrera, int planEstudio, int idAgrupacion, boolean incompleta) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.ciclo = ciclo;
        this.unidadesValorativas = unidadesValorativas;
        this.departamento = id_depar;
        this.codigoCarrera = codigoCarrera;
        this.planEstudio = planEstudio;
        this.idAgrupacion = idAgrupacion;
        this.incompleta = incompleta;
    }
    
    public int getIdAgrupacion() {
        return idAgrupacion;
    }
    
    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the ciclo
     */
    public int getCiclo() {
        return ciclo;
    }

    /**
     * @param ciclo the ciclo to set
     */
    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    /**
     * @return the unidadesValorativas
     */
    public int getUnidadesValorativas() {
        return unidadesValorativas;
    }

    /**
     * @param unidadesValorativas the unidadesValorativas to set
     */
    public void setUnidadesValorativas(int unidadesValorativas) {
        this.unidadesValorativas = unidadesValorativas;
    }
    
    /**
     * @return the departamento
     */
    public int getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(int departamento) {
        this.departamento = departamento;
    }
    
    public int getTotalHorasRequeridas(){
        int total = (int) Math.rint((this.unidadesValorativas*20)/16);
        return total;
    }

    /**
     * @return the incompleta
     */
    public boolean quedoIncompleta() {
        return incompleta;
    }

    /**
     * @param incompleta the incompleta to set
     */
    public void setIncompleta(boolean incompleta) {
        this.incompleta = incompleta;
    }
    
    public String getCodigoCarrera() {
        return codigoCarrera;
    }

    public void setCodigoCarrera(String codigoCarrera) {
        this.codigoCarrera = codigoCarrera;
    }

    public int getPlanEstudio() {
        return planEstudio;
    }

    public void setPlanEstudio(int planEstudio) {
        this.planEstudio = planEstudio;
    }
}
