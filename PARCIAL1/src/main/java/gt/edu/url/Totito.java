//Andres S. Gálvez A.
//Carné: 1024718
package gt.edu.url;
import gt.edu.url.implementation.Minimax;
import gt.edu.url.implementation.TTTBoard;

import java.util.Scanner;

public class Totito {

    private TTTBoard board = new TTTBoard();
    private Scanner scanner = new Scanner(System.in);
    private Integer getPlayerMove() {
        Integer playerMove = -1;
        while (!board.getLegalMoves().contains(playerMove)) {
            System.out.println("Ingrese un cuadrante valido (0-8):");
            Integer play = scanner.nextInt();
            playerMove = play;
        }
        return playerMove;
    }

    private void runGame() {
        // main game loop
        while (true) {
            Integer humanMove = getPlayerMove();
            board = board.move(humanMove);
            if (board.isWin()) {
                System.out.println("¡La humanidad gana!");
                break;
            } else if (board.isDraw()) {
                System.out.println("¡Empate!");
                break;
            }
            Integer computerMove = Minimax.findBestMove(board, 9);
            System.out.println("La computadora dice " + computerMove);
            board = board.move(computerMove);
            System.out.println(board);
            if (board.isWin()) {
                System.out.println("¡Las máquinas dominan!");
                break;
            } else if (board.isDraw()) {
                System.out.println("Empate");
                break;
            }
        }
    }

    public static void main(String[] args) {
        new Totito().runGame();
    }
}