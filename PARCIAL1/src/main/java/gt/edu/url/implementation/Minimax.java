package gt.edu.url.implementation;
import gt.edu.url.adapter.Board;
import gt.edu.url.adapter.Piece;

import java.util.Random;

public class Minimax {
    // Encuentra la mejor utilidad para el jugador originalPlayer
    public static <Move> double minimax(Board<Move> board, boolean maximizing, Piece originalPlayer, int maxDepth) {
        // TODO implement minimax
        return new Random().nextInt(1);
    }

    public static <Move> Move findBestMove(Board<Move> board, int maxDepth) {
        double bestEval = Double.NEGATIVE_INFINITY;
        Move bestMove = null; // won't stay null for sure
        for (Move move : board.getLegalMoves()) {
            double result = minimax(board.move(move), false, board.getTurn(),
                    maxDepth);
            if (result > bestEval) {
                bestEval = result;
                bestMove = move;
            }
        }
        return bestMove;
    }
}
