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

import com.example.maick.revisaocarro.cls.Carros;

public class AlterarCarros extends AppCompatActivity
{
    int id;
    int idC;
    EditText nome;
    EditText marca;
    EditText modelo;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alterar_carros);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent it = getIntent();
        Carros c = new Carros();
        if (!it.getSerializableExtra("carro").equals(null))
        {
            c = (Carros) it.getSerializableExtra("carro");
        }

        nome = (EditText) findViewById(R.id.nome_a);
        marca = (EditText) findViewById(R.id.marca_a);
        modelo = (EditText) findViewById(R.id.modelo_a);

        nome.setText(c.nome);
        marca.setText(c.marca);
        modelo.setText(c.modelo);
        id = c.id_prop;
        idC = c.id;
        System.out.printf("ID DO CARRO: " + c.id);
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(AlterarCarros.this, CarrosActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.putExtra("id", id);
        startActivity(intent);
        return;
    }

   public void altCar(View v)
    {
        EditText nome = (EditText) findViewById(R.id.nome_a);
        EditText marca = (EditText) findViewById(R.id.marca_a);
        EditText modelo = (EditText) findViewById(R.id.modelo_a);

        if (Validar())
        {
            Carros c = new Carros();
            c.nome = String.valueOf(nome.getText());
            c.marca = String.valueOf(marca.getText());
            c.modelo = String.valueOf(modelo.getText());
            DataHelper dh = new DataHelper(this);

            c.alterarCarros(dh, String.valueOf(idC), c);
            Intent intent = new Intent(AlterarCarros.this, CarrosActivity.class);
            intent.putExtra("id", id);
            Toast.makeText(this, "Carro alterado com sucesso!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
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
