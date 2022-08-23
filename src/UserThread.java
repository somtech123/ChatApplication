import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserThread extends Thread{
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;

    public UserThread(Socket socket, ChatServer server){
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader =
                    new BufferedReader( new InputStreamReader( socket.getInputStream()  ) );
            writer = new PrintWriter( socket.getOutputStream(), true );

            printUsers();

            String userName = reader.readLine();
            server.addUserName(userName);

            String serverMessage = "New User connected: " + userName;
            server.broadcast(serverMessage, this);

            String clientMessage;
            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]: " + clientMessage;
                server.broadcast(serverMessage, this);
            }while (!clientMessage.equals( "bye" ));


        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    void printUsers(){
        if (server.hasUsers()){
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }
    void sendMessage(String message){
        writer.println(message);
    }
}
