package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maick.revisaocarro.R;
import com.example.maick.revisaocarro.cls.ItensCarro;

import java.io.File;
import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.startActivityForResult;
import static com.example.maick.revisaocarro.R.drawable.item_1;

/**
 * Created by maick on 30/11/2016.
 */

public class RevisoesAdapter extends BaseAdapter
{
    private final ArrayList<ItensCarro> itens;
    private final Context contexto;

    public RevisoesAdapter(ArrayList<ItensCarro> lista, Context context)
    {
        this.contexto = context;
        this.itens = lista;
    }

    @Override
    public int getCount()
    {
        return itens.size();
    }

    @Override
    public Object getItem(int i)
    {
        return itens.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return itens.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ItensCarro ic = itens.get(i);
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View v = inflater.inflate(R.layout.item_lista, null);

        TextView nome = (TextView) v.findViewById(R.id.item_nome);
        TextView mes = (TextView) v.findViewById(R.id.item_mes);
        TextView km = (TextView) v.findViewById(R.id.item_km);

        ImageView foto = (ImageView) v.findViewById(R.id.item_foto);


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
        mes.setText("Meses para revisão:  " + String.valueOf(ic.meses));
        km.setText("KM para revisão:  " + String.valueOf(ic.km));

        return v;
    }
}
