package com.example.mislugares.casos_uso;
import android.content.Intent;
import android.app.Activity;
import com.example.mislugares.presentacion.AcercaDeActivity;//Para lanzar esta activity
import com.example.mislugares.presentacion.PreferenciasActivity;//Para lanzar esta activity
//Clase con métodos para lanzar activities
public class CasosUsoActividades
{
    //declaración del atributo de la clase: la activity que muestra la nueva
    private Activity actividad;
    /**Clase con métodos para lanzar actividades
     *
     * @param actividad Activity lanzadora de la nueva actividad a mostrar
     */
    public CasosUsoActividades(Activity actividad)//Tiene que reciber la actividad llamadora
    {
        this.actividad = actividad;
    }
    public void lanzarAcercaDe()
    {
        //La activity que nos pasan como parámetro en el constructor lanza AcercaDeActivity
        actividad.startActivity(new Intent(actividad, AcercaDeActivity.class));
    }
    public void lanzarPreferencias()
    {
        //La activity que nos pasan como parámetro en el constructor lanza PreferenciasActivity
        actividad.startActivity(new Intent(actividad, PreferenciasActivity.class));
    }
    public void lanzarEditar(int pos)
    {
        //La activity que nos pasan como parámetro en el constructor lanza PreferenciasActivity
        //actividad.startActivity(new Intent(actividad, EdicionLugarActivity.class));
    }
}
