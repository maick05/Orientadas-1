package com.example.maick.revisaocarro;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maick.revisaocarro.cls.Carros;
import com.example.maick.revisaocarro.cls.Revisoes;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
//import android.R;


public class CarrosActivity extends AppCompatActivity
{
    int id_u;
    DataHelper dh;
    ListView l;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carros);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent it = getIntent();
        Bundle bundle = it.getExtras();
        if (!bundle.equals(null))
        {
            id_u = bundle.getInt("id");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Snackbar.make(view, "", Snackbar.LENGTH_LONG)
                        .setAction("Ok", null).show();
                Intent intent = new Intent(CarrosActivity.this, AdicionarCarros.class);
                intent.putExtra("id", id_u);
                startActivity(intent);
            }
        });

        dh = new DataHelper(this);

        l = (ListView) findViewById(R.id.lista_v);
        registerForContextMenu(l);
        CarregarLista();

        l.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int posicao, long id)
            {
                Carros c2 = (Carros) l.getItemAtPosition(posicao);
                Intent intent = new Intent(CarrosActivity.this, RevisoesActivity.class);
                intent.putExtra("carro", c2);
                intent.putExtra("id", id_u);
                startActivity(intent);
            }
        });


        ///dh.db.execSQL("INSERT INTO itens_carro VALUES('1', 'Troca da Correia Dentada', '50000', '30', '')");
    }

    public void CarregarLista()
    {
        Carros c = new Carros();
        ArrayList<Carros> a1 = c.listaCarros(dh, String.valueOf(id_u));

        if (a1.size() == 0)
        {
            TextView txt = (TextView) findViewById(R.id.txt);
            txt.setText("Você não possui carros cadastrado, clique no botão abaixo par adicionar um!");
        }

        ArrayAdapter<Carros> MyArray = new ArrayAdapter<Carros>(this, android.R.layout.simple_spinner_dropdown_item, a1);
        l.setAdapter(MyArray);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem alterar = menu.add("Alterar");
        MenuItem deletar = menu.add("Excluir");

        alterar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem)
            {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                ListView l = (ListView) findViewById(R.id.lista_v);
                Carros c = (Carros) l.getItemAtPosition(info.position);

                Intent intent = new Intent(CarrosActivity.this, AlterarCarros.class);
                //Bundle bundle = new Bundle();
                intent.putExtra("carro", c);
                startActivity(intent);
                return false;
            }
        });



        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem)
            {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                ListView l = (ListView) findViewById(R.id.lista_v);
                Carros c = (Carros) l.getItemAtPosition(info.position);
                DataHelper dh = new DataHelper(CarrosActivity.this);
                //dh.db.execSQL("DELETE FROM CARROS");
                dh.Delete("Carros", "id_carro", String.valueOf(c.id));
                CarregarLista();
                Toast.makeText(CarrosActivity.this, "Carro excluído com sucesso!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        return super.onContextItemSelected(item);
    }

    public void onBackPressed() {
        Intent intent = new Intent(CarrosActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
