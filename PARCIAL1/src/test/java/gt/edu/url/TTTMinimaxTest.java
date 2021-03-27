package gt.edu.url;

import gt.edu.url.implementation.Minimax;
import gt.edu.url.implementation.TTTBoard;
import gt.edu.url.implementation.TTTPiece;
import org.junit.Test;
import static org.junit.Assert.*;

public class TTTMinimaxTest {

    @Test
    public void boardWorks() {
        //Available moves are 0,1,2 and 8
        TTTPiece[] basicBoardPieces = new TTTPiece[] {
                TTTPiece.E, TTTPiece.O, TTTPiece.E,
                TTTPiece.X, TTTPiece.X, TTTPiece.X,
                TTTPiece.O, TTTPiece.X, TTTPiece.E };
        TTTBoard basicBoard = new TTTBoard(basicBoardPieces, TTTPiece.X);
        assertNotNull(basicBoard);
    }

    @Test
    public void availableMoves() {
        //Available moves are 0,1,2 and 8
        TTTPiece[] availableMoves = new TTTPiece[] {
                TTTPiece.E, TTTPiece.E, TTTPiece.E,
                TTTPiece.X, TTTPiece.X, TTTPiece.X,
                TTTPiece.O, TTTPiece.X, TTTPiece.E };
        TTTBoard availabilityBoard = new TTTBoard(availableMoves, TTTPiece.X);
        assertEquals(availabilityBoard.getLegalMoves().size(), 4);
    }

    @Test
    public void isWin() {
        TTTPiece[] noWinBoard = new TTTPiece[] {
                TTTPiece.E, TTTPiece.E, TTTPiece.E,
                TTTPiece.X, TTTPiece.X, TTTPiece.E,
                TTTPiece.O, TTTPiece.X, TTTPiece.O };
        TTTBoard testBoard4 = new TTTBoard(noWinBoard, TTTPiece.X);
        assertEquals(testBoard4.isWin(), false);

        TTTPiece[] winBoard = new TTTPiece[] {
                TTTPiece.E, TTTPiece.E, TTTPiece.E,
                TTTPiece.X, TTTPiece.X, TTTPiece.X,
                TTTPiece.O, TTTPiece.X, TTTPiece.O };
        TTTBoard testBoard5 = new TTTBoard(winBoard, TTTPiece.X);
        assertEquals(testBoard5.isWin(), true);
    }

    @Test
    public void evaluate() {
        TTTPiece[] evaluatePlayer = new TTTPiece[] {
                TTTPiece.X, TTTPiece.X, TTTPiece.X,
                TTTPiece.E, TTTPiece.X, TTTPiece.E,
                TTTPiece.E, TTTPiece.O, TTTPiece.O };
        TTTBoard testBoard1 = new TTTBoard(evaluatePlayer, TTTPiece.X);
        var value = testBoard1.evaluate(TTTPiece.O);
    }

    @Test
    public void easyPosition() {
        TTTPiece[] toWinEasyPosition = new TTTPiece[] {
                TTTPiece.X, TTTPiece.O, TTTPiece.X,
                TTTPiece.X, TTTPiece.E, TTTPiece.O,
                TTTPiece.E, TTTPiece.E, TTTPiece.O };
        TTTBoard testBoard1 = new TTTBoard(toWinEasyPosition, TTTPiece.X);
        Integer answer1 = Minimax.findBestMove(testBoard1, 8);
        assertEquals(answer1.intValue(), 6);
    }

    @Test
    public void blockPosition() {
        TTTPiece[] toBlockPosition = new TTTPiece[] {
                TTTPiece.X, TTTPiece.E, TTTPiece.E,
                TTTPiece.E, TTTPiece.E, TTTPiece.O,
                TTTPiece.E, TTTPiece.X, TTTPiece.O };
        TTTBoard testBoard2 = new TTTBoard(toBlockPosition, TTTPiece.X);
        Integer answer2 = Minimax.findBestMove(testBoard2, 8);
        assertEquals(answer2.intValue(), 2);
    }

    @Test
    public void hardPosition() {
        TTTPiece[] toWinHardPosition = new TTTPiece[] {
                TTTPiece.X, TTTPiece.E, TTTPiece.E,
                TTTPiece.E, TTTPiece.E, TTTPiece.O,
                TTTPiece.O, TTTPiece.X, TTTPiece.E };
        TTTBoard testBoard3 = new TTTBoard(toWinHardPosition, TTTPiece.X);
        Integer answer3 = Minimax.findBestMove(testBoard3, 8);
        assertEquals(answer3.intValue(), 1);
    }
}
