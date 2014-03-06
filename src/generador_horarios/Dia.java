/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexander
 */
public class Dia {
    
    private String nombre;
    private ArrayList<Hora> horas;
    
    public Dia(){
        
    }
    
    public Dia(String nombre){
        this.nombre = nombre;
        horas = new ArrayList<Hora>();
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
    
}
