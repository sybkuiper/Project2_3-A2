package Players;

import Hanze.GameClient;

public class Human extends Player {
    String name;
    GameClient client;

    public Human(GameClient client, String name){
        this.name = name;
        this.client = client;
        client.ConnectToServer(this);
        client.getServerCommunication(this).logIN(name);
    }

    public String getName() {
        return name;
    }
}
