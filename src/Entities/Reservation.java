package Entities;

import java.util.Date;

public class Reservation {
    private int id;
    private Client client;
    private Chambre chambre;
    private Date datedebut; // java.util.Date for general use
    private Date datefin;   // java.util.Date for general use
    private String approvalStatus; // Status of the reservation (Approved/Denied/Pending)

    public Reservation() {
    }

    public Reservation(int id, Client client, Chambre chambre, Date datedebut, Date datefin) {
        this.id = id;
        this.client = client;
        this.chambre = chambre;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.approvalStatus = "Pending"; // Default status
    }

    // Getters
    public int getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public java.sql.Date getDatedebut() {
        return (java.sql.Date) datedebut;
    }

    public java.sql.Date getDatefin() {
        return (java.sql.Date) datefin;
    }

    public java.sql.Date getDatedebutAsSqlDate() {
        if (datedebut != null) {
            return new java.sql.Date(datedebut.getTime()); // Convert java.util.Date to java.sql.Date
        }
        return null;
    }

    public java.sql.Date getDatefinAsSqlDate() {
        if (datefin != null) {
            return new java.sql.Date(datefin.getTime()); // Convert java.util.Date to java.sql.Date
        }
        return null;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    // Convenience Method for Approve/Deny
    public void setApproved(boolean isApproved) {
        this.approvalStatus = isApproved ? "Approved" : "Denied";
    }

    public String getStatus() {
        return approvalStatus;
    }
}
