package Model;

import Controller.ViewController;

import java.util.LinkedHashSet;
import java.util.Map;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;


public class TicTacToe extends Game {

    public TicTacToe(int rows, int columns, String playerOne, ViewController controller, boolean online){
        super(rows, columns, playerOne, controller, online);
        controller.setGame(this);
        System.out.println("game initialized");
    }

    public TicTacToe(int rows, int columns, String playerOne, String playerTwo, ViewController controller, boolean online){
        super(rows, columns, playerOne, playerTwo, controller, online);
        controller.setGame(this);
    }

    @Override
    public Integer think(Map<Integer,String> gameBoard){
        Integer bestMove = 0;
        int bestScore = -1000;

        //get all empty spots and try going there to see what is the best spot
        for (Map.Entry<Integer, String> entry : gameBoard.entrySet()) {
            Integer key = entry.getKey();
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

    public Map<Integer, String> updateBoard(Map<Integer, String> gameBoard, int madeMove, String color){
        return null;
    }

    @Override
    public LinkedHashSet<Integer> getLegalMoves(Map<Integer, String> gameBoard, String color) {
        return null;
    }


    public void updateGameBoard(Integer move, String player){
        int key = move;
        if(playersTurn.equals(playerOne)) {
            gameBoard.replace(key,"X");
            getController().updateGrid(key, "X");
        } else{
            gameBoard.replace(key,"O");
            getController().updateGrid(key, "O");
        }
        System.out.println(player + " has placed move: " + move);
        getController().setBeurt(getPlayersTurn() + " is aan de beurt");
        printGameState();
    }

    //looks to all posible outcomes of the game state
    @Override
    public int minimax(Map<Integer,String> gameBoard,int steps, boolean isMaximizing){
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
            for (Map.Entry<Integer, String> entry : gameBoard.entrySet()) {
                Integer key = entry.getKey();
                Object value = entry.getValue();
                if(value.equals("E")){
                    gameBoard.replace(key, "X");
                    int score = minimax(gameBoard,steps + 1, false);
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
            for (Map.Entry<Integer, String> entry : gameBoard.entrySet()) {
                Integer key = entry.getKey();
                Object value = entry.getValue();
                if(value.equals("E")){
                    gameBoard.replace(key, "O");
                    int score = minimax(gameBoard,steps + 1, true);
                    gameBoard.replace(key,"E");
                    if(score<bestScore) {
                        bestScore = score;
                    }
                }
            }
            return bestScore;
        }
    }

    //check if the gamestate has a winner and return the winner or TIE
    @Override
    public String checkWinner(Map<Integer,String> gameBoard){
        String winner = null;
        String a;
        String b;
        String c;
        int position = 0;

        //horizontal
        for (int i = 0; i<3;i++){
            a = gameBoard.get(position);
            b = gameBoard.get(position+1);
            c = gameBoard.get(position+2);
            if (a.equals(b) && b.equals(c)){ if (!a.equals("E")){winner = a;}}
            position += 3; }

        //vertical
        for (int i = 0; i<3;i++){
            a = gameBoard.get(i);
            b = gameBoard.get(i+3);
            c = gameBoard.get(i+6);
            if (a.equals(b) && b.equals(c)){ if (!a.equals("E")){winner = a;}}}

        //diagonal
        a = gameBoard.get(0);
        b = gameBoard.get(4);
        c = gameBoard.get(8);
        if (a.equals(b) && b.equals(c)){ if (!a.equals("E")){winner = a;}}

        a = gameBoard.get(2);
        b = gameBoard.get(4);
        c = gameBoard.get(6);
        if (a.equals(b) && b.equals(c)){ if (!a.equals("E")){winner = a;}}



        if(winner == null && openSpots(gameBoard) == 0){winner = "TIE";}
        return winner;
    }
}
