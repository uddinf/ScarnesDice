package com.example.demouser.dice_project;

import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.Random;

/**
 * Class the has the game logic
 */
public class MainActivity extends AppCompatActivity
{

    private Random rand = new Random();
    ImageView diceImageView;
    TextView usersOverallScoreTxtVw, computersScoreTxtVw, usersCurrentTurnsScoreTxtVw;
    Button rollBtn, holdBtn, resetBtn;
    Resources res;
    Boolean yourTurn = true;

    private int userOverallScore = 0;

    private int usersTurnScore = 0;

    private int computersOverallScore = 0;

    private int computersTurnsScore = 0;

    private int currRoll = 0;

    private Runnable rollDice;
    private Handler handler = new Handler();


    private void refresh()
    {

    }

    /**
     * Constructor
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        diceImageView = findViewById(R.id.diceImage);

        usersOverallScoreTxtVw = findViewById(R.id.UsersScore);

        computersScoreTxtVw = findViewById(R.id.ComputersScore);

        usersCurrentTurnsScoreTxtVw = findViewById(R.id.CurrentTurnsScore);// status

        rollBtn = findViewById(R.id.RollButton);

        holdBtn = findViewById(R.id.HoldButton);

        resetBtn = findViewById(R.id.ResetButton);

        res = getResources();

        // diceImageView = diceImageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.dice1));


        rollButton();
        resetButton();
        holdButton();

        // just a way to use run r, we use handler because android works with this
        //
        usersOverallScoreTxtVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Handler handler = new Handler(); // handles an action after a certain amount of time

                // this will perform an action without stopping the thread and remaining active
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        int result = rand.nextInt(1000);
                        usersOverallScoreTxtVw.setText(Integer.toString(rand.nextInt(100))); // updates the user's score each time it's clicked

                        if (result > 100) {
                            handler.postDelayed(this, 1000);
                        }

                    }
                };
                handler.postDelayed(r,1000); // calls handler function to delay action for a few seconds
            }
        });

    }

    /**
     * Method for button to be rolled
     */
    private void rollButton() {

        rollBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                int currRoll; // current roll variable

                currRoll = rollDie(); // roll die method is saved to current roll since it returns an int value

                // if the current roll does not equal 1
                if (currRoll != 1)
                {
                    usersTurnScore += currRoll; // adds the result of the dice rolled to the current roll's score
                    usersCurrentTurnsScoreTxtVw.setText("User's Current Turn Score: " + usersTurnScore);
                }

                else
                {
                    usersTurnScore = 0; // sets result to zero if the user rolls a one

                    usersCurrentTurnsScoreTxtVw.setText("User's Current Turn Score: " + usersTurnScore); // resets the current score to zero if user gets a one

//                    computerTurn();
                }

            }
        });

    }

    /**
     * Resets all buttons to have a value of zero
     */
    private void resetButton()
    {
        // action listener for the reset button to do necessary action of resetting all scores
        resetBtn.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                userOverallScore = 0;
                usersTurnScore = 0;
                computersOverallScore = 0;
                computersTurnsScore = 0;

                usersOverallScoreTxtVw.setText("User's Score: " + userOverallScore);

                computersScoreTxtVw.setText("Computer's Score: " + computersOverallScore);

                usersCurrentTurnsScoreTxtVw.setText("User's Current Turn Score: " + usersTurnScore);
            }

        });
    }

    /**
     * Function for hold button
     */
    private void holdButton()
    {
        holdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userOverallScore += usersTurnScore; // adds the overall score to the user's score

                usersTurnScore= 0;

                usersOverallScoreTxtVw.setText("User's Score: " + userOverallScore);

                usersCurrentTurnsScoreTxtVw.setText("User's Current Turn Score: " + usersTurnScore);

                computerTurn();

            }
        });
    }

    /**
     * Helper method for the computer's turn
     */
    private void computerTurn ()
    {

        rollBtn.setEnabled(false);
        resetBtn.setEnabled(false);
        holdBtn.setEnabled(false);

        final int currRoll = rollDie();


        rollDice = new Runnable()
        {
            @Override
            public void run() {
                if(computersTurnsScore < 20 && currRoll != 1)
                {
                    computersTurnsScore += currRoll;
                    handler.postDelayed(this, 500);
                }
                else if(currRoll == 1)
                {
                    computersScoreTxtVw.setText("");// status
                    computersTurnsScore = 0;
                    resetBtn.setEnabled(true);
                    rollBtn.setEnabled(true);

                }
                else {
                    computersOverallScore += computersTurnsScore;
                    computersScoreTxtVw.setText("Computer's Score: " + computersOverallScore);// status
                    resetBtn.setEnabled(true);
                    rollBtn.setEnabled(true);
                    holdBtn.setEnabled(true);
                    computersTurnsScore = 0;
                }
            }
        };

        handler.postDelayed(rollDice, 1000);

//        Boolean compsTurn = true;
        // Disable the roll and hold buttons

//        holdBtn.setEnabled(false);
//        rollBtn.setEnabled(false);

//        while (compsTurn)
//        {
//            int currRoll = rollDie();
//            computersTurnsScore += currRoll;

            // if the roll is 1, current score for computer is zero and we don't add it to the overall score, and its the users turn
//            if (currRoll == 1) {
//                compsTurn = false;
//                computersTurnsScore = 0;
//            }

            // if turn score is >= 20, hold
//            if( computersTurnsScore >= 20)
//            {
//                compsTurn = false;
//                computersOverallScore += computersTurnsScore;
//                computersScoreTxtVw.setText("Computer's Score: " + computersOverallScore);
//                computersTurnsScore = 0;
//            }


//        }

//        holdBtn.setEnabled(true);
//        rollBtn.setEnabled(true);

        // Create a while loop to iterate over each rolled dice

    }

    /**
     * Helper method for rolling functionality
     * @return
     */
    private int rollDie()
        {
            int result = rand.nextInt(6) + 1;
            switch (result)
            {
                case 1:
//                    diceImageView.setImageDrawable(res.getDrawable(R.drawable.dice1)); can use if you don't mind about the through line
                    diceImageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.dice1));
                    break;
                case 2:
                    diceImageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.dice2));
                    break;
                case 3:
                    diceImageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.dice3));
                    break;
                case 4:
                    diceImageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.dice4));
                    break;
                case 5:
                    diceImageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.dice5));
                    break;
                case 6:
                    diceImageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.dice6));
                    break;
            }
            return result;
        }

}

