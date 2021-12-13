//package com.codebrainer.registration.registration;
package com.example.miapackages;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText clientUserName;
    EditText clientPassword;
    EditText address;
    EditText email;
    Button register;

    String userName;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    public void onRegister(View view){
        clientUserName =(EditText) findViewById(R.id.clientUserName);
        clientPassword = (EditText)findViewById(R.id.clientPassword);

        if(checkDataEntered() == true){
            userName = clientUserName.getText().toString();
            password = clientPassword.getText().toString();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, String> client = new HashMap<>();
            client.put(userName, password);
            db.collection("users").document("clients").set(client);
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
