package Model;

import Controller.ViewController;

import java.util.*;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

public abstract class Game {

    public String turn = "B";
    protected Map<Integer,String> gameBoard = new HashMap<>(); //game map
    protected Map<String, String> players;
    protected ArrayList<String> debugmoves = new ArrayList<>();
    private int rows;
    private int columns;
    protected String playerOne;
    protected String playersTurn;
    private ViewController controller;
    protected String playerTwo;
    protected boolean online;

    public Game(int rows, int columns, String playerOne, ViewController controller, boolean online){
        controller.setGame(this);
        this.rows = rows;
        this.columns = columns;
        this.playerOne = playerOne;
        this.controller = controller;
        this.online = online;
        this.gameBoard = generateGameBoard();
        if(!online){
            // game is played offline
            //gecommented om othello te testen
            //playersTurn = getFirstPlayer(controller.playerName);
            controller.setBeurt(selectPlayerOne(playerOne, "AI") + " is aan de beurt");
            if(this instanceof Reversi) {
                controller.performActionOnTile("updateTileAmounts", BLACK);
                controller.performActionOnTile("updateTileAmounts", WHITE);
                controller.performActionOnTile("disableIllegalMoves",BLACK);
                if (playersTurn.equals("AI")) {
                    makeMove(think(getGameBoard()));
                }
            }
        }
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
        setPlayersTurn(playerOne);
        this.gameBoard = generateGameBoard();
    }

    /**
     * When the game is played offline, this function is called to select if the player or the AI is player one
     * @param playerOne the player playing in the interface
     * @param playerTwo the artificial intelligence
     */
    private String selectPlayerOne(String playerOne, String playerTwo){
        Random random = new Random();
        if(random.nextInt(2) == 0){
            setPlayersTurn(playerOne);
            this.playerTwo = playerTwo;
        } else {
            setPlayersTurn(playerTwo);
            this.playerOne = playerTwo;
            this.playerTwo = playerOne;
        }
        return this.playerOne;
    }

    /**
     * Changes the players turn to the next player to make a move
     * @param player the player that is allowed to move
     */
    public void setPlayersTurn(String player){
        playersTurn = player;
    }

    public ViewController getController() {
        return controller;
    }

    public Map<Integer, String> getGameBoard() {
        return gameBoard;
    }

    public Map<Integer,String> generateGameBoard(){
        Map<Integer,String> board = new HashMap<>();
        int sizeOfBoard = rows * columns;
        for(int field = 0; field < sizeOfBoard; field++){
            System.out.println(field);
            if(field == 27 || field == 36){
                board.put(field,"W");
            } else if (field == 28 || field == 35){
                board.put(field,"B");
            } else {
                board.put(field, "E");
            }
        }
        return board;
    }

    public void offline() {
        String winner = checkWinner(getGameBoard());
        if (winner != null) {
            System.out.println("Game end, result : " + winner);
        } else {
            makeMove(think(getGameBoard()));
        }
    }

    public String getPlayersTurn(){return playersTurn;}

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
        controller.performActionOnTile("hideLegalMoves");
        if (playersTurn.equals(playerTwo)){
            updateGameBoard(move,playersTurn);
            playersTurn = playerOne;
            controller.performActionOnTile("disableIllegalMoves",BLACK);
        } else if(playersTurn.equals(playerOne)){
            updateGameBoard(move, playersTurn);
            playersTurn = playerTwo;
            controller.performActionOnTile("disableIllegalMoves",WHITE);
        }

        // in order to ensure the AI makes a play
        if(playersTurn.equals("AI")){
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
    public String getPlayerOne(){
        return playerOne;
    };

    public abstract void updateGameBoard(Integer move, String player);
    public abstract Integer think(Map<Integer,String> gameBoard);
    public abstract int minimax(Map<Integer,String> gameBoard,int steps, boolean isMaximizing);
    public abstract String checkWinner(Map<Integer,String> gameBoard);
    public abstract Map<Integer, String> updateBoard(Map<Integer, String> gameBoard, int madeMove, String color);

    public abstract LinkedHashSet<Integer> getLegalMoves(Map<Integer, String> gameBoard, String color);


}
