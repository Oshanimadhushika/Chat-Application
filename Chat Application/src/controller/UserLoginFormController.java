package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UserLoginFormController {
    public AnchorPane LoginFormContext;
    public JFXTextField txtUserName;
    public Button btnLogIN;

    static String userName;

    public void LogOnAction(ActionEvent actionEvent) throws IOException {
        userName=txtUserName.getText();
        Stage stage = (Stage) txtUserName.getScene().getWindow();
        stage.close();
        Stage stage1=new Stage();
        stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ChatRoomForm.fxml"))));
        stage1.setResizable(false);
        //primaryStage.getIcons().add(new Image("location"));
        stage1.setTitle("Group Chat Application");
        stage1.centerOnScreen();
        stage1.show();
    }
}
