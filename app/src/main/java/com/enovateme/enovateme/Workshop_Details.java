package com.enovateme.enovateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Workshop_Details extends AppCompatActivity {
    YouTubePlayerView view;
    TextView Name,Teacher,Duration,Time,Language,Platform,Subject,Description;
    Button pdf,pay,close;
    String nam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop__details);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        Name=findViewById(R.id.name);
        Teacher=findViewById(R.id.teacher);
        Description=findViewById(R.id.des);
        Duration=findViewById(R.id.duration);
        Time=findViewById(R.id.time);
        Language=findViewById(R.id.language);
        view=findViewById(R.id.web);
        Subject=findViewById(R.id.subject);
        pdf=findViewById(R.id.pdf);
        pay=findViewById(R.id.pay);
        close=findViewById(R.id.back);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Workshop_Details.this,Workshop.class);
                startActivity(i);
            }
        });

        Intent i=getIntent();
        nam=i.getStringExtra("Name");
        Query databaseReference= FirebaseDatabase.getInstance().getReference("Workshop").orderByChild("Name").equalTo(nam);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){

                        Name.setText(ds.child("Name").getValue().toString());
                        Teacher.setText(ds.child("Teacher").getValue().toString());
                        Description.setText(ds.child("Description").getValue().toString());
                        Duration.setText(ds.child("Duration").getValue().toString());
                        Time.setText(ds.child("Time").getValue().toString());
                        Language.setText(ds.child("Language").getValue().toString());
                        Subject.setText(ds.child("Subject").getValue().toString());
                        final String PDF=ds.child("PDF").getValue().toString();
                        final String Link=ds.child("Link").getValue().toString();
                        final String n1=ds.child("Name").getValue().toString();
                        view.addYouTubePlayerListener(new YouTubePlayerListener() {
                            @Override
                            public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                                youTubePlayer.loadVideo(Link,0);
                            }

                            @Override
                            public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState playerState) {

                            }

                            @Override
                            public void onPlaybackQualityChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackQuality playbackQuality) {

                            }

                            @Override
                            public void onPlaybackRateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackRate playbackRate) {

                            }

                            @Override
                            public void onError(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerError playerError) {

                            }

                            @Override
                            public void onCurrentSecond(@NotNull YouTubePlayer youTubePlayer, float v) {

                            }

                            @Override
                            public void onVideoDuration(@NotNull YouTubePlayer youTubePlayer, float v) {

                            }

                            @Override
                            public void onVideoLoadedFraction(@NotNull YouTubePlayer youTubePlayer, float v) {

                            }

                            @Override
                            public void onVideoId(@NotNull YouTubePlayer youTubePlayer, @NotNull String s) {

                            }

                            @Override
                            public void onApiChange(@NotNull YouTubePlayer youTubePlayer) {

                            }
                        });
                    view.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            String videoId = "86HFMQwGzeI";
                            youTubePlayer.loadVideo(Link, 0);
                        }
                    });
                    view.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onApiChange(@NotNull YouTubePlayer youTubePlayer) {
                            super.onApiChange(youTubePlayer);
                        }

                    });
                        pay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Workshop Request");
                                HashMap<Object,String> hashMap=new HashMap<>();
                                hashMap.put("Requested By",FirebaseAuth.getInstance().getCurrentUser().getUid());
                                hashMap.put("For",n1);
                                Date c = Calendar.getInstance().getTime();
                                Calendar c1 = Calendar.getInstance();
                                SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
                                String datetime = dateformat.format(c1.getTime());
                                hashMap.put("Date",datetime);
                                reference.push().setValue(hashMap);
                                View view1= LayoutInflater.from(getApplicationContext()).inflate(R.layout.done_hire, (LinearLayout)findViewById(R.id.conta));
                                final AlertDialog dialog=new AlertDialog.Builder(Workshop_Details.this).setView(view1).create();
                                Button done=view1.findViewById(R.id.done);
                                TextView des=view1.findViewById(R.id.des);
                                des.setText("Your Request For Workshop Has Been Sent To Our Team We Will Reach You As Soon As Possible,Thank You.");
                                done.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                        });

                        pdf.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i=new Intent(Workshop_Details.this,ViewInfozine.class);
                                i.putExtra("Link",PDF);
                                i.putExtra("Name",n1);
                                startActivity(i);
                            }
                        });
                    }
                }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
