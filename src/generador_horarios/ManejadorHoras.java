/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import static generador_horarios.Procesador.getNumeroAleatorio;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author alexander
 */
public abstract class ManejadorHoras {
    
    public static ArrayList<Hora> getTodasHoras(){
        ArrayList<Hora> horas = new ArrayList();
        Conexion conexion = new Conexion();        
        ResultSet resultadoConsulta;
        try {
            conexion.conectar();
            resultadoConsulta = conexion.consulta("SELECT * FROM horas ORDER BY id_hora");
            while(resultadoConsulta.next()){
                Hora hora = new Hora(resultadoConsulta.getInt("id_hora"));
                hora.setInicio(resultadoConsulta.getString("inicio"));
                hora.setFin(resultadoConsulta.getString("fin"));
                horas.add(hora);
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return horas;
    }
    
    /**
     *
     * @param horas = las horas de las que dispongo para elegir
     * @param desde = el límite inferior para elegir
     * @param hasta = el límite superior para elegir
     * @return      = La hora elegida
     */
    public static Hora elegirHora(ArrayList<Hora> horas,int desde,int hasta){
        int hora = getNumeroAleatorio(desde, hasta);
        return horas.get(hora);
    }
    
    /**Devuelve las primeras horas disponibles consecutivas que encuentre
     * 
     * @param horas = horas del dia en que va a tratar de asignar
     * @param cantidadHoras = cuantas horas a asignar
     * @param desde = desde cual hora tratar de asignar
     * @param hasta = hasta cual hora tratar de asginar
     * @param nombre_dia = nombre del dia en el que se quiere asignar, se usa para comprobar choques
     * @param materia = objeto materia que se esta tratando de asignar
     * @param aulas = todas las aulas de campus, se usan para verificar choques
     * @param m = array de todas las materias que posee campus
     * @return las horas disponibles sin choque en las que se puede asignar el grupo; null si no hay ninguna
     */
    public static ArrayList<Hora> buscarHorasDisponibles(ArrayList<Hora> horas,int cantidadHoras,int desde,int hasta,String nombre_dia,Materia materia,ArrayList<Aula> aulas, ArrayList<Materia> m){
        ArrayList<Hora> horasDisponibles = new ArrayList();
        for (int i = desde; i < hasta; i++) {                   //Verifico si hay horas continuas disponibles en el intervalo requerido
            Boolean hayBloquesDisponibles=false;
            
            //Si hay una hora disponible debe verificarse que su indice no sea tal que se desborde el array al preguntar por las siguientes
            if(horas.get(i).estaDisponible() && horas.get(i).getIdHora()<=(hasta+1)-cantidadHoras){            
                hayBloquesDisponibles = true;
                for (int j = i+1; j < i+cantidadHoras; j++) {
                    Hora hora = horas.get(j);
                    if(hora.getIdHora()==8){
                        hayBloquesDisponibles=false;
                    }
                    if(!hora.estaDisponible()){                            
                        hayBloquesDisponibles=false;
                    }
                }
            }
            //Si hay horas consecutivas disponibles las agrego al array
            if(hayBloquesDisponibles){
                if(!chocaMateria(nombre_dia,horas.get(i).getIdHora(),aulas,materia,cantidadHoras,m)){
                    for (int j = i; j < i+cantidadHoras; j++) {
                        horasDisponibles.add(horas.get(j));
                    }
                }
                return horasDisponibles;
            }
        }
        return null;
    }
    
    /**
     * 
     * @param horas = horas del dia en que va a tratar de asignar
     * @param cantidadHoras = cuantas horas a asignar
     * @param desde = desde cual hora tratar de asignar
     * @param hasta = hasta cual hora tratar de asginar
     * @return horas disponibles en las que se puede asignar el grupo aunque hayan choques
     */
    public static ArrayList<Hora> buscarHorasDisponibles(ArrayList<Hora> horas,int cantidadHoras,int desde,int hasta){        
        ArrayList<Hora> horasDisponibles = new ArrayList();
        for (int i = desde; i < hasta; i++) {                   //Verifico si hay horas continuas disponibles en el intervalo requerido
            Boolean hayBloquesDisponibles=false;
            
            //Si hay una hora disponible debe verificarse que su indice no sea tal que se desborde el array al preguntar por las siguientes
            if(horas.get(i).estaDisponible() && horas.get(i).getIdHora()<=(hasta+1)-cantidadHoras){            
                hayBloquesDisponibles = true;
                for (int j = i+1; j < i+cantidadHoras; j++) {
                    Hora hora = horas.get(j);
                    if(hora.getIdHora()==8){
                        hayBloquesDisponibles=false;
                    }
                    if(!hora.estaDisponible()){                            
                        hayBloquesDisponibles=false;
                    }
                }
            }
            //Si hay horas consecutivas disponibles las agrego al array
            if(hayBloquesDisponibles){
                for (int j = i; j < i+cantidadHoras; j++) {
                    horasDisponibles.add(horas.get(j));
                }
                if(horasDisponibles.size() == cantidadHoras)
                    return horasDisponibles;
            }
        }
        return null;
    }
    
    /** Metodo para buscar horas en un dia elegido debajo de una materia del mismo nivel
     * 
     * @param cantidadHoras = numero de horas que se quieren asignar
     * @param desde = desde cual hora se quiere hacer la asignacion
     * @param hasta = hasta cual hora tratar de hacer la asignacion
     * @param nombre_dia = nombre del dia en que se quiere hacer la asignacion
     * @param materia = objeto materia de la cual se quiere asignar un grupo
     * @param aulasConCapa = array de aulas que tienen capacidad para asignar al grupo de la materia
     * @param aulas = array de todas las aulas que tiene el campus, se usa para verificar si hay choques
     * @param m = array de todas las materias del campus, se usa para comprobar choques
     * @return horas disponibles en las que se puede asignar el grupo
     */
    public static ArrayList<Hora> buscarHorasParaNivel(int cantidadHoras,int desde,int hasta,String nombre_dia,Materia materia,ArrayList<Aula> aulasConCapa, ArrayList<Aula> aulas, ArrayList<Materia> m){
        ArrayList<Hora> horasDisponibles = null;
        for(int x=0; x<aulasConCapa.size(); x++){
            Dia dia = aulasConCapa.get(x).getDia(nombre_dia);
            horasDisponibles = buscarHorasDisponibles(dia.getHoras(),cantidadHoras,desde,hasta,nombre_dia,materia,aulas, m);
            if(horasDisponibles != null)
                break;
        }
        return horasDisponibles;
    }
    
    /** Meotodo para relizar busquedas de una materia que pertenece al mismo nivel en el dia elegido
     * 
     * @param materia
     * @param horas
     * @param todas_mats
     * @return ultima hora en la que hay una materia del mismo nivel
     */
    public static Hora MateriaDeNivelEnHoras(Materia materia, ArrayList<Hora> horas, ArrayList<Materia> todas_mats){
        Hora horaNivel = null;
        
        for(int x=0; x<horas.size(); x++){
            if(!horas.get(x).estaDisponible() && horas.get(x).getGrupo().getId_depar() == materia.getDepartamento()){
                Grupo grupo = horas.get(x).getGrupo();
                ArrayList<Materia> materias = ManejadorMaterias.getMateriaDeGrupo(grupo.getCod_materia(), grupo.getId_depar(), todas_mats);
                for(int j=0; j<materias.size(); j++){
                    if(materias.get(j).getCodigoCarrera().equals(materia.getCodigoCarrera()) && materias.get(j).getCiclo() == materia.getCiclo()){
                        horaNivel = horas.get(x); //Devolver la ultima hora con materia del nivel en el supuesto que no hay horas vacias
                        break;
                    }
                }
            }
        }
        
        return horaNivel;
    }
    
    /** Metodo para comprobar si existe choques de materia de mismo nivel en un dia determinado y hora determinada
     * 
     * @param nombre_dia
     * @param id_hora
     * @param aulas
     * @param materia
     * @param num_horas
     * @param todas_mats
     * @return true si hay choque, false si no hay choque
     */
    public static boolean chocaMateria(String nombre_dia, int id_hora, ArrayList<Aula> aulas, Materia materia, int num_horas, ArrayList<Materia> todas_mats){
        boolean chocan = false;
        
        for(int i=0; i<aulas.size(); i++){
            Dia dia = aulas.get(i).getDia(nombre_dia);
            for(int h=id_hora; h<id_hora+num_horas; h++){
                Hora hora = dia.getHoras().get(h-1);
                if(!hora.estaDisponible()){
                    Grupo grupo = hora.getGrupo();
                    ArrayList<Materia> materias = ManejadorMaterias.getMateriaDeGrupo(grupo.getCod_materia(), grupo.getId_depar(), todas_mats);
                    for(int j=0; j<materias.size(); j++){
                        if(materias.get(j).getCodigoCarrera().equals(materia.getCodigoCarrera()) && materias.get(j).getCiclo() == materia.getCiclo()){
                            chocan = true;
                            System.out.println("Esta materia: "+materia.getCodigo()+" choca con: "+grupo.getCod_materia()+" GT "+grupo.getId_grupo()+" en hora: "+h+" del dia "+nombre_dia);
                            break;
                        }
                    }
                    if(chocan)
                        break;
                }
            }
            if(chocan)
                break;
        }
        
        return chocan;
    }
    
    /** Metodo para generar nuevas horas clase
     * 
     * @param initManana = hora de inicio del dia clase
     * @param initTarde = hora de final dia clase
     * @return horas generadas en los limites recibidos
     */
    public static ArrayList<Hora> generarHoras(Time initManana, Time initTarde){
        int id=1;
        Time horaInicial=initManana;
        Time horaFinal=new Time(0);
        long duracionHora = 50*60*1000;
        ArrayList<Hora> horas = new ArrayList();
        try{
            if(initManana.getTime()*50*60*1000*7 > initTarde.getTime()){ //Si las 7 horas de 50 minutos no han terminado al comenzar jornada despertina
                throw new Exception("Horas sobrelapan. Cambie hora");
            }
            while(id <= 15){ //7 horas en la mañana, 8 horas en la tarde
                horaFinal.setTime(horaInicial.getTime() + duracionHora);
                horas.add(new Hora(id,horaInicial.toString(),horaFinal.toString()));
                horaInicial.setTime(horaFinal.getTime());
                id++;
                if(id == 8){
                    horaInicial.setTime(initTarde.getTime()); //inicio de jornada despertina
                    horaFinal.setTime(horaInicial.getTime()); //final apunta a inicio
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        return horas;
    }
    
    /** Metodo para actulizar las horas creadas con generarHoras() en la base de datos
     * 
     * @param horas = horas generadas con generarHoras()
     */
    public static void actualizarHoras(ArrayList<Hora> horas){
        Conexion con = new Conexion();
        try{
            con.conectar();
            for(Hora h : horas){
                con.actualizarHora("UPDATE horas_test SET inicio=?,final=? WHERE id_hora=?;", h.getIdHora(), h.getInicio(), h.getFin());
            }
            con.cierraConexion();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
}
