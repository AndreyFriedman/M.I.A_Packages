package com.example.miapackages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    EditText userName;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    public void init(){
        userName =(EditText)findViewById(R.id.userName);
        password =(EditText)findViewById(R.id.password);
    }

    public void onManager(View view){
        Intent intent = new Intent(this, Manager.class);
        startActivity(intent);
    }

    public void onClient(View view){
        String document = "clients";
        boolean isClient = searchInDataSet(document);
        if(isClient) {
            Intent intent = new Intent(this, Client.class);
            startActivity(intent);
        }
        else{

        }
    }
    public void onDriver(View view){
        Intent intent = new Intent(this, Driver.class);
        startActivity(intent);
    }
    public void register(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public boolean searchInDataSet(String document){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("Users").document(document).;
//        db.collection("Users");
        DocumentReference users = db.collection("Users").document(document);
        //users.




        return true;
    }


}