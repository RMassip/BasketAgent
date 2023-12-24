package Model;

public class Joueur {
    private String nomJ;
    private String prenomJ;
    private int statGen;
    private int statPot;
    private int statAthle;
    private int statPhysi;
    private int statTechni;
    private int statRela;
    private int opinion;


    public Joueur(){
        
    }


    public String getNomJ() {
      return this.nomJ;
    }
    public void setNomJ(String value) {
      this.nomJ = value;
    }

    public String getPrenomJ() {
      return this.prenomJ;
    }
    public void setPrenomJ(String value) {
      this.prenomJ = value;
    }

    public int getStatGen() {
      return this.statGen;
    }
    public void setStatGen(int value) {
      this.statGen = value;
    }

    public int getStatPot() {
      return this.statPot;
    }
    public void setStatPot(int value) {
      this.statPot = value;
    }

    public int getStatAthle() {
      return this.statAthle;
    }
    public void setStatAthle(int value) {
      this.statAthle = value;
    }

    public int getStatPhysi() {
      return this.statPhysi;
    }
    public void setStatPhysi(int value) {
      this.statPhysi = value;
    }

    public int getStatTechni() {
      return this.statTechni;
    }
    public void setStatTechni(int value) {
      this.statTechni = value;
    }

    public int getStatRela() {
      return this.statRela;
    }
    public void setStatRela(int value) {
      this.statRela = value;
    }

    public int getOpinion() {
      return this.opinion;
    }
    public void setOpinion(int value) {
      this.opinion = value;
    }

    private String GetNom(){
        
        return nomJ;
    }
}
