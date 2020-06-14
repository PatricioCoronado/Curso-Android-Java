package com.example.mislugares;
import android.app.Application;
import com.example.mislugares.datos.LugaresLista;//Implementa la interface RepositorioLugares
import com.example.mislugares.datos.RepositorioLugares;//Hay que acceder a la interface también
public class Aplicacion extends Application
{
    //Para hacer global el repositorio con la lista de lugares
    public RepositorioLugares lugares = new LugaresLista();
    // Métodos del padre (sobreescritos) y propios
    @Override public void onCreate()
    {
        super.onCreate();
    }
    //getter para entregar el repositorio con la lista de lugares
    public RepositorioLugares getLugares()
    {
        return lugares;//Repositorio
    }
}

