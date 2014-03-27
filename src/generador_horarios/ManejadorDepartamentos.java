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
 * @author dasm
 */
public abstract class ManejadorDepartamentos {
    
    public static ArrayList<Departamento> getDepartamentos() throws SQLException{
        ArrayList<Departamento> depars = new ArrayList();
        Conexion con = new Conexion();
        ResultSet resultado;
        
        resultado = con.consulta("SELECT * FROM departamentos");
        
        while(resultado.next()){
            depars.add(new Departamento(resultado.getInt(1),resultado.getString(2)));
        }
        
        con.cierraConexion();
        
        return depars;
    }
    
    public static ArrayList<String> getNombreDepartamentos(){
        ArrayList<String> nombre_departamentos = new ArrayList();
        Conexion conexion = new Conexion();        
        ResultSet resultadoConsulta;
        try {
            conexion.conectar();
            resultadoConsulta = conexion.consulta("SELECT nombre_depar FROM departamentos ORDER BY nombre_depar");
            while(resultadoConsulta.next()){
                nombre_departamentos.add(resultadoConsulta.getString("nombre_depar"));
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());            
        }        
        return nombre_departamentos;
    }
    
    public static String getNombreDepartamento(int idDepartamento){
        String nombreDepartamento="";
        Conexion conexion = new Conexion();        
        ResultSet resultadoConsulta;
        try {
            conexion.conectar();
            resultadoConsulta = conexion.consulta("SELECT nombre_depar FROM departamentos WHERE id_depar='"+idDepartamento+"'");
            while(resultadoConsulta.next()){               
                nombreDepartamento=resultadoConsulta.getString("nombre_depar");
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return nombreDepartamento;
    }
    
    public static int getIdDepar(String nombre, ArrayList<Departamento> depars){
        int id = 0;
        
        for(int x=0; x<depars.size(); x++){
            if(depars.get(x).getNombre().equals(nombre))
                id = depars.get(x).getId();
        }
        
        return id;
    }
    
}
