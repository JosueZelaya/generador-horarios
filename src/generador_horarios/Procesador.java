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
    
    /*
     * Elige horas disponibles dentro del array y que sean consecutivas
     */
    public Hora[] elegirHorasDisponibles(ArrayList<Hora> horas,int cantidadHoras,int desde, int hasta){
        Hora horasDisponibles[] =new Hora[cantidadHoras];
        
        for (int i = desde; i < hasta; i++) {
            
        }
      
        
        int hora = getNumeroAleatorio(desde, hasta);
        
        
        return horasDisponibles;
    }
    
    public Aula elegirAula(ArrayList<Aula> aulas){
        int desde = 0;
        int hasta = aulas.size()-1;
        int aula = getNumeroAleatorio(desde, hasta);
        return aulas.get(aula);
    }
    
    public void asignarMateria(Semana semana,Materia materia,Boolean cicloPar){
        int desde=0;
        int hasta=0;
        Dia dia; 
        Hora horasDisponibles[];
        Aula aula;
                
        //Si el ciclo es par solo elegimos las materias pares, de lo contrario elegimos las impares
        if((materia.getCiclo() % 2 == 0 && cicloPar) /*Pares*/ || (materia.getCiclo() % 2 != 0 && !cicloPar)/*Impares*/){            
            dia = elegirDia(semana.getDias());                      //Elegimos el día
            ArrayList<Aula> aulas = dia.getAulas();                 //Obtenemos las aulas de ese día                        
            aula = elegirAula(aulas);                               //Elegimos el aula
            ArrayList<Hora> horas = aula.getHoras();                //Obtenemos las horas de esa aula
            if(materia.getCiclo()<=5){
                hasta = 8;
            }else{
                desde = 8;
            }
            horasDisponibles = elegirHorasDisponibles(horas, 2,desde,hasta);
            
            /*if(hora.estaDisponible()){                              //Si el aula está disponible
                hora.setMateria(materia);
                hora.setDisponible(false);
            }else{                                                  //Si el aula no está disponible
                asignarMateria(semana, materia,cicloPar);           //Volvemos a buscar otra aula
            }*/
        }           
        
        
    }
    
}
