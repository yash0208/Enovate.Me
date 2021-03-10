package com.enovateme.enovateme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.internal.$Gson$Preconditions;

import static maes.tech.intentanim.CustomIntent.customType;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JoinUs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinUs extends Fragment {
    Button y1,n1,y2,n2,y3,n3;
    WebView webView;
    LinearLayout l1,l2;
    String link,name;
    TextView t1,t2,t3;
    ScrollView scrollView;
    Button b;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public JoinUs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JoinUs.
     */
    // TODO: Rename and change types and number of parameters
    public static JoinUs newInstance(String param1, String param2) {
        JoinUs fragment = new JoinUs();
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
        View view=inflater.inflate(R.layout.fragment_join_us, container, false);
        y1=view.findViewById(R.id.y1);
        n1=view.findViewById(R.id.n1);
        y3=view.findViewById(R.id.y3);
        n3=view.findViewById(R.id.n3);
        y2=view.findViewById(R.id.y2);
        n2=view.findViewById(R.id.n2);
        l1=view.findViewById(R.id.l1);
        scrollView=view.findViewById(R.id.scroll1);
        l2=view.findViewById(R.id.l2);
        t1=view.findViewById(R.id.t1);
        t2=view.findViewById(R.id.t2);
        t3=view.findViewById(R.id.t3);
        b=view.findViewById(R.id.open12);
        webView=view.findViewById(R.id.web);
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Get99");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                link=snapshot.child("1").getValue().toString();
                name=snapshot.child("name").getValue().toString();
                Log.e("Link",link);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        String pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
        String doc="<iframe src="+link+" width='100%' height='100%' style='border: none;'></iframe>";

        y1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l1.setVisibility(View.VISIBLE);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                Toast.makeText(getActivity(),"That's Great Scroll Down Te See More",Toast.LENGTH_SHORT).show();
            }
        });
        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l1.setVisibility(View.VISIBLE);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                Toast.makeText(getActivity(),"That's Great Scroll Down Te See More",Toast.LENGTH_SHORT).show();
            }
        });
        y2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l2.setVisibility(View.VISIBLE);
            }
        });
        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l2.setVisibility(View.VISIBLE);
                t1.setText("We know that you want to spend some pocket money with your friends and also some to start something new to develop your career like start-up or in some knowledge related to your interest as you are a responsible person of your family and people around you …. Right ?  ");
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                Toast.makeText(getActivity(),"That's Great Scroll Down Te See More",Toast.LENGTH_SHORT).show();
            }
        });
        y3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
                b.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                Toast.makeText(getActivity(),"That's Great Scroll Down Te See More",Toast.LENGTH_SHORT).show();
            }
        });
        n3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
                b.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),"That's Great Scroll Down Te See More",Toast.LENGTH_SHORT).show();
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
        webView.loadData( doc , "text/html", "UTF-8");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),ViewInfozine.class);
                i.putExtra("Link",link);
                i.putExtra("Name",name);
                customType(getActivity(),"up-to-bottom");
                Toast.makeText(getActivity(),"That's Great Scroll Down Te See More",Toast.LENGTH_SHORT).show();
                startActivity(i);

            }
        });
        return view;
    }
}
