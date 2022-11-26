package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void playBtnClick(View view){
        Intent intent = new Intent(this, Played_Setup.class);
        startActivity(intent);
    }
}