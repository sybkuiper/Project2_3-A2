package Players;

import Games.Game;
import Hanze.GameClient;
import Hanze.ServerCommunication;

public abstract class Player {
    private GameClient client;
    private String name;
    private ServerCommunication serverConnection;
    //private Game activeGame;

    public Player(GameClient client, String name){
        this.client = client;
        this.name = name;
    }

    //public Game getActiveGame() {
    //    return activeGame;
    //}

    //public void setActiveGame(Game activeGame) {
    //    this.activeGame = activeGame;
    //}

    public void setServerConnection(ServerCommunication serverConnection) {
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
