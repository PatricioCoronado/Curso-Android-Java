package com.example.mislugares.presentacion;
import android.app.Activity;
import android.os.Bundle;

import com.example.mislugares.presentacion.PreferenciasFragment;

public class PreferenciasActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Manejador de fragment que inicia una transacción de reemplazo de fragmento
        getFragmentManager()
        .beginTransaction()//Comienza la transacción
        .replace(android.R.id.content, new PreferenciasFragment())//Introduce nuestro fragment
        .commit();//Ejecuta
    }
}
