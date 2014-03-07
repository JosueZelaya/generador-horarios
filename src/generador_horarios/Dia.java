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
    private ArrayList<Aula> aulas;
    
    public Dia(){
        
    }
    
    public Dia(String nombre){
        this.nombre = nombre;
        aulas = new ArrayList<>();
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
    
}
