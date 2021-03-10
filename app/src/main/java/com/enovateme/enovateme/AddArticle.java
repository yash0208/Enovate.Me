package com.enovateme.enovateme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static maes.tech.intentanim.CustomIntent.customType;

public class AddArticle extends AppCompatActivity {
    EditText Name,Tittle,Field,Article;
    Button Done;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        Name=findViewById(R.id.con_name);
        Tittle=findViewById(R.id.con_email);
        Field=findViewById(R.id.contact_con);
        Article=findViewById(R.id.col_add);
        Done=findViewById(R.id.save_con);
        databaseReference= FirebaseDatabase.getInstance().getReference("Articles");
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(Name.getText().toString())){
                    Toast.makeText(AddArticle.this,"Enter Valid Name",Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(Tittle.getText().toString())){
                    Toast.makeText(AddArticle.this,"Enter Valid Tittle",Toast.LENGTH_LONG).show();
                }if(TextUtils.isEmpty(Field.getText().toString())){
                    Toast.makeText(AddArticle.this,"Enter Valid Field",Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(Article.getText().toString())){
                    Toast.makeText(AddArticle.this,"Please Write Something",Toast.LENGTH_LONG).show();
                }
                else {
                    HashMap<Object,String> hashMap=new HashMap<>();
                    hashMap.put("Name",Name.getText().toString());
                    hashMap.put("Tittle",Tittle.getText().toString());
                    hashMap.put("Field",Field.getText().toString());
                    hashMap.put("Article",Article.getText().toString());
                    databaseReference.push().setValue(hashMap);
                }
                View view1= LayoutInflater.from(getApplicationContext()).inflate(R.layout.done_hire, (LinearLayout)findViewById(R.id.conta));
                final AlertDialog dialog=new AlertDialog.Builder(AddArticle.this).setView(view1).create();
                Button done=view1.findViewById(R.id.done);
                TextView des=view1.findViewById(R.id.des);
                des.setText("Your Article Has Been Sent To Our Team We Will Review It And Reach You As Soon As Possible.");
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });
    }

    public void back(View view) {
        Intent i=new Intent(AddArticle.this,StartEarning.class);

        startActivity(i);
    }
}
