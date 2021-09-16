package ui;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

// goggame-1971477531.ico Img from GWENT: the witcher card game.
//https://stackoverflow.com/questions/10121991/javafx-application-icon for Icon sample
// https://www.geeksforgeeks.org/javafx-button-with-examples/ for startup.

public class Main extends Application {
    Parent root;
    @Override
    public void init() throws Exception {
        root = FXMLLoader.load(getClass().getResource("DeckBuilderFX.fxml"));
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Gwent DeckBuilder");
        primaryStage.setScene(new Scene(root));
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


    public static void main(String[] args) {
        LauncherImpl.launchApplication(Main.class, MyPreloader.class, args);
    }
}
