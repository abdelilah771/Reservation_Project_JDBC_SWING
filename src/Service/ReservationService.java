package Service;

import Connection_Project.Connexion;
import Entities.Client;
import Entities.Chambre;
import Entities.Reservation;
import IDAO.Idao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements Idao<Reservation> {

    @Override
    public boolean create(Reservation reservation) {
        if (isRoomReserved(reservation.getChambre().getId(), reservation.getDatedebut(), reservation.getDatefin())) {
            System.out.println("Error: The room is already reserved during the specified period.");
            return false;
        }

        String query = "INSERT INTO reservation (client_id, chambre_id, datedebut, datefin) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setInt(1, reservation.getClient().getId());
            ps.setInt(2, reservation.getChambre().getId());
            ps.setDate(3, reservation.getDatedebut());
            ps.setDate(4, reservation.getDatefin());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Error creating reservation: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Reservation reservation) {
        if (isRoomReserved(reservation.getChambre().getId(), reservation.getDatedebut(), reservation.getDatefin(), reservation.getId())) {
            System.out.println("Error: The room is already reserved during the specified period.");
            return false;
        }

        String query = "UPDATE reservation SET client_id = ?, chambre_id = ?, datedebut = ?, datefin = ? WHERE id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setInt(1, reservation.getClient().getId());
            ps.setInt(2, reservation.getChambre().getId());
            ps.setDate(3, reservation.getDatedebut());
            ps.setDate(4, reservation.getDatefin());
            ps.setInt(5, reservation.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Error updating reservation: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Reservation reservation) {
        String query = "DELETE FROM reservation WHERE id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setInt(1, reservation.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Error deleting reservation: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Reservation findById(int id) {
        String query = "SELECT r.*, c.id AS chambre_id, cl.id AS client_id " +
                "FROM reservation r " +
                "JOIN chambre c ON r.chambre_id = c.id " +
                "JOIN client cl ON r.client_id = cl.id WHERE r.id = ?";
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Client client = new Client(rs.getInt("client_id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("telephone"), rs.getString("email"));
                    Chambre chambre = new Chambre(rs.getInt("chambre_id"), rs.getString("telephone"), null); // Fetch categorie if needed
                    return new Reservation(rs.getInt("id"), client, chambre, rs.getDate("datedebut"), rs.getDate("datefin"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding reservation by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT r.*, c.id AS chambre_id, cl.id AS client_id " +
                "FROM reservation r " +
                "JOIN chambre c ON r.chambre_id = c.id " +
                "JOIN client cl ON r.client_id = cl.id";
        try (Statement st = Connexion.getCnx().createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Client client = new Client(rs.getInt("client_id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("telephone"), rs.getString("email"));
                Chambre chambre = new Chambre(rs.getInt("chambre_id"), rs.getString("telephone"), null); // Fetch categorie if needed
                reservations.add(new Reservation(rs.getInt("id"), client, chambre, rs.getDate("datedebut"), rs.getDate("datefin")));
            }
        } catch (SQLException e) {
            System.out.println("Error finding all reservations: " + e.getMessage());
        }
        return reservations;
    }

    private boolean isRoomReserved(int chambreId, Date startDate, Date endDate) {
        return isRoomReserved(chambreId, startDate, endDate, -1);
    }

    private boolean isRoomReserved(int chambreId, Date startDate, Date endDate, int excludeReservationId) {
        String query = "SELECT COUNT(*) FROM reservation WHERE chambre_id = ? AND " +
                "((datedebut BETWEEN ? AND ?) OR (datefin BETWEEN ? AND ?) OR (? BETWEEN datedebut AND datefin))";
        if (excludeReservationId > 0) {
            query += " AND id != ?";
        }
        try (PreparedStatement ps = Connexion.getCnx().prepareStatement(query)) {
            ps.setInt(1, chambreId);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            ps.setDate(4, startDate);
            ps.setDate(5, endDate);
            ps.setDate(6, startDate);
            if (excludeReservationId > 0) {
                ps.setInt(7, excludeReservationId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking room reservation status: " + e.getMessage());
        }
        return false;
    }
}