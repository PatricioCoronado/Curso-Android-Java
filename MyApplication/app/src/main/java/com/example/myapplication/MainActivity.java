package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d("HolaMundo","Entramos en onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
