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
        Campus campus = new Campus();
        ArrayList<Materia> materias;        
        Procesador procesador = new Procesador();
        
        materias = ManejadorMaterias.getTodasMaterias();
        
        for (int i = 0; i < materias.size(); i++) {            
            procesador.procesarMateria(campus, materias.get(i),false);
        }        
        
        //IMPRIMIR LA SEMANA
        imprimir(campus);
    }
    
    public static void imprimir(Campus campus){
        ArrayList<Aula> aulas;
        aulas = campus.getAulas();
        ArrayList<Dia> dias;
        ArrayList<Hora> horas;
        
        for (int i = 0; i < aulas.size(); i++) {
            Aula aula = aulas.get(i);
            System.out.println("Aula: "+aula.getNombre());
            dias = aula.getDias();
            for (int j = 0; j < dias.size(); j++) {
                Dia dia = dias.get(j);
                System.out.println("        Nombre: "+dia.getNombre());
                horas = dia.getHoras();
                for (int k = 0; k < horas.size(); k++) {
                    Hora hora = horas.get(k);
                    System.out.println("            Dia: "+dia.getNombre()+" Aula: "+aula.getNombre()+" Hora: "+hora.getIdHora()+", Disponible: "+hora.estaDisponible() + ", Materia:"+hora.getMateria().getNombre()+", Ciclo: "+hora.getMateria().getCiclo());                    
                }
                
            }
        }
    }
    
}
