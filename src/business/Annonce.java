package business;

import java.util.ArrayList;
import java.util.Date;

public class Annonce {

    private int annonceID;
    private String libelleAnnonce;
    private Utilisateur utilisateur;
    private Vehicule vehicule;
    private Date date_parution;
    private String description;
    public static ArrayList<Annonce> allAnnonces = new ArrayList<Annonce>();


    public Annonce(int annonceID, String libelleAnnonce, Utilisateur utilisateur, Vehicule vehicule, Date date_parution, String description) {
        this.annonceID = annonceID;
        this.libelleAnnonce = libelleAnnonce;
        this.utilisateur = utilisateur;
        this.vehicule = vehicule;
        this.date_parution = date_parution;
        this.description = description;
    }


    public static Annonce getAnnonceByID(int id){
        for (Annonce annonce : allAnnonces){
            if (annonce.getAnnonceID() == id){
                return annonce;
            }
        }
        return null;
    }

    public int getAnnonceID() {
        return annonceID;
    }

    public void setAnnonceID(int annonceID) {
        this.annonceID = annonceID;
    }

    public String getLibelleAnnonce() {
        return libelleAnnonce;
    }

    public void setLibelleAnnonce(String libelleAnnonce) {
        this.libelleAnnonce = libelleAnnonce;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public Date getDate_parution() {
        return date_parution;
    }

    public void setDate_parution(Date date_parution) {
        this.date_parution = date_parution;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return libelleAnnonce;
    }
}
