package Test;

import Entities.Client;
import Entities.Chambre;
import Entities.Reservation;
import java.util.Date;

public class ReservationTest {

    public static void main(String[] args) {
        // Create a client
        Client client = new Client(1, "John", "Doe", "123456789", "john.doe@example.com");

        // Create a chambre
        Chambre chambre = new Chambre(101, "123-456");

        // Create reservation objects
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + (1000 * 60 * 60 * 24)); // 1 day later

        Reservation reservation = new Reservation(1, client, chambre, startDate, endDate);

        // Display initial reservation details
        System.out.println("Initial Reservation:");
        displayReservation(reservation);

        // Approve the reservation
        System.out.println("\nApproving the reservation...");
        reservation.setApprovalStatus("Approved");
        displayReservation(reservation);

        // Deny the reservation
        System.out.println("\nDenying the reservation...");
        reservation.setApprovalStatus("Denied");
        displayReservation(reservation);

        // Try setting an invalid approval status
        System.out.println("\nSetting an invalid approval status...");
        try {
            reservation.setApprovalStatus("InvalidStatus");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
    }

    private static void displayReservation(Reservation reservation) {
        System.out.println("Reservation ID: " + reservation.getId());
        System.out.println("Client: " + reservation.getClient().getNom() + " " + reservation.getClient().getPrenom());
        System.out.println("Chambre ID: " + reservation.getChambre().getId());
        System.out.println("Start Date: " + reservation.getDatedebut());
        System.out.println("End Date: " + reservation.getDatefin());
        System.out.println("Approval Status: " + reservation.getApprovalStatus());
    }
}
