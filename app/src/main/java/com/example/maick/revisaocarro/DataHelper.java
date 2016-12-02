package com.example.maick.revisaocarro;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.maick.revisaocarro.cls.usuarios;

import java.sql.SQLOutput;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DataHelper
{
    private static final String DATABASE_NAME = "example.db";
    private static final int DATABASE_VERSION = 1;
    private static String TABLE_NAME = "usuarios";
    private Context context;
    public SQLiteDatabase db;

    private SQLiteStatement insertStmt;

    private static final String INSERT = "insert into "
            + TABLE_NAME + "(name) values (?)";
    private static String colunas;

    public DataHelper(Context context)
    {
        this.TABLE_NAME = "usuarios";
        this.colunas = "(" +
            "id_usuario int primary key not null," +
            "nome VARCHAR(50) not null, " +
            "email VARCHAR(50) not null, " +
            "senha VARCHAR(50) not null" +
            ");";
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        //db.execSQL()
        //db.execSQL();
        //this.insertStmt = this.db.compileStatement(INSERT);
    }

    public ArrayList<String> Listar(String tabela, String colunas)
    {
        String selectQuery =
                "SELECT"+ colunas +" FROM "+ tabela;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        ArrayList<String> conversor = new ArrayList<>();
        do
        {
            conversor.add(cursor.getString(cursor.getColumnIndex("id_usuario")) + " - " + cursor.getString(cursor.getColumnIndex("nome")));
        } while (cursor.moveToNext());

        return conversor;
    }

    public Cursor ListarWhere(String tabela, String colunas, String campo, String valor)
    {
        String selectQuery =
                "SELECT "+ colunas +" FROM "+ tabela + " WHERE "+ campo + " = " + "'" + valor + "';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor Listar2(String tabela, String colunas)
    {
        String selectQuery =
                "SELECT "+ colunas +" FROM "+ tabela;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public void insert(String tabela, String valores)
    {
        String sql = "INSERT INTO " + tabela +
                " VALUES("+ valores +");";
        db.execSQL(sql);
        System.out.println();
    }

    public void deleteAll(String TABLE_NAME)
    {
        this.db.delete(TABLE_NAME, null, null);
    }

    public int Autoincremento(String tabela, String id)
    {
        String selectQuery = "SELECT "+ id +" FROM "+ tabela + " ORDER BY " + id + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0)
        {
            return cursor.getInt(cursor.getColumnIndex(id)) + 1;
        }
        else
        {
            return 1;
        }
    }
    
    public void Delete(String tabela, String id, String valor)
    {
        db.execSQL("DELETE FROM " + tabela + " WHERE " + id + " = '" + valor + "';");
    }
    
    public void Update(String tabela, String campo, String valor, String id, String valor_id)
    {
        db.execSQL("UPDATE " + tabela + " SET " + campo + " = '" + valor + "' WHERE " + id + " = '" + valor_id + "';");
    }

    private static class OpenHelper extends SQLiteOpenHelper
    {
        OpenHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
            db.execSQL("CREATE TABLE " + TABLE_NAME + " " + colunas);
            System.out.println("Criando");
            db.execSQL("CREATE TABLE carros (" +
                    "id_carro int primary key not null, " +
                    "nome VARCHAR(50) not null, " +
                    "marca VARCHAR(50) null, " +
                    "modelo VARCHAR(50) null, " +
                    "id_proprietario int not null, " +
                    "FOREIGN KEY(id_proprietario) REFERENCES usuarios(id_usuario));");

            db.execSQL("CREATE TABLE itens_carro (" +
                    "id_item int primary key not null," +
                    "nome VARCHAR(50) not null," +
                    "km_troca int not null, " +
                    "meses_troca int not null," +
                    "descricao VARCHAR(450) null);");
            
            db.execSQL("CREATE TABLE revisao_itens_carro( " +
                    "id_revisao_item_carro int primary key not null," +
                    "Carros_id_carro int not null," +
                    "Item_id_item int not null," +
                    "status VARCHAR(25) null," +
                    "km_troca int null," +
                    "data_renovacao date null," +
                    "FOREIGN KEY(Carros_id_carro) REFERENCES carros(id_carro),"
                    + "FOREIGN KEY(Item_id_item) REFERENCES itens_carro(id_item));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w("Example", "Upgrading database, this will drop tables and recreate.");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
