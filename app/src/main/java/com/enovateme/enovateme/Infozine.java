package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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

public class Infozine extends AppCompatActivity {
    ImageView t11,t12,t13;
    DatabaseReference databaseReference;
    Adapter adapter;
    TextView t,f,r,v;
    private ChipNavigationBar chipNavigationBar;
    Adapter1 adapter1;
    String Type1,Type;
    LinearLayout linearLayoutManager;
    Adapter a;
    RecyclerView rec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infozine);
        chipNavigationBar=findViewById(R.id.chip);
        chipNavigationBar.setItemSelected(R.id.home, true);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Infozine_Header");
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        ViewFlipper mViewFlipper=findViewById(R.id.viewFlipper);

        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(4000);
        mViewFlipper.startFlipping();
        mViewFlipper.setInAnimation(this,R.anim.left_in);
        mViewFlipper.setOutAnimation(this,R.anim.left_out);
        t11=findViewById(R.id.t11);
        t12=findViewById(R.id.t12);
        t13=findViewById(R.id.t13);
        linearLayoutManager=findViewById(R.id.linearLayout4);
        v=findViewById(R.id.video);
        Query databaseReference1= FirebaseDatabase.getInstance().getReference("User Data").orderByChild("UserId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference1.addValueEventListener(new ValueEventListener() {
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
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                Glide.with(getApplicationContext()).load(ds.child("1").getValue().toString()).into(t11);
                Glide.with(getApplicationContext()).load(ds.child("2").getValue().toString()).into(t12);
                Glide.with(getApplicationContext()).load(ds.child("3").getValue().toString()).into(t13);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query=FirebaseDatabase.getInstance().getReference().child("Monthly Infozine" +
                "" +
                "" +
                "");
        f=findViewById(R.id.feed);
        r=findViewById(R.id.research);

        rec=findViewById(R.id.rec);
        rec.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Tips_Class> options =
                new FirebaseRecyclerOptions.Builder<Tips_Class>()
                        .setQuery(query,Tips_Class.class)
                        .setLifecycleOwner(this)
                        .build();
        a=new Adapter(options);
        rec.setAdapter(a);

        f.setOnClickListener(new View.OnClickListener() {






            @Override
            public void onClick(View view) {

                r.setTextColor(Color.parseColor("#ff515c6f"));
                f.setTextColor(Color.parseColor("#ffff6969"));
                v.setTextColor(Color.parseColor("#ff515c6f"));


                rec.setLayoutManager(new LinearLayoutManager(Infozine.this));
                linearLayoutManager.setVisibility(View.VISIBLE);

                Query query=FirebaseDatabase.getInstance().getReference().child("Monthly Infozine");
                FirebaseRecyclerOptions<Tips_Class> options =
                        new FirebaseRecyclerOptions.Builder<Tips_Class>()
                                .setQuery(query,Tips_Class.class)
                                .build();
                a=new Adapter(options);
                rec.setAdapter(a);
                a.startListening();
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r.setTextColor(Color.parseColor("#ffff6969"));
                f.setTextColor(Color.parseColor("#ff515c6f"));
                v.setTextColor(Color.parseColor("#ff515c6f"));
                linearLayoutManager.setVisibility(View.GONE);
                rec.setLayoutManager(new LinearLayoutManager(Infozine.this));
                Query query1= FirebaseDatabase.getInstance().getReference().child("Infozine Issue");
                FirebaseRecyclerOptions<tips_pic> option1 =
                        new FirebaseRecyclerOptions.Builder<tips_pic>()
                                .setQuery(query1,tips_pic.class)
                                .build();
                adapter1=new Adapter1(option1);
                rec.setAdapter(adapter1);
                adapter1.startListening();
            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r.setTextColor(Color.parseColor("#ff515c6f"));
                f.setTextColor(Color.parseColor("#ff515c6f"));
                v.setTextColor(Color.parseColor("#ffff6969"));
                linearLayoutManager.setVisibility(View.GONE);
                Query query=FirebaseDatabase.getInstance().getReference().child("infozine");
                FirebaseRecyclerOptions<Tips_Class> options =
                        new FirebaseRecyclerOptions.Builder<Tips_Class>()
                                .setQuery(query,Tips_Class.class)
                                .build();
                a=new Adapter(options);
                rec.setLayoutManager(new LinearLayoutManager(Infozine.this));
                rec.setAdapter(a);
                a.startListening();
            }
        });
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.home:
                        rec.setLayoutManager(new LinearLayoutManager(Infozine.this));
                        linearLayoutManager.setVisibility(View.VISIBLE);

                        Query query=FirebaseDatabase.getInstance().getReference().child("Monthly Infozine");
                        FirebaseRecyclerOptions<Tips_Class> options =
                                new FirebaseRecyclerOptions.Builder<Tips_Class>()
                                        .setQuery(query,Tips_Class.class)
                                        .build();
                        a=new Adapter(options);
                        rec.setAdapter(a);
                        a.startListening();

                        break;
                    case R.id.idea:
                        linearLayoutManager.setVisibility(View.GONE);
                        rec.setLayoutManager(new LinearLayoutManager(Infozine.this));
                        Query query1= FirebaseDatabase.getInstance().getReference().child("Infozine Issue");
                        FirebaseRecyclerOptions<tips_pic> option1 =
                                new FirebaseRecyclerOptions.Builder<tips_pic>()
                                        .setQuery(query1,tips_pic.class)
                                        .build();
                        adapter1=new Adapter1(option1);
                        rec.setAdapter(adapter1);
                        adapter1.startListening();
                        break;
                    case R.id.profile:
                        linearLayoutManager.setVisibility(View.GONE);
                        Query query2=FirebaseDatabase.getInstance().getReference().child("infozine");
                        FirebaseRecyclerOptions<Tips_Class> options2 =
                                new FirebaseRecyclerOptions.Builder<Tips_Class>()
                                        .setQuery(query2,Tips_Class.class)
                                        .build();
                        a=new Adapter(options2);
                        rec.setLayoutManager(new LinearLayoutManager(Infozine.this));
                        rec.setAdapter(a);
                        a.startListening();
                        break;

                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Infozine.this,Home_Activity.class);

        startActivity(i);
    }

    public void back(View view) {
        Intent i=new Intent(Infozine.this,Home_Activity.class);

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
                        Toast.makeText(Infozine.this,"Join Us For Issue Wise Infozine",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Intent i=new Intent(Infozine.this,IssueWiseInfozine.class);

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
    @Override
    protected void onStart() {
        super.onStart();
a.startListening();
    }
    public  class Adapter extends FirebaseRecyclerAdapter<Tips_Class, Adapter.viewholder>{
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
        protected void onBindViewHolder(@NonNull Adapter.viewholder viewholder, int i1, @NonNull final Tips_Class tips) {
            viewholder.name.setText(tips.getName());
            Log.e("Yash",tips.getName());
            Glide.with(getApplicationContext()).load(tips.getUrl()).centerCrop().fitCenter().into(viewholder.im);
            viewholder.des.setText(tips.getDes());
            String Type1=tips.getType();
            if(Type1.equals("Normal")){
                viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(Infozine.this,ViewInfozine.class);
                        i.putExtra("Link",tips.getLink());
                        i.putExtra("Name",tips.getName());

                        startActivity(i);
                    }
                });
            }
            else {
                if(Type.equals("Normal")){
                    viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(Infozine.this,"Join Us For Premium Content",Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else {
                    viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i=new Intent(Infozine.this,ViewInfozine.class);
                            i.putExtra("Link",tips.getLink());
                            i.putExtra("Name",tips.getName());

                            startActivity(i);
                        }
                    });

                }
            }

        }

        @NonNull
        @Override
        public Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tips_card, parent, false);

            return new Adapter.viewholder(view);

        }

        public class viewholder extends RecyclerView.ViewHolder{
            ImageView im;
            TextView name,des;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                im=itemView.findViewById(R.id.image1);
                name=itemView.findViewById(R.id.name1);
                des=itemView.findViewById(R.id.description1);
            }
        }
    }
    }

