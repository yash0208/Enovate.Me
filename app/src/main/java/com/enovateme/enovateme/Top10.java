package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import static maes.tech.intentanim.CustomIntent.customType;

public class Top10 extends AppCompatActivity {
    RecyclerView rec,summery,recomanded,rec4;
    Adapter adapter;
    Adapter2 adapter2,adapter3;
    private ChipNavigationBar chipNavigationBar;

    Adapter1 adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10);
        rec=findViewById(R.id.rec1);
        chipNavigationBar=findViewById(R.id.chip);
        chipNavigationBar.setItemSelected(R.id.home, true);
        summery=findViewById(R.id.rec2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        recomanded=findViewById(R.id.rec3);
        rec4=findViewById(R.id.rec4);
        Query query= FirebaseDatabase.getInstance().getReference().child("Top10 PDF");
        Query query1=FirebaseDatabase.getInstance().getReference().child("Top10 Image");
        FirebaseRecyclerOptions<Books1> options1 =
                new FirebaseRecyclerOptions.Builder<Books1>()
                        .setQuery(query1,Books1.class)
                        .setLifecycleOwner(this)
                        .build();
        adapter3=new Adapter2(options1);
        rec4.setAdapter(adapter3);
        Query query2=FirebaseDatabase.getInstance().getReference().child("Top Most List");
        FirebaseRecyclerOptions<Books1> options2 =
                new FirebaseRecyclerOptions.Builder<Books1>()
                        .setQuery(query2,Books1.class)
                        .setLifecycleOwner(this)
                        .build();
        adapter2=new Adapter2(options2);
        recomanded.setLayoutManager(new LinearLayoutManager(this));
        recomanded.setAdapter(adapter2);
        summery.setLayoutManager(new LinearLayoutManager(this));
        adapter1=new Adapter1(options1);
        summery.setAdapter(adapter1);
        rec.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Books1> options =
                new FirebaseRecyclerOptions.Builder<Books1>()
                        .setQuery(query,Books1.class)
                        .setLifecycleOwner(this)
                        .build();
        adapter=new Adapter(options);
        rec.setAdapter(adapter);
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.home:
                        rec.setVisibility(View.VISIBLE);
                        summery.setVisibility(View.GONE);

                        break;
                    case R.id.idea:
                        rec.setVisibility(View.GONE);
                        summery.setVisibility(View.VISIBLE);

                        break;

                }

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        adapter1.startListening();
        adapter2.startListening();
        adapter3.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Top10.this,Home_Activity.class);

        startActivity(i);
    }

    public void back(View view) {
        Intent i=new Intent(Top10.this,Home_Activity.class);

        startActivity(i);
    }

    public class Adapter2 extends FirebaseRecyclerAdapter<Books1, Adapter2.viewholder> {
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter2(@NonNull FirebaseRecyclerOptions<Books1> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Adapter2.viewholder viewholder, int i, @NonNull final Books1 books1) {
            Glide.with(getApplicationContext()).load(books1.getPic()).fitCenter().into(viewholder.image);
            viewholder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i1=new Intent(Top10.this,FullScreenImage.class);

                    i1.putExtra("Link",books1.getPic());
                    startActivity(i1);
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
    public class Adapter1 extends FirebaseRecyclerAdapter<Books1, Adapter1.viewholder>{
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter1(@NonNull FirebaseRecyclerOptions<Books1> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Adapter1.viewholder viewholder, int i, @NonNull final Books1 books1) {
            Glide.with(getApplicationContext()).load(books1.getPic()).fitCenter().into(viewholder.image);
            viewholder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i1=new Intent(Top10.this,FullScreenImage.class);
                    i1.putExtra("Link",books1.getPic());
                    startActivity(i1);

                }
            });
        }

        @NonNull
        @Override
        public Adapter1.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.imagesa, parent, false);

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
    public class Adapter extends FirebaseRecyclerAdapter<Books1, Adapter.viewholder>{
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter(@NonNull FirebaseRecyclerOptions<Books1> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Adapter.viewholder viewholder, int i, @NonNull final Books1 books1) {

            Glide.with(getApplicationContext()).load(books1.getPic()).fitCenter().into(viewholder.image);
            viewholder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(Top10.this,ViewInfozine.class);
                    i.putExtra("Link",books1.getLink());
                    i.putExtra("Name",books1.getGenre());
                    startActivity(i);

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
