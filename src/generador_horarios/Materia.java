/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generador_horarios;

/**
 *
 * @author alexander
 */
public class Materia {

    private String codigo;
    private String nombre;
    private int ciclo;
    private int unidadesValorativas;
    private String departamento;
    private int horasAsignadas;
    
    public Materia() {
        nombre = "";
    }
    
    public Materia(String nombre){
        this.nombre = nombre;
        ciclo = 0;
        unidadesValorativas=0;
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
    
    //Calcula el total de horas que debe tener esta materia en base a sus unidades valorativas...
    public int getTotalHorasRequeridas(){
        switch(unidadesValorativas){
            case 0:
                return 0;                
            case 16:
                return 5;
            default:
                return 5;
        }
    }

}
