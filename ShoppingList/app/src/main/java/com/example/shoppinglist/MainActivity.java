package com.example.shoppinglist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.example.shoppinglist.ViewLists.lists;

public class MainActivity extends AppCompatActivity {


    static ShoppingList shoppingList = new ShoppingList("Default", new ArrayList<Product>());
    static List<ShoppingList> shoppingLists = new ArrayList<>();
    ListView lv = null;
    static List<HashMap<String, String>> listItems = new ArrayList<>();
    static HashMap<String, String> products = new HashMap<>();
    static SimpleAdapter adapter = null;
    static TextView totalPrice = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setTitle(shoppingList.getNome());
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);

        if (lists.size() == 0)
        {
            lists.add(shoppingList.getNome());
            shoppingLists.add(shoppingList);
        }

        adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"First Line", "Second Line"}, new int[]{R.id.text1, R.id.text2});

        lv = findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setClickable(true);

        totalPrice = findViewById(R.id.total_price);
        totalPrice.setText(String.valueOf(shoppingList.getTotalPrice()));

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int arg2, long arg3)
            {
                androidx.appcompat.app.AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                alertDialog.setTitle("Delete Item");
                alertDialog.setMessage("Are you sure?");
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listItems.remove(arg2);
                        adapter.notifyDataSetChanged();
                        lv.setAdapter(adapter);
                    }
                });
                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addItem) {
            Intent intent = new Intent (this, AddItem.class);
            startActivityForResult(intent, -1); //ex: requestCode = 1
            return (true);
        }

        if (id == R.id.action_view_lists)
        {
            Intent intent = new Intent (this, ViewLists.class);
            startActivityForResult(intent, -1);
            return (true);
        }
        if (id == R.id.action_add_list)
        {
            Intent intent = new Intent (this, AddList.class);
            startActivityForResult(intent, -1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (data != null) {
                setTitle(shoppingList.getNome());
                adapter.notifyDataSetChanged();
                lv.setAdapter(adapter);
            }

        }
        else if (resultCode == 2)
        {
            if (data != null)
            {
                setTitle(shoppingList.getNome());
                adapter.notifyDataSetChanged();
                lv.setAdapter(adapter);
            }
        }
        else if (resultCode == Activity.RESULT_FIRST_USER)
        {
            setTitle(shoppingList.getNome());
            adapter.notifyDataSetChanged();
            lv.setAdapter(adapter);
        }

    }
}
