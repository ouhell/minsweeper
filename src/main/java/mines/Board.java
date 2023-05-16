package mines;


import java.awt.Graphics;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Board extends JPanel {
    private static final long serialVersionUID = 6195235521361212179L;

    private static final int NUM_IMAGES = 13;
    private static final int CELL_SIZE = 15;

    private static final int COVER_FOR_CELL = 10;
    private static final int MARK_FOR_CELL = 10;
    private static final int EMPTY_CELL = 0;
    private static final int MINE_CELL = 9;
    private static final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    private static final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

    private static final int DRAW_MINE = 9;
    private static final int DRAW_COVER = 10;
    private static final int DRAW_MARK = 11;
    private static final int DRAW_WRONG_MARK = 12;

    private int[] field;
    private boolean inGame;
    private int minesLeft;
    private Image[] img;
    private int mines = 40;
    private int rows = 16;
    private int cols = 16;
    private int allCells;
    private JLabel statusbar;


    public Board(JLabel statusbar) {

        this.statusbar = statusbar;


        img = new Image[NUM_IMAGES];
        for (int i = 0; i < NUM_IMAGES; i++) {
            String path = getClass().getResource("/images/" + i
                    + ".gif").getPath();
            img[i] =
                    (new ImageIcon(path)).getImage();
        }

        setDoubleBuffered(true);

        addMouseListener(new MinesAdapter());
        try {
            newGame();
        } catch (Exception e) {
            System.exit(-1);
        }

    }


    public void newGame() throws NoSuchAlgorithmException {

        SecureRandom random;
        int currentCol;

        int i = 0;
        int position = 0;


        random = SecureRandom.getInstanceStrong();
        inGame = true;
        minesLeft = mines;

        allCells = rows * cols;
        field = new int[allCells];

        for (i = 0; i < allCells; i++)
            field[i] = COVER_FOR_CELL;

        statusbar.setText(Integer.toString(minesLeft));


        i = 0;
        while (i < mines) {

            position = (int) (allCells * random.nextDouble());


            if ((position >= allCells) ||
                    (field[position] == COVERED_MINE_CELL)) continue;


            currentCol = position % cols;
            field[position] = COVERED_MINE_CELL;
            i++;

            if (currentCol > 0) {

                initializeCell(position - 1 - cols, false);


                initializeCell(position - 1, false);


                initializeCell(position + cols - 1, true);


            }


            initializeCell(position - cols, false);


            initializeCell(position + cols, true);


            if (currentCol < (cols - 1)) {

                initializeCell(position - cols + 1, false);


                initializeCell(position + cols + 1, true);


                initializeCell(position + 1, true);


            }

        }
    }

    // method that construct a cell given its number
    private void initializeCell(int cell, boolean checkAllCells) {
        boolean cellChecker = checkAllCells ? cell < allCells : cell >= 0;
        if (cellChecker && field[cell] != COVERED_MINE_CELL) {
            field[cell] += 1;
        }
    }


    public void findEmptyCells(int j) {

        int currentCol = j % cols;


        if (currentCol > 0) {

            findCell(j - cols - 1, false);


            findCell(j - 1, false);


            findCell(j + cols - 1, true);

        }


        findCell(j - cols, false);


        findCell(j + cols, true);


        if (currentCol < (cols - 1)) {

            findCell(j - cols + 1, false);


            findCell(j + cols + 1, true);


            findCell(j + 1, true);

        }

    }

    private void findCell(int cell, boolean checkAllCells) {

        final boolean cellChecker = checkAllCells ? cell < allCells : cell >= 0;

        if (cellChecker && field[cell] > MINE_CELL) {
            field[cell] -= COVER_FOR_CELL;
            if (field[cell] == EMPTY_CELL)
                findEmptyCells(cell);
        }


    }

    // override the Jpanel pain method to display our ui
    @Override
    public void paint(Graphics g) {

        int cell = 0;
        int uncover = 0;


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                cell = field[(i * cols) + j];

                if (inGame && cell == MINE_CELL)
                    inGame = false;

                if (!inGame) {

                    cell = selectCellType(cell);


                } else if (cell > COVERED_MINE_CELL) {
                    cell = DRAW_MARK;
                } else if (cell > MINE_CELL) {
                    cell = DRAW_COVER;
                    uncover++;
                }


                g.drawImage(img[cell], (j * CELL_SIZE),
                        (i * CELL_SIZE), this);
            }
        }


        if (uncover == 0 && inGame) {
            inGame = false;
            statusbar.setText("Game won");
        } else if (!inGame)
            statusbar.setText("Game lost");
    }

    private int selectCellType(int cell) {
        if (cell == COVERED_MINE_CELL) {
            cell = DRAW_MINE;
        } else if (cell == MARKED_MINE_CELL) {
            cell = DRAW_MARK;
        } else if (cell > COVERED_MINE_CELL) {
            cell = DRAW_WRONG_MARK;
        } else if (cell > MINE_CELL) {
            cell = DRAW_COVER;
        }
        return cell;
    }


    public class MinesAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;

            boolean rep = false;


            if (!inGame) {

                try {
                    newGame();
                } catch (Exception ex) {
                    System.exit(-1);
                }
                repaint();
            }


            if ((x >= cols * CELL_SIZE) || (y >= rows * CELL_SIZE)) return;


            if (e.getButton() == MouseEvent.BUTTON3 && field[(cRow * cols) + cCol] > MINE_CELL) {


                rep = true;

                markCell(cRow, cCol);


            } else {
                rep = checkCell(cRow, cCol);
            }

            if (rep)
                repaint();


        }


        // flag a cell to disarm a mine
        private void markCell(int cRow, int cCol) {
            // if the cell is not marked
            if (field[(cRow * cols) + cCol] <= COVERED_MINE_CELL) {
                // if there are mines left
                if (minesLeft > 0) {
                    field[(cRow * cols) + cCol] += MARK_FOR_CELL;
                    minesLeft--;
                    statusbar.setText(Integer.toString(minesLeft));
                } else
                    statusbar.setText("No marks left");
            }
            // if the cell is already marked unmark it
            else {

                field[(cRow * cols) + cCol] -= MARK_FOR_CELL;
                minesLeft++;
                statusbar.setText(Integer.toString(minesLeft));
            }
        }

        // check the cell if there is a bomb
        private boolean checkCell(int cRow, int cCol) {
            // if cell is marked the cell can't be checked
            if (field[(cRow * cols) + cCol] > COVERED_MINE_CELL) {
                return false;
            }
            // cell is unmarked
            if ((field[(cRow * cols) + cCol] > MINE_CELL) &&
                    (field[(cRow * cols) + cCol] < MARKED_MINE_CELL)) {

                field[(cRow * cols) + cCol] -= COVER_FOR_CELL;


                if (field[(cRow * cols) + cCol] == MINE_CELL)
                    inGame = false;
                if (field[(cRow * cols) + cCol] == EMPTY_CELL)
                    findEmptyCells((cRow * cols) + cCol);
            }
            return true;
        }
    }
}