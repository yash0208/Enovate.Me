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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.api.Quota;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import static maes.tech.intentanim.CustomIntent.customType;

public class frameworks extends AppCompatActivity {
    RecyclerView rec;
    TextView f,r,v;
    Adapter adapter;
    Adapter1 adapter1;
    private ChipNavigationBar chipNavigationBar;

    String Type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frameworks);
        chipNavigationBar=findViewById(R.id.chip);
        chipNavigationBar.setItemSelected(R.id.home, true);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

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

        rec=findViewById(R.id.rec);
        f=findViewById(R.id.feed);
        r=findViewById(R.id.research);
        v=findViewById(R.id.video);
        rec.setLayoutManager(new LinearLayoutManager(this));

        Query query= FirebaseDatabase.getInstance().getReference().child("Framworks").orderByChild("Type").equalTo("Normal");
        FirebaseRecyclerOptions<tips_pic> options =
                new FirebaseRecyclerOptions.Builder<tips_pic>()
                        .setQuery(query,tips_pic.class)
                        .setLifecycleOwner(this)
                        .build();
        Query query1= FirebaseDatabase.getInstance().getReference().child("Frameworks_Fields");
        FirebaseRecyclerOptions<tips_pic> option1 =
                new FirebaseRecyclerOptions.Builder<tips_pic>()
                        .setQuery(query1,tips_pic.class)
                        .setLifecycleOwner(this)
                        .build();
        adapter1=new Adapter1(option1);
        adapter=new Adapter(options);
        adapter.startListening();
        rec.setAdapter(adapter);
            r.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    r.setTextColor(Color.parseColor("#ffff6969"));
                    f.setTextColor(Color.parseColor("#ff515c6f"));
                    v.setTextColor(Color.parseColor("#ff515c6f"));
                    rec.setAdapter(adapter1);
                    adapter1.startListening();
                }
            });
            f.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    r.setTextColor(Color.parseColor("#ff515c6f"));
                    f.setTextColor(Color.parseColor("#ffff6969"));
                    v.setTextColor(Color.parseColor("#ff515c6f"));
                    Query query= FirebaseDatabase.getInstance().getReference().child("Framworks").orderByChild("Type").equalTo("Normal");
                    FirebaseRecyclerOptions<tips_pic> options =
                            new FirebaseRecyclerOptions.Builder<tips_pic>()
                                    .setQuery(query,tips_pic.class)
                                    .build();
                    adapter=new Adapter(options);
                    rec.setAdapter(adapter);
                    adapter.startListening();
                }
            });
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(Type.equals("Normal")){
                        Toast.makeText(frameworks.this,"Join Us For Premium Content",Toast.LENGTH_LONG).show();
                    }
                    else {
                        r.setTextColor(Color.parseColor("#ff515c6f"));
                        f.setTextColor(Color.parseColor("#ff515c6f"));
                        v.setTextColor(Color.parseColor("#ffff6969"));
                        Query query= FirebaseDatabase.getInstance().getReference().child("Framworks").orderByChild("Type").equalTo("Premium");
                        FirebaseRecyclerOptions<tips_pic> options =
                                new FirebaseRecyclerOptions.Builder<tips_pic>()
                                        .setQuery(query,tips_pic.class)
                                        .build();
                        adapter=new Adapter(options);
                        rec.setAdapter(adapter);
                        adapter.startListening();
                    }

                }
            });
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.home:
                        Query query= FirebaseDatabase.getInstance().getReference().child("Framworks").orderByChild("Type").equalTo("Normal");
                        FirebaseRecyclerOptions<tips_pic> options =
                                new FirebaseRecyclerOptions.Builder<tips_pic>()
                                        .setQuery(query,tips_pic.class)
                                        .build();
                        adapter=new Adapter(options);
                        rec.setAdapter(adapter);
                        adapter.startListening();

                        break;
                    case R.id.idea:
                        rec.setAdapter(adapter1);
                        adapter1.startListening();
                        break;
                    case R.id.profile:
                        if(Type.equals("Normal")){
                            Toast.makeText(frameworks.this,"Join Us For Premium Content",Toast.LENGTH_LONG).show();
                            Query query2= FirebaseDatabase.getInstance().getReference().child("Framworks").orderByChild("Type").equalTo("Normal");
                            FirebaseRecyclerOptions<tips_pic> options2 =
                                    new FirebaseRecyclerOptions.Builder<tips_pic>()
                                            .setQuery(query2,tips_pic.class)
                                            .build();
                            adapter=new Adapter(options2);
                            rec.setAdapter(adapter);
                            adapter.startListening();
                            chipNavigationBar.setItemSelected(R.id.home, true);
                            break;
                        }
                        else {
                            Query query1= FirebaseDatabase.getInstance().getReference().child("Framworks").orderByChild("Type").equalTo("Premium");
                            FirebaseRecyclerOptions<tips_pic> options1 =
                                    new FirebaseRecyclerOptions.Builder<tips_pic>()
                                            .setQuery(query1,tips_pic.class)
                                            .build();
                            adapter=new Adapter(options1);
                            rec.setAdapter(adapter);
                            adapter.startListening();
                        }
                        break;

                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(frameworks.this,Home_Activity.class);

        startActivity(i);
    }

    public void back(View view) {
        Intent i=new Intent(frameworks.this,Home_Activity.class);



        startActivity(i);
    }

    public class Adapter1 extends FirebaseRecyclerAdapter<tips_pic, Adapter1.viewholder> {
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter1(@NonNull FirebaseRecyclerOptions<tips_pic> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Adapter1.viewholder viewholder, int i, @NonNull final tips_pic tips) {
            viewholder.name.setText(tips.getName());
            viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Type.equals("Normal")){
                        Toast.makeText(frameworks.this,"Join Us For Issue Wise Frameworks",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Intent i=new Intent(frameworks.this,fieldwise_framwork.class);

                        i.putExtra("Name",tips.getName());
                        startActivity(i);
                    }
                }
            });
        }

        @NonNull
        @Override
        public Adapter1.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.issue, parent, false);

            return new Adapter1.viewholder(view);

        }

        public class viewholder extends RecyclerView.ViewHolder{
            ImageView im;
            TextView name;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.Name);

            }
        }
    }
    public class Adapter extends FirebaseRecyclerAdapter<tips_pic, Adapter.viewholder> {
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
                    Intent i1=new Intent(frameworks.this,FullScreenImage.class);

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
