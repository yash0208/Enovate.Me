package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import android.animation.Animator;
import android.content.res.Resources;
import android.os.Build;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;

import static maes.tech.intentanim.CustomIntent.customType;

public class Want_Consultancy extends AppCompatActivity {
    EditText Name,Skill,Description,Price,Contact;
    private View background;
    Button Done;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want__consultancy);
        background = findViewById(R.id.background);

        if (savedInstanceState == null) {
            background.setVisibility(View.INVISIBLE);

            final ViewTreeObserver viewTreeObserver = background.getViewTreeObserver();

            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        background.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }

                });
            }

        }
        Name=findViewById(R.id.con_name);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        Skill=findViewById(R.id.con_email);
        Description=findViewById(R.id.contact_con);
        Button close=findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Want_Consultancy.this,HireMe.class);
                startActivity(i);
            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Want Consultancy").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Query query=FirebaseDatabase.getInstance().getReference().child("Want Consultancy").orderByChild("Id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    Name.setText(""+ds.child("Name").getValue().toString());
                    Skill.setText(""+ds.child("Skill").getValue().toString());
                    Description.setText(""+ds.child("Description").getValue().toString());
                    Price.setText(""+ds.child("Charge").getValue().toString());
                    Contact.setText(""+ds.child("Contact").getValue().toString());
                }}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Price=findViewById(R.id.col_add);
        Contact=findViewById(R.id.pinb_con);
        Done=findViewById(R.id.save_con);


        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(Name.getText())){
                    Toast.makeText(Want_Consultancy.this,"Enter Your Good Name",Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(Skill.getText())){
                    Toast.makeText(Want_Consultancy.this,"Enter Your Skill",Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(Description.getText())){
                    Toast.makeText(Want_Consultancy.this,"Enter Your Skill Description",Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(Price.getText())){
                    Toast.makeText(Want_Consultancy.this,"Enter Your Charge Per Hour",Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(Contact.getText())){
                    Toast.makeText(Want_Consultancy.this,"Enter Your Contact",Toast.LENGTH_LONG).show();
                }
                else {
                    HashMap<Object,String> hashMap=new HashMap<>();
                    hashMap.put("Charge",Price.getText().toString());
                    hashMap.put("Description",Description.getText().toString());
                    hashMap.put("Id", FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                    hashMap.put("Skill",Skill.getText().toString());
                    hashMap.put("Name",Name.getText().toString());
                    hashMap.put("Contact",Contact.getText().toString());
                    databaseReference.setValue(hashMap);
                    View view1= LayoutInflater.from(getApplicationContext()).inflate(R.layout.done_hire, (LinearLayout)findViewById(R.id.conta));
                    final AlertDialog dialog=new AlertDialog.Builder(Want_Consultancy.this).setView(view1).create();
                    Button done=view1.findViewById(R.id.done);
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();

                        }
                    });
                    dialog.show();
                }
            }
        });
    }
    private void circularRevealActivity() {
        int cx = background.getRight() - getDips(44);
        int cy = background.getBottom() - getDips(44);

        float finalRadius = Math.max(background.getWidth(), background.getHeight());

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                background,
                cx,
                cy,
                0,
                finalRadius);

        circularReveal.setDuration(3000);
        background.setVisibility(View.VISIBLE);
        circularReveal.start();

    }

    private int getDips(int dps) {
        Resources resources = getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dps,
                resources.getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = background.getWidth() - getDips(44);
            int cy = background.getBottom() - getDips(44);

            float finalRadius = Math.max(background.getWidth(), background.getHeight());
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(background, cx, cy, finalRadius, 0);

            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    background.setVisibility(View.INVISIBLE);
                    finish();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            circularReveal.setDuration(1000);
            circularReveal.start();
        }
        else {
            super.onBackPressed();
        }
    }

    public void back(View view) {
        Intent i=new Intent(Want_Consultancy.this,Consultancy.class);



        startActivity(i);
    }

}

