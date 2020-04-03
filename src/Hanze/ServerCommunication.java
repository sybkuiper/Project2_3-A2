package Hanze;

import Players.Player;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServerCommunication extends Thread {
    private final static String LOCAL_IP = "localhost";
    private final static String REMOTE_IP = "145.33.225.170";
    private final static int PORT = 7789;
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private List<String> responses;
    private List<String> commandQueue;
    private boolean isRunning = false;
    private String name;

    public ServerCommunication(GameClient client, String name) throws IOException, InterruptedException {
        this.socket = new Socket(LOCAL_IP,PORT);
        this.responses = new ArrayList<>();
        this.commandQueue = new ArrayList<>();
        this.in = new Scanner(this.socket.getInputStream());
        this.out = new PrintWriter(this.socket.getOutputStream(),true);
        this.name = name;
        //store socket and server thread in controller
        client.getSockets().add(socket);
        client.getServerCommunications().add(this);
        //start thread
        this.start();
    }


    public void disconnectFromServer() throws IOException {
        this.in.close();
        this.out.close();
        this.socket.close();
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
        List<String> ignoreList = new ArrayList<>();
        ignoreList.add("Strategic Game Server Fixed [Version 1.1.0]");
        ignoreList.add("(C) Copyright 2015 Hanzehogeschool Groningen");
        ignoreList.add("OK");

        if(!ignoreList.contains(input)) {
            responses.add(0, input);
            System.out.println(input);
        }
    }

    public void run(){
        if(!this.isRunning){
            try {
                this.sleep(5000);
                this.isRunning = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while(this.isRunning){
            parse(in.nextLine());

            if(!commandQueue.isEmpty()){
                out.println(commandQueue.get(0));
                System.out.println(this.name + " : " + commandQueue.get(0));
                commandQueue.remove(commandQueue.get(0));
                out.flush();
            }
        }
    }
}