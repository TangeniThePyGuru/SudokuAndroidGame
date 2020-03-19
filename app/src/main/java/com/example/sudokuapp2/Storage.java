package com.example.sudokuapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.widget.EditText;

public class Storage {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    // store signs in this array, + means that the number belongs to the
    public static String[][] signs = new String[9][9];

    public Storage(Context context){
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        editor = preferences.edit();
    }

    public int[][] readSavedGame(){

        // initialize the an array to store the data
        int[][] game = new int[9][9];

        // read the persisted data
        String savedGame = preferences.getString("GAME", "");

        String[] rows = savedGame.split("\n");
        // loop through the rows
        for (int i = 0; i < rows.length; i++){
            String[] singleRow;
            singleRow = rows[i].split(",");
            // loop through the row
            for (int j = 0; j < singleRow.length; j++){
                // players number
                if (singleRow[j].substring(0,1) == "+")
                    game[i][j] = Integer.parseInt(singleRow[j].substring(1));
                // generated numbers
                else
                    game[i][j] = Integer.parseInt(singleRow[j].substring(1));
            }
        }

        return game;
    }

    public void saveGame(int[][] game, EditText[][] gameBoard){
        String row = "";

        // clear all the existing data
        editor.clear();
        for (int i = 0; i < game.length; i++){
            for (int j = 0; j < game.length; j++){
                // all the zeros belong to the player
                if (game[i][j] == 0){
                    row += "+"+game[i][j];
                    signs[i][j] = "+";
                } else {
                    // all the non-zeros in a gray area were generated
                    if (((ColorDrawable) gameBoard[i][j].getBackground()).getColor() == Color.LTGRAY) {
                        row += "-" + game[i][j];
                        signs[i][j] = "-";
                    }
                    // all the numbers in the green colored boxes belong to the player
                    else {
                        row += "+" + game[i][j];
                        signs[i][j] = "+";
                    }
                }
                // no comma should appear at the end of a row
                if( j + 1 < game.length)
                    row += ",";
            }
            // no new line char should appear at the end of the last row
            if( i + 1 < game.length)
                row += "\n";
        }

        // save the game to the persistent memory
        editor.putString("GAME", row);
        // persist the game
        editor.apply();
    }

}
