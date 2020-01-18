package com.example.shoppinglist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewLists extends AppCompatActivity {

    ListView lv = null;
    //static SimpleAdapter adapter = null;
    static ArrayAdapter<String> adapter = null;
    static ArrayList<String> lists = new ArrayList<>();

    HashMap<String, String> resultsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lists);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        lv = findViewById(R.id.listView);

        adapter = new ArrayAdapter (this, android.R.layout.simple_list_item_1, lists);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.shoppingList = MainActivity.shoppingLists.get(position);
                for (int i = 0; i<MainActivity.shoppingList.getProducts().size(); i++)
                {
                    resultsMap.clear();
                    resultsMap.put("First Line", MainActivity.shoppingList.getProducts().get(i).getNome());
                    resultsMap.put("Second Line", MainActivity.shoppingList.getProducts().get(i).getPreco() + "€/" + MainActivity.shoppingList.getProducts().get(i).getUnidade());
                }
                MainActivity.listItems.clear();
                MainActivity.listItems.add(resultsMap);
                Intent intent = new Intent();
                setResult(RESULT_FIRST_USER, intent);
                finish();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int arg2, long arg3)
            {
                lists.remove(arg2);
                adapter.notifyDataSetChanged();
                lv.setAdapter(adapter);
                return false;
            }
        });
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
