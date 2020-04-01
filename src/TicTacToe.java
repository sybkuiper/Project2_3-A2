import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

//this class keeps the status of a running game of Tic-tac-toe
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

    //when starting the game. needs cleaning
    private void startGame(Player player){
        //makes array that keeps track of the gameBoard
        for(int i=0;i<9;i++){
            gameState.put(Integer.toString(i),"E");
        }
        System.out.println(gameState);
        player.subscribe("Tic-tac-toe");

        //before the game goes to running, watch who starts the game and then start the game.
        while (!runningGame){
            if(player.getServerResponse().get(0).contains("SVR GAME MATCH")){
                runningGame = true;
            }
        }
        if (player.getServerResponse().get(0).contains("PLAYERTOMOVE: \"" + playerName + "\"")){ player.myTurn = true;
        }else{ AI.myTurn = true; }
        runningGame();
    }

    //looks who's turn it is and gives them the turn.
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

    //when it is the playersTurn wait for input. now via the commandline later via GUI.
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

    //when it is the AI's turn and let him make a move based on it's  algorithm.
    private void AITurn(){
        String move;
        move = AI.think(gameState);

        gameState.replace(move, "X"); //O = round X = cross E = empty
        AI.move(move);
        System.out.println(gameState);
        player.myTurn = true;
    }

}
