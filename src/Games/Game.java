package Games;

import Players.Human;
import Players.Player;
import Players.Robot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Game {

    Game game;
    Player player1;
    Player player2;
    boolean online;
    Player turn;
    Random rand = new Random();
    private Map<String,String> gameBoard = new HashMap<String,String>(); //game map

    public Game(){

    }

    public Game(String gameType, Player player1, Player player2, boolean online){
        this.online = online;
        switch(gameType){
            case "Tic-tac-toe":
                game = new TicTacToe(player1, this);
                //player1.setActiveGame(game);
                if(!online){
                    addSecondPlayer(player2);
                    ((TicTacToe) game).offline();
                //   player2.setActiveGame(game);
                }
                break;
            case "Othello":
                game = new Reversi();
                break;
        }
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer2() {return player2; }

    private void addSecondPlayer(Player player){
        this.player2 = player;
    }

    public Map<String, String> getGameBoard() {
        return gameBoard;
    }

    public boolean getOnline(){return online;}

    public void makeMove(Player player){
    }
}
