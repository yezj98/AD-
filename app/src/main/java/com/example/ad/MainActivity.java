package com.example.ad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.IpSecManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ad.Result.end;
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
    int a = 0, i=0;
    TextView tv_question;
    int correct = 0, wrong = 0;
    ArrayList<Integer> list = new ArrayList<Integer>(10);

    private String answer;

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

        for (int i = 1; i <=11; i++) {
            list.add(i);
            Log.d("snumber", "" + list);
        }
        Collections.shuffle(list);

        NextQuestion();
    }

//
//    private void GameOver() {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
//        alertDialogBuilder
//                .setMessage("Game Over")
//                .setCancelable(false)
//                .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                    }
//                })
//                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        System.exit(0);
//                    }
//                });
//        alertDialogBuilder.show();
//    }


    private void NextQuestion() {


        Log.d("countnumber ", "" + a);
        int number = list.get(a);
        Log.d("xnumber ", "" + number);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ECS").child("Q" + number);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final get get = dataSnapshot.getValue(get.class);

                tv_question.setText(get.getQuestion());
                btn_one.setText(get.getOption1());
                Log.d ("dd",""+get.getOption1());
                btn_two.setText(get.getOption2());
                btn_three.setText(get.getOption3());
                btn_four.setText(get.getOption4());
                Log.d ("ddd",""+get.getAnswer());

                btn_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (btn_one.getText().toString().equals(get.getAnswer())) {

                            Toast.makeText(MainActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                            btn_one.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                            correct++;
                            a++;
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    btn_one.getBackground().clearColorFilter();
                                    NextQuestion();
                                }
                            }, 300);
                        } else {
                            wrong++;
                            a++;

                            NextQuestion();

                        }
                        }
                    });


                        btn_two.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (btn_two.getText().toString().equals(get.getAnswer())) {
                                    Toast.makeText(MainActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
                                    correct++;
                                    a++;
                                    btn_two.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            btn_two.getBackground().clearColorFilter();
                                            NextQuestion();
                                        }
                                    },300);

                                } else {
                                    wrong++;
                                    a++;
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
                                    a++;
                                    btn_three.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            btn_three.getBackground().clearColorFilter();
                                            NextQuestion();
                                        }
                                    },300);
                                } else {
                                    wrong++;
                                    a++;
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
                                    a++;
                                    btn_four.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            btn_four.getBackground().clearColorFilter();
                                            NextQuestion();
                                        }
                                    },300);
                                } else {
                                    wrong++;
                                    a++;
                                    NextQuestion();
                                }
                            }
                        });

                    }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (a == 10 ) {
            Bundle bundle = new Bundle();
            bundle.putInt("correct", correct);
            Bundle bundle1 = new Bundle();
            bundle1.putInt("wrong", wrong);
            Intent intent = new Intent(MainActivity.this, end.class);
            intent.putExtras(bundle);
            intent.putExtras(bundle1);
            startActivity(intent);
        }

    }
}
