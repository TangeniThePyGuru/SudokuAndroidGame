package com.example.sudokuapp2;

import android.annotation.SuppressLint;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.Color;
import android.content.Context;
import android.widget.GridLayout;
import android.widget.TextView;


@SuppressLint("ViewConstructor")
public class AppInterface extends GridLayout
{
    private EditText[][] board;
    public TextView message;
    public Button btnNewGame;
    public Button btnSaveGame;
    public Button btnResumeGame;
//    private int size;
    public AppInterface(Context context, int size, int width)
    {
        super(context);
        //create interface
        setRowCount(size + 2);
        setColumnCount(size);

        board = new EditText[size][size];

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                board[i][j] = new EditText(context);
                board[i][j].setBackgroundColor(Color.parseColor("#009900"));
                board[i][j].setInputType(InputType.TYPE_CLASS_NUMBER);
                board[i][j].setGravity(Gravity.CENTER);
                board[i][j].setPadding(0, 3, 0, 0);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = width;
                params.height = width;
                params.rowSpec = GridLayout.spec(i, 1);
                params.columnSpec = GridLayout.spec(j, 1);
                params.topMargin = params.bottomMargin = 1;
                params.leftMargin = params.rightMargin = 1;
                board[i][j].setLayoutParams(params);
                addView(board[i][j]);
            }
        }

        message = new TextView(context);
        message.setGravity(Gravity.CENTER_HORIZONTAL);
        message.setPadding(2,2,2,2);
        message.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.WRAP_CONTENT;
        params.rowSpec = GridLayout.spec(size, 1);
        params.columnSpec = GridLayout.spec(0, size);
        params.topMargin = params.bottomMargin = 5;
        params.leftMargin = params.rightMargin = 5;
        message.setLayoutParams(params);
        addView(message);

        // new game button
        btnNewGame = new Button(context);
        btnNewGame.setId(generateViewId());
        btnNewGame.setGravity(Gravity.CENTER_HORIZONTAL);
//        btnNewGame.setPadding(2,2,2,2);
        btnNewGame.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        btnNewGame.setText("NEW GAME");
        btnNewGame.setBackgroundColor(Color.RED);
        params = new GridLayout.LayoutParams();
        params.width = width * 3;
        params.height = width;
        params.rowSpec = GridLayout.spec(size + 1, 1);
        params.columnSpec = GridLayout.spec(0, 3);
//        params.topMargin = params.bottomMargin = 5;
//        params.leftMargin = params.rightMargin = 5;
        btnNewGame.setLayoutParams(params);
        addView(btnNewGame);

        // save game button
        btnSaveGame = new Button(context);
        btnSaveGame.setId(generateViewId());
        btnSaveGame.setGravity(Gravity.CENTER_HORIZONTAL);
//        btnSaveGame.setPadding(2,2,2,2);
        btnSaveGame.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        btnSaveGame.setText("SAVE GAME");
        btnSaveGame.setBackgroundColor(Color.BLUE);
        params = new GridLayout.LayoutParams();
        params.width = width * 3;
        params.height = width;
        params.rowSpec = GridLayout.spec(size + 1, 1);
        params.columnSpec = GridLayout.spec(3, 3);
//        params.topMargin = params.bottomMargin = 5;
//        params.leftMargin = params.rightMargin = 5;
        btnSaveGame.setLayoutParams(params);
        addView(btnSaveGame);

        // resume game button
        btnResumeGame = new Button(context);
        btnResumeGame.setId(generateViewId());
        btnResumeGame.setGravity(Gravity.CENTER_HORIZONTAL);
//        btnResumeGame.setPadding(2,2,2,2);
        btnResumeGame.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        btnResumeGame.setText("RESUME GAME");
        btnResumeGame.setBackgroundColor(Color.GREEN);
        params = new GridLayout.LayoutParams();
        params.width = width * 3;
        params.height = width;
        params.rowSpec = GridLayout.spec(size + 1, 1);
        params.columnSpec = GridLayout.spec(6, 3);
//        params.topMargin = params.bottomMargin = 5;
//        params.leftMargin = params.rightMargin = 5;
        btnResumeGame.setLayoutParams(params);
        addView(btnResumeGame);
    }

    public void drawInitialBoard(int[][] board)
    {
        //draw initial board
        for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < this.board.length; j++){
                if (board[i][j] == 0) {
                    this.board[i][j].setText("");
                    this.board[i][j].setEnabled(true);
                } else {
                    this.board[i][j].setText(board[i][j]+"");
                    this.board[i][j].setBackgroundColor(Color.LTGRAY);
                    this.board[i][j].setEnabled(false);
                }
            }
        }
    }

    // draw the saved game onto the board
    public void drawSavedBoard(int[][] board){
        for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < this.board.length; j++){
                // fill the players cells with number
                if (Storage.signs[i][j].equals("+")) {
                    this.board[i][j].setText(board[i][j]+"");
                    this.board[i][j].setEnabled(true);
                    this.board[i][j].setBackgroundColor(Color.parseColor("#009900"));
                } else {
                    // fill the cells with generated numbers
                    this.board[i][j].setText(board[i][j]+"");
                    this.board[i][j].setBackgroundColor(Color.LTGRAY);
                    this.board[i][j].setEnabled(false);
                }
            }
        }
    }

    public void setTextChangeHandler(TextWatcher textChangeHandler, int x, int y)
    {
         board[x][y].addTextChangedListener(textChangeHandler);
    }

    public String getInput(int x, int y)
    {
        //return input at x, y
        return board[x][y].getText().toString();
    }

    public void clear(int x, int y)
    {
        //clear input at x, y
        board[x][y].setText("");
    }

    public EditText[][] getBoard() {
        return this.board;
    }
}
