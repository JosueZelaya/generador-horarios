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
import static generador_horarios.ManejadorAgrupaciones.obtenerNombrePropietario;
import static generador_horarios.ManejadorAgrupaciones.obtenerIdDepartamento;

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
                aula.setDisponible(true);
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

    public static ArrayList<Aula> obtenerAulasPorCapacidad(ArrayList<Aula> aulas, int num_alumnos){
        ArrayList<Aula> aulasSelec = new ArrayList();
        for(int x=0; x<aulas.size(); x++){
            Aula aula = aulas.get(x);
            int capacidad = aula.getCapacidad();
            if(capacidad >= num_alumnos)
                aulasSelec.add(aulas.get(x));
        }
        return aulasSelec;
    }
    
    public static DefaultTableModel getHorarioEnAula(ArrayList<Aula> aulas, String aula, DefaultTableModel table, ArrayList<Materia> materias){
        for(int i=0; i<aulas.size(); i++){
            if(aulas.get(i).getNombre().equals(aula)){
                ArrayList<Dia> dias = aulas.get(i).getDias();
                for(int x=0; x<dias.size(); x++){
                    ArrayList<Hora> horas = dias.get(x).getHoras();
                    for(int y=0; y<horas.size(); y++){
                        Hora hora = horas.get(y);
                        Grupo grupo = hora.getGrupo();
                        if(!hora.estaDisponible() && grupo.getId_Agrup() != 0){
                            String propietario = obtenerNombrePropietario(grupo.getId_Agrup(),materias);
                            String texto = propietario+" GT: "+grupo.getId_grupo();
                            table.setValueAt(texto, y, x+1);
                        }else
                            table.setValueAt("", y, x+1);
                    }
                }
                break;
            }
        }
        return table;
    }
    
    public static DefaultTableModel getHorarioEnAula_Depar(ArrayList<Aula> aulas, String aula, DefaultTableModel table, int id_depar, ArrayList<Agrupacion> agrups, ArrayList<Materia> materias){
        for(int i=0; i<aulas.size(); i++){
            if(aulas.get(i).getNombre().equals(aula)){
                ArrayList<Dia> dias = aulas.get(i).getDias();
                for(int x=0; x<dias.size(); x++){
                    ArrayList<Hora> horas = dias.get(x).getHoras();
                    for(int y=0; y<horas.size(); y++){
                        Hora hora = horas.get(y);
                        Grupo grupo = hora.getGrupo();
                        if(obtenerIdDepartamento(grupo.getId_Agrup(), agrups) == id_depar){
                            String texto = obtenerNombrePropietario(grupo.getId_Agrup(),materias)+" GT: "+grupo.getId_grupo();
                            table.setValueAt(texto, y, x+1);
                        }else
                            table.setValueAt("", y, x+1);
                    }
                }
                break;
            }
        }
        
        return table;
    }
    
    public static DefaultTableModel getHorarioEnAula_Carrera(ArrayList<Aula> aulas, String aula, DefaultTableModel table, ArrayList ids_agrups, ArrayList<Materia> materias){
        
        for(int i=0; i<aulas.size(); i++){
            if(aulas.get(i).getNombre().equals(aula)){
                ArrayList<Dia> dias = aulas.get(i).getDias();
                for(int x=0; x<dias.size(); x++){
                    ArrayList<Hora> horas = dias.get(x).getHoras();
                    for(int y=0; y<horas.size(); y++){
                        Grupo grupo = horas.get(y).getGrupo();
                        for(int z=0; z<ids_agrups.size(); z++){
                            if((int)ids_agrups.get(z) == grupo.getId_Agrup()){
                                table.setValueAt(obtenerNombrePropietario(grupo.getId_Agrup(),materias)+" GT: "+grupo.getId_grupo(), y, x+1);
                                break;
                            }
                            else
                                table.setValueAt("", y, x+1);
                        }
                    }
                }
                break;
            }
        }
        return table;
    }
    
}
