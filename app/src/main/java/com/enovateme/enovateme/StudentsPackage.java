package com.enovateme.enovateme;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentsPackage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentsPackage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView frame_rec,tips_rec,infozine;
    Frameworks frameworks;
    Infozine adapter1;
    Tips tips;
    public StudentsPackage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentsPackage.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentsPackage newInstance(String param1, String param2) {
        StudentsPackage fragment = new StudentsPackage();
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
        View view=inflater.inflate(R.layout.fragment_students_package, container, false);
        frame_rec=view.findViewById(R.id.frame);
        frame_rec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        Query query1= FirebaseDatabase.getInstance().getReference().child("Frameworks_Fields");
        FirebaseRecyclerOptions<tips_pic> option1 =
                new FirebaseRecyclerOptions.Builder<tips_pic>()
                        .setQuery(query1,tips_pic.class)
                        .setLifecycleOwner(this)
                        .build();

        frameworks=new Frameworks(option1);
        tips_rec=view.findViewById(R.id.tips1);
        tips_rec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        frame_rec.setAdapter(frameworks);
        Query query=FirebaseDatabase.getInstance().getReference().child("Tips_Sections");
        FirebaseRecyclerOptions<Tips_Class> options =
                new FirebaseRecyclerOptions.Builder<Tips_Class>()
                        .setQuery(query,Tips_Class.class)
                        .setLifecycleOwner(this)
                        .build();
        tips=new Tips(options);
        tips_rec.setAdapter(tips);
        infozine=view.findViewById(R.id.infozine1);
        infozine.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        Query query2= FirebaseDatabase.getInstance().getReference().child("infozine");
        FirebaseRecyclerOptions<tips_pic> option2 =
                new FirebaseRecyclerOptions.Builder<tips_pic>()
                        .setQuery(query2,tips_pic.class)
                        .build();
        adapter1=new Infozine(option2);
        infozine.setAdapter(adapter1);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        tips.startListening();
        frameworks.startListening();
        adapter1.startListening();
    }
    public class Tips extends FirebaseRecyclerAdapter<Tips_Class,Tips.viewholder>{
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Tips(@NonNull FirebaseRecyclerOptions<Tips_Class> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull viewholder viewholder, int i, @NonNull Tips_Class tips_pic) {
            Glide.with(getActivity()).load(tips_pic.getLink()).fitCenter().into(viewholder.image);
            viewholder.name.setText(tips_pic.getName());
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tips_circle, parent, false);

            return new viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder{
            ImageView image;
            TextView name;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                image=itemView.findViewById(R.id.image);
                name=itemView.findViewById(R.id.name);
            }
        }
    }
    public class Infozine extends FirebaseRecyclerAdapter<tips_pic,Infozine.viewholder>{
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Infozine(@NonNull FirebaseRecyclerOptions<tips_pic> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Infozine.viewholder viewholder, int i, @NonNull tips_pic tips_pic) {
                Glide.with(getContext()).load(tips_pic.getUrl()).fitCenter().into(viewholder.logo);
        }

        @NonNull
        @Override
        public Infozine.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.infozine_card, parent, false);

            return new viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder{
            ImageView logo;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                logo=itemView.findViewById(R.id.image);
            }
        }
    }
    public class Frameworks extends FirebaseRecyclerAdapter<tips_pic,Frameworks.viewholder>{
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Frameworks(@NonNull FirebaseRecyclerOptions<tips_pic> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull viewholder viewholder, int i, @NonNull tips_pic tips_pic) {
            Glide.with(getActivity()).load(tips_pic.getLink()).fitCenter().into(viewholder.image);
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.package_cart, parent, false);

            return new viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder{
            ImageView image;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                image=itemView.findViewById(R.id.Image);

            }
        }
    }
}
