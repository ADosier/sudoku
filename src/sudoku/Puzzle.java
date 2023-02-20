package sudoku;

import java.util.*;

/* Todo the puzzle class takes care of wave collapse functionality and needs to work with the following
        - numbers (the solved numbers)
        - Qstate & entropy (either separate bool[9] + number of truths
        - isGiven
    Functions needed:
        - random(a, b) to generate a number between and including a and b to randomly choose how the wave function collapses
        - wave collapse () to house all the operations needed to generate the puzzle from scratch
        - qupdate() when a cell collapses into a specific number, the other cells must update their quantum states and entropy
*
*
* */


public class Puzzle {

    public class PCell{
        int num;
        private boolean [] Qstate = new boolean [9];
        private int entropy;
        private int row;
        private int col;
        PCell(int r, int c)
        {
            Arrays.fill(Qstate, true); // fill array with true
            entropy = 9;
            num = 0; // default to 0
            row = r;
            col = c;
        }

        public void updateQState(int setNum)
        {
            // the possibility of setNum is set to false on Qstate
            // if the Qstate was changed from true to false, reduce the entropy
            boolean currentState = Qstate[setNum-1];
            if (currentState) // change entropy if state == true
            {
                Qstate[setNum-1] = false;
                entropy--;
            }

        }
        public boolean [] getQstate(){
            return Qstate;
        }
        public int getEntropy(){
            return entropy;
        }
        public boolean getQstate(int index)
        {
            if (index < 0)
            {
                return false;
            }
            if(index > 8)
            {
                return false;
            }
            return Qstate[index];
        }
        public boolean collapseSolution(int sol)
        {
            if(sol == 0)
            {
                return false;
            }
            num = sol;
            entropy = 0;
            Arrays.fill(Qstate, false);
            return true;
        }

        public void printQState(){
            System.out.print("{");
            for (int i = 0; i < 8; i++)
            {
                if(Qstate[i] == true)
                {
                    System.out.print("True, ");
                }
                else
                {
                    System.out.print("False, ");
                }
            }
            if(Qstate[8] == true)
            {
                System.out.println("True }");
            }
            else{
                System.out.println("False }");
            }
        }
    }

    Random random = new Random();
    PCell[][] board = new PCell[9][9]; // the PCells are just pre-cells used in the wave collapse function prior to returning a puzzle to the game

    // the raw numbers across the board
    int[][] numbers = new int[9][9];

    // slots where the puzzle is already filled out with clue numbers
    boolean[][] isGiven = new boolean[9][9];

    public Puzzle() {
        super();
    }

    public void newPuzzle(int cellsToGuess) {

        // fill the board out with actual objects
        for (int i =0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                board[i][j] = new PCell(i, j);
            }
        }

        // wave collapse until all values are set it will populate a board with numbers that follow the convensions of sudoku
        while(waveCollapse());


        //verify the puzzle follows sudoku rules. this is primarily for future puzzles that users may enter
        if (!puzzleVerify())
        {
            System.out.println("The puzzle failed validation!");
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                numbers[row][col] = board[row][col].num;
            }

        }

        // create a list that has 82 booleans where cellsToGuess is the number of false entries
        ArrayList<Boolean> clues = new ArrayList<Boolean>();
        for (int i = 0; i < 81; i++) // 81 is 9x9
        {
            if (i < cellsToGuess)
            {
                clues.add(false);
            }
            else
            {
                clues.add(true);
            }
        }

        Collections.shuffle(clues); // shuffle the false entries throughout the puzzle

        // copy over this array to isGiven so the boardPuzzle can render out the puzzle easier
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                isGiven[row][col] = clues.get(col+(row*9));// col+(row*9) is a conversion between a long list and the [row][col] format in isGiven
            }
        }
    }

    private boolean puzzleVerify() {
        // This function simply takes in an int [9][9] and verifies if the numbers follow the rules of sudoku


        for (int i = 0; i < 9; i++) {
            Set<Integer> col = new HashSet<>();
            Set<Integer> row = new HashSet<>();
            for(int j =0; j < 9; j++)
            {

                // check columns
                if(!col.add(board[j][i].num))
                {   // add will return false if the number already exists within the set
                    // therefore, if I put each number in a given column or row into the set,
                        // the first element to break the sudoku rule will invalidate the puzzle
                    return false;
                }

                // check rows
                if(!row.add(board[j][i].num))
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
                        if(!box.add(board[colIndex+boxCol][rowIndex+boxRow].num))
                        {
                            return false;
                        }
                    }
            }
        }

        return true;
    }
    private boolean waveCollapse()
    {

        // find a list of lowest entropies that aren't 0
        ArrayList<PCell> uncollapsedList = new ArrayList<PCell>();
        int lowestEntropy = 9;
        // if lentropycell is empty, return false
        // sort the cells from lowest to highest entropy
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if(board[i][j].getEntropy() != 0)
                {
                    uncollapsedList.add(board[i][j]);
                    if (board[i][j].getEntropy() < lowestEntropy)
                    {
                        lowestEntropy = board[i][j].getEntropy();
                    }
                }
            }
        }
        if(uncollapsedList.isEmpty())
        {
            // if all values for entropy are 0, return false. the numbers have collapsed into a solution
            return false;
        }

        // sort the cells in order of entropy, lowest to highest
        uncollapsedList.sort(Comparator.comparing((PCell p) -> p.getEntropy()));

        ArrayList<PCell> lEntropyList = new ArrayList<PCell>();

        for(int i =0; i < uncollapsedList.size(); i++)
        {
            if (uncollapsedList.get(i).getEntropy() > lowestEntropy)
            {
                break; // get out of loop. all lowest entropy pCells already copied over
            }
            lEntropyList.add(uncollapsedList.get(i));
        }

        //the list of all lowest entropies should exist within lentropyList
        // test this to make sure it works

        // choose one at random


        // random PCell chosen from the list of least entropy
        PCell target = lEntropyList.get(random(0, lEntropyList.size()-1));

        ArrayList<Integer> possibleNumbers = new ArrayList<Integer>();
        for (int i =0; i < 9; i ++)
        {
            if (target.getQstate(i))
            {
                possibleNumbers.add(i+1); // index corresponds to the number n+1 in qstate
            }
        }

        if(possibleNumbers.isEmpty())
        {
            System.out.println("the program tried to collapse a solved pCell");
        }


        //lEntropyList.get(random(0, lEntropyList.size()-1));
        int solution = possibleNumbers.get(random(0, possibleNumbers.size()-1));

        if(board[target.row][target.col].collapseSolution(solution))
        {
            propagateUpdates(board[target.row][target.col]);
        }

        return true;
    }
    private void propagateUpdates(PCell target)
    {
        // propagate updates to every element in the same row/col/room
        for (int i =0; i < 9; i++)
        {
            // update all elements in the same row
            board[i][target.col].updateQState(target.num);

            // update all elements in the same column
            board[target.row][i].updateQState(target.num);

        }


        // round down the row/column numbers to match a number divisible by 3
        // this calculates the origin point of the "room (3x3 grid)" the target is located in
        int rowOrigin = target.row/3;
        int colOrigin = target.col/3;

        for(int c = 0; c < 3; c++)
        {
            for(int r = 0; r < 3; r++)
            {
                // from the room origin's row/col, itterate through each pCell and propagate changes
                board[(rowOrigin*3)+r][(colOrigin*3)+c].updateQState(target.num);
            }
        }




    }
    public int random(int min, int max){
        // generates a random number between min and max and including them.
        return random.nextInt(max - min + 1) + min;
    }


}
