/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import static generador_horarios.ManejadorMaterias.getTodasMaterias;
import static generador_horarios.Procesador.getNumeroAleatorio;
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
        
        materias = getTodasMaterias();
        
        for (int i = 0; i < materias.size(); i++) {            
            Materia materia = materias.get(i);
            ArrayList<Grupo> grupos;
            grupos = new ArrayList();
            int numGrupos = getNumeroAleatorio(1, 12);
            for (int j = 0; j < numGrupos; j++) {
                int numEstudiantes = getNumeroAleatorio(1, 100);            
                Grupo grupo = new Grupo(j,numEstudiantes);
                grupos.add(grupo);
            } 
            materia.setGrupos(grupos);
            procesador.procesarMateria(semana, materia,false);
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
                    System.out.println("            Dia: "+dia.getNombre()+" Aula: "+aula.getNombre()+" Hora: "+hora.getIdHora()+", Disponible: "+hora.estaDisponible() + ", Materia:"+hora.getMateria().getNombre()+" Grupo: "+hora.getGrupo().getId()+"Ciclo: "+hora.getMateria().getCiclo());                    
                }
            }
        }
    }
    
}
