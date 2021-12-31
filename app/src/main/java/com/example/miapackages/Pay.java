package com.example.miapackages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class Pay extends AppCompatActivity {
    String clientName;
    int totPrice;
    Data d = new Data();
    String address;
    String phone;
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Intent intent = getIntent();
        clientName = intent.getStringExtra("clientName");
        totPrice = intent.getIntExtra("tot",0);
        address = intent.getStringExtra("Address");
        phone = intent.getStringExtra("Phone");

        TextView priceRep = (TextView) findViewById(R.id.totalPay);
        String pr = String.valueOf(totPrice);
        priceRep.setText(pr);

    }
    public void onPay(View view) {
        payData();
        Intent intent = new Intent(this, Client.class);
        intent.putExtra("clientName",clientName);
        intent.putExtra("Address",address);
        intent.putExtra("Phone",phone);
        startActivity(intent);
    }
    protected void payData(){
        System.out.println("1!1!1!! "+clientName);
        System.out.println("1!1!1!! "+address);
        System.out.println("1!1!1!! "+phone);
        System.out.println("1!1!1!! "+totPrice);
        System.out.println("Claas Pay: "+ address + " "+ phone);
        String cart = "cart";
        String pac = "package";
        d.hashData(db,cart, clientName,pac,address,phone);

    }
}
