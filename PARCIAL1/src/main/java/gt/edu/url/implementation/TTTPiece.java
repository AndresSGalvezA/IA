package gt.edu.url.implementation;

import gt.edu.url.adapter.Piece;

public enum TTTPiece implements Piece {
    X, O, E; // E is Empty

    @Override
    public Piece opposite() {
        switch (this) {
            case X:
                return TTTPiece.O;
            case O:
                return TTTPiece.X;
            default: // E, empty
                return TTTPiece.E;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case X:
                return "X";
            case O:
                return "O";
            default: // E, empty
                return " ";
        }
    }
}
