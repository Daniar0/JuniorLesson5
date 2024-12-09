package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try(Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleReader=new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Connected to chat server! ");
            Thread readerThread = new Thread(() -> {
                try{
                    String message;
                    while ((message=in.readLine())!=null) {
                        System.out.println(message);
                    }
                }catch(IOException e){
                    System.err.println("Connection closed.");
                }
            });
            readerThread.start();
            String userInput;
            while ((userInput=consoleReader.readLine())!=null){
                out.println(userInput);
            }

        }catch (IOException e){
            System.err.println("Unable to connect to chat server." + e.getMessage());
        }
    }
}
