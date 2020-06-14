package com.example.mislugares.presentacion;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import com.example.mislugares.Aplicacion;
import com.example.mislugares.R;
import com.example.mislugares.casos_uso.CasosUsoActividades;
import com.example.mislugares.casos_uso.CasosUsoLugar;
import com.example.mislugares.datos.RepositorioLugares;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/************************************************************************
        Clase MAinActivity
 Declarada en manifest.xml LAUNCHE ACTIVITY
 por lo que se lanzará como primera activity
 ************************************************************************/
public class MainActivity extends AppCompatActivity
{
    MediaPlayer mp;
    private RecyclerView recyclerView;
    public AdaptadorLugares adaptador;
    private RepositorioLugares lugares; //Lista de objetos tipo "lugar"
    private CasosUsoLugar usoLugar; //Para utilizar los métodos que actúan sobe objetos "Lugar"
    private CasosUsoActividades usoActividades;
    @Override //Método del ciclo de vida de la actividad
    /************************************************************************
        Primer método del ciclo de vida de la app
     ************************************************************************/
    protected void onCreate(Bundle savedInstanceState)
    {
        //--------ONCREATE. MÉTODO DEL CICLO DE VIDA DE LA ACTIVIDAD-----------
        super.onCreate(savedInstanceState); //Constructor del padre
        setContentView(R.layout.activity_main);//Indexa los elementos de la adtivity
        //Para que lance un toast cuando se cree la actividad solo para probar
        // Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        //Instanciación dela lista de objetos tipo "lugar" de la clase Aplication global
        mp = MediaPlayer.create(this, R.raw.audio);
        lugares = ((Aplicacion) getApplication()).lugares;
        usoLugar = new CasosUsoLugar(this, lugares);
        usoActividades = new CasosUsoActividades(this);
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
        //Instancia el adaptador
        adaptador = ((Aplicacion) getApplication()).adaptador;//adaptador es global
        //Instancia el ReciclerView
        recyclerView = findViewById(R.id.recycler_view);
        //Configura el ReciclerView
        recyclerView.setHasFixedSize(true);//El mismo tamaño siempre
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//vista Lineal
        recyclerView.setAdapter(adaptador);//Le aplica el adaptador
        //
        adaptador.setOnItemClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int pos = recyclerView.getChildAdapterPosition(v);
                usoLugar.mostrar(pos);
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
    /****************************************************************************
     * Metodos del ciclo de vida de la app
     *
     ****************************************************************************/

    @Override protected void onStart()
    {
    super.onStart();
    //Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }
    @Override protected void onResume()
    {
        super.onResume();
        mp.start();
        //Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }
    @Override protected void onPause()
    {
        //Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        super.onPause();
        mp.pause();
    }
    @Override protected void onStop()
    {
        //Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
        super.onStop();
        mp.pause();
    }
    @Override protected void onRestart()
    {
        super.onRestart();
        //Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }
    @Override protected void onDestroy()
    {
        //Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        mp.stop();
    }

    /**************************************************************************
     * Guarda el estado de la actividad antes de ser destruida por el sistema
     * @param estadoGuardado Bundle para guardar el estado
     ***************************************************************************/
    @Override protected void onSaveInstanceState(Bundle estadoGuardado)
    {
        super.onSaveInstanceState(estadoGuardado);
        if (mp != null) {
            int pos = mp.getCurrentPosition();
            estadoGuardado.putInt("posicion", pos);
        }
    }
    /***************************************************************************
     * Recupera el estado de la actividad cuando esta fué destruida por el sistema
     * @param estadoGuardado
     ***************************************************************************/
    @Override protected void onRestoreInstanceState(Bundle estadoGuardado)
    {
        super.onRestoreInstanceState(estadoGuardado);
        if (estadoGuardado != null && mp != null)
        {
            int pos = estadoGuardado.getInt("posicion");
            mp.seekTo(pos);
        }
    }
/*****************************************************************************
* Sale de la app
*****************************************************************************/
    public void lanzarSalir(View view){finish();}
    // TODO Seguir insertando código desde aquí
/*****************************************************************************/
}// MainActivity
/*********************************************************************************
 *  FIN
*********************************************************************************/