package sudoku;

import javax.swing.*;
import java.awt.*;
//import java.swing.*;

public class MyFrame extends JFrame {

    MyPanel sidePanel1;
    MyPanel sidePanel2;

    MyPanel titlePanel;
    MyPanel botPanel;

    BoardPanel boardPanel;


    JButton jbNewGame;

    MyFrame(){


        this.setTitle("Alec's Sodoku");
        this.setResizable(false);
        this.setSize(1000,1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // click the x to exit
        this.setLocationRelativeTo(null); // this centers the window to the middle of the screen instead of top left
        this.setVisible(true); // I assume this is so you can set next frames or overlays to display on top of each other

        // set up the inner panel for the game board
        sidePanel1  = new MyPanel();
        sidePanel2  = new MyPanel();
        boardPanel  = new BoardPanel();
        titlePanel  = new MyPanel();
        botPanel    = new MyPanel();

        JLabel winLabel = new JLabel("Puzzle Solved!");
        winLabel.setBounds(0,0, 250,250);
        winLabel.setForeground(new Color(0x00FF00));
        winLabel.setFont(new Font("MV Boli", Font.PLAIN, 32));
        titlePanel.add(winLabel);
        winLabel.setVisible(false);


        this.add(boardPanel, BorderLayout.CENTER);
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(botPanel, BorderLayout.SOUTH);
        this.add(sidePanel1, BorderLayout.EAST);
        this.add(sidePanel2, BorderLayout.WEST);



        //TODO setup the JBNewGame button to setup a new game on the south panel
        boardPanel.newGame(winLabel);


        this.pack(); // essentially compresses the frame so the memory size is at or smaller than what it is expecting



    }


}
