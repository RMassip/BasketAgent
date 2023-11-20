import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    static {
        // Configurer le niveau de journalisation global (par exemple, Level.INFO pour tout afficher)
        Logger.getLogger("").setLevel(Level.INFO);

        // Créer un gestionnaire de console
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);

        // Associer le gestionnaire de console à tous les loggers
        Logger.getLogger("").addHandler(consoleHandler);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("FXML/Accueil.fxml"));
            Scene scene = new Scene(root);

            primaryStage.setTitle("Basket Agent");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
