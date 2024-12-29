package services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import IDAO.Idao;
import Connection_Project.Connexion;
import Entities.Categorie;

public class CategorieService implements Idao<Categorie> {

    @Override
    public boolean create(Categorie o) {
        String req = "INSERT INTO categorie VALUES (null, ?, ?)";
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(req);
            ps.setString(1, o.getCode());
            ps.setString(2, o.getLibelle());
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erreur de create SQL: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Categorie o) {
        String req = "UPDATE categorie SET code = ?, libelle = ? WHERE id = ?";
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(req);
            ps.setString(1, o.getCode());
            ps.setString(2, o.getLibelle());
            ps.setInt(3, o.getId());
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erreur update SQL: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Categorie o) {
        String req = "DELETE FROM categorie WHERE id = ?";
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(req);
            ps.setInt(1, o.getId());
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erreur delete SQL: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Categorie findById(int id) {
        String req = "SELECT * FROM categorie WHERE id = ?";
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Categorie(rs.getInt("id"), rs.getString("code"), rs.getString("libelle"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur select par id SQL: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Categorie> findAll() {
        List<Categorie> categories = new ArrayList<>();
        String req = "SELECT * FROM categorie";
        try {
            PreparedStatement ps = Connexion.getCnx().prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categories.add(new Categorie(rs.getInt("id"), rs.getString("code"), rs.getString("libelle")));
            }
            return categories;
        } catch (SQLException e) {
            System.out.println("Erreur select SQL: " + e.getMessage());
        }
        return null;
    }
}
