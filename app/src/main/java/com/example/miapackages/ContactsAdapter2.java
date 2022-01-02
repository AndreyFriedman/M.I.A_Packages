package com.example.miapackages;
import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

class ContactsAdapter2 extends RecyclerView.Adapter<ContactsAdapter2.ViewHolder2> {
    Data d = new Data();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;





    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView phoneT,addressT,itemT, amountT;
        public Button messageButton;
        private Context context;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder2(View itemView, Context context) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.

            super(itemView);
            this.phoneT = itemView.findViewById(R.id.phone_num);
            this.addressT = itemView.findViewById(R.id.address);
            this.itemT = itemView.findViewById(R.id.items);
            this.amountT = itemView.findViewById(R.id.amount);
            this.context = context;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) { //when clicks on item
            int position = getAdapterPosition();// gets item position
            String items = orders.get(position).getItemsS();
            String address = orders.get(position).getAddressS();
            String amounts = orders.get(position).getAmounts();
            String phone = orders.get(position).getPhone();
            String id = orders.get(position).getIdS();

            this.phoneT = itemView.findViewById(R.id.item_amount);
            this.addressT = itemView.findViewById(R.id.item_price);
            this.itemT = itemView.findViewById(R.id.item_desc);
            this.amountT = itemView.findViewById(R.id.message_button);

            Uri uri = Uri.parse("smsto:"+phone);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", "Your order is waiting for you outside your door");
            context.startActivity(intent);

            d.delDoc(db,"package",id);

        }
    }

    private List<Order> orders;
    private int mazav;
    private String clientName;



    // Pass in the contact array into the constructor
    public ContactsAdapter2(ArrayList<Order> contacts, int i, String clientNameTemp) {
        orders = contacts;
        mazav = i;
        clientName = clientNameTemp;
    }

    @Override
    public ContactsAdapter2.ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.orders, parent, false);

        // Return a new holder instance
        ViewHolder2 viewHolder2 = new ViewHolder2(contactView,context);
        return viewHolder2;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ContactsAdapter2.ViewHolder2 holder, int position) {
        // Get the data model based on position
        Order contact = orders.get(position);

        // Set item views based on your views and data model
        TextView phone = holder.phoneT;
        TextView address = holder.addressT;
        TextView item = holder.itemT;
        TextView amount = holder.amountT;

        item.setText(contact.getItemsS());
        amount.setText(contact.getAmounts());
        phone.setText(contact.getPhone());
        address.setText(contact.getAddressS());


    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return orders.size();
    }


}