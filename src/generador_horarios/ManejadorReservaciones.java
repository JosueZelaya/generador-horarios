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
public class ManejadorReservaciones {
    
    public static ArrayList<Reservacion> getTodasReservaciones(){
        ArrayList<Reservacion> reservs = new ArrayList();
        Conexion con;
        ResultSet res;
        try{
            con = new Conexion();
            con.conectar();
            res = con.consulta("select nombre_dia,(select inicio from horas as h where h.id_hora = res.id_hora),(select fin from horas as h where h.id_hora = res.id_hora), cod_aula from reservaciones as res;");
            while(res.next()){
                reservs.add(new Reservacion(res.getString(1),res.getTime(2).toString(),res.getTime(3).toString(),res.getString(4)));
            }
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error en getTodasReservaciones()\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return reservs;
    }
    
    public static void nuevaReservacion(String dia, int id_hora, String cod_aula){
        Conexion con;
        try{
            con = new Conexion();
            con.conectar();
            con.insertar("INSERT INTO reservaciones values('"+dia+"',"+String.valueOf(id_hora)+",'"+cod_aula+"')");
            con.cierraConexion();
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error en nuevaReservacion()\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void eliminarReservacion(String nombre_dia, String hora_inicio, String cod_aula, ArrayList<Aula> aulas){
        Conexion con;
        try{
            con = new Conexion();
            con.conectar();
            con.borrar("DELETE FROM reservaciones WHERE nombre_dia='"+nombre_dia+"' AND id_hora=(select id_hora from horas where inicio='"+hora_inicio+"') AND cod_aula='"+cod_aula+"';");
            con.cierraConexion();
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error en eliminarReservacion()\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        for(Aula a : aulas){
            boolean hecho = false;
            if(a.getNombre().equals(cod_aula)){
                Dia dia = a.getDia(nombre_dia);
                for(Hora h : dia.getHoras()){
                    if(h.getInicio().equals(hora_inicio)){
                        h.setDisponible(true);
                        hecho = true;
                        break;
                    }
                }
            }
            if(hecho)
                break;
        }
    }
    
    public static void asignarReservaciones(Facultad facultad){
        Conexion con;
        ResultSet resultado;
        ArrayList<Aula> aulas = facultad.getAulas();
        
        try{
            con = new Conexion();
            con.conectar();
            resultado = con.consulta("SELECT * FROM reservaciones;");
            while(resultado.next()){
                boolean hecha = false;
                for(int i=0;i<facultad.getAulas().size();i++){
                    Aula aula = aulas.get(i);
                    if(resultado.getString(3).equals(aula.getNombre())){
                        Dia dia = aula.getDia(resultado.getString(1));
                        if(dia != null){
                            for(Hora h : dia.getHoras()){
                                if(h.getIdHora() == resultado.getInt(2)){
                                    h.setDisponible(false);
                                    hecha = true;
                                    break;
                                }
                            }
                        }
                    }
                    if(hecha)
                        break;
                }
            }
            con.cierraConexion();
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error en asignarReservas()\n"+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
