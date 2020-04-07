import java.io.IOException;
import java.util.List;

public class Player {
    Client client;
    public boolean myTurn = false;

    Player(String ip, int port){
        setup(ip, port);
    }

    public void setup(String ip, int port){
        this.client = new Client(ip, port);
        client.serverConnect();

    }

    public void logIN(String name){client.giveCommand("login " + name); }

    public void subscribe(String game){
        client.giveCommand("subscribe " + game);
    }

    public void logout(){client.giveCommand("logout");}

    public void forfeit(){client.giveCommand("forfeit");}

    public void move(String move){client.giveCommand("move " + move); }

    public List<String> getServerResponse(){return client.getServerResponses();}

    public Client getClient() {
        return client;
    }
}
