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
    private List<String> availableGames;
    private List<String> onlinePlayers;
    private boolean isRunning = false;

    public NetworkController(String ip_address, int port) throws IOException {
        socket = new Socket(ip_address,port);
        commandQueue = new ArrayList<>();
        ignoreList = new ArrayList<>();
        availableGames = new ArrayList<>();
        ignoreList.add("Strategic Game Server Fixed [Version 1.1.0]");
        ignoreList.add("(C) Copyright 2015 Hanzehogeschool Groningen");
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(),true);
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

    public void getGameList(){
        addToCommandQueue("get gamelist");
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

    private void parse(String input){
        if(input.startsWith("SVR GAMELIST ")){
            input = input.replace("SVR GAMELIST ","").replace("[","").replace("]","");
            String[] split = input.split(",");
            availableGames.addAll(Arrays.asList(split));
        }
        if(input.startsWith("SVR PLAYERLIST ")){
            input = input.replace("SVR PLAYERLIST ","").replace("[","").replace("]","");
            String[] split = input.split(",");
            onlinePlayers.addAll(Arrays.asList(split));
        }

        if(input.startsWith("SVR GAME CHALLENGE ")){
            if(input.startsWith("SVR GAME CHALLENGE CANCELLED ")){
                input = input.replace("SVR GAME CHALLENGE CANCELLED {CHALLENGENUMBER: ","").replace("}","");
                System.out.println("Challenge " + input + " is geannuleerd");
                //Todo: controller call die aangeeft dat de match uitnodiging voor ID is verlopen
            } else {
                input = input.replace("SVR GAME CHALLENGE ", "");
                createMap(input).get("CHALLENGENUMBER").replace("\"", "");
                createMap(input).get("GAMETYPE").replace("\"", "");
            }
        }

        if(input.startsWith("SVR GAME MATCH ")){

            input = input.replace("SVR GAME MATCH ", "");
          //  if(player instanceof Human && player.getClient().getGame() == null) {
          //      client.startGame(createMap(input).get("GAMETYPE").replace("\"",""), true);
            }
            //Todo: Start game interface
        }
     //   if(input.startsWith("SVR GAME YOURTURN ")){
     //       if(player.getClient().getGame() != null) {
     //           player.getClient().getGame().getGame().makeMove(player); //Todo: makeMove() function, where the AI should automatically make the best move, and the player should have the ability to move.
    //        }
     //   }

//        if(input.startsWith("SVR GAME ")){
//            input = input.replace("SVR GAME ","");
//            if(input.startsWith("WIN")){
//                if(player instanceof Human) {
//                    //Todo: send alert that player won (interface call) + ability to request rematch
//                }
//                System.out.println(player.getName() + " : has won");
//                client.setGame(null);
//                //Todo: verwerken reactie spel, hoe? testen
//            } else if (input.startsWith("LOSS")){
//                if(player instanceof Human) {
//                    //Todo: send alert that player lost (interface call) + ability to request rematch
//                }
//                System.out.println(player.getName() + " : has lost");
//                client.setGame(null);
//            } else if (input.startsWith("DRAW")){
//                if(player instanceof Human) {
//                    //Todo: send alert that player tied (interface call) + ability to request rematch
//                }
//                System.out.println(player.getName() + " : has tied");
//                client.setGame(null);
//            }
//        }
//    }


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
