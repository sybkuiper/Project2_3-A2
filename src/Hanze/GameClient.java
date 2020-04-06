package Hanze;

import Games.Game;
import Games.TicTacToe;
import Players.Human;
import Players.Player;
import Players.Robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Class that contains the game client for the application.
 * This will be the main controller for the application.
 *
 * @author JasperSikkema, WJSchuringa
 */

public class GameClient {
    private List<Socket> sockets;
    private List<ServerCommunication> serverCommunications;
    private Player player;
    private HashMap<String, Player> playerObjects;
    private List<String> onlinePlayers;
    private List<String> games;
    private Game game;

    public GameClient() throws IOException, InterruptedException {
        sockets = new ArrayList<>();
        serverCommunications = new ArrayList<>();
        playerObjects = new HashMap<>();
        onlinePlayers = new ArrayList<>();
        games = new ArrayList<>();
        playerObjects.put("Reversi",new Robot(this,0,"Reversi"));
        playerObjects.put("Tic-tac-toe",new Robot(this, 0, "Tic-tac-toe"));
        playerObjects.put("Human",new Human(this, "kees" ));
        player = playerObjects.get("Human");
        TestThread test = new TestThread(player);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public HashMap<String, Player> getPlayerObjects() {
        return playerObjects;
    }

    public List<String> getOnlinePlayers() {
        return onlinePlayers;
    }

    public List<String> getGames() {
        return games;
    }

    public List<Socket> getSockets() {
        return sockets;
    }

    public List<ServerCommunication> getServerCommunications() {
        return serverCommunications;
    }

    public void startGame(String gameType, Player robot, boolean online){
        setGame(new Game(gameType, player, robot, online));
    }

    public void startGame(String gameType, boolean online){
        setGame(new Game(gameType, player,null , online));
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new GameClient();
    }
}

    /**
     * Sends a command through the PrintWriter out to the server.
     * @param command the command to send to the server.
     * public void sendCommand(String command) {
     *         serverCommunication.getOut().println(command);
     *     }
     */
