package com.enovateme.enovateme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFinal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFinal extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView rec;
    HomeFinal.mp_slide_adepter adapter;
    ImageView t1,t2,t3,i1,i2,i3,i4;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFinal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFinal.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFinal newInstance(String param1, String param2) {
        HomeFinal fragment = new HomeFinal();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home_final, container, false);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Main_Page_Header");
                Query query= FirebaseDatabase.getInstance().getReference().child("MainPage");
                rec=view.findViewById(R.id.viewPager);
        ViewFlipper mViewFlipper=view.findViewById(R.id.viewFlipper);
        t2=view.findViewById(R.id.t2);
        t3=view.findViewById(R.id.t3);
        t1=view.findViewById(R.id.t1);
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(4000);
        mViewFlipper.startFlipping();
        mViewFlipper.setInAnimation(getActivity(),R.anim.left_in);
        mViewFlipper.setOutAnimation(getActivity(),R.anim.left_out);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                Glide.with(getContext()).load(ds.child("1").getValue()).fitCenter().into(t1);
                Glide.with(getContext()).load(ds.child("2").getValue()).fitCenter().into(t2);
                Glide.with(getContext()).load(ds.child("3").getValue()).fitCenter().into(t3);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<cul> options =
                new FirebaseRecyclerOptions.Builder<cul>()
                        .setQuery(query,cul.class)
                        .build();
        adapter=new mp_slide_adepter(options);
        rec.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()){@Override
        public void smoothScrollToPosition(RecyclerView recyclerView,        RecyclerView.State state, int position) {
            LinearSmoothScroller smoothScroller = new                LinearSmoothScroller(getActivity()) {
                private static final float SPEED = 3000;// Change this                value (default=25f)
                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return SPEED / displayMetrics.densityDpi;
                }
            };
            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
        }  };
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rec.setLayoutManager(linearLayoutManager);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                rec.smoothScrollToPosition(adapter.getItemCount()-1);
            }
        },2000);
        adapter.startListening();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    public void autoScroll(){
        final int speedScroll = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                if(count == adapter.getItemCount())
                    count =0;
                if(count < adapter.getItemCount()){
                    rec.smoothScrollToPosition(++count);
                    handler.postDelayed(this,speedScroll);
                }
            }
        };
        handler.postDelayed(runnable,speedScroll);
    }
    public  class mp_slide_adepter extends FirebaseRecyclerAdapter<cul, mp_slide_adepter.viewholder> {
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */

        public mp_slide_adepter(@NonNull FirebaseRecyclerOptions<cul> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull viewholder viewholder, int i, @NonNull cul cul) {
            try {
                Glide.with(getContext()).load(cul.getLink()).into(viewholder.imageView);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }



        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.slide_container, parent, false);

            return new viewholder(view);
        }

        class viewholder extends RecyclerView.ViewHolder{
            private RoundedImageView imageView;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                imageView =itemView.findViewById(R.id.image);

            }
        }
    }

}
