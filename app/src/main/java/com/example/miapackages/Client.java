package com.example.miapackages;

import android.content.Context;
import android.content.Intent;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client extends AppCompatActivity {
    ArrayList<Item> items = new ArrayList<>();
    Data d = new Data();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        // Initialize contacts
        items = createContactsList();
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
        startActivity(intent);
    }
}



class ContactsAdapter extends
        RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    Data d = new Data();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

            this.nameT = itemView.findViewById(R.id.item_name);
            this.amountT = itemView.findViewById(R.id.item_amount);
            this.priceT = itemView.findViewById(R.id.item_price);
            this.suppT = itemView.findViewById(R.id.item_supp);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) { //when clicks on item
            int position = getAdapterPosition(); // gets item position
            System.out.println(items.get(position).getName());
            String n = items.get(position).getName();
            int amm = items.get(position).getAmount();
            if(amm > 0){
                items.get(position).setAmount(amm-1);

                Map<String, Object> m = new HashMap<>();
                m.put("amount", items.get(position).getAmount());
                m.put("price", items.get(position).getPrice());
                m.put("supplier", items.get(position).getSupplier());

                d.setDoc(db,"items",items.get(position).getName(),m);
                d.addDocCart(db,"i@g.c", items.get(position).getName(), items.get(position).getPrice(), items.get(position).getSupplier());
            }
            else{
                System.out.println("there is no more items");

                Map<String, Object> m = new HashMap<>();
                m.put("amount", items.get(position).getAmount());
                m.put("price", items.get(position).getPrice());
                m.put("supplier", items.get(position).getSupplier());

                d.setDoc(db,"items",items.get(position).getName(),m);
            }



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


