package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static maes.tech.intentanim.CustomIntent.customType;

public class Tpis_Detailed extends AppCompatActivity {
    RecyclerView rec;
    Adapter a;
    String Name;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpis__detailed);
        Intent i=getIntent();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        Name=i.getStringExtra("Name");
        name=findViewById(R.id.name1);
        name.setText(Name);
        rec=findViewById(R.id.rec);
        rec.setLayoutManager(new LinearLayoutManager(this));
        Query query= FirebaseDatabase.getInstance().getReference().child(Name);
        FirebaseRecyclerOptions<tips_pic> options =
                new FirebaseRecyclerOptions.Builder<tips_pic>()
                        .setQuery(query,tips_pic.class)
                        .setLifecycleOwner(this)
                        .build();
        a=new Adapter(options);
        rec.setAdapter(a);    }

    @Override
    protected void onStart() {
        super.onStart();
        a.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Tpis_Detailed.this,Tips.class);



        startActivity(i);
    }

    public void back(View view) {
        Intent i=new Intent(Tpis_Detailed.this,Tips.class);



        startActivity(i);
    }

    public class Adapter extends FirebaseRecyclerAdapter<tips_pic,Adapter.viewholder> {
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter(@NonNull FirebaseRecyclerOptions<tips_pic> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Adapter.viewholder viewholder, int i, @NonNull final tips_pic tips) {

            Glide.with(getApplicationContext()).load(tips.getLink()).centerCrop().fitCenter().into(viewholder.im);
            viewholder.im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i1=new Intent(Tpis_Detailed.this,FullScreenImage.class);
                    i1.putExtra("Link",tips.getLink());

                    startActivity(i1);
                }
            });
        }

        @NonNull
        @Override
        public Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tips_cards, parent, false);

            return new Adapter.viewholder(view);

        }

        public class viewholder extends RecyclerView.ViewHolder{
            ImageView im;
            TextView name;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                im=itemView.findViewById(R.id.image1);

            }
        }
    }
}
