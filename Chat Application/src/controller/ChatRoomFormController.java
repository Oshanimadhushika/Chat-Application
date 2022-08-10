package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatRoomFormController extends Thread {
    public AnchorPane ChatRoomContext;
    public JFXTextField txtTypeMessage;
    public Button btnSend;
    public ImageView emojiIcon;
    public ImageView cameraIcon;
    public JFXTextArea txtArea;
    public Label lblName;

    BufferedReader reader;
    PrintWriter writer;
    Socket socket;

    public void initialize() throws IOException {
        String userName=UserLoginFormController.userName;
        lblName.setText(String.valueOf(userName));
        try {
            socket = new Socket("localhost", 5001);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        try {
            while (true) {

                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println(cmd);
                // txTextArea.appendText(cmd+"\n");
                StringBuilder fullMsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fullMsg.append(tokens[i]);
                }
                System.out.println(fullMsg);
                //txTextArea.appendText(cmd+" "+fullMsg+"\n");
//                if (cmd.equalsIgnoreCase("Client" + ":")) {
//                    continue;
//                } else if (fullMsg.toString().equalsIgnoreCase("bye")) {
//                    break;
//                }

                System.out.println("cmd="+cmd+"-----"+"UserName"+" "+lblName.getText());
                if(!cmd.equalsIgnoreCase(lblName.getText()+":")){
                    txtArea.appendText(msg + "\n");
                }

            }
//            reader.close();
//            writer.close();
//            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SendOnAction(ActionEvent actionEvent) {
        String msg = txtTypeMessage.getText();
        writer.println(lblName.getText() + ": " + txtTypeMessage.getText());
        txtArea.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        txtArea.appendText("YOU : " +" "+ msg + "\n");
        txtTypeMessage.clear();
        if(msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
//            Stage stage = (Stage) txtTextMsg.getScene().getWindow();
//            stage.close();
        }
    }

    public void Emoji_On_Mouse_Click(MouseEvent mouseEvent) {
    }

    public void Camera_On_Mouse_Clicked(MouseEvent mouseEvent) {

    }
}
