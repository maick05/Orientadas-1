package com.example.maick.revisaocarro.cls;

import android.database.Cursor;

import com.example.maick.revisaocarro.DataHelper;

import java.util.ArrayList;

/**
 * Created by maick on 30/11/2016.
 */

public class Revisoes
{
    public int id;
    public int id_item;
    public int id_carro;
    public int km;
    public String data_renov;
    public String status;
    public ItensCarro item;

    public ArrayList<Revisoes> listaRevisoes(DataHelper dh, String id)
    {
        String selectQuery =
                "SELECT * FROM revisao_itens_carro WHERE Carros_id_carro = '"+ id +"';";
        Cursor cursor = dh.db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        ArrayList<Revisoes> lista = new ArrayList<>();
        if (cursor.getCount() > 0)
        {
            do
            {
                Revisoes rev = new Revisoes();
                rev.id = cursor.getInt(cursor.getColumnIndex("id_revisao_item_carro"));
                rev.id_carro = cursor.getInt(cursor.getColumnIndex("Carros_id_carro"));
                rev.km = cursor.getInt(cursor.getColumnIndex("km_troca"));
                rev.id_item = cursor.getInt(cursor.getColumnIndex("Item_id_item"));
                rev.status = cursor.getString(cursor.getColumnIndex("status"));
                rev.km = cursor.getInt(cursor.getColumnIndex("km_troca"));
                rev.data_renov = cursor.getString(cursor.getColumnIndex("data_renovacao"));
                Cursor cursorItem = dh.ListarWhere("itens_carro", "*", "id_item", String.valueOf(rev.id_item));
                cursorItem.moveToFirst();
                if (cursorItem.getCount() > 0)
                {
                    do
                    {
                        rev.item = new ItensCarro();
                        rev.item.id = cursorItem.getInt(cursorItem.getColumnIndex("id_item"));
                        rev.item.nome = cursorItem.getString(cursorItem.getColumnIndex("nome"));
                        rev.item.km = cursorItem.getInt(cursorItem.getColumnIndex("km_troca"));
                        rev.item.meses = cursorItem.getInt(cursorItem.getColumnIndex("meses_troca"));
                        rev.item.descricao = cursorItem.getString(cursorItem.getColumnIndex("descricao"));

                        lista.add(rev);
                    }while (cursorItem.moveToNext());
                }
            }while (cursor.moveToNext());
        }
        return lista;
    }

    public void Renovar(DataHelper dh, String data, String id)
    {
        dh.Update("revisao_itens_carro", "data_renovacao", data,"id_revisao_item_carro", id);
    }

    public ArrayList<Revisoes> listaRevisoesPendentes(DataHelper dh, int km_atual)
    {

        String selectQuery =
                "SELECT ic.*, ric.*" +
                        " FROM revisao_itens_carro ric" +
                        " INNER JOIN itens_carro ic" +
                            " ON ric.Item_id_item = ic.id_item" +
                        " WHERE " + km_atual+" <= ic.km_troca";
        Cursor cursor = dh.db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        ArrayList<Revisoes> lista = new ArrayList<>();
        if (cursor.getCount() > 0)
        {
            do
            {
                Revisoes rev = new Revisoes();
                rev.id = cursor.getInt(cursor.getColumnIndex("ric.id_revisao_item_carro"));
                rev.id_carro = cursor.getInt(cursor.getColumnIndex("ric.Carros_id_carro"));
                rev.km = cursor.getInt(cursor.getColumnIndex("ric.km_troca"));
                rev.id_item = cursor.getInt(cursor.getColumnIndex("ric.Item_id_item"));
                rev.status = cursor.getString(cursor.getColumnIndex("ric.status"));
                rev.km = cursor.getInt(cursor.getColumnIndex("ric.km_troca"));
                rev.data_renov = cursor.getString(cursor.getColumnIndex("ric.data_renovacao"));

                Cursor cursorItem = dh.ListarWhere("itens_carro", "*", "id_item", String.valueOf(rev.id_item));
                cursorItem.moveToFirst();
                if (cursorItem.getCount() > 0)
                {
                    do
                    {
                        rev.item = new ItensCarro();
                        rev.item.id = cursorItem.getInt(cursorItem.getColumnIndex("id_item"));
                        rev.item.nome = cursorItem.getString(cursorItem.getColumnIndex("nome"));
                        rev.item.km = cursorItem.getInt(cursorItem.getColumnIndex("km_troca"));
                        rev.item.meses = cursorItem.getInt(cursorItem.getColumnIndex("meses_troca"));
                        rev.item.descricao = cursorItem.getString(cursorItem.getColumnIndex("descricao"));

                        lista.add(rev);
                    }while (cursorItem.moveToNext());
                }
            }while (cursor.moveToNext());
        }
        return lista;
    }
}
