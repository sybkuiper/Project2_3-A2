package Model;

import Controller.ViewController;

import java.util.Map;

public class Reversi extends Game {
    public Reversi(int rows, int columns, String playerOne, ViewController controller, boolean online){
        super(rows, columns, playerOne, controller, online);
        generateGameBoard();
    }

    @Override
    public void makeMove(Integer move){}

    @Override
    public Integer think(Map<Integer,String> gameBoard) {
        return null;
    }

    @Override
    public int minimax(Map<Integer,String> gameBoard,int steps, boolean isMaximizing) {
        return 0;
    }

    @Override
    public int openSpots(Map<Integer,String> gameBoard) {
        return 0;
    }

    @Override
    public String checkWinner(Map<Integer,String> gameBoard) {
        return null;
    }
}
