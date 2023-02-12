package sudoku;

public class Puzzle {

    // the raw numbers across the board
    int[][] numbers = new int[9][9];

    // slots where the puzzle is already filled out with clue numbers
    boolean[][] isGiven = new boolean [9][9];

    public Puzzle() {
        super();
    }

    public void newPuzzle(int cellsToGuess){

        // todo Generate a puzzle given the number of cells to be guessed, which controls difficulty

        // for now I will use this puzzle array to make sure everything works before implementing my wave collapse function

        int[][] premadeBoard =
                       {{5, 3, 4, 6, 7, 8, 9, 1, 2},
                        {6, 7, 2, 1, 9, 5, 3, 4, 8},
                        {1, 9, 8, 3, 4, 2, 5, 6, 7},
                        {8, 5, 9, 7, 6, 1, 4, 2, 3},
                        {4, 2, 6, 8, 5, 3, 7, 9, 1},
                        {7, 1, 3, 9, 2, 4, 8, 5, 6},
                        {9, 6, 1, 5, 3, 7, 2, 8, 4},
                        {2, 8, 7, 4, 1, 9, 6, 3, 5},
                        {3, 4, 5, 2, 8, 6, 1, 7, 9}};

        // once you have a board of filled out numbers, copy over the numbers into the array "numbers" to initalize the puzzle

        for (int row = 0; row < 9; row++)
        {
            for (int col = 0; col < 9; col++) {
                numbers[row][col] = premadeBoard[row][col];
            }

        }

        boolean[][] premadeClues =
                       {{true, true, true, true, true, false, true, true, true},
                        {true, true, true, true, true, true, true, true, false},
                        {true, true, true, true, true, true, true, true, true},
                        {true, true, true, true, true, true, true, true, true},
                        {true, true, true, true, true, true, true, true, true},
                        {true, true, true, true, true, true, true, true, true},
                        {true, true, true, true, true, true, true, true, true},
                        {true, true, true, true, true, true, true, true, true},
                        {true, true, true, true, true, true, true, true, true}};

        for (int row = 0; row < 9; row++)
        {
            for (int col = 0; col < 9; col++) {
                isGiven[row][col] = premadeClues[row][col];
            }

        }

    }



}
