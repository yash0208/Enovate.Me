package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Talks extends AppCompatActivity {
    EditText search_text;
    RecyclerView recyclerView;
    Adapter adapter;
    Adapter2 adapter2;
    Query query,query2;
    private ChipNavigationBar chipNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talks);
        search_text=findViewById(R.id.search_text);
        recyclerView=findViewById(R.id.recycler_view);
        chipNavigationBar=findViewById(R.id.chip);
        chipNavigationBar.setItemSelected(R.id.home, true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String search=search_text.getText().toString();
                Query SearchQuery= FirebaseDatabase.getInstance().getReference().child("Talks").child("Videos").orderByChild("tag").startAt(search.toLowerCase()).endAt(search.toLowerCase() + "\uf8ff");
                FirebaseRecyclerOptions<VideoWithTag> options =
                        new FirebaseRecyclerOptions.Builder<VideoWithTag>()
                                .setQuery(SearchQuery,VideoWithTag.class)
                                .build();
                adapter=new Adapter(options);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
                adapter.startListening();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String search=search_text.getText().toString();
                Query SearchQuery= FirebaseDatabase.getInstance().getReference().child("Talks").child("Videos").orderByChild("tag").startAt(search.toLowerCase()).endAt(search.toLowerCase() + "\uf8ff");
                FirebaseRecyclerOptions<VideoWithTag> options =
                        new FirebaseRecyclerOptions.Builder<VideoWithTag>()
                                .setQuery(SearchQuery,VideoWithTag.class)
                                .build();
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter=new Adapter(options);
                recyclerView.setAdapter(adapter);
                adapter.startListening();
            }
        });
        query= FirebaseDatabase.getInstance().getReference().child("Talks").child("Videos");
        FirebaseRecyclerOptions<VideoWithTag> options =
                new FirebaseRecyclerOptions.Builder<VideoWithTag>()
                        .setQuery(query,VideoWithTag.class)
                        .build();
        adapter=new Adapter(options);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        query2= FirebaseDatabase.getInstance().getReference().child("Talks").child("Tags");
        FirebaseRecyclerOptions<VideoWithTag> option1 =
                new FirebaseRecyclerOptions.Builder<VideoWithTag>()
                        .setQuery(query2,VideoWithTag.class)
                        .build();
        adapter2=new Adapter2(option1);
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.home:
                        recyclerView.setAdapter(adapter);
                        search_text.setVisibility(View.VISIBLE);
                        break;
                    case R.id.idea:
                        recyclerView.setAdapter(adapter2);
                        search_text.setVisibility(View.GONE);
                        break;
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        adapter2.startListening();
    }

    //Creating Adapter For Recyclerview
    public class Adapter extends FirebaseRecyclerAdapter<VideoWithTag,Adapter.viewholder>{

        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter(@NonNull FirebaseRecyclerOptions<VideoWithTag> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Adapter.viewholder viewholder, int i, @NonNull final VideoWithTag videoWithTag) {
            try {
                Glide.with(getApplicationContext()).load(videoWithTag.image).into(viewholder.image);
            } catch (Exception e) {
                e.printStackTrace();
            }
            viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(Talks.this,Video_Player.class);
                    intent.putExtra("Link",videoWithTag.getLink());
                    startActivity(intent);
                }
            });
        }

        @NonNull
        @Override
        public Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.image_card, parent, false);
            return new viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder{
            ImageView image;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                image=itemView.findViewById(R.id.imageView2);
            }
        }
    }
    public class Adapter2 extends FirebaseRecyclerAdapter<VideoWithTag,Adapter2.viewholder>{

        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter2(@NonNull FirebaseRecyclerOptions<VideoWithTag> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Adapter2.viewholder viewholder, int i, @NonNull VideoWithTag videoWithTag) {
            viewholder.name.setText(videoWithTag.getTag().toUpperCase());
        }

        @NonNull
        @Override
        public Adapter2.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.issue, parent, false);
            return new viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder{
            TextView name;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.Name);
            }
        }
    }
    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
