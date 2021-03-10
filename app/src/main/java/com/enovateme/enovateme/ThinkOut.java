package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import static maes.tech.intentanim.CustomIntent.customType;

public class ThinkOut extends AppCompatActivity {
    TextView f,r,v;
    RecyclerView rec;
    String Type;
    Adapter a1,a2,a3;
    private ChipNavigationBar chipNavigationBar;
    Adapter2 a4;
    Adapter1 a5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_think_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        chipNavigationBar=findViewById(R.id.chip);
        chipNavigationBar.setItemSelected(R.id.home, true);
        rec=findViewById(R.id.feed_rec);
        Query databaseReference= FirebaseDatabase.getInstance().getReference("User Data").orderByChild("UserId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){


                        String type=""+ds.child("Type").getValue().toString();
                        Type=type;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query1= FirebaseDatabase.getInstance().getReference().child("ThinkOut").child("Feed");
        FirebaseRecyclerOptions<cul> option1 =
                new FirebaseRecyclerOptions.Builder<cul>()
                        .setQuery(query1,cul.class)
                        .setLifecycleOwner(this)
                        .build();
        a1=new Adapter(option1);
        rec.setAdapter(a1);
        f=findViewById(R.id.feed);
        r=findViewById(R.id.research);
        v=findViewById(R.id.video);
        rec.setLayoutManager(new LinearLayoutManager(this));
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r.setTextColor(Color.parseColor("#ffff6969"));
                f.setTextColor(Color.parseColor("#ff515c6f"));
                v.setTextColor(Color.parseColor("#ff515c6f"));

                Query query1= FirebaseDatabase.getInstance().getReference().child("ThinkOut").child("Research");
                FirebaseRecyclerOptions<cul> option1 =
                        new FirebaseRecyclerOptions.Builder<cul>()
                                .setQuery(query1,cul.class)
                                .build();
                a5=new Adapter1(option1);
                a5.startListening();
                rec.setAdapter(a5);
                a5.startListening();
            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                r.setTextColor(Color.parseColor("#ff515c6f"));
                f.setTextColor(Color.parseColor("#ffff6969"));
                v.setTextColor(Color.parseColor("#ff515c6f"));
                Query query2= FirebaseDatabase.getInstance().getReference().child("ThinkOut").child("Feed");
                FirebaseRecyclerOptions<cul> option2 =
                        new FirebaseRecyclerOptions.Builder<cul>()
                                .setQuery(query2,cul.class)
                                .build();
                a1=new Adapter(option2);
                rec.setAdapter(a1);
                a1.startListening();
                a2.startListening();
            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r.setTextColor(Color.parseColor("#ff515c6f"));
                f.setTextColor(Color.parseColor("#ff515c6f"));
                v.setTextColor(Color.parseColor("#ffff6969"));
                Query query3= FirebaseDatabase.getInstance().getReference().child("ThinkOut").child("videos");
                FirebaseRecyclerOptions<cul> options =
                        new FirebaseRecyclerOptions.Builder<cul>()
                                .setQuery(query3,cul.class)
                                .build();
                a4=new Adapter2(options);
                rec.setAdapter(a4);
                a4.startListening();
            }
        });
        Query query3= FirebaseDatabase.getInstance().getReference().child("ThinkOut").child("videos");
        FirebaseRecyclerOptions<cul> options =
                new FirebaseRecyclerOptions.Builder<cul>()
                        .setQuery(query3,cul.class)
                        .build();
        a4=new Adapter2(options);
        Query query2= FirebaseDatabase.getInstance().getReference().child("ThinkOut").child("Research");
        FirebaseRecyclerOptions<cul> option2 =
                new FirebaseRecyclerOptions.Builder<cul>()
                        .setQuery(query2,cul.class)
                        .build();
        a2=new Adapter(option2);
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.home:
                        Query query2= FirebaseDatabase.getInstance().getReference().child("ThinkOut").child("Feed");
                        FirebaseRecyclerOptions<cul> option2 =
                                new FirebaseRecyclerOptions.Builder<cul>()
                                        .setQuery(query2,cul.class)
                                        .build();
                        a1=new Adapter(option2);
                        rec.setAdapter(a1);
                        a1.startListening();
                        a2.startListening();

                        break;
                    case R.id.idea:
                        Query query1= FirebaseDatabase.getInstance().getReference().child("ThinkOut").child("Research");
                        FirebaseRecyclerOptions<cul> option1 =
                                new FirebaseRecyclerOptions.Builder<cul>()
                                        .setQuery(query1,cul.class)
                                        .build();
                        a5=new Adapter1(option1);
                        a5.startListening();
                        rec.setAdapter(a5);
                        a5.startListening();
                        break;
                    case R.id.profile:
                        Query query3= FirebaseDatabase.getInstance().getReference().child("ThinkOut").child("videos");
                        FirebaseRecyclerOptions<cul> options =
                                new FirebaseRecyclerOptions.Builder<cul>()
                                        .setQuery(query3,cul.class)
                                        .build();
                        a4=new Adapter2(options);
                        rec.setAdapter(a4);
                        a4.startListening();
                        break;
                }

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        a1.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(ThinkOut.this,Home_Activity.class);

        startActivity(i);
    }

    public void back(View view) {
        Intent i=new Intent(ThinkOut.this,Home_Activity.class);

        startActivity(i);
    }
    public class Adapter1 extends FirebaseRecyclerAdapter<cul, Adapter1.viewholder>{
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter1(@NonNull FirebaseRecyclerOptions<cul> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Adapter1.viewholder viewholder, int i, @NonNull final cul cul) {
            Glide.with(getApplicationContext()).load(cul.getLink()).fitCenter().into(viewholder.image);
            viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(ThinkOut.this,ViewInfozine.class);
                    intent.putExtra("Link",cul.getPdf());
                    intent.putExtra("Name",cul.getName());
                    startActivity(intent);

                }
            });
        }

        @NonNull
        @Override
        public Adapter1.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.image_card, parent, false);

            return new Adapter1.viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder {
            ImageView image;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                image=itemView.findViewById(R.id.imageView2);
            }
        }
    }
    public class Adapter2 extends FirebaseRecyclerAdapter<cul, Adapter2.viewholder> {
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter2(@NonNull FirebaseRecyclerOptions<cul> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Adapter2.viewholder viewholder, int i, @NonNull final cul cul) {
            Glide.with(getApplicationContext()).load(cul.getLink()).fitCenter().into(viewholder.image);
            viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Type.equals("Normal")){
                        Toast.makeText(ThinkOut.this,"Join Us For Video Content",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Intent intent=new Intent(ThinkOut.this,Video_Player.class);
                        intent.putExtra("Link",cul.getVideo());
                        startActivity(intent);

                    }
                }
            });
        }

        @NonNull
        @Override
        public Adapter2.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.image_card, parent, false);

            return new Adapter2.viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder {
            ImageView image;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                image=itemView.findViewById(R.id.imageView2);
            }
        }
    }
    public class Adapter extends FirebaseRecyclerAdapter<cul, Adapter.viewholder>{
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter(@NonNull FirebaseRecyclerOptions<cul> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Adapter.viewholder viewholder, int i, @NonNull final cul cul) {
            Glide.with(getApplicationContext()).load(cul.getLink()).fitCenter().into(viewholder.image);
            viewholder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i1=new Intent(ThinkOut.this,FullScreenImage.class);

                    i1.putExtra("Link",cul.getLink());
                    startActivity(i1);
                }
            });
        }

        @NonNull
        @Override
        public Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.image_card, parent, false);

            return new Adapter.viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder {
            ImageView image;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                image=itemView.findViewById(R.id.imageView2);
            }
        }
    }
}
