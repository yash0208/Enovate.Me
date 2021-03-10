package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class Consultancy extends AppCompatActivity {
    RecyclerView rec;
    Adapter a;
    private FloatingActionButton mMainAddFab, mAddUserFab, mAddContactFab;
    private TextView mAddUserText, mAddContactText;
    Button close;
    private Animation mFabOpenAnim, mFabCloseAnim;

    private boolean isOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultancy);
        rec=findViewById(R.id.rec_hir);
        mMainAddFab = findViewById(R.id.main_add_fab);
        mAddUserFab = findViewById(R.id.add_user_fab);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        mAddContactFab = findViewById(R.id.add_contact_fab);
        mAddContactFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Consultancy.this,Want_To_Be_Consultant.class);

                startActivity(i);
            }
        });
        mAddUserFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Consultancy.this,Want_Consultancy.class);

                startActivity(i);
            }
        });
        mAddUserText = findViewById(R.id.add_user_text);
        mAddContactText = findViewById(R.id.add_contact_text);

        mFabOpenAnim = AnimationUtils.loadAnimation(Consultancy.this, R.anim.fab_open);
        mFabCloseAnim = AnimationUtils.loadAnimation(Consultancy.this, R.anim.fab_close);

        isOpen = false;

        mMainAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOpen){

                    mAddUserFab.setAnimation(mFabCloseAnim);
                    mAddContactFab.setAnimation(mFabCloseAnim);

                    mAddUserText.setVisibility(View.INVISIBLE);
                    mAddContactText.setVisibility(View.INVISIBLE);

                    isOpen = false;
                } else {

                    mAddUserFab.setAnimation(mFabOpenAnim);
                    mAddContactFab.setAnimation(mFabOpenAnim);

                    mAddUserText.setVisibility(View.VISIBLE);
                    mAddContactText.setVisibility(View.VISIBLE);

                    isOpen = true;
                }
            }
        });
        rec.setLayoutManager(new LinearLayoutManager(this));
        Query query= FirebaseDatabase.getInstance().getReference().child("Consultant");
        FirebaseRecyclerOptions<Hire_Class> options =
                new FirebaseRecyclerOptions.Builder<Hire_Class>()
                        .setQuery(query,Hire_Class.class)
                        .build();
        a=new Adapter(options);
        rec.setAdapter(a);

    }

    @Override
    protected void onStart() {
        super.onStart();
        a.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Consultancy.this,Home_Activity.class);

        startActivity(i);
    }

    public void back(View view) {
        Intent i=new Intent(Consultancy.this,Home_Activity.class);

        startActivity(i);
    }

    public class Adapter extends FirebaseRecyclerAdapter<Hire_Class, Adapter.viewholder> {
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter(@NonNull FirebaseRecyclerOptions<Hire_Class> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull final Adapter.viewholder viewholder, int i, @NonNull final Hire_Class hire_class) {
            final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("User Data");
            Query query=FirebaseDatabase.getInstance().getReference("User Data").orderByChild("UserId").equalTo(hire_class.getId());
            viewholder.Price.setText(hire_class.getCharge()+"₹/Hr");
            viewholder.Skill.setText(hire_class.getSkill());
            query.addValueEventListener(new ValueEventListener() {
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
                                    viewholder.imageView.setImageResource(R.drawable.male_selected);
                                }
                                else {
                                    viewholder.imageView.setImageResource(R.drawable.female_selected);
                                }
                            }
                            viewholder.Name.setText(name);
                            viewholder.Email.setText(email);


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(Consultancy.this,R.style.BottomSheetDialogTheme);
                    View bottomsheet= LayoutInflater.from(getApplicationContext()).inflate(R.layout.con_card, (FrameLayout)findViewById(R.id.container));
                    final TextView Name=bottomsheet.findViewById(R.id.sname);
                    final TextView Email=bottomsheet.findViewById(R.id.email);
                    TextView Skill=bottomsheet.findViewById(R.id.skill);
                    Button Done=bottomsheet.findViewById(R.id.add);
                    Done.setText("Add");
                    Done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            View view1= LayoutInflater.from(getApplicationContext()).inflate(R.layout.done_hire, (LinearLayout)findViewById(R.id.conta));
                            final AlertDialog dialog=new AlertDialog.Builder(Consultancy.this).setView(view1).create();
                            Button done=view1.findViewById(R.id.done);
                            TextView des=view1.findViewById(R.id.des);
                            String dateStr = "04/05/2010";
                            Date c = Calendar.getInstance().getTime();
                            Calendar c1 = Calendar.getInstance();
                            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
                            String datetime = dateformat.format(c1.getTime());

                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                            String formattedDate = df.format(c);
                            HashMap<Object,String> hashMap=new HashMap<>();
                            hashMap.put("Requested By", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            hashMap.put("Want To Hire",hire_class.getId());
                            hashMap.put("Date",datetime);
                            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference().child("Hire Requests Using Profile");
                            databaseReference1.push().setValue(hashMap);
                            des.setText("Your Hiring Request Has Been Sent We Will Reach You As Soon As Possible And Help You To Get Service.");
                            done.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    });
                    TextView Des=bottomsheet.findViewById(R.id.des);
                    final CircleImageView image=bottomsheet.findViewById(R.id.Con_Pro);
                    Skill.setText(hire_class.getSkill());
                    Des.setText(hire_class.getDescription());
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("User Data");
                    Query query=FirebaseDatabase.getInstance().getReference("User Data").orderByChild("UserId").equalTo(hire_class.getId());
                    query.addValueEventListener(new ValueEventListener() {
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
                                            image.setImageResource(R.drawable.male_selected);
                                        }
                                        else {
                                            image.setImageResource(R.drawable.female_selected);
                                        }
                                    }
                                    Name.setText(name);
                                    Email.setText(email);
                                }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    bottomSheetDialog.setContentView(bottomsheet);
                    bottomSheetDialog.show();
                }
            });
        }

        @NonNull
        @Override
        public Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.hire_small_page, parent, false);

            return new Adapter.viewholder(view);

        }

        public class viewholder extends RecyclerView.ViewHolder {
            TextView Name,Des,Skill,Email,Price;
            CircleImageView imageView;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                Name=itemView.findViewById(R.id.nameas);
                Email=itemView.findViewById(R.id.emailas);
                Skill=itemView.findViewById(R.id.skillas);
                Price=itemView.findViewById(R.id.rateas);
                imageView=itemView.findViewById(R.id.picas);
            }
        }
    }
}
