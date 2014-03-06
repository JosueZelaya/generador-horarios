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
public class Procesador {
    
     public int getNumeroAleatorio(int desde,int hasta){
        int aleatorio= (int) Math.floor(Math.random()*(hasta-desde+1)+desde);        
        return aleatorio;
    }
    
     public Dia elegirDia(ArrayList<Dia> dias){
        int desde = 0;
        int hasta = dias.size()-1;
        int dia = getNumeroAleatorio(desde, hasta);
        return dias.get(dia);
    }
     
    public Hora elegirHora(ArrayList<Hora> horas,int desde,int hasta){
        //int desde = 0;
        //int hasta = horas.size()-1;
        int hora = getNumeroAleatorio(desde, hasta);
        return horas.get(hora);
    }
    
    public Aula elegirAula(ArrayList<Aula> aulas){
        int desde = 0;
        int hasta = aulas.size()-1;
        int aula = getNumeroAleatorio(desde, hasta);
        return aulas.get(aula);
    }
    
    public void asignarMateria(Semana semana,Materia materia,Boolean cicloPar){
        Dia dia; 
        Hora hora;
        Aula aula;
                
        //Si el ciclo es par solo elegimos las materias pares, de lo contrario elegimos las impares
        if((materia.getCiclo() % 2 == 0 && cicloPar) /*Pares*/ || (materia.getCiclo() % 2 != 0 && !cicloPar)/*Impares*/){            
            dia = elegirDia(semana.getDias());                      //Elegimos el día
            ArrayList<Hora> horas = dia.getHoras();                 //Obtenemos las horas de ese día
            
            if(materia.getCiclo()<=5){                              //Si la materia sale entre el primer y quito ciclo
                hora = elegirHora(horas,0,8);                       //Elegimos una hora entre la mañana y la tarde   
                
            }else{                                                  //Si la materia sale después del quito ciclo
                hora = elegirHora(horas,9,horas.size()-1);          //Elegimos una hora entre la tarde y la noche   
            }            
            aula = elegirAula(hora.getAulas());                     //Elegimos el aula
        
            if(aula.estaDisponible()){                              //Si el aula está disponible
                aula.setMateria(materia);
                aula.setDisponible(false);
            }else{                                                  //Si el aula no está disponible
                asignarMateria(semana, materia,cicloPar);           //Volvemos a buscar otra aula
            }
        }           
        
        
    }
    
}
