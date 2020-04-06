package Games;

import Hanze.ServerCommunication;
import Players.Human;
import Players.Player;
import Players.Robot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


public class TicTacToe extends Game {

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); //voor testen normaal komt de input van de GUI

    public TicTacToe(Player player){
        game = this;
        player1 = player;
        for(int i=0;i<9;i++) {
            getGameBoard().put(Integer.toString(i), "E");
        }
    }

    public void makeMove(Player player){
        if(player instanceof Human){
            try {
                System.out.println("Your turn: give a number between 0 and 8");
                String input = reader.readLine();
                getGameBoard().replace(input, "O"); //O = circle X = cross E = empty
                player.getServerConnection().move(input);
                System.out.println("Kees");
                System.out.println(getGameBoard());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (player instanceof Robot) {
            String move;
            move = ((Robot) player).think(getGameBoard());
            System.out.println("AI");
            getGameBoard().replace(move, "X"); //O = round X = cross E = empty
            player.getServerConnection().move(move);
            System.out.println(move);
            System.out.println(getGameBoard());
        }
    }
}
