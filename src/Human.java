//this class implements everything a player can do
public class Human extends Player {
    String name;

    Human(String ip, int port, String name){
        super(ip, port);
        this.name = name;
        logIN(name);
    }

    public String getName() {
        return name;
    }
}
