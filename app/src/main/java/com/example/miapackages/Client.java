package com.example.miapackages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

public class Client extends AppCompatActivity {
    ArrayList<Item> items = new ArrayList<>();
    Data d = new Data();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String clientName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Intent intent = getIntent();
        clientName = intent.getStringExtra("clientName");
        // Initialize contacts
        items = createContactsList();
    }

    protected void onCreate2(ArrayList<Item> items){
        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        // Create adapter passing in the sample user data
        ContactsAdapter adapter = new ContactsAdapter(items,1, clientName);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
        
    }

    protected ArrayList<Item> createContactsList() {
        db.collection("items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId() + " => " + document.getData());
                                items.add(new Item(document.getId(), (String) document.get("supplier"), (int) ((Long) document.get("price") + 0), (int) ((Long) document.get("amount") + 0)));
                            }
                            onCreate2(items);
                        } else {
                            System.out.println("Error getting documents: " + task.getException());
                        }
                    }
                });
        return items;
    }

    public void onCart(View view){
        Intent intent = new Intent(this, Cart.class);
        intent.putExtra("clientName",clientName);
        startActivity(intent);
    }
}
