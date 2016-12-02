package com.example.maick.revisaocarro;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maick.revisaocarro.cls.Carros;
import com.example.maick.revisaocarro.cls.ItensCarro;

import java.util.Calendar;

import bibliotecas.DatePickerFragment;

public class AddItemRevActivity extends AppCompatActivity
{
    static final int DATE_DIALOG_ID = 0;
    ItensCarro ic = new ItensCarro();
    Carros c = new Carros();
    String data;
    DataHelper dh;
    int id_u;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_rev);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dh = new DataHelper(this);

        Intent it = getIntent();
        if (!it.getSerializableExtra("carro").equals(null))
        {
            c = (Carros) it.getSerializableExtra("carro");
        }
        if (!it.getSerializableExtra("item").equals(null))
        {
            ic = (ItensCarro) it.getSerializableExtra("item");
        }
        Bundle bundle = it.getExtras();
        if (!bundle.equals(null))
        {
            id_u = bundle.getInt("id");
        }
        ImageView foto = (ImageView) findViewById(R.id.foto_ir);
        TextView nome = (TextView) findViewById(R.id.nome_i);
        TextView mes = (TextView) findViewById(R.id.mes_i);
        TextView km = (TextView) findViewById(R.id.km_i);
        TextView descricao = (TextView) findViewById(R.id.descricao_i);

        if (ic.id == 1)
        {
            foto.setBackgroundResource(R.drawable.item_1);
            foto.setImageResource(R.drawable.item_1);
        }
        if (ic.id == 2)
        {
            foto.setBackgroundResource(R.drawable.item_2);
            foto.setImageResource(R.drawable.item_2);
        }
        if (ic.id == 3)
        {
            foto.setBackgroundResource(R.drawable.item_3);
            foto.setImageResource(R.drawable.item_3);
        }
        if (ic.id == 4)
        {
            foto.setBackgroundResource(R.drawable.item_4);
            foto.setImageResource(R.drawable.item_4);
        }

        nome.setText(ic.nome);
        mes.setText(String.valueOf(ic.meses));
        km.setText(String.valueOf(ic.km));
        descricao.setText(ic.descricao);

    }

    public void addRev(View v)
    {
        if(data != null)
        {
            String col = "'"+ dh.Autoincremento("revisao_itens_carro", "id_revisao_item_carro") +"', '" + c.id + "', '" + ic.id +
                    "', 'ativo', 0, '" + data + "'";
            dh.insert("revisao_itens_carro", col);
            Intent intent = new Intent(AddItemRevActivity.this, RevisoesActivity.class);
            intent.putExtra("item", ic);
            intent.putExtra("carro", c);
            intent.putExtra("id", id_u);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "É necessário adicionar uma data da última revisão ou aquisição do item!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendario = Calendar.getInstance();

        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, ano, mes,
                        dia);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            data = String.valueOf(dayOfMonth) + " /"
                    + String.valueOf(monthOfYear+1) + " /" + String.valueOf(year);
        }
    };

    public void showData(View v)
    {
        showDialog(DATE_DIALOG_ID);
    }

    public void onBackPressed()
    {
        Intent intent = new Intent(AddItemRevActivity.this, AddRevisoes.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.putExtra("carro", c);
        intent.putExtra("id", id_u);
        startActivity(intent);
        return;
    }
}