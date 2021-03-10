package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.DialerKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static maes.tech.intentanim.CustomIntent.customType;

public class HierRequst extends AppCompatActivity {
    EditText Name,Skill,Description,Price,Contact;
    Button Done;
    DatabaseReference databaseReference;
    Button close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hier_requst);
        Name=findViewById(R.id.con_name);
        Skill=findViewById(R.id.con_email);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        Description=findViewById(R.id.contact_con);
        Price=findViewById(R.id.col_add);
        Contact=findViewById(R.id.pinb_con);
        close=findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HierRequst.this,HireMe.class);

                startActivity(i);
            }
        });
        Done=findViewById(R.id.save_con);
         databaseReference= FirebaseDatabase.getInstance().getReference().child("Freelancer").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Query query=FirebaseDatabase.getInstance().getReference().child("Freelancer").orderByChild("Id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
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

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(Name.getText())){
                    Toast.makeText(HierRequst.this,"Enter Your Good Name",Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(Skill.getText())){
                    Toast.makeText(HierRequst.this,"Enter Your Skill",Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(Description.getText())){
                    Toast.makeText(HierRequst.this,"Enter Your Skill Description",Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(Price.getText())){
                    Toast.makeText(HierRequst.this,"Enter Your Charge Per Hour",Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(Contact.getText())){
                    Toast.makeText(HierRequst.this,"Enter Your Contact",Toast.LENGTH_LONG).show();
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
                    final AlertDialog dialog=new AlertDialog.Builder(HierRequst.this).setView(view1).create();
                    Button done=view1.findViewById(R.id.done);
                    TextView des=view1.findViewById(R.id.des);
                    des.setText("Your Request Has Been Sent To Our Team We Will Reach Tou As Soon As Possible");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(HierRequst.this,HireMe.class);

        startActivity(i);
    }
}
