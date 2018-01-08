package business;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur{

    private int userID;
    private String nom;
    private String prenom;
    private String username;
    private String password;
    private String mail;
    private Boolean isAdmin;
    private ArrayList<Annonce> annonces;
    private ArrayList<Vehicule> vehicules;
    public static ArrayList<Utilisateur> allUsers = new ArrayList<Utilisateur>();

    public Utilisateur(int userID, String nom, String prenom, String username, String password, String mail, Boolean isAdmin) {
        this.userID = userID;
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.isAdmin = isAdmin;
        vehicules = new ArrayList<Vehicule>();
        annonces = new ArrayList<Annonce>();
    }

    public static Utilisateur getUserByID(int id){
        Utilisateur utilisateurCherche = null;
        for (Utilisateur utilisateur : allUsers){
            if (utilisateur.getUserID() == id){
                utilisateurCherche = utilisateur;
                break;
            }
        }
        return utilisateurCherche;
    }

    public static Utilisateur getUserBylogin(String login){
        Utilisateur utilisateurCherche = null;
        for (Utilisateur utilisateur : allUsers){
            if (utilisateur.getUsername().equals(login)){
                utilisateurCherche = utilisateur;
                break;
            }
        }
        return utilisateurCherche;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void addVehicule(Vehicule vehicule){
        vehicules.add(vehicule);
    }

    public void addAnnonce(Annonce annonce){
        annonces.add(annonce);
    }

    public void removeVehicule(Vehicule vehicule){
        annonces.remove(vehicule);
    }

    public void removeAnnonce(Annonce annonce){
        if(annonce.getUtilisateur() == this)
        {
            annonces.remove(annonce);
        }
        else{
            System.out.println("Vous ne pouvez pas supprimer une annonce qui ne vous appartiens pas.");
        }

    }



    public ArrayList<Vehicule> getVehicules() {
        return vehicules;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public ArrayList<Annonce> getAnnonces() {
        return annonces;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return nom + " " + prenom;
    }
}
