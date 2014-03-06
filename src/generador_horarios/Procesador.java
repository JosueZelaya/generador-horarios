/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

import java.util.ArrayList;

/**
 *
 * @author alexander
 */
public class Procesador {
    
     public int getNumeroAleatorio(int desde,int hasta){
        int aleatorio= (int) Math.floor(Math.random()*(hasta-desde+1)+desde);        
        return aleatorio;
    }
    
     public Dia elegirDia(ArrayList<Dia> dias){
        int desde = 0;
        int hasta = dias.size()-1;
        int dia = getNumeroAleatorio(desde, hasta);
        return dias.get(dia);
    }
     
    public Hora elegirHora(ArrayList<Hora> horas){
        int desde = 0;
        int hasta = horas.size()-1;
        int hora = getNumeroAleatorio(desde, hasta);
        return horas.get(hora);
    }
    
    public Aula elegirAula(ArrayList<Aula> aulas){
        int desde = 0;
        int hasta = aulas.size()-1;
        int aula = getNumeroAleatorio(desde, hasta);
        return aulas.get(aula);
    }
    
    public void asignarMateria(Semana semana,Materia materia){
        
        Dia dia = elegirDia(semana.getDias());
        System.out.println("Dia elegido: "+dia.getNombre());
        
        Hora hora = elegirHora(dia.getHoras());
        System.out.println("Hora elegida: "+hora.getIdHora());
        
        Aula aula = elegirAula(hora.getAulas());
        System.out.println("Aula elegida: "+aula.getNombre());
        
        if(aula.estaDisponible()){
            aula.setMateria(materia);
            aula.setDisponible(false);
        }else{
            asignarMateria(semana, materia);
        }
        
    }
    
}
