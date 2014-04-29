/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author dasm
 */
public abstract class ManejadorAgrupaciones {
    
    public static ArrayList<Agrupacion> getAgrupaciones(){
        ArrayList<Agrupacion> agrupaciones = new ArrayList();
        Conexion con = new Conexion();
        ResultSet resultado;
        
        try{
            con.conectar();
            resultado = con.consulta("SELECT * FROM agrupacion");
            while(resultado.next()){
                agrupaciones.add(new Agrupacion(resultado.getInt("id_agrupacion"),resultado.getInt("id_depar"),resultado.getInt("num_grupos"),resultado.getInt("num_alumnos")));
            }
            con.cierraConexion();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error en getAgrupaciones()", JOptionPane.ERROR_MESSAGE);
        }
        
        return agrupaciones;
    }
    
    public static Agrupacion getAgrupacion(int id, ArrayList<Agrupacion> a){
        for(int i = 0;i<a.size();i++){
            if(a.get(i).getId() == id){
                return a.get(i);
            }
        }
        return null;
    }
    
    public static Agrupacion getAgrupacion(Materia materia,ArrayList<Agrupacion> agrupaciones){
        for(int i = 0;i<agrupaciones.size();i++){
            if(agrupaciones.get(i).getId() == materia.getIdAgrupacion()){
                return agrupaciones.get(i);
            }
        }
        return null;
    }
    
    public static String obtenerNombrePropietario(int id_agrup,ArrayList<Materia> materias){
        String propietario = "";
        for(Materia mat : materias){
            if(mat.getIdAgrupacion() == id_agrup){
                propietario = mat.getNombre();
                break;
            }
        }
        return propietario;
    }
    
    public static int obtenerIdDepartamento(int id_agrup,ArrayList<Agrupacion> agrups){
        int id_depar=0;
        for(Agrupacion a : agrups){
            if(a.getId() == id_agrup){
                id_depar = a.getDepartamento();
                break;
            }
        }
        return id_depar;
    }
    
    public static ArrayList obtenerAgrupacionesDeCarrera(String carrera){
        Conexion con;
        ResultSet result;
        ArrayList ids = new ArrayList();
        try{
            con = new Conexion();
            result = con.consulta("SELECT cm.id_agrupacion FROM carreras_materias as cm JOIN carreras as c ON cm.carreras_id_carrera = c.id_carrera WHERE c.nombre_carrera = '"+carrera+"'");
            while(result.next()){
                ids.add(result.getInt("id_agrupacion"));
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return ids;
    }
    
    public static int obtenerIdAgrupacion(String cod_mat, int id_depar, ArrayList<Materia> materias){
        int id_agrup = 0;
        for(Materia m : materias){
            if(m.getCodigo().equals(cod_mat) && m.getDepartamento() == id_depar){
                id_agrup = m.getIdAgrupacion();
                break;
            }
        }
        return id_agrup;
    }
}
