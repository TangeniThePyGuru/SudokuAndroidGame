package com.example.sudokuapp2;

public class Game
{
    private int[][] board;

    public Game()
    {
         //generate initial board
        Sudoku sudoku = new Sudoku();
        this.board = sudoku.generate();
    }

    public int[][] getBoard()
    {
        //return current board
        return this.board;
    }

    public boolean check(int value, int x, int y)
    {
        //check whether value can be placed at x, y
        int a, b, i, j;

        for (j = 0; j < 9; j++)
            if (j != y && board[x][j] == value)
                return false;

        for (i = 0; i < 9; i++)
            if (i != x && board[i][y] == value)
                return false;

        a = (x/3)*3; b = (y/3)*3;
        for (i = 0; i < 3; i++)
            for (j = 0; j < 3; j++)
                if ((a + i != x) && (b + j != y) && board[a+i][b+j] == value)
                    return false;

        return true;
    }

    // set a cell in a board
    public void set(int value, int x, int y)
    {
        //set value at x, y
        board[x][y] = value;
    }

    // set/update the board
    public void setBoard(int[][] board) {
        this.board = board;
    }
}
