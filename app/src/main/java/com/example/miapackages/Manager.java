package com.example.miapackages;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Manager extends Activity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager2);

        Spinner manager_options = (Spinner) findViewById(R.id.spinner);
        manager_options.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView err = (TextView) findViewById(R.id.err);
        err.setVisibility(View.INVISIBLE);

        Spinner manager_options = (Spinner) findViewById(R.id.spinner);
        String opt = String.valueOf(manager_options.getSelectedItem());

        TextView editTextName = (TextView) findViewById(R.id.editTextName);
        TextView editTextAmount = (TextView) findViewById(R.id.editTextAmount);
        TextView editTextPrice = (TextView) findViewById(R.id.editTextPrice);
        TextView editTextSupplier = (TextView) findViewById(R.id.editTextSupplier);
        Button add_product = (Button) findViewById(R.id.add_product);

        TextView editTextNameDel = (TextView) findViewById(R.id.editTextNameDel);
        Button delete_product = (Button) findViewById(R.id.delete_product);

        TextView driver_name = (TextView) findViewById(R.id.driver_name);
        TextView driver_password = (TextView) findViewById(R.id.driver_password);
        Button add_driver = (Button) findViewById(R.id.add_driver);

        TextView delete_driver_name = (TextView) findViewById(R.id.delete_driver_name);
        Button delete_driver = (Button) findViewById(R.id.delete_driver);

        if (id == 0){
            editTextName.setVisibility(View.VISIBLE);
            editTextAmount.setVisibility(View.VISIBLE);
            editTextPrice.setVisibility(View.VISIBLE);
            editTextSupplier.setVisibility(View.VISIBLE);
            add_product.setVisibility(View.VISIBLE);
            editTextNameDel.setVisibility(View.INVISIBLE);
            delete_product.setVisibility(View.INVISIBLE);
            driver_name.setVisibility(View.INVISIBLE);
            driver_password.setVisibility(View.INVISIBLE);
            add_driver.setVisibility(View.INVISIBLE);
            delete_driver_name.setVisibility(View.INVISIBLE);
            delete_driver.setVisibility(View.INVISIBLE);
        }
        if (id == 1){
            editTextName.setVisibility(View.INVISIBLE);
            editTextAmount.setVisibility(View.INVISIBLE);
            editTextPrice.setVisibility(View.INVISIBLE);
            editTextSupplier.setVisibility(View.INVISIBLE);
            add_product.setVisibility(View.INVISIBLE);
            editTextNameDel.setVisibility(View.VISIBLE);
            delete_product.setVisibility(View.VISIBLE);
            driver_name.setVisibility(View.INVISIBLE);
            driver_password.setVisibility(View.INVISIBLE);
            add_driver.setVisibility(View.INVISIBLE);
            delete_driver_name.setVisibility(View.INVISIBLE);
            delete_driver.setVisibility(View.INVISIBLE);
        }
        if (id == 2){
            editTextName.setVisibility(View.INVISIBLE);
            editTextAmount.setVisibility(View.INVISIBLE);
            editTextPrice.setVisibility(View.INVISIBLE);
            editTextSupplier.setVisibility(View.INVISIBLE);
            add_product.setVisibility(View.INVISIBLE);
            editTextNameDel.setVisibility(View.INVISIBLE);
            delete_product.setVisibility(View.INVISIBLE);
            driver_name.setVisibility(View.VISIBLE);
            driver_password.setVisibility(View.VISIBLE);
            add_driver.setVisibility(View.VISIBLE);
            delete_driver_name.setVisibility(View.INVISIBLE);
            delete_driver.setVisibility(View.INVISIBLE);
        }
        if (id == 3){
            editTextName.setVisibility(View.INVISIBLE);
            editTextAmount.setVisibility(View.INVISIBLE);
            editTextPrice.setVisibility(View.INVISIBLE);
            editTextSupplier.setVisibility(View.INVISIBLE);
            add_product.setVisibility(View.INVISIBLE);
            editTextNameDel.setVisibility(View.INVISIBLE);
            delete_product.setVisibility(View.INVISIBLE);
            driver_name.setVisibility(View.INVISIBLE);
            driver_password.setVisibility(View.INVISIBLE);
            add_driver.setVisibility(View.INVISIBLE);
            delete_driver_name.setVisibility(View.VISIBLE);
            delete_driver.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void itemClick(View view){
        int amountS = 0;
        int priceS = 0;
        String nameS = "abc";
        String supplierS = "abc";

        EditText name = (EditText)findViewById(R.id.editTextName);
        CharSequence str1 = name.getText().toString();
        if (TextUtils.isEmpty(str1) != true)
            nameS = name.getText().toString();

        EditText amount = (EditText)findViewById(R.id.editTextAmount);
        CharSequence str2 = amount.getText().toString();
        if (TextUtils.isEmpty(str2) != true)
            amountS = Integer.valueOf(amount.getText().toString());

        EditText price = (EditText)findViewById(R.id.editTextPrice);
        CharSequence str3 = price.getText().toString();
        if (TextUtils.isEmpty(str3) != true)
            priceS = Integer.valueOf(price.getText().toString());

        EditText supplier = (EditText)findViewById(R.id.editTextSupplier);
        CharSequence str4 = supplier.getText().toString();
        if (TextUtils.isEmpty(str4) != true)
            supplierS = supplier.getText().toString();

        else {
            TextView err = (TextView) findViewById(R.id.err);
            err.setVisibility(View.VISIBLE);
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        prodManagment(db, nameS, amountS, priceS, supplierS);
    }

    protected void prodManagment(FirebaseFirestore db, String name, int amount, int price, String supp){
        isExist(db,name,amount,price,supp);
    }
    protected void isExist(FirebaseFirestore db, String name, int amount, int price, String supp){

        DocumentReference docRef = db.collection("items").document(name);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("DocumentSnapshot data: " + document.getData());
                        addNewProduct(db,name, (int) ((Long)document.get("amount") + amount),price,supp);
                    } else {
                        System.out.println("No such document");
                        addNewProduct(db,name,amount,price,supp);
                    }
                } else {
                    System.out.println( "get failed with " + task.getException());
                }
            }

        });
    }


    protected void addNewProduct(FirebaseFirestore db, String name, int amount, int price, String supp){
        Map<String, Object> prod = new HashMap<>();
        prod.put("amount", amount);
        prod.put("price", price);
        prod.put("supplier", supp);
        db.collection("items").document(name).set(prod)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("DocumentSnapshot added with ID: ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error writing document"+e);
                    }
                });
    }
    public void deleteClick(View view){
        String nameS = "";
        EditText name = (EditText)findViewById(R.id.editTextNameDel);
        //String nameS = name.getText().toString();
        CharSequence str1 = name.getText().toString();
        if (TextUtils.isEmpty(str1) != true)
            nameS = name.getText().toString();
        else {
            TextView err = (TextView) findViewById(R.id.err);
            err.setVisibility(View.VISIBLE);
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        deleteItem(db, nameS);
    }
    protected void deleteItem(FirebaseFirestore db, String name){
        db.collection("items").document(name)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println( "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println( "Error deleting document" + e);
                    }
                });
    }


    public void addDriver(View view){   //get the name and pass of the driver from editText
        String driverName = "";
        String driverPass = "";

        EditText driver_name_view = (EditText) findViewById(R.id.driver_name);
        CharSequence str1 = driver_name_view.getText().toString();
        if (TextUtils.isEmpty(str1) != true)
            driverName = driver_name_view.getText().toString();

        EditText driver_pass_view = (EditText) findViewById(R.id.driver_password);
        CharSequence str2 = driver_pass_view.getText().toString();
        if (TextUtils.isEmpty(str2) != true)
            driverPass = driver_pass_view.getText().toString();

        else {
            TextView err = (TextView) findViewById(R.id.err);
            err.setVisibility(View.VISIBLE);
            return;
        }

        //add the driver to the data base
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, String> driver = new HashMap<>();
        driver.put(driverName, driverPass);
        db.collection("users").document("drivers").set(driver, SetOptions.merge());
    }
    public void deleteDriver(View view) {
        String driverName = "";

        EditText driver_name_view = (EditText) findViewById(R.id.delete_driver_name);
        CharSequence str1 = driver_name_view.getText().toString();
        if (TextUtils.isEmpty(str1) != true)
            driverName = driver_name_view.getText().toString();

        else {
            TextView err = (TextView) findViewById(R.id.err);
            err.setVisibility(View.VISIBLE);
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> updates = new HashMap<>();
        updates.put(driverName, FieldValue.delete());

        DocumentReference docRef = db.collection("users").document("drivers");
        docRef.update(updates)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    System.out.println("DocumentSnapshot successfully deleted!");
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("Error deleting document" + e);
                }
            });
    }



}