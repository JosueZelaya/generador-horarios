/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author dasm
 */
public class Conexion {
    
    private String usuario;
    private String clave;
    private String url;
    private Connection conn = null;
    private Statement estancia;
    
    public Conexion(String usuario, String clave, String url) {
        this.usuario = usuario;
        this.clave = clave;
        this.url = url;
    }
    
    public Conexion(){
        this.usuario = "postgres";
        this.clave = "password";
        this.url = "jdbc:postgresql://localhost/horarios_bd";
    }
    
    public void conectar() throws SQLException {
            this.conn = DriverManager.getConnection(this.url, this.usuario, this.clave);        
    }
    
    public void cierraConexion() throws SQLException {
        this.conn.close();
    }
    
    public ResultSet consulta(String consulta) throws SQLException {
        this.estancia = (Statement) conn.createStatement();
        return this.estancia.executeQuery(consulta);
    }
 
    public void actualizar(String actualiza) throws SQLException {
        this.estancia = (Statement) conn.createStatement();
        estancia.executeUpdate(actualiza);
    }
 
    public ResultSet borrar(String borra) throws SQLException {
        Statement st = (Statement) this.conn.createStatement();
        return (ResultSet) st.executeQuery(borra);
    }
 
    public int insertar(String inserta) throws SQLException {
        Statement st = (Statement) this.conn.createStatement();
        return st.executeUpdate(inserta);
    }
}

