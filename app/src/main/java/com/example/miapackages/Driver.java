package com.example.miapackages;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class Driver extends AppCompatActivity {
    ArrayList<Order> orders = new ArrayList<>();
    Data d = new Data();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String clientName;
    String address;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        Intent intent = getIntent();
//        clientName = intent.getStringExtra("clientName");
//        address = intent.getStringExtra("Address");
//        phone = intent.getStringExtra("Phone");
//        System.out.println(clientName+"@@@@@@2@22@");
        // Initialize contacts
        orders = createContactsList();
    }

    protected void onCreate2(ArrayList<Order> orders){
        // Lookup the recyclerview in activity layout

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rcView);
        // Create adapter passing in the sample user data
        ContactsAdapter2 adapter = new ContactsAdapter2(orders, 1, clientName);
            // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
                // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!

    }

    protected ArrayList<Order> createContactsList() {
        db.collection("package")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String address="";
                            String phone="";
                            String items="";
                            String amounts="";

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId() + " => " + document.getData());
                                Map<String,Object> doc = document.getData();
                                if(!doc.isEmpty()){
                                    for(Map.Entry<String, Object> entry : doc.entrySet()){
                                        if(entry.getKey().toString().equals("Address")){
                                            if(entry.getValue() != null) {
                                                address = entry.getValue().toString();
                                            }
                                            else{
                                                address = "address error";
                                            }
                                        }
                                        if(entry.getKey().toString().equals("Order")){
                                            System.out.println(entry.getKey().toString() + entry.getValue());
                                            Map<String,Object> itemsOrder = (Map<String,Object>)entry.getValue();
                                            for(Map.Entry<String, Object> e : itemsOrder.entrySet()){
                                                System.out.println("e:" + e.getKey());
                                                Map<String,Object> itemD = (Map<String,Object>)e.getValue();
                                                for(Map.Entry<String, Object> en : itemD.entrySet()){
                                                    if(en.getKey().equals("amount")){
                                                        amounts += en.getValue().toString() + "\n";
                                                    }
                                                }
                                                items += e.getKey() + "\n";

                                            }
                                        }
                                        if(entry.getKey().toString().equals("Phone")) {
                                            phone = entry.getValue().toString();
                                        }

                                    }
                                }
                                System.out.println("items:"+ items);
                                System.out.println("amounts:"+ amounts);
                                System.out.println("phone:"+ phone);
                                System.out.println("address:"+ address);
                                orders.add(new Order(document.getId(),phone, address, items,amounts));
                                address="";
                                phone="";
                                items="";
                                amounts="";
                            }
                            onCreate2(orders);
                        } else {
                            System.out.println("Error getting documents: " + task.getException());
                        }
                    }
                });
        return orders;
    }

}