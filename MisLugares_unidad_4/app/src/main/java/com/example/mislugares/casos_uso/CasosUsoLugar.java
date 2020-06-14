package com.example.mislugares.casos_uso;
import android.app.Activity;
import android.content.Intent;

import com.example.mislugares.R;
import com.example.mislugares.datos.RepositorioLugares;
import com.example.mislugares.modelo.TipoLugar;
import com.example.mislugares.modelo.Lugar;
import com.example.mislugares.presentacion.EdicionLugarActivity;
import com.example.mislugares.presentacion.VistaLugarActivity;

public class CasosUsoLugar {
    //Declaración de objetos privados conde se copiaran los objetos pasados
    //como parametro al constructor de al clase
    private Activity actividad;
    private RepositorioLugares lugares;

    /**
     * Clase con métodos para manejar objeto de las clase "lugar" en la lista
     * de lugares implementada en la clase LugaresLista que implementala el
     * iterfaz RepositirioLugares. Para acceder a los lugares tiene que acceder
     * lugares instanciado como RepositorioLugares
     *
     * @param actividad Activity desde la que se quiere manipular los objetos "Lugar"
     * @param lugares   Repositorio de lugares
     */
    public CasosUsoLugar(Activity actividad, RepositorioLugares lugares) {
        this.actividad = actividad;
        this.lugares = lugares;
    }
    // OPERACIONES BÁSICAS

    /*************************************************************************
     * Mostrar el lugar que ocupa la poición "pos" de la lista
     * Lanza una activity
     * @param pos posición a mosrar
     *************************************************************************/
    public void mostrar(int pos) {
        Intent i = new Intent(actividad, VistaLugarActivity.class);
        i.putExtra("pos", pos);
        actividad.startActivity(i);
    }

    /*************************************************************************
     * Borrar el lugar que ocupa la poición "pos" de la lista
     * Borra de la lista del repositorio
     * @param id posición a borrar
     *************************************************************************/
    public void borrar(final int id) {
        lugares.borrar(id);
        actividad.finish();
    }

    /*************************************************************************
     * Mostrar el lugar que ocupa la poición "pos" de la lista
     * Lanza una activity
     * @param pos posición a mosrar
     *************************************************************************/
    public void editar(int pos, int codidoSolicitud)
    {
        Intent i = new Intent(actividad, EdicionLugarActivity.class);
        i.putExtra("pos", pos);
        actividad.startActivityForResult(i, codidoSolicitud);
    }



/*
    public void editar(int pos) {
        Intent i = new Intent(actividad, EdicionLugarActivity.class);
        i.putExtra("pos", pos);
        actividad.startActivity(i);
   }
*/
    /*************************************************************************
     * Cambia los valores dellugar apuntado por id con los del "lugar"
     * que entra como parámetro
     * @param id
     * @param lugarCambiado
     ************************************************************************/
    public void guardar(int id, Lugar lugarCambiado)
    {
        lugares.actualiza(id, lugarCambiado);
    }

}
/*******************************************************************************
 * FIN
 * *****************************************************************************/


