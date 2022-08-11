package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ChatRoomFormController extends Thread {
    public AnchorPane ChatRoomContext;
    public JFXTextField txtTypeMessage;
    public Button btnSend;
    public ImageView cameraIcon;
    //public JFXTextArea txtArea;
    public Label lblName;
    public VBox vbox;

    BufferedReader reader;
    PrintWriter writer;
    Socket socket;
    private FileChooser fileChooser;
    private File filePath;



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
    public void run() {
        try {
            while (true) {


                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];

//                txtTextArea.appendText(cmd+"\n");
                StringBuilder fullMsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fullMsg.append(tokens[i]);
                }


                String[] msgToAr = msg.split(" ");
                String st = "";
                for (int i = 0; i < msgToAr.length - 1; i++) {
                    st += msgToAr[i + 1] + " ";
                }
//======================================================================


                Text text = new Text(st);
                String firstChars = "";
                if (st.length() > 3) {
                    firstChars = st.substring(0, 3);

                }


                if (firstChars.equalsIgnoreCase("img")) {
                    //for the Images

                    st = st.substring(3, st.length() - 1);


                    File file = new File(st);
                    Image image = new Image(file.toURI().toString());

                    ImageView imageView = new ImageView(image);

                    imageView.setFitHeight(150);
                    imageView.setFitWidth(200);


                    HBox hBox = new HBox(10);
                    hBox.setAlignment(Pos.BOTTOM_RIGHT);


                    if (!cmd.equalsIgnoreCase(lblName.getText())) {

                        vbox.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);


                        Text text1 = new Text("  " + cmd + " :");
                        hBox.getChildren().add(text1);
                        hBox.getChildren().add(imageView);

                    } else {
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(imageView);
                        Text text1 = new Text(": Me ");
                        hBox.getChildren().add(text1);

                    }

                    Platform.runLater(() -> vbox.getChildren().addAll(hBox));


                } else {
                    //For the Text
//                    text.setFill(Color.WHITE);
                    //   text.getStyleClass().add("message");
                    TextFlow tempFlow = new TextFlow();

                    if (!cmd.equalsIgnoreCase(lblName.getText() + ":")) {
                        Text txtName = new Text(cmd + " ");
                        txtName.getStyleClass().add("txtName");
                        tempFlow.getChildren().add(txtName);
                    }

                    tempFlow.getChildren().add(text);
                    tempFlow.setMaxWidth(200); //200

                    TextFlow flow = new TextFlow(tempFlow);

                    HBox hBox = new HBox(12); //12

                    //=================================================


                    if (!cmd.equalsIgnoreCase(lblName.getText() + ":")) {

                        //  tempFlow.getStyleClass().add("tempFlowFlipped");
                        //  flow.getStyleClass().add("textFlowFlipped");
                        vbox.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.getChildren().add(flow);

                    } else {
                        // text.setFill(Color.WHITE);
                        // tempFlow.getStyleClass().add("tempFlow");
                        // flow.getStyleClass().add("textFlow");
                        Text text2 = new Text(fullMsg + ": Me");
                        TextFlow flow2 = new TextFlow(text2);
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(flow2);
                    }
                    //  hBox.getStyleClass().add("hbox");
                    Platform.runLater(() -> vbox.getChildren().addAll(hBox));
                }
            }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void SendOnAction(ActionEvent actionEvent) {

       /* String msg = txtTypeMessage.getText();
        writer.println(lblName.getText() + ": " + txtTypeMessage.getText());
        txtArea.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        txtArea.appendText("YOU : " +" "+ msg + "\n");
        txtTypeMessage.clear();
        if(msg.equalsIgnoreCase("BYE") ||(msg.equalsIgnoreCase("Bye") ||(msg.equalsIgnoreCase("bye") ||(msg.equalsIgnoreCase("logout"))))) {
            System.exit(0);
//            Stage stage = (Stage) txtTextMsg.getScene().getWindow();
//            stage.close();
        }*/

            String msg = txtTypeMessage.getText();
            writer.println(lblName.getText() + ": " + txtTypeMessage.getText());

            txtTypeMessage.clear();

            if(msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
                System.exit(0);

            }
    }

    public void Emoji_On_Mouse_Click(MouseEvent mouseEvent) {
    }

    public void Camera_On_Mouse_Clicked(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = fileChooser.showOpenDialog(stage);
        writer.println(lblName.getText() + " " + "img" + filePath.getPath());
    }
}
