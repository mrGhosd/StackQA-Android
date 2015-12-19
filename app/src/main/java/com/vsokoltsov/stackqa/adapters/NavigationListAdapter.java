package com.vsokoltsov.stackqa.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.controllers.AppController;
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
        final TextView text = (TextView) convertView.findViewById(R.id.navigationText);
        final ImageView image = (ImageView) convertView.findViewById(R.id.navigationImage);

        // getting movie data for the row
        final NavigationItem navigation = navigationList.get(position);


        if (navigation.isUserCell()){

            String url = AppController.APP_HOST + navigation.getUser().getAvatarUrl();
            ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    int avatarSize = (int) activity.getResources().getDimension(R.dimen.user_profile_avatar_in_navigation_menu);
                    int avatarMargin = (int) activity.getResources().getDimension(R.dimen.user_profile_avatar_top_margin);
                    text.setText(navigation.getUser().getCorrectNaming());
                    text.setTextSize(16);
                    setMarginsForTextField(text, avatarMargin);
                    image.setImageBitmap(getRoundedShape(response));
                    image.getLayoutParams().height = avatarSize;
                    image.getLayoutParams().width = avatarSize;
                }
            }, 0, 0, null, null);
            AppController.getInstance().addToRequestQueue(ir);
        }
        else {
            text.setText(navigation.getTitle());
            image.setImageResource(navigation.getImage());
        }
        return convertView;
    }

    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 100;
        int targetHeight = 100;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }
    public void setMarginsForTextField(TextView textview, int margin) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textview.getLayoutParams();
        params.setMargins(15, margin, 0, 0);
        textview.setLayoutParams(params);
    }
}
