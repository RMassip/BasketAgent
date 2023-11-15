package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Partie {
    private final StringProperty nameAgence;
    private final int id;

    public Partie(String nameAgence, int id) {
        this.nameAgence = new SimpleStringProperty(nameAgence);
        this.id = id;
    }

    public Partie(String nameAgence) {
        this.nameAgence = new SimpleStringProperty(nameAgence);
        this.id = 0;
    }

    public String getNameAgence() {
        return nameAgence.get();
    }

    public StringProperty nameAgenceProperty() {
        return nameAgence;
    }

    public void setNameAgence(String nameAgence) {
        this.nameAgence.set(nameAgence);
    }

    public int getId() {
        return id;
    }
}
