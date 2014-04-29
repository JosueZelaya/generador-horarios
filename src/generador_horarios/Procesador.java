/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;


import static generador_horarios.ManejadorDias.elegirDiaDiferente;
import static generador_horarios.ManejadorHoras.getUltimasHoraDeNivel;
import static generador_horarios.ManejadorHoras.buscarHoras;
import static generador_horarios.ManejadorAulas.obtenerAulasPorCapacidad;
import static generador_horarios.ManejadorHoras.buscarHorasConChoque;
import static generador_horarios.ManejadorAgrupaciones.obtenerNombrePropietario;
import static generador_horarios.ManejadorAgrupaciones.obtenerIdDepartamento;
import static generador_horarios.ManejadorHoras.buscarHorasUltimoRecurso;
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
    ArrayList<Aula> aulasConCapacidad;
    /* Las agrupaciones contienen la información de cuántos grupos contiene una materia,
     * cuántos alumnos son por cada grupo, a qué departamento(o Escuela) pertenece la materia
     * y cuántos grupos de esa materia se han asignado
     */
    ArrayList<Agrupacion> agrupaciones;
    Agrupacion agrupacion;
    Grupo grupo; //Grupo a asignar
    
    int desde;
    int hasta;
    int limite; //Separacion de hora para turno de primeros años y ultimos años
    
    public Procesador(){
        holguraAula = 10;
        limite = 10;
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
        if(materia.getCiclo()<=6){
            desde = 0;
            hasta = limite;
        }
        else{
            desde = limite;
            hasta = 15;
        }
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
    
    /** Busca la mejor posicion para ubicar la materia segun choques, capacidad de aulas, numero de alumnos
     *
     * 
     * @throws Exception = Si no encuentra aulas para asignar la materia
     */
    public void localizarBloqueOptimo() throws Exception{                                
        boolean sePudoAsignar=false;                        //Informa si el grupo pudo ser asignado
        System.out.println("A tratar con "+materia.getNombre()+" GT: "+grupo.getId_grupo());
        if(asignarDiasConsiderandoChoques(aulasConCapacidad.get(0).getDias())) // se trata de asignar el grupo en el aula elegida comprobando si existen choques
            sePudoAsignar=true;
        else if(agrupacion.getNum_grupos() > 1 && asignarDiasSinConsiderarChoques(aulasConCapacidad.get(0).getDias())) // se asignan las horas que no se pudieron asignar por choques, ya no se consideran choques
            sePudoAsignar = true;
        
        if(!sePudoAsignar){ //Se asigna el día sábado si no se pudieron asignar las horas del grupo durante la semana
            Dia dia = aulasConCapacidad.get(0).getDia("Sabado");
            desde = dia.getHoras().get(0).getIdHora()-1;                       ///Todo el dia disponible
            hasta = dia.getHoras().get(dia.getHoras().size()-1).getIdHora();   ///para las materias que llegen a el
            ArrayList<Hora> horasDisponibles = buscarHoras(materia.getTotalHorasRequeridas()-grupo.getHorasAsignadas(), desde, hasta, "Sabado", materia, aulasConCapacidad, aulas, materias); //elige las primeras horas disponibles que encuentre ese día
            if(horasDisponibles != null)               //Si hay horas disponibles
                asignar(grupo, horasDisponibles);        //Asignamos la materia
            else{
                grupo.setIncompleto(true);
                throw new Exception("¡Ya no hay aulas disponibles para el grupo "+grupo.getId_grupo()+" Materia: "+obtenerNombrePropietario(grupo.getId_Agrup(),materias)+" Departamento: "+obtenerIdDepartamento(grupo.getId_Agrup(),agrupaciones));
            }
        }
    }
    
    /** Asignar dias considerando choques de horario en ellos
     * 
     * @param dias = dias en los que se puede dar clases dentro del aula elegida
     * @return true si se puede hacer la asignacion de todas las horas que requiere el grupo
     */
    public boolean asignarDiasConsiderandoChoques(ArrayList<Dia> dias){
        Dia diaElegido;
        ArrayList<Dia> diasUsados = new ArrayList();
        ArrayList<Dia> diasNoAsignados = new ArrayList();
        //Se repite el proceso hasta que todos los grupos de la materia hayan sido asignados
        while(materia.getTotalHorasRequeridas() > grupo.getHorasAsignadas()){
           //Se debe elegir un día diferente para cada clase
            diaElegido = elegirDiaDiferente(dias, diasUsados); //Elegimos un día entre todos que sea diferente de los días que ya hemos elegido
            if(diaElegido != null){
                System.out.println("Se probara sin choques en dia "+diaElegido.getNombre());
//                ArrayList<Hora> horas = diaElegido.getHoras();      //Obtenemos todas las horas en que pueden haber clases ese día

//                if(horas.size() < hasta && horas.size() < limite) //Si el dia tiene menos horas clase que las que se requieren por el limite
//                    hasta = horas.size();
//                else if(horas.size() < hasta && horas.size() > limite)
//                    hasta = horas.size();

                if(!asignarHorasConsiderandoChoques(diaElegido.getNombre()))
                    diasNoAsignados.add(diaElegido);
                
                diasUsados.add(diaElegido);    //Guardamos el día para no elegirlo de nuevo para esta materia                                                   
            } else
                break;
        }
        if(materia.getTotalHorasRequeridas() > grupo.getHorasAsignadas()){
            diasUsados.clear();
            while(materia.getTotalHorasRequeridas() > grupo.getHorasAsignadas()){
                diaElegido = elegirDiaDiferente(diasNoAsignados, diasUsados);
                if(diaElegido != null){
                    System.out.println("Se probara ultimo recurso en dia: "+diaElegido.getNombre());
//                    ArrayList<Hora> horas = diaElegido.getHoras();      //Obtenemos todas las horas en que pueden haber clases ese día

//                    if(horas.size() < hasta && horas.size() < limite) //Si el dia tiene menos horas clase que las que se requieren por el limite
//                        hasta = horas.size();
//                    else if(horas.size() < hasta && horas.size() > limite)
//                        hasta = horas.size();
                    
                    ultimoRecursoConsiderandoChoques(diaElegido.getNombre());
                    diasUsados.add(diaElegido);
                } else
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
                System.out.println("Se probara con choques en dia "+diaElegido.getNombre()+" para la materia: "+materia.getCodigo());
//                ArrayList<Hora> horas = diaElegido.getHoras();      //Obtenemos todas las horas en que pueden haber clases ese día
                
//                if(horas.size() < hasta && horas.size() < limite) //Si el dia tiene menos horas clase que las que se requieren por el limite
//                    hasta = horas.size();
//                else if(horas.size() < hasta && horas.size() > limite)
//                    hasta = horas.size();
                
                asignarHorasSinConsiderarChoques(diaElegido.getNombre());
                diasUsados.add(diaElegido);    //Guardamos el día para no elegirlo de nuevo para esta materia                                                   
            }else
                return false;
       }
       return true;
    }
    
    /** Asignar Horas considerando choques
     * 
     * @param nombreDia = nombre del dia en el que se quiere hacer la asignacion; se utiliza para compbrobar choques
     * @return true si encuentra un bloque de horas y los asigna al grupo
     */
    public boolean asignarHorasConsiderandoChoques(String nombreDia){
        ArrayList<Hora> horasDisponibles = null;
        int numHorasContinuas = calcularHorasContinuasRequeridas(materia, grupo);  //Calculamos el numero de horas continuas para la clase
        ArrayList horasNivel = getUltimasHoraDeNivel(grupo, materia, materias, agrupaciones, aulasConCapacidad, nombreDia);
        for(Object hora : horasNivel){
            if(((int)hora+numHorasContinuas)<hasta){
                horasDisponibles = buscarHoras(numHorasContinuas, (int)hora+1, (int)hora+1+numHorasContinuas, nombreDia, materia, aulasConCapacidad, aulas, materias);
                if(horasDisponibles != null && !horasDisponibles.isEmpty())
                    break;
            }
        }
        if(horasDisponibles != null && !horasDisponibles.isEmpty()){
            asignar(grupo, horasDisponibles);
            return true;
        }
        return false;
    }
    
    /**Metodo para asginar horas si no se pudo continua a una materia del mismo nivel (se consideran choques)
     * 
     * @param nombreDia nombre del Dia en el que se quiere hacer la asignacion de horas
     */
    public void ultimoRecursoConsiderandoChoques(String nombreDia){
        int numHorasContinuas = calcularHorasContinuasRequeridas(materia, grupo);  //Calculamos el numero de horas continuas para la clase
        ArrayList<Hora> horasDisponibles = buscarHorasUltimoRecurso(numHorasContinuas, desde, hasta, nombreDia, materia, aulasConCapacidad, aulas, materias); //elige las primeras horas disponibles que encuentre ese día
        if(horasDisponibles != null && !horasDisponibles.isEmpty())
            asignar(grupo, horasDisponibles);
    }
    
    //Asignar horas sin considerar choques
    public void asignarHorasSinConsiderarChoques(String nombreDia){
        ArrayList<Hora> horasDisponibles;
        int numHorasContinuas = calcularHorasContinuasRequeridas(materia, grupo);  //Calculamos el numero de horas continuas para la clase
        horasDisponibles = buscarHorasConChoque(numHorasContinuas, desde, hasta, nombreDia, aulasConCapacidad,grupo);
        if(horasDisponibles != null)
            asignar(grupo, horasDisponibles);
    }
    
    /** Realiza el procesamiento necesario para generar el horario de una materia.
     * 
     * @param materia = La materia que se quiere asignar
     * @param id_docente = identificador del docente que impartira el grupo a asignar horario
     * @param agrupacion = agrupacion a la que pertenece la materia a asignar
     * @throws Exception = Si no existe la informacion necesario en el objeto Facultad
     */
    public void procesarMateria(Materia materia, int id_docente, Agrupacion agrupacion) throws Exception{
        if(facultad!=null){
            this.materia = materia;             //La materia que se debe procesar
            this.agrupacion = agrupacion; //Se busca dentro de todas las agrupaciones, cuál es la que pertenece a la materia que se quiere asignar
            grupo = new Grupo(agrupacion);   //El grupo con la información de la agrupación, este grupo es el que será asignado en un aula
            grupo.setId_docente(id_docente); //Se le asigna al grupo a cual docente pertenecera para comprobaciones de choques
            aulasConCapacidad = obtenerAulasPorCapacidad(aulas,agrupacion.getNum_alumnos()+holguraAula);
            establecerTurno();                 //Se establece el turno
            localizarBloqueOptimo();  //Debe asignar la materia a un aula de la facultdad
        }else
            throw new Exception("No se encuentra la información de la facultad");           
    }
    
    public void asignarDatos(Facultad facultad){
        this.facultad = facultad;
        aulas = facultad.getAulas();        //Se obtienen las aulas de la facultad en las cuales se podría asignar materias
        materias = facultad.getMaterias();  //Se obtienen todas las materias de la facultad
        agrupaciones = facultad.agrupaciones;  //Se obtienen todas las agrupaciones de materias que existen
    }
    
}