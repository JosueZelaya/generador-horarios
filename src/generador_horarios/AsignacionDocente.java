/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generador_horarios;

/**
 *
 * @author dasm
 */
public class AsignacionDocente {
    
    private int id_docente;
    private int id_agrupacion;
    private int num_grupos;

    public AsignacionDocente(int id_docente, int id_agrupacion, int num_grupos) {
        this.id_docente = id_docente;
        this.id_agrupacion = id_agrupacion;
        this.num_grupos = num_grupos;
    }

    public int getId_docente() {
        return id_docente;
    }

    public void setId_docente(int id_docente) {
        this.id_docente = id_docente;
    }

    public int getId_agrupacion() {
        return id_agrupacion;
    }

    public void setId_agrupacion(int id_agrupacion) {
        this.id_agrupacion = id_agrupacion;
    }

    public int getNum_grupos() {
        return num_grupos;
    }

    public void setNum_grupos(int num_grupos) {
        this.num_grupos = num_grupos;
    }
}
