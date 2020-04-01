import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class serverResponseThread extends Thread{
    private BufferedReader reader;
    private Socket socket;
    private List<String> serverResponses;

    public serverResponseThread(Socket socket){
        this.socket = socket;
        this.serverResponses = new ArrayList<>();

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        }catch (IOException e){
            System.out.println("error, no inputstream " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void run(){
        while(true){
            try{
                String response = reader.readLine();
                serverResponses.add(0, response);
            }catch (IOException e){
                System.out.println("Error, no reading from server " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }

    public List<String> getServerResponses() {
        return serverResponses;
    }
}
