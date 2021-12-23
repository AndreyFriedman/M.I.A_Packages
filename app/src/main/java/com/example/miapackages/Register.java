//package com.codebrainer.registration.registration;
package com.example.miapackages;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    Data d = new Data();
    EditText clientUserName;
    EditText clientPassword;
    EditText address;
    EditText phone;
    Button register;

    String userName;
    String password;
    String cAddress;
    String cPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    public void onRegister(View view){
        clientUserName =(EditText) findViewById(R.id.clientUserName);
        clientPassword = (EditText)findViewById(R.id.clientPassword);
        address = (EditText) findViewById(R.id.ClientAddress);
        phone = (EditText) findViewById(R.id.ClientPhone);


        if(checkDataEntered() == true){
            userName = clientUserName.getText().toString();
            password = clientPassword.getText().toString();
            cAddress = address.getText().toString();
            cPhone = phone.getText().toString();
            HashMap<String,String> client = new HashMap<String,String>();
            HashMap<String, Object> clientDetails = new HashMap<>();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            client.put("userName", userName);
            client.put("password", password);
            client.put("Address", cAddress);
            client.put("Phone", cPhone);
            clientDetails.put(userName,client);
            DocumentReference users = db.collection("users").document("clients");
            CollectionReference cart = db.collection("cart");//.document("clients");
            users.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>(){
                @Override
                public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()) {
                        Map<String, Object> cli = documentSnapshot.getData();
                        for (Map.Entry<String, Object> entry : cli.entrySet()) {
                            Map<String, Object> detClient = (Map<String, Object>) entry.getValue();
                            HashMap<String,String> detClient2 = new HashMap<String,String>();
                            for (Map.Entry<String, Object> e : detClient.entrySet()) {
                                detClient2.put(e.getKey(),e.getValue().toString());
                            }
                            clientDetails.put(entry.getKey(),detClient2);
                        }
                    }
                    users.set(clientDetails);
                    //cart.add(userName);
                    HashMap<String,Object> temp = new HashMap<>();
                    d.setDoc(db,"cart",userName,temp);

                }
            });
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches());

    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean checkDataEntered() {
        if (isEmpty(clientUserName)) {

            clientUserName.setError("You must enter eMail to register!");
            return false;
        }
        if (!isEmail(clientUserName)){
            clientUserName.setError("You must enter eMail to register!");
            return false;
        }
        if (isEmpty(clientPassword)) {
            clientPassword.setError("Password is required!");
            return false;
        }

        return true;
    }
}
