import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

//this class is used to communicate with the server
public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String ip;
    private int port;
    private serverResponseThread serverResponse;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    //start connection with the server
    public void serverConnect(){
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            serverResponse = new serverResponseThread(clientSocket);
            serverResponse.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //give command to the server
    public void giveCommand(String command){
        out.println(command);
    }

    //disconnect from the server
    public void serverDisconnect() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getServerResponses() {
        return serverResponse.getServerResponses();
    }
}
