package com.example.miapackages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class AddProduct extends AppCompatActivity {

    private static final int GalleryPick=1;
    private Uri imageUri;
    ImageView inputImage;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
//        inputImage=(ImageView)findViewById(R.id.product_image);
//        inputImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                openGallery();
//            }
//        });
    }

    private void openGallery()
    {
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick && requestCode==RESULT_OK && data!=null)
        {
            imageUri=data.getData();
            inputImage.setImageURI(imageUri);
        }
    }

    Data d = new Data();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void itemClick(View view){
        int amountS = 0;
        int priceS = 0;
        String nameS = "abc";
        String descriptionS = "abc";
        if(imageUri==null)
        {
            Toast.makeText(this,"product image is menadtory",Toast.LENGTH_SHORT).show();
        }
        EditText name = (EditText)findViewById(R.id.product_name);
        CharSequence str1 = name.getText().toString();
        if (TextUtils.isEmpty(str1) != true)
            nameS = name.getText().toString();

        EditText amount = (EditText)findViewById(R.id.product_amount);
        CharSequence str2 = amount.getText().toString();
        if (TextUtils.isEmpty(str2) != true)
            amountS = Integer.valueOf(amount.getText().toString());

        EditText price = (EditText)findViewById(R.id.product_price);
        CharSequence str3 = price.getText().toString();
        if (TextUtils.isEmpty(str3) != true)
            priceS = Integer.valueOf(price.getText().toString());

        EditText description = (EditText)findViewById(R.id.product_description);
        CharSequence str4 = description.getText().toString();
        if (TextUtils.isEmpty(str4) != true)
            descriptionS = description.getText().toString();

        else {
            TextView err = (TextView) findViewById(R.id.err);
            err.setVisibility(View.VISIBLE);
            return;
        }

        d.addDocItem(db, nameS, amountS, priceS, descriptionS);
    }
}