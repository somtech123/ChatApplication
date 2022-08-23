import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    private String hostname;
    private int port;
    private String userName;

    public ChatClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void execute(){
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the chat server");

            new ReadThread( socket, this ).start();
            new WriterThread( socket, this ).start();


        }catch (UnknownHostException ex){
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException e){
            System.out.println("error " + e.getMessage());
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
