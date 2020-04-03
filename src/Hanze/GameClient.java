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

    public GameClient() throws IOException, InterruptedException {
        sockets = new ArrayList<>();
        serverCommunications = new ArrayList<>();
        new Human(this, "kees" );
        new Robot(this, 0, "Tic-tac-toe");
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
