package Players;

import Hanze.GameClient;
import Hanze.ServerCommunication;

import java.io.IOException;

public class Human extends Player {
    public Human(GameClient client, String name) throws IOException, InterruptedException {
        super(client,name);
        setServerConnection(new ServerCommunication(client,this));
        getServerConnection().logIN(getName());
        getServerConnection().getGameList();
        getServerConnection().getPlayerList();
//        getServerConnection().challenge(client.getRobots().get("Reversi").getName(), "Reversi");
//        var test = client.getIntelligences().get("Othello").getName();
//        System.out.println(test);
    }
}
