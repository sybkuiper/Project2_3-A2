package Controller;

import Model.Reversi;
import Model.TicTacToe;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;


/**
 * The network controller for the game client handles all incoming and outbound traffic related to the application.
 *
 * Used sources:
 *      https://cs.lmu.edu/~ray/notes/javanetexamples/
 *      https://stackoverflow.com/questions/26485964/how-to-convert-string-into-hashmap-in-java
 *      https://stackoverflow.com/questions/7347856/how-to-convert-a-string-into-an-arraylist
 *      https://stackoverflow.com/questions/9588516/how-to-convert-string-list-into-list-object-in-java
 *
 * @author WJSchuringa && JasperSikkema
 * @version 1.0
 */
public class NetworkController extends Thread {

    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private List<String> ignoreList;
    private List<String> onlinePlayers;
    private ViewController controller;


    public NetworkController(ViewController controller,String player,String ip_address, int port) throws IOException, InterruptedException {
        this.controller = controller;
        socket = new Socket(ip_address,port);
        ignoreList = new ArrayList<>();
        onlinePlayers = new ArrayList<>();
        ignoreList.add("Strategic Game Server Fixed [Version 1.1.0]");
        ignoreList.add("(C) Copyright 2015 Hanzehogeschool Groningen");
        ignoreList.add("OK");
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(),true);
        logIN(player);
        getPlayerList();
    }

    public void disconnect() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public void addToCommandQueue(String command){
        System.out.println(command);
        out.println(command);
        out.flush();
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
                onlinePlayers = new ArrayList<>();
                onlinePlayers.addAll(Arrays.asList(split));
                controller.setOnlinePlayers(onlinePlayers);
            } else {
                onlinePlayers = new ArrayList<>();
                onlinePlayers.add(input);
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
            input = input.replace("SVR GAME MATCH ", "");
            System.out.println("Initializing game....");
            Map<String, String> map = createMap(input);
            System.out.println("GameType " +map.get("GAMETYPE").replace("\"", ""));
            System.out.println("PlayerOne " + map.get("PLAYERTOMOVE").replace("\"", ""));
            System.out.println("Opponent " +map.get("OPPONENT").replace("\"", ""));
            controller.initializeGame(map.get("GAMETYPE").replace("\"", ""), map.get("PLAYERTOMOVE").replace("\"", ""),map.get("OPPONENT").replace("\"", ""));
            for(int move : controller.getGame().getLegalMoves(controller.getGame().getGameBoard(), map.get("PLAYERTOMOVE").replace("\"", ""))){
                controller.updateGrid(move, "legalMove");
            }
        }
        //Todo: Start game interface

        if(input.startsWith("SVR GAME MOVE ")){
            input = input.replace("SVR GAME MOVE ", "");
            controller.hideLegalMoves();
            if(controller.getGame() instanceof TicTacToe) {
                controller.getGame().updateGameBoard(Integer.parseInt(createMap(input).get("MOVE").replace("\"", "")), createMap(input).get("PLAYER").replace("\"", ""));
            } else {
//                System.out.println(controller.getGame());
                //controller.getGame().updateBoard(controller.getGame().getGameBoard(),Integer.parseInt(createMap(input).get("MOVE").replace("\"", "")) ,"B");
                controller.getGame().updateGameBoard(Integer.parseInt(createMap(input).get("MOVE").replace("\"", "")) ,createMap(input).get("PLAYER").replace("\"", ""));
            }
            }

        if (input.startsWith("SVR GAME YOURTURN ")) {
            System.out.println(controller.getGame());

            move(Integer.toString(controller.getGame().think(controller.getGame().getGameBoard())));
        }

        if(input.startsWith("SVR GAME ")){
            input = input.replace("SVR GAME ","");
            if(input.startsWith("WIN")){
                System.out.println(input);
                controller.alertGameState("WIN");
                //Todo: verwerken reactie spel, hoe? testen
            } else if (input.startsWith("LOSS")){
                controller.alertGameState("LOSS");
            } else if (input.startsWith("DRAW")){
                controller.alertGameState("DRAW");
            }
        }
    }

    public void run(){
        while(true){
            String newLine = in.nextLine();
            if(!ignoreList.contains(newLine)) {
                parse(newLine);
            }
        }
    }
}
