package server;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;


public class Server {
    private static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket=new ServerSocket(5001);

        Socket accept;
       /* Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();

        System.out.println("Reading: " + System.currentTimeMillis());

        byte[] sizeAr = new byte[4];
        inputStream.read(sizeAr);
        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

        byte[] imageAr = new byte[size];
        inputStream.read(imageAr);

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

        System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
        ImageIO.write(image, "jpg", new File("C:\\Users\\Oshani Madhushika\\Pictures\\web design\\Screenshot_20220706_212259.jpg"));

        serverSocket.close();*/

        while (true){

            System.out.println("Waiting for Client ...");
            accept= serverSocket.accept();
            System.out.println("Client Connected");
            ClientHandler clientThread = new ClientHandler(accept, clients);
            clients.add(clientThread);
            clientThread.start();
        }


       /* //socket=new Socket("localhost", 5001);
        InputStream inputStream = socket.getInputStream();
        File input_file=new File("C:\\Users\\Oshani Madhushika\\Pictures");
        BufferedImage image=ImageIO.read(input_file);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ImageIO.write(image,"Jpg",byteArrayOutputStream);
        inputStream.read(byteArrayOutputStream.toByteArray());
        inputStream.flush();

        JOptionPane.showMessageDialog(null,"seat");*/





    }
}
