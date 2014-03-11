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
public class Materia {

    private String codigo;
    private String nombre;
    private int ciclo;
    private int unidadesValorativas;
    private int horasAsignadas;
    private String departamento;
    private int grupo;
    private int capacidad;
    private ArrayList<String> carreras;
    
    public Materia(){
        codigo = "";
        nombre = "";
        ciclo = 0;
        unidadesValorativas=0;
        departamento="";
        carreras = new ArrayList();
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
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * @return the carreras
     */
    public ArrayList<String> getCarreras() {
        return carreras;
    }

    /**
     * @param carreras the carreras to set
     */
    public void setCarreras(ArrayList<String> carreras) {
        this.carreras = carreras;
    }
    
    public int getTotalHorasRequeridas(){
        int total = Math.round((this.unidadesValorativas*20)/16);
        if(total>5)
            total = 6;
        return total;
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
    
}
