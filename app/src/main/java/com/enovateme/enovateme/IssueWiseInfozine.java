package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
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

import static maes.tech.intentanim.CustomIntent.customType;

public class IssueWiseInfozine extends AppCompatActivity {
    RecyclerView rec;
 Adapter a;
    String Name;
    String Type;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_wise_infozine);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        Intent i=getIntent();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        Name=i.getStringExtra("Name");
        name=findViewById(R.id.name1);
        name.setText(Name);
        rec=findViewById(R.id.rec);
        rec.setLayoutManager(new LinearLayoutManager(this));
        Query query=FirebaseDatabase.getInstance().getReference().child("infozine").orderByChild("Issue").equalTo(Name);
        FirebaseRecyclerOptions<Tips_Class> options =
                new FirebaseRecyclerOptions.Builder<Tips_Class>()
                        .setQuery(query,Tips_Class.class)
                        .build();
        a=new Adapter(options);
        rec.setAdapter(a);
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
        a.startListening();   }

    @Override
    protected void onStart() {
        super.onStart();
        a.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(IssueWiseInfozine.this,Infozine.class);

        startActivity(i);
    }

    public void back(View view) {
        Intent i=new Intent(IssueWiseInfozine.this,Infozine.class);

        startActivity(i);
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
                        Intent i=new Intent(IssueWiseInfozine.this,ViewInfozine.class);
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
                            Toast.makeText(IssueWiseInfozine.this,"Join Us For Premium Content",Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else {
                    viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i=new Intent(IssueWiseInfozine.this,ViewInfozine.class);
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
