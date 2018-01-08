package database;
import business.Annonce;
import business.Utilisateur;
import business.Vehicule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author sqlitetutorial.net
 */
public class DbQuery {
    /**
     * Connect to a sample database
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:data/database.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                    //conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return conn;
    }

    public static ArrayList<Utilisateur> selectAllUtilisateurs(){
        String sql = "SELECT * FROM utilisateur";
        ArrayList<Utilisateur> listeUtilisateurs = new ArrayList<Utilisateur>();

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                int id = rs.getInt(1);
                String nom = rs.getString(2);
                String prenom = rs.getString(3);
                String username = rs.getString(4);
                String password = rs.getString(5);
                String mail = rs.getString(6);
                Boolean isAdmin = rs.getBoolean(7);
                Utilisateur utilisateur = new Utilisateur(id, nom, prenom, username, password, mail, isAdmin);
                listeUtilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listeUtilisateurs;
    }

    public static Utilisateur selectLastUser(){
        String sql = "SELECT * FROM utilisateur ORDER BY id DESC LIMIT 1";
        Utilisateur utilisateur = null;
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                int id = rs.getInt(1);
                String nom = rs.getString(2);
                String prenom = rs.getString(3);
                String username = rs.getString(4);
                String password = rs.getString(5);
                String mail = rs.getString(6);
                Boolean isAdmin = rs.getBoolean(7);
                utilisateur = new Utilisateur(id, nom, prenom, username, password, mail, isAdmin);
                Utilisateur.allUsers.add(utilisateur);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return utilisateur;
    }

    public static ArrayList<Annonce> selectAllAnnonces(){
        String sql = "SELECT * FROM annonce";
        ArrayList<Annonce> listeAnnonces = new ArrayList <Annonce>();

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                int id = rs.getInt(1);
                String libelle = rs.getString(2);
                Date date_paru = rs.getDate(3);
                Utilisateur utilisateur = Utilisateur.getUserByID(rs.getInt(4));
                Vehicule vehicule = Vehicule.getVehiculeByID(rs.getInt(5));
                String description = rs.getString(6);
                Annonce annonce = new Annonce(id, libelle, utilisateur, vehicule, date_paru, description);
                listeAnnonces.add(annonce);
                utilisateur.getAnnonces().add(annonce);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listeAnnonces;
    }

    public static ArrayList<Vehicule> selectAllVehicules(){
        String sql = "SELECT * FROM vehicule";
        ArrayList<Vehicule> listeVehicules = new ArrayList <Vehicule>();

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                int id = rs.getInt(1);
                String marque = rs.getString(2);
                String modele = rs.getString(3);
                int prix = rs.getInt(4);
                Boolean isOnSale = rs.getBoolean(5);
                Utilisateur utilisateur = Utilisateur.getUserByID(rs.getInt(6));
                int year = rs.getInt(7);


                Vehicule vehicule = new Vehicule(id, marque, modele, prix, utilisateur, isOnSale, year);
                listeVehicules.add(vehicule);
                utilisateur.getVehicules().add(vehicule);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listeVehicules;
    }

    public static void insertAnnonce(String libelle, long date_parution, int utilisateur_id, int vehicule_id, String description) {
        String sql = "INSERT INTO annonce (libelle, date_parution, utilisateur_id, vehicule_id, " +
                "date_parution, description) VALUES (?,?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, libelle);
            pstmt.setLong(2, date_parution);
            pstmt.setInt(3, utilisateur_id);
            pstmt.setInt(4, vehicule_id);
            pstmt.setLong(5, 1512342000000L);
            pstmt.setString(6, description);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertVehicule(String marque, String modele, int prix,
                                      Boolean isOnSale, int utilisateur_id, int year) {
        String sql = "INSERT INTO vehicule (marque, modele, prix, is_on_sale, utilisateur_id, year)" +
                " VALUES (?,?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, marque);
            pstmt.setString(2, modele);
            pstmt.setInt(3, prix);
            pstmt.setBoolean(4, isOnSale);
            pstmt.setInt(5, utilisateur_id);
            pstmt.setInt(6, year);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertUser(String nom, String prenom, String username, String password,
                                      String mail) {
        String sql = "INSERT INTO utilisateur (nom, prenom, username, password, mail)" +
                " VALUES (?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.setString(5, mail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateAnnonce(int id, String libelle, String description) {
        String sql = "UPDATE annonce "
                + "SET libelle = ? , "
                + "description = ? "
                + "WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, libelle);
            pstmt.setString(2, description);
            pstmt.setInt(3, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteAnnonce(int id) {
        String sql = "DELETE FROM annonce WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteUser(int id) {
        String sql = "DELETE FROM utilisateur WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}