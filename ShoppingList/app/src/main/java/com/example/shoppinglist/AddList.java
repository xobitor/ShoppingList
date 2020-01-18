package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.HashMap;

public class AddList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_addNewItem)
        {

            final EditText nListName = findViewById(R.id.NewListName_input);

            String listName = nListName.getText().toString();

            ShoppingList shpLst = new ShoppingList(listName, new ArrayList<Product>());

            MainActivity.shoppingList = shpLst;
            addList(shpLst);
            Intent intent = new Intent();
            setResult(2, intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_list, menu);
        return true;
    }

    public void addList(ShoppingList shpList)
    {
        MainActivity.shoppingLists.add(shpList);
        MainActivity.listItems.clear();
        MainActivity.totalPrice.setText(String.valueOf(shpList.getTotalPrice()));
        ViewLists.lists.add(shpList.getNome());
    }
}
