package com.example.anhnh.sliderdemo;

/**
 * Created by anhnh on 10/27/2016.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import MyObject.Film;


public class SwipeAdapter extends PagerAdapter {
    //private TypedArray image;
    //private String[] enName;
    private ArrayList<Film> enName;
    private String[] japName;
    private Context context;
    private LayoutInflater layoutInflater;

    public SwipeAdapter(Context context, ArrayList<Film> array) {
        this.enName = array;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return enName.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.swipe_layout, container,false);
        //image = itemView.getResources().obtainTypedArray(R.array.movie_poster);
        //enName = itemView.getResources().getStringArray(R.array.movie_eng_name);
//        japName = itemView.getResources().getStringArray(R.array.jap_name);
        //set layout*/
        ImageView imageView = (ImageView) itemView.findViewById(R.id.image_view);
        TextView textView = (TextView) itemView.findViewById(R.id.text_view);
        //set data every slide
        //imageView.setImageResource(image.getResourceId(position,-1));
        Picasso.with(itemView.getContext()).load(enName.get(position).getImage()).into(imageView);
        textView.setText(enName.get(position).getName());
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
