package com.example.maick.revisaocarro.cls;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.maick.revisaocarro.DataHelper;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maick on 22/11/2016.
 */
public class usuarios
{
    int id;
    String nome;
    String email;
    String senha;

    public boolean validaRegistro(DataHelper dh, String email)
    {
        Cursor cursor = dh.ListarWhere("usuarios", "*", "email", email);
        cursor.moveToFirst();
        ArrayList<usuarios> lista = new ArrayList<>();
        int i = 0;
        if (cursor.getCount() > 0)
        {
            do
            {
                usuarios u = new usuarios();
                System.out.println("POSICAO: " + i);
                //u.id = cursor.getInt(cursor.getColumnIndex("id_usuario"));
                //u.nome = cursor.getString(cursor.getColumnIndex("nome"));
                u.email = cursor.getString(cursor.getColumnIndex("email"));
                //u.senha = cursor.getString(cursor.getColumnIndex("senha"));
                lista.add(u);
                i++;
            } while (cursor.moveToNext());
        }


        if (lista.size() > 0)
        {
            return  true;
        }
        else
        {
            return false;
        }
    }

    public int validaLogin(DataHelper dh, String email, String senha)
    {
        Cursor cursor = dh.ListarWhere("usuarios", "*", "email", email);
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
        {
            usuarios u = new usuarios();
            u.id = cursor.getInt(cursor.getColumnIndex("id_usuario"));
            //u.nome = cursor.getString(cursor.getColumnIndex("nome"));
            u.email = cursor.getString(cursor.getColumnIndex("email"));
            u.senha = cursor.getString(cursor.getColumnIndex("senha"));

            System.out.printf("Senha u. = " + u.senha +" e senha2: " + senha);
            if(u.senha.equals(senha))
            {
                return u.id;
            }
            else
            {
                System.out.println("\nSNEHA");
                return -1;
            }
        }
        else
        {
            System.out.println("EMAIL");
            return -1;
        }
    }
}
