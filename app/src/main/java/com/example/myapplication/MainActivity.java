package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onManager(View view){
        Intent intent = new Intent(this, Manager.class);
        startActivity(intent);
    }
    public void onClient(View view){
        Intent intent = new Intent(this, Client.class);
        startActivity(intent);
    }
    public void onDriver(View view){
        Intent intent = new Intent(this, Driver.class);
        startActivity(intent);
    }
}