package com.enovateme.enovateme;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureLibraries;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.melnykov.fab.FloatingActionButton;

import java.security.PublicKey;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPage extends Fragment implements GestureDetector.OnGestureListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final int SWIPE_MIN_DISTANCE = 120;
    RecyclerView recyclerView;
    RoundedImageView t1,t2,t3;
    ViewFlipper viewFlipper;
    ViewFlipper mViewFlipper;
    ImageView wi1,wi2,wi3,sp;
    FloatingActionButton fab;
    TextView wt1,wt2,wt3,wtt1,wtt2,wtt3;
    rating rating;
    RecyclerView consultant;
    String pw1,pw2,pw3;
    Consultant_Adapter consultant_adapter;
    RecyclerView most_viewed_recycler;
    Adapter1 adapter1;
    RecyclerView books;
    ImageView BooksImage;
     private float initialX;
    public MainPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainPage.
     */
    // TODO: Rename and change types and number of parameters
    public static MainPage newInstance(String param1, String param2) {
        MainPage fragment = new MainPage();
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
        View view=inflater.inflate(R.layout.fragment_main_page, container, false);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Main_Page_Header");
        Query query= FirebaseDatabase.getInstance().getReference().child("MainPage");
        t2=view.findViewById(R.id.imageView2);
        t3=view.findViewById(R.id.imageView1);
        t1=view.findViewById(R.id.imageView3);
        wi1=view.findViewById(R.id.wi1);
        wi2=view.findViewById(R.id.wi2);
        wi3=view.findViewById(R.id.wi3);
        fab=view.findViewById(R.id.fab);
        sp=view.findViewById(R.id.student_package);
        consultant=view.findViewById(R.id.consultants);
        consultant.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        Query con= FirebaseDatabase.getInstance().getReference().child("Consultant");
        FirebaseRecyclerOptions<Main_Page_Consultant> options_con =
                new FirebaseRecyclerOptions.Builder<Main_Page_Consultant>()
                        .setQuery(con,Main_Page_Consultant.class)
                        .build();
        consultant_adapter=new Consultant_Adapter(options_con);
        consultant.setAdapter(consultant_adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),SearchWorkshop.class);
                startActivity(i);
            }
        });
        wt1=view.findViewById(R.id.wt1);
        wt2=view.findViewById(R.id.wt2);
        wt3=view.findViewById(R.id.wt3);
        wtt1=view.findViewById(R.id.wtt1);
        wtt2=view.findViewById(R.id.wtt2);
        wtt3=view.findViewById(R.id.wtt3);
        books=view.findViewById(R.id.books);
      
       BooksImage=view.findViewById(R.id.book_pic);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Glide.with(getContext()).load(snapshot.child("books").getValue().toString()).centerCrop().into(BooksImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        books.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        Query query2=FirebaseDatabase.getInstance().getReference().child("Book");
        FirebaseRecyclerOptions<Books1> options2 =
                new FirebaseRecyclerOptions.Builder<Books1>()
                        .setQuery(query2,Books1.class)
                        .setLifecycleOwner(this)
                        .build();
        adapter1=new Adapter1(options2);
        books.setAdapter(adapter1);
        most_viewed_recycler=view.findViewById(R.id.most_viewed_recycler);
        Query query1= FirebaseDatabase.getInstance().getReference().child("Review");
        FirebaseRecyclerOptions<Rating> options =
                new FirebaseRecyclerOptions.Builder<Rating>()
                        .setQuery(query1,Rating.class)
                        .build();
        rating=new rating(options);
        most_viewed_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        most_viewed_recycler.setAdapter(rating);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                Glide.with(getContext()).load(ds.child("1").getValue()).fitCenter().into(t1);
                Glide.with(getContext()).load(ds.child("2").getValue()).fitCenter().into(t2);
                Glide.with(getContext()).load(ds.child("3").getValue()).fitCenter().into(t3);
                Glide.with(getContext()).load(ds.child("Package").getValue()).fitCenter().into(sp);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference pwd=FirebaseDatabase.getInstance().getReference("Popular Workshop");
        pwd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pw1=snapshot.child("1").getValue().toString();
                DatabaseReference pww1=  FirebaseDatabase.getInstance().getReference("Workshop").child(pw1);
                pww1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Glide.with(getContext()).load(snapshot.child("Logo").getValue().toString()).fitCenter().into(wi1);
                        wt1.setText(snapshot.child("Name").getValue().toString());
                        wtt1.setText(snapshot.child("Teacher").getValue().toString());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                pw2=snapshot.child("2").getValue().toString();
                DatabaseReference pww2=  FirebaseDatabase.getInstance().getReference("Workshop").child(pw2);
                pww2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Glide.with(getContext()).load(snapshot.child("Logo").getValue().toString()).fitCenter().into(wi2);
                        wt2.setText(snapshot.child("Name").getValue().toString());
                        wtt3.setText(snapshot.child("Teacher").getValue().toString());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                pw3=snapshot.child("3").getValue().toString();
                DatabaseReference pww3=  FirebaseDatabase.getInstance().getReference("Workshop").child(pw3);
                pww3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Glide.with(getContext()).load(snapshot.child("Logo").getValue().toString()).fitCenter().into(wi3);
                        wt3.setText(snapshot.child("Name").getValue().toString());
                        wtt3.setText(snapshot.child("Teacher").getValue().toString());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        rating.startListening();
        adapter1.startListening();
        consultant_adapter.startListening();
    }

    public void previousView(View v) {
        viewFlipper.setInAnimation(getContext(), R.anim.left_out);
        viewFlipper.setOutAnimation(getContext(), R.anim.left_in);
        viewFlipper.showPrevious();
    }
    public void nextView(View v) {
        viewFlipper.setInAnimation(getContext(),android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
        viewFlipper.showNext();
    }
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
    public class rating extends FirebaseRecyclerAdapter<Rating,rating.viewholder> {


        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public rating(@NonNull FirebaseRecyclerOptions<Rating> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull rating.viewholder viewholder, int i, @NonNull Rating rating) {
            Glide.with(getContext()).load(rating.getImage()).into(viewholder.imageView);
            viewholder.Name.setText(rating.getName());
            viewholder.Details.setText(rating.getReview());
            float rat= Float.parseFloat(rating.getRate());
            viewholder.rate.setRating(rat);
        }

        @NonNull
        @Override
        public rating.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rating_card, parent, false);

            return new viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder{
            RatingBar rate;
            TextView Name,Details;
            ImageView imageView;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                imageView=itemView.findViewById(R.id.mv_image);
                Name=itemView.findViewById(R.id.mv_title);
                Details=itemView.findViewById(R.id.mv_desc);
                rate=itemView.findViewById(R.id.mv_rating);
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
            Glide.with(getContext()).load(books1.getPic()).centerCrop().into(viewholder.image);
            viewholder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getActivity(),ViewInfozine.class);
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
    public class Consultant_Adapter extends FirebaseRecyclerAdapter<Main_Page_Consultant,Consultant_Adapter.viewholder> {
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Consultant_Adapter(@NonNull FirebaseRecyclerOptions<Main_Page_Consultant> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Consultant_Adapter.viewholder viewholder, int i, @NonNull final Main_Page_Consultant main_page_consultant) {
            Glide.with(getActivity()).load(main_page_consultant.getDP()).fitCenter().into(viewholder.pic);
            viewholder.Name.setText(main_page_consultant.getName());
            viewholder.Field.setText(main_page_consultant.getField());

            viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getActivity(),Consultant_Profile.class);
                    i.putExtra("Id",main_page_consultant.getId());
                    startActivity(i);
                }
            });
        }

        @NonNull
        @Override
        public Consultant_Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.consultant_card_mp, parent, false);

            return new viewholder(view);
        }
        public class viewholder extends RecyclerView.ViewHolder {
            CircleImageView pic;
            TextView Name,Field;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                pic=itemView.findViewById(R.id.pic);
                Name=itemView.findViewById(R.id.name);
                Field=itemView.findViewById(R.id.sub);
            }
        }
    }


}
