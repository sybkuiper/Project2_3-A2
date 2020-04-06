package Hanze;

import Players.Player;
import Players.Robot;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ServerCommunication extends Thread {
    private final static String LOCAL_IP = "localhost";
    private final static String REMOTE_IP = "145.33.225.170";
    private final static int PORT = 7789;
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private List<String> commandQueue;
    private boolean isRunning = false;
    private Player player;
    private GameClient client;

    public ServerCommunication(GameClient client, Player player) throws IOException, InterruptedException {
        this.socket = new Socket(LOCAL_IP,PORT);
        this.client = client;
        this.commandQueue = new ArrayList<>();
        this.in = new Scanner(this.socket.getInputStream());
        this.out = new PrintWriter(this.socket.getOutputStream(),true);
        this.player = player;
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

    public void challenge(String player, String gameType){
        //addToCommandQueue("challenge " + player + " " + gameType + "");
        addToCommandQueue("challenge \"" + player + "\" \"" + gameType + "\"");
    }

    public void acceptChallenge(String challengeNumber){
        addToCommandQueue("challenge accept " + challengeNumber);
    }

    public void logout(){
        addToCommandQueue("logout");
    }

    /**
     * Geef een match op.
     */
    public void forfeit(){
        addToCommandQueue("forfeit");
    }

    public void move(String move){
        addToCommandQueue("move " + move);
    }

    private Map<String, String> createMap(String input) {
        input = input.substring(1, input.length() - 1);
        String[] keyvalue = input.split(", ");
        Map<String, String> map = new HashMap<>();
        for (String pair : keyvalue) {
            String[] entry = pair.split(": ");
            map.put(entry[0], entry[1]);
        }
        System.out.println(player.getName() + " : " + map);
        return map;
    }

    private void parse(String input){
        //https://stackoverflow.com/questions/7347856/how-to-convert-a-string-into-an-arraylist
        List<String> ignoreList = new ArrayList<>();
        ignoreList.add("Strategic Game Server Fixed [Version 1.1.0]");
        ignoreList.add("(C) Copyright 2015 Hanzehogeschool Groningen");
//        ignoreList.add("OK");

        if(!ignoreList.contains(input)) {
            //https://stackoverflow.com/questions/9588516/how-to-convert-string-list-into-list-object-in-java
            if(input.startsWith("SVR GAMELIST ")){
                input = input.replace("SVR GAMELIST ","").replace("[","").replace("]","");
                String[] split = input.split(",");
                for(String str : split){
                    client.getGames().add(str);
                }
            }
            if(input.startsWith("SVR PLAYERLIST ")){
                input = input.replace("SVR PLAYERLIST ","").replace("[","").replace("]","");
                String[] split = input.split(",");
                for(String str : split){
                    client.getOnlinePlayers().add(str);
                }
            }
            if(input.startsWith("SVR GAME CHALLENGE ")){
                if(input.startsWith("SVR GAME CHALLENGE CANCELLED ")){
                    input = input.replace("SVR GAME CHALLENGE CANCELLED {CHALLENGENUMBER: ","").replace("}","");
                    System.out.println("Challenge " + input + " is geannuleerd");
                    //Todo: controller call die aangeeft dat de match uitnodiging voor ID is verlopen
                } else {
                    System.out.println(player.getName() + ": received game invite");
                    //https://stackoverflow.com/questions/26485964/how-to-convert-string-into-hashmap-in-java
                    input = input.replace("SVR GAME CHALLENGE ", "");
                    if(player instanceof Robot){
                        acceptChallenge(createMap(input).get("CHALLENGENUMBER").replace("\"",""));
                    }
                }
            }
            if(input.startsWith("SVR GAME MATCH ")){
                //https://stackoverflow.com/questions/26485964/how-to-convert-string-into-hashmap-in-java
                input = input.replace("SVR GAME MATCH ", "");
                input = input.substring(1, input.length() - 1);
                String[] keyvalue = input.split(", ");
                Map<String, String> map = new HashMap<>();
                for (String pair : keyvalue) {
                    String[] entry = pair.split(": ");
                    map.put(entry[0], entry[1]);
                }
                System.out.println(player.getName() + " : " + map);
                //System.out.println(player.getName() + " : " + map.get("PLAYERTOMOVE").replace("\"",""));
                //System.out.println((player.getName().equals(map.get("PLAYERTOMOVE").replace("\"", ""))));
//                if(player.getName().equals(map.get("PLAYERTOMOVE").replace("\"",""))){
//                    System.out.println(player.getName() + " : " + "it's my turn");
//                    move("8");
//                }
                //Todo: Start game interface
            }
            if(input.startsWith("SVR GAME YOURTURN ")){
                input = input.replace("SVR GAME YOURTURN ","");
                input = input.substring(1,input.length()-1);
                String[] entry = input.split(": ");
                Map<String,String> map = new HashMap<>();
                map.put(entry[0],entry[1]);
                System.out.println(map);
                System.out.println(player.getName() + " : " + "it's my turn");
                player.getActiveGame().makeMove(); //Todo: makeMove() function, where the AI should automatically make the best move, and the player should have the ability to move.
                //Todo: Enable ability to make a turn (should enable interface, the interface allows the method call move)
            }
            if(input.startsWith("SVR GAME MOVE ")){
                input = input.replace("SVR GAME MOVE ","");
                input = input.substring(1,input.length()-1);
                String[] keyvalue = input.split(", ");
                Map<String, String> map = new HashMap<>();
                for (String pair : keyvalue) {
                    String[] entry = pair.split(": ");
                    map.put(entry[0], entry[1]);
                }
                System.out.println(map);
                //Todo: verwerken reactie spel, hoe? testen
            }
            if(input.startsWith("SVR GAME ")){
                input.replace("SVR GAME ","");
                if(input.startsWith("WIN")){
                    input = input.replace("WIN ","");
                    input = input.substring(1,input.length()-1);
                    String[] keyvalue = input.split(", ");
                    Map<String, String> map = new HashMap<>();
                    for (String pair : keyvalue) {
                        String[] entry = pair.split(": ");
                        map.put(entry[0], entry[1]);
                    }
                    System.out.println(map);
                    //Todo: verwerken reactie spel, hoe? testen
                } else if (input.startsWith("LOSS")){
                    input = input.replace("LOSS ","");
                    input = input.substring(1,input.length()-1);
                    String[] keyvalue = input.split(", ");
                    Map<String, String> map = new HashMap<>();
                    for (String pair : keyvalue) {
                        String[] entry = pair.split(": ");
                        map.put(entry[0], entry[1]);
                    }
                    System.out.println(map);
                    //Todo: verwerken reactie spel, hoe? testen
                } else if (input.startsWith("DRAW")){
                    input = input.replace("DRAW ","");
                    input = input.substring(1,input.length()-1);
                    String[] keyvalue = input.split(", ");
                    Map<String, String> map = new HashMap<>();
                    for (String pair : keyvalue) {
                        String[] entry = pair.split(": ");
                        map.put(entry[0], entry[1]);
                    }
                    System.out.println(map);
                    //Todo: verwerken reactie spel, hoe? testen
                }
            }
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
 /*           for (String player : client.getOnlinePlayers()){
                if(!(player == getName())){
                    challenge(player,"\"Tic-tac-toe\"");
                }
            }*/
            if(!commandQueue.isEmpty()){
                out.println(commandQueue.get(0));
                System.out.println(player.getName() + " : " + commandQueue.get(0));
                commandQueue.remove(commandQueue.get(0));
                out.flush();
            }
        }
    }

    public void getPlayerList() {
        addToCommandQueue("get playerlist");
    }

    public void getGameList(){
        addToCommandQueue("get gamelist");
    }
}