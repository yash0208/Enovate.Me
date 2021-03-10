package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static maes.tech.intentanim.CustomIntent.customType;

public class Framowork extends AppCompatActivity{
    ImageView t1,t2,t3;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framowork);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        ViewFlipper mViewFlipper=findViewById(R.id.viewFlipper);

        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(4000);
        mViewFlipper.startFlipping();
        mViewFlipper.setInAnimation(this,R.anim.left_in);
        mViewFlipper.setOutAnimation(this,R.anim.left_out);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Frameworks_Header");
        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                Glide.with(getApplicationContext()).load(ds.child("1").getValue().toString()).into(t1);
                Glide.with(getApplicationContext()).load(ds.child("2").getValue().toString()).into(t2);
                Glide.with(getApplicationContext()).load(ds.child("3").getValue().toString()).into(t3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void back(View view) {
        Intent i=new Intent(Framowork.this,Home_Activity.class);

        startActivity(i);
    }

    public void open(View view) {
        Intent i=new Intent(Framowork.this,frameworks.class);
        startActivity(i);

    }
}
