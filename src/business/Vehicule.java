package business;

import java.util.ArrayList;
import java.util.Date;

public class Vehicule {

    private int vehiculeID;
    private String marque;
    private String modele;
    private int prix;
    private int year;
    private Utilisateur utilisateur;
    private boolean isOnSale;
    public static ArrayList<Vehicule> allVehicules = new ArrayList<Vehicule>();


    public Vehicule(int vehiculeID, String marque, String modele, int prix, Utilisateur utilisateur, boolean isOnSale, int year) {
        this.vehiculeID = vehiculeID;
        this.marque = marque;
        this.modele = modele;
        this.prix = prix;
        this.year = year;
        this.utilisateur = utilisateur;
        this.isOnSale = isOnSale;

    }

    public static Vehicule getVehiculeByID(int id){
        Vehicule vehiculeCherche = null;
        for (Vehicule vehicule : allVehicules){
            if (vehicule.getVehiculeID() == id){
                vehiculeCherche = vehicule;
            }
        }
        return vehiculeCherche;
    }

    public boolean isOnSale() {
        return isOnSale;
    }

    public void setOnSale(boolean onSale) {
        isOnSale = onSale;
    }

    public int getVehiculeID() {
        return vehiculeID;
    }

    public void setVehiculeID(int vehiculeID) {
        this.vehiculeID = vehiculeID;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public String toString() {
        return marque + " - " + modele + " | " + utilisateur.getUsername();
    }
}
