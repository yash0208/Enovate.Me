package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.L;
import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchWorkshop extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter adapter;
    EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_workshop);
        recyclerView=findViewById(R.id.recycler_view);
        search=findViewById(R.id.search_field);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String search_text=search.getText().toString().toLowerCase();
                Query query1= FirebaseDatabase.getInstance().getReference().child("Workshop").orderByChild("tag").startAt(search_text).endAt(search_text + "\uf8ff");;
                FirebaseRecyclerOptions<WorkshopClass> option1 =
                        new FirebaseRecyclerOptions.Builder<WorkshopClass>()
                                .setQuery(query1,WorkshopClass.class)
                                .setLifecycleOwner(SearchWorkshop.this)
                                .build();
                adapter =new Adapter(option1);
                recyclerView.setLayoutManager(new LinearLayoutManager(SearchWorkshop.this));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String search_text=search.getText().toString().toLowerCase();
                Query query1= FirebaseDatabase.getInstance().getReference().child("Workshop").orderByChild("tag").startAt(search_text).endAt(search_text + "\uf8ff");;
                FirebaseRecyclerOptions<WorkshopClass> option1 =
                        new FirebaseRecyclerOptions.Builder<WorkshopClass>()
                                .setQuery(query1,WorkshopClass.class)
                                .setLifecycleOwner(SearchWorkshop.this)
                                .build();
                adapter =new Adapter(option1);
                recyclerView.setLayoutManager(new LinearLayoutManager(SearchWorkshop.this));
                recyclerView.setAdapter(adapter);
            }
        });
        Query query1= FirebaseDatabase.getInstance().getReference().child("Workshop");
        FirebaseRecyclerOptions<WorkshopClass> option1 =
                new FirebaseRecyclerOptions.Builder<WorkshopClass>()
                        .setQuery(query1,WorkshopClass.class)
                        .setLifecycleOwner(this)
                        .build();
        adapter =new Adapter(option1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    public class Adapter extends FirebaseRecyclerAdapter<WorkshopClass, Adapter.viewholder>{
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter(@NonNull FirebaseRecyclerOptions<WorkshopClass> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull viewholder viewholder, int i, @NonNull WorkshopClass workshopClass) {
            Glide.with(getApplicationContext()).load(workshopClass.getImage()).fitCenter().into(viewholder.imageView);
            viewholder.Name.setText(workshopClass.getName());
            viewholder.Teacher.setText(workshopClass.getTeacher());
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.workshop_card_search, parent, false);

            return new Adapter.viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder{
            TextView Name,Teacher;
            RelativeLayout rec;
            ImageView imageView;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                imageView=itemView.findViewById(R.id.wi1);
                Name=itemView.findViewById(R.id.wt1);
                Teacher=itemView.findViewById(R.id.wtt1);
                rec=itemView.findViewById(R.id.back);
            }
        }
    }
}
