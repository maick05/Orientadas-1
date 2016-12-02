package com.example.maick.revisaocarro.cls;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.maick.revisaocarro.DataHelper;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maick on 22/11/2016.
 */
public class Carros implements Serializable
{
    public int id;
    public String nome;
    public String marca;
    public String modelo;
    public int id_prop;

    public ArrayList<Carros> listaCarros(DataHelper dh, String id_p)
    {
        Cursor cursor = dh.ListarWhere("Carros", "*", "id_proprietario", id_p);
        cursor.moveToFirst();
        ArrayList<Carros> lista = new ArrayList<>();
        //System.out.println("COUNT: " + cursor.getCount());
        if (cursor.getCount() > 0)
        {
            do
            {
                Carros c = new Carros();
                c.id = cursor.getInt(cursor.getColumnIndex("id_carro"));
                c.nome = cursor.getString(cursor.getColumnIndex("nome"));
                c.marca = cursor.getString(cursor.getColumnIndex("marca"));
                c.modelo = cursor.getString(cursor.getColumnIndex("modelo"));
                c.id_prop = cursor.getInt(cursor.getColumnIndex("id_proprietario"));
                lista.add(c);
            }while (cursor.moveToNext());
        }
        return lista;
    }
    
    public void alterarCarros(DataHelper dh, String id, Carros c)
    {
        dh.Update("Carros", "nome", c.nome,"id_carro", id);
        dh.Update("Carros", "marca", c.marca,"id_carro", id);
        dh.Update("Carros", "modelo", c.modelo,"id_carro", id);
    }

    @Override
    public String toString()
    {
        String t = nome + " - " + marca + " " + modelo;
        return t;
    }
}
