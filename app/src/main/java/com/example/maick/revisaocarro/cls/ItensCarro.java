package com.example.maick.revisaocarro.cls;

import android.database.Cursor;

import com.example.maick.revisaocarro.DataHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

/**
 * Created by maick on 30/11/2016.
 */

public class ItensCarro implements Serializable
{
    public int id;
    public String nome;
    public int km;
    public int meses;
    public String descricao;

    public ArrayList<ItensCarro> listaItens(DataHelper dh, String id)
    {
        ArrayList<ItensCarro> lista = new ArrayList<>();
        String selectQuery0 =
                "SELECT * FROM revisao_itens_carro WHERE Carros_id_carro = '"+ id + "';";
        Cursor cursor = dh.db.rawQuery(selectQuery0, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0)
        {
            String selectQuery =
                    "SELECT * FROM itens_carro " +
                            "EXCEPT " +
                    "SELECT ic.* FROM itens_carro ic, revisao_itens_carro ric" +
                        " WHERE ic.id_item == ric.Item_id_item AND ric.Carros_id_carro = '" + id + "';";
            cursor = dh.db.rawQuery(selectQuery, null);
            cursor.moveToFirst();
        }
        else
        {
            String selectQuery =
                    "SELECT * FROM itens_carro ";
            cursor = dh.db.rawQuery(selectQuery, null);
            cursor.moveToFirst();
        }
            if (cursor.getCount() > 0)
            {
                do
                {
                    ItensCarro ic = new ItensCarro();
                    ic.id = cursor.getInt(cursor.getColumnIndex("id_item"));
                    ic.nome = cursor.getString(cursor.getColumnIndex("nome"));
                    ic.km = cursor.getInt(cursor.getColumnIndex("km_troca"));
                    ic.meses = cursor.getInt(cursor.getColumnIndex("meses_troca"));
                    ic.descricao = cursor.getString(cursor.getColumnIndex("descricao"));
                    lista.add(ic);
                } while (cursor.moveToNext());
            }
        return lista;
    }
}
