package stud.mi;

import stud.mi.server.ChatServer;

public class Application {

    private Application() {
    }

    public static void main(String[] args) {
        String port = System.getenv("PORT");
        final ChatServer chatServer = new ChatServer(Integer.parseInt(port));
        chatServer.start();
    }
}
