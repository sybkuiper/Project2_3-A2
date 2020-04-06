package Players;

import Hanze.GameClient;
import Hanze.ServerCommunication;

import java.io.IOException;
import java.util.*;

public class Robot extends Player {
    private int difficulty; //0 easy,1 hard,2 impossible
    private String game;
    private Random randomGenerator;
    public Robot(GameClient client, int difficulty, String game) throws IOException, InterruptedException {
        super(client,game +".A.I.");
        setServerConnection(new ServerCommunication(client, this));
        this.difficulty = difficulty;
        this.game = game;
        randomGenerator = new Random();
        getServerConnection().logIN(getName());
        getServerConnection().subscribe(game);
    }

    public String think(Map<String,String> gameState){
        String bestMove = "0";
        int bestScore = -1000;
        int openSpots = openSpots(gameState);

        //get all empty spots and try going there to see what is the best spot
        for (Map.Entry<String, String> entry : gameState.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value.equals("E")){
                gameState.replace(key, "X");
                int score = minimax(gameState, 0, false, openSpots);
                gameState.replace(key,"E");
                if(score>bestScore) {
                    bestScore = score;
                    bestMove = key;
                }
            }
        }
        return bestMove;
    }

    private int minimax(Map<String, String> gameState, int steps, boolean isMaximizing, int openSpots){
        String result = testTicTacToeWinner(gameState,openSpots);
        if(result != null){
            int score = 0;
            if(result.equals("TIE")){score = 0;}
            if(result.equals("O")){score = -1;}
            if(result.equals("X")){score = 1;}
            return score;
        }
        if(isMaximizing){
            int bestScore = -1000;
            for (Map.Entry<String, String> entry : gameState.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if(value.equals("E")){
                    gameState.replace(key, "X");
                    int score = minimax(gameState, steps + 1, false, openSpots);
                    gameState.replace(key,"E");
                    if(score>bestScore) {
                        bestScore = score;
                    }
                }
            }
            return bestScore;
        }else{
            int bestScore = 1000;
            for (Map.Entry<String, String> entry : gameState.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if(value.equals("E")){
                    gameState.replace(key, "O");
                    int score = minimax(gameState, steps + 1, true, openSpots);
                    gameState.replace(key,"E");
                    if(score<bestScore) {
                        bestScore = score;
                    }
                }
            }
            return bestScore;
        }
    }

    private int openSpots(Map<String,String> gameState){
        int openspots = 0;
        for (Map.Entry<String, String> entry : gameState.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value.equals("E")){
                openspots++;
            }
        }
        return openspots;
    }

    private String testTicTacToeWinner(Map<String,String> gameState, int openSpots){
        String winner = null;
        String a;
        String b;
        String c;
        int position = 0;

        //horizontal
        for (int i = 0; i<3;i++){
             a = gameState.get(Integer.toString(position));
             b = gameState.get(Integer.toString(position+1));
             c = gameState.get(Integer.toString(position+2));
            if (a.equals(b) && b.equals(c)){ if (!a.equals("E")){winner = a;}}
            position += 3; }

        //vertical
        for (int i = 0; i<3;i++){
             a = gameState.get(Integer.toString(i));
             b = gameState.get(Integer.toString(i+3));
             c = gameState.get(Integer.toString(i+6));
            if (a.equals(b) && b.equals(c)){ if (!a.equals("E")){winner = a;}}}

        //diagonal
         a = gameState.get(Integer.toString(0));
         b = gameState.get(Integer.toString(4));
         c = gameState.get(Integer.toString(8));
        if (a.equals(b) && b.equals(c)){ if (!a.equals("E")){winner = a;}}

         a = gameState.get(Integer.toString(2));
         b = gameState.get(Integer.toString(4));
         c = gameState.get(Integer.toString(6));
        if (a.equals(b) && b.equals(c)){ if (!a.equals("E")){winner = a;}}

        if(winner == null && openSpots == 0){winner = "TIE";}
        return winner;
        System.out.println(winner);
    }
}
