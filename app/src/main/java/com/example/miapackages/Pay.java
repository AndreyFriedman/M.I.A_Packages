package com.example.miapackages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Pay extends AppCompatActivity {
    String clientName;
    int totPrice;
    Data d = new Data();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Intent intent = getIntent();
        clientName = intent.getStringExtra("clientName");
        totPrice = intent.getIntExtra("tot",0);
        TextView priceRep = (TextView) findViewById(R.id.totalPay);
        String pr = String.valueOf(totPrice);
        priceRep.setText(pr);

    }
    public void onPay(View view) {
        //d.payData(clientName);
        Intent intent = new Intent(this, Client.class);
        intent.putExtra("clientName",clientName);
        startActivity(intent);
    }
}
