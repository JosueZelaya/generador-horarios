/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;


import static generador_horarios.ManejadorDias.elegirDiaDiferente;
import static generador_horarios.ManejadorHoras.buscarHorasDisponibles;
import static generador_horarios.ManejadorHoras.MateriaDeNivelEnHoras;
import static generador_horarios.ManejadorAgrupaciones.getAgrupacion;
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
    
    //Calcula el número de horas continuas que se necesitan para impartir las clases
    public int calcularHorasContinuasRequeridas(Materia materia, Grupo grupo){
        int horasRequeridas = materia.getTotalHorasRequeridas(); //Horas requeridas de la materia por semana
        int horasAsignadas = grupo.getHorasAsignadas();        //Horas que ya han sido asignadas a la materia esta semana
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
    public void asignar(Grupo grupo, ArrayList<Hora> horasDisponibles){
         Hora hora;
         for (int j = 0; j < horasDisponibles.size(); j++) {
            hora = horasDisponibles.get(j);
            hora.setGrupo(grupo);
            hora.setDisponible(false);
            grupo.setHorasAsignadas(grupo.getHorasAsignadas()+1);
         }         
     }
    
    public void asignarAulaPorCapacidad(Materia materia,Campus campus) throws Exception{
        ArrayList<Agrupacion> agrupaciones = campus.getAgrupaciones();
        Agrupacion agrupacion = getAgrupacion(materia.getCodigo(),materia.getDepartamento(),agrupaciones);
        
        ArrayList<Aula> aulas = campus.getAulas();
        int cantidadAlumnos = agrupacion.getNum_alumnos();
        int numAulas = campus.getAulas().size();
        Grupo grupo = new Grupo(agrupacion.getPropietario(),agrupacion.getDepartamento(),agrupacion.getNumGruposAsignados()+1);

        boolean sePudoAsignar=false;
        for (int i = 0; i < numAulas; i++) {
            Aula aula = aulas.get(i);
            int capacidad = aula.getCapacidad();
            if(capacidad >= cantidadAlumnos+10){ //Las aulas deben quedar con una holgura de 10               
                ArrayList<Dia> dias;
                dias = aula.getDias();
                if(asignarDias(materia, grupo, dias)){
                    sePudoAsignar=true;
                   break; 
                }                
            }            
        }
        if(!sePudoAsignar){ //Se asigna el día sábado            
            for (int i = 0; i < aulas.size(); i++) {
               Aula aula = aulas.get(i);
               int capacidad = aula.getCapacidad();
               if(capacidad >= cantidadAlumnos+10){ //Las aulas deben quedar con una holgura de 10               
                   Dia dia = aula.getDia("Sabado");
                   ArrayList<Hora> horas = dia.getHoras();
                   ArrayList<Hora> horasDisponibles = buscarHorasDisponibles(horas,materia.getTotalHorasRequeridas()-grupo.getHorasAsignadas(), desde, hasta); //elige las primeras horas disponibles que encuentre ese día
                   if(horasDisponibles != null){                 //Si hay horas disponibles
                        asignar(grupo, horasDisponibles);        //Asignamos la materia            
                        break;
                   }
                   if(i==aulas.size()-1){
                       grupo.setIncompleto(true);
                       throw new Exception("¡Ya no hay aulas disponibles para el grupo "+grupo.getId_grupo()+" Materia: "+grupo.getCod_materia()+" Departamento: "+grupo.getId_depar());
                   }
               }           
            }        
        }
    }
    
    public boolean asignarDias(Materia materia, Grupo grupo, ArrayList<Dia> dias){
       Dia diaElegido;
       ArrayList<Dia> diasUsados = new ArrayList();       
       //Se debe elegir un día diferente para cada clase
       while(materia.getTotalHorasRequeridas() > grupo.getHorasAsignadas()){
            diaElegido = elegirDiaDiferente(dias, diasUsados); //Elegimos un día entre todos
            if(diaElegido != null){
                ArrayList<Hora> horas;       
                horas = diaElegido.getHoras();      //Obtenemos todas las horas en que pueden haber clases ese día                
                asignarHoras(materia, grupo, horas);
                diasUsados.add(diaElegido);    //Guardamos el día para no elegirno de nuevo para esta materia                                                   
            }else{
                return false;
            }            
       }
       return true;
    }
    
    public void asignarHoras(Materia materia, Grupo grupo, ArrayList<Hora> horas){
        //System.out.println("intentando para: "+materia.getCodigo()+" y grupo: "+grupo.getId_grupo());
        ArrayList<Hora> horasDisponibles;
        int numHorasContinuas = calcularHorasContinuasRequeridas(materia, grupo);  //Calculamos el numero de horas continuas para la clase
//        Hora horaNivel = MateriaDeNivelEnHoras(materia, horas);
//        if(horaNivel != null)
//            horasDisponibles = buscarHorasDisponibles(horas, numHorasContinuas, horaNivel.getIdHora(), hasta);
//        else
//            horasDisponibles = buscarHorasDisponibles(horas, numHorasContinuas, desde, hasta); //elige las primeras horas disponibles que encuentre ese día
        horasDisponibles = buscarHorasDisponibles(horas, numHorasContinuas, desde, hasta); //elige las primeras horas disponibles que encuentre ese día
        if(horasDisponibles != null){                                   //Si hay horas disponibles
            asignar(grupo, horasDisponibles);                         //Asignamos la materia            
        }
    }
    
    //Realiza el procesamiento necesario para generar el horario de una materia.
    public void procesarMateria(Campus campus,Materia materia) throws Exception{
            establecerTurno(materia);                         //Se establece el turno
            asignarAulaPorCapacidad(materia, campus);
    }
    
}
