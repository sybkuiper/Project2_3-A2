package Players;

import Hanze.GameClient;

import java.util.*;

public class Robot extends Player {
    private int difficulty; //0 easy,1 hard,2 impossible
    private String game;
    private Random randomGenerator;

    public Robot(GameClient client, int difficulty, String game){
        this.difficulty = difficulty;
        this.game = game;
        randomGenerator = new Random();
        client.ConnectToServer(this);
        client.getServerCommunication(this).logIN(game +".A.I.");
        client.getServerCommunication(this).subscribe(game);
    }

    public String think(Map<String,String> gameState){
        String bestMove = "0";
        int bestScore = -10;

        //get all empty spots and try going there to see what is the best spot
        for (Map.Entry<String, String> entry : gameState.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value.equals("E")){
                gameState.replace(key, "C");
                int score = minimax(gameState, 0, true);
                gameState.replace(key,"E");
                if(score>bestScore) {
                    bestScore = score;
                    bestMove = key;
                }
            }
        }
        return bestMove;
    }

    private int minimax(Map<String, String> gameState, int steps, boolean isMaximizing){
        return 1;
    }
}
