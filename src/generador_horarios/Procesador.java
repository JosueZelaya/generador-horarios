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
    
    //Devuelve un número aleatorio entre los límites desde y hasta, los límites no se excluyen.
    public static int getNumeroAleatorio(int desde,int hasta){
        return (int) Math.floor(Math.random()*(hasta-desde+1)+desde);        
    }
    
    //Verifica que la materia corresponda a un ciclo par o impar
    public boolean materiaEsDeEsteCiclo(Materia materia,boolean esCicloPar){
         //Si el ciclo es par solo elegimos las materias pares, de lo contrario elegimos las impares
         return (materia.getCiclo() % 2 == 0 && esCicloPar) /*Pares*/ || (materia.getCiclo() % 2 != 0 && !esCicloPar)/*Impares*/;         
     }
    
    //Calcula el número de horas continuas que se necesitan para impartir las clases
    public int calcularHorasContinuasRequeridas(Materia materia){
        int horasRequeridas = materia.getTotalHorasRequeridas(); //Horas requeridas de la materia por semana
        int horasAsignadas = materia.getHorasAsignadas();        //Horas que ya han sido asignadas a la materia esta semana
        if(horasRequeridas==3 || horasRequeridas==1)
            return horasRequeridas;
        else if(horasRequeridas-horasAsignadas==3)
            return 3;
        else
             return 2;         
     }
    
    /*
     * Establece en qué turno debe impartirse la materia
     * Para las materias de los primeros años (menores al quinto semestre):
     * -Su turno abarca las horas de la mañana y parte de la tarde
     * Para las materias de los últimos años (mayores al quinto semestre):
     * -Su turno abarca los horarios de la tarde y noche
     */
    public void establecerTurno(Materia materia){
        if(materia.getCiclo()<=5)
            hasta = 8;
        else
            desde = 8; 
    }
    
    //Asigna la materia en las horas correspondientes
    public void asignar(Materia materia,ArrayList<Hora> horasDisponibles){
         Hora hora;
         for (int j = 0; j < horasDisponibles.size(); j++) {
            hora = horasDisponibles.get(j);
            hora.setMateria(materia);
            hora.setDisponible(false);
            materia.setHorasAsignadas(materia.getHorasAsignadas()+1);
        }
     }
    
    //Realiza el procesamiento necesario para generar el horario de una materia.
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
                establecerTurno(materia);                                       //Se establece el turno
                aulas = campus.getAulas();                                      //Obtenemos todas las aulas
                aulaElegida = elegirAula(aulas);                                //Elegimos una aula entre todas
                dias = aulaElegida.getDias();                                   //Obtenemos todos los días que está disponible esa aula
                diaElegido = elegirDiaDiferente(dias, diasUsados);              //Elegimos un día entre todos
                horas = diaElegido.getHoras();                                  //Obtenemos todas las horas en que pueden haber clases ese día                
                horasDisponibles = elegirHorasDisponibles(horas,numHorasContinuas,desde,hasta); //Elegimos las horas continuas que se encuentren disponibles en ese turno
                if(horasDisponibles != null){                                   //Si hay horas disponibles
                    asignar(materia, horasDisponibles);                         //Asignamos la materia
                    diasUsados.add(diaElegido);                                 //Guardamos el día para no elegirno de nuevo para esta materia                           
                }else{
                    procesarMateria(campus, materia, esCicloPar);
                }
                    
            }        
        }
       
       
    }
    
    
}
