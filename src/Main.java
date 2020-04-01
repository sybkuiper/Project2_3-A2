import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 7789;
        InetAddress address;
        address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();

        Human human = new Human(ip, port, "kees");
        Robot ticTacToeAI = new Robot(ip,port,0, "Tic-tac-toe");

        new TicTacToe(human, ticTacToeAI);
    }
}
