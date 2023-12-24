package Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class FindJoueurControlleur {

    @FXML
    private Button BtnScout;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnfind;

    @FXML
    private TableView<?> tabfindjoueur;

    @FXML
    public void initialize(){
        
    }

    @FXML
    void OnClickBtnScout(ActionEvent event) {

    }

    @FXML
    void OnClickbtnfind(ActionEvent event) {

    }

    @FXML
    void OnclickbtnBack(ActionEvent event) {

        try {
            
            //sauvegarde des données dans la bd.

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/Principale.fxml"));
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
