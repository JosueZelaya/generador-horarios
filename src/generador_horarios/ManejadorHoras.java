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
import javax.swing.JOptionPane;

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
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
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
    
    //Devuelve las primeras horas disponibles consecutivas que encuentre
    public static ArrayList<Hora> buscarHorasDisponibles(ArrayList<Hora> horas,int cantidadHoras,int desde,int hasta,String nombre_dia,Materia materia,ArrayList<Aula> aulas){        
        ArrayList<Hora> horasDisponibles = new ArrayList();
        for (int i = desde; i <= hasta; i++) {                   //Verifico si hay horas continuas disponibles en el intervalo requerido
            Boolean hayBloquesDisponibles=false;
            
            //Si hay una hora disponible debe verificarse que su indice no sea tal que se desborde el array al preguntar por las siguientes
            if(horas.get(i).estaDisponible() && horas.get(i).getIdHora()<=(horas.size()+1)-cantidadHoras){            
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
                    if(!chocaMateria(nombre_dia,horas.get(j).getIdHora(),aulas,materia,cantidadHoras))
                        horasDisponibles.add(horas.get(j));
                }
                if(horasDisponibles.size() == cantidadHoras)
                    return horasDisponibles;
            }
        }
        return null;
    }
    
    public static ArrayList<Hora> buscarHorasParaNivel(int cantidadHoras,int desde,int hasta,String nombre_dia,Materia materia,ArrayList<Aula> aulas){
        ArrayList<Hora> horasDisponibles = null;
        for(int x=0; x<aulas.size(); x++){
            Dia dia = aulas.get(x).getDia(nombre_dia);
            horasDisponibles = buscarHorasDisponibles(dia.getHoras(),cantidadHoras,desde+1,hasta,nombre_dia,materia,aulas);
            if(horasDisponibles != null)
                break;
        }
        return horasDisponibles;
    }
    
    public static Hora MateriaDeNivelEnHoras(Materia materia, ArrayList<Hora> horas){
        Hora horaNivel = null;
        Conexion con;
        try{
            con = new Conexion();
            for(int x=0; x<horas.size(); x++){
                if(!horas.get(x).estaDisponible() && horas.get(x).getGrupo().getId_depar() == materia.getDepartamento()){
                    con.conectar();
                    Grupo grupo = horas.get(x).getGrupo();
                    String codigo = grupo.getCod_materia();
                    int id_dep = grupo.getId_depar();
                    ResultSet res = con.consultaMateriaDeGrupo("SELECT carreras_id_carrera, ciclo FROM carreras_materias WHERE materias_cod_materia=? AND agrupacion_id_depar=?;", codigo, id_dep);
                    while(res.next()){
                        if(res.getString(1).equals(materia.getCodigoCarrera()) && res.getInt(2) == materia.getCiclo()){
                            horaNivel = horas.get(x); //Devolver la ultima hora con materia del nivel en el supuesto que no hay horas vacias
                        }
                    }
                    con.cierraConexion();
                }
                else if(horas.get(x).estaDisponible() && horas.get(x).getIdHora()==7)
                    continue;
                else if(horas.get(x).estaDisponible())
                    break;
            }
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error en MateriadeNivelEnHoras\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return horaNivel;
    }
    
    public static boolean chocaMateria(String nombre_dia, int id_hora, ArrayList<Aula> aulas, Materia materia, int num_horas){
        boolean chocan = false;
        Conexion con;
        try{
            con = new Conexion();
            for(int i=0; i<aulas.size(); i++){
                Dia dia = aulas.get(i).getDia(nombre_dia);
                for(int h=id_hora; h<id_hora+num_horas; h++){
                    Hora hora = dia.getHoras().get(h-1);
                    if(!hora.estaDisponible()){
                        Grupo grupo = hora.getGrupo();
                        con.conectar();
                        ResultSet res = con.consulta("SELECT carreras_id_carrera, ciclo FROM carreras_materias WHERE materias_cod_materia='"+grupo.getCod_materia()+"' AND agrupacion_id_depar="+grupo.getId_depar()+";");
                        while(res.next()){
                            if(res.getString(1).equals(materia.getCodigoCarrera()) && res.getInt(2) == materia.getCiclo()){
                                chocan = true;
                                break;
                            }
                        }
                        con.cierraConexion();
                    }
                }
                if(chocan)
                    break;
            }
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error en chocaMateria\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return chocan;
    }
    
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
