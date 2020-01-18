package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import java.util.HashMap;

//Atividade para adicionar item à lista de compras seleccionada
public class AddItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

//Define a cor de fundo da atividade
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);

//Mostrar o botão "Back" para voltar à main
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
    }

//Função que fecha esta atividade e retorna à main
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

//Criação de variáveis para ler os inputs do utilizador
            final EditText nProductName = findViewById(R.id.NewProductName_input);
            final EditText nProductPrice = findViewById(R.id.NewProductPrice_input);
            final RadioButton rbKg = findViewById(R.id.NewProductUnity_kg_rb);
            final RadioButton rbUn = findViewById(R.id.NewProductUnity_un_rb);

//Conversão em Strings e double para criação de novo objeto da Classe Product

            if (nProductName.getText().toString().isEmpty())
            {
                productName = "New product";
            }
            else {
                productName = nProductName.getText().toString();
            }

            if (nProductPrice.getText().toString().isEmpty())
            {
                productPrice = 0;
            }
            else {
                productPrice = Double.parseDouble(nProductPrice.getText().toString());
            }
            if (rbKg.isChecked())
            {
                productUnity = "Kg";
            }
            else {
                productUnity = "Un";
            }


            Intent intent = new Intent();
            Product product = new Product (productName, productPrice, productUnity); //Criação de novo objeto Product com os inputs
            addItem(product);

            MainActivity.totalPrice.setText(String.valueOf(MainActivity.shoppingList.getTotalPrice()) + "€"); //Alterar imediatamente a label Preço total na main
            setResult(RESULT_OK, intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

//Adiciona o novo item à lista de compras seleccionada
//Converte esta informação em <String, String> (nome do produto, preço) para apresentar depois na main
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
