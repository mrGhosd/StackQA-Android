package com.vsokoltsov.stackqa.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.models.Answer;
import com.vsokoltsov.stackqa.models.NavigationItem;

import java.util.List;

/**
 * Created by vsokoltsov on 01.10.15.
 */
public class NavigationListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<NavigationItem> navigationList;

    public NavigationListAdapter(Activity activity, List<NavigationItem> navigationList) {
        this.activity = activity;
        this.navigationList = navigationList;
    }

    @Override
    public int getCount() {
        return navigationList.size();
    }

    @Override
    public Object getItem(int location) {
        return navigationList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.navigation_item, null);

        //Перечисляем элементы в layout-е строки списка вопросов
        TextView text = (TextView) convertView.findViewById(R.id.navigationText);
        ImageView image = (ImageView) convertView.findViewById(R.id.navigationImage);

        // getting movie data for the row
        NavigationItem navigation = navigationList.get(position);

        text.setText(navigation.getTitle());
        image.setImageResource(navigation.getImage());
        return convertView;
    }
}
