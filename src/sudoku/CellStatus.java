package sudoku;

public enum CellStatus {
    CLUE,
    TO_GUESS,
    CORRECT,
    WRONG
    // clue status: default given numbers
    // to_guess:    empty values
    // correct:     guesses that are correct
    // wrong:       guesses that are wrong
    // game condition: if there are no cells with to guess or wrong, the game ends
}
