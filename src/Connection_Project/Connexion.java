package Connection_Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private static final String url = "jdbc:mysql://localhost:3306/my_db";
    private static final String user = "root";
    private static final String password = "";
    private static Connection cnx = null; // Doit être de type java.sql.Connection

    static {
        try {
            // Charger le driver JDBC pour MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Pilote chargé");

            // Établir la connexion à la base de données
            cnx = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion à la BD réussie");

        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la BD : " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur de chargement du pilote : " + e.getMessage());
        }
    }

    public static Connection getCnx() { // Retourne un objet de type java.sql.Connection
        return cnx;
    }
}
