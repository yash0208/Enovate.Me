package com.enovateme.enovateme;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search extends Fragment {
    RecyclerView rec;
    Adapter adapter;
    EditText search;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search.
     */
    // TODO: Rename and change types and number of parameters
    public static Search newInstance(String param1, String param2) {
        Search fragment = new Search();
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
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        rec=view.findViewById(R.id.user_list);
        search=view.findViewById(R.id.search_field);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String st=search.getText().toString();
                Query firebaseSearchQuery = FirebaseDatabase.getInstance().getReference().child("User Data").orderByChild("Name")
                        .startAt(st.toLowerCase())
                        .endAt(st.toLowerCase() + "\uf8ff");
                FirebaseRecyclerOptions<User> options =
                        new FirebaseRecyclerOptions.Builder<User>()
                                .setQuery(firebaseSearchQuery,User.class)
                                .build();
                rec.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter=new Adapter(options);
                rec.setAdapter(adapter);
                adapter.startListening();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String st=search.getText().toString();
                Query firebaseSearchQuery = FirebaseDatabase.getInstance().getReference().child("User Data").orderByChild("Name")
                        .startAt(st)
                        .endAt(st + "\uf8ff");
                FirebaseRecyclerOptions<User> options =
                        new FirebaseRecyclerOptions.Builder<User>()
                                .setQuery(firebaseSearchQuery,User.class)
                                .build();
                rec.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter=new Adapter(options);
                rec.setAdapter(adapter);
                adapter.startListening();
            }
        });
        Query query= FirebaseDatabase.getInstance().getReference().child("User Data");
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(query,User.class)
                        .setLifecycleOwner(this)
                        .build();
        rec.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new Adapter(options);
        rec.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    public class Adapter extends FirebaseRecyclerAdapter<User,Adapter.viewholder>{
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter(@NonNull FirebaseRecyclerOptions<User> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull viewholder viewholder, int i, @NonNull final User user) {
            if(user.getGender().equals("Male")){
                viewholder.image.setImageResource(R.drawable.male_selected);
            }
            if(user.getGender().equals("Female")){
                viewholder.image.setImageResource(R.drawable.female_selected);
            }
            viewholder.name.setText(user.getName());

            viewholder.emil.setText(user.getEmail());
            viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),UserProfile.class);
                    intent.putExtra("Id",user.getUserId());
                    startActivity(intent);                }
            });
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_card, parent, false);

            return new Adapter.viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder {
            TextView name,emil;
            CircleImageView image;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.name);
                emil=itemView.findViewById(R.id.email);
                image=itemView.findViewById(R.id.profile_pic_sl);
            }
        }
    }
}
