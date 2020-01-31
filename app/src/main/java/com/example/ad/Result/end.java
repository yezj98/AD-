package com.example.ad.Result;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ad.LogInActivity;
import com.example.ad.MainActivity;
import com.example.ad.MenuActivity;
import com.example.ad.Module;
import com.example.ad.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class end extends AppCompatActivity {
    TextView correct, wrong;
    List  <marks> userMarks =new ArrayList<>();
    TextView topScore1;
    TextView topScore2;
    TextView topScore3, moduleName, module1, module2, module3;
    Button home, again;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Intent intent = getIntent();
        final String previous  = intent.getStringExtra("FROM");

          topScore1 = findViewById(R.id.topScore1);
          topScore2 = findViewById(R.id.topScore2);
          topScore3 = findViewById(R.id.topScore3);

        module1 = findViewById(R.id.module1);
        module2 = findViewById(R.id.module2);
        module3 = findViewById(R.id.module3);

        correct = findViewById(R.id.textView);
        wrong = findViewById(R.id.textView1);

        home = findViewById(R.id.button2);

        again = findViewById(R.id.button);

        //Get the data from bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int a = bundle.getInt("correct");
            int b = bundle.getInt("wrong");

            correct.setText("Total correct:" + a);
            wrong.setText("Total wrong:" + b);
        }

        showScore();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(end.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (previous.equals("Module")){
                    Intent intent1 = new Intent(end.this, Module.class);
                    startActivity(intent1);
                }
                else if (previous.equals("Main")) {
                    Intent intent1 = new Intent(end.this, MainActivity.class);
                    startActivity(intent1);
                }

            }
        });
    }

    public void showScore () {


        String muserID = FirebaseAuth.getInstance().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(muserID).child("Score");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot d : dataSnapshot.getChildren()){
                marks marks = d.getValue(com.example.ad.Result.marks.class);
                userMarks.add(marks);

                Collections.sort(userMarks, new Comparator<com.example.ad.Result.marks>() {
                    @Override
                    public int compare(com.example.ad.Result.marks marks, com.example.ad.Result.marks t1) {
                        return t1.getScore() - marks.getScore();

                    }
                });
                loadRanks();
                Log.d("hello",""+ marks);

            }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadRanks() {

        if (userMarks.size()>= 3){
            topScore1.setText(Integer.toString(userMarks.get(0).getScore()));
            module1.setText(userMarks.get(0).getMoudule());
            topScore1.setVisibility(View.VISIBLE);
            module1.setVisibility(View.VISIBLE);
            topScore2.setText(Integer.toString(userMarks.get(1).getScore()));
            module2.setText(userMarks.get(1).getMoudule());
            topScore2.setVisibility(View.VISIBLE);
            module2.setVisibility(View.VISIBLE);
            topScore3.setText(Integer.toString(userMarks.get(2).getScore()));
            module3.setText(userMarks.get(2).getMoudule());
            topScore3.setVisibility(View.VISIBLE);
            module3.setVisibility(View.VISIBLE);
        }

        else if (userMarks.size() >= 2) {
            topScore2.setText(Integer.toString(userMarks.get(1).getScore()));
            module2.setText(userMarks.get(1).getMoudule());
            topScore2.setVisibility(View.VISIBLE);
            module2.setVisibility(View.VISIBLE);
        }

        else if (userMarks.size() >= 1){
            topScore1.setText(Integer.toString(userMarks.get(0).getScore()));
            module1.setText(userMarks.get(0).getMoudule());
            topScore1.setVisibility(View.VISIBLE);
            module1.setVisibility(View.VISIBLE);
        }
    }


}
