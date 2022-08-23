public class Main {
    public static void main(String[] args) {

        ChatClient client = new ChatClient( "localhost", 4045 );
        client.execute();
    }
}