package Games;

import Hanze.ServerCommunication;
import Players.Human;
import Players.Player;
import Players.Robot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


public class TicTacToe extends Game {

    Game parent;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); //voor testen normaal komt de input van de GUI

    public TicTacToe(Player player, Game parent){
        this.parent = parent;
        game = this;
        player1 = player;
        online = parent.getOnline();
        for(int i=0;i<9;i++) {
            getGameBoard().put(Integer.toString(i), "E");
        }
    }

    public void offline(){
        if(turn == null){
            if(!online){
                if(rand.nextInt(2) == 0){turn = player1;}else {
                    turn = parent.getPlayer2();
                }
            }
        }
        String winner;
        winner = checkWinner(getGameBoard());
        if(winner != null){System.out.println("Game end, result : "+winner);}
        else{
            makeMove(turn);
        }

    }

    public void makeMove(Player player){
        if(player instanceof Human){
            try {
                System.out.println(getGameBoard());
                System.out.println("Your turn: give a number between 0 and 8");
                String input = reader.readLine();
                getGameBoard().replace(input, "O"); //O = circle X = cross E = empty
                if(online){
                    player.getServerConnection().move(input);
                }else{
                    System.out.println("Player : move "+ input);
                    turn = parent.getPlayer2();
                    offline();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (player instanceof Robot) {
            String move;
            move = ((Robot) player).think(getGameBoard());
            getGameBoard().replace(move, "X"); //O = round X = cross E = empty
            if(online){
                player.getServerConnection().move(move);
            }else{
                System.out.println("AI : move "+ move);
                turn = player1;
                offline();
            }
        }
    }

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
}
