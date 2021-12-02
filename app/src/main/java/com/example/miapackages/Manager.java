package com.example.miapackages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.Thread;
import java.lang.Object;
import java.util.HashMap;
import java.util.Map;

public class Manager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String prodName = "ball";
        prodManagment(db, prodName, 10, 50, "nike");

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
                        System.out.println("Error adding document" + e);
                    }
                });
    }
}