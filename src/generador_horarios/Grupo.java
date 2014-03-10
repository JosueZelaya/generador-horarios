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
public class Grupo {
    
    private int id;
    private int numEstudiantes;
    
    public Grupo(){
        
    }
    
    public Grupo(int id,int numEstudiantes){
        this.id = id;
        this.numEstudiantes = numEstudiantes;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the numEstudiantes
     */
    public int getNumEstudiantes() {
        return numEstudiantes;
    }

    /**
     * @param numEstudiantes the numEstudiantes to set
     */
    public void setNumEstudiantes(int numEstudiantes) {
        this.numEstudiantes = numEstudiantes;
    }
    
}
