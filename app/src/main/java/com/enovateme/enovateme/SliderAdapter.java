package com.enovateme.enovateme;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import java.awt.font.TextAttribute;

class SliderAdapter extends PagerAdapter {
    Context context;
    public SliderAdapter(Context context){
        this.context=context;

    }
    int num1[]={R.string.first,R.string.second};
int image[]={
        R.drawable.img1,R.drawable.img2
    };
    int headings[]={
            R.string.first_slide_title,R.string.second_slide_title
    };
    int description[]={R.string.first_slide_desc,R.string.second_slide_desc};
    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==(ConstraintLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView im=(ImageView)view.findViewById(R.id.slider_image);
        TextView num=view.findViewById(R.id.btn_next);
        num.setText(num1[position]);
        TextView tittle=view.findViewById(R.id.slider_heading);
        TextView des=view.findViewById(R.id.slider_desc);
        im.setImageResource(image[position]);
        tittle.setText(headings[position]);
        des.setText(description[position]);

        container.addView(view);
        return view;
    }
}
