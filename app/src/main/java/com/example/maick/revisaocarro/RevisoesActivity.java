package com.example.maick.revisaocarro;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maick.revisaocarro.cls.Carros;
import com.example.maick.revisaocarro.cls.ItensCarro;
import com.example.maick.revisaocarro.cls.Revisoes;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import adapter.RevisoesAdapter;
import adapter.RevisoesAdapterCar;

public class RevisoesActivity extends AppCompatActivity
{
    private static final int DATE_DIALOG_ID = 0;
    Carros c = new Carros();
    ListView l;
    DataHelper dh;
    String data;
    Revisoes r;
    int id_u;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent it = getIntent();
        Bundle bundle = it.getExtras();
        if (!it.getSerializableExtra("carro").equals(null))
        {
            c = (Carros) it.getSerializableExtra("carro");
        }

        id_u = bundle.getInt("id");

        l = (ListView) findViewById(R.id.list_rev);
        registerForContextMenu(l);
        dh = new DataHelper(this);
        CarregarLista();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_rev);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "", Snackbar.LENGTH_LONG)
                        .setAction("Ok", null).show();
                Intent intent = new Intent(RevisoesActivity.this, AddRevisoes.class);
                intent.putExtra("carro", c);
                intent.putExtra("id", id_u);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(RevisoesActivity.this, CarrosActivity.class);
        intent.putExtra("id", id_u);
        startActivity(intent);
        return;
    }

    public void CarregarLista()
    {
        Revisoes r = new Revisoes();
        ArrayList<Revisoes> lista = r.listaRevisoes(dh, String.valueOf(c.id));
        RevisoesAdapterCar rac = new RevisoesAdapterCar(lista, this);
        l.setAdapter(rac);
    }

    public void verificar(View v)
    {
        TextView km = (TextView) findViewById(R.id.km_v);
        int km_atual = Integer.parseInt(String.valueOf(km.getText()));

        Revisoes r = new Revisoes();
        ArrayList<Revisoes> lista = r.listaRevisoesPendentes(dh, km_atual);
        RevisoesAdapterCar rac = new RevisoesAdapterCar(lista, this);
        l.setAdapter(rac);
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        Calendar calendario = Calendar.getInstance();

        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        switch (id)
        {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, ano, mes,
                        dia);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth)
        {
            data = String.valueOf(dayOfMonth) + " /"
                    + String.valueOf(monthOfYear + 1) + " /" + String.valueOf(year);
            r.Renovar(dh, data, String.valueOf(r.id));
            CarregarLista();
            Toast.makeText(RevisoesActivity.this, "Revisão realizada com sucesso!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem renovar = menu.add("Informar data da revisão");
        MenuItem deletar = menu.add("Remover Revisão");

        renovar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem)
            {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

                Revisoes rev = (Revisoes) l.getItemAtPosition(info.position);
                showDialog(DATE_DIALOG_ID);
                //rev.Renovar(dh, data, String.valueOf(rev.id));
                r = rev;
                CarregarLista();
                return false;
            }
        });


        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem)
            {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Revisoes rev = (Revisoes) l.getItemAtPosition(info.position);
                //dh.db.execSQL("DELETE FROM revisao_itens_carro");
                dh.Delete("revisao_itens_carro", "id_revisao_item_carro", String.valueOf(rev.id));
                CarregarLista();
                Toast.makeText(RevisoesActivity.this, "Revisão removida com sucesso!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        return super.onContextItemSelected(item);
    }

}