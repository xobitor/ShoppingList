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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.shoppinglist.ViewLists.lists;

//CLASSE MAIN
public class MainActivity extends AppCompatActivity {

//Criação de variáveis estáticas para serem utilizadas noutras atividades
    static ShoppingList shoppingList = new ShoppingList("Default", new ArrayList<Product>());
    static List<ShoppingList> shoppingLists = new ArrayList<>();
    static ListView lv = null;
    static List<HashMap<String, String>> listItems = new ArrayList<>();
    static HashMap<String, String> products = new HashMap<>();
    static SimpleAdapter adapter = null;
    static TextView totalPrice = null;
    static String title = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//Ao iniciar, definir o título como sendo o nome da lista seleccionada e definir a cor de fundo da atividade
        title = shoppingList.getNome();
        setTitle(title);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);

//Para a aplicação não iniciar sem listas, é criada e apresentada uma lista Default, que poderá ser apagada depois
        if (lists.size() == 0)
        {
            lists.add(shoppingList.getNome());
            shoppingLists.add(shoppingList);
        }

//Criação do adaptador de listView para que os itens da lista de compras seleccionada possam ser apresentados
        adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"First Line", "Second Line"}, new int[]{R.id.text1, R.id.text2});

//Mostrar os itens na listView
        lv = findViewById(R.id.listView);
        lv.setAdapter(adapter);

//Definir a listView como clicável
        lv.setClickable(true);

//Alterar o campo preço total para o valor obtido da lista de compras seleccionado
        totalPrice = findViewById(R.id.total_price);
        totalPrice.setText(shoppingList.getTotalPrice() + "€");

//Definir ação caso se pressione num item da listView durante tempo (Long Press)
//Neste caso, ao pressionar por um tempo, irá aparecer uma caixa de diálogo a perguntar se tem a certeza que quer
//apagar o item seleccionado
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

//Execução de funções com base nos botões apresentados
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


//Recebe dados de output de outras atividades
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (data != null) {
                title = shoppingList.getNome();
                setTitle(title);
                adapter.notifyDataSetChanged();
                lv.setAdapter(adapter);
            }

        }
        else if (resultCode == 2)
        {
            if (data != null)
            {
                title = shoppingList.getNome();
                setTitle(title);
                adapter.notifyDataSetChanged();
                lv.setAdapter(adapter);
            }
        }
        else if (resultCode == Activity.RESULT_FIRST_USER)
        {
            title = shoppingList.getNome();
            setTitle(title);
            adapter.notifyDataSetChanged();
            lv.setAdapter(adapter);
        }

    }
}
