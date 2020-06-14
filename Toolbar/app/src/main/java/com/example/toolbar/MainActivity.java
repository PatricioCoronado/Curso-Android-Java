package com.example.toolbar;
import android.view.Menu;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);//Constructor de AppCompatActivity
        setContentView(R.layout.activity_main);
        //Toolbar instancianción y soporte de gestión para que actúe como actionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }//onCreate
    //--------MENU-------------------------------------------------------------
    @Override    public boolean onCreateOptionsMenu(Menu menu) //Infla el menú
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) //Recibe la selección del menú
    {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        // TODO Más opociones del main menún aquí
        //if (id == R.id.action_settings){ TODO tu código aquí; return true;}
        return super.onOptionsItemSelected(item);
    }
}