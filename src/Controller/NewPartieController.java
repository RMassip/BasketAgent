package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DataBase.SQLiteDatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewPartieController {

    @FXML
    private Label AgencieName;

    @FXML
    private Label PersoFirstName;

    @FXML
    private Label PersoName;

    @FXML
    private TextField TextAgencieName;

    @FXML
    private TextField TextPersoFirstName;

    @FXML
    private TextField TextePersoName;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnContinue;

    @FXML
    void btnBackOnClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/Accueil.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnContinueOnClick(ActionEvent event) {
        // Récupérer la valeur du champ
        String nameAgence = TextAgencieName.getText();
        String name = TextePersoName.getText();
        String firstname = TextPersoFirstName.getText();

        // Insertion dans la base de données
        Connection connection = null;
        try {
            connection = SQLiteDatabaseManager.connect(true);

            // Préparer la requête d'insertion
            String insertQuery = "INSERT INTO Partie (nameAgence_Partie,firstname_Partie,name_Partie) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, nameAgence);
                preparedStatement.setString(2, firstname);
                preparedStatement.setString(3, name);

                // Exécuter la requête d'insertion
                preparedStatement.executeUpdate();

                btnBack();
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'erreur d'insertion
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Gérer l'erreur de connexion
        } finally {
            // Fermer la connexion dans le bloc finally
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'erreur de fermeture de la connexion
            }
        }
    }

    @FXML
    void btnBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/Accueil.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnContinue.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
