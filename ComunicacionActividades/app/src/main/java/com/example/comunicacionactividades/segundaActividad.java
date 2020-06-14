package com.example.comunicacionactividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.content.Intent;
import android.os.Bundle;

public class segundaActividad extends Activity {
    public TextView textPregunta;
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Lee los componentes el layout
        setContentView(R.layout.segunda_actividad);
        //Busca el texto
        textPregunta=findViewById(R.id.tPregunta);
        //Lee el nombre que le envía la artivity main
        Bundle extras = getIntent().getExtras();
        String nombre = extras.getString("usuario");
        if(nombre.equals("")) volverSinNombre();
        //Escribe la pregunta
        String pregunta = "Hola "+nombre+ "¿Aceptas las condiciones?";
        textPregunta.setText(pregunta);
        //Crea un intent para devolver resultados
    }
    //Métodos de la activity
    public void aceptar(View view)
    {
        String respuesta="si";//Esto es lo que se devuelve
        Intent intent = new Intent();
        intent.putExtra("resultado",respuesta);
        setResult(RESULT_OK, intent);
        finish();
    }
    public void rechazar(View view)
    {
        String respuesta="no";//Esto es lo que se devuelve
        Intent intent = new Intent();
        intent.putExtra("resultado",respuesta);
        setResult(RESULT_OK, intent);
        finish();
    }
    private void volverSinNombre()
    {
        String respuesta="sin nombre";//Esto es lo que se devuelve
        Intent intent = new Intent();
        intent.putExtra("resultado",respuesta);
        setResult(RESULT_OK, intent);
        finish();
    }
}
