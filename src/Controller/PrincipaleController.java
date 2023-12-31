package Controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
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
    private Button btnNext;

    @FXML
    private TableColumn<?, ?> tabJoueur;

    @FXML
    private Button btnFindJoueur;

    @FXML
    public void initialize() {

        final Logger LOGGER = Logger.getLogger(PrincipaleController.class.getName());
        int id = 0;

        try (Connection connection = SQLiteDatabaseManager.connect(false)) {
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

            //LOGGER.log(Level.INFO, "ID récupéré depuis le JSON : {0}", id);
            
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
            
            //sauvegarde des données dans la bd.(pas sur de l'uttilisé pour l'instant)

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

    /*
    * 
    * Multiplier le nombre d'évenement par le nombre de joueur. boucle for d'appel.
    * 
    */
    @FXML
    void OnClickbtnNext(ActionEvent event){

        String[] tabStrings = labWeek.getText().split("/");
        int semaine = Integer.parseInt(tabStrings[0]);
        semaine+=1;
        if(semaine < 53){
            labWeek.setText(semaine+"/52");
        }else{
            labWeek.setText("1/52");
            int annee = Integer.parseInt(labYear.getText());
            annee += 1;
            labYear.setText(annee+"");
        }

        System.out.println(semaine);

        int id = 0;

        try (Connection connection = SQLiteDatabaseManager.connect(false)) {
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

            String updateQuery = "UPDATE Partie SET argent = ?, reputation = ?, week = ?, year = ? WHERE id_Partie = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

                String[] argents = labargent.getText().split(" ");
                int argent = Integer.parseInt(argents[0]);

                int reputation = Integer.parseInt(labrepu.getText());

                int year = Integer.parseInt(labYear.getText());

                preparedStatement.setInt(1, argent);
                preparedStatement.setInt(2, reputation);
                preparedStatement.setInt(3, semaine);
                preparedStatement.setInt(4, year);
                preparedStatement.setInt(5, id);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    /* 
                    System.out.println("La partie a été mise à jour avec succès.");
                    String verifQuery ="SELECT * FROM Partie WHERE id_Partie = ?";

                    try{
                        PreparedStatement preparedStatement2 = connection.prepareStatement(verifQuery);
                        preparedStatement2.setInt(1, id);
                        ResultSet resultSet = preparedStatement2.executeQuery();
                        //while (resultSet.isAfterLast()) {
                        System.out.println(resultSet.getInt("week"));
                        //};
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    */
                } else {
                    System.out.println("Aucune partie mise à jour. Vérifiez l'ID de la partie.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'erreur d'exécution de la requête UPDATE
            }
            
            if(IsEvent() == true){


                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/Event.fxml"));
                    Parent root = loader.load();

                    Scene scene = new Scene(root);

                    Stage stage = (Stage) btnBack.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("Pas d'évènement cette semaine.");
            }
            
        } catch (Exception e) {
                e.printStackTrace();
        }
    }

    public boolean IsEvent(){

        //proba d'un event:
        int isevent = 0; 
        Integer tabProbaEvent[] = new Integer[100];
        for (int i = 0; i < 100; i++) {
            //70 sur 100 de chance pour pas d'évent.
            if(i < 69){
                tabProbaEvent[i] = 0;
            }else{
                tabProbaEvent[i] = 1;
            }
        }

        double choixdouble = (Math.random()*100);
        int choixint = (int) choixdouble;
        if(tabProbaEvent[choixint-1] == 1){
            isevent = 1;
        }else{
            isevent = 0;
        }
        if( isevent == 1){
            return true;
        }else{
            return false;   
        }
    }

    //ouverture de la page d'affichage des joueurs trouvés
    @FXML
    void OnClickbtnFindJoueur(ActionEvent event) {
        try {
            
            //sauvegarde des données dans la bd.

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/FindJoueur.fxml"));
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
    void OnMouseClickTabJoueur(MouseEvent event) {
        System.out.println("Test");
    }
}
