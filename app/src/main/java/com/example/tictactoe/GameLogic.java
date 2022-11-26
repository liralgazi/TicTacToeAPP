package com.example.tictactoe;

import android.widget.Button;
import android.widget.TextView;

public class GameLogic {

    private int[][] gameBoard;
    private Button playAgainBTN;
    private Button homeBTN;
    private TextView playerTurn;
    //default names
    private String[] names = {"Player 1", "Player 2"};

    private  int player  = 1; //player 1 will always go first

    GameLogic(){
        gameBoard = new int[3][3];
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<3; j++)
            {
                //all the matrix (game board) will be 0
                gameBoard[i][j] = 0;
            }

        }
    }

    public boolean updateGameBoard(int row, int col)
    {
        //check if the box is empty
        if (gameBoard[row-1][col-1] == 0){
            gameBoard[row-1][col-1] = player;

            //updating the text view
            if (player == 1)
                playerTurn.setText((names[1]) + "'s Turn");
            else
                playerTurn.setText((names[0]) + "'s Turn");
            return true;
        }
        else
            //the spot isnt avaliable
            return false;
    }

    public void resetGame(){
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<3; j++)
            {
                //all the matrix (game board) will be 0
                gameBoard[i][j] = 0;
            }

        }
    }

    public void setPlayAgainBTN(Button playAgainBTN) {
        this.playAgainBTN = playAgainBTN;
    }

    public void setHomeBTN(Button homeBTN) {
        this.homeBTN = homeBTN;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }
}
