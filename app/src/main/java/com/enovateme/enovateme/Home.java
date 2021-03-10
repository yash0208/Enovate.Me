package com.enovateme.enovateme;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import static maes.tech.intentanim.CustomIntent.customType;

import com.bumptech.glide.Glide;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.api.AdviceOrBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView Join;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private Context mContext;
    ImageView t1,t2,t3,i1,i2,i3,i4;
    private float initialX;
    DatabaseReference databaseReference;
    FrameLayout idea;
    FrameLayout tips,book1,hire_me,framework,infozine,history,think_out,top10,consultancy,collaboration,workshop,earn;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Main_Page_Header");
        history=view.findViewById(R.id.history);
        workshop=view.findViewById(R.id.workshop);
        ViewFlipper mViewFlipper=view.findViewById(R.id.viewFlipper);

        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(4000);
        mViewFlipper.startFlipping();
        mViewFlipper.setInAnimation(getActivity(),R.anim.left_in);
        mViewFlipper.setOutAnimation(getActivity(),R.anim.left_out);


        LinearLayout container1=view.findViewById(R.id.container);
        AdView adView;
        adView =new AdView(getActivity(),"227638281969200_227643145302047", AdSize.BANNER_HEIGHT_50);
        container1.addView(adView);
        adView.loadAd();
        Join=view.findViewById(R.id.join);
        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1= LayoutInflater.from(getContext()).inflate(R.layout.done_hire, (LinearLayout)view.findViewById(R.id.conta));
                final AlertDialog dialog=new AlertDialog.Builder(getActivity()).setView(view1).create();
                Button done=view1.findViewById(R.id.done);
                TextView collab=view1.findViewById(R.id.collab);
                collab.setText("Thank You For Joining US");
                ImageView image=view1.findViewById(R.id.image);
                image.setImageResource(R.drawable.thanoks);


                TextView des=view1.findViewById(R.id.des);
                des.setText("Your Joining Request Has Been Sent To Our Team We Will Reach You As Soon As Possible");
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Joining Request");

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                HashMap<String, String> hashMap=new HashMap<>();
                                hashMap.put("By",FirebaseAuth.getInstance().getCurrentUser().getUid());
                                hashMap.put("Status","Pending");
                                databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(hashMap);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        earn=view.findViewById(R.id.start_earning);
        earn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),StartEarning.class);
                startActivity(intent);
                customType(getActivity(),"bottom-to-up");
            }
        });
    workshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Workshop.class);
                startActivity(intent);
                customType(getActivity(),"bottom-to-up");
            }
        });
        think_out=view.findViewById(R.id.think_outs);
        consultancy=view.findViewById(R.id.consultancy);
        collaboration=view.findViewById(R.id.collaboration);
        collaboration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Collaboration.class);
                startActivity(intent);
                customType(getActivity(),"bottom-to-up");
            }
        });
        consultancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Consultancy.class);
                startActivity(intent);
                customType(getActivity(),"bottom-to-up");
            }
        });
        top10=view.findViewById(R.id.top10);
        top10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Top10.class);
                startActivity(intent);
                customType(getActivity(),"bottom-to-up");
            }
        });
        think_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ThinkOut.class);
                startActivity(intent);
                customType(getActivity(),"bottom-to-up");
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),History.class);
                startActivity(intent);
                customType(getActivity(),"bottom-to-up");
            }
        });
        t1=view.findViewById(R.id.t1);
        book1=view.findViewById(R.id.books1);
        book1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Books.class);
                startActivity(intent);
                customType(getActivity(),"bottom-to-up");
            }
        });
        hire_me=view.findViewById(R.id.hire_me1);
        hire_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),HireMe.class);
                startActivity(intent);
                customType(getActivity(),"bottom-to-up");
            }
        });
        infozine=view.findViewById(R.id.infozine);
        infozine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Infozine.class);
                startActivity(intent);
                customType(getActivity(),"bottom-to-up");
            }
        });
        idea=view.findViewById(R.id.idea);
        idea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Your_Idea.class);
                startActivity(intent);
                customType(getActivity(),"bottom-to-up");
            }
        });
        t2=view.findViewById(R.id.t2);
        t3=view.findViewById(R.id.t3);
        framework=view.findViewById(R.id.framework);

        framework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Framowork.class);
                startActivity(intent);
                customType(getActivity(),"bottom-to-up");
            }
        });
        i1=view.findViewById(R.id.im11);

        i2=view.findViewById(R.id.im12);

        i3=view.findViewById(R.id.im13);

        i4=view.findViewById(R.id.im14);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                try {
                    Glide.with(getContext()).load(ds.child("1").getValue()).fitCenter().into(t1);
                    Glide.with(getContext()).load(ds.child("2").getValue()).fitCenter().into(t2);
                    Glide.with(getContext()).load(ds.child("3").getValue()).fitCenter().into(t3);
                    Glide.with(getContext()).load(ds.child("4").getValue()).fitCenter().into(i1);
                    Glide.with(getContext()).load(ds.child("5").getValue()).fitCenter().into(i2);
                    Glide.with(getContext()).load(ds.child("6").getValue()).fitCenter().into(i3);
                    Glide.with(getContext()).load(ds.child("7").getValue()).fitCenter().into(i4);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        tips=view.findViewById(R.id.tips);
        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Talks.class);
                startActivity(intent);
                customType(getActivity(),"bottom-to-up");
            }
        });
        return view;
    }



}

