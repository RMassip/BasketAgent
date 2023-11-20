package Controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import DataBase.SQLiteDatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PrincipaleController {

    @FXML
    private Label labWeek;

    @FXML
    private Label labYear;
    
    @FXML
    private Label labargent;

    @FXML
    private Label labnameAgence;

    @FXML
    private Label labrepu;

    @FXML
    private Button btnBack;

    @FXML
    public void initialize() {

        final Logger LOGGER = Logger.getLogger(PrincipaleController.class.getName());
        int id = 0;

        try (Connection connection = SQLiteDatabaseManager.connect()) {
            //recup json
            JSONParser jsonP = new JSONParser();
            try {
                JSONObject jsonO = (JSONObject)jsonP.parse(new FileReader("src/Save/save.json"));
            
                id = ((Long) jsonO.get("partie")).intValue();
            } catch (FileNotFoundException e) { 
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

            LOGGER.log(Level.INFO, "ID récupéré depuis le JSON : {0}", id);
            
            // Exécuter la requête pour récupérer l'ID
            String selectQuery = "SELECT nameAgence_Partie,argent,reputation,week,year FROM Partie WHERE id_Partie = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {

                        String nameAgence = resultSet.getString("nameAgence_Partie");
                        String argent = resultSet.getString("argent");
                        String reputation = resultSet.getString("reputation");
                        String week = resultSet.getString("week");
                        String year = resultSet.getString("year");

                        /* 
                        System.out.println("nameAgence: " + nameAgence);
                        System.out.println("argent: " + argent);
                        System.out.println("reputation: " + reputation);
                        */

                        labnameAgence.setText(nameAgence);
                        labargent.setText(argent+" $");
                        labrepu.setText(reputation);
                        labWeek.setText(week+"/52");
                        labYear.setText(year);

                    }else{
                        System.out.println("erreur Requète");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'erreur d'exécution de la requête
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Gérer l'erreur de connexion
        }
    }

    @FXML
    void btnBackOnClicks(ActionEvent event) {
        try {
            
            //sauvegarde des données dans la bd.

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
}
