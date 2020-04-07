//package Players;
//
//import GameClient;
//import ServerCommunication;
//
//import java.io.IOException;
//
//public class Human extends Player {
//    public Human(GameClient client, String name) throws IOException, InterruptedException {
//        super(client,name);
//        setServerConnection(new ServerCommunication(client,this));
//        getServerConnection().logIN(getName());
//        getServerConnection().getGameList();
//        getServerConnection().getPlayerList();
//
//    }
//}
