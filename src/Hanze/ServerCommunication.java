package Hanze;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServerCommunication extends Thread {
    private Scanner in;
    private PrintWriter out;
    private List<String> responses;
    private List<String> commandQueue;

    public ServerCommunication(Socket socket){
        this.responses = new ArrayList<>();
        this.commandQueue = new ArrayList<>();

        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e){
            System.out.println("error, no inputstream " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Scanner getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void addToCommandQueue(String command){
        commandQueue.add(command);
    }

    public void logIN(String name){
        addToCommandQueue("login " + name);
    }

    public void subscribe(String game){
        addToCommandQueue("subscribe " + game);
    }

    public void logout(){
        addToCommandQueue("logout");
    }

    public void forfeit(){
        addToCommandQueue("forfeit");
    }

    public void move(String move){
        addToCommandQueue("move " + move);
    }

    public List<String> getResponses() {
        return responses;
    }

    private void parse(String input){
        //https://stackoverflow.com/questions/7347856/how-to-convert-a-string-into-an-arraylist
        String[] responseType = {"GAMELIST","PLAYERLIST"};
        if (input.contains(responseType[0])){

        } else if (input.contains(responseType[1])){
            
        }
        responses.add(0, input);
    }

    public void run(){
        while(true){
            try {
                Thread.sleep(1000); // To prevent system from running continously.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while(!commandQueue.isEmpty()){
                for(String command : commandQueue){
                    out.println(command);
                }
            }
            String response = in.nextLine();
            parse(response);
        }
    }
}

/*    Communication with server:

        While listening:
            While command queue is not empty
                Send commands
            Parse response
 */
