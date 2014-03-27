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
        
        con.conectar();
        resultado = con.consulta("SELECT * FROM departamentos;");
        
        while(resultado.next()){
            depars.add(new Departamento(resultado.getInt(1),resultado.getString(2)));
        }
        
        con.cierraConexion();
        
        return depars;
    }
    
    public static int getIdDepar(String nombre, ArrayList<Departamento> depars){
        int id = 0;
        
        for(int x=0; x<depars.size(); x++){
            if(depars.get(x).getNombre().equals(nombre)){
                id = depars.get(x).getId();
                break;
            }
        }
        
        return id;
    }
    
    public static String getNombreDepar(int id, ArrayList<Departamento> depars){
        String name="";
        
        for(int x=0; x<depars.size(); x++){
            if(depars.get(x).getId() == id){
                name = depars.get(x).getNombre();
                break;
            }
        }
        
        return name;
    }
    
}
