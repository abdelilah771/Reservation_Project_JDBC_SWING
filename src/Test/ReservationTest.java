package Test;

import Entities.Client;
import Entities.Chambre;
import Entities.Reservation;
import Service.ClientService;
import Service.ChambreService;
import Service.ReservationService;

import java.util.Date;
import java.util.List;

public class ReservationTest {
    public static void main(String[] args) {
        // Initialize services
        ClientService clientService = new ClientService();
        ChambreService chambreService = new ChambreService();
        ReservationService reservationService = new ReservationService();

        // Create test data
        Client client1 = new Client(1, "Doe", "John", "123456789", "john.doe@example.com");
        clientService.create(client1);

        Chambre chambre1 = new Chambre(1, "Room 101", null);
        chambreService.create(chambre1);

        // Test create reservation
        Reservation reservation1 = new Reservation(1, client1, chambre1, new Date(), new Date(System.currentTimeMillis() + 86400000)); // 1 day later
        if (reservationService.create(reservation1)) {
            System.out.println("Reservation created successfully.");
        } else {
            System.out.println("Failed to create reservation.");
        }

        // Test duplicate reservation (should fail)
        Reservation reservationDuplicate = new Reservation(2, client1, chambre1, new Date(), new Date(System.currentTimeMillis() + 86400000));
        if (reservationService.create(reservationDuplicate)) {
            System.out.println("Duplicate reservation created (error).\n");
        } else {
            System.out.println("Duplicate reservation not allowed.");
        }

        // Test findById
        Reservation foundReservation = reservationService.findById(1);
        if (foundReservation != null) {
            System.out.println("Found reservation: ");
            System.out.println("ID=" + foundReservation.getId() + ", Client=" + foundReservation.getClient().getNom() + ", Chambre=" + foundReservation.getChambre().getId());
        } else {
            System.out.println("Reservation not found.");
        }

        // Test findAll
        List<Reservation> reservations = reservationService.findAll();
        System.out.println("All reservations:");
        for (Reservation res : reservations) {
            System.out.println("ID=" + res.getId() + ", Client=" + res.getClient().getNom() + ", Chambre=" + res.getChambre().getId());
        }

        // Test update
        reservation1.setDatefin(new Date(System.currentTimeMillis() + 172800000)); // Extend by 2 days
        if (reservationService.update(reservation1)) {
            System.out.println("Reservation updated successfully.");
        } else {
            System.out.println("Failed to update reservation.");
        }

        // Test delete
        if (reservationService.delete(reservation1)) {
            System.out.println("Reservation deleted successfully.");
        } else {
            System.out.println("Failed to delete reservation.");
        }
    }
}
