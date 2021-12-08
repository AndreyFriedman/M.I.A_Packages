package com.example.miapackages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.view.View;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
        EditText name = (EditText)findViewById(R.id.editTextName);
        String nameS = name.getText().toString();
        EditText amount = (EditText)findViewById(R.id.editTextAmount);
        int amountS = Integer.valueOf(amount.getText().toString());
        EditText price = (EditText)findViewById(R.id.editTextPrice);
        int priceS = Integer.valueOf(price.getText().toString());
        EditText supplier = (EditText)findViewById(R.id.editTextSupplier);
        String supplierS = supplier.getText().toString();


        System.out.println(nameS);
        System.out.println(amountS);
        System.out.println(priceS);
        System.out.println(supplierS);

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
        EditText name = (EditText)findViewById(R.id.editTextNameDel);
        String nameS = name.getText().toString();
        System.out.println(nameS);
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
        EditText driver_neme_view = (EditText) findViewById(R.id.driver_name);
        String driverName = driver_neme_view.getText().toString();
        EditText driver_pass_view = (EditText) findViewById(R.id.driver_password);
        String driverPass = driver_pass_view.getText().toString();
        //add the driver to the data base
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, String> driver = new HashMap<>();
        driver.put(driverName, driverPass);
        db.collection("users").document("drivers").set(driver, SetOptions.merge());
    }
    public void deleteDriver(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        EditText driver_neme_view = (EditText) findViewById(R.id.delete_driver_name);
        String driverName = driver_neme_view.getText().toString();

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