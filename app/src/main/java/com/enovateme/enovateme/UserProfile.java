package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

import static maes.tech.intentanim.CustomIntent.customType;

public class UserProfile extends AppCompatActivity {
    String id;
    TextView Name,Email,dis;
    RecyclerView rec;
    Button close;
    Button edit,logout;
    CircleImageView m;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        Intent i=getIntent();
        id=i.getStringExtra("Id");
        Name=findViewById(R.id.n11);
        Email=findViewById(R.id.e11);
        dis=findViewById(R.id.des1);
        edit=findViewById(R.id.add);
        close=findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
                logout=findViewById(R.id.profile);
        m=findViewById(R.id.m);
        Query databaseReference= FirebaseDatabase.getInstance().getReference("User Data").orderByChild("UserId").equalTo(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){

                        String name=""+ds.child("Name").getValue().toString();
                        String email=""+ds.child("Email").getValue().toString();
                        String add=""+ds.child("Address").getValue().toString();
                        String contact=""+ds.child("Contact Number").getValue().toString();
                        String pincode=""+ds.child("PinCode").getValue().toString();
                        String des=""+ds.child("Description").getValue().toString();
                        if(ds.hasChild("Gender")){
                            String gender=""+ds.child("Gender").getValue().toString();
                            if(gender.equals("Male")){
                                m.setImageResource(R.drawable.male_selected);
                            }
                            if (gender.equals("Female")){
                                m.setImageResource(R.drawable.female_selected);
                            }
                        }

                        Name.setText(name);
                        Email.setText(email);
                        dis.setText(des);

                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        rec=findViewById(R.id.rec);
        rec.setLayoutManager(new LinearLayoutManager(this));
        Query query=FirebaseDatabase.getInstance().getReference().child("Your Idea").orderByChild("Id").equalTo(id);
        FirebaseRecyclerOptions<idea> options =
                new FirebaseRecyclerOptions.Builder<idea>()
                        .setQuery(query,idea.class)
                        .setLifecycleOwner(this)
                        .build();
        adapter=new Adapter(options);
        rec.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public  class Adapter extends FirebaseRecyclerAdapter<idea, Adapter.viewholder> {

        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter(@NonNull FirebaseRecyclerOptions<idea> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull final Adapter.viewholder viewholder, int i, @NonNull idea idea) {
            final String Userid=idea.getId();
            final String id=getRef(i).getKey().toString();
            DatabaseReference dat=FirebaseDatabase.getInstance().getReference().child("Your Idea").child(id).child("Comments");
            dat.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {



                    String num1=snapshot.getChildrenCount()+"";
                    viewholder.num.setText(num1);


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            viewholder.comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(UserProfile.this,Comment.class);

                    i.putExtra("Id",id);
                    startActivity(i);
                }
            });
            viewholder.date.setText(idea.getDate());
            viewholder.idea.setText(idea.getIdea());
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("User Data").child(Userid);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot ds) {

                    String nam1e=""+ds.child("Name").getValue().toString();
                    String email=""+ds.child("Email").getValue().toString();
                    String add=""+ds.child("Address").getValue().toString();
                    String contact=""+ds.child("Contact Number").getValue().toString();
                    String pincode=""+ds.child("PinCode").getValue().toString();
                    String des=""+ds.child("Description").getValue().toString();
                    viewholder.name.setText(nam1e);

                    if(ds.hasChild("Gender")){
                        String gender=""+ds.child("Gender").getValue().toString();
                        if(gender.equals("Male")){
                            viewholder.image.setImageResource(R.drawable.male_selected);

                        }
                        else {
                            viewholder.image.setImageResource(R.drawable.female_selected);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        @NonNull
        @Override
        public Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.idea_card, parent, false);

            return new Adapter.viewholder(view);

        }

        public class viewholder extends RecyclerView.ViewHolder {
            TextView idea,name,date,num;
            CircleImageView image;
            Button comment;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                comment=itemView.findViewById(R.id.comment1);
                num=itemView.findViewById(R.id.num);
                idea=itemView.findViewById(R.id.idea1);
                name=itemView.findViewById(R.id.name1);
                date=itemView.findViewById(R.id.date);
                image=itemView.findViewById(R.id.pic);
            }
        }
    }
}
