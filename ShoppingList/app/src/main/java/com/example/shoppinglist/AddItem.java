package com.example.shoppinglist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AddItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_addNewItem)
        {
            String productName;
            double productPrice;
            String productUnity = "";

            final EditText nProductName = findViewById(R.id.NewProductName_input);
            final EditText nProductPrice = findViewById(R.id.NewProductPrice_input);
            final RadioButton rbKg = findViewById(R.id.NewProductUnity_kg_rb);
            final RadioButton rbUn = findViewById(R.id.NewProductUnity_un_rb);

            productName = nProductName.getText().toString();
            productPrice = Double.parseDouble(nProductPrice.getText().toString());


            if (rbKg.isChecked())
            {
                productUnity = "Kg";
            }
            else if (rbUn.isChecked()) {
                productUnity = "Un";
            }


            Intent intent = new Intent();

            Product product = new Product (productName, productPrice, productUnity);
            addItem(product);
            MainActivity.totalPrice.setText(String.valueOf(MainActivity.shoppingList.getTotalPrice()));
            setResult(RESULT_OK, intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void addItem(Product product)
    {
        MainActivity.shoppingList.addProduct(product);
        MainActivity.products.put(product.getNome(), String.valueOf(product.getPreco()));
        HashMap<String, String> resultsMap = new HashMap<>();

        resultsMap.put("First Line", product.getNome());
        resultsMap.put("Second Line", product.getPreco() + "€/" + product.getUnidade());
        MainActivity.listItems.add(resultsMap);
    }
}
