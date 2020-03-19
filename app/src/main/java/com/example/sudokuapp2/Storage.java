package com.example.sudokuapp2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Storage {

    public int[][] readSavedGame(){

        int[][] game = new int[9][9];

        try {

            File myObj = new File("sudoku.txt");
            Scanner myReader = new Scanner(myObj);
            int row = 0;
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                for (int j = 0; j < game.length; j++){
                    game[row][j] = Integer.parseInt(data[j]);
                }
                row++;
            }


            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return game;

    }

    public void saveGame(int[][] game){

        try {

            File myObj = new File("sudoku.txt");
            String row = "";
            // delete the file if it exists already
            if (myObj.exists()) myObj.delete();

            if (myObj.createNewFile()) {
                for (int i = 0; i < game.length; i++){
                    for (int j = 0; j < game.length; j++){
                        row += game[i][j] == 0 ? 0 : game[i][j] +",";
                    }
                    row += "\n";
                }
            }

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
