package Service;

import Connection_Project.Connexion;
import Entities.Categorie;
import IDAO.Idao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieService implements Idao<Categorie> {

    @Override
    public boolean create(Categorie categorie) {
        String query = "INSERT INTO categorie (code, libelle) VALUES (?, ?)";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setString(1, categorie.getCode());
            ps.setString(2, categorie.getLibelle());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Error creating categorie: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Categorie categorie) {
        String query = "UPDATE categorie SET code = ?, libelle = ? WHERE id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setString(1, categorie.getCode());
            ps.setString(2, categorie.getLibelle());
            ps.setInt(3, categorie.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Error updating categorie: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Categorie categorie) {
        String query = "DELETE FROM categorie WHERE id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setInt(1, categorie.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Error deleting categorie: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Categorie findById(int id) {
        String query = "SELECT * FROM categorie WHERE id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Categorie(rs.getInt("id"), rs.getString("code"), rs.getString("libelle"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding categorie by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Categorie> findAll() {
        List<Categorie> categories = new ArrayList<>();
        String query = "SELECT * FROM categorie";
        try (Statement st = Connexion.getCnx().createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                categories.add(new Categorie(rs.getInt("id"), rs.getString("code"), rs.getString("libelle")));
            }
        } catch (SQLException e) {
            System.out.println("Error finding all categories: " + e.getMessage());
        }
        return categories;
    }
}
