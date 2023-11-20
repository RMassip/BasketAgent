package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Partie {
    private final StringProperty nameAgence;
    private final String firstname_Partie;
    private final String name_Partie;
    private final int id;
    private int argent;
    private int reputation;
    private int week;
    private int year;

    public Partie(String nameAgence, int id,String firstname_Partie,String name_Partie, int argent, int reputation,int week,int year ) {
        this.nameAgence = new SimpleStringProperty(nameAgence);
        this.id = id;
        this.argent = argent;
        this.reputation = reputation;
        this.firstname_Partie = firstname_Partie;
        this.name_Partie = name_Partie;
        this.week = week;
        this.year = year;
    }

    public Partie(String nameAgence) {
        this.nameAgence = new SimpleStringProperty(nameAgence);
        this.id = 0;
        this.argent = 0;
        this.reputation = 0;
        this.firstname_Partie = "";
        this.name_Partie = "";
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

    public void setArgent(int argent){
        this.argent = argent;
    }

    public int getArgent(){
        return argent;
    }

    public void setReputation(int reputation){
        this.reputation = reputation;
    }

    public int geteputation(){
        return reputation;
    }

    public void setWeek(int week){
        this.week = week;
    }

    public int getWeek(){
        return week;
    }

    public void setYear(int year){
        this.year = year;
    }
    
    public int getYear(){
        return year;
    }
}
