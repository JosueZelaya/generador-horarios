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
    private String ciclo;
    private String unidadesValorativas;
    
    public Materia(){
        
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
    public String getCiclo() {
        return ciclo;
    }

    /**
     * @param ciclo the ciclo to set
     */
    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    /**
     * @return the unidadesValorativas
     */
    public String getUnidadesValorativas() {
        return unidadesValorativas;
    }

    /**
     * @param unidadesValorativas the unidadesValorativas to set
     */
    public void setUnidadesValorativas(String unidadesValorativas) {
        this.unidadesValorativas = unidadesValorativas;
    }
    
}
