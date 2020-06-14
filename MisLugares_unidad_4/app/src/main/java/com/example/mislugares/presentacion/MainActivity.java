package com.example.mislugares.presentacion;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.mislugares.Aplicacion;
import com.example.mislugares.R;
import com.example.mislugares.casos_uso.CasosUsoActividades;
import com.example.mislugares.casos_uso.CasosUsoLugar;
import com.example.mislugares.datos.LugaresLista;
import com.example.mislugares.datos.RepositorioLugares;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/************************************************************************
        Clase MAinActivity
 Declarada en manifest.xml LAUNCHE ACTIVITY
 por lo que se lanzará como primera activity
 ************************************************************************/
public class MainActivity extends AppCompatActivity
{
    private RepositorioLugares lugares; //Lista de objetos tipo "lugar"
    private CasosUsoLugar usoLugar; //Para utilizar los métodos que actúan sobe objetos "Lugar"
    private CasosUsoActividades usoActividades;
    private Button bAcercaDe; //Declaración de un botón para "lanzarAcercaDe"
    private Button bSalir; //Declaración de un botón para salir de la app
    @Override //Método del ciclo de vida de la actividad
    /************************************************************************
        Constructor de MAinActivity
     ************************************************************************/
    protected void onCreate(Bundle savedInstanceState)
    {
        //--------ONCREATE. MÉTODO DEL CICLO DE VIDA DE LA ACTIVIDAD-----------
        super.onCreate(savedInstanceState); //Constructor del padre
        setContentView(R.layout.activity_main);//Indexa los elementos de la adtivity
        //Instanciación dela lista de objetos tipo "lugar" de la clase Aplication global
        lugares = ((Aplicacion) getApplication()).lugares;
        usoLugar = new CasosUsoLugar(this, lugares);
        usoActividades = new CasosUsoActividades(this);
        //Creamos el listener del botón "acerca de"
        bAcercaDe = findViewById(R.id.button3);//Creamos el objeto Button de activity_main
        bAcercaDe.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                lanzarAcercaDe(null);
            }
        });
        //Listener para el botón salir
        bSalir = findViewById(R.id.button4);
        bSalir.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                lanzarSalir(null);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);//Lee el indice del toolbar
        setSupportActionBar(toolbar);//incializa el toolbar
        //----Botón flotante fab----------------------------------------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "no se ha programado el botón", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }
    /************************************************************************
     inflado del menú de MAinActivity
     ************************************************************************/
    @Override    public boolean onCreateOptionsMenu(Menu menu) //Infla el menú
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    /************************************************************************
        Servicio al menú de MAinActivity
     **************************************************************************/
    @Override public boolean onOptionsItemSelected(MenuItem item) //Recibe la selección del menú
       {
        //Lee el id del item del menú pulsado
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id==R.id.action_settings)
        {
            return true;
        }
        if (id == R.id.acercaDe)
        {
            usoActividades.lanzarAcercaDe();
            return true;
        }
        if (id==R.id.preferencias)
        {
            lanzarPreferencias(null);
            return true;
        }
        if (id == R.id.menu_buscar)
        {
            lanzarVistaLugar(null);
            return true;
        }

               // TODO Más opociones del main menún aquí
        //if (id == R.id.action_settings){ TODO tu código aquí; return true;}
        return super.onOptionsItemSelected(item);
    }

   /************************************************************************
    *   METODOS
    *   DE MAINACTIVITY
   **************************************************************************/
    /**
     * Lanza la activity acerca_de
     * @param view
     */
    public void lanzarAcercaDe(View view)
    {
        Intent intentAcercade = new Intent(this, AcercaDeActivity.class);
        startActivity(intentAcercade);
    }
    // Reemplaza el contenida de la activity por el fragment de preferencias

    /***************************************************************************
     * Lanza la activity  "preferencias"
     *
     * @param view vista que llama a la función
     ***************************************************************************/
    public void lanzarPreferencias(View view)
    {usoActividades.lanzarPreferencias();}
    /****************************************************************************
     * Muestra un toast con un resumen de las preferencias
     *
     * @param view vista que llama a la función
     *****************************************************************************/
    public void mostrarPreferencias(View view)
    {
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);
        String s = "notificaciones: "+ pref.getBoolean("notificaciones",true)
                +", máximo a listar: " + pref.getString("maximo","?");
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    /****************************************************************************
     * Muestra un lugar
     * @param view vista que ejecuta la función
     ****************************************************************************/
    /*
    public void lanzarVistaLugar(View view)
     {

        usoLugar.mostrar(0);
    }
    */
    public void lanzarVistaLugar(View view)
    {
        //Crea una vista EditText
       final EditText entrada = new EditText(this);
        entrada.setText("0");
        //Crea un pop-up AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("Selección de lugar")
                .setMessage("indica su id:")
                .setView(entrada)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {

                        int id = Integer.parseInt(entrada.getText().toString());
                        if (id>=0 && id <=lugares.tamanyo())//Para evitar un id incorrecto
                        {
                            usoLugar.mostrar(id);
                        }
                    }
                }
                )//setPositiveButton
                .setNegativeButton("Cancelar", null)
                .show();
    }
/*****************************************************************************
* Sale de la app
*****************************************************************************/
    public void lanzarSalir(View view){finish();}
    // TODO Seguir insertando código desde aquí
}// MainActivity
/*********************************************************************************
 *  FIN
*********************************************************************************/