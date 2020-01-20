package com.example.shoppinglist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewLists extends AppCompatActivity {
//Inicialização de variáveis
    ListView lv = null;
    static ArrayAdapter<String> adapter = null;
    static ArrayList<String> lists = new ArrayList<>();
    HashMap<String, String> resultsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lists);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);

//Mostrar o botão "Back"
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//Armazenar na variável lv a listview da layout activity_view_lists
        lv = findViewById(R.id.listView);

//Como só é pretendido apresentar o nome das listas, criou-se um simples ArrayAdapter para esse efeito
        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, lists){

//Como o fundo da aplicação é preto, para efeitos de vissibilidade, queremos o texto a branco
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
//Obter o item da listview
                View view = super.getView(position, convertView, parent);

//Inicializar uma variável para armazenar a linha da listview
                TextView tv = view.findViewById(android.R.id.text1);

//Mudar a cor do texto para branco
                tv.setTextColor(Color.WHITE);

//Mostrar o item
                return view;
            }
        };

//Definir acção ao clicar no item
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//Para seleccionar, primeiro obtém-se o item seleccionado da lista de ShoppingList da main
                MainActivity.shoppingList = MainActivity.shoppingLists.get(position);

//Ciclo para preencher o HashMap para depois apresentar os itens corretos na main
                for (int i = 0; i<MainActivity.shoppingList.getProducts().size(); i++)
                {
//Primeiro, para ter a certeza que não se repetem itens, limpa-se a variável resultsMap
//Depois, adiciona-se o nome do produto para ser a primeira linha e o preço e unidade na segunda
                    resultsMap.clear();
                    resultsMap.put("First Line", MainActivity.shoppingList.getProducts().get(i).getNome());
                    resultsMap.put("Second Line", MainActivity.shoppingList.getProducts().get(i).getPreco() + "€/" + MainActivity.shoppingList.getProducts().get(i).getUnidade());
                }
//Limpa-se a lista de  hashMap definido para apresentar os itens na main e adiciona-se este hashMap, para apresentar os itens corretos
                MainActivity.listItems.clear();
                MainActivity.listItems.add(resultsMap);
                MainActivity.title = MainActivity.shoppingList.getNome();
                MainActivity.totalPrice.setText(String.valueOf(MainActivity.shoppingList.getTotalPrice()) + "€");
                MainActivity.lv.setAdapter(MainActivity.adapter);
                Intent intent = new Intent();
                setResult(RESULT_FIRST_USER, intent);
                finish();
            }
        });
//Definir ação ao manter pressionado numa lista durante um tempo
//A acção é idêntica à acção da main. Ao manter pressionado, aparecerá uma caixa de diálogo a perguntar se tem a certeza
//que quer apagar o item seleccionado, apagando ao clicar em "OK"
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int arg2, long arg3)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                alertDialog.setTitle("Delete List");
                alertDialog.setMessage("Are you sure?");
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lists.remove(arg2);
                        adapter.notifyDataSetChanged();
                        lv.setAdapter(adapter);
                        finish();
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
        lv.setAdapter(adapter);
    }

//Ação do botão "Back"
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
