package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class GameDisplay extends AppCompatActivity {

    private TicTacToeBoard ticTacToeBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_display);

        Button playAgainBTN = findViewById(R.id.button5);
        Button homeBTN = findViewById(R.id.button6);
        TextView playerTurn = findViewById(R.id.playerTurn);

        //the buttons will not be visible until the game is finished
        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);

        String[] playerNames = getIntent().getStringArrayExtra("PLAYER_NAMES");

        //if the user didn't assign names
        if(playerNames != null)
        {
            playerTurn.setText(playerNames[0]+ "'s Turn");
        }

        //find the board and store it into a variable
        ticTacToeBoard  = findViewById(R.id.ticTacToeBoard);

        ticTacToeBoard.setUpGame(playAgainBTN, homeBTN, playerTurn, playerNames);
    }

    public void playAgainBtnClick(View view)
    {
       ticTacToeBoard.resetGame();
       //update the display
       ticTacToeBoard.invalidate();
    }

    public void homeBtnClick(View view)
    {
        //this btn will direct the user back to the home screen
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }



}