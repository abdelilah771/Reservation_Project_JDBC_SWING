package Test;

import Entities.Categorie;
import Entities.Chambre;
import Service.ChambreService;
import services.CategorieService;

import java.util.List;

public class MainChambreTest {
    public static void main(String[] args) {
        ChambreService chambreService = new ChambreService();
        CategorieService categorieService = new CategorieService();

        // Ensure we have a category to associate with a chambre
        Categorie categorie = new Categorie();
        categorie.setCode("CAT001");
        categorie.setLibelle("Luxury");
        boolean categorieCreated = categorieService.create(categorie);

        if (!categorieCreated) {
            System.out.println("Failed to create a test category.");
            return;
        }

        // Retrieve the created category (assuming the last inserted one)
        List<Categorie> categories = categorieService.findAll();
        if (categories.isEmpty()) {
            System.out.println("No categories found.");
            return;
        }

        Categorie testCategorie = categories.get(0);

        // Test creating a chambre
        Chambre chambre = new Chambre();
        chambre.setTelephone("123456789");
        chambre.setCategorie(testCategorie);

        if (chambreService.create(chambre)) {
            System.out.println("Chambre created successfully.");
        } else {
            System.out.println("Failed to create chambre.");
        }

        // Test finding all chambres
        System.out.println("\nAll Chambres:");
        List<Chambre> chambres = chambreService.findAll();
        for (Chambre c : chambres) {
            System.out.println("ID=" + c.getId() + ", Telephone=" + c.getTelephone() +
                    ", Categorie Code=" + c.getCategorie().getCode() +
                    ", Categorie Libelle=" + c.getCategorie().getLibelle());
        }

        // Test finding a chambre by ID
        if (!chambres.isEmpty()) {
            Chambre foundChambre = chambreService.findById(chambres.get(0).getId());
            if (foundChambre != null) {
                System.out.println("\nChambre found by ID: ID=" + foundChambre.getId() +
                        ", Telephone=" + foundChambre.getTelephone() +
                        ", Categorie Code=" + foundChambre.getCategorie().getCode() +
                        ", Categorie Libelle=" + foundChambre.getCategorie().getLibelle());
            } else {
                System.out.println("\nFailed to find chambre by ID.");
            }
        }

        // Test updating a chambre
        if (!chambres.isEmpty()) {
            Chambre chambreToUpdate = chambres.get(0);
            chambreToUpdate.setTelephone("987654321");
            if (chambreService.update(chambreToUpdate)) {
                System.out.println("\nChambre updated successfully.");
            } else {
                System.out.println("\nFailed to update chambre.");
            }
        }

//        // Test deleting a chambre
//        if (!chambres.isEmpty()) {
//            Chambre chambreToDelete = chambres.get(0);
//            if (chambreService.delete(chambreToDelete)) {
//                System.out.println("\nChambre deleted successfully.");
//            } else {
//                System.out.println("\nFailed to delete chambre.");
//            }
//        }
    }
}
