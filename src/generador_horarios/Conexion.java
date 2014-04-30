/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

/**
 *
 * @author dasm
 */
public class Conexion {
    
    private final String usuario;
    private final String clave;
    private final String url;
    private Connection conn;
    private Statement declaracion;
    private PreparedStatement ps;
    
    public Conexion(String usuario, String clave, String url) {
        this.usuario = usuario;
        this.clave = clave;
        this.url = url;
        conn = null;
        ps = null;
    }
    
    public Conexion(){
        this.usuario = "postgres";
        this.clave = "password";
        this.url = "jdbc:postgresql://localhost/horarios_bd";
        conn = null;
        ps = null;
    }
    
    public void conectar() throws SQLException {
            this.conn = DriverManager.getConnection(this.url, this.usuario, this.clave);        
    }
    
    public void cierraConexion() throws SQLException {
        if(this.declaracion == null || this.declaracion.isClosed())
            this.ps.close();
        else
            this.declaracion.close();
        
        this.conn.close();
    }
    
    public ResultSet consulta(String consulta) throws SQLException {
        this.declaracion = (Statement) conn.createStatement();
        return this.declaracion.executeQuery(consulta);
    }
 
    public void actualizar(String actualiza) throws SQLException {
        this.declaracion = (Statement) conn.createStatement();
        declaracion.executeUpdate(actualiza);
    }
 
    public void borrar(String borra) throws SQLException {
        this.declaracion = (Statement) this.conn.createStatement();
        this.declaracion.executeUpdate(borra);
    }
 
    public int insertar(String inserta) throws SQLException {
        this.declaracion = (Statement) this.conn.createStatement();
        return this.declaracion.executeUpdate(inserta);
    }
    
    public ResultSet consultaCiclos(String consulta, boolean param) throws SQLException {
        this.ps = this.conn.prepareStatement(consulta);
        if(param){
            this.ps.setInt(1, 2);
            this.ps.setInt(2, 4);
            this.ps.setInt(3, 6);
            this.ps.setInt(4, 8);
            this.ps.setInt(5, 10);
        } else{
            this.ps.setInt(1, 1);
            this.ps.setInt(2, 3);
            this.ps.setInt(3, 5);
            this.ps.setInt(4, 7);
            this.ps.setInt(5, 9);
        }
        return this.ps.executeQuery();
    }
    
    public ResultSet consultaMateriaDeGrupo(String consulta, String codigoMateria, int id_depar) throws SQLException{
        this.ps = this.conn.prepareStatement(consulta);
        this.ps.setString(1, codigoMateria);
        this.ps.setInt(2, id_depar);
        return this.ps.executeQuery();
    }
    
    public void actualizarHora(String statement, int id, String inicio, String fin) throws SQLException{
        this.ps = this.conn.prepareStatement(statement);
        Time i = Time.valueOf(inicio);
        Time f = Time.valueOf(fin);
        
        this.ps.setInt(3, id);
        this.ps.setTime(1, i);
        this.ps.setTime(2, f);
        
        this.ps.executeUpdate();
    }
}

