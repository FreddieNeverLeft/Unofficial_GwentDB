
// https://blog.codecentric.de/en/2015/09/javafx-how-to-easily-implement-application-preloader-2/

package ui;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MyPreloader extends Preloader {
    private Stage preloaderStage;
    private Scene scene;

    @Override
    public void init() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoadingScreen.fxml"));
        scene = new Scene(root);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;
        primaryStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        FileInputStream input = null;
        try {
            input = new FileInputStream("src/main/imgs/gog.png");
        } catch (FileNotFoundException e) {
            System.out.println("file not found.");
        }
        Image i = new Image(input);
        primaryStage.getIcons().add(i);
        primaryStage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
        if (stateChangeNotification.getType() == StateChangeNotification.Type.BEFORE_START) {
            preloaderStage.hide();
        }
    }
}
