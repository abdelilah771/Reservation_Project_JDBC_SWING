package Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import IDAO.Idao;
import Connection_Project.Connexion;
import Entities.Client;

public class ClientService implements Idao<Client>{

    @Override
    public boolean create(Client o) {
        String req="insert into clients values(null,?,?,?,?)";
        try {
            PreparedStatement ps=Connexion.getCnx().prepareStatement(req);
            ps.setString(1, o.getNom());
            ps.setString(2,o.getPrenom());
            ps.setString(3, o.getTelephone());
            ps.setString(4, o.getEmail());
            if(ps.executeUpdate()==1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erreur de create SQL");
        }
        return false;
    }

    @Override
    public boolean update(Client o) {
        String req="update clients set nom=?,prenom=?,telephone=?,email=? where id=?";
        try {
            PreparedStatement ps=Connexion.getCnx().prepareStatement(req);
            ps.setString(1, o.getNom());
            ps.setString(2,o.getPrenom());
            ps.setString(3, o.getTelephone());
            ps.setString(4, o.getEmail());
            ps.setInt(5, o.getId());
            if(ps.executeUpdate()==1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erreur update SQL");
        }
        return false;
    }

    @Override
    public boolean delete(Client o) {

        return false;
    }

    @Override
    public Client findById(int id) {
        String req="select * from clients where id=?";
        try {
            PreparedStatement ps=Connexion.getCnx().prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                return new Client(rs.getInt(1),rs.getString("nom"),rs.getString(3),rs.getString(4),rs.getString(5));
            }
        } catch (SQLException e) {
            System.out.println("Erreur select par id SQL");
        }

        return null;
    }

    @Override
    public List<Client> findAll() {
        List<Client> ls=new ArrayList<>();
        String req="select * from clients";
        try {
            PreparedStatement ps=Connexion.getCnx().prepareStatement(req);
            ResultSet rs=ps.executeQuery();
            while(rs.next()) {
                ls.add(new Client(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
            }
            return ls;
        } catch (SQLException e) {
            System.out.println("Erreur select SQL");
        }

        return null;
    }

}
