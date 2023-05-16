package mines;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.swing.*;

import static org.junit.Assert.*;

public class BoardTest {

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

//    @Test
//    public void initializeCellStartTest() {
//        JLabel statusbar = new JLabel("");
//        Board board = new Board(statusbar);
//        board.initializeCell(3, false);
//        assertTrue(true);
//    }
//

    @Test
    public void initializeCellStartTest() {

        try {
            JLabel statusbar = new JLabel("");
            Board board = new Board(statusbar);
            board.newGame();

            // Access private field
            Field privateField = Board.class.getDeclaredField("field");
            privateField.setAccessible(true);
            int[] fieldValue = (int[]) privateField.get(board);

            int testCell = 10;
            final int oldVal = 3;
            fieldValue[testCell] = oldVal;

            // Invoke private method
            Method privateMethod = Board.class.getDeclaredMethod("initializeCell", int.class, boolean.class);
            privateMethod.setAccessible(true);
            privateMethod.invoke(board, testCell, false);

            assertTrue(fieldValue[testCell] == oldVal + 1);
        } catch (Exception e) {

            assertTrue(false);
        }


    }

    public void initializeCellOuterOuTest() {

        try {
            JLabel statusbar = new JLabel("");
            Board board = new Board(statusbar);
            board.newGame();

            // Access private field
            Field privateField = Board.class.getDeclaredField("field");
            privateField.setAccessible(true);
            int[] fieldValue = (int[]) privateField.get(board);

            int testCell = 10;
            final int oldVal = 3;
            fieldValue[testCell] = oldVal;

            // Invoke private method
            Method privateMethod = Board.class.getDeclaredMethod("initializeCell", int.class, boolean.class);
            privateMethod.setAccessible(true);
            privateMethod.invoke(board, testCell, true);

            assertTrue(fieldValue[testCell] == oldVal + 1);
        } catch (Exception e) {

            assertTrue(false);
        }


    }

    @Test
    public void findCellStartTest() {

        try {
            JLabel statusbar = new JLabel("");
            Board board = new Board(statusbar);
            board.newGame();

            // Access private field
            Field privateField = Board.class.getDeclaredField("field");
            privateField.setAccessible(true);
            int[] fieldValue = (int[]) privateField.get(board);

            int testCell = 10;

            fieldValue[testCell] = MINE_CELL + 1;

            // Invoke private method
            Method privateMethod = Board.class.getDeclaredMethod("findCell", int.class, boolean.class);
            privateMethod.setAccessible(true);
            privateMethod.invoke(board, testCell, false);

            assertTrue(fieldValue[testCell] == EMPTY_CELL);
        } catch (Exception e) {

            assertTrue(false);
        }


    }

    @Test
    public void findCellOuterTest() {
        try {
            JLabel statusbar = new JLabel("");
            Board board = new Board(statusbar);
            board.newGame();

            // Access private field
            Field privateField = Board.class.getDeclaredField("field");
            privateField.setAccessible(true);
            int[] fieldValue = (int[]) privateField.get(board);

            int testCell = 3;

            fieldValue[testCell] = MINE_CELL + 1;

            // Invoke private method
            Method privateMethod = Board.class.getDeclaredMethod("findCell", int.class, boolean.class);
            privateMethod.setAccessible(true);
            privateMethod.invoke(board, testCell, true);

            assertTrue(fieldValue[testCell] == EMPTY_CELL);
        } catch (Exception e) {

            assertTrue(false);
        }


    }

//    @Test
//    public void findCellOuterTest() {
//        JLabel statusbar = new JLabel("");
//        Board board = new Board(statusbar);
//        board.findCell(3, true);
//        assertTrue(true);
//    }
}