package com.vsokoltsov.stackqa.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.vsokoltsov.stackqa.R;
import com.vsokoltsov.stackqa.controllers.AppController;
import com.vsokoltsov.stackqa.models.Answer;
import com.vsokoltsov.stackqa.models.AuthManager;
import com.vsokoltsov.stackqa.models.NavigationItem;
import com.vsokoltsov.stackqa.models.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vsokoltsov on 01.10.15.
 */
public class NavigationListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<NavigationItem> navigationList;
    private NavigationItem navigation;

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
        navigation = navigationList.get(position);

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            if (navigation.isUserCell()) {
                convertView = inflater.inflate(R.layout.navigation_header, null);
                configViewForUserItem(convertView);
            }
            else {
                convertView = inflater.inflate(R.layout.navigation_item, null);
                configViewForSimilarItem(convertView);
            }
        return convertView;
    }

    private void configViewForUserItem(View contentView) {
        User user = navigation.getUser();
        final CircleImageView avatarView = (CircleImageView) contentView.findViewById(R.id.circleView);
        TextView userName = (TextView) contentView.findViewById(R.id.name);
        TextView userEmail = (TextView) contentView.findViewById(R.id.email);
        if (user.getCorrectNaming().equals(user.getEmail())) {
            userName.setText(user.getCorrectNaming());
        }
        else {
            userName.setText(user.getCorrectNaming());
            userName.setText(user.getEmail());
        }
        Drawable background = (Drawable) activity.getResources().getDrawable(R.drawable.backgroundploy);
        int width = background.getIntrinsicWidth();
        Bitmap bitmap = ((BitmapDrawable) background).getBitmap();
        int backgroundHeight = (int) activity.getResources().getDimension(R.dimen.user_header_navigation_menu_height);
        Drawable d = new BitmapDrawable(activity.getResources(), Bitmap.createScaledBitmap(bitmap, width, backgroundHeight, true));
        contentView.findViewById(R.id.backgroundView).setBackground(d);
        String url = AppController.APP_HOST + navigation.getUser().getAvatarUrl();
//        nv.setDefaultImageResId(R.drawable.default_image); // image for loading...
//        nv.setImageUrl(imageUrl, ImgController.getInstance().getImageLoader());
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                avatarView.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void configViewForSimilarItem(View convertView) {
        final TextView text = (TextView) convertView.findViewById(R.id.navigationText);
        final ImageView image = (ImageView) convertView.findViewById(R.id.navigationImage);

        text.setText(navigation.getTitle());
        image.setImageResource(navigation.getImage());
    }
}


