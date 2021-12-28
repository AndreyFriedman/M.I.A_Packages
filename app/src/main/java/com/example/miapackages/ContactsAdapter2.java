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

class ContactsAdapter2 extends RecyclerView.Adapter<ContactsAdapter2.ViewHolder2> {
    Data d = new Data();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<Order> orders;
//    private int mazav;
//    private String clientName;


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameT, phoneT,addressT,itemT, amountT;
        public Button messageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder2(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            this.nameT = itemView.findViewById(R.id.item_name);
            this.phoneT = itemView.findViewById(R.id.item_amount);
            this.addressT = itemView.findViewById(R.id.item_price);
            this.itemT = itemView.findViewById(R.id.item_supp);
            this.amountT = itemView.findViewById(R.id.message_button);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) { //when clicks on item
            int position = getAdapterPosition();// gets item position
            String items = orders.get(position).getItemsS();
            String name = orders.get(position).getIdS();
            String amounts = orders.get(position).getAmounts();
            //System.out.println(amm);
            //            this.nameT = itemView.findViewById(R.id.item_name);
//            this.phoneT = itemView.findViewById(R.id.item_amount);
//            this.addressT = itemView.findViewById(R.id.item_price);
//            this.itemT = itemView.findViewById(R.id.item_supp);
//            this.amountT = itemView.findViewById(R.id.message_button);
//            if (mazav == 1) {
//
//                System.out.println(amm);
//                if (amm > 0) {
//                    items.get(position).setAmount(amm - 1);
//
//                    Map<String, Object> m = new HashMap<>();
//                    m.put("amount", amm);
//                    m.put("price", price);
//                    m.put("supplier", items.get(position).getSupplier());
//
//                    d.setDoc(db, "items", name, m);
//                    d.addDocCart(db, clientName, name, price, items.get(position).getSupplier(), 1);
//                } else {
//                    System.out.println("there is no more items");
//
//                    Map<String, Object> m = new HashMap<>();
//                    m.put("amount", amm);
//                    m.put("price", price);
//                    m.put("supplier", items.get(position).getSupplier());
//
//                    d.setDoc(db, "items", name, m);
//                }
//            }
//
//            if (mazav == 2) {
//
//
//                //items.get(position).setAmount(amm + 1);
//
//                Map<String, Object> m = new HashMap<>();
//                m.put("amount", amm);
//                m.put("price", price);
//                m.put("supplier", items.get(position).getSupplier());
//
//                d.addDocItem(db, name, 1, price, items.get(position).getSupplier());
//                d.addDocCart(db, clientName, name, price, items.get(position).getSupplier(), 2);
//            }

        }
    }

    // Pass in the contact array into the constructor
    public ContactsAdapter2(ArrayList<Order> contacts, int i, String clientNameTemp) {
        orders = contacts;
        //mazav = i;
        String clientName = clientNameTemp;
    }

    @Override
    public ContactsAdapter2.ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.orders, parent, false);

        // Return a new holder instance
        ViewHolder2 viewHolder2 = new ViewHolder2(contactView);
        return viewHolder2;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ContactsAdapter2.ViewHolder2 holder, int position) {
        // Get the data model based on position
        Order contact = orders.get(position);

        // Set item views based on your views and data model
        TextView name = holder.nameT;
        TextView phone = holder.phoneT;
        TextView address = holder.addressT;
        TextView item = holder.itemT;
        TextView amount = holder.amountT;
//            this.nameT = itemView.findViewById(R.id.item_name);
//            this.phoneT = itemView.findViewById(R.id.item_amount);
//            this.addressT = itemView.findViewById(R.id.item_price);
//            this.itemT = itemView.findViewById(R.id.item_supp);
//            this.amountT = itemView.findViewById(R.id.message_button);
        //name.setText(contact.getIdS());
       // amount.setText(contact.getAmounts());
//        price.setText(contact.getPrice());
//        supp.setText(contact.getSupplier());
//        if (mazav == 2)
//            tot.setText(Integer.toString(contact.getTotAmount()));
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return orders.size();
    }
}