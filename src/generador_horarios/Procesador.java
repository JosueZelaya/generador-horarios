/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import static generador_horarios.ManejadorHoras.elegirHorasDisponibles;
import static generador_horarios.ManejadorAulas.elegirAula;
import java.util.ArrayList;

/**
 *
 * @author alexander
 */
public class Procesador {
    
     public static int getNumeroAleatorio(int desde,int hasta){
        int aleatorio= (int) Math.floor(Math.random()*(hasta-desde+1)+desde);        
        return aleatorio;
    } 
    
     
    
    public void asignarMateria(Semana semana,Materia materia,Boolean cicloPar){
        int desde=0;
        int hasta=15;
        Dia dia; 
        ArrayList<Hora> horasDisponibles;
        ArrayList<Dia> diasUsados = new ArrayList();
        int numHorasContinuas = 2;
        if(materia.getTotalHorasRequeridas()==3 || materia.getTotalHorasRequeridas()==1){
            numHorasContinuas = materia.getTotalHorasRequeridas();
        }
        Aula aula;
                
        //Si el ciclo es par solo elegimos las materias pares, de lo contrario elegimos las impares
        if((materia.getCiclo() % 2 == 0 && cicloPar) /*Pares*/ || (materia.getCiclo() % 2 != 0 && !cicloPar)/*Impares*/){            
            
            //Las horas se asignan dependiendo de sus unidades valorativas.
            while(materia.getTotalHorasRequeridas() > materia.getHorasAsignadas()){
                //Elegimos el día que sea diferente a los días que ya elegimos para esta materia
                dia = ManejadorDias.elegirDiaDiferente(semana.getDias(), diasUsados);                      
                   
                
                ArrayList<Aula> aulas = dia.getAulas();                 //Obtenemos las aulas de ese día                        
                aula = elegirAula(aulas);                               //Elegimos el aula
                ArrayList<Hora> horas = aula.getHoras();                //Obtenemos las horas de esa aula
                if(materia.getCiclo()<=5){
                    hasta = 8;
                }else{
                    desde = 8;
                }                    
                
                horasDisponibles = elegirHorasDisponibles(horas,numHorasContinuas,desde,hasta);
                if(horasDisponibles != null){ //Si hay horas disponibles
                    for (int j = 0; j < horasDisponibles.size(); j++) {
                        horasDisponibles.get(j).setMateria(materia);
                        horasDisponibles.get(j).setDisponible(false);
                        materia.setHorasAsignadas(materia.getHorasAsignadas()+1);
                    }
                    diasUsados.add(dia);
                    if(materia.getTotalHorasRequeridas()-materia.getHorasAsignadas()==3){
                        numHorasContinuas = 3;
                    }                    
                }
                
            }                       
            
        }        
        
    }
    
}
