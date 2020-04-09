package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

//sources:
//https://stackoverflow.com/questions/26485964/how-to-convert-string-into-hashmap-in-java
//https://stackoverflow.com/questions/7347856/how-to-convert-a-string-into-an-arraylist
//https://stackoverflow.com/questions/9588516/how-to-convert-string-list-into-list-object-in-java

public class NetworkController extends Thread {

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private List<String> commandQueue;
    private List<String> ignoreList;
    //private List<String> availableGames;
    private List<String> onlinePlayers;
    private boolean isRunning = false;
    private ViewController controller;

    public NetworkController(ViewController controller,String player,String ip_address, int port) throws IOException, InterruptedException {
        this.controller = controller;
        socket = new Socket(ip_address,port);
        commandQueue = new ArrayList<>();
        ignoreList = new ArrayList<>();
        //availableGames = new ArrayList<>();
        onlinePlayers = new ArrayList<>();
        ignoreList.add("Strategic Game Server Fixed [Version 1.1.0]");
        ignoreList.add("(C) Copyright 2015 Hanzehogeschool Groningen");
        ignoreList.add("OK");
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(),true);
        logIN(player);
        //getGameList();
        getPlayerList();
        this.start();
    }

    public void disconnect() throws IOException {
        in.close();
        out.close();
        socket.close();
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

    public void challenge(String player, String gameType) { addToCommandQueue("challenge \"" + player + "\" \"" + gameType + "\""); }

    public void acceptChallenge(String challengeNumber){
        addToCommandQueue("challenge accept " + challengeNumber);
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

    public void getPlayerList() {
        addToCommandQueue("get playerlist");
    }

    /*public void getGameList(){
        addToCommandQueue("get gamelist");
    }*/

    private Map<String, String> createMap(String input) {
        input = input.substring(1, input.length() - 1);
        String[] keyvalue = input.split(", ");
        Map<String, String> map = new HashMap<>();
        for (String pair : keyvalue) {
            String[] entry = pair.split(": ");
            map.put(entry[0], entry[1]);
        }
        return map;
    }



    private void parse(String input) {
        /*
        if (input.startsWith("SVR GAMELIST ")) {
            input = input.replace("SVR GAMELIST ", "").replace("[", "").replace("]", "");
            String[] split = input.split(",");
            for (String game : split) {
                availableGames.add(game);
                subscribe(game);
            }
            System.out.println(availableGames);
            controller.setAvailableGames(availableGames);
        }*/

        if (input.startsWith("SVR PLAYERLIST ")) {
            input = input.replace("SVR PLAYERLIST ", "").replace("[", "").replace("]", "");
            if (input.contains(",")) {
                String[] split = input.split(",");
                onlinePlayers.addAll(Arrays.asList(split));
                controller.setOnlinePlayers(onlinePlayers);
            } else {
                onlinePlayers.add(input);
                System.out.println(onlinePlayers);
                controller.setOnlinePlayers(onlinePlayers);
            }
        }

        if (input.startsWith("SVR GAME CHALLENGE ")) {
            if (input.startsWith("SVR GAME CHALLENGE CANCELLED ")) {
                input = input.replace("SVR GAME CHALLENGE CANCELLED {CHALLENGENUMBER: ", "").replace("}", "");
                System.out.println("Challenge " + input + " is geannuleerd");
//                controller.getInvitations.remove(input);
                //Todo: controller call die aangeeft dat de match uitnodiging voor ID is verlopen
            } else {
                /**
                 * Automatically accept an invite when received
                 */
                input = input.replace("SVR GAME CHALLENGE ", "");
                acceptChallenge(createMap(input).get("CHALLENGENUMBER").replace("\"", ""));

            }
        }

        if (input.startsWith("SVR GAME MATCH ")) {
            //           controller.initializeGame();
        }
        //Todo: Start game interface

        if (input.startsWith("SVR GAME YOURTURN ")) {
            //           controller.getAI().think();
        }

//        if(input.startsWith("SVR GAME ")){
//            input = input.replace("SVR GAME ","");
//            if(input.startsWith("WIN")){
//                controller.alertGameState("WIN");
//                client.setGame(null);
//                //Todo: verwerken reactie spel, hoe? testen
//            } else if (input.startsWith("LOSS")){
//                controller.alertGameState("LOSS");
//                client.setGame(null);
//            } else if (input.startsWith("DRAW")){
//                controller.alertGameState("DRAW");
//                client.setGame(null);
//            }
//        }
//    }
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
            String newLine = in.nextLine();
            if(!ignoreList.contains(newLine)) {
                parse(newLine);
            }
            if(!commandQueue.isEmpty()){
                out.println(commandQueue.get(0));
                commandQueue.remove(commandQueue.get(0));
                out.flush();
            }
        }
    }
}
