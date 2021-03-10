package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static maes.tech.intentanim.CustomIntent.customType;

public class GenreWiseBook extends AppCompatActivity {

    RecyclerView rec;
    String type,Type;
    TextView name;
  Adapter1 adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_wise_book);
        name=findViewById(R.id.name);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        rec=findViewById(R.id.rec2);
        Intent i=getIntent();
        type=i.getStringExtra("Name");
        name.setText(type);
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

        Query query1=FirebaseDatabase.getInstance().getReference().child("Book").orderByChild("Type").equalTo(type);
        FirebaseRecyclerOptions<Books1> options1 =
                new FirebaseRecyclerOptions.Builder<Books1>()
                        .setQuery(query1,Books1.class)
                        .setLifecycleOwner(this)
                        .build();
        rec.setLayoutManager(new GridLayoutManager(this,2));
        adapter1=new Adapter1(options1);
        rec.setAdapter(adapter1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter1.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(GenreWiseBook.this,Books.class);

        startActivity(i);
    }

    public void back(View view) {
        Intent i=new Intent(GenreWiseBook.this,Books.class);

        startActivity(i);
    }
    public class Adapter1 extends FirebaseRecyclerAdapter<Books1, Adapter1.viewholder> {
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
        protected void onBindViewHolder(@NonNull Adapter1.viewholder viewholder, int i1, @NonNull final Books1 books1) {
            Glide.with(getApplicationContext()).load(books1.getPic()).centerCrop().into(viewholder.image);
            Log.e("Yaaa",books1.getGenre());
            viewholder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (books1.getType().equals("Normal")){
                        Intent i=new Intent(GenreWiseBook.this,ViewInfozine.class);
                        i.putExtra("Link",books1.getLink());
                        i.putExtra("Name",books1.getGenre());
                        startActivity(i);

                    }
                    else {
                        if(Type.equals("Normal")){
                            Toast.makeText(GenreWiseBook.this,"Join Us For Premium Content",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Intent i=new Intent(GenreWiseBook.this,ViewInfozine.class);
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
        public Adapter1.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
}
