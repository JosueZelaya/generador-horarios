/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import static generador_horarios.Procesador.getNumeroAleatorio;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alexander
 */
public abstract class ManejadorAulas {
    
    
    
    public static ArrayList<Aula> getTodasAulas(){
        ArrayList<Aula> aulas = new ArrayList();
        
        Conexion conexion = new Conexion();        
        ResultSet resultadoConsulta;
        try {
            conexion.conectar();
            resultadoConsulta = conexion.consulta("SELECT * FROM aulas ORDER BY capacidad ASC");
            while(resultadoConsulta.next()){
                Aula aula = new Aula();
                aula.setNombre(resultadoConsulta.getString("cod_aula"));
                aula.setCapacidad(resultadoConsulta.getInt("capacidad"));
                aulas.add(aula);
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());            
        }        
        return aulas;
    }
  
     public static ArrayList<Aula> getTodasAulasOrdenadas(String ordenarPor){
        ArrayList<Aula> aulas = new ArrayList();
        
        Conexion conexion = new Conexion();        
        ResultSet resultadoConsulta;
        try {
            conexion.conectar();
            resultadoConsulta = conexion.consulta("SELECT * FROM aulas ORDER BY "+ordenarPor+" DESC");
            while(resultadoConsulta.next()){
                Aula aula = new Aula();
                aula.setNombre(resultadoConsulta.getString("cod_aula"));
                aula.setCapacidad(resultadoConsulta.getInt("capacidad"));
                aulas.add(aula);
            }
            conexion.cierraConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());            
        }        
        return aulas;
    }
    
    public static Aula elegirAulaDiferente(ArrayList<Aula> aulas,ArrayList<Aula> aulasUsadas){
        //Si ya se usaron todas las aulas entonces no seguimos buscando y devolvemos null
        if(aulas.size() == aulasUsadas.size()){
            return null;
        } 
        Aula aula = elegirAula(aulas);
        for (int i = 0; i < aulasUsadas.size(); i++) {
            if(aula.getNombre().equals(aulasUsadas.get(i).getNombre())){
                aula = elegirAulaDiferente(aulas, aulasUsadas);
            }
        }
        return aula;
    }
    
    public static Aula elegirAula(ArrayList<Aula> aulas){
        int desde = 0;
        int hasta = aulas.size()-1;
        int aula = getNumeroAleatorio(desde, hasta);
        return aulas.get(aula);
    }
    
    public static DefaultTableModel getHorarioEnAula(ArrayList<Aula> aulas, String aula, DefaultTableModel table){
        for(int i=0; i<aulas.size(); i++){
            if(aulas.get(i).getNombre().equals(aula)){
                ArrayList<Dia> dias = aulas.get(i).getDias();
                for(int x=0; x<dias.size(); x++){
                    ArrayList<Hora> horas = dias.get(x).getHoras();
                    for(int y=0; y<horas.size(); y++){
                        table.setValueAt(horas.get(y).getGrupo().getCod_materia()+"\nGrupo: "+horas.get(y).getGrupo().getId_grupo()+"\nDepartamento: "+horas.get(y).getGrupo().getId_depar(), y, x+1);
                    }
                }
            }
        }
        
        return table;
    }
    
    public static DefaultTableModel getHorarioEnAula_Depar(ArrayList<Aula> aulas, String aula, DefaultTableModel table, int id_depar, ArrayList<Departamento> depars){
        
        
        return table;
    }
    
}
