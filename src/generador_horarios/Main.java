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
import java.sql.SQLException;
import java.sql.Time;

/**
 *
 * @author alexander
 */
public class Main {
    
    public static void main(String[] args){     
        VentanaInicio ventanaInicial = new VentanaInicio();
        ventanaInicial.setLocationRelativeTo(null);
        ventanaInicial.setVisible(true);           
    }
    
    public static void imprimir(Facultad campus){
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
                System.out.println("\tNombre: "+dia.getNombre());
                horas = dia.getHoras();
                for (int k = 0; k < horas.size(); k++) {
                    Hora hora = horas.get(k);
                    System.out.println("\t\tDia: "+dia.getNombre()+" Aula: "+aula.getNombre()+" Hora: "+hora.getIdHora()+", Disponible: "+hora.estaDisponible() + ", Materia:"+hora.getGrupo().getCod_materia()+", Grupo: "+hora.getGrupo().getId_grupo()+", Departamento"+hora.getGrupo().getId_depar());                    
                }
                
            }
        }
    }
    
}
