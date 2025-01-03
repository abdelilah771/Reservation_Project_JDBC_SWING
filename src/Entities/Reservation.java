package Entities;

import java.util.Date;

public class Reservation {
    private int id;
    private Client client;
    private Chambre chambre;
    private java.util.Date datedebut;
    private java.util.Date datefin;

    public Reservation() {
    }

    public Reservation(int id,Client client, Chambre chambre, Date datedebut, Date datefin) {
        this.id=id;
        this.client = client;
        this.chambre = chambre;
        this.datedebut = datedebut;
        this.datefin = datefin;
    }

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
        return new java.sql.Date(datedebut.getTime());  // Convertir java.util.Date en java.sql.Date
    }

    public java.sql.Date getDatefin() {
        return new java.sql.Date(datefin.getTime());  // Convertir java.util.Date en java.sql.Date
    }

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
}
