package Players;

import Hanze.GameClient;
import Hanze.ServerCommunication;

public abstract class Player {
    private GameClient client;
    private String name;
    private ServerCommunication serverConnection;

    public Player(GameClient client, String name, ServerCommunication serverConnection){
        this.client = client;
        this.name = name;
        this.serverConnection = serverConnection;
    }

    public ServerCommunication getServerConnection() {
        return serverConnection;
    }

    public GameClient getClient() {
        return client;
    }

    public String getName() {
        return name;
    }
}
