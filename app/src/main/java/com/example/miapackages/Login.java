package com.example.miapackages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void onLogin(View view){
        String document1 = "clients";
        String document2 = "drivers";
        String document3 = "managers";
        init();
        if (typeUser.equals("client")) {
            searchInDataSet(document1);
        }
        if (typeUser.equals("driver")) {
            searchWorkers(document2);
        }
        if (typeUser.equals("manager")) {
            searchWorkers(document3);
        }
        if (typeUser.equals("no type user selected")) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            password.setError("You need to choose type!");
        }
    }

    EditText userName;
    EditText password;
    String typeUser;
    String userString;

    public void init(){
        Spinner type = (Spinner) findViewById(R.id.types);
        typeUser = String.valueOf(type.getSelectedItem());
        System.out.println("11112 " + typeUser);
        userName =findViewById(R.id.userName);

        CharSequence str1 = userName.getText().toString();
        userString = (String)str1;
        System.out.println("11112 " + str1);
        System.out.println("uS11112 " + userString);
        password =findViewById(R.id.password);
        CharSequence str2 = password.getText().toString();

        System.out.println("11112 " + str2);
    }
    //String userString =

    public boolean searchInDataSet(String document){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference users = db.collection("users").document(document);
        users.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Map<String,Object> cli = documentSnapshot.getData();
                    for (Map.Entry<String, Object> entry : cli.entrySet()) {
                        CharSequence userN = userName.getText().toString();
                        if (entry.getKey().equals(userN)){
                            Map<String, Object> client = (Map<String, Object>) entry.getValue();
                            for (Map.Entry<String, Object> e : client.entrySet()) {
                                if (e.getKey().equals("password")) {
                                    CharSequence passwordS = password.getText().toString();
                                    if(e.getValue().toString().equals(passwordS)){
                                        loginSucces();//return true;
                                    }
                                    else{
                                        loginFailure();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
        return false;
    }
    public boolean searchWorkers(String document){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference users = db.collection("users").document(document);
        users.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Map<String,Object> cli = documentSnapshot.getData();
                    for (Map.Entry<String, Object> entry : cli.entrySet()) {
                        CharSequence userN = userName.getText().toString();
                        CharSequence passwordS = password.getText().toString();
                        if (entry.getKey().equals(userN) && entry.getValue().equals(passwordS)) {
                            loginSucces();//return true;
                        } else {
                            loginFailure();
                        }
                    }
                }
            }
        });
        return false;
    }


    public void loginSucces() {
        if (typeUser.equals("client")) {
            Intent intent = new Intent(this, Client.class);
            intent.putExtra("clientName",userString);

            startActivity(intent);
        }
        if (typeUser.equals("driver")) {
            Intent intent = new Intent(this, Driver.class);
            startActivity(intent);
        }
        if (typeUser.equals("manager")) {
            Intent intent = new Intent(this, Manager.class);
            startActivity(intent);
        }
    }
    public void loginFailure(){
        if (typeUser.equals("client")) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            password.setError("Maybe you need to register?");
        }
        if (typeUser.equals("driver")) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            password.setError("Probably manager mistake");
        }
        if (typeUser.equals("manager")) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            password.setError("You sure that you manager?");
        }
    }

    public String getClintName(){
        return userString;
    }
}
