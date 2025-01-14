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
        String query = "INSERT INTO chambre (id, telephone, categorie_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, chambre.getId());
            stmt.setString(2, chambre.getTelephone());
            if (chambre.getCategorie() != null) {
                stmt.setInt(3, chambre.getCategorie().getId());
            } else {
                throw new SQLException("Categorie cannot be null for chambre creation.");
            }
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error creating chambre: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Chambre chambre) {
        String query = "UPDATE chambre SET telephone = ?, categorie_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, chambre.getTelephone());
            if (chambre.getCategorie() != null) {
                stmt.setInt(2, chambre.getCategorie().getId());
            } else {
                throw new SQLException("Categorie cannot be null for chambre update.");
            }
            stmt.setInt(3, chambre.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating chambre: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Chambre chambre) {
        String query = "DELETE FROM chambre WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, chambre.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting chambre: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Chambre findById(int id) {
        String query = "SELECT chambre.*, categorie.id AS cat_id, categorie.code, categorie.libelle " +
                "FROM chambre " +
                "LEFT JOIN categorie ON chambre.categorie_id = categorie.id " +
                "WHERE chambre.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Categorie categorie = new Categorie(
                            rs.getInt("cat_id"),
                            rs.getString("code"),
                            rs.getString("libelle")
                    );
                    return new Chambre(
                            rs.getInt("id"),
                            rs.getString("telephone"),
                            categorie
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding chambre by ID: " + e.getMessage());
        }
        return null;
    }

    public List<Chambre> findAll() {
        List<Chambre> chambres = new ArrayList<>();
        String query = "SELECT chambre.*, categorie.id AS cat_id, categorie.code, categorie.libelle " +
                "FROM chambre " +
                "LEFT JOIN categorie ON chambre.categorie_id = categorie.id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Categorie categorie = new Categorie(
                        rs.getInt("cat_id"),
                        rs.getString("code"),
                        rs.getString("libelle")
                );
                Chambre chambre = new Chambre(
                        rs.getInt("id"),
                        rs.getString("telephone"),
                        categorie
                );
                chambres.add(chambre);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving chambres: " + e.getMessage());
        }
        return chambres;
    }
}
