package Test;

import Entities.Client;
import Entities.Chambre;
import Entities.Categorie;
import Entities.Reservation;
import Service.ClientService;
import Service.ChambreService;
import Service.CategorieService;
import Service.ReservationService;

import java.util.Date;
import java.util.List;

public class ReservationTest {
    public static void main(String[] args) {
        // Initialize services
        ClientService clientService = new ClientService();
        ChambreService chambreService = new ChambreService();
        CategorieService categorieService = new CategorieService();
        ReservationService reservationService = new ReservationService();

        // Create categories
        Categorie categorie1 = new Categorie(1, "CAT001", "Standard Room");
        Categorie categorie2 = new Categorie(2, "CAT002", "Deluxe Room");
        categorieService.create(categorie1);
        categorieService.create(categorie2);

        // Create clients
        Client client1 = new Client(1, "Doe", "John", "123456789", "john.doe@example.com");
        Client client2 = new Client(2, "Smith", "Jane", "987654321", "jane.smith@example.com");
        clientService.create(client1);
        clientService.create(client2);

        // Create rooms
        Chambre chambre1 = new Chambre(1, "101", categorie1);
        Chambre chambre2 = new Chambre(2, "102", categorie2);
        Chambre chambre3 = new Chambre(3, "103", categorie1);
        chambreService.create(chambre1);
        chambreService.create(chambre2);
        chambreService.create(chambre3);

        // Create reservations
        Reservation reservation1 = new Reservation(1, client1, chambre1, new Date(), new Date(System.currentTimeMillis() + 86400000)); // 1 day later
        Reservation reservation2 = new Reservation(2, client2, chambre2, new Date(), new Date(System.currentTimeMillis() + 172800000)); // 2 days later

        if (reservationService.create(reservation1)) {
            System.out.println("Reservation 1 created successfully.");
        } else {
            System.out.println("Failed to create Reservation 1.");
        }

        if (reservationService.create(reservation2)) {
            System.out.println("Reservation 2 created successfully.");
        } else {
            System.out.println("Failed to create Reservation 2.");
        }

        // Test duplicate reservation
        Reservation reservationDuplicate = new Reservation(3, client1, chambre1, new Date(), new Date(System.currentTimeMillis() + 86400000));
        if (reservationService.create(reservationDuplicate)) {
            System.out.println("Duplicate reservation created (error).");
        } else {
            System.out.println("Duplicate reservation not allowed.");
        }

        // Test findById
        Reservation foundReservation = reservationService.findById(1);
        if (foundReservation != null) {
            System.out.println("Found reservation:");
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
        reservation1.setDatefin(new Date(System.currentTimeMillis() + 259200000)); // Extend by 3 days
        if (reservationService.update(reservation1)) {
            System.out.println("Reservation 1 updated successfully.");
        } else {
            System.out.println("Failed to update Reservation 1.");
        }

        // Test delete
        if (reservationService.delete(reservation2)) {
            System.out.println("Reservation 2 deleted successfully.");
        } else {
            System.out.println("Failed to delete Reservation 2.");
        }

//        // Cleanup: Delete all created data
//        reservationService.delete(reservation1);
//        reservationService.delete(reservationDuplicate);
//        chambreService.delete(chambre1);
//        chambreService.delete(chambre2);
//        chambreService.delete(chambre3);
//        categorieService.delete(categorie1);
//        categorieService.delete(categorie2);
//        clientService.delete(client1);
//        clientService.delete(client2);

        System.out.println("Test completed.");
    }
}
