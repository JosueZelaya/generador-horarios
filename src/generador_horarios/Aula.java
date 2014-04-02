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
public class Aula implements Serializable{
    
    private String nombre;
    private int capacidad;
    private boolean disponible;
    private ArrayList<Dia> dias;

    public Aula() {
        this.nombre = "";
        this.capacidad = 0;
        this.disponible = true;
        this.dias = new ArrayList();
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
     * @return the capacidad
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
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
    
    /*
     * Devuelve el día que le indiquemos por medio del nombre
     * Si no lo encuentra devuelve null
     */
    public Dia getDia(String nombre_dia){
        for (int i = 0; i < dias.size(); i++) {
            Dia dia = dias.get(i);
            if(dia.getNombre().equals(nombre_dia))
                return dia;
        }
        return null;
    }

    /**
     * @return the dias
     */
    public ArrayList<Dia> getDias() {
        return dias;
    }

    /**
     * @param dias the dias to set
     */
    public void setDias(ArrayList<Dia> dias) {
        this.dias = dias;
    }
    
}
