package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static maes.tech.intentanim.CustomIntent.customType;

public class Tips extends AppCompatActivity {
    DatabaseReference databaseReference;
    ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9;
    InterstitialAd interstitialAd;
    Adapter a;
    ImageView t1,t2,t3;
    RecyclerView rec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_tips);
        i1=findViewById(R.id.i1);
        i2=findViewById(R.id.i2);
        i3=findViewById(R.id.i3);
        i4=findViewById(R.id.i4);
        i5=findViewById(R.id.i5);
        i6=findViewById(R.id.i6);
        i7=findViewById(R.id.i7);
        i8=findViewById(R.id.i8);
        i9=findViewById(R.id.i9);
        Query query=FirebaseDatabase.getInstance().getReference().child("Tips_Sections");
        rec=findViewById(R.id.rec);
        rec.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Tips_Class> options =
                new FirebaseRecyclerOptions.Builder<Tips_Class>()
                        .setQuery(query,Tips_Class.class)
                        .setLifecycleOwner(this)
                        .build();
        a=new Adapter(options);
        rec.setAdapter(a);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Tips_Logo");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {

                    Glide.with(getApplicationContext()).load(ds.child("1").getValue().toString()).into(i1);
                    Glide.with(getApplicationContext()).load(ds.child("2").getValue().toString()).into(i2);
                    Glide.with(getApplicationContext()).load(ds.child("3").getValue().toString()).into(i3);
                    Glide.with(getApplicationContext()).load(ds.child("4").getValue().toString()).into(i4);
                    Glide.with(getApplicationContext()).load(ds.child("5").getValue().toString()).into(i5);
                    Glide.with(getApplicationContext()).load(ds.child("6").getValue().toString()).into(i6);
                    Glide.with(getApplicationContext()).load(ds.child("7").getValue().toString()).into(i7);
                    Glide.with(getApplicationContext()).load(ds.child("8").getValue().toString()).into(i8);
                    Glide.with(getApplicationContext()).load(ds.child("9").getValue().toString()).into(i9);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        a.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i=new Intent(Tips.this,Home_Activity.class);

        startActivity(i);
    }

    public void back(View view) {
        Intent i=new Intent(Tips.this,Home_Activity.class);

        startActivity(i);
    }

    public  class Adapter extends FirebaseRecyclerAdapter<Tips_Class,Adapter.viewholder>{
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter(@NonNull FirebaseRecyclerOptions<Tips_Class> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull viewholder viewholder, int i, @NonNull final Tips_Class tips) {
                viewholder.name.setText(tips.getName());

                viewholder.des.setVisibility(View.INVISIBLE);
            Log.e("Yash",tips.getName());
                    Glide.with(getApplicationContext()).load(tips.getLink()).centerCrop().fitCenter().into(viewholder.im);
            viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AdRequest adRequest=new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
                    interstitialAd=new InterstitialAd(Tips.this);
                    interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
                    interstitialAd.loadAd(adRequest);
                    if(interstitialAd.isLoaded()||interstitialAd.isLoading()){
                        interstitialAd.show();
                        Intent i=new Intent(Tips.this,Tpis_Detailed.class);
                        i.putExtra("Name",tips.getName());

                        startActivity(i);
                    }
                    else {
                        Intent i=new Intent(Tips.this,Tpis_Detailed.class);
                        i.putExtra("Name",tips.getName());

                        startActivity(i);
                    }

                }
            });
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tips_card, parent, false);

            return new viewholder(view);

        }

        public class viewholder extends RecyclerView.ViewHolder{
            ImageView im;
            TextView name,des;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                im=itemView.findViewById(R.id.image1);
                des=itemView.findViewById(R.id.description1);
                name=itemView.findViewById(R.id.name1);
            }
        }
    }
}
