package com.example.comunicacionactividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public EditText editTextNombre;
    public TextView editTextResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextNombre = findViewById(R.id.tNombre);
        editTextResultado=findViewById(R.id.tResultado);
    }
    //onClick del botón
    public void verificar(View view )
    {
        //Lee el nombre del usuario
        Editable edNombre = editTextNombre.getText();
        String nombre=edNombre.toString();
        //creamos el Intent intent para lanzar segunda_actividad
        Intent intent = new Intent(this,segundaActividad.class);
        //Ponemos la información
        intent.putExtra("usuario", nombre);
        //Lanzamos segunda actividad
        startActivityForResult(intent, 1234);
    }
    //Método para recibir la información devuelta
    @Override protected void onActivityResult
    (int requestCode, int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234 && resultCode == RESULT_OK)
        {
            //Ahora leemos la infommación
            String respuesta = data.getExtras().getString("resultado");
            //Analizamos la información
            if (respuesta.equals("sin nombre")) return;
            //Escribimos el resultado
            else
            {
                editTextResultado.setText(respuesta);
            }
        }
    }
}
