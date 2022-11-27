package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameLogic {

    private int[][] gameBoard;
    private Button playAgainBTN;
    private Button homeBTN;
    private TextView playerTurn;
    //default names
    private String[] names = {"Player 1", "Player 2"};

    //1st element --> row, 2end element --> col, 3rd element --> lineType
    private int[] winType = {-1,-1,-1};

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

    @SuppressLint("SetTextI18n")
    public boolean updateGameBoard(int row, int col)
    {
        //check if the box is empty
        if (gameBoard[row-1][col-1] == 0){
            gameBoard[row-1][col-1] = player;

            //updating the text view
            if (player == 1)
                //changes to the 2end player's name
                playerTurn.setText((names[1]) + "'s Turn");
            else
                //changes to the 1st player's name
                playerTurn.setText((names[0]) + "'s Turn");
            return true;
        }
        else
            //the spot isn't available
            return false;
    }


    //this function checks if there is a winner and returns true
    //(returns and false if there are no winners)
    @SuppressLint("SetTextI18n")
    public boolean winnerCheck()
    {
        boolean isWinner= false;
        //horizontal check (winType == 1)
        for (int i=0; i<3; i++){
            if (gameBoard[i][0] == gameBoard[i][1] &&
                    gameBoard[i][0] == gameBoard[i][2] &&
                    gameBoard[i][0] != 0) {

                isWinner = true;
                winType = new int[] {i, 0,1};
            }
        }
        //vertical check (winType == 2)
        for (int i=0; i<3; i++){
            if (gameBoard[0][i] == gameBoard[1][i] &&
                    gameBoard[0][i] == gameBoard[2][i] &&
                    gameBoard[0][i] != 0) {
                isWinner = true;
                winType = new int[] {0, i,2};

            }
        }

        //negative diagonal check (winType == 3)
        if (gameBoard[0][0] == gameBoard[1][1] &&
                gameBoard[0][0] == gameBoard[2][2] &&
                gameBoard[0][0] != 0) {
            isWinner = true;
            winType = new int[] {0, 2,3};

        }
        //positive diagonal check (winType == 4)
        if (gameBoard[2][0] == gameBoard[1][1] &&
                gameBoard[2][0] == gameBoard[0][2] &&
                gameBoard[2][0] != 0) {
            isWinner = true;
            winType = new int[] {2, 2,4};

        }
        int boardFilled = 0;

        //count how many cells are filled in the board
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<3; j++)
            {
                if (gameBoard[i][j] !=0 )
                    boardFilled +=1;
            }
        }
        if(isWinner)
        {
            //set the buttons to be visible again
            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);
            //show the winning player's name
            playerTurn.setText(names[player-1] + " WON !!!");
            return true;
        }
        else if (boardFilled == 9)
        {
            //set the buttons to be visible again
            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);
            //show the there was a tie
            playerTurn.setText("TIE GAME !!!!!");
            return true;
        }
        else
            return false;

    }
    @SuppressLint("SetTextI18n")
    public void resetGame(){
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<3; j++)
            {
                //all the matrix (game board) will be 0
                gameBoard[i][j] = 0;
            }
        }
        // resetting all the variables
        player =1;
        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);
        playerTurn.setText((names[0] + "'s Turn"));


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

    public int[] getWinType()
    {
        return winType;
    }
}
