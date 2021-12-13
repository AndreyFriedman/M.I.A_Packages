package com.example.miapackages;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import java.util.List;

public class Client extends AppCompatActivity {
    ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        // Initialize contacts
        createContactsList();
    }

    protected void onCreate2(ArrayList<Item> items){
        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        // Create adapter passing in the sample user data
        ContactsAdapter adapter = new ContactsAdapter(items);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
    }


    public ArrayList<Item> createContactsList() {
        ArrayList<Item> items = new ArrayList<Item>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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

    public void onAddCart(View view) {

    }
}

class Item {
    private int amountS = 0;
    private int priceS = 0;
    private String nameS = "abc";
    private String supplierS = "abc";

    public Item(String name, String supp, int price, int amount) {
        nameS = name;
        supplierS = supp;
        priceS = price;
        amountS = amount;
    }

    public String getName() {
        return nameS;
    }
    public String getSupplier() {
        return supplierS;
    }
    public int getPrice() {
        return priceS;
    }
    public int getAmount() {
        return amountS;
    }
}

class ContactsAdapter extends
        RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameT,amountT,priceT,suppT;
        public Button messageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameT = itemView.findViewById(R.id.item_name);
            amountT = itemView.findViewById(R.id.item_amount);
            priceT = itemView.findViewById(R.id.item_price);
            suppT = itemView.findViewById(R.id.item_supp);

        }
    }
    private List<Item> items;

    // Pass in the contact array into the constructor
    public ContactsAdapter(ArrayList<Item> contacts) {
        items = contacts;
    }

    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.items, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Item contact = items.get(position);

        // Set item views based on your views and data model
        TextView name = holder.nameT;
        TextView amount = holder.amountT;
        TextView price = holder.priceT;
        TextView supp = holder.suppT;

        name.setText(contact.getName());
        amount.setText(Integer.toString(contact.getAmount()));
        price.setText(Integer.toString(contact.getPrice()));
        supp.setText(contact.getSupplier());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return items.size();
    }
}


