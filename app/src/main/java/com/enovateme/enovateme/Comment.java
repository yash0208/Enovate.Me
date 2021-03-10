package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static maes.tech.intentanim.CustomIntent.customType;

public class Comment extends AppCompatActivity {
    DatabaseReference databaseReference;
    String id;
    String uid;
    EditText message;
    String user;
    Button send;
    Adapter adapter;
    RecyclerView rec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        Intent i=getIntent();
        id=i.getStringExtra("Id");
        uid=id;
        message=findViewById(R.id.message);
        send=findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(message.getText())){
                    Toast.makeText(Comment.this,"Please Say Something",Toast.LENGTH_LONG).show();
                }
                else {
                    HashMap<Object,String> hashMap=new HashMap<>();
                    String dateStr = "04/05/2010";
                    Date c = Calendar.getInstance().getTime();


                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                    String formattedDate = df.format(c);


                    hashMap.put("Id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    hashMap.put("Idea",message.getText().toString());
                    hashMap.put("Date",formattedDate);
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Your Idea").child(id).child("Comments");
                    databaseReference.push().setValue(hashMap);
                    message.setText("");
                }
            }
        });

        rec=findViewById(R.id.rec1);
        rec.setLayoutManager(new LinearLayoutManager(this));
       Query query=FirebaseDatabase.getInstance().getReference().child("Your Idea").child(id).child("Comments");
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

    public void back(View view) {
        onBackPressed();
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
            final String id1=getRef(i).getKey().toString();
            Query databaseReference1=FirebaseDatabase.getInstance().getReference("Your Idea").orderByChild("IId").equalTo(uid);
            databaseReference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for ( DataSnapshot ds: snapshot.getChildren()){
                            user=""+ds.child("Id").getValue().toString();


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            viewholder.comment.setVisibility(View.GONE);
            viewholder.num.setVisibility(View.GONE);

            viewholder.date.setText(idea.getDate());
            viewholder.idea.setText(idea.getIdea());
            viewholder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(Userid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        AlertDialog.Builder alert=new AlertDialog.Builder(Comment.this);
                        alert.setTitle("Do You Really Want To Delete Comment...!!")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        FirebaseDatabase.getInstance().getReference("Your Idea").child(uid)
                                                .child("Comments").child(id1).removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d("Delete", "Idea Has Been Deleted");
                                                        } else {
                                                            Log.d("Delete", "Idea Has Been Deleted");
                                                        }
                                                    }
                                                });

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(Comment.this,"Welcome Back",Toast.LENGTH_LONG).show();
                                    }
                                });
                        AlertDialog alertDialog=alert.create();
                        alertDialog.show();
                    }
                    if(Userid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        AlertDialog.Builder alert=new AlertDialog.Builder(Comment.this);
                        alert.setTitle("Do You Really Want To Delete Comment...!!")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        FirebaseDatabase.getInstance().getReference("Your Idea").child(uid)
                                                .child("Comments").child(id1).removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d("Delete", "Idea Has Been Deleted");
                                                        } else {
                                                            Log.d("Delete", "Idea Has Been Deleted");
                                                        }
                                                    }
                                                });

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(Comment.this,"Welcome Back",Toast.LENGTH_LONG).show();
                                    }
                                });
                        AlertDialog alertDialog=alert.create();
                        alertDialog.show();
                    }
                    if(user.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        AlertDialog.Builder alert=new AlertDialog.Builder(Comment.this);
                        alert.setTitle("Do You Really Want To Delete Comment...!!")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("Comments").child(id).removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d("Delete", "Idea Has Been Deleted");
                                                        } else {
                                                            Log.d("Delete", "Idea Has Been Deleted");
                                                        }
                                                    }
                                                });

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(Comment.this,"Welcome Back",Toast.LENGTH_LONG).show();
                                    }
                                });
                        AlertDialog alertDialog=alert.create();
                        alertDialog.show();
                    }
                    return false;
                }
            });

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
