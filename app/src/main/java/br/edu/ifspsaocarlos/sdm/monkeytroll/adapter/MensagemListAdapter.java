package br.edu.ifspsaocarlos.sdm.monkeytroll.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.monkeytroll.R;
import br.edu.ifspsaocarlos.sdm.monkeytroll.model.Mensagem;
import br.edu.ifspsaocarlos.sdm.monkeytroll.util.ContatoSessionManager;

public class MensagemListAdapter extends ArrayAdapter<Mensagem> {
    LayoutInflater inflater;
    ContatoSessionManager session;

    public MensagemListAdapter(Activity activity, List<Mensagem> navItems) {
        super(activity, R.layout.drawer_item, navItems);
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        session = new ContatoSessionManager(activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Mensagem mensagem = getItem(position);
        String remetenteId = session.getUserDetails().get(ContatoSessionManager.KEY_ID); //id do remetente

        RelativeLayout.LayoutParams right = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        right.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        right.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.mensagem_item, null);
            holder = new ViewHolder();
            holder.mensagem = (TextView) convertView.findViewById(R.id.msgCorpo);
            if (remetenteId.equals(mensagem.getOrigem())) {
                holder.mensagem.setLayoutParams(right);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mensagem.setText(mensagem.getCorpo());
        if (remetenteId.equals(mensagem.getOrigem())) {
            holder.mensagem.setLayoutParams(right);
        }

        return convertView;
    }

    private static class ViewHolder {
        public TextView mensagem;
    }
}
