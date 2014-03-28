/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alexander
 */
public abstract class ManejadorCarreras {
    
    public static ArrayList<String> getNombreTodasCarreras(){
        ArrayList<String> nombre_carreras = new ArrayList();
        Conexion conexion = new Conexion();        
        ResultSet resultadoConsulta;
        try {
            conexion.conectar();
            resultadoConsulta = conexion.consulta("SELECT nombre_carrera FROM carreras ORDER BY nombre_carrera");
            while(resultadoConsulta.next()){
                nombre_carreras.add(resultadoConsulta.getString("nombre_carrera"));
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());            
        }        
        return nombre_carreras;       
        
    }
    
    public static ArrayList<String> getNombreTodasCarrerasPorDepartamento(int idDepartamento){
        ArrayList<String> nombre_carreras = new ArrayList();
        Conexion conexion = new Conexion();        
        ResultSet resultadoConsulta;
        try {
            conexion.conectar();
            resultadoConsulta = conexion.consulta("SELECT nombre_carrera FROM carreras WHERE id_depar='"+idDepartamento+"' ORDER BY nombre_carrera");
            while(resultadoConsulta.next()){
                nombre_carreras.add(resultadoConsulta.getString("nombre_carrera"));
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());            
        }        
        return nombre_carreras;
    }
    
     public static String getCodigoCarrera(String nombreCarrera){
        String codCarrera="";
        Conexion conexion = new Conexion();        
        ResultSet resultadoConsulta;
        try {
            conexion.conectar();
            resultadoConsulta = conexion.consulta("SELECT id_carrera FROM carreras WHERE nombre_carrera='"+nombreCarrera+"'");
            while(resultadoConsulta.next()){               
                codCarrera=resultadoConsulta.getString("id_carrera");
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return codCarrera;
    }
    
}
