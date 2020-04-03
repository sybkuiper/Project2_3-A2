package Players;

import Hanze.GameClient;
import Hanze.ServerCommunication;

import java.io.IOException;

public class Human extends Player {
    public Human(GameClient client, String name) throws IOException, InterruptedException {
        super(client,name,new ServerCommunication(client,name));
        getServerConnection().logIN(super.getName());
        getServerConnection().getGameList();
        getServerConnection().getPlayerList();

    }
}
