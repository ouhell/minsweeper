package mines;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class Mines extends JFrame {
    private static final long serialVersionUID = 4772165125287256837L;


    // set up window dimensions
    private static final int WINDOW_WIDTH = 250;
    private static final int WINDOW_HEIGHT = 290;

    private JLabel statusbar;

    public Mines() {

        // set the window to be closable and apply the dimensions and title
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Minesweeper");

        statusbar = new JLabel("");
        add(statusbar, BorderLayout.SOUTH);

        add(new Board(statusbar));

        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Mines();
    }
}