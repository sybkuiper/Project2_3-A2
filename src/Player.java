import java.io.IOException;
import java.util.List;

//this class implements all the commands players can use
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

    //logIN to be visible on the gameServer
    public void logIN(String name){client.giveCommand("login " + name); }

    //subscribe to a game so when someoneelse whants to play
    public void subscribe(String game){
        client.giveCommand("subscribe " + game);
    }

    //to close of the server
    public void logout(){client.giveCommand("logout");}

    //give up a game
    public void forfeit(){client.giveCommand("forfeit");}

    //make a move in a game
    public void move(String move){client.giveCommand("move " + move); }

    //get all the responses of the server
    public List<String> getServerResponse(){return client.getServerResponses();}

    public Client getClient() {
        return client;
    }
}
