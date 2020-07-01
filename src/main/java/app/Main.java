package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/connectScene.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Connect");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /*
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/scenes/loginScene.fxml"));
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(new Scene(root, 1067.0, 647.0));
        primaryStage.show();
    }

     */


    public static void main(String[] args) {
        launch(args);

    }
}
