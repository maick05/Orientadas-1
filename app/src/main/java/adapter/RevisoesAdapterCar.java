package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maick.revisaocarro.R;
import com.example.maick.revisaocarro.cls.Revisoes;
import com.example.maick.revisaocarro.cls.Revisoes;

import java.util.ArrayList;

/**
 * Created by maick on 30/11/2016.
 */

public class RevisoesAdapterCar extends BaseAdapter
{
    private final ArrayList<Revisoes> revisoes;
    private final Context contexto;

    public RevisoesAdapterCar(ArrayList<Revisoes> lista, Context context)
    {
        this.contexto = context;
        this.revisoes = lista;
    }

    @Override
    public int getCount()
    {
        return revisoes.size();
    }

    @Override
    public Object getItem(int i)
    {
        return revisoes.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return revisoes.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {

        Revisoes rev = revisoes.get(i);
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View v = inflater.inflate(R.layout.item_lista_rev, null);

        TextView nome = (TextView) v.findViewById(R.id.item_nome_rev);
        TextView data = (TextView) v.findViewById(R.id.item_data_rev);
        TextView km = (TextView) v.findViewById(R.id.item_km_rev);

        ImageView foto = (ImageView) v.findViewById(R.id.item_foto);
        /*foto.setImageBitmap();
        String caminhoFoto = aluno.getCaminhoFoto();
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto);
        }*/




        nome.setText(rev.item.nome);
        data.setText("Data que foi revisado:  " + String.valueOf(rev.data_renov));
        km.setText("KM para revisão:  " + String.valueOf(rev.item.km) + " km");
        String dr = "";
        //.setText("Data para revisão:  " + String.valueOf(dr));

        return v;
    }
}