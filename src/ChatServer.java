import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private int port;
   private Set<String > userNames = new HashSet<>();
   private Set<UserThread> userThreads = new HashSet<>();

    public ChatServer(int port) {
        this.port = port;
    }
    public void execute(){
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Chat app is listing on " + port);
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("new user connected" );

                UserThread newUser = new UserThread( socket, this );
                userThreads.add( newUser );
                newUser.start();
            }

        }catch (IOException e){
            System.out.println("Error in server: " + e.getMessage());
        }
    }
    // sending message across server
    public void broadcast(String message, UserThread excludeUser){
        for (UserThread aUser : userThreads){
            if (aUser != excludeUser){
                aUser.sendMessage( message );
            }
        }
    }

    public void addUserName(String userName){
        userNames.add( userName );
    }

    public void removeUser(String userName, UserThread aUser){
        boolean removed = userNames.remove( userName );
        if (removed){
            userThreads.remove( userName );
            System.out.println("The user " + userName + " quitted");
        }
    }

    public Set<String> getUserNames() {
        return userNames;
    }
    boolean hasUsers(){
        return  !this.userNames.isEmpty();
    }
}
