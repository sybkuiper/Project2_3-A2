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
    //while (true) {
        player.getServerConnection().challenge("test", "Tic-tac-toe");
        //player.getServerConnection().challenge(player.getClient().getRobots().get("Tic-tac-toe").getName(), "Tic-tac-toe");
    //}
    }
}
