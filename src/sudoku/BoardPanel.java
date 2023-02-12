package sudoku;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;


public class BoardPanel extends JPanel {
    public final static int GRID_SIZE = 65; // size of each grid cell dimension in pixels
    private final int size = GRID_SIZE*9; // 65*(9-1)
    private final int brushOffset = 2;
    private JLabel winLabel;

    private Cell[][] cells = new Cell[9][9];

    // puzzle contains the puzzle data (solution/which numbers are visible)
    private Puzzle puzzle = new Puzzle();

    BoardPanel() {

        super.setLayout(null);

        // Create each cell in the correct position.
        for (int row = 0; row < 9; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                cells[row][col] = new Cell(row, col);
                // to fit grid size nicely set size to 61
                // the coordinates for each jtextField is gridsize*n + 2
                 cells[row][col].setBounds((col * GRID_SIZE) + 3*brushOffset, (row * GRID_SIZE) + 3*brushOffset, GRID_SIZE - (4*brushOffset), GRID_SIZE - (4*brushOffset));
                super.add(cells[row][col]);
            }
        }



        setBackground(new Color(80 ,89,94));
        super.setPreferredSize(new Dimension(size+(3*brushOffset),size+(3*brushOffset)));
    }


    public void newGame(JLabel wg) {
        //create new puzzle
        puzzle.newPuzzle(2);
        winLabel = wg;
        for (int row = 0; row < 9; row++)
        {
            for(int col = 0; col < 9; col++)
            {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);


                // only setup input fields on cells that are not clues
                if(cells[row][col].status != CellStatus.CLUE)
                {
                    ((AbstractDocument) cells[row][col].getDocument()).setDocumentFilter(new SudokuFilter());
                }

                //System.out.println("Cells " + row + " " + col + ": " + cells[row][col].number);
                //System.out.println("puzzle number " + row + " " + col + ": " + puzzle.numbers[row][col]);
            }
        }
    }
    public boolean isSolved(){
        for(int row = 0; row < 9; row++)
        {
            for(int col = 0; col < 9; col++)
            {
                if(cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG) {
                    return false; // return if there are any unsolved/incorrect cells
                }
            }
        }
        return true;
    }
    public void update(){
        // todo find a way to only update the cell selected from the document filter
        // perhaps create a boolean "selected" in the cell class that is true?


        // this is a solution that I know will work, it is not ideal in the slightest though
        for(int row = 0; row < 9; row++)
        {
            for(int col = 0; col < 9; col++)
            {

                if(cells[row][col].isEditable())
                {
                    cellUpdate(cells[row][col]);
                }
            }
        }
    }
    public void cellUpdate(Cell source){

        String str = source.getText();

        // empty cell check
        if (str.isEmpty())
        {
            source.status = CellStatus.WRONG;
            return;
        }

        int numIn;

        try{
            // gather the integer that was input
            numIn = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe){
            // Document filter should not allow this error to occur
            // it is ignored for now
            return;
        }

        // update the status for the given cell
        if(numIn == source.number)
        {
            System.out.println("Updating cell with numIn of " + numIn + " and source.num " + source.number + "to Correct");
            source.status = CellStatus.CORRECT;
        }
        else{
            System.out.println("Updating cell with numIn of " + numIn + " and source.num " + source.number + "to Wrong");
            source.status = CellStatus.WRONG; // the cell status "WRONG" lets the game know that its neutral state contains a number (doesn't blank out wrong answers)
        }

        source.paint();

        // if the puzzle is solved, make the screen display the win label
        if (isSolved())
        {
            System.out.println("Updating cell with numIn of " + numIn + " and source.num " + source.number + "to final solution");
            //System.out.println("CONGRATS! You Solved the Puzzle");
            winLabel.setVisible(true);
        }

    }

    class SudokuFilter extends DocumentFilter{
        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            // replace is called frequently while typing input into the boxes this is primarily where work gets done
            if(offset >= fb.getDocument().getLength()){
                if (fb.getDocument().getLength() == 1)
                {
                    return; // only single digit inserts allowed
                }

                int numIn;
                try{
                    // gather the integer that was input
                    numIn = Integer.parseInt(text);
                }
                catch(NumberFormatException nfe){
                    // if the single character input is not an integer ignore it
                    return;
                }
                if (numIn == 0) // only digits 1-9 allowed
                {
                    return;
                }
                System.out.println("replace Added: " + text);
            }
            else{
                String old = fb.getDocument().getText(offset, length);
                System.out.println("Replaced " + old + " with " + text);
            }
            super.replace(fb,offset,length,text,attrs);
            update();
        }

        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
            // if the character is a digit
            int numIn;
            try{
                // gather the integer that was input
                numIn = Integer.parseInt(text);
            }
            catch(NumberFormatException nfe){
                // if the single character input is not an integer ignore it
                return;
            }
            System.out.println("instert Added: " + text);
            super.insertString(fb, offset, text, attr);
        }

        @Override
        public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
            System.out.println("Removed: " + fb.getDocument().getText(offset, length));
            super.remove(fb, offset, length);
        }
    }


    // focus listener to enter text when focus is lost (avoid hitting enter every time)
    // private inner class to attach to all editable cells
    private class CellInputListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            // get a reference of the JTextField that triggers the action event
            Cell sourceCell = (Cell)e.getSource();

            cellUpdate(sourceCell);
        }
    }
    private class CellFocusListener implements FocusListener {

        public void focusGained(FocusEvent e)
        {

        }
        @Override
        public void focusLost(FocusEvent e) {
            Cell sourceCell = (Cell)e.getSource();
            cellUpdate(sourceCell);
        }
    }



    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g; // child class of Graphics that has more options available, so we cast it

        g2d.setPaint(Color.black);

        // Sudoku grid lines for a 9x9 game
        for(int i = 0; i <= 9; i++)
        {
            if(i%3 == 0){
                g2d.setStroke(new BasicStroke(5));
                g2d.drawLine((i*65)+brushOffset, 0+brushOffset, (i*65)+brushOffset, size+brushOffset);
                g2d.drawLine(0+brushOffset,(i*65)+brushOffset, size+brushOffset,(i*65)+brushOffset);
            }
            else{
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine((i*65)+brushOffset, 0+brushOffset, (i*65)+brushOffset, size+brushOffset);
                g2d.drawLine(0+brushOffset,(i*65)+brushOffset, size+brushOffset,(i*65)+brushOffset);
            }
        }

    }
}
