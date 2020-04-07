package Hanze;

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

/**
 * Class that contains the game client for the application.
 * This will be the main controller for the application.
 *
 * @author JasperSikkema, WJSchuringa
 */

public class GameClient {
    private List<Socket> sockets;
    private List<ServerCommunication> serverCommunications;
    private HashMap<String,Player> intelligences;
    private List<String> onlinePlayers;
    private List<String> games;

    public GameClient() throws IOException, InterruptedException {
        sockets = new ArrayList<>();
        serverCommunications = new ArrayList<>();
        intelligences = new HashMap<>();
        onlinePlayers = new ArrayList<>();
        games = new ArrayList<>();
        intelligences.put("Reversi",new Robot(this,0,"Reversi"));
        intelligences.put("Tic-tac-toe",new Robot(this, 0, "Tic-tac-toe"));
        new Human(this, "kees" );
    }

    public HashMap<String, Player> getIntelligences() {
        return intelligences;
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
