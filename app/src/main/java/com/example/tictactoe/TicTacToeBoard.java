package com.example.tictactoe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TicTacToeBoard extends View {

    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winningLineColor;
    private int cellSize = getWidth()/3;
    private final Paint paint = new Paint();
    private final GameLogic game;
    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        game = new GameLogic();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard , 0, 0);
        try{
            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0 );
            XColor = a.getInteger(R.styleable.TicTacToeBoard_XColor, 0 );
            OColor = a.getInteger(R.styleable.TicTacToeBoard_OColor, 0 );
            winningLineColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor, 0 );

        }finally {
            a.recycle();
        }

    }

    @Override
    protected void onMeasure(int width, int height)
    {
        super.onMeasure(width,height);
        //getting the user's screen measure so our board will fit
        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        //there are 3 cells in a row
        cellSize =dimension/3;
        setMeasuredDimension(dimension, dimension);

    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        //Draw lines
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        drawGameBoard(canvas);
//        drawX(canvas,1,1);
//        drawO(canvas, 2,2);
        drawMarkers(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //getting the (x,y) position of the user's top
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            //converting the tap to the row and col on the board
            int row = (int)Math.ceil(y/cellSize);
            int col = (int)Math.ceil(x/cellSize);

            if(game.updateGameBoard(row,col)){
                invalidate();

                //updating the player's turn
                if(game.getPlayer() % 2 == 0)
                    //determine if its even or odd number
                    game.setPlayer(game.getPlayer()-1);
                else
                    game.setPlayer(game.getPlayer()+1);
            }

            invalidate();
            return true;
        }
        return false;
    }

    private void drawGameBoard(Canvas canvas)
    {
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);
        for(int i=1; i<3; i++)
        {
            //draw coll
            canvas.drawLine(cellSize*i, 0, cellSize*i, canvas.getWidth(), paint);
        }
        for(int j=1; j<3; j++)
        {
            canvas.drawLine(0, cellSize*j, canvas.getWidth(),cellSize*j, paint);
        }
    }


    private void drawMarkers(Canvas canvas)
    {
        //scan the board and choose to draw X or O
        for (int i=0; i<3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game.getGameBoard()[i][j] != 0) {
                    if (game.getGameBoard()[i][j] == 1)
                        drawX(canvas, i ,j);
                    else
                        drawO(canvas, i ,j);
                }
            }
        }

    }
    private void drawX(Canvas canvas, int row, int col)
    {
        paint.setColor(XColor);

        canvas.drawLine((col+1)*cellSize,
                row*cellSize,
                col*cellSize,
                (row+1)*cellSize,
                paint);
        canvas.drawLine((col)*cellSize,
                (row)*cellSize,
                (col+1)*cellSize,
                (row+1)*cellSize,
                paint);
    }
    private void drawO(Canvas canvas, int row, int col)
    {
        paint.setColor(OColor);

        canvas.drawOval(col*cellSize,
                row*cellSize,
                col*cellSize+ cellSize,
                row*cellSize+cellSize,
                paint);

    }


    public void setUpGame(Button playAgain, Button home,TextView playerDisplay, String[] names )
    {
        //assign the values
        game.setPlayAgainBTN(playAgain);
        game.setHomeBTN(home);
        game.setPlayerTurn(playerDisplay);
        game.setNames(names);
    }


    public void resetGame(){
        game.resetGame();
    }


}
