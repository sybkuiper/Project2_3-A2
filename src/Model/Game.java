//package Model;
//
//import Players.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class Game {
//
//    Game game;
//    Player player1;
//    Player player2;
//    private Map<String,String> gameBoard = new HashMap<String,String>(); //game map
//
//    public Game(){
//
//    }
//
//    public Game(String gameType, Player player1, Player player2, boolean online){
//        switch(gameType){
//            case "Tic-tac-toe":
//                game = new TicTacToe(player1);
//                //player1.setActiveGame(game);
//                if(!online){
//                    addSecondPlayer(player2);
//                //   player2.setActiveGame(game);
//                }
//                break;
//            case "Othello":
//                game = new Reversi();
//                break;
//        }
//    }
//
//    public void startGame(String gameType, Player robot, boolean online){
//        setGame(new Game(gameType, player, robot, online));
//    }
//
//    public void startGame(String gameType, boolean online){
//        setGame(new Game(gameType, player,null , online));
//    }
//
//
//    public Game getGame() {
//        return game;
//    }
//
//    private void addSecondPlayer(Player player){
//        player2 = player;
//    }
//
//    public Map<String, String> getGameBoard() {
//        return gameBoard;
//    }
//
//    public void makeMove(Player player){
//    }
//}
