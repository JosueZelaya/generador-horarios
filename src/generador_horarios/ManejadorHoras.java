/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import static generador_horarios.Procesador.getNumeroAleatorio;
import static generador_horarios.ManejadorAgrupaciones.obtenerIdDepartamento;
import static generador_horarios.ManejadorAgrupaciones.obtenerNombrePropietario;
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
    
    /**Retorna las horas de un día específico
     * 
     * @param dia = nombre del día del que se quiere obtener las horas
     * @return las horas del dia en la base de datos
     */
    public static ArrayList<Hora> getHorasDia(String dia){
        ArrayList<Hora> horas = new ArrayList();
        Conexion conexion = new Conexion();        
        ResultSet resultadoConsulta;
        try{
            conexion.conectar();
            resultadoConsulta = conexion.consulta("select * from horas where id_hora in (select id_hora from dia_horas where nombre_dia='"+dia+"') order by id_hora asc;");
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
     * @return las horas disponibles sin choque en las que se puede asignar el grupoHora; null si no hay ninguna
     */
    public static Object buscarHorasDisponibles(ArrayList<Hora> horas,int cantidadHoras,int desde,int hasta,String nombre_dia,Materia materia,ArrayList<Aula> aulas, ArrayList<Materia> m){
        ArrayList<Hora> horasDisponibles = new ArrayList();
        Object resul = null;
        for (int i = desde; i < hasta; i++) {                   //Verifico si hay horas continuas disponibles en el intervalo requerido
            Boolean hayBloquesDisponibles=false;
            //Si hay una hora disponible debe verificarse que su indice no sea tal que se desborde el array al preguntar por las siguientes
            if(horas.get(i).estaDisponible() && horas.get(i).getIdHora()<=(hasta+1)-cantidadHoras){            
                hayBloquesDisponibles = true;
                for (int j = i+1; j < i+cantidadHoras; j++) {
                    Hora hora = horas.get(j);
                    if(hora.getIdHora()==8){
                        hayBloquesDisponibles=false;
                        break;
                    }
                    if(!hora.estaDisponible()){                            
                        hayBloquesDisponibles=false;
                        break;
                    }
                }
            }
            //Si hay horas consecutivas disponibles las agrego al array
            if(hayBloquesDisponibles){
                if(!chocaMateria(nombre_dia,horas.get(i).getIdHora(),aulas,materia,cantidadHoras,m)){
                    for (int j = i; j < i+cantidadHoras; j++) {
                        horasDisponibles.add(horas.get(j));
                    }
                    System.out.println("ahi va un bloque para asignar");
                    resul = horasDisponibles;
                    return resul;
                } else
                    resul = "Choque";
            }
        }
        return resul;
    }
    
    /**
     * 
     * @param horas = horas del dia en que va a tratar de asignar
     * @param cantidadHoras = cuantas horas a asignar
     * @param desde = desde cual hora tratar de asignar
     * @param hasta = hasta cual hora tratar de asginar
     * @param nombre_dia
     * @param aulasConCapa
     * @param grupo
     * @return horas disponibles en las que se puede asignar el grupoHora aunque hayan choques
     */
    public static ArrayList<Hora> buscarHorasDisponibles(ArrayList<Hora> horas,int cantidadHoras,int desde,int hasta,String nombre_dia,ArrayList<Aula>aulasConCapa,Grupo grupo){        
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
                        break;
                    }
                    if(!hora.estaDisponible()){                            
                        hayBloquesDisponibles=false;
                        break;
                    }
                }
            }
            //Si hay horas consecutivas disponibles las agrego al array
            if(hayBloquesDisponibles){
                if(!chocaGrupo(nombre_dia,horas.get(i).getIdHora(),horas.get(i).getIdHora()+cantidadHoras,aulasConCapa,grupo)){
                    for (int j = i; j < i+cantidadHoras; j++) {
                        horasDisponibles.add(horas.get(j));
                    }
                    return horasDisponibles;
                }
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
     * @param materia = objeto materia de la cual se quiere asignar un grupoHora
     * @param aulasConCapa = array de aulas que tienen capacidad para asignar al grupoHora de la materia
     * @param aulas = array de todas las aulas que tiene el campus, se usa para verificar si hay choques
     * @param m = array de todas las materias del campus, se usa para comprobar choques
     * @return horas disponibles en las que se puede asignar el grupoHora
     */
    public static ArrayList<Hora> buscarHoras(int cantidadHoras,int desde,int hasta,String nombre_dia,Materia materia,ArrayList<Aula> aulasConCapa, ArrayList<Aula> aulas, ArrayList<Materia> m){
        ArrayList<Hora> horasDisponibles = null;
        for(int x=0; x<aulasConCapa.size(); x++){
            System.out.println("A probar en aula "+aulasConCapa.get(x).getNombre());
            Dia dia = aulasConCapa.get(x).getDia(nombre_dia);
            Object resul = buscarHorasDisponibles(dia.getHoras(),cantidadHoras,desde,hasta,nombre_dia,materia,aulas,m);
            if(resul != null && resul.toString().equals("Choque"))
                break;
            else if(resul != null && !((ArrayList<Hora>)resul).isEmpty()){
                horasDisponibles = (ArrayList<Hora>)resul;
                break;
            }
        }
        return horasDisponibles;
    }
    
    public static ArrayList<Hora> buscarHorasUltimoRecurso(int cantidadHoras,int desde,int hasta,String nombre_dia,Materia materia,ArrayList<Aula> aulasConCapa, ArrayList<Aula> aulas, ArrayList<Materia> m){
        ArrayList<Hora> horasDisponibles = null;
        for(int x=0; x<aulasConCapa.size(); x++){
            System.out.println("A probar en aula "+aulasConCapa.get(x).getNombre()+" Desde: "+desde+" Hasta: "+hasta);
            Dia dia = aulasConCapa.get(x).getDia(nombre_dia);
            Object resul = buscarHorasDisponibles(dia.getHoras(),cantidadHoras,desde,hasta,nombre_dia,materia,aulas,m);
            if(resul != null && !resul.toString().equals("Choque") && !((ArrayList<Hora>)resul).isEmpty()){
                horasDisponibles = (ArrayList<Hora>)resul;
                break;
            }
        }
        return horasDisponibles;
    }
    
    public static ArrayList<Hora> buscarHorasConChoque(int cantidadHoras,int desde,int hasta,String nombre_dia,ArrayList<Aula> aulasConCapa,Grupo grupo){
        ArrayList<Hora> horasDisponibles = null;
        for(int x=0; x<aulasConCapa.size(); x++){
            Dia dia = aulasConCapa.get(x).getDia(nombre_dia);
            if(!grupoPresente(desde,hasta,dia.getHoras(),grupo))
                horasDisponibles = buscarHorasDisponibles(dia.getHoras(),cantidadHoras,desde,hasta,nombre_dia,aulasConCapa,grupo);
            if(horasDisponibles != null)
                break;
        }
        return horasDisponibles;
    }
    
    public static boolean grupoPresente(int desde, int hasta, ArrayList<Hora> horas, Grupo grupo){
        for(Hora hora : horas){
            if(!hora.estaDisponible()){
                Grupo grupoHora = hora.getGrupo();
                if(grupoHora.getId_Agrup() == grupo.getId_Agrup() && grupoHora.getId_grupo() == grupo.getId_grupo())
                    return true;
            }
        }
        return false;
    }
    
    public static boolean chocaGrupo(String nombre_dia, int desde, int hasta, ArrayList<Aula> aulas, Grupo grupo){
        for(int i=0; i<aulas.size(); i++){
            Dia dia = aulas.get(i).getDia(nombre_dia);
            for(int h=desde; h<hasta; h++){
                Hora hora = dia.getHoras().get(h-1);
                if(!hora.estaDisponible()){
                    Grupo grupoHora = hora.getGrupo();
                    if(grupoHora.getId_Agrup() == grupo.getId_Agrup() && grupoHora.getId_grupo() == grupo.getId_grupo()){
                        System.out.println("Este grupo: "+grupo.getId_grupo()+" de la Agrupacion "+grupo.getId_Agrup()+" choca en hora: "+h+" del dia "+nombre_dia);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean chocaGrupoDocente(Grupo grupo, int desde, int hasta, ArrayList<Aula> aulas, String nombre_dia){
        
        return false;
    }
    
    /** Meotodo para relizar busquedas de una materia que pertenece al mismo nivel en el dia elegido
     *
     * @param grupo = grupo que se quiere asignar en dia elegido
     * @param materia = materia a la que pertenece el grupo que se quiere asignar
     * @param todas_mats = array con todas las materias de la facultad
     * @param todas_agrups = todas las agrupaciones en sistema
     * @param aulas
     * @param nombreDia
     * @return ultima hora en la que hay una materia del mismo nivel
     */
    public static ArrayList getUltimasHoraDeNivel(Grupo grupo, Materia materia, ArrayList<Materia> todas_mats, ArrayList<Agrupacion> todas_agrups, ArrayList<Aula> aulas, String nombreDia){
        ArrayList horasNivel = new ArrayList();
        for(Aula a : aulas){
            int hora = -1;
            ArrayList<Hora> horas = a.getDia(nombreDia).getHoras();
            for(int x=0; x<horas.size(); x++){
                if(!horas.get(x).estaDisponible() && obtenerIdDepartamento(horas.get(x).getGrupo().getId_Agrup(),todas_agrups) == materia.getDepartamento()){
                    Grupo grupoHora = horas.get(x).getGrupo();
                    //Se obtiene la materia a la que pertenece el grupoHora
                    ArrayList<Materia> materias = ManejadorMaterias.getMateriaDeGrupo(grupoHora.getId_Agrup(), todas_mats);
                    for(int j=0; j<materias.size(); j++){
                        Materia materiaHora = materias.get(j);
                        if(materiaHora.getCodigoCarrera().equals(materia.getCodigoCarrera()) && materiaHora.getCiclo() == materia.getCiclo()){
                            if(materiaHora.getCodigo().equals(materia.getCodigo()) && grupoHora.getId_grupo() != grupo.getId_grupo()){
                                hora = x;
                                break;
                            }
                            else if(!materiaHora.getCodigo().equals(materia.getCodigo())){
                                hora = x;
                                break;
                            }
                        }
                    }
                }
            }
            if(hora != -1)
                horasNivel.add(hora);
        }
        return horasNivel;
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
        for(int i=0; i<aulas.size(); i++){
            Dia dia = aulas.get(i).getDia(nombre_dia);
            for(int h=id_hora; h<id_hora+num_horas; h++){
                Hora hora = dia.getHoras().get(h-1);
                if(!hora.estaDisponible()){
                    Grupo grupo = hora.getGrupo();
                    ArrayList<Materia> materias = ManejadorMaterias.getMateriaDeGrupo(grupo.getId_Agrup(), todas_mats);
                    for(int j=0; j<materias.size(); j++){
                        if(materias.get(j).getCodigoCarrera().equals(materia.getCodigoCarrera()) && materias.get(j).getCiclo() == materia.getCiclo()){
                            System.out.println("Esta materia: "+materia.getCodigo()+" choca con: "+obtenerNombrePropietario(grupo.getId_Agrup(),todas_mats)+" GT "+grupo.getId_grupo()+" en hora: "+h+" del dia "+nombre_dia);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
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
