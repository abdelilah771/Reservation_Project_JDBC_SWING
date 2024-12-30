package Service;

import Connection_Project.Connexion;
import Entities.Categorie;
import Entities.Chambre;
import IDAO.Idao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChambreService implements Idao<Chambre> {

    private Connection connection = Connexion.getCnx();

    @Override
    public boolean create(Chambre chambre) {
        String query = "INSERT INTO chamber (telephone, categorie_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, chambre.getTelephone());
            if (chambre.getCategorie() != null) {
                statement.setInt(2, chambre.getCategorie().getId());
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error creating chambre: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Chambre chambre) {
        String query = "UPDATE chamber SET telephone = ?, categorie_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, chambre.getTelephone());
            if (chambre.getCategorie() != null) {
                statement.setInt(2, chambre.getCategorie().getId());
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            statement.setInt(3, chambre.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating chambre: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Chambre chambre) {
        String query = "DELETE FROM chamber WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, chambre.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting chambre: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Chambre findById(int id) {
        String query = "SELECT c.id AS chambre_id, c.telephone AS chambre_telephone, cat.id AS categorie_id, cat.code AS categorie_code, cat.libelle AS categorie_libelle " +
                "FROM chamber c LEFT JOIN categorie cat ON c.categorie_id = cat.id WHERE c.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Categorie categorie = null;
                    if (resultSet.getInt("categorie_id") != 0) {
                        categorie = new Categorie(
                                resultSet.getInt("categorie_id"),
                                resultSet.getString("categorie_code"),
                                resultSet.getString("categorie_libelle")
                        );
                    }
                    return new Chambre(
                            resultSet.getInt("chambre_id"),
                            resultSet.getString("chambre_telephone"),
                            categorie
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding chambre by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Chambre> findAll() {
        List<Chambre> chambres = new ArrayList<>();
        String query = "SELECT c.id AS chambre_id, c.telephone AS chambre_telephone, cat.id AS categorie_id, cat.code AS categorie_code, cat.libelle AS categorie_libelle " +
                "FROM chamber c LEFT JOIN categorie cat ON c.categorie_id = cat.id";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Categorie categorie = null;
                if (resultSet.getInt("categorie_id") != 0) {
                    categorie = new Categorie(
                            resultSet.getInt("categorie_id"),
                            resultSet.getString("categorie_code"),
                            resultSet.getString("categorie_libelle")
                    );
                }
                chambres.add(new Chambre(
                        resultSet.getInt("chambre_id"),
                        resultSet.getString("chambre_telephone"),
                        categorie
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error finding all chambres: " + e.getMessage());
        }
        return chambres;
    }
}
