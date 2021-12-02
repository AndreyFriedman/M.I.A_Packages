package com.example.miapackages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import android.widget.EditText;
import java.util.HashMap;
import java.util.Map;

public class Manager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

    }

    public void itemClick(View view){
        EditText name = (EditText)findViewById(R.id.editTextName);
        String nameS = name.getText().toString();
        EditText amount = (EditText)findViewById(R.id.editTextAmount);
        int amountS = Integer.valueOf(amount.getText().toString());
        EditText price = (EditText)findViewById(R.id.editTextPrice);
        int priceS = Integer.valueOf(price.getText().toString());
        EditText supplier = (EditText)findViewById(R.id.editTextSupplier);
        String supplierS = supplier.getText().toString();

        System.out.println(nameS);
        System.out.println(amountS);
        System.out.println(priceS);
        System.out.println(supplierS);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        prodManagment(db, nameS, amountS, priceS, supplierS);
    }

    protected void prodManagment(FirebaseFirestore db, String name, int amount, int price, String supp){
        isExist(db,name,amount,price,supp);
    }
    protected void isExist(FirebaseFirestore db, String name, int amount, int price, String supp){

        DocumentReference docRef = db.collection("items").document(name);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.getData());
                        addNewProduct(db,name, (int) ((Long)document.get("amount") + amount),price,supp);
                    } else {
                        System.out.println("No such document");
                        addNewProduct(db,name,amount,price,supp);
                    }
                } else {
                    System.out.println( "get failed with " + task.getException());
                }
            }

        });
    }

    protected void addNewProduct(FirebaseFirestore db, String name, int amount, int price, String supp){
        Map<String, Object> prod = new HashMap<>();
        prod.put("amount", amount);
        prod.put("price", price);
        prod.put("supplier", supp);
        db.collection("items").document(name).set(prod)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("DocumentSnapshot added with ID: ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error writing document"+e);
                    }
                });
    }


    public void addDriver(View view)
    {   //get the name and pass of the driver from editText
        EditText driver_neme_view = (EditText) findViewById(R.id.driver_name);
        String driverName = driver_neme_view.getText().toString();
        EditText driver_pass_view = (EditText) findViewById(R.id.driver_password);
        String driverPass = driver_pass_view.getText().toString();
        //add the driver to the data base
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, String> driver = new HashMap<>();
        driver.put(driverName, driverPass);
        db.collection("users").document("drivers").set(driver, SetOptions.merge());
    }
    protected void removeDriver(FirebaseFirestore db , String username)
    {

    }
}