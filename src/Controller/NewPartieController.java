package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
        String valeurAgence = TextAgencieName.getText();
        String name = TextePersoName.getText();
        String firstname = TextPersoFirstName.getText();

        //insertion base de données
    }

}