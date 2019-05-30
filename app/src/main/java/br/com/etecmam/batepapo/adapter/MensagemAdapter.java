package br.com.etecmam.batepapo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.etecmam.batepapo.R;
import br.com.etecmam.batepapo.dmp.Conversa;

public class MensagemAdapter extends BaseAdapter {

    private final Context contexto;
    private final List<Conversa> lista;

    public MensagemAdapter(Context contexto, List<Conversa> lista) {
        this.contexto = contexto;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(contexto).inflate(R.layout.layout_mensagem, null);
        TextView nome = v.findViewById(R.id.mensagem_nome);
        TextView texto = v.findViewById(R.id.mensagem_texto);

        nome.setText( lista.get(i).getNumero() );
        texto.setText( lista.get(i).getTexto() );

        return v;
    }
}
