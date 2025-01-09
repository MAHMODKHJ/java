package control;

import entity.Consts;
import entity.Manufacturer;
import entity.Wine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;



public class ManufacturerControl {
    private static ManufacturerControl _instance;

    private ManufacturerControl() {
    }

    public static ManufacturerControl getInstance() {
        if (_instance == null) {
            _instance = new ManufacturerControl();
        }
        return _instance;
    }

    public List<Manufacturer> getAllManufacturers() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try {
        	Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                 PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SHOW_MANUFACTURERS);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    manufacturers.add(new Manufacturer(
                            rs.getInt("manufacturerID"),
                            rs.getString("fullName"),
                            rs.getString("phoneNumber"),
                            rs.getString("address"),
                            rs.getString("email")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return manufacturers;
    }

    public boolean addManufacturer(int manufacturerId, String fullName, long phoneNumber, String address, String email) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                 PreparedStatement stmt = conn.prepareStatement(Consts.SQL_INSERT_MANUFACTURER)) {

                stmt.setInt(1, manufacturerId);       // Parameter 1
                stmt.setString(2, fullName);         // Parameter 2
                stmt.setLong(3, phoneNumber);        // Parameter 3
                stmt.setString(4, address);          // Parameter 4
                stmt.setString(5, email);            // Parameter 5

                stmt.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    public boolean removeManufacturer(int manufacturerId) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            // Check if the manufacturer is associated with any wine
            if (isManufacturerInUse(manufacturerId)) {
                throw new IllegalArgumentException("Cannot remove manufacturer. It is associated with existing wines.");
            }

            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                 PreparedStatement stmt = conn.prepareStatement(Consts.SQL_DELETE_MANUFACTURER)) {

                stmt.setInt(1, manufacturerId); // Set the ManufacturerID parameter
                stmt.executeUpdate();
                return true; // Deletion was successful
            }
        } catch (IllegalArgumentException e) {
            // Show a message dialog for manufacturers in use
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Deletion failed
    }

    private boolean isManufacturerInUse(int manufacturerId) {
        List<Wine> wines = WineControl.getInstance().getAllWines(); // Retrieve all wines
        for (Wine wine : wines) {
            if (wine.getManufacturerId() == manufacturerId) {
                return true; // Manufacturer is associated with at least one wine
            }
        }
        return false; // Manufacturer is not associated with any wine
    }



    public Manufacturer getManufacturerById(int id) {
        try {
        	Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                 PreparedStatement stmt = conn.prepareStatement(Consts.SQL_GET_MANUFACTURER)) {

                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new Manufacturer(
                                rs.getInt("manufacturerID"),
                                rs.getString("fullName"),
                                rs.getString("phoneNumber"),
                                rs.getString("address"),
                                rs.getString("email"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public boolean updateManufacturer(int manufacturerId, String fullName, int phoneNumber, String address, String email) {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                 PreparedStatement stmt = conn.prepareStatement(Consts.SQL_UPDATE_MANUFACTURER)) {

                stmt.setString(1, fullName);
                stmt.setInt(2, phoneNumber);
                stmt.setString(3, address);
                stmt.setString(4, email);
                stmt.setInt(5, manufacturerId);

                stmt.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

   
}
