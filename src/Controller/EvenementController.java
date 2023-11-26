package Controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class EvenementController {

    @FXML
    private Button btnN1;

    @FXML
    private Button btnN2;

    @FXML
    private Label texEvent;

    int choix = 0 ;

    @FXML
    public void initialize() {

        List<String> ListEvent = new ArrayList<String>();
        ListEvent.add("Images Comprométente");
        ListEvent.add("offre de sponsor");
        ListEvent.add("Blessure");

        /* choix en pourcentage */
        double choixdouble = (Math.random()*100);
        int choixint = (int) choixdouble;
        //System.out.println(choixint+"ème %");

        /* division des pourcent en trache par le nombre de choix */
        double pourcent = 100/ListEvent.size();
        int tranche = (int) pourcent;
        //System.out.println(tranche+"% par tranche");

        int num = 1;
        boolean find = false;

        while (find == false) {
            if (choixint <= tranche*num) {
                choix = num;
                find = true;
            }else{
                num += 1;
            }
        }


        switch (choix) {
            case 1:
                texEvent.setText(ListEvent.get(0));
                btnN1.setText("Payer pour faire taire \n le journaliste \n Argent -10000$ ");
                btnN2.setText("Ne rien faire. \n Relation joueur -10 \n Réputation -1");
                break;
            case 2:
                // idée argent sale et donc blanchiment.
                texEvent.setText(ListEvent.get(1));
                btnN1.setText("Accepter. +100000$");
                btnN2.setText("Refuser.");
                break;
            case 3:
                texEvent.setText(ListEvent.get(2));
                btnN1.setText("Payer pour ces soins. \n Argent : -10000$ \n Blésser pendant 2 semaines");
                btnN2.setText("Ne rien faire. \n Relation joueur -10 \n Réputation -1 \n Blésser pendant 4 semaines");
                break;
        }
    }

    @FXML
    void OnClickbtnN1(ActionEvent event) {

        if(choix!= 0){

            int id =0;
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

            switch (choix) {
                case 1:
                    updateBD(id, 10000, 0, true, false);
                    break;
                case 2:
                    updateBD(id, 100000, 0, false, false);
                    break;
                case 3:
                    updateBD(id, 10000, 0, true, false);
                    break;
            }
        }

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/Principale.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnN1.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void OnClickbtnN2(ActionEvent event) {

        if(choix!= 0){

            int id =0;
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

            switch (choix) {
                case 1:
                    updateBD(id, 0, 1, false, true);
                    break;
                case 2:
                    //updateBD(id, 100000, 0, false, false);
                    break;
                case 3:
                    updateBD(id, 0, 1, false, true);
                    break;
            }
        }

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/Principale.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnN2.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBD(int id,int argent,int reputation,boolean Bargent,boolean Breputation){

        Connection connection = getConnection();

        String verifQuery ="SELECT argent, reputation FROM Partie WHERE id_Partie = ?";

        int argentAct = 0;
        int reputationAct = 0;

        try{
            PreparedStatement select = connection.prepareStatement(verifQuery);
            select.setInt(1, id);
            ResultSet resultSet = select.executeQuery();
            argentAct = resultSet.getInt("argent");
            reputationAct = resultSet.getInt("reputation");

        } catch (Exception e) {
            e.printStackTrace();
        }


        String updateQuery = "UPDATE Partie SET argent = ?, reputation = ? WHERE id_Partie = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            int argentfin = 0;
            int reputationfin = 0;

            if(Bargent == false){
                argentfin = argentAct + argent;  
            }else{
                argentfin = argentAct - argent;  
            }

            if (Breputation == false) {
                reputationfin = reputationAct + reputation;  
            }else{
                reputationfin = reputationAct - reputation;  
            }

            preparedStatement.setInt(1, argentfin);
            preparedStatement.setInt(2, reputationfin);
            preparedStatement.setInt(3, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Bd mise à jour.");
            } else {
                System.out.println("Aucune partie mise à jour. Vérifiez l'ID de la partie.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur d'exécution de la requête UPDATE
        }
        closeConnection(connection);
    }

    public Connection getConnection(){
        try{
            Connection connection = SQLiteDatabaseManager.connect(false);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

