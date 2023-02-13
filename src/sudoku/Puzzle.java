package sudoku;

import java.util.HashSet;
import java.util.Set;

public class Puzzle {

    // the raw numbers across the board
    int[][] numbers = new int[9][9];

    // slots where the puzzle is already filled out with clue numbers
    boolean[][] isGiven = new boolean[9][9];

    public Puzzle() {
        super();
    }

    public void newPuzzle(int cellsToGuess) {

        // todo Generate a puzzle given the number of cells to be guessed, which controls difficulty


        // for now I will use this puzzle array to make sure everything works before implementing my wave collapse function
        // *** as it stands, I have a hardcoded puzzle that reports back the board of numbers and the cells take over from there


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

        //TODO this is testing code for puzzle validation
        if (!puzzleVerify(premadeBoard))
        {
            System.out.println("The puzzle failed validation!");
        }

        for (int row = 0; row < 9; row++) {
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

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                isGiven[row][col] = premadeClues[row][col];
            }

        }

    }

    private boolean puzzleVerify(int nums [][]) {
        // This function simply takes in an int [9][9] and verifies if the numbers follow the rules of sudoku


        for (int i = 0; i < 9; i++) {
            Set<Integer> col = new HashSet<>();
            Set<Integer> row = new HashSet<>();
            for(int j =0; j < 9; j++)
            {

                // check columns
                if(!col.add(nums[j][i]))
                {   // add will return false if the number already exists within the set
                    // therefore, if I put each number in a given column or row into the set,
                        // the first element to break the sudoku rule will invalidate the puzzle
                    return false;
                }

                // check rows
                if(!row.add(nums[i][j]))
                { // this does the same as above, but with the i and j elements flipped
                    return false;
                }
            }

        }


        // check each box (the 3x3's)
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j< 3; j++)
            {
                int rowIndex = j*3;
                int colIndex = i*3;
                // these are the coordinates for the top left element of each box
                Set<Integer> box = new HashSet<>();
                for (int boxCol = 0; boxCol < 3; boxCol++)
                    for (int boxRow = 0; boxRow < 3; boxRow++)
                    {
                        if(!box.add(nums[colIndex+boxCol][rowIndex+boxRow]))
                        {
                            return false;
                        }
                    }
            }
        }

        return true;
    }



}
