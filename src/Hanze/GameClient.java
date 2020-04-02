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
    private final static String REMOTE_IP = "145.33.225.170";
    private final static int PORT = 7789;
//    private Socket socket;
//    private ServerCommunication serverCommunication;
    private HashMap<Player, Socket> sockets;
    private HashMap<Player, ServerCommunication> serverCommunications;

    public GameClient() {
        sockets = new HashMap<>();
        serverCommunications = new HashMap<>();
        Human human = new Human(this, "kees" );
        Robot robot = new Robot(this, 0, "Tic-tac-toe");
    }

    /**
     * Creates a socket to connect to a server at a specific port.
     */
    public void ConnectToServer(Player player){
        try {
//            socket = new Socket("localhost", PORT);
//            serverCommunication = new ServerCommunication(socket);
//            serverCommunication.start();
//            TestThreadCommands t = new TestThreadCommands(serverCommunication);
//            t.start();
            Socket socket = new Socket("localhost",PORT);
            sockets.put(player, socket);
            serverCommunications.put(player, new ServerCommunication(socket));
            serverCommunications.get(player).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerCommunication getServerCommunication(Player player){
        return serverCommunications.get(player);
    }

    /**
     * Closes the BufferedReader in, PrintWriter out and the Socket socket.
     */
    public void DisconnectFromServer(){
        try {
            for(Player player : sockets.keySet()) {
                serverCommunications.get(player).getIn().close();
                serverCommunications.get(player).getOut().close();
                sockets.get(player).close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
