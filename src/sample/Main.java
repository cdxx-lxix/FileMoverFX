package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("File Mover 9000FX");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        String css = this.getClass().getResource("sample.css").toExternalForm();
        scene.getStylesheets().add(css);
        Image icon = new Image("images/icon.PNG");
        primaryStage.getIcons().add(icon);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
