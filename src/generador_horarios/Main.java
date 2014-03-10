/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.util.ArrayList;

/**
 *
 * @author alexander
 */
public class Main {
    
    public static void main(String[] args){
        Semana semana = new Semana();
        ArrayList<Materia> materias;        
        Procesador procesador = new Procesador();
        
        materias = ManejadorMaterias.getTodasMaterias();
        
        for (int i = 0; i < materias.size(); i++) {            
            procesador.procesarMateria(semana, materias.get(i),false);
        }        
        
        //IMPRIMIR LA SEMANA
        imprimir(semana);
    }
    
    public static void imprimir(Semana semana){
        for (int i = 0; i < semana.getDias().size(); i++) {
            Dia dia = semana.getDias().get(i);
            System.out.println("Dia: "+dia.getNombre());
            for (int j = 0; j < dia.getAulas().size(); j++) {
                Aula aula = dia.getAulas().get(j);
                System.out.println("        Aula: "+aula.getNombre()+", Capacidad: "+aula.getCapacidad());                
                for (int k = 0; k < aula.getHoras().size(); k++) {
                    Hora hora = aula.getHoras().get(k);
                    //System.out.println("            Hora: "+hora.getIdHora()+"| "+hora.getInicio()+"| "+hora.getFin()+", Disponible: "+hora.estaDisponible() + ", Materia:"+hora.getMateria().getNombre()+", Ciclo: "+hora.getMateria().getCiclo());
                    System.out.println("            Dia: "+dia.getNombre()+" Aula: "+aula.getNombre()+" Hora: "+hora.getIdHora()+", Disponible: "+hora.estaDisponible() + ", Materia:"+hora.getMateria().getNombre()+", Ciclo: "+hora.getMateria().getCiclo());                    
                }
            }
        }
    }
    
}
