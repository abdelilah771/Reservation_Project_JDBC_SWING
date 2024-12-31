package Service;

import Connection_Project.Connexion;
import Entities.Categorie;
import Entities.Chambre;
import IDAO.Idao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChambreService implements Idao<Chambre> {

    private CategorieService categorieService = new CategorieService();

    @Override
    public boolean create(Chambre chambre) {
        if (chambre.getCategorie() == null || chambre.getCategorie().getId() <= 0) {
            System.out.println("Error creating chambre: Categorie cannot be null or invalid for chambre creation.");
            return false;
        }

        String query = "INSERT INTO chambre (telephone, categorie_id) VALUES (?, ?)";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setString(1, chambre.getTelephone());
            ps.setInt(2, chambre.getCategorie().getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Error creating chambre: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Chambre chambre) {
        if (chambre.getCategorie() == null || chambre.getCategorie().getId() <= 0) {
            System.out.println("Error updating chambre: Categorie cannot be null or invalid.");
            return false;
        }

        String query = "UPDATE chambre SET telephone = ?, categorie_id = ? WHERE id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setString(1, chambre.getTelephone());
            ps.setInt(2, chambre.getCategorie().getId());
            ps.setInt(3, chambre.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Error updating chambre: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Chambre findById(int id) {
        String query = "SELECT * FROM chambre WHERE id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int categorieId = rs.getInt("categorie_id");
                    Categorie categorie = categorieService.findById(categorieId);
                    return new Chambre(rs.getInt("id"), rs.getString("telephone"), categorie);
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
        String query = "SELECT * FROM chambre";
        try (Statement st = Connexion.getCnx().createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                int categorieId = rs.getInt("categorie_id");
                Categorie categorie = categorieService.findById(categorieId);
                chambres.add(new Chambre(rs.getInt("id"), rs.getString("telephone"), categorie));
            }
        } catch (SQLException e) {
            System.out.println("Error finding all chambres: " + e.getMessage());
        }
        return chambres;
    }

    @Override
    public boolean delete(Chambre chambre) {
        String query = "DELETE FROM chambre WHERE id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setInt(1, chambre.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Error deleting chambre: " + e.getMessage());
            return false;
        }
    }
}
