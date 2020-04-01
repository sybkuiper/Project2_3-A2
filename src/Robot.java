import java.util.*;

//this class implements everything a Robot can do. Including making decisions based on miniMaxi.
public class Robot extends Player {
    private int difficulty; //0 easy,1 hard,2 impossible
    private String game;
    private Random randomGenerator;

    public Robot(String ip, int port, int difficulty, String game){
        super(ip, port);
        this.difficulty = difficulty;
        this.game = game;
        randomGenerator = new Random();
        setup(game +".A.I");
    }

    //give the bot a name and subscribe it to a game.
    private void setup(String name){
        logIN(name);
        subscribe(game);
    }

    //the decision making of the bot (now mainly focused on tic-tac-toe)
    String think(Map<String,String> gameState){
        String bestMove = "0";
        int bestScore = -10;

        //get all empty spots and try going there to see what is the best spot
        for (Map.Entry<String, String> entry : gameState.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value.equals("E")){
                //try every empty position and score every outcome to get the best one.
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

    //minimaxi algorithm
    private int minimax(Map<String, String> gameState, int steps, boolean isMaximizing){
        return 1;
    }
}
