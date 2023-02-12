package sudoku;

public class Main {
    public static void main(String[] args) {

        // The main game loop begins in MyFrame and continues as such
        // the only  thing I would include here is the possiblity to import a sudoku puzzle from another file
        new MyFrame();

    }
}


// TODO actually add the new game button

/* TODO attempt to generate a sodoku game with the wave collapse function
    - Each cell contains an attribute known as a quantum state which is a bool[9]
        - the booleans are states they could possibly be, to start each cell has its QState set to true for each item
    - as numbers are filled in, a QUpdate is called
        - QUpdate updates the QState array to remove numbers that exist in the same row/column/box
            - Each cell contains an int entropy value starting at 9
            - Each time QUpdate is called, if a number is removed from a slot, its entropy drops.
                - Entropy values correspond with the number of true values existing in the QState
    - numbers are chosen to be filled in by doing the following:
        - find all cell/s with the lowest entropy value
            - if more than one cell exists in this set, choose one at random
        - once a cell is chosen, choose a QState slot that is true at random
            - once the slot is chosen, update the value of the cell with the QState's index+1 (to be 1-9 instead of 0-8)
        - after updating that number, call QUpdate to update every cell's QState and their entropies
    - End this loop as soon as every value is filled in
    ==> Use this to create a puzzle from scratch or to fill out the rest of a prefilled puzzle

// TODO use the same method with wave collapse in order to create a button to solve it as well
        ==> create a game state to prefill a puzzle and click to solve
// TODO allow user to save the puzzle to try to solve it later


*/







