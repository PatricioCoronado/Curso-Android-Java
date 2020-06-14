package com.example.mislugares.presentacion;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mislugares.Aplicacion;
import com.example.mislugares.R;
import com.example.mislugares.casos_uso.CasosUsoLugar;
import com.example.mislugares.datos.RepositorioLugares;
import com.example.mislugares.modelo.Lugar;
import java.text.DateFormat;
import java.util.Date;
import android.widget.RatingBar;
import android.widget.Toast;

/**
 * Clase VistaLugarActivity
 * Lee el repositorio de lugares y una posición que apunta a un lugar
 */
public class VistaLugarActivity extends AppCompatActivity
{
        final static int RESULTADO_GALERIA = 2;
        final static int RESULTADO_FOTO = 3;
    final static int RESULTADO_EDITAR = 1;
        private ImageView  imageView;//Dummy se usa por compativilidad
        private ImageView foto;
        private Uri uriUltimaFoto;
        private RepositorioLugares lugares;
        private CasosUsoLugar usoLugar;
        private int pos;
        private Lugar lugar;
        //No tiene constructor, lanza una activity
        @Override protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            //Carga el layout vista_lugar
            setContentView(R.layout.vista_lugar);
            //La vista llamadora debe poner datos en la llamada con el valor de "pos"
            Bundle extras = getIntent().getExtras();
            pos = extras.getInt("pos", 0);
            //Instancia el repositorio
            lugares = ((Aplicacion) getApplication()).lugares;
            //Instancia la clase que aporta métodos para manejar la clase Lugar
            usoLugar = new CasosUsoLugar(this, lugares);
            //Selecciona un lugar concreto
            lugar = lugares.elemento(pos);
            foto = findViewById(R.id.foto);
            actualizaVistas();
        }
        //----------------------Menú-------------------------------------------
        @Override public boolean onCreateOptionsMenu(Menu menu)
        {
            getMenuInflater().inflate(R.menu.vista_lugar, menu);
            return true;
        }
        @Override public boolean onOptionsItemSelected(MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.accion_compartir:
                    usoLugar.compartir(lugar);
                    return true;
                case R.id.accion_llegar:
                    usoLugar.verMapa(lugar);
                    return true;
                case R.id.accion_editar:
                    editar(pos);
                    return true;
                case R.id.accion_borrar:
                    borrar(pos);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        /***********************************************************************
         * Actualiza la vista de la actitivy con los nuevos valores del
         * lugar cuando regrese de la Activity EdicionLugarActivity
         ***********************************************************************/
        protected void onActivityResult(int requestCode, int resultCode,Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == RESULTADO_EDITAR)
            {
                actualizaVistas();
                findViewById(R.id.scrollView1).invalidate();
            }
            else if (requestCode == RESULTADO_GALERIA)
            {
                if (resultCode == Activity.RESULT_OK)
                {
                    usoLugar.ponerFoto(pos, data.getDataString(), foto);
                }//if
                else
                {
                    Toast.makeText(this, "Foto no cargada", Toast.LENGTH_LONG).show();
                }//else
            }//else if
            else if (requestCode == RESULTADO_FOTO)
            {
                if (resultCode == Activity.RESULT_OK && uriUltimaFoto!=null)
                {
                    lugar.setFoto(uriUltimaFoto.toString());
                    usoLugar.ponerFoto(pos, lugar.getFoto(), foto);
                } //if
                else
                {
                    Toast.makeText(this, "Error en captura", Toast.LENGTH_LONG).show();
                }//else
            }//else if
        }// onActivityResult
        /***********************************************************************
         * Rellena los datos de una vista, en concreto la apuntada por "pos"
        ***********************************************************************/
        public void actualizaVistas()
        {
            TextView nombre = findViewById(R.id.nombre);
            nombre.setText(lugar.getNombre());
            ImageView logo_tipo = findViewById(R.id.logo_tipo);
            logo_tipo.setImageResource(lugar.getTipo().getRecurso());
            TextView tipo = findViewById(R.id.tipo);
            tipo.setText(lugar.getTipo().getTexto());
            TextView direccion = findViewById(R.id.direccion);
            direccion.setText(lugar.getDireccion());

            //Teléfono
            if (lugar.getTelefono() == 0)
            {
                findViewById(R.id.telefono).setVisibility(View.GONE);
            } else
            {
                findViewById(R.id.telefono).setVisibility(View.VISIBLE);
                TextView telefono = findViewById(R.id.telefono);
                telefono.setText(Integer.toString(lugar.getTelefono()));
            }
            // url
            if (lugar.getUrl().isEmpty())
            {
                findViewById(R.id.url).setVisibility(View.GONE);
            } else
            {
                findViewById(R.id.url).setVisibility(View.VISIBLE);
                TextView url = findViewById(R.id.url);
                url.setText(lugar.getUrl());
            }
            // comentario
            if (lugar.getComentario().isEmpty())
            {
                findViewById(R.id.comentario).setVisibility(View.GONE);
            }
            else
            {
                findViewById(R.id.comentario).setVisibility(View.VISIBLE);
                TextView comentario = findViewById(R.id.comentario);
                comentario.setText(lugar.getComentario());
            }
            if(lugar.getFecha()==0)
            {
                findViewById(R.id.fecha).setVisibility(View.GONE);
                findViewById(R.id.hora).setVisibility(View.GONE);
            }
            else
            {
                findViewById(R.id.fecha).setVisibility(View.VISIBLE);
                TextView fecha = findViewById(R.id.fecha);
                fecha.setText(DateFormat.getDateInstance().format(
                        new Date(lugar.getFecha())));
                findViewById(R.id.hora).setVisibility(View.VISIBLE);
                TextView hora = findViewById(R.id.hora);
                hora.setText(DateFormat.getTimeInstance().format(
                        new Date(lugar.getFecha())));
            }
            //Apunta al RatingBar con "valoracion"
            RatingBar valoracion = findViewById(R.id.valoracion);
            valoracion.setRating(lugar.getValoracion());
            //Crea una función listener para el ratingBar "valoracion"
            valoracion.setOnRatingBarChangeListener
                    (
                            new RatingBar.OnRatingBarChangeListener()
                            {//Vamos a utilizar la función onRatingChanged para
                                @Override public void onRatingChanged(RatingBar ratingBar,float valor, boolean fromUser)
                                {
                                    lugar.setValoracion(valor);
                                }
                            }
                    );
            //Le pasamos una foto y una alternativa por defecto
            usoLugar.visualizarFoto(lugar, foto,R.id.foto);
        }
        /**********************************************************************************
         * Crea un cuadro de dialogo para asegurase de que quiere borrar el lugar
         * @param id Posición en la lista del "Lugar" que quiere borrar
         **********************************************************************************/
        private void borrar(final int id)
        {
            new AlertDialog.Builder(this)
                    .setTitle("borrar lugar")
                    .setMessage("¿Seguro que lo quieres borrar?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int whichButton)
                                {
                                    usoLugar.borrar(id);
                                }
                            }
                    )//setPositiveButton
                    .setNegativeButton("No", null)
                    .show();
        }
        /**
         * Lanza la actividad que muestra datos para editar
         *  @param id Posición en la lista del "Lugar" que quieres editar
         */
        private void editar(int id)
        {
            usoLugar.editar(id,RESULTADO_EDITAR);
        }

    /*************************************************************************
     * Llamada a una función que lanza un intent implicito que habre GoogleMaps
     * @param view
     *************************************************************************/
    public void verMapa(View view)
    {
        usoLugar.verMapa(lugar);
    }
    /**************************************************************************
     * Llamada a una función que lanza un intent implicito que habre el teléfono
     * @param view
     *************************************************************************/
    public void llamarTelefono(View view)
    {
        usoLugar.llamarTelefono(lugar);
    }
    /**************************************************************************
     * Llamada a una función que lanza un intent implicito que habre Chrome
     * @param view
     *************************************************************************/
    public void verPgWeb(View view)
    {
        usoLugar.verPgWeb(lugar);
    }
    /**************************************************************************
     * Actualiza la fotografía de la vista del lugar
     * @param view
     *************************************************************************/
    public void ponerDeGaleria(View view)
    {
        usoLugar.ponerDeGaleria(imageView);//imageView es dummy, solo por compatibilidad
    }
    /**
     *
     * @param view
     */
    public void tomarFoto(View view) //view es dummy
    {
        uriUltimaFoto = usoLugar.tomarFoto(RESULTADO_FOTO);
    }

    /**
     *
     * @param view
     */
    public void eliminarFoto(View view) {
        usoLugar.ponerFoto(pos, "", foto);
    }

}//class
 /***************************************************************************************
  * FIN
 ***************************************************************************************/