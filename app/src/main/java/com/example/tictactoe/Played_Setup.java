package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;

public class Played_Setup extends AppCompatActivity {

    private EditText player1;
    private EditText player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.played_setup);
        //extract the players name by ID
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
    }

    public void submitBtnClick(View view)
    {
        String player1Name = player1.getText().toString();
        String player2Name = player2.getText().toString();
        Intent intent =  new Intent(this, GameDisplay.class);

        intent.putExtra("PLAYER_NAMES", new String[] {player1Name, player2Name});
        startActivity(intent);

    }
}