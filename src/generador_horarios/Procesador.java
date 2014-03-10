/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import static generador_horarios.ManejadorAulas.elegirAula;
import static generador_horarios.ManejadorDias.elegirDiaDiferente;
import static generador_horarios.ManejadorHoras.elegirHorasDisponibles;
import java.util.ArrayList;

/**
 *
 * @author alexander
 */
public class Procesador {
    
    int desde;
    int hasta;
    
    public Procesador(){
        desde=0;
        hasta=15;
    }
    
    public static int getNumeroAleatorio(int desde,int hasta){
        int aleatorio= (int) Math.floor(Math.random()*(hasta-desde+1)+desde);        
        return aleatorio;
    }
    
    public boolean materiaEsDeEsteCiclo(Materia materia,boolean esCicloPar){
         //Si el ciclo es par solo elegimos las materias pares, de lo contrario elegimos las impares
         return (materia.getCiclo() % 2 == 0 && esCicloPar) /*Pares*/ || (materia.getCiclo() % 2 != 0 && !esCicloPar)/*Impares*/;         
     }
    
    public int calcularHorasContinuasRequeridas(Materia materia){
        int numHorasContinuas;
        if(materia.getTotalHorasRequeridas()==3 || materia.getTotalHorasRequeridas()==1){
            numHorasContinuas = materia.getTotalHorasRequeridas();
        }else if(materia.getTotalHorasRequeridas()-materia.getHorasAsignadas()==3){
            numHorasContinuas = 3;
        }else{
             numHorasContinuas = 2;
        }
         return numHorasContinuas;
     }
    
    public void establecerTurno(Materia materia){
        if(materia.getCiclo()<=5){
            hasta = 8;
        }else{
            desde = 8;
        } 
    }
    
    public void asignar(Materia materia,ArrayList<Hora> horasDisponibles){
         for (int j = 0; j < horasDisponibles.size(); j++) {
            horasDisponibles.get(j).setMateria(materia);
            horasDisponibles.get(j).setDisponible(false);
            materia.setHorasAsignadas(materia.getHorasAsignadas()+1);
        }
     }
    
    public void procesarMateria(Campus campus,Materia materia,boolean esCicloPar){
       ArrayList<Aula> aulas;
       ArrayList<Dia> dias;
       ArrayList<Dia> diasUsados = new ArrayList(); 
       ArrayList<Hora> horas;
       ArrayList<Hora> horasDisponibles;
       Aula aulaElegida;
       Dia diaElegido;
       int numHorasContinuas;
       
               
       if(materiaEsDeEsteCiclo(materia, esCicloPar)){//Verificamos que la materia corresponda a este ciclo
           //Las horas se asignan dependiendo de sus unidades valorativas.
            while(materia.getTotalHorasRequeridas() > materia.getHorasAsignadas()){
                numHorasContinuas = calcularHorasContinuasRequeridas(materia);  //Calculamos el numero de horas continuas para la clase
                establecerTurno(materia);                                       //Se establece el turno, si es de la mañana o la tarde
                aulas = campus.getAulas();                                      //Obtenemos todas las aulas
                aulaElegida = elegirAula(aulas);                                //Elegimos una aula entre todas
                dias = aulaElegida.getDias();                                   //Obtenemos todos los días que está disponible esa aula
                diaElegido = elegirDiaDiferente(dias, diasUsados);              //Elegimos un día entre todos
                horas = diaElegido.getHoras();                                  //Obtenemos todas las horas que pueden haber clases ese día                
                horasDisponibles = elegirHorasDisponibles(horas,numHorasContinuas,desde,hasta); //Elegimos las horas continuas que se encuentren disponibles en ese turno
                if(horasDisponibles != null){                                   //Si hay horas disponibles
                    asignar(materia, horasDisponibles);                         //Asignamos la materia
                    diasUsados.add(diaElegido);                                 //Guardamos el día para no elegirno de nuevo para esta materia                           
                }
                    
            }        
        }
       
       
    }
    
    
}
