package com.enovateme.enovateme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static maes.tech.intentanim.CustomIntent.customType;

public class StartEarning extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_earning);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(StartEarning.this,Home_Activity.class);

        startActivity(i);
    }

    public void back(View view) {
        Intent i=new Intent(StartEarning.this,Home_Activity.class);

        startActivity(i);
    }

    public void article(View view) {
        Intent i=new Intent(StartEarning.this,AddArticle.class);

        startActivity(i);
    }

    public void consultant(View view) {
        Intent i=new Intent(StartEarning.this,Want_To_Be_Consultant.class);

        startActivity(i);
    }

    public void freelancer(View view) {
        Intent i=new Intent(StartEarning.this,HierRequst.class);

        startActivity(i);
    }

    public void addw(View view) {
        View view1= LayoutInflater.from(getApplicationContext()).inflate(R.layout.done_hire, (LinearLayout)findViewById(R.id.conta));
        final AlertDialog dialog=new AlertDialog.Builder(StartEarning.this).setView(view1).create();
        Button done=view1.findViewById(R.id.done);
        TextView collab=view1.findViewById(R.id.collab);
        collab.setText("Want To Add Your Workshop ?");
        ImageView image=view1.findViewById(R.id.image);
        image.setImageResource(R.drawable.collab1);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        TextView des=view1.findViewById(R.id.des);
        des.setText("Look Like You Want To Add Workshop To Teach Others Your Skill Mail Us Using Below Button We Will Reach You Soon");
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mailer = new Intent(Intent.ACTION_SEND);
                mailer.setType("text/plain");
                mailer.putExtra(Intent.EXTRA_EMAIL, new String[]{"enovativeeliteclub1907@gmail.com"});
                mailer.putExtra(Intent.EXTRA_SUBJECT, "Add Workshop");
                mailer.putExtra(Intent.EXTRA_TEXT, "Description Regarding Workshop");
                startActivity(Intent.createChooser(mailer, "Send email..."));
            }
        });
        dialog.show();
    }

    public void cola(View view) {
        View view1= LayoutInflater.from(getApplicationContext()).inflate(R.layout.done_hire, (LinearLayout)findViewById(R.id.conta));
        final AlertDialog dialog=new AlertDialog.Builder(StartEarning.this).setView(view1).create();
        Button done=view1.findViewById(R.id.done);
        TextView collab=view1.findViewById(R.id.collab);
        collab.setText("Want Collaboration With Us ?");
        ImageView image=view1.findViewById(R.id.image);
        image.setImageResource(R.drawable.collab1);
        TextView des=view1.findViewById(R.id.des);
        des.setText("Look Like You Want Collaboration With Us For Collaboration For That Just Click On Below Button And Mail Us We Will Reach You Soon.");
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mailer = new Intent(Intent.ACTION_SEND);
                mailer.setType("text/plain");
                mailer.putExtra(Intent.EXTRA_EMAIL, new String[]{"enovativeeliteclub1907@gmail.com"});
                mailer.putExtra(Intent.EXTRA_SUBJECT, "Collaboration With Us");
                mailer.putExtra(Intent.EXTRA_TEXT, "Description Regarding Collaboration");
                startActivity(Intent.createChooser(mailer, "Send email..."));
            }
        });
        dialog.show();
    }
}
