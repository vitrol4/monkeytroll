package br.edu.ifspsaocarlos.sdm.monkeytroll.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.monkeytroll.R;
import br.edu.ifspsaocarlos.sdm.monkeytroll.model.NavItem;

public class DrawerListAdapter extends ArrayAdapter<NavItem> {
    LayoutInflater inflater;

    public DrawerListAdapter(Activity activity, List<NavItem> navItems) {
        super(activity, R.layout.drawer_item, navItems);
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.drawer_item, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.subTitle = (TextView) convertView.findViewById(R.id.subTitle);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NavItem nav = getItem(position);
        holder.title.setText(nav.getmTitle());
        holder.subTitle.setText(nav.getmSubtitle());
        holder.icon.setImageResource(nav.getmIcon());

        return convertView;
    }

    private static class ViewHolder {
        public TextView title;
        public TextView subTitle;
        public ImageView icon;
    }
}
