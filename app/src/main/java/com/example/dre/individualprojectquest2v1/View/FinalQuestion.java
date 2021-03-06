package com.example.dre.individualprojectquest2v1.View;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dre.individualprojectquest2v1.Constants.Constant;
import com.example.dre.individualprojectquest2v1.R;

import java.util.HashSet;
import java.util.Set;

public class FinalQuestion extends AppCompatActivity {

    // should prob fire up lint or something
    // know some of these are not used
    // workload is very high right now
    // this should be two different activites or
    // some of the code should be offloaded to java files



    private Button finalWagerBtn;
    private Button endQuiz;
    private TextView tVInstructionsNResults;
    private TextView pointsWager;
    private SharedPreferences myPrefs;
    //private TextView testView;
    private boolean endGameBool;
    private SeekBar seekBar;
    private EditText userInitials;
    private int points =0;
    private int pointsWagerInt = 0;
    private String pointsWagerNFinalAnswer = "points wager ="; // this changes to final score after wager submission

    private String topScoreSoFar = "";
    private String SecondScoreSoFar = "";
    private String thirdScoreSoFar = "";
    private String tempScore = "";
    private String tempScore2nd = "";

    private Set<String> set1 = new HashSet<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_question);
        tVInstructionsNResults = (TextView) findViewById(R.id.textViewFinal);
        tVInstructionsNResults.setText("Make a points wager before you find out the final question");

        userInitials = (EditText) findViewById(R.id.editTextInitials);
        pointsWager = (TextView) findViewById(R.id.tvPointsWager);
        finalWagerBtn = (Button) findViewById(R.id.wagerQ6Sub);
        endQuiz = (Button) findViewById(R.id.confirmID);
        endQuiz.setClickable(false);
        endQuiz.setVisibility(View.INVISIBLE);


        //Todo test code remove
        //testView = (TextView) findViewById(R.id.textViewTestSharedID);

        // data retreval

        SharedPreferences prefs = getSharedPreferences(Constant.PREFS_ANSWERS, 0);

        // *********************************************
        // reads point totals on a type and totals them

        if(prefs.contains("Q1"))
        {
            points += prefs.getInt("Q1",0);
        }


        if(prefs.contains("Q2A1"))
        {
            points += prefs.getInt("Q2A1", 0);
        }

        if(prefs.contains("Q2A2"))
        {
            points += prefs.getInt("Q2A2",0);
        }

        if(prefs.contains("Q2A3"))
        {
            points += prefs.getInt("Q2A3",0);
        }

        if(prefs.contains("Q3"))
        {
            points += prefs.getInt("Q3", 0);
        }

        if(prefs.contains("Q4"))
        {
            points += prefs.getInt("Q4", 0);
        }

        if(prefs.contains("Q5"))
        {
            points += prefs.getInt("Q5", 0);
        }

        //testView.setText("points = " + Integer.toString(points));

        // updates the value presented so you know how far over the
        // seekbar is
        seekBar = (SeekBar) findViewById(R.id.finalSeekbar);
        seekBar.setMax(points);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pointsWager.setText(pointsWagerNFinalAnswer + Integer.toString(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });






        finalWagerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FinalQuestion.this);
                builder.setMessage("Are you sure about your wager?").setPositiveButton("yes", dialogClickListner)
                        .setNegativeButton("No", dialogClickListner).show();

            }
        });

        // if the quiz ends this saves the score in the correct order by highest
        // should change how this is coded

        endQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(endGameBool) // writes final score and switches intent
                {

                    if(userInitials.length() < 4)
                    {
                        myPrefs = getSharedPreferences(Constant.PREFS_SCORE, 0);
                        SharedPreferences.Editor editor = myPrefs.edit();
                        topScoreSoFar = myPrefs.getString("TopScore",null);
                        SecondScoreSoFar = myPrefs.getString("SecondScore", null);
                        thirdScoreSoFar = myPrefs.getString("ThirdScore", null);

                        // this code orders the top 3 scores
                        // this code should not be in a imo
                        // very busy so i ordered the dictionary values like this
                        // prob should be in some sort of list than deligated out to the pref files



                        if(thirdScoreSoFar == null && SecondScoreSoFar !=null && topScoreSoFar !=null ) // is a top score but not a second
                        {
                            if(Integer.parseInt(topScoreSoFar) < points) // place in first
                            {
                                tempScore = myPrefs.getString("TopScore", "0"); // stores old first
                                tempScore2nd = myPrefs.getString("SecondScore", "0"); // stores old second
                                editor.putString("TopScore", Integer.toString(points));
                                editor.putString("SecondScore", tempScore); // put first in second
                                editor.putString("ThirdScore", tempScore2nd);//put second in third
                                editor.apply();
                            }
                            else if(Integer.parseInt(SecondScoreSoFar) < points)
                            {
                                tempScore = myPrefs.getString("SecondScore", "0");
                                editor.putString("SecondScore",Integer.toString(points));
                                editor.putString("ThirdScore", tempScore);
                            }
                            else
                            {
                                editor.putString("ThirdScore", Integer.toString(points));
                            }

                        }


                        if(SecondScoreSoFar == null && topScoreSoFar !=null ) // is a top score but not a second
                        {
                            if(Integer.parseInt(topScoreSoFar) < points)
                            {
                                tempScore = myPrefs.getString("TopScore", "0");
                                editor.putString("SecondScore",tempScore);
                                editor.putString("TopScore", Integer.toString(points));
                                editor.apply();
                            }
                            else
                            {
                                editor.putString("SecondScore", Integer.toString(points));
                                editor.apply();
                            }
                        }


                        if(topScoreSoFar == null)
                        {
                            editor.putString("TopScore", Integer.toString(points));
                            editor.apply();
                        }
                        else // a top score found so need to compare
                        {
                            if(Integer.parseInt(topScoreSoFar) < points)
                            {
                                editor.putString("TopScore", Integer.toString(points));
                                editor.apply();
                            }
                        }


                        // scores intent opens
                        Intent intent = new Intent(FinalQuestion.this, ScoreInflatorActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else // this dialog just tells you enter valid initials
                    {
                        userInitials.setText("");
                        AlertDialog.Builder builder3 = new AlertDialog.Builder(FinalQuestion.this);
                        builder3.setMessage("Please Enter Initial length less than 4").setPositiveButton("Okay", dialogClickListner3).show();
                        //Todo store initials at the same index


                    }


                }
                else // this dialog box confirms it is your final answer
                {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(FinalQuestion.this);
                    builder2.setMessage("Are you sure about your final Answer?").setPositiveButton("yes", dialogClickListner2)
                            .setNegativeButton("No", dialogClickListner2).show();
                }

            }
        });

    }

    // there is two buttons it switches after the first dialog box
    // that is most of what this code dones
    // it also relabels the TextView at the top
    // and resets the max size of the seekbar so you
    // can answer the last question

    DialogInterface.OnClickListener dialogClickListner = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {

                case DialogInterface.BUTTON_POSITIVE:
                    finalWagerBtn.setVisibility(View.INVISIBLE);
                    finalWagerBtn.setClickable(false);
                    endQuiz.setClickable(true);
                    endQuiz.setVisibility(View.VISIBLE);
                    pointsWagerInt = seekBar.getProgress();
                    tVInstructionsNResults.setText("Find (!7)? Please answer with the seekbar (only need to be within 30 for full credit");
                    pointsWagerNFinalAnswer = "Final answer = ";
                    seekBar.setMax(7500);


                    //ToDO this is bad form and should be recoded





                case DialogInterface.BUTTON_NEGATIVE:
                    //ToDo
                    break;
            }
        }
    };

    // confirms the answer
    // changes the TextView at the top to show you final score
    // you can move on to the top scores activity after this
    // also evaluates the points wager

    DialogInterface.OnClickListener dialogClickListner2 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {

                case DialogInterface.BUTTON_POSITIVE:
                    seekBar.getProgress();
                    if(seekBar.getProgress() > 5010 && seekBar.getProgress() < 5070)
                    {
                        points = points + pointsWagerInt;
                    }
                    else
                    {
                        points = points - pointsWagerInt;
                    }

                    tVInstructionsNResults.setTextSize(22);
                    tVInstructionsNResults.setBackgroundColor(Color.RED);
                    tVInstructionsNResults.setTextColor(Color.BLACK);

                    tVInstructionsNResults.setText("Your score : " + points);
                    userInitials.setVisibility(View.VISIBLE);
                    userInitials.setHint("Enter Initials");
                    endQuiz.setText("Submit Initials");
                    endGameBool = true;


                    //Todo display results bring to topscore




                case DialogInterface.BUTTON_NEGATIVE:
                    //ToDo
                    break;
            }
        }
    };

    // left empty on purpose


    DialogInterface.OnClickListener dialogClickListner3 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {

                case DialogInterface.BUTTON_POSITIVE:
            }
        }
    };



}
