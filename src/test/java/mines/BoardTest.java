package mines;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.swing.*;

import static org.junit.Assert.*;

public class BoardTest {


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

            assertEquals(oldVal + 1, fieldValue[testCell]);
        } catch (Exception e) {

            fail();
        }


    }


    @Test
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

            assertEquals(fieldValue[testCell], oldVal + 1);
        } catch (Exception e) {

            fail();
        }


    }

    @Test
    public void findCellStartTest() {

        try {
            JLabel statusbar = new JLabel("");
            Board board = new Board(statusbar);
            board.newGame();

            Field mineCellField = Board.class.getDeclaredField("MINE_CELL");
            mineCellField.setAccessible(true);
            final int MINE_CELL = (int) mineCellField.get(board);

            Field emptyCellField = Board.class.getDeclaredField("EMPTY_CELL");
            emptyCellField.setAccessible(true);
            final int EMPTY_CELL = (int) emptyCellField.get(board);

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

            assertEquals(EMPTY_CELL, fieldValue[testCell]);
        } catch (Exception e) {

            fail();
        }


    }

    @Test
    public void findCellOuterTest() {
        try {


            JLabel statusbar = new JLabel("");
            Board board = new Board(statusbar);
            board.newGame();

            Field mineCellField = Board.class.getDeclaredField("MINE_CELL");
            mineCellField.setAccessible(true);
            final int MINE_CELL = (int) mineCellField.get(board);

            Field emptyCellField = Board.class.getDeclaredField("EMPTY_CELL");
            emptyCellField.setAccessible(true);
            final int EMPTY_CELL = (int) emptyCellField.get(board);

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

            assertEquals(EMPTY_CELL, fieldValue[testCell]);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }


    }


    @Test
    public void checkCellTest() {
        try {
            JLabel statusbar = new JLabel("");
            Board board = new Board(statusbar);
            board.newGame();

            Board.MinesAdapter adapter = board.new MinesAdapter();
            int row = 3;
            int col = 0;


            // Access private field
            Field privateField = Board.class.getDeclaredField("field");
            privateField.setAccessible(true);
            int[] fieldValue = (int[]) privateField.get(board);

            Method markCell = Board.MinesAdapter.class.getDeclaredMethod("markCell", int.class, int.class);
            markCell.setAccessible(true);

            markCell.invoke(adapter, row, col);


            Method checkCell = Board.MinesAdapter.class.getDeclaredMethod("checkCell", int.class, int.class);
            checkCell.setAccessible(true);

            boolean result = (boolean) checkCell.invoke(adapter, row, col);

            assertFalse(result);


            row = 4;


            result = (boolean) checkCell.invoke(adapter, row, col);


            assertTrue(result);


        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }


    }

    @Test
    public void markCellTest() {
        try {
            JLabel statusbar = new JLabel("");
            Board board = new Board(statusbar);
            board.newGame();

            Board.MinesAdapter adapter = board.new MinesAdapter();
            int row = 3;
            int col = 0;

            Field markForCellField = Board.class.getDeclaredField("MARK_FOR_CELL");
            markForCellField.setAccessible(true);
            final int MARK_FOR_CELL = (int) markForCellField.get(board);


            Method markCell = Board.MinesAdapter.class.getDeclaredMethod("markCell", int.class, int.class);
            markCell.setAccessible(true);

            markCell.invoke(adapter, row, col);


            Field colField = Board.class.getDeclaredField("cols");
            colField.setAccessible(true);
            int colsValue = (int) colField.get(board);


            // Access private field
            Field privateField = Board.class.getDeclaredField("field");
            privateField.setAccessible(true);
            int[] fieldValue = (int[]) privateField.get(board);

            assertTrue(fieldValue[(row * colsValue) + col] >= MARK_FOR_CELL);


        } catch (Exception e) {

            fail();
        }


    }


}