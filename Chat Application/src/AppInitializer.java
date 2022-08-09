import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import static javafx.application.Application.launch;

public class AppInitializer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

       /* primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/UserLoginForm.fxml"))));
        primaryStage.setResizable(false);
        //primaryStage.getIcons().add(new Image("location"));
        primaryStage.setTitle("Group Chat Application");
        primaryStage.centerOnScreen();
        primaryStage.show();*/
        URL resource = getClass().getResource("view/UserLoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Group Chat Application");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }


}
