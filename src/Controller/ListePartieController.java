package Controller;

import Model.Partie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DataBase.SQLiteDatabaseManager;

public class ListePartieController {

    @FXML
    private TableView<Partie> TabSav;

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<Partie, Void> colDelete;

    @FXML
    private TableColumn<Partie, String> colNameAgence;

    private ObservableList<Partie> partiesList = FXCollections.observableArrayList();

    @FXML
    void btnBackOnClicks(ActionEvent event) {
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
    public void initialize() {
        // Initialiser les colonnes
        colNameAgence.setCellValueFactory(cellData -> cellData.getValue().nameAgenceProperty());
    
        // Ajouter la colonne de suppression
        colDelete.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("X");
    
            {
                deleteButton.setPrefWidth(46.399993896484375); // Ajustez la largeur du bouton ici
    
                deleteButton.setOnAction(event -> {
                    Partie partie = getTableView().getItems().get(getIndex());
                    deletePartie(partie);
                });
            }
    
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    
        // Charger les données depuis la base de données
        loadPartiesData();
    }

    private void loadPartiesData() {
        // Connexion à la base de données
        try (Connection connection = SQLiteDatabaseManager.connect()) {
            // Exécuter la requête pour récupérer les parties
            String selectQuery = "SELECT nameAgence_Partie FROM Partie";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Ajouter les parties à la liste
                while (resultSet.next()) {
                    Partie partie = new Partie(
                            resultSet.getString("nameAgence_Partie")
                    );
                    partiesList.add(partie);
                }

                // Assigner la liste au TableView
                TabSav.setItems(partiesList);

            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'erreur d'exécution de la requête
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Gérer l'erreur de connexion
        }
    }

    private void deletePartie(Partie partie) {
        // Connexion à la base de données
        try (Connection connection = SQLiteDatabaseManager.connect()) {
            // Exécuter la requête DELETE
            String deleteQuery = "DELETE FROM Partie WHERE nameAgence_Partie = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setString(1, partie.getNameAgence());
    
                // Exécuter la requête de suppression
                int rowsDeleted = preparedStatement.executeUpdate();
    
                if (rowsDeleted > 0) {
                    // Si au moins une ligne a été supprimée de la base de données,
                    // alors supprimez également la partie de la liste
                    partiesList.remove(partie);
                    // Rafraîchissez votre TableView
                    TabSav.refresh();
                } else {
                    System.out.println("Aucune partie supprimée. Vérifiez le nom de l'agence.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'erreur d'exécution de la requête DELETE
            }
    
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Gérer l'erreur de connexion
        }
    }

    
    @FXML
    private void Dolancement(MouseEvent event) {
        
    }
}
