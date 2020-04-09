package Model;

import java.util.Map;

public class Reversi extends Game {
    public Reversi(int rows, int columns){
        super(rows, columns);
        generateGameBoard();
    }

    @Override
    public String think(Map<String, String> gameBoard) {
        return null;
    }

    @Override
    public int minimax(Map<String, String> gameBoard, int steps, boolean isMaximizing) {
        return 0;
    }

    @Override
    public int openSpots(Map<String, String> gameBoard) {
        return 0;
    }

    @Override
    public String checkWinner(Map<String, String> gameBoard) {
        return null;
    }
}
