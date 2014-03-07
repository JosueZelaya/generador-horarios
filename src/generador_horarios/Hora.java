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
public class Hora {
    
    private int idHora;
    private String inicio;
    private String fin;
    private Materia materia;
    private boolean disponible;
    
    public Hora(){
        
    }
    
    public Hora(int id){
        this.idHora = id;
        disponible = true;
        materia = new Materia();
    }

    /**
     * @return the aulas
     */

    /**
     * @return the idHora
     */
    public int getIdHora() {
        return idHora;
    }

    /**
     * @param idHora the idHora to set
     */
    public void setIdHora(int idHora) {
        this.idHora = idHora;
    }

    /**
     * @return the inicio
     */
    public String getInicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the fin
     */
    public String getFin() {
        return fin;
    }

    /**
     * @param fin the fin to set
     */
    public void setFin(String fin) {
        this.fin = fin;
    }

    /**
     * @return the disponible
     */
    public boolean estaDisponible() {
        return disponible;
    }

    /**
     * @param disponible the disponible to set
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * @return the materia
     */
    public Materia getMateria() {
        return materia;
    }

    /**
     * @param materia the materia to set
     */
    public void setMateria(Materia materia) {
        this.materia = materia;
    }
    
}
