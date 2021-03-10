package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;
import com.squareup.seismic.ShakeDetector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static maes.tech.intentanim.CustomIntent.customType;

public class Home_Activity extends AppCompatActivity implements ShakeDetector.Listener, GoogleApiClient.OnConnectionFailedListener {
    SNavigationDrawer sNavigationDrawer;
    Class fragmentClass;
    public static Fragment fragment;
    private AdView mAdView;
    CircleImageView profile_cat;
    String type;
    String name;
    DatabaseReference databaseReference;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_INVITE = 0;

    private GoogleApiClient mGoogleApiClient;
    TextView Tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment=new Home();
        profile_cat=findViewById(R.id.profile_cat);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        ShakeDetector shakeDetector = new ShakeDetector((ShakeDetector.Listener) Home_Activity.this);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        shakeDetector.start(sensorManager);
        fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.container_1, fragment).commit();
        sNavigationDrawer = findViewById(R.id.navigationDrawer);
        Tag=findViewById(R.id.Tag);
        List<com.shrikanthravi.customnavigationdrawer2.data.MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Home",R.drawable.h21));
        menuItems.add(new MenuItem("Search",R.drawable.s21));
        menuItems.add(new MenuItem("Why Us?",R.drawable.w21));
        menuItems.add(new MenuItem("About Us",R.drawable.joinaa));
        databaseReference= FirebaseDatabase.getInstance().getReference("User Data");
        DatabaseReference databaseReference= (DatabaseReference) FirebaseDatabase.getInstance().getReference("User Data").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Name");
           databaseReference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot ds) {
                   if(!ds.exists()){
                       Intent i=new Intent(Home_Activity.this,Profile_Edit.class);
                       startActivity(i);
                   }

               }


               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
        Query query=FirebaseDatabase.getInstance().getReference("User Data").orderByChild("UserId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    name=""+ds.child("Name").getValue().toString();
                    String email=""+ds.child("Email").getValue().toString();
                    String add=""+ds.child("Address").getValue().toString();
                    type=""+ds.child("Type").getValue().toString();
                    String contact=""+ds.child("Contact Number").getValue().toString();
                    String pincode=""+ds.child("PinCode").getValue().toString();
                    String des=""+ds.child("Description").getValue().toString();

                    if(ds.hasChild("Gender")){
                        String gender=""+ds.child("Gender").getValue().toString();
                        if(gender.equals("Male")){
                            profile_cat.setImageResource(R.drawable.male_selected);
                        }
                        else {
                            profile_cat.setImageResource(R.drawable.female_selected);
                        }
                    }
                    if(!type.equals("Normal")){
                        profile_cat.setImageResource(R.drawable.prime);
                    }
                    if(!ds.hasChild("Name")){
                        Intent i=new Intent(Home_Activity.this,Profile_Edit.class);

                        startActivity(i);
                    }
                    Tag.setText("Hello "+name+",");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sNavigationDrawer.setMenuItemList(Collections.<com.shrikanthravi.customnavigationdrawer2.data.MenuItem>unmodifiableList((List<? extends com.shrikanthravi.customnavigationdrawer2.data.MenuItem>) menuItems));
        sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {
            @Override
            public void onDrawerOpening() {
                System.out.println("Enovate Me");
            }

            @Override
            public void onDrawerClosing() {
                System.out.println("Enovate Me");
            }

            @Override
            public void onDrawerOpened() {
                System.out.println("Enovate Me");
            }

            @Override
            public void onDrawerClosed() {
                System.out.println("Enovate Me");
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                System.out.println("Enovate Me");
            }
        });
        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                System.out.println("Enovate Me");

                switch (position) {
                    case 0: {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragment = new Home();
                        Tag.setText("Hello "+name+",");
                        fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.container_1, fragment).commit();

                        break;
                    }
                    case 1: {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragment = new Search();
                        Bundle bundle = new Bundle();
                        bundle.putString("Id",FirebaseAuth.getInstance().getCurrentUser().getUid());
// set Fragmentclass Arguments
                        Tag.setText("Search User");
                        fragment.setArguments(bundle);
                        fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.container_1, fragment).commit();
                        break;
                    }
                    case 2: {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragment = new JoinUs();
                        Bundle bundle = new Bundle();
                        bundle.putString("Id",FirebaseAuth.getInstance().getCurrentUser().getUid());
// set Fragmentclass Arguments
                        Tag.setText("Why Us");
                        fragment.setArguments(bundle);
                        fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.container_1, fragment).commit();
                        break;
                    }
                    case 3: {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragment = new AboutUs();
                        Bundle bundle = new Bundle();
                        bundle.putString("Id",FirebaseAuth.getInstance().getCurrentUser().getUid());
// set Fragmentclass Arguments
                        Tag.setText("About Us");
                        fragment.setArguments(bundle);
                        fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.container_1, fragment).commit();
                        break;
                    }
                }}
        });
    }
    @Override
    public void hearShake() {
        // open camera
        // refresh the app
        // do any custom business logic
        View view1= LayoutInflater.from(getApplicationContext()).inflate(R.layout.main_page_alert_dialog, (LinearLayout)findViewById(R.id.conta));
        final android.app.AlertDialog dialog=new android.app.AlertDialog.Builder(Home_Activity.this).setView(view1).create();
        LinearLayout l1=view1.findViewById(R.id.l1);
        LinearLayout l2=view1.findViewById(R.id.l2);
        LinearLayout l3=view1.findViewById(R.id.l3);
        LinearLayout l4=view1.findViewById(R.id.l4);
        LinearLayout l5=view1.findViewById(R.id.l5);
        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=com.enovateme.enovateme" + getPackageName())));
                }
            }
        });
        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Enovate Me");
                String shareMessage= "Your Friend "+name+"\nInvited You To The Latest Enovate Me Learn And Earn In New Way In 2020.\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.enovateme.enovateme"  +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "Invite Via"));
            }
        });
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mailer = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" +"enovativeeliteclub1907@gmail.com"));

                mailer.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                mailer.putExtra(Intent.EXTRA_TEXT, "Description For Feedback");
                startActivity(mailer);
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" +"enovativeeliteclub1907@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Report Bug");
                intent.putExtra(Intent.EXTRA_TEXT, "Details Regarding Bug");
                startActivity(intent);
                //mailer.setType("text/plain");
                //mailer.putExtra(Intent.EXTRA_EMAIL, new String[]{"enovativeeliteclub1907@gmail.com"});
                //mailer.putExtra(Intent.EXTRA_SUBJECT, "Report Bug");
               // mailer.putExtra(Intent.EXTRA_TEXT, "Details Regarding Bug");

            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1= LayoutInflater.from(getApplicationContext()).inflate(R.layout.done_hire, (LinearLayout)findViewById(R.id.conta));
                final android.app.AlertDialog dialog1=new android.app.AlertDialog.Builder(Home_Activity.this).setView(view1).create();
                Button done=view1.findViewById(R.id.done);
                TextView collab=view1.findViewById(R.id.collab);
                collab.setText("Thank You For Joining US");
                ImageView image=view1.findViewById(R.id.image);
                image.setImageResource(R.drawable.thanoks);

                final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Joining Request");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap<String, String> hashMap=new HashMap<>();
                        hashMap.put("By",FirebaseAuth.getInstance().getCurrentUser().getUid());
                        hashMap.put("Status","Pending");
                        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(hashMap);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                TextView des=view1.findViewById(R.id.des);
                des.setText("Your Joining Request Has Been Sent To Our Team We Will Reach You As Soon As Possible");
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                });
                dialog1.show();
            }

        });
        dialog.show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d(TAG, "onActivityResult: sent invitation " + id);
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
            }
        }
    }



    @Override
    public void onBackPressed() {

        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Confirm Exit...!!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Home_Activity.super.onBackPressed();
                finishAffinity();
            }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Home_Activity.this,"Welcome Back",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=alert.create();
        alertDialog.show();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
