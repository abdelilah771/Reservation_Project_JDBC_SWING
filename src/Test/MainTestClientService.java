package Test;

import services.ClientService;
import Entities.Client;
import java.util.List;

public class MainTestClientService {
    public static void main(String[] args) {
        ClientService service = new ClientService();

        // Create a new client
        Client newClient = new Client();
        newClient.setNom("John");
        newClient.setPrenom("Doe");
        newClient.setTelephone("123456789");
        newClient.setEmail("john.doe@example.com");

        if (service.create(newClient)) {
            System.out.println("Client created successfully.");
        } else {
            System.out.println("Failed to create client.");
        }

        // Find a client by ID
        int clientId = 1; // Replace with an actual ID from your database
        Client clientById = service.findById(clientId);

        if (clientById != null) {
            System.out.println("Client found by ID:");
            System.out.println("ID=" + clientById.getId() + ", Name=" + clientById.getNom() + ", Prenom=" + clientById.getPrenom() + ", Telephone=" + clientById.getTelephone() + ", Email=" + clientById.getEmail());
        } else {
            System.out.println("Client with ID=" + clientId + " not found.");
        }

        // Update the client
        if (clientById != null) {
            clientById.setTelephone("987654321");
            clientById.setEmail("updated.email@example.com");

            if (service.update(clientById)) {
                System.out.println("Client updated successfully.");
            } else {
                System.out.println("Failed to update client.");
            }
        }

        // Find all clients
        List<Client> allClients = service.findAll();

        if (allClients != null && !allClients.isEmpty()) {
            System.out.println("All clients:");
            for (Client c : allClients) {
                System.out.println("ID=" + c.getId() + ", Name=" + c.getNom() + ", Prenom=" + c.getPrenom() + ", Telephone=" + c.getTelephone() + ", Email=" + c.getEmail());
            }
        } else {
            System.out.println("No clients found.");
        }

        // Delete a client
        if (clientById != null && service.delete(clientById)) {
            System.out.println("Client deleted successfully.");
        } else {
            System.out.println("Failed to delete client.");
        }
    }
}
