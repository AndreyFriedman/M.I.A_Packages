package com.example.miapackages;

import static com.example.miapackages.Notification.CHANNEL_1_ID;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Pay extends AppCompatActivity {
    public NotificationManagerCompat notificationManager;

    private static final String CHANNEL_ID = "channel";
    String clientName;
    int totPrice;
    Data d = new Data();
    String address;
    String phone;
    ArrayList<Item> items;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Intent intent = getIntent();
        clientName = intent.getStringExtra("clientName");
        totPrice = intent.getIntExtra("tot",0);
        address = intent.getStringExtra("Address");
        phone = intent.getStringExtra("Phone");
        items = this.getIntent().getExtras().getParcelableArrayList("items");

        notificationManager = NotificationManagerCompat.from(this);
        //createNotificationChannel();
        TextView hello=(TextView)findViewById(R.id.hello_name);
        hello.setText(clientName+" we almost finished");
        TextView priceRep = (TextView) findViewById(R.id.totalPay);
        String pr = String.valueOf(totPrice);
        priceRep.setText("Your total is "+pr);

    }
    public void onPay(View view) {
        sendOnChannel1();
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

        Date date = new Date();
        long time =date.getTime();
        //String id
        String id = Long.toString(time);
        String cart = "cart";
        String pac = "package";
        String saveCurrentDate, saveCurrentTime;
        Calendar calendar=Calendar.getInstance();
//        SimpleDateFormat currentDate= new SimpleDateFormat("MMM dd, yyyy");
//        saveCurrentDate=currentDate.format(calendar.getFirstDayOfWeek());
//        SimpleDateFormat sdf = new SimpleDateFormat();
//        Data date = sdf.parse(dateString);
//        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
//        saveCurrentTime=currentTime.format(calendar.getTime());
        String randomKey=Long.toString(calendar.getTimeInMillis());//saveCurrentDate+saveCurrentTime;

        d.hashData(db,cart, clientName,pac,address,phone, randomKey);
        HashMap<String,Object> prod = new HashMap<>();
        String ad = "";
        ad = ad + clientName + ", " + address + ", " + phone + ", " + totPrice ;
        prod.put("info",ad);
        prod.put("order",items);
        d.setDoc(db,"history",id,prod);


    }
    public void sendOnChannel1() {

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("M.I.A Packages")
                .setContentText("Your order has been placed")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)

                .build();

        notificationManager.notify(1, notification);
    }


}
