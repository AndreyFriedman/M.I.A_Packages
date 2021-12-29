package com.example.miapackages;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart extends AppCompatActivity {
    ArrayList<Item> items = new ArrayList<>();
    Data d = new Data();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String clientName;
    int totPriceAll;
    String address;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Intent intent = getIntent();
        clientName = intent.getStringExtra("clientName");
        address = intent.getStringExtra("Address");
        phone = intent.getStringExtra("Phone");
        System.out.println("11111111"+ clientName);
        items = createContactsList();

    }

    protected void onCreate2(ArrayList<Item> items){
        items.toString();
        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        // Create adapter passing in the sample user data
        ContactsAdapter adapter = new ContactsAdapter(items, 2, clientName);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!

    }

    protected ArrayList<Item> createContactsList() {
        totPriceAll = 0;
        DocumentReference docRef = db.collection("cart").document(clientName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> maps = new HashMap<>();
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.getData());
                        document.getData().forEach((key, value)-> maps.put(key,value));
                        System.out.println(maps.size()+"88888888888888");
                        System.out.println(maps.toString()+"88888888888888");
                        maps.forEach((key, value)->{
                                    String pr;
                                    pr = maps.get(key).toString();
                                    int amountStart = pr.indexOf("amount=") + 7;
                                    int amountEnd = pr.indexOf(", price");
                                    int priceStart = pr.indexOf("price=") + 6;
                                    int priceEnd = pr.indexOf(", supplier");
                                    int supplierStart = pr.indexOf("supplier=") + 9;
                                    int supplierEnd = pr.indexOf(", totPrice");
                                    int totPriceStart = pr.indexOf("totPrice=") + 9;
                                    int totPriceEnd = pr.indexOf("}");


//                                    System.out.println(maps.toString());
//                                    System.out.println(maps.get(key).toString());
//                                    System.out.println(key);
//                                    System.out.println((pr.substring(supplierStart, supplierEnd)));
//                                    System.out.println(Integer.parseInt(pr.substring(priceStart, priceEnd)));
//                                    System.out.println(Integer.parseInt(pr.substring(amountStart, amountEnd)));
//                                    System.out.println(Integer.parseInt(pr.substring(totPriceStart, totPriceEnd)));
                                    int pp=Integer.parseInt(pr.substring(priceStart, priceEnd));
                                    items.add(new Item(key,(pr.substring(supplierStart, supplierEnd)),pp, Integer.parseInt(pr.substring(amountStart, amountEnd)),Integer.parseInt(pr.substring(totPriceStart, totPriceEnd))));
                                    System.out.println("__________________________________________________________");
                                    totPriceAll += Integer.parseInt(pr.substring(totPriceStart, totPriceEnd));
                                });

                        onCreate2(items);
                    } else {
                        System.out.println("No such document");
                    }

                } else {
                    System.out.println("Error getting documents: " + task.getException());
                }
            }
        });
        return items;
    }

    public void onClient(View view) {
        Intent intent = new Intent(this, Client.class);
        intent.putExtra("clientName",clientName);
        startActivity(intent);
    }
    public void onPay(View view) {
        Intent intent = new Intent(this, Pay.class);
        intent.putExtra("clientName",clientName);
        intent.putExtra("Address",address);
        intent.putExtra("Phone",phone);

        intent.putExtra("tot",totPriceAll);
        startActivity(intent);
    }

}
