package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Consultant_Profile extends AppCompatActivity implements View.OnClickListener {
    ConstraintLayout expandableView;
    Button arrowBtn;
    CardView cardView;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
    TextView name,field,number,email,des;
    ImageButton flora, fauna;
    String id,form;
    Intent go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant__profile);
        Intent i=getIntent();
        id=i.getStringExtra("Id");
        name=findViewById(R.id.name);
        field=findViewById(R.id.desc);
        number=findViewById(R.id.phoneNumber);
        email=findViewById(R.id.mailNumber);
        des=findViewById(R.id.specs);
        Query databaseReference=FirebaseDatabase.getInstance().getReference("Consultant").orderByChild("Id").equalTo(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                for(DataSnapshot snapshot: snapshot1.getChildren()){
                    String Name=""+snapshot.child("Name").getValue().toString();
                    String Field=""+snapshot.child("Field").getValue().toString();
                    String Description=""+snapshot.child("Description").getValue().toString();
                    String Mail=""+snapshot.child("Mail").getValue().toString();
                    form=""+snapshot.child("Form").getValue().toString();
                    String Number=""+snapshot.child("Contact").getValue().toString();
                    number.setText(Number);
                    name.setText(Name);
                    field.setText(Field);
                    des.setText(Description);
                    email.setText(Mail);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        expandableView = findViewById(R.id.expandableView);
        arrowBtn = findViewById(R.id.arrowBtn);
        cardView = findViewById(R.id.cardView);

        arrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableView.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.VISIBLE);
                    arrowBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.GONE);
                    arrowBtn.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dpa);
                }
            }
        });
        initialize();

        gestureDetector = new GestureDetector(new SwipeGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
    }
    private void initialize() {
        //find view by id to image button
        //set onClickListener to image button
    }

    @Override
    public void onClick(View view) {

    }

    public void lunch(View view) {
        Uri uri=Uri.parse(form);
        Intent i=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }

    private class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_MIN_DISTANCE = 50;
        private static final int SWIPE_MAX_OFF_PATH = 200;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            try {


                float diffAbs = Math.abs(e1.getY() - e2.getY());
                float diff = e1.getX() - e2.getX();

                if (diffAbs > SWIPE_MAX_OFF_PATH)
                    return false;

                // Left swipe
                if (diff > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    onBackPressed();
                }
                // Right swipe
                else if (-diff > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    onBackPressed();
                }
            } catch (Exception e) {
                Log.e("Home", "Error on gestures");
            }
            return false;
        }

    }
}
