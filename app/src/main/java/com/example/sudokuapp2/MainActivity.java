package com.example.sudokuapp2;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    private final int SIZE = 9;
    private Game game;
    private AppInterface appInterface;

    private Storage storage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = new Storage(this);

        // when app start generate a new game
        newGame();
    }


    class ButtonClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v.getId() == appInterface.btnNewGame.getId()){
                newGame();
            }

            else if (v.getId() == appInterface.btnResumeGame.getId()){
                resumeGame();
            }

            else if (v.getId() == appInterface.btnSaveGame.getId()){
                saveGame();
            }
        }
    }

    private class TextChangeHandler implements TextWatcher
    {
        private int x;
        private int y;

        public TextChangeHandler(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        public void afterTextChanged(Editable e)
        {
            boolean complete = false;

            //get input from x, y
            String input = appInterface.getInput(x, y);
            //Depending on the input, modify model and interface,
            //cover all cases (3 or 4 cases)
            if (input.equals("")){
//                appInterface
                game.set(0, x, y);
            } else if (input.length() > 1){
                appInterface.clear(x, y);
                game.set(0, x, y);
            } else {
                boolean status = game.check(Integer.parseInt(input), x, y);
                if (status){
                    game.set(Integer.parseInt(input), x, y);
                    outerloop:
                    for (int i = 0; i < game.getBoard().length; i++){
                        for (int j = 0; j < game.getBoard().length; j++){
                            if (game.getBoard()[i][j] == 0){
                                complete = false;
                                break outerloop;
                            } else complete = true;
                        }
                    }

                    // if user completes the game show them this message
                    if (complete == true){
                        appInterface.message.setText("Congratulations! You have completed the game.");
                        appInterface.message.setBackgroundColor(Color.parseColor("#009900"));
                        appInterface.message.setTextColor(Color.BLACK);
                    }
                } else {
                    game.set(0, x, y);
                    appInterface.clear(x, y);
                }
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        public void onTextChanged(CharSequence s, int start, int before, int after)
        {

        }
    }

    public void newGame(){
        //create model
        game = new Game();

        //create interface, draw initial board
        Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        int width = screenSize.x/SIZE;

        // initialize game board interface
        appInterface = new AppInterface(this, SIZE, width);
        appInterface.drawInitialBoard(game.getBoard());

        //attach event handlers
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                TextChangeHandler temp = new TextChangeHandler(i, j);
                appInterface.setTextChangeHandler(temp, i, j);
            }
        }

        // initialize event handler class
        View.OnClickListener onClickHandler = new ButtonClickHandler();

        // handle the events for the three buttons
        appInterface.btnSaveGame.setOnClickListener(onClickHandler);
        appInterface.btnResumeGame.setOnClickListener(onClickHandler);
        appInterface.btnNewGame.setOnClickListener(onClickHandler);

        // update the view with a grid
        setContentView(appInterface);
    }

    public void resumeGame(){

        // update board
        game.setBoard(storage.readSavedGame());
        // update game interface
        appInterface.drawSavedBoard(game.getBoard());
    }

    public void saveGame(){
        // save board to persistent memory
        storage.saveGame(game.getBoard(), appInterface.getBoard());
    }

}
