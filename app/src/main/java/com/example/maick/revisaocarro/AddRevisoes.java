package com.example.maick.revisaocarro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maick.revisaocarro.cls.Carros;
import com.example.maick.revisaocarro.cls.ItensCarro;

import java.util.ArrayList;

import adapter.RevisoesAdapter;

public class AddRevisoes extends AppCompatActivity
{
    DataHelper dh;
    Carros c;
    ItensCarro ic = new ItensCarro();
    ListView l;
    int id_u;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_revisoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dh = new DataHelper(this);
        Intent it = getIntent();
        if (!it.getSerializableExtra("carro").equals(null))
        {
            c = (Carros) it.getSerializableExtra("carro");
        }
        Bundle bundle = it.getExtras();
        if (!bundle.equals(null))
        {
            id_u = bundle.getInt("id");
        }

        l = (ListView) findViewById(R.id.lista_r);
        CarregarLista();

        l.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int posicao, long id)
            {
                ItensCarro ic = (ItensCarro) l.getItemAtPosition(posicao);
                Intent intent = new Intent(AddRevisoes.this, AddItemRevActivity.class);
                intent.putExtra("item", ic);
                intent.putExtra("carro", c);
                intent.putExtra("id", id_u);
                startActivity(intent);
            }
        });
    }

    public void CarregarLista()
    {
        ArrayList<ItensCarro> lista = ic.listaItens(dh, String.valueOf(c.id));

        if (lista.size() == 0)
        {
            TextView txt = (TextView) findViewById(R.id.txt_rev);
            txt.setText("Não mais nenhum item para adicionar, você já adicionou todos!");
        }

        RevisoesAdapter ra = new RevisoesAdapter(lista, this);
        l.setAdapter(ra);
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(AddRevisoes.this, RevisoesActivity.class);
        intent.putExtra("carro", c);
        intent.putExtra("id", id_u);
        startActivity(intent);
        return;
    }
}
