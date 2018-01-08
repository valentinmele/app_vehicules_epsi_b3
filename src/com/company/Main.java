package com.company;

import java.text.ParseException;
import business.Utilisateur;
import business.Vehicule;
import business.Annonce;
import database.DbQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class Main {
    public static Utilisateur UserSession = null;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public static void main(String[] args) {


        Utilisateur.allUsers = DbQuery.selectAllUtilisateurs();
        Vehicule.allVehicules = DbQuery.selectAllVehicules();
        Annonce.allAnnonces = DbQuery.selectAllAnnonces();

        menuUser();
        menuApp();
    }

    public static void menuUser(){
        Scanner scChoice = new Scanner(System.in);
        boolean userWhile = true;
        while (userWhile) {
            System.out.println(ANSI_YELLOW + "BIENVENUE SUR LA BONNE VOITURE ! " + ANSI_RESET);
            System.out.println("(1) - Se connecter");
            System.out.println("(2) - S'inscrire");
            System.out.println("(0) - Quitter");

            System.out.print(ANSI_YELLOW + "Entrez votre sélection: " + ANSI_RESET);
            int selection = scChoice.nextInt();

            // Connexion
            if (selection == 1) {
                boolean loginWhile = true;
                while (loginWhile) {
                    Scanner scConnexion = new Scanner(System.in);
                    System.out.println("Nom d'utilisateur ?");
                    String Username = scConnexion.nextLine();
                    System.out.println("Mot de passe ?");
                    String password = scConnexion.nextLine();

                    try{
                        Utilisateur user = Utilisateur.getUserBylogin(Username);
                        if (user.getPassword().equals(password)){
                            UserSession = user;
                            loginWhile = false;
                            userWhile = false;
                        }
                        else{
                            System.out.println(ANSI_RED + "Mauvaise combinaison login/password !" + ANSI_RESET);
                        }
                    }
                    catch (NullPointerException e){
                        System.out.println("Ce nom d'utilisateur n'existe pas.");
                    }
                }
            }
            // Inscription
            else if (selection == 2) {
                Scanner scInscription = new Scanner(System.in);
                System.out.println("Choisir nom d'utilisateur.");
                String username = scInscription.nextLine();
                System.out.println("Choisir mot de passe.");
                String password = scInscription.nextLine();
                System.out.println("Saisir votre nom");
                String nom = scInscription.nextLine();
                System.out.println("Choisir prenom.");
                String prenom = scInscription.nextLine();
                System.out.println("Choisir adresse mail.");
                String mail = scInscription.nextLine();


                Utilisateur user = Utilisateur.getUserBylogin(username);
                while(user != null){
                    System.out.println("Nom d'utilisateur déja pris. Veuillez en saisir un autre.");
                    System.out.println("Choisir un nouveau nom d'utilisateur.");
                    username = scInscription.nextLine();
                    user = Utilisateur.getUserBylogin(username);
                }

                DbQuery.insertUser(nom, prenom, username, password, mail);
                UserSession = DbQuery.selectLastUser();

                System.out.println(ANSI_GREEN + "Vous êtes désormais connecté en tant que " + nom + " " + prenom + ANSI_RESET);
                userWhile = false;
            }
            // Quitter
            else if (selection == 0) {
                break;
            }
        }
    }

    public static void menuApp(){
        Scanner scChoice = new Scanner(System.in);
        boolean userApp = true;
        while (userApp) {
            System.out.println("(1) - Voir toutes les annonces.");
            System.out.println("(2) - Vendre votre véhicule.");
            System.out.println("(3) - Voir vos annonces.");
            System.out.println("(4) - Modifier vos annonces.");
            if(UserSession.getAdmin()){
                System.out.println("(5) - Supprimer une annonce.");
                System.out.println("(6) - Supprimer un utilisateur.");
            }
            System.out.println("(0) - Quitter");

            System.out.print(ANSI_YELLOW + "Entrez votre sélection: " + ANSI_RESET);
            int selection = scChoice.nextInt();

            // Voir toutes les annonces
            if (selection == 1) {
                ArrayList<Annonce> annonces = DbQuery.selectAllAnnonces();
                for(Annonce annonce : annonces){
                    System.out.println(ANSI_BLUE + "--- " + annonce.getLibelleAnnonce() + " ---" + ANSI_RESET);
                    System.out.println(ANSI_CYAN +"   - INFORMATIONS : " + ANSI_RESET);
                    System.out.println("    Description : " + annonce.getDescription());
                    System.out.println("    Date de parution : " + annonce.getDate_parution());
                    System.out.println("    Contact : " + annonce.getUtilisateur().getUsername() + " - " + annonce.getUtilisateur().getMail());
                    System.out.println(ANSI_CYAN +"   - VEHICULE : - " + ANSI_RESET);
                    System.out.println("    Marque : " + annonce.getVehicule().getMarque());
                    System.out.println("    Modèle : " + annonce.getVehicule().getModele());
                    System.out.println("    Date MEC : " + annonce.getVehicule().getYear());
                    System.out.println("    Prix TTC : " + annonce.getVehicule().getPrix() + "\n");
                }
            }
            // Vendre votre véhicule
            else if (selection == 2) {
                Scanner scCreerVehicule = new Scanner(System.in);
                System.out.println("* Saisir marque. *");
                String marque = scCreerVehicule.nextLine();
                System.out.println("* Saisir modèle. *");
                String modele = scCreerVehicule.nextLine();
                System.out.println("* Saisir l'année de sortie du véhicule. *");
                int year = scCreerVehicule.nextInt();
                System.out.println("* Saisir prix *");
                int prix = scCreerVehicule.nextInt();

                Date date = new Date();
                long time = date.getTime();

                DbQuery.insertVehicule(marque, modele, prix, true, UserSession.getUserID(), year);
                Vehicule.allVehicules = DbQuery.selectAllVehicules();
                int vehiculeId = Vehicule.allVehicules.get(Vehicule.allVehicules.size() - 1).getVehiculeID();

                System.out.println(ANSI_BLUE + "--- CREATION DE L'ANNONCE : ---" + ANSI_RESET);
                Scanner scCreerAnnonce = new Scanner(System.in);
                System.out.println("* Saisir le titre de l'annonce. *");
                String libelle = scCreerAnnonce.nextLine();
                System.out.println("* Saisir la description de l'annonce. *");
                String description = scCreerAnnonce.nextLine();

                int annonceId = Annonce.allAnnonces.get(Annonce.allAnnonces.size() - 1).getAnnonceID() + 1;
                Vehicule vehicule = UserSession.getVehicules().get(UserSession.getVehicules().size() - 1);
                Annonce annonce = new Annonce(annonceId, libelle, UserSession, vehicule, date, description);
                DbQuery.insertAnnonce(libelle, time, UserSession.getUserID(), vehiculeId, description);
                Annonce.allAnnonces.add(annonce);
                UserSession.getAnnonces().add(annonce);
            }
            else if (selection == 3) {
                System.out.println("VOS ANNONCES : ");
                for(Annonce annonce : UserSession.getAnnonces()){
                    System.out.println(ANSI_BLUE + "--- " + annonce.getLibelleAnnonce() + " ---" + ANSI_RESET);
                    System.out.println(ANSI_CYAN + "   - INFORMATIONS : " + ANSI_RESET);
                    System.out.println("    Description : " + annonce.getDescription());
                    System.out.println("    Date de parution : " + annonce.getDate_parution());
                    System.out.println("    Contact : " + annonce.getUtilisateur().getUsername()
                            + " - " + annonce.getUtilisateur().getMail());
                    System.out.println(ANSI_CYAN + "   - VEHICULE : - " + ANSI_RESET);
                    System.out.println("    Marque : " + annonce.getVehicule().getMarque());
                    System.out.println("    Modèle : " + annonce.getVehicule().getModele());
                    System.out.println("    Date MEC : " + annonce.getVehicule().getYear());
                    System.out.println("    Prix TTC : " + annonce.getVehicule().getPrix() + "\n");
                 }
            }
            else if (selection == 4) {
                System.out.println("VOS ANNONCES A MODIFIER : ");
                for(Annonce annonce : UserSession.getAnnonces()){
                    System.out.println(ANSI_BLUE + "--- " + annonce.getLibelleAnnonce() + " ---" + ANSI_RESET);
                    System.out.println(ANSI_CYAN + "   - INFORMATIONS : - " + ANSI_RESET);
                    System.out.println("    ID : " + annonce.getAnnonceID());
                    System.out.println("    Description : " + annonce.getDescription());
                    System.out.println("    Date de parution : " + annonce.getDate_parution() + "\n");
                }
                System.out.println("Saisir l'ID de l'annonce que vous souhaitez modifier.");

                Scanner scUpdate = new Scanner(System.in);
                int selectionUpdate = scUpdate.nextInt();
                Annonce annonce = Annonce.getAnnonceByID(selectionUpdate);

                Scanner scUpdateAnnonce = new Scanner(System.in);

                if (annonce != null) {
                    if (annonce.getUtilisateur() == UserSession){
                        System.out.println("Saisir titre (tapez DELETE pour supprimer cette annonce.");
                        String titre = scUpdateAnnonce.nextLine();
                        if (titre.equals("DELETE")){
                            DbQuery.deleteAnnonce(annonce.getAnnonceID());
                            Annonce.allAnnonces.remove(annonce);
                            System.out.println("Annonce supprimée.");
                        }
                        else{
                            System.out.println("Saisir description.");
                            String description = scUpdateAnnonce.nextLine();

                            DbQuery.updateAnnonce(annonce.getAnnonceID(), titre, description);
                            annonce.setLibelleAnnonce(titre);
                            annonce.setDescription(description);
                        }
                    }
                    else{
                        System.out.println(ANSI_RED + "Impossible d'effectuer cette action. Vérifiez l'ID saisi." + ANSI_RESET);
                    }
                }
                else{
                    System.out.println(ANSI_RED + "Cette annonce n'existe pas." + ANSI_RESET);
                }

            }
            /* Suppression annonce */
            else if (selection == 5 && UserSession.getAdmin()){
                for(Annonce annonce : Annonce.allAnnonces){
                    System.out.println("* " + annonce.getAnnonceID() + " :" + annonce.getLibelleAnnonce());
                    System.out.println("** " + annonce.getDescription()+ " \n");
                }

                System.out.println("Saisir l'ID de l'annonce que vous souhaitez modifier.");
                Scanner scDeleteAnonnceAdmin = new Scanner(System.in);
                int idAnnonceToDelete = scDeleteAnonnceAdmin.nextInt();

                Annonce annonce = Annonce.getAnnonceByID(idAnnonceToDelete);

                if(annonce != null){
                    DbQuery.deleteAnnonce(annonce.getAnnonceID());
                    Annonce.allAnnonces.remove(annonce);

                }
                else{
                    System.out.println("Cette annonce n'existe pas.");
                }
            }
            /* Suppression utilisateur */
            else if (selection == 6 && UserSession.getAdmin()){
                for(Utilisateur utilisateur : Utilisateur.allUsers){
                    System.out.println("* " + utilisateur.getUserID() + " : " + utilisateur);
                }
                System.out.println("Saisir l'ID de l'utilisateur que vous souhaitez modifier.");

                Scanner scDeleteUserAdmin = new Scanner(System.in);
                int idUserToDelete = scDeleteUserAdmin.nextInt();

                Utilisateur utilisateur = Utilisateur.getUserByID(idUserToDelete);

                if(utilisateur != null){
                    DbQuery.deleteUser(utilisateur.getUserID());
                    Utilisateur.allUsers.remove(utilisateur);
                }
                else{
                    System.out.println("Cet utilisateur n'existe pas.");
                }
            }
            // Quitter
            else if (selection == 0) {
                break;
            }
        }
    }

    public static long parseDate() throws ParseException {
        String someDate = "1995-01-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(someDate);
        System.out.println(date.getTime());
        return date.getTime();
    }

}

