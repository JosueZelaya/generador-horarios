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
public class Dia implements Serializable{
 
    private String nombre;
    private ArrayList<Hora> horas;    

    public Dia(String nombre) {
        this.nombre = nombre;
        this.horas = new ArrayList();
    }
    
    public Dia() {
        this.nombre = "";
        this.horas = new ArrayList();
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
     * @return the horas
     */
    public ArrayList<Hora> getHoras() {
        return horas;
    }

    /**
     * @param horas the horas to set
     */
    public void setHoras(ArrayList<Hora> horas) {
        this.horas = horas;
    }
    
    
    
    
}
