package Model;

import Controller.ViewController;

import java.util.Map;

public class Reversi extends Game {
    public Reversi(int rows, int columns, String playerOne, ViewController controller){
        super(rows, columns, playerOne, controller);
        setGame(this);
        generateGameBoard();
    }

    @Override
    public Integer think() {
        return null;
    }

    @Override
    public int minimax(int steps, boolean isMaximizing) {
        return 0;
    }

    @Override
    public int openSpots() {
        return 0;
    }

    @Override
    public String checkWinner() {
        return null;
    }
}
