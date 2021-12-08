//package com.codebrainer.registration.registration;
package com.example.miapackages;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

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
        address =(EditText) findViewById(R.id.address);
        email = (EditText)findViewById(R.id.email);

        if(checkDataEntered())
            userName = clientUserName.getText().toString();
            password = clientPassword.getText().toString();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, String> client = new HashMap<>();
            client.put(userName, password);
            db.collection("users").document("clients").set(client, SetOptions.merge());
            Intent intent = new Intent(this, Client.class);
            startActivity(intent);
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean checkDataEntered() {
        if (isEmpty(clientUserName)) {
            Toast t = Toast.makeText(this, "You must enter first name to register!", Toast.LENGTH_SHORT);
            t.show();
        }

        if (isEmpty(clientPassword)) {
            clientPassword.setError("Last name is required!");
        }

        if (isEmail(email) == false) {
            email.setError("Enter valid email!");
        }
        return !isEmpty(clientPassword)&&(!isEmpty(clientUserName))&&isEmail(email);
    }
}
