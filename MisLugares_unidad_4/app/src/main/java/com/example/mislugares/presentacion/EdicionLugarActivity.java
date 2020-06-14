package com.example.mislugares.presentacion;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mislugares.Aplicacion;
import com.example.mislugares.R;
import com.example.mislugares.casos_uso.CasosUsoLugar;
import com.example.mislugares.datos.RepositorioLugares;
import com.example.mislugares.modelo.Lugar;
import com.example.mislugares.modelo.TipoLugar;

import java.text.DateFormat;
import java.util.Date;
import android.widget.RatingBar;


public class EdicionLugarActivity extends AppCompatActivity {
    private RepositorioLugares lugares;
    private CasosUsoLugar usoLugar;
    private Lugar lugar;//Aquí pongo los datos del lugar que lea del repositorio
    private int pos;//Posición en el repositorio del lugar a cambiar
    private EditText nombre;
    private Spinner  tipo;
    private EditText direccion;
    private EditText telefono;
    private EditText url;
    private EditText comentario;

    //No tiene constructor, lanza una activity
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Carga el layout vista_lugar
        setContentView(R.layout.edicion_lugar);
        //La vista llamadora me envía la posición en "pos"
        Bundle extras = getIntent().getExtras();
        pos = extras.getInt("pos", 0);
        //Instancia el repositorio
        lugares = ((Aplicacion) getApplication()).lugares;
        //Instancia la clase que aporta métodos para manejar la clase Lugar
        usoLugar = new CasosUsoLugar(this, lugares);
        //Leo los datos del lugar concreto
        lugar = lugares.elemento(pos);
        //Instancia el spinner
        tipo = findViewById(R.id.tipo);
        //adaptador es un array que permite acceder al enum tipo lugar y
        //leer los emunerados
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, TipoLugar.getNombres());
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo.setAdapter(adaptador);//Pon los datos del adaptador en el spinner
        tipo.setSelection(lugar.getTipo().ordinal());//Muestra el tipo correspondiente
        actualizaVistas();
    }
    // MENU
    @Override public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_editar, menu);
        return true;
    }
    // SELECCION ITEM MENU
    @Override public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.accion_cancelar:
                finish();
                return true;
            case R.id.accion_guardar:
            guardar();
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
        //Actualiza la vista mostrada
        public void actualizaVistas()
        {
            //Nombre
            nombre = findViewById(R.id.nombre);
            nombre.setText(lugar.getNombre());
            //Dirección
             direccion = findViewById(R.id.direccion);
             direccion.setText(lugar.getDireccion());
            //Teléfono
             telefono = findViewById(R.id.telefono);
             telefono.setText(Integer.toString(lugar.getTelefono()));
            // url
             url = findViewById(R.id.url);
             url.setText(lugar.getUrl());
            // comentario
             comentario = findViewById(R.id.comentario);
             comentario.setText(lugar.getComentario());
        }
        private void guardar()
        {
            lugar.setNombre(nombre.getText().toString());
            lugar.setTipo(TipoLugar.values()[tipo.getSelectedItemPosition()]);
            lugar.setDireccion(direccion.getText().toString());
            lugar.setTelefono(Integer.parseInt(telefono.getText().toString()));
            lugar.setUrl(url.getText().toString());
            lugar.setComentario(comentario.getText().toString());
            usoLugar.guardar(pos, lugar);
            finish();
        }
    }
