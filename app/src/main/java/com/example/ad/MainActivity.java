package com.example.ad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button btn_one, btn_two, btn_three, btn_four;
    int a = 0;
    Array hello[];
    TextView tv_question;
    int correct = 0, wrong = 0;
    ArrayList<Integer> list = new ArrayList<Integer>(10);
    private Question question = new Question();

    private String answer;
    private int questionLength = question.questions.length;

    int x = 0;

    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new Random();


        btn_one = (Button) findViewById(R.id.btn_one);

        btn_two = (Button) findViewById(R.id.btn_two);

        btn_three = (Button) findViewById(R.id.btn_three);

        btn_four = (Button) findViewById(R.id.btn_four);


        tv_question = (TextView) findViewById(R.id.tv_question);

        for (int i = 1; i<= 10; i++){
            list.add(i);
            Log.d ("snumber","" + i);
        }
        Collections.shuffle(list);

        NextQuestion();
    }


    private void GameOver() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder
                .setMessage("Game Over")
                .setCancelable(false)
                .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
        alertDialogBuilder.show();
    }

    public static int randomNumber(int max) {
        int a;
        boolean found = false;
        Random random = new Random();
        a = random.nextInt(max);
        ArrayList<Integer> list = new ArrayList<Integer>(max);

        for (int i = 0; i < max; i++)
        {
            list.add(i);

        }

        for (int i = 0; i<=max; i++) {
            if (a == list.get(i))
            {
                a = random.nextInt(max);
            }
            else
            {
                return a;
            }
        }

//        if (found = true)
//        {
//
//        }
//
        Log.d("Number current",""+a);
        return a;
    }



    private void NextQuestion() {


        a++;
        a = a;
        Log.d ("countnumber " ,""+a);
        int number = list.get(a);
        Log.d ("xnumber " ,""+number );
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ECS").child("Q" + number);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final get get = dataSnapshot.getValue(get.class);

                tv_question.setText(get.getQuestion());
                btn_one.setText(get.getOption1());
                btn_two.setText(get.getOption2());
                btn_three.setText(get.getOption3());
                btn_four.setText(get.getOption4());

                btn_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (btn_one.getText().toString().equals(get.getAnswer())) {
                            Toast.makeText(MainActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                            correct++;
                            NextQuestion();
                        } else {
                            wrong++;
                            NextQuestion();

                        }
                        btn_two.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (btn_two.getText().toString().equals(get.getAnswer())) {
                                    Toast.makeText(MainActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                                    correct++;
                                    NextQuestion();

                                } else {
                                    wrong++;
                                    NextQuestion();
                                }
                            }
                        });
                        btn_three.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (btn_three.getText().toString().equals(get.getAnswer())) {
                                    Toast.makeText(MainActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                                    correct++;
                                    NextQuestion();
                                } else {
                                    wrong++;
                                    NextQuestion();
                                }
                            }
                        });
                        btn_four.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (btn_four.getText().toString().equals(get.getAnswer())) {
                                    Toast.makeText(MainActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                                    correct++;
                                    NextQuestion();
                                } else {
                                    wrong++;
                                    NextQuestion();
                                }
                            }
                        });

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void show() {
        final GenericTypeIndicator<List<String>> gti = new GenericTypeIndicator<List<String>>() {
        };

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> questionOptions = dataSnapshot.child("array").getValue(gti);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
