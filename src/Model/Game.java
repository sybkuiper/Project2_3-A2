package Model;

import Controller.ViewController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class Game {

    private Map<Integer,String> gameBoard = new HashMap<>(); //game map
    private Map<String, String> players;
    private int rows;
    private int columns;
    private String playerOne;
    protected String playersTurn;
    private ViewController controller;

    public Game(int rows, int columns, String playerOne, ViewController controller, boolean online){
        if(!online){
            // game is played offline
            playersTurn = getFirstPlayer(controller.playerName);
            if(playersTurn == "AI"){
                makeMove(think(getGameBoard()));
            }
        }
        this.rows = rows;
        this.columns = columns;
        this.playerOne = playerOne;
        this.controller = controller;
        generateGameBoard();
    }

    public String getFirstPlayer(String player){
        int random = new Random().nextInt(2);
        /*switch(random){
            case 0:
                return player;
            case 1:
                return "AI";
        }*/
        return player; // als AI geretourneerd wordt als 1e speler crasht de applicatie
    }

    public ViewController getController() {
        return controller;
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

    public void offline() {
        String winner = checkWinner(getGameBoard());
        if (winner != null) {
            System.out.println("Game end, result : " + winner);
        } else {
            makeMove(think(getGameBoard()));
        }
    }

    public void updateGameBoard(Integer move, String player){
        int key = move;
        if(player.equals("AI")) {//playerOne)) {
            gameBoard.replace(key, "X");
            getController().showPlayer(key);
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

    public abstract void makeMove(Integer move);
    public abstract Integer think(Map<Integer,String> gameBoard);
    public abstract int minimax(Map<Integer,String> gameBoard,int steps, boolean isMaximizing);
    public abstract int openSpots(Map<Integer,String> gameBoard);
    public abstract String checkWinner(Map<Integer,String> gameBoard);
}
