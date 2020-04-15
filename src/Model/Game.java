package Model;

import Controller.ViewController;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;

public abstract class Game {

    public String turn = "B";
    protected Map<Integer,String> gameBoard = new HashMap<>(); //game map
    protected Map<String, String> players;
    private int rows;
    private int columns;
    protected String playerOne;
    protected String playersTurn;
    private ViewController controller;
    protected String playerTwo;
    protected boolean online;

    public Game(int rows, int columns, String playerOne, ViewController controller, boolean online){
        if(!online){
            // game is played offline
            //gecommented om othello te testen
            //playersTurn = getFirstPlayer(controller.playerName);
            playersTurn = playerOne; //"AI";
            if(playersTurn == "AI"){
                makeMove(think(getGameBoard()));
            }
        }
        this.rows = rows;
        this.columns = columns;
        this.playerOne = playerOne;
        this.controller = controller;
        this.online = online;
        generateGameBoard();
    }

    public Game(int rows, int columns, String playerOne, String playerTwo, ViewController controller, boolean online){
        if(!online){
            // game is played offline
            //gecommented om othello te testen
            //playersTurn = getFirstPlayer(controller.playerName);
            playersTurn = playerOne; //"AI";
            if(playersTurn == "AI"){
                makeMove(think(getGameBoard()));
            }
        }
        this.players = new HashMap<>();
        this.rows = rows;
        this.columns = columns;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        if(this instanceof TicTacToe){
            if(playerOne.equals(controller.playerName)) {
                players.put(playerOne, "X");
                players.put(playerTwo, "O");
            } else {
                players.put(playerOne, "O");
                players.put(playerTwo, "X");
            }
        }
        this.controller = controller;
        this.online = online;
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
            if(field == 27 || field == 36){
                gameBoard.put(field,"W");
            } else if (field == 28 || field == 35){
                gameBoard.put(field,"B");
            } else {
                gameBoard.put(field, "E");
            }
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

    public void makeMove(Integer move){
        if (playersTurn.equals("AI")){
            updateGameBoard(move,playersTurn);
            playersTurn = playerOne;
        } else if(players.equals(playerOne)){
            updateGameBoard(move, playersTurn);
            playersTurn = "AI";
            makeMove(think(getGameBoard()));
        }
    }

    public int openSpots(Map<Integer,String> gameBoard){
        int openspots = 0;
        for (Map.Entry<Integer, String> entry : gameBoard.entrySet()) {
            Integer key = entry.getKey();
            Object value = entry.getValue();
            if(value.equals("E")){
                openspots++;
            }
        }
        return openspots;
    }

    public abstract void updateGameBoard(Integer move, String player);
    public abstract Integer think(Map<Integer,String> gameBoard);
    public abstract int minimax(Map<Integer,String> gameBoard,int steps, boolean isMaximizing);
    public abstract String checkWinner(Map<Integer,String> gameBoard);
    public abstract Map<Integer, String> updateBoard(Map<Integer, String> gameBoard, int madeMove, String color);

    public abstract LinkedHashSet<Integer> getLegalMoves(Map<Integer, String> gameBoard, String color);
}
