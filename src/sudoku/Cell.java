package sudoku;

import javax.swing.*;
import java.awt.*;

// sudoku.Cell is the individual component for each grid cell

public class Cell extends JTextField {


    // final values for colors, the only things I will need to change are
    // TODO selected background color
    public static final Color FG_CLUE   = Color.BLACK;
    public static final Color FG_GUESS  = Color.white;
    // set background to null to see if that fixes the problem
    // TODO default font options
    public static final Font FONT_NUMBERS = new Font("News No 2", Font.PLAIN, 30);

    // the position for the cell
    int row;
    int col;

    // number existing within the cell
    int number;
    // 0 is blank

    //status from sudoku.Cell status
    CellStatus status;

    public Cell(int newRow, int newCol){
        super(); // run JTextField constructor
        this.row = newRow;
        this.col = newCol;


        // offsets are dealt with on the panel level so the cell itself doesn't have to deal with positioning

        // TODO setup fonts with their options
        super.setHorizontalAlignment(JTextField.CENTER); // center all answers
        super.setFont(FONT_NUMBERS);
    }

    // this will reset the board after you've filled in all the CLUE numbers and set their statuses
    public void newGame(int num, boolean isGiven){
        this.number = num;
        status = isGiven? CellStatus.CLUE : CellStatus.TO_GUESS;
        paint(); // paint itself after it is set
    }

    // This sudoku.Cell paints itself based on its status
    public void paint(){
        switch(status)
        {
            case CLUE:
                // set display properties for JTextField
                super.setText(number + "");
                super.setEditable(false);
                //set background/ fg colors
                super.setOpaque(false); //
                super.setForeground(FG_CLUE);
                break;
            case TO_GUESS:
                super.setText(""); // todo ********************* THIS MAY END UP CLEARING OUT ANSWERS IN THE FUTURE
                super.setEditable(true);
                super.setOpaque(false);
                super.setForeground(FG_GUESS);
                break;
            case CORRECT:
                break;
            case WRONG:
                // if it is set to wrong, don't signal anything, It will keep the colors the same as to_guess
                // the only difference is that the cell won't clear the text with .setText("")
                break;
        }
    }

}
