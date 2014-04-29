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
public class ManejadorAsignacionesDocs {
    
    public static ArrayList<AsignacionDocente> obtenerTodasAsignacionesDocs(){
        ArrayList<AsignacionDocente> asignaciones = new ArrayList();
        Conexion con=new Conexion();
        ResultSet resul;
        try{
            con.conectar();
            resul=con.consulta("SELECT * FROM docentes_agrupaciones");
            while(resul.next()){
                asignaciones.add(new AsignacionDocente(resul.getInt(1),resul.getInt(2),resul.getInt(3)));
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return asignaciones;
    }
    
    public static ArrayList<AsignacionDocente> obtenerAsignacionesDeAgrup(int id_agrup,ArrayList<AsignacionDocente> todas_asignaciones){
        ArrayList<AsignacionDocente> asignaciones = new ArrayList();
        for(AsignacionDocente asignacion : todas_asignaciones){
            if(asignacion.getId_agrupacion() == id_agrup)
                asignaciones.add(asignacion);
        }
        return asignaciones;
    }
}
