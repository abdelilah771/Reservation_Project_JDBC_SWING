package Entities;

public class Chambre {
    private int id;
    private String telephone;
    private Categorie categorie;

    public Chambre() {
    }

    public Chambre(int id, String telephone, Categorie categorie) {
        this.id = id;
        this.telephone = telephone;
        this.categorie = categorie;
    }

    public Chambre(int i, String s) {
    }

    public int getId() {
        return id;
    }

    public String getTelephone() {
        return telephone;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
