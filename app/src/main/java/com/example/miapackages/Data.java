package com.example.miapackages;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Data extends AppCompatActivity {
    //HashMap<String,Object> userData = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
    }

    protected void addDocItem(FirebaseFirestore db, String name, int amount, int price, String supp){

        DocumentReference docRef = db.collection("items").document(name);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> prod = new HashMap<>();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.getData());
                        prod.put("amount", (int) ((Long)document.get("amount") + amount));
//                        prod.put("price", price);
//                        prod.put("supplier", supp);
//                        setDoc(db,"items",name,prod);
                    } else {
                        System.out.println("No such document");
                        prod.put("amount", amount);
//                        prod.put("price", price);
//                        prod.put("supplier", supp);
//                        setDoc(db,"items",name,prod);
                    }
                    prod.put("price", price);
                    prod.put("supplier", supp);
                    setDoc(db,"items",name,prod);
                } else {
                    System.out.println( "get failed with " + task.getException());
                }
            }
        });
    }

    protected void addDocCart(FirebaseFirestore db, String userName, String name, int price, String supplier, int mazav){

        DocumentReference docRef = db.collection("cart").document(userName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> maps = new HashMap<>();
                    Map<String, Object> prod = new HashMap<>();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.getData());

                        //maps.put(userName,document.getData());
                        document.getData().forEach(
                                (key, value)
                                        -> maps.put(key,value));


                        String pr;
                        if(document.getData().get(name) == null){
                            prod.put("amount", 1);
                            prod.put("price", price);
                            prod.put("supplier", supplier);
                            prod.put("totPrice", price);
                        }
                        else {
                            pr = document.getData().get(name).toString();
                            int amountStart = pr.indexOf("amount=") + 7;
                            int amountEnd = pr.indexOf(", supplier=");
                            int priceStart = pr.indexOf("price=") + 6;
                            int priceEnd = pr.indexOf(", totPrice");
                            int supplierStart = pr.indexOf("supplier=") + 9;
                            int supplierEnd = pr.indexOf(", price");
                            int totPriceStart = pr.indexOf("totPrice=") + 9;
                            int totPriceEnd = pr.indexOf("}");
                            if (mazav == 1){
                                prod.put("amount", Integer.parseInt(pr.substring(amountStart, amountEnd)) + 1);
                                prod.put("price", Integer.parseInt(pr.substring(priceStart, priceEnd)));
                                prod.put("supplier", (pr.substring(supplierStart, supplierEnd)));
                                prod.put("totPrice", Integer.parseInt(pr.substring(totPriceStart, totPriceEnd)) + Integer.parseInt(pr.substring(priceStart, priceEnd)));
                            }
                            if (mazav == 2){
                                prod.put("amount", Integer.parseInt(pr.substring(amountStart, amountEnd)) - 1);
                                prod.put("price", Integer.parseInt(pr.substring(priceStart, priceEnd)));
                                prod.put("supplier", (pr.substring(supplierStart, supplierEnd)));
                                prod.put("totPrice", Integer.parseInt(pr.substring(totPriceStart, totPriceEnd)) - Integer.parseInt(pr.substring(priceStart, priceEnd)));

                            }




                        }
                        maps.put(name, prod);
                        setDoc(db,"cart",userName,maps);
                    } else {
                        System.out.println("No such document");

                    }
                } else {
                    System.out.println( "get failed with " + task.getException());
                }
            }
        });
    }


    protected void setDoc(FirebaseFirestore db,String col, String doc, Map<String, Object> prod){

        db.collection(col).document(doc).set(prod)
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
    protected void delField(FirebaseFirestore db, String col, String doc, Map<String, Object> updates){

        DocumentReference docRef = db.collection(col).document(doc);
        docRef.update(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error deleting document" + e);
                    }
                });
    }
    protected void delAllFields(FirebaseFirestore db, String col, String doc, Map<String, Object> updates){

        DocumentReference docRef = db.collection(col).document(doc);
        docRef.set(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error deleting document" + e);
                    }
                });
    }


    protected void delDoc(FirebaseFirestore db, String col, String doc){
        db.collection(col).document(doc)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println( "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println( "Error deleting document" + e);
                    }
                });
    }

    protected void hashData(FirebaseFirestore db,String coll,String doc,String pac,String address,String phone){
        DocumentReference extract = db.collection(coll).document(doc);
        extract.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    HashMap<String, Object> data = new HashMap<String, Object>();
                    data = (HashMap<String, Object>) documentSnapshot.getData();
                    int i = 0;
                    for (Map.Entry<String, Object> entry : data.entrySet()) {
                        System.out.println(i+" "+entry.getKey()+" "+ entry.getValue());
                        i++;
                    }
                    HashMap<String,Object> pack = new HashMap<>();
                    pack.put("Order",data);
                    pack.put("Address",address);
                    pack.put("Phone",phone);
                    setDoc(db,pac,doc,pack);
                    HashMap<String,Object> empty = new HashMap<>();
                    delAllFields(db,"cart",doc,empty);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Data.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}