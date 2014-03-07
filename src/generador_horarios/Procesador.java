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
    public ArrayList<Hora> elegirHorasDisponibles(ArrayList<Hora> horas,int cantidadHoras,int desde, int hasta){
        ArrayList<Hora> horasPosibles =new ArrayList();
        ArrayList<Hora> horasDisponibles =new ArrayList();
        Hora hora;        
        
        for (int i = desde; i < hasta; i++) {                   //Verifico si hay horas continuas disponibles en el intervalo requerido
            Boolean hayDisponibles=false;
            /*
             * REVISAR LA SIGUIENTE LÍNEA
             */
            if(horas.get(i).estaDisponible() && horas.get(i).getIdHora()<horas.size()-cantidadHoras+1){ //Si hay una hora verifico...
                hayDisponibles = true;                
                    for (int j = i; j < i+cantidadHoras; j++) {
                        if(!horas.get(j).estaDisponible()){
                            hayDisponibles=false;
                        }
                    }                                
            }
            //Si hay horas consecutivas disponibles agrego la primera de ellas al array de horas posibles
            if(hayDisponibles){
                horasPosibles.add(horas.get(i));
            }
        }
      
        if(horasPosibles.size()>0){
            //Elijo una hora de las horas posibles que he agregado
            int indice = getNumeroAleatorio(0,horasPosibles.size()-1);
            hora = horasPosibles.get(indice);
            int indice2 = horas.indexOf(hora);
            
            for (int i = indice2; i < indice2+cantidadHoras; i++) {
                horasDisponibles.add(horas.get(i));
            }
            return horasDisponibles;
        }       
        return null;
    }
    
    public Aula elegirAula(ArrayList<Aula> aulas){
        int desde = 0;
        int hasta = aulas.size()-1;
        int aula = getNumeroAleatorio(desde, hasta);
        return aulas.get(aula);
    }
    
    public void asignarMateria(Semana semana,Materia materia,Boolean cicloPar){
        int desde=0;
        int hasta=15;
        Dia dia; 
        ArrayList<Hora> horasDisponibles;
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
            
            horasDisponibles = elegirHorasDisponibles(horas, 3,desde,hasta);
            if(horasDisponibles != null){
                for (int i = 0; i < horasDisponibles.size(); i++) {
                    horasDisponibles.get(i).setMateria(materia);
                    horasDisponibles.get(i).setDisponible(false);
                }
            }
            
        }           
        
        
    }
    
}
