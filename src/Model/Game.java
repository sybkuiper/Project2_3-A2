package Model;

import Controller.ViewController;

import java.util.HashMap;
import java.util.Map;

public abstract class Game {

    private Game game;
    private Map<Integer,String> gameBoard = new HashMap<>(); //game map
    private int rows;
    private int columns;
    private String playerOne;
    private ViewController controller;

    public Game(int rows, int columns, String playerOne, ViewController controller){
        this.rows = rows;
        this.columns = columns;
        this.playerOne = playerOne;
        this.controller = controller;
        generateGameBoard();
    }

    public ViewController getController() {
        return controller;
    }

    protected void setGame(Game game){
        this.game = game;
    }

    public Map<Integer, String> getGameBoard() {
        return gameBoard;
    }

    public void generateGameBoard(){
        int sizeOfBoard = rows * columns;
        for(int field = 0; field < sizeOfBoard; field++){
            System.out.println(field);
            gameBoard.put(field,"E");
        }
    }

    public void updateGameBoard(Integer move, String player){
        int key = move;
        if(player.equals(controller.field.getText())) {//playerOne)) {
            gameBoard.replace(key, "X");
        } else {
            gameBoard.replace(key, "O");
        }
        System.out.println(player + " has placed move: " + move);
        printGameState();
    }

    public String getPlayerOne() {
        return playerOne;
    }

    public void printGameState(){
        for(int row = 1; row <= rows; row++){
            int rowToPrint = row*columns;
            int printed = row*columns-columns;
            String rowString = "";
            while(printed < rowToPrint) {
                if ((printed + 1) < rowToPrint) {
                    rowString = rowString.concat(gameBoard.get(printed) + ", ");
                } else {
                    rowString = rowString.concat(gameBoard.get(printed));
                }
                printed++;
            }
            System.out.println(rowString);
        }
    }

    public abstract Integer think(Map<Integer,String> gameBoard);
    public abstract int minimax(Map<Integer,String> gameBoard,int steps, boolean isMaximizing);
    public abstract int openSpots(Map<Integer,String> gameBoard);
    public abstract String checkWinner(Map<Integer,String> gameBoard);
}
