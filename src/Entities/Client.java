package Entities;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String username;
    private String password;
    private static int count=0;

    public Client(int id, String nom, String prenom, String telephone, String email, String username, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Client() {

    }

    public Client(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Client [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", telephone=" + telephone + ", email="
                + email + ", username=" + username + "]";
    }
}
