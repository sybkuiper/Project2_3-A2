package Hanze;

import Players.Player;

public class TestThread extends Thread {

    Player player;

    public TestThread(Player player){
        this.player = player;
        this.start();
    }

    @Override
    public void run() {
//        player.getServerConnection().challenge("Test","Tic-tac-toe");
//        player.getServerConnection().challenge(player.getClient().getPlayerObjects().get("Tic-tac-toe").getName(), "Tic-tac-toe");
    }
}
