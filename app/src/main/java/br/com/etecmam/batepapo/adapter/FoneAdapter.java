package br.com.etecmam.batepapo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.etecmam.batepapo.R;

public class FoneAdapter extends BaseAdapter {

    private List<String> fones;
    private Context contexto;


    public FoneAdapter(Context contexto, List<String> fones) {
        this.fones = fones;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return fones.size();
    }

    @Override
    public Object getItem(int i) {
        return fones.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = LayoutInflater.from(contexto).inflate( R.layout.layout_telefone, null);
        ( (TextView) v.findViewById(R.id.item_fone) ).setText( fones.get(i));
        return v;
    }
}
