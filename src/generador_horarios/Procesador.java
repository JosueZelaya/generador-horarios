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
        Dia elegido = new Dia();
        int desde = 0;
        int hasta = dias.size()-1;
        int dia = getNumeroAleatorio(desde, hasta);
        elegido = dias.get(dia);
        return elegido;
    }
     
    public void asignarMateria(Materia materia){
        
    }
    
}
