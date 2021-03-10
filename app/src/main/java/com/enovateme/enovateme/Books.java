package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import static maes.tech.intentanim.CustomIntent.customType;

public class Books extends AppCompatActivity {
    RecyclerView rec,summery,recomanded,rec4;
    Adapter adapter;
    Adapter2 adapter2,adapter3;

    private ChipNavigationBar chipNavigationBar;

    String Type;
    Adapter1 adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        rec=findViewById(R.id.rec1);
        LinearLayout container1=findViewById(R.id.container);
        chipNavigationBar=findViewById(R.id.chip);
        chipNavigationBar.setItemSelected(R.id.home, true);
        AdView adView;
        adView =new AdView(this,"227638281969200_227643145302047", AdSize.BANNER_HEIGHT_50);
        container1.addView(adView);
        adView.loadAd();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        summery=findViewById(R.id.rec2);
        recomanded=findViewById(R.id.rec3);
        rec4=findViewById(R.id.rec4);
        recomanded.setVisibility(View.GONE);
        rec4.setVisibility(View.GONE);
        Query query= FirebaseDatabase.getInstance().getReference().child("Books Genres");
        Query query1=FirebaseDatabase.getInstance().getReference().child("Summery");
        Query query3=FirebaseDatabase.getInstance().getReference().child("Our Unique Publish");
        FirebaseRecyclerOptions<Books1> options5 =
                new FirebaseRecyclerOptions.Builder<Books1>()
                        .setQuery(query1,Books1.class)
                        .setLifecycleOwner(this)
                        .build();
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
        FirebaseRecyclerOptions<Books1> options1 =
                new FirebaseRecyclerOptions.Builder<Books1>()
                        .setQuery(query3,Books1.class)
                        .setLifecycleOwner(this)
                        .build();
        adapter3=new Adapter2(options1);
        rec4.setAdapter(adapter3);
        rec4.setLayoutManager(new GridLayoutManager(this,2));
        Query query2=FirebaseDatabase.getInstance().getReference().child("Book");
        FirebaseRecyclerOptions<Books1> options2 =
                new FirebaseRecyclerOptions.Builder<Books1>()
                        .setQuery(query2,Books1.class)
                        .setLifecycleOwner(this)
                        .build();
        adapter2=new Adapter2(options2);
        recomanded.setLayoutManager(new GridLayoutManager(this,2));
        recomanded.setAdapter(adapter2);
        summery.setLayoutManager(new GridLayoutManager(this,2));
        adapter1=new Adapter1(options5);
        summery.setAdapter(adapter1);
        rec.setLayoutManager(new GridLayoutManager(this,2));
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
                       summery.setVisibility(View.VISIBLE);
                       rec4.setVisibility(View.GONE);
                       recomanded.setVisibility(View.GONE);
                       break;
                    case R.id.idea:
                        summery.setVisibility(View.GONE);
                        rec4.setVisibility(View.VISIBLE);
                        recomanded.setVisibility(View.GONE);
                        break;
                    case R.id.profile:

                        summery.setVisibility(View.GONE);
                        rec4.setVisibility(View.GONE);
                        recomanded.setVisibility(View.VISIBLE);
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

    public void back(View view) {
        Intent i=new Intent(Books.this,Home_Activity.class);

        startActivity(i);
    }

    public class Adapter2 extends FirebaseRecyclerAdapter<Books1,Adapter2.viewholder>{
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
        protected void onBindViewHolder(@NonNull viewholder viewholder, int i1, @NonNull final Books1 books1) {
            Glide.with(getApplicationContext()).load(books1.getPic()).centerCrop().into(viewholder.image);

            viewholder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (books1.getType().equals("Normal")){
                        Intent i=new Intent(Books.this,ViewInfozine.class);
                        i.putExtra("Link",books1.getLink());


                        i.putExtra("Name",books1.getGenre());
                        startActivity(i);
                    }
                    else {
                        if(Type.equals("Normal")){
                            Toast.makeText(Books.this,"Join Us For Premium Content",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Intent i=new Intent(Books.this,ViewInfozine.class);
                            i.putExtra("Link",books1.getLink());
                            i.putExtra("Name",books1.getGenre());

                            startActivity(i);
                        }
                    }

                }
            });

        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.book, parent, false);

            return new Adapter2.viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder {
            ImageView image;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                image=itemView.findViewById(R.id.image);
            }
        }
    }
    public class Adapter1 extends FirebaseRecyclerAdapter<Books1,Adapter1.viewholder>{
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
        protected void onBindViewHolder(@NonNull viewholder viewholder, int i1, @NonNull final Books1 books1) {
            Glide.with(getApplicationContext()).load(books1.getPic()).centerCrop().into(viewholder.image);
            viewholder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(Books.this,ViewInfozine.class);
                    i.putExtra("Link",books1.getLink());
                    i.putExtra("Name",books1.getGenre());
                    startActivity(i);

                }
            });

        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.book, parent, false);

            return new Adapter1.viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder {
            ImageView image;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                image=itemView.findViewById(R.id.image);
            }
        }
    }
    public class Adapter extends FirebaseRecyclerAdapter<Books1,Adapter.viewholder>{
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
        protected void onBindViewHolder(@NonNull viewholder viewholder, int i, @NonNull final Books1 books1) {
            Glide.with(getApplicationContext()).load(books1.getPic()).centerCrop().into(viewholder.image);
            viewholder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(Books.this,GenreWiseBook.class);

                    i.putExtra("Name",books1.getGenre());
                    startActivity(i);

                }
            });
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.books_gener, parent, false);

            return new Adapter.viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder {
            ImageView image;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                image=itemView.findViewById(R.id.image);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Books.this,Home_Activity.class);

        startActivity(i);
    }
}
