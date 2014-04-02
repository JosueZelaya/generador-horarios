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
public abstract class ManejadorAgrupaciones {
    
    public static ArrayList<Agrupacion> getAgrupaciones(){
        ArrayList<Agrupacion> agrupaciones = new ArrayList();
        Conexion con = new Conexion();
        ResultSet resultado;
        
        try{
            con.conectar();
            resultado = con.consulta("SELECT * FROM agrupacion");
            while(resultado.next()){
                agrupaciones.add(new Agrupacion(resultado.getString("cod_materia"),resultado.getInt("id_depar"),resultado.getInt("num_grupos"),resultado.getInt("numero_alumnos")));
            }
            con.cierraConexion();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return agrupaciones;
    }
    
    public static Agrupacion getAgrupacion(String cod_materia, int id_depar, ArrayList<Agrupacion> a){
        for(int i = 0;i<a.size();i++){
            if(a.get(i).getPropietario().equals(cod_materia) && a.get(i).getDepartamento() == id_depar){
                return a.get(i);
            }
        }
        return null;
    }
    
    public static Agrupacion getAgrupacion(Materia materia,ArrayList<Agrupacion> agrupacion){
        for(int i = 0;i<agrupacion.size();i++){
            if(agrupacion.get(i).getPropietario().equals(materia.getCodigo()) && agrupacion.get(i).getDepartamento() == materia.getDepartamento()){
                return agrupacion.get(i);
            }
        }
        return null;
    }
    
}
