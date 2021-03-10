package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static maes.tech.intentanim.CustomIntent.customType;

public class Collaboration extends AppCompatActivity {
    RecyclerView rec;
    Adapter adapter;
    private FloatingActionButton mMainAddFab, mAddUserFab, mAddContactFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collaboration);
        mMainAddFab = findViewById(R.id.main_add_fab);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        LinearLayout container1=findViewById(R.id.container);
        AdView adView;
        adView =new AdView(this,"227638281969200_227643145302047", AdSize.BANNER_HEIGHT_50);
        container1.addView(adView);
        adView.loadAd();
        mMainAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1= LayoutInflater.from(getApplicationContext()).inflate(R.layout.done_hire, (LinearLayout)findViewById(R.id.conta));

                final AlertDialog dialog=new AlertDialog.Builder(Collaboration.this).setView(view1).create();
                Button done=view1.findViewById(R.id.done);
                TextView collab=view1.findViewById(R.id.collab);
                collab.setText("Want Collaboration With Us ?");
                ImageView image=view1.findViewById(R.id.image);
                image.setImageResource(R.drawable.collab1);
                TextView des=view1.findViewById(R.id.des);
                des.setText("Looks Like You Want Collaborate With Us For Collaborate For That Just Click On Below Button And Mail Us We Will Reach You Soon.");
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
        });
        rec=findViewById(R.id.rec3);
        rec.setLayoutManager(new LinearLayoutManager(this));
        Query query= FirebaseDatabase.getInstance().getReference().child("Collaboration Details");
        FirebaseRecyclerOptions<CollaborationClass> options =
                new FirebaseRecyclerOptions.Builder<CollaborationClass>()
                        .setQuery(query,CollaborationClass.class)
                        .build();
        adapter=new Adapter(options);
        rec.setAdapter(adapter);
        adapter.startListening();
    }

    public void back(View view) {
        Intent i=new Intent(Collaboration.this,Home_Activity.class);

        startActivity(i);
    }

    public class Adapter extends FirebaseRecyclerAdapter<CollaborationClass,Adapter.viewholder>{

        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter(@NonNull FirebaseRecyclerOptions<CollaborationClass> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Adapter.viewholder viewholder, int i, @NonNull CollaborationClass collaborationClass) {
            viewholder.des.setText(collaborationClass.getDescription());
            viewholder.link.setText(collaborationClass.getLink());
            viewholder.name.setText(collaborationClass.getName());
            Glide.with(getApplicationContext()).load(collaborationClass.getPic1()).centerCrop().into(viewholder.i1);
            Glide.with(getApplicationContext()).load(collaborationClass.getPic2()).centerCrop().into(viewholder.i2);
            Glide.with(getApplicationContext()).load(collaborationClass.getPic3()).centerCrop().into(viewholder.i3);
        }

        @NonNull
        @Override
        public Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.collab_card, parent, false);

            return new Adapter.viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder {
            TextView des,link,name;
            ImageView i1,i2,i3;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                des=itemView.findViewById(R.id.collaboration_details);
                name=itemView.findViewById(R.id.collaboration_Name);
                link=itemView.findViewById(R.id.collaboration_link);
                i1=itemView.findViewById(R.id.im1);
                i2=itemView.findViewById(R.id.im2);
                i3=itemView.findViewById(R.id.im3);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Collaboration.this,Home_Activity.class);

        startActivity(i);
    }
}
