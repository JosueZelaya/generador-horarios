/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

/**
 *
 * @author dasm
 */
class Reservacion {
    
    public final String nombre_dia;
    public final String inicio;
    public final String fin;
    public final String cod_aula;
    
    public Reservacion(String nombre_dia, String inicio, String fin, String cod_aula){
        this.nombre_dia = nombre_dia;
        this.inicio = inicio;
        this.fin = fin;
        this.cod_aula = cod_aula;
    }
    
}
