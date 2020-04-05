package Games;

import Hanze.ServerCommunication;
import Players.Human;
import Players.Player;
import Players.Robot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


public class TicTacToe {
    private Player player;
    private String playerName;
    private ServerCommunication playerServer;
    private ServerCommunication AIServer;
    private Robot AI;

    private boolean runningGame = false;

    private Map<String,String> gameState = new HashMap<String,String>(); //game map

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); //voor testen normaal komt de input van de GUI

    public TicTacToe(Human player, Robot AI){
        this.player = player;
        this.AI = AI;
        playerName = player.getName();
        playerServer = player.getServerConnection();
        AIServer = AI.getServerConnection();
        for(int i=0;i<9;i++){
            gameState.put(Integer.toString(i),"E");
        }
        playerServer.subscribe("Tic-tac-toe");
        while(playerServer.getPlayersTurn() == null){
            System.out.println("hello world");
        }
        runningGame = true;
        runningGame();
    }

    private void runningGame(){
        while(runningGame){
            String nameOfPlayerWithTurn = playerServer.getPlayersTurn().replace("\"","");
            System.out.println(nameOfPlayerWithTurn.equals(player.getName()));
            if(nameOfPlayerWithTurn.equals(player.getName())){
                playersTurn();
            }else{
                AITurn();
            }
        }
    }

    private void playersTurn(){
        try {
            String input = "1";
            gameState.replace(input, "O"); //O = circle X = cross E = empty
            playerServer.move(input);
            System.out.println("Kees");
            System.out.println(gameState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void AITurn(){
        String move;
        move = AI.think(gameState);
        System.out.println("AI");
        gameState.replace(move, "X"); //O = round X = cross E = empty
        AIServer.move(move);
        System.out.println(move);
        System.out.println(gameState);
    }

}
