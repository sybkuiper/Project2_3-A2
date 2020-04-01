import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

/**
 * This is the main class for the client application of Van Den Broek Spelen & Vermaak
 *
 * @author JasperSikkema, WJSchuringa
 */

public class Main {
    private final static int PORT = 7789;

    public static void main(String[] args) throws IOException, InterruptedException {

        String ip = InetAddress.getLocalHost().getHostAddress();
        System.out.println(ip);

        new TicTacToe(new Human(ip, PORT, "kees"),
                      new Robot(ip, PORT,0, "Tic-tac-toe"));
    }
}
