package com.example.miapackages;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ContactsAdapter extends
        RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    Data d = new Data();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameT,amountT,priceT,suppT,message_buttonT;
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
            this.suppT = itemView.findViewById(R.id.item_desc);
            this.message_buttonT = itemView.findViewById(R.id.message_button);

            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) { //when clicks on item
            int position = getAdapterPosition();// gets item position
            int price = items.get(position).getPrice();
            String name = items.get(position).getName();
            int amm = items.get(position).getAmount();
            System.out.println(amm);
            if (mazav == 1) {

                System.out.println(amm);
                if (amm > 0) {
                    amm=amm-1;
                    items.get(position).setAmount(amm);

                    Map<String, Object> m = new HashMap<>();
                    m.put("amount", amm);
                    m.put("price", price);
                    m.put("description", items.get(position).getDescription());
                    System.out.println("addddd item");
                    d.setDoc(db, "items", name, m);
                    d.addDocCart(db, clientName, name, price, items.get(position).getDescription(), 1);
                } else {
                    System.out.println("there is no more items");

                    Map<String, Object> m = new HashMap<>();
                    m.put("amount", amm);
                    m.put("price", price);
                    m.put("description", items.get(position).getDescription());

                    d.setDoc(db, "items", name, m);
                }
            }

            if (mazav == 2){


                items.get(position).setAmount(amm + 1);

                Map<String, Object> m = new HashMap<>();
                m.put("amount", amm);
                m.put("price", price);
                m.put("description", items.get(position).getDescription());

                d.addDocItem(db,name,1,price,items.get(position).getDescription());
                d.addDocCart(db, clientName, name, price, items.get(position).getDescription(), 2);
            }

        }
    }
    private List<Item> items;
    private int mazav;
    private String clientName;

    // Pass in the contact array into the constructor
    public ContactsAdapter(ArrayList<Item> contacts, int i, String clientNameTemp) {
        items = contacts;
        mazav = i;
        clientName = clientNameTemp;
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
        TextView tot = holder.message_buttonT;

        name.setText(contact.getName());
        amount.setText(Integer.toString(contact.getAmount()));
        price.setText(Integer.toString(contact.getPrice()));
        supp.setText(contact.getDescription());
        if (mazav == 2)
            tot.setText(Integer.toString(contact.getTotAmount()));
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

}


