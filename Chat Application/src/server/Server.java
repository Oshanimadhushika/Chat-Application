package server;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket=new ServerSocket(5001);
        Socket accept;
        BufferedImage image = ImageIO.read(new File("This PC\\Pictures"));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);

        while (true){

            System.out.println("Waiting for Client ...");
            accept= serverSocket.accept();
            System.out.println("Client Connected");
            ClientHandler clientThread = new ClientHandler(accept, clients);
            clients.add(clientThread);
            clientThread.start();
        }
    }
}
