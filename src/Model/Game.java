package Model;

import java.util.HashMap;
import java.util.Map;

public abstract class Game {

    private Game game;
    private Map<Integer,String> gameBoard = new HashMap<>(); //game map
    private int rows;
    private int columns;

    public Game(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        generateGameBoard();
    }

    public Map<Integer, String> getGameBoard() {
        return gameBoard;
    }

    public void generateGameBoard(){
        if(game instanceof Reversi) {
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    gameBoard.put(row, "E");
                }
            }
        } else if (game instanceof TicTacToe){
            for(int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    getGameBoard().put(row, "E");
                }
            }
        }
    }

    public void printGameState(){
        if(game instanceof Reversi){
            String gameState = "";
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    gameState = gameState.concat(getGameBoard().get(row + column));
                }
            }
        } else if (game instanceof TicTacToe){
            String gameState = "";
            for(int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    gameState = gameState.concat(getGameBoard().get(row + column));
                }
                System.out.println(gameState);
            }
        }
    }

    public abstract String think(Map<String,String> gameBoard);
    public abstract int minimax(Map<String, String> gameBoard, int steps, boolean isMaximizing);
    public abstract int openSpots(Map<String,String> gameBoard);
    public abstract String checkWinner(Map<String,String> gameBoard);
}
