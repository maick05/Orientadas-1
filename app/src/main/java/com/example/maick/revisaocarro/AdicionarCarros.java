package com.example.maick.revisaocarro;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.IllegalFormatCodePointException;

public class AdicionarCarros extends AppCompatActivity
{
    int id;
    EditText nome;
    EditText marca;
    EditText modelo;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_carros);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent it = getIntent();
        Bundle bundle = it.getExtras();
        if (!String.valueOf(bundle.getInt("id")).equals(null))
        {
            id = bundle.getInt("id");
        }
        nome = (EditText) findViewById(R.id.nome_ad);
        marca = (EditText) findViewById(R.id.marca_ad);
        modelo = (EditText) findViewById(R.id.modelo_ad);
    }

    public void onBackPressed() {

        Intent intent = new Intent(AdicionarCarros.this, CarrosActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.putExtra("id", id);
        startActivity(intent);
        return;
    }

    public void addCar(View v)
    {
        if(Validar())
        {
            DataHelper dh = new DataHelper(this);
            String t = "'" + dh.Autoincremento("Carros", "id_carro") + "', '"+ nome.getText() + "', '"+ marca.getText() + "', '"+ modelo.getText()+"','"+ id +"'";
            dh.insert("Carros", t);
            Intent intent = new Intent(AdicionarCarros.this, CarrosActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
            Toast.makeText(this, "Carro adicionado com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean Validar()
    {
        if(nome.getText().length() == 0)
        {
            nome.setError("O campo nome não pode ficar vazio!");
            return false;
        }
        else if(marca.getText().length() == 0)
        {
            marca.setError("O campo marca não pode ficar vazio!");
            return false;
        }
        else if(modelo.getText().length() == 0  )
        {
            modelo.setError("O campo modelo não pode ficar vazio!");
            return false;
        }
        else
        {
            return true;
        }
    }
}