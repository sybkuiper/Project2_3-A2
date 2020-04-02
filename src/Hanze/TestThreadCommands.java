package Hanze;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TestThreadCommands extends Thread {
    public TestThreadCommands(ServerCommunication socket) throws InterruptedException, IOException {
        Thread.sleep(5000);
        //PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        socket.logIN("johan");
        Thread.sleep(5000);
        socket.subscribe("Tic-tac-toe");
    }
}
