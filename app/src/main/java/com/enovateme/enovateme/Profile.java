package com.enovateme.enovateme;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    String id;
    TextView Name,Email,dis;
    RecyclerView rec;
    Button edit,logout;
    CircleImageView m;
    Adapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        Name=view.findViewById(R.id.n11);
        Email=view.findViewById(R.id.e11);
        dis=view.findViewById(R.id.des1);
        edit=view.findViewById(R.id.add);
        logout=view.findViewById(R.id.profile);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditProfile.class);
                customType(getActivity(),"bottom-to-up");
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
        m=view.findViewById(R.id.m);
        id = getArguments().getString("Id");
        Query databaseReference= FirebaseDatabase.getInstance().getReference("User Data").orderByChild("UserId").equalTo(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){

                        String name=""+ds.child("Name").getValue().toString();
                        String email=""+ds.child("Email").getValue().toString();
                        String add=""+ds.child("Address").getValue().toString();
                        String contact=""+ds.child("Contact Number").getValue().toString();
                        String pincode=""+ds.child("PinCode").getValue().toString();
                        String des=""+ds.child("Description").getValue().toString();
                   String type=""+ds.child("Type").getValue().toString();

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
        rec=view.findViewById(R.id.rec);
        rec.setLayoutManager(new LinearLayoutManager(getActivity()));
        Query query=FirebaseDatabase.getInstance().getReference().child("Your Idea").orderByChild("Id").equalTo(id);
        FirebaseRecyclerOptions<idea> options =
                new FirebaseRecyclerOptions.Builder<idea>()
                        .setQuery(query,idea.class)
                        .setLifecycleOwner(this)
                        .build();
        adapter=new Adapter(options);
        rec.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
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
            viewholder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(Userid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        AlertDialog.Builder alert=new AlertDialog.Builder(getActivity());
                        alert.setTitle("Do You Really Want To Delete Your Idea...!!")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("Your Idea").child(id).removeValue()
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
                                        Toast.makeText(getActivity(),"Welcome Back",Toast.LENGTH_LONG).show();
                                    }
                                });
                        AlertDialog alertDialog=alert.create();
                        alertDialog.show();
                    }
                    else{
                        AlertDialog.Builder alert=new AlertDialog.Builder(getActivity());
                        alert.setTitle("Want To Report ?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        DatabaseReference data=FirebaseDatabase.getInstance().getReference().child("Report");
                                        data.child(id).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Reported By").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(getActivity(),"Welcome Back",Toast.LENGTH_LONG).show();
                                    }
                                });
                        AlertDialog alertDialog=alert.create();
                        alertDialog.show();
                    }
                    return false;
                }
            });
            DatabaseReference dat=FirebaseDatabase.getInstance().getReference().child("Your Idea").child(id).child("Comments");
            dat.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot ds) {
                    String num1=ds.getChildrenCount()+"";
                    viewholder.num.setText(num1);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            viewholder.comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getActivity(),Comment.class);
                    i.putExtra("Id",id);
                    customType(getActivity(),"bottom-to-up");
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
