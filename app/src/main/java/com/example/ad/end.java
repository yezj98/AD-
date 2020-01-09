package com.example.ad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class end extends AppCompatActivity {
    TextView textView;

    String a = "aa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);


        textView = (TextView) findViewById(R.id.tv_question1);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int a = bundle.getInt("correct");
        int b = bundle.getInt("wrong");

        textView.setText("Total correct:" + a + "\n" + "Total wrong:" + b);


    }


}
