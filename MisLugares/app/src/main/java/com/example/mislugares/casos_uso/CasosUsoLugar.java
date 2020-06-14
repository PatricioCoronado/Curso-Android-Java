package com.example.mislugares.casos_uso;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mislugares.R;
import com.example.mislugares.datos.RepositorioLugares;
import com.example.mislugares.modelo.GeoPunto;
import com.example.mislugares.modelo.TipoLugar;
import com.example.mislugares.modelo.Lugar;
import com.example.mislugares.presentacion.EdicionLugarActivity;
import com.example.mislugares.presentacion.VistaLugarActivity;
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
import androidx.core.content.FileProvider;

import com.example.mislugares.Aplicacion;
import com.example.mislugares.R;
import com.example.mislugares.casos_uso.CasosUsoLugar;
import com.example.mislugares.datos.RepositorioLugares;
import com.example.mislugares.modelo.Lugar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import android.widget.RatingBar;
import android.widget.Toast;

import static android.widget.Toast.*;

public class CasosUsoLugar {
    //Declaración de objetos privados conde se copiaran los objetos pasados
    //como parametro al constructor de al clase
    final static int RESULTADO_GALERIA = 2;
    final static int RESULTADO_FOTO = 3;
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
    public void editar(int pos, int codidoSolicitud) {
        Intent i = new Intent(actividad, EdicionLugarActivity.class);
        i.putExtra("pos", pos);
        actividad.startActivityForResult(i, codidoSolicitud);
    }

    /*************************************************************************
     * Cambia los valores dellugar apuntado por id con los del "lugar"
     * que entra como parámetro
     * @param id
     * @param lugarCambiado
     ************************************************************************/
    public void guardar(int id, Lugar lugarCambiado) {
        lugares.actualiza(id, lugarCambiado);
    }
    // INTENCIONES IMPLICITAS
    /**
     *
     * @param lugar
     */
    public void compartir(Lugar lugar)
    {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT,lugar.getNombre() + " - " + lugar.getUrl());
        actividad.startActivity(i);
    }
    /**
     *
     * @param lugar
     */
    public void llamarTelefono(Lugar lugar)
    {
        actividad.startActivity(new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + lugar.getTelefono())));
    }
    /**
     *
     * @param lugar
     */
    public void verPgWeb(Lugar lugar)
    {
        actividad.startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(lugar.getUrl())));
    }
    /**
     *
     * @param lugar
     */
    public final void verMapa(Lugar lugar)
    {
        double lat = lugar.getPosicion().getLatitud();
        double lon = lugar.getPosicion().getLongitud();
        Uri uri = lugar.getPosicion() != GeoPunto.SIN_POSICION
                ? Uri.parse("geo:" + lat + ',' + lon)
                : Uri.parse("geo:0,0?q=" + lugar.getDireccion());
        actividad.startActivity(new Intent("android.intent.action.VIEW", uri));
    }
    // FOTOGRAFÍAS...........................................................

    /**
     * Actualiza la fotografía de la vista del lugar
     * @param view
     */
    public void ponerDeGaleria(View view)//view es  dummy, no se usa
    {
        String action;
        if (android.os.Build.VERSION.SDK_INT >= 19)
        { // API 19 - Kitkat
            action = Intent.ACTION_OPEN_DOCUMENT;
        }
        else
        {
            action = Intent.ACTION_PICK;
        }
        //Instancia el intent
        Intent intent = new Intent(action,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //Configura el intent
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        //Lanza el intent
        actividad.startActivityForResult(intent, RESULTADO_GALERIA);
    }

    /**
     * Modifica el atributo "foto" de lugar escribiendo el URI
     * de una imagen en el string "foto"
     *
     * @param pos posición del lugar en la lista de LugaresLista
     * @param uri URI de la foto
     * @param foto ImageView con la foto
     */
    public void ponerFoto(int pos, String uri, ImageView foto)
    {
        Lugar lugar = lugares.elemento(pos);//Apunta al lugar
        lugar.setFoto(uri);//Le pone la foto que nos han pasado en imageVIew
        visualizarFoto(lugar, foto,0);

    }
    /**
     * Muestra la foto en la vista del lugar. Si "foto" existe la muestra
     * si no muestra una foto por defecto y si no existe no muestra nada (nill)
     * @param lugar Lugar visualizado
     * @param fotoFondo Foto de fondo a visualizar en el lugar
     */
    public void visualizarFoto(Lugar lugar, ImageView fotoFondo, int id)
    {
        if (lugar.getFoto() != null && !lugar.getFoto().isEmpty())
        {
            fotoFondo.setImageURI(Uri.parse(lugar.getFoto()));
            Uri uri = Uri.parse(lugar.getFoto());
            fotoFondo.setImageBitmap(reduceBitmap(actividad, lugar.getFoto(), 512,   512));
        }
        else
        {
            //Si no tenemos una foto que visualizar ponemos una por defect
            if(id!=0) fotoFondo.setId(id);//Le hemos pasado el contexto
            else fotoFondo.setImageBitmap(null);
        }
    }

    /**
     *
     * @param contexto actividad
     * @param uri String con la dirección de la foto
     * @param maxAncho máximo ancho de salida
     * @param maxAlto máximo alto de salida
     * @return Un bitmap o null
     */
    private Bitmap reduceBitmap(Context contexto, String uri, int maxAncho, int maxAlto)
    {
        try
        {
            InputStream input = null;
            Uri u = Uri.parse(uri);//Convierte string en uri
            if (u.getScheme().equals("http") || u.getScheme().equals("https"))
            {
                input = new URL(uri).openStream();
            }
            else
            {
                input = contexto.getContentResolver().openInputStream(u);
            }
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = (int) Math.max(
                    Math.ceil(options.outWidth / maxAncho),
                    Math.ceil(options.outHeight / maxAlto));
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(input, null, options);
        }
        catch (FileNotFoundException e)
        {
            makeText(contexto, "Fichero/recurso de imagen no encontrado", LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            makeText(contexto, "Error accediendo a imagen", LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @param codidoSolicitud para identificar el retorno de la acción
     * @return uri de una foto tomada con la cámara p null si no la encuentra
     */
    public Uri tomarFoto(int codidoSolicitud)
    {
        try //Intenta encontrar un fichero de foto reciente
        {
            Uri uriUltimaFoto;
            //Busca un fichero llamado img_hora_en_segundos.jpg del directorio de fotos
            File file = File.createTempFile(
                    "img_" + (System.currentTimeMillis()/ 1000), ".jpg" ,
                    actividad.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            if (Build.VERSION.SDK_INT >= 24)
            {
                uriUltimaFoto = FileProvider.getUriForFile(
                actividad, "com.example.mislugares.fileProvider", file);
            }
            else
            {
                uriUltimaFoto = Uri.fromFile(file);
            }
            Intent intent   = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra (MediaStore.EXTRA_OUTPUT, uriUltimaFoto);
            actividad.startActivityForResult(intent, codidoSolicitud);
            return uriUltimaFoto;
        }
        catch (IOException ex)
        {
            makeText(actividad, "Error al crear fichero de imagen",
                    LENGTH_LONG).show();
            return null;
        }
    }

}
/*******************************************************************************
 * FIN
 * *****************************************************************************/


