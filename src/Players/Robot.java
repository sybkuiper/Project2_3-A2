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

    public String think(Map<String,String> gameBoard){
        String bestMove = "0";
        int bestScore = -1000;

        //get all empty spots and try going there to see what is the best spot
        for (Map.Entry<String, String> entry : gameBoard.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            //try all empty spots and make them X and do minimax on the gamboard
            if(value.equals("E")){
                gameBoard.replace(key, "X");
                int score = minimax(gameBoard, 0, false);
                gameBoard.replace(key,"E");
                if(score>bestScore) {
                    bestScore = score;
                    bestMove = key;
                }
            }
        }
        return bestMove;
    }

    //looks to all posible outcomes of the game state
    private int minimax(Map<String, String> gameBoard, int steps, boolean isMaximizing){

        //give a score to an outcome. the better the outcome, the higher score.
        String result = checkWinner(gameBoard);
        if(result != null){
            int score = 0;
            if(result.equals("TIE")){score = 0;}
            if(result.equals("O")){score = -1;}
            if(result.equals("X")){score = 1;}
            return score;
        }


        //if its the maximizing turn, the AI's turn, check all outcomes
        if(isMaximizing){
            int bestScore = -1000;
            for (Map.Entry<String, String> entry : gameBoard.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if(value.equals("E")){
                    gameBoard.replace(key, "X");
                    int score = minimax(gameBoard, steps + 1, false);
                    gameBoard.replace(key,"E");
                    if(score>bestScore) {
                        bestScore = score;
                    }
                }
            }
            return bestScore;
            //if its the minimizing turn, the other player, check all outcomes
        }else{
            int bestScore = 1000;
            for (Map.Entry<String, String> entry : gameBoard.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if(value.equals("E")){
                    gameBoard.replace(key, "O");
                    int score = minimax(gameBoard, steps + 1, true);
                    gameBoard.replace(key,"E");
                    if(score<bestScore) {
                        bestScore = score;
                    }
                }
            }
            return bestScore;
        }
    }

    //check for open spots on the board
    private int openSpots(Map<String,String> gameBoard){
        int openspots = 0;
        for (Map.Entry<String, String> entry : gameBoard.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value.equals("E")){
                openspots++;
            }
        }
        return openspots;
    }

    //check if the gamestate has a winner and return the winner or TIE
    public String checkWinner(Map<String,String> gameBoard){
        String winner = null;
        String a;
        String b;
        String c;
        int position = 0;

        //horizontal
        for (int i = 0; i<3;i++){
             a = gameBoard.get(Integer.toString(position));
             b = gameBoard.get(Integer.toString(position+1));
             c = gameBoard.get(Integer.toString(position+2));
            if (a.equals(b) && b.equals(c)){ if (!a.equals("E")){winner = a;}}
            position += 3; }

        //vertical
        for (int i = 0; i<3;i++){
             a = gameBoard.get(Integer.toString(i));
             b = gameBoard.get(Integer.toString(i+3));
             c = gameBoard.get(Integer.toString(i+6));
            if (a.equals(b) && b.equals(c)){ if (!a.equals("E")){winner = a;}}}

        //diagonal
         a = gameBoard.get(Integer.toString(0));
         b = gameBoard.get(Integer.toString(4));
         c = gameBoard.get(Integer.toString(8));
        if (a.equals(b) && b.equals(c)){ if (!a.equals("E")){winner = a;}}

         a = gameBoard.get(Integer.toString(2));
         b = gameBoard.get(Integer.toString(4));
         c = gameBoard.get(Integer.toString(6));
        if (a.equals(b) && b.equals(c)){ if (!a.equals("E")){winner = a;}}



        if(winner == null && openSpots(gameBoard) == 0){winner = "TIE";}
        return winner;
    }
}
