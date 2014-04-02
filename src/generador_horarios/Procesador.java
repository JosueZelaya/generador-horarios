/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;


import static generador_horarios.ManejadorDias.elegirDiaDiferente;
import static generador_horarios.ManejadorHoras.buscarHorasDisponibles;
import static generador_horarios.ManejadorHoras.gethoraDondeExisteMateriaDelMismoNivel;
import static generador_horarios.ManejadorAgrupaciones.getAgrupacion;
import static generador_horarios.ManejadorHoras.buscarHorasParaNivel;
import static generador_horarios.ManejadorAulas.obtenerAulasPorCapacidad;
import java.util.ArrayList;

/**
 *
 * @author alexander
 */
public class Procesador {
    
    Facultad facultad;                  //Información de la facultad para la cual se generará el horario.
    Materia materia;                    //La materia que se desea asignar, esta se divide en grupos y son estos los que se asignan
    ArrayList<Materia> materias;        //Todas las materias que se imparten en la facultad
    ArrayList<Aula> aulas;              //Las aulas en las que se podría asignar, si estuvieran disponibles
    int holguraAula;                    //La holgura que cada aula debe tener al albergar alumnos
    /* Las agrupaciones contienen la información de cuántos grupos contiene una materia,
     * cuántos alumnos son por cada grupo, a qué departamento(o Escuela) pertenece la materia
     * y cuántos grupos de esa materia se han asignado
     */
    ArrayList<Agrupacion> agrupaciones;
    Agrupacion agrupacion;
    Grupo grupo; //Grupo a asignar
    
    
    int desde;
    int hasta;
    
    public Procesador(){
        holguraAula = 10;
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
    public void establecerTurno(){
        if(materia.getCiclo()<=6)
            hasta = 10;
        else
            desde = 10; 
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
         System.out.println("Grupo asignado; "+grupo.getCod_materia()+" GT "+grupo.getId_grupo());
     }
    
     /*
      * Verdadero si el aula cumple con una holgura de 10 alumnos en su capacidad
      */
     public boolean aulaCumpleCriterioDeCapacidad(int capacidadAula){
        return capacidadAula >= agrupacion.getNum_alumnos()+holguraAula;
     }
    
    /** Intenta asignar una aula para la materia de acuerdo a su capacidad y al número de alumnos de esa materia
     * Cada materia se divide en uno o más grupos, dependiendo de la cantidad de alumnos que cursan la materia.
     * 
     * @throws Exception = Si no encuentra aulas para asignar la materia
     */
    public void asignarAulaPorCapacidad() throws Exception{                                
        int numAulas = aulas.size();                        //Se obtiene la cantidad de aulas con las que cuenta la facultad      
        boolean sePudoAsignar=false;                        //Informa si el grupo pudo ser asignado
        for (int i = 0; i < numAulas; i++) {                //Se recorren todas las aulas
            Aula aula = aulas.get(i);           
            int capacidad = aula.getCapacidad();
            if(aulaCumpleCriterioDeCapacidad(capacidad)){   //Las aulas deben quedar con una holgura de 10              
                ArrayList<Dia> dias;
                dias = aula.getDias();                      //Los días en los que se puede dar clase en esa aula
                if(asignarDiasConsiderandoChoques(dias)){ // se trata de asignar el grupo en el aula elegida comprobando si existen choques
                   sePudoAsignar=true;
                   break; 
                }
                else if(asignarDiasSinConsiderarChoques(dias)){ // se asignan las horas que no se pudieron asignar por choques, ya no se consideran choques
                    sePudoAsignar = true;
                    break;
                }
            }            
        }
        if(!sePudoAsignar){ //Se asigna el día sábado si no se pudieron asignar las horas del grupo durante la semana       
            for (int i = 0; i < numAulas; i++) {
               Aula aula = aulas.get(i);
               int capacidad = aula.getCapacidad();
               if(aulaCumpleCriterioDeCapacidad(capacidad)){ //Las aulas deben quedar con una holgura de 10               
                   Dia dia = aula.getDia("Sabado");
                   ArrayList<Hora> horas = dia.getHoras();
                   ArrayList<Hora> horasDisponibles = buscarHorasDisponibles(horas,materia.getTotalHorasRequeridas()-grupo.getHorasAsignadas(), desde, hasta, "Sabado", materia, aulas, materias); //elige las primeras horas disponibles que encuentre ese día
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
    
    //Asignar dias considerando choques de horario en ellos
    /**
     * 
     * @param dias = dias en los que se puede dar clases dentro del aula elegida
     * @return true si se puede hacer la asignacion de todas las horas que requiere el grupo
     */
    public boolean asignarDiasConsiderandoChoques(ArrayList<Dia> dias){
       Dia diaElegido;
       ArrayList<Dia> diasUsados = new ArrayList();       
       //Se repite el proceso hasta que todos los grupos de la materia hayan sido asignados
       while(materia.getTotalHorasRequeridas() > grupo.getHorasAsignadas()){
           //Se debe elegir un día diferente para cada clase
            diaElegido = elegirDiaDiferente(dias, diasUsados); //Elegimos un día entre todos que sea diferente de los días que ya hemos elegido
            if(diaElegido != null){
                ArrayList<Hora> horas;       
                horas = diaElegido.getHoras();      //Obtenemos todas las horas en que pueden haber clases ese día                
                asignarHorasConsiderandoChoques(horas, diaElegido.getNombre());
                diasUsados.add(diaElegido);    //Guardamos el día para no elegirno de nuevo para esta materia                                                   
            }else{
                return false;
            }            
       }
       return true;
    }
    
    //Asiganar dias sin considerar choques en ellos
    public boolean asignarDiasSinConsiderarChoques(ArrayList<Dia> dias){
       Dia diaElegido;
       ArrayList<Dia> diasUsados = new ArrayList();       
       //Se debe elegir un día diferente para cada clase
       while(materia.getTotalHorasRequeridas() > grupo.getHorasAsignadas()){
            diaElegido = elegirDiaDiferente(dias, diasUsados); //Elegimos un día entre todos
            if(diaElegido != null){
                ArrayList<Hora> horas;       
                horas = diaElegido.getHoras();      //Obtenemos todas las horas en que pueden haber clases ese día                
                asignarHorasSinConsiderarChoques(horas);
                diasUsados.add(diaElegido);    //Guardamos el día para no elegirno de nuevo para esta materia                                                   
            }else{
                return false;
            }            
       }
       return true;
    }
    
    //Asignar Horas considerando choques
    /**
     * 
     * @param horas = horas del dia en el que se quiere hacer la asignacion
     * @param nombreDia = nombre del dia en el que se quiere hacer la asignacion; se utiliza para compbrobar choques
     */
    public void asignarHorasConsiderandoChoques(ArrayList<Hora> horas, String nombreDia){
        int num_alumnos = agrupacion.getNum_alumnos()+holguraAula;
        ArrayList<Hora> horasDisponibles;
        int numHorasContinuas = calcularHorasContinuasRequeridas(materia, grupo);  //Calculamos el numero de horas continuas para la clase
        Hora horaNivel = gethoraDondeExisteMateriaDelMismoNivel(materia, horas, materias);
        if(horaNivel != null){
            if((horaNivel.getIdHora() == 13 && numHorasContinuas == 3) || (horaNivel.getIdHora() == 14 && numHorasContinuas >= 2) || horaNivel.getIdHora() == 15)
                horasDisponibles = buscarHorasDisponibles(horas, numHorasContinuas, desde, hasta, nombreDia, materia, aulas, materias);
            else
                horasDisponibles = buscarHorasParaNivel(numHorasContinuas, horaNivel.getIdHora(), horaNivel.getIdHora()+numHorasContinuas, nombreDia, materia, obtenerAulasPorCapacidad(aulas,num_alumnos), aulas, materias);
        } else
            horasDisponibles = buscarHorasDisponibles(horas, numHorasContinuas, desde, hasta, nombreDia, materia, aulas, materias); //elige las primeras horas disponibles que encuentre ese día
        
        if(horasDisponibles != null)
            asignar(grupo, horasDisponibles);
    }
    
    //Asignar horas sin considerar choques
    public void asignarHorasSinConsiderarChoques(ArrayList<Hora> horas){
        ArrayList<Hora> horasDisponibles;
        int numHorasContinuas = calcularHorasContinuasRequeridas(materia, grupo);  //Calculamos el numero de horas continuas para la clase
        
        horasDisponibles = buscarHorasDisponibles(horas, numHorasContinuas, desde, hasta);
        
        if(horasDisponibles != null)
            asignar(grupo, horasDisponibles);
    }
    
    
    
    /** Realiza el procesamiento necesario para generar el horario de una materia.
     * 
     * @param materia = La materia que se quiere asignar
     * @throws Exception = Cuando ya no hay aulas para asignarla
     */
    public void procesarMateria(Materia materia) throws Exception{
        if(facultad!=null){            
            this.materia = materia;             //La materia que se debe procesar
            this.agrupacion = getAgrupacion(materia, agrupaciones); //Se busca dentro de todas las agrupaciones, cuál es la que pertenece a la materia que se quiere asignar
            grupo = new Grupo(agrupacion);   //El grupo con la información de la agrupación, este grupo es el que será asignado en un aula
            establecerTurno();                  //Se establece el turno
            asignarAulaPorCapacidad();  //Debe asignar la materia a un aula de la facultdad
        }else{
            throw new Exception("No se encuentra la información de la facultad");
        }            
    }
    
    public void setFacultad(Facultad facultad){
        this.facultad = facultad;
        aulas = facultad.getAulas();        //Se obtienen las aulas de la facultad en las cuales se podría asignar materias
        materias = facultad.getMaterias();  //Se obtienen todas las materias de la facultad
        agrupaciones = facultad.getAgrupaciones();  //Se obtienen todas las agrupaciones de materias que existen
        
    }
    
}
