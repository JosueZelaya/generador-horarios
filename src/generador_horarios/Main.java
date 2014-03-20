/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import static generador_horarios.ManejadorMaterias.getTodasMaterias;
import static generador_horarios.Procesador.getNumeroAleatorio;
import java.util.ArrayList;
import static generador_horarios.ManejadorAgrupaciones.getAgrupacion;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexander
 */
public class Main {
    
    public static void main(String[] args){
        Campus campus = new Campus(ManejadorAgrupaciones.getAgrupaciones());
        ArrayList<Materia> materias;        
        Procesador procesador = new Procesador();
        boolean cicloPar = true;
        
        materias = ManejadorMaterias.getTodasMaterias(cicloPar);
        
        for (int i = 0; i < materias.size(); i++) {
            Agrupacion agrup = getAgrupacion(materias.get(i).getCodigo(),materias.get(i).getDepartamento(),campus.getAgrupaciones());
            while(agrup.getNumGruposAsignados() < agrup.getNum_grupos()){
                try {
                    procesador.procesarMateria(campus, materias.get(i));
                } catch (Exception ex) {
                    //Se produce cuando ya no hay aulas disponibles
                    System.out.println(ex.getMessage());                                        
                }
                agrup.setNumGruposAsignados(agrup.getNumGruposAsignados()+1);
                //System.out.println("Grupo Asignado: "+agrup.getNumGruposAsignados()+" materia: "+agrup.getPropietario());
            }
        }        
        
        //IMPRIMIR LA SEMANA
        imprimir(campus);
        
//        int num= Procesador.getNumeroAleatorio(0,1);
//        System.out.println("numero: "+num);
        
    }
    
    public static void imprimir(Campus campus){
        ArrayList<Aula> aulas;
        aulas = campus.getAulas();
        ArrayList<Dia> dias;
        ArrayList<Hora> horas;
        
        for (int i = 0; i < aulas.size(); i++) {
            Aula aula = aulas.get(i);
            System.out.println("Aula: "+aula.getNombre());
            dias = aula.getDias();
            for (int j = 0; j < dias.size(); j++) {
                Dia dia = dias.get(j);
                System.out.println("        Nombre: "+dia.getNombre());
                horas = dia.getHoras();
                for (int k = 0; k < horas.size(); k++) {
                    Hora hora = horas.get(k);
                    System.out.println("            Dia: "+dia.getNombre()+" Aula: "+aula.getNombre()+" Hora: "+hora.getIdHora()+", Disponible: "+hora.estaDisponible() + ", Materia:"+hora.getGrupo().getCod_materia()+", Grupo: "+hora.getGrupo().getId_grupo()+", Departamento"+hora.getGrupo().getId_depar());                    
                }
                
            }
        }
    }
    
}
