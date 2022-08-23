import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread{
private BufferedReader reader;
private Socket socket;
private ChatClient client;

public ReadThread(Socket socket, ChatClient client){
    this.socket = socket;
    this.client = client;

    try {
        reader = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
    }catch (IOException e){
        System.out.println("Error getting input " + e.getMessage());
        e.printStackTrace();
    }
}
public void run(){
    while (true){
        try {
            String response = reader.readLine();
            System.out.println("\n" + response);

            if (client.getUserName() != null){
                System.out.println("[" + client.getUserName() + "]: ");
            }

        }catch (IOException ex){
            System.out.println("Error reading from the server");
            break;
        }
    }
}

}
