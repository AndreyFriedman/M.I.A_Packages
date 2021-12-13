package com.example.miapackages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


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
    public void register(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
    public void onLogin(View view){
        String document = "clients";
        searchInDataSet(document);
        init();
    }

    EditText userName;
    EditText password;

    public void init(){
        userName =findViewById(R.id.userName);
        CharSequence str1 = userName.getText().toString();

        System.out.println("11112 " + str1);
        password =findViewById(R.id.password);
        CharSequence str2 = password.getText().toString();

        System.out.println("11112 " + str2);
    }

    public boolean searchInDataSet(String document){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference users = db.collection("users").document(document);
        final Map<String, Object>[] client = new Map[]{new HashMap<>()};
        users.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Map<String,Object> cli = documentSnapshot.getData();
                    cli.put("iaga","klw");
                    for ( String key : cli.keySet()) {
                        CharSequence userS = userName.getText().toString();
                        System.out.println("1111111111111111111 " + key);
                        if(userS.equals(key)){
                            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + key);
                            CharSequence passwordS = password.getText().toString();
                            if(cli.get(key).equals(passwordS)){
                                loginsucces();//return true;
                            }
                            else{
                                loginfailure();
                            }
                        }
                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
        return false;
    }

    public void loginsucces(){
        Intent intent = new Intent(this, Client.class);
        startActivity(intent);
    }
    public void loginfailure(){
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        password.setError("You are not client please register");
    }




}