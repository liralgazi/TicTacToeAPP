package com.example.tictactoe;

import android.annotation.SuppressLint;
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

    private boolean winningLine= false;
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

        drawMarkers(canvas);

        //drawing the line on the board
        if(winningLine)
        {
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
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

            //prevent the users to tap on the board after there's a winner
            if (!winningLine)
            {
                if (game.updateGameBoard(row, col)) {
                    invalidate();
                    if(game.winnerCheck()){
                        winningLine = true;
                        //updating our board
                        invalidate();
                    }

                    //updating the player's turn
                    if (game.getPlayer() % 2 == 0)
                        //   determine if its even or odd number
                        game.setPlayer(game.getPlayer() - 1);
                    else
                        game.setPlayer(game.getPlayer() + 1);
                }
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

        canvas.drawLine((float)((col+1)*cellSize - cellSize*0.2),
                (float)(row*cellSize + cellSize*0.2),
                (float)(col*cellSize + cellSize*0.2),
                (float)((row+1)*cellSize -cellSize*0.2),
                paint);
        canvas.drawLine((float)((col)*cellSize + cellSize*0.2),
                (float)((row)*cellSize + cellSize*0.2),
                (float)((col+1)*cellSize - cellSize*0.2),
                (float)((row+1)*cellSize - cellSize*0.2),
                paint);
    }
    private void drawO(Canvas canvas, int row, int col)
    {
        paint.setColor(OColor);

        canvas.drawOval((float)(col*cellSize + cellSize*0.2),
                (float)(row*cellSize + cellSize*0.2),
                (float)((col*cellSize+ cellSize) -cellSize*0.2),
                (float)((row*cellSize+cellSize) - cellSize*0.2),
                paint);

    }


    //DRAW LINES METHODS

    private void drawHorizontalLine(Canvas canvas,int row,int col )
    {
        //draw the line int the middle of the row (middle of the cell)
        canvas.drawLine(col,
                (float) (row*cellSize + cellSize/2),
                cellSize*3,
                (float) (row*cellSize + cellSize/2),
                paint);
    }

    private void drawVerticalLine(Canvas canvas,int row,int col )
    {
        //draw the line int the middle of the row (middle of the cell)
        canvas.drawLine((float) (col*cellSize + cellSize/2),
                row,
                (float) (col*cellSize + cellSize/2),
                cellSize*3,
                paint);
    }

    private void drawDiagonalLinePos(Canvas canvas)
    {
        //draw the line int the middle of the row (middle of the cell)
        canvas.drawLine(0,
                cellSize*3,
                cellSize*3,
                0,
                paint);
    }

    private void drawDiagonalLineNeg(Canvas canvas)
    {
        //draw the line int the middle of the row (middle of the cell)
        canvas.drawLine(0,
                0,
                cellSize*3,
                cellSize*3,
                paint);
    }

    private void drawWinningLine(Canvas canvas)
    {
        //draw the line according to the winner
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];
        switch (game.getWinType()[2]){
            //horizontal
            case 1:
                drawHorizontalLine(canvas,row,col);
                break;
            case 2:
                drawVerticalLine(canvas,row,col);
                break;
            case 3:
                drawDiagonalLineNeg(canvas);
                break;
            case 4:
                drawDiagonalLinePos(canvas);
                break;
        }
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
        winningLine =false;
    }


}
