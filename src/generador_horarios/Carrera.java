/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.util.ArrayList;

/**
 *
 * @author dasm
 */
public class Carrera {
    
    private String codigo;
    private String nombre;
    private ArrayList<Materia> materias;
    
    public Carrera(){
        this.codigo = "";
        this.nombre = "";
        this.materias = null;
    }
    
    public Carrera(String codigo, String nombre){
        this.codigo = codigo;
        this.nombre = nombre;
        this.materias = null;
    }
    
    public Carrera(String codigo, String nombre, ArrayList<Materia> materias){
        this.codigo = codigo;
        this.nombre = nombre;
        this.materias = materias;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<Materia> materias) {
        this.materias = materias;
    }
    
}
