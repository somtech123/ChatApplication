import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriterThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;

    public WriterThread(Socket socket, ChatClient client){
        this.socket = socket;
        this.client = client;

        try {
            writer = new PrintWriter( socket.getOutputStream(), true );

        }catch (IOException ex){
            System.out.println("Error getting output stream: " + ex.getMessage());
        }
    }

    public void run(){
        Scanner scanner = new Scanner( System.in );

        System.out.println("\nEnter your name: ");
        String userName = scanner.nextLine();
        client.setUserName(userName);
        writer.println(userName);

        String text;
        do {
            System.out.println("[" + userName + "]: ");
            text = scanner.nextLine();
            writer.println(text);
        }while (!text.equals( "bye" ));
        try {
            System.out.println("disconnected from chat server");
            socket.close();
        }catch (IOException e){
        }
    }

}
