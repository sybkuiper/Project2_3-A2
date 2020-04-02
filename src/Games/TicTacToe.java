/*package Games;

import Players.Human;
import Players.Player;
import Players.Robot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


public class TicTacToe {
    private Player player;
    private String playerName;
    private Robot AI;
    private boolean runningGame = false;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); //temporarily for user input
    private Map<String,String> gameState = new HashMap<String,String>();

    public TicTacToe(Human player, Robot AI){
        this.player = player;
        this.AI = AI;
        playerName = player.getName();
        startGame(this.player);
    }

    private void startGame(Player player){
        for(int i=0;i<9;i++){
            gameState.put(Integer.toString(i),"E");
        }
        System.out.println(gameState);
        player.subscribe("Tic-tac-toe");

        while (!runningGame){
            if(player.getServerResponse().get(0).contains("SVR GAME MATCH")){
                runningGame = true;
            }
        }
        if (player.getServerResponse().get(0).contains("PLAYERTOMOVE: \"" + playerName + "\"")){ player.myTurn = true;
        }else{ AI.myTurn = true; }
        runningGame();
    }

    private void runningGame(){
        while(runningGame){
            System.out.println(player.myTurn);
            if(player.myTurn){
                playersTurn();
            }else{
                AITurn();
            }
        }
    }

    private void playersTurn(){
        try {
            String input = reader.readLine();
            player.move(input);
            gameState.replace(input, "O"); //O = circle X = cross E = empty
            player.myTurn = false;
            System.out.println(gameState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void AITurn(){
        String move;
        move = AI.think(gameState);

        gameState.replace(move, "X"); //O = round X = cross E = empty
        AI.move(move);
        System.out.println(gameState);
        player.myTurn = true;
    }

}
*/