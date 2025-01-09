package control;

import entity.Wine;
import entity.Consts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class WineControl {
	 private static WineControl _instance;

	    private WineControl() {
	    }

	    public static WineControl getInstance() {
	        if (_instance == null) {
	            _instance = new WineControl();
	        }
	        return _instance;
	    }

	    public List<Wine> getAllWines() {
	        List<Wine> wines = new ArrayList<>();
	        try {
	        	Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
	                 PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SHOW_WINES);
	                 ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    wines.add(new Wine(
	                            rs.getString("catalogNumber"),
	                            rs.getString("name"),
	                            rs.getString("description"),
	                            rs.getInt("productionYear"),
	                            rs.getInt("price"),
	                            rs.getString("sweetnessLevel"),
	                            rs.getString("image"),
	                            rs.getInt("manufacturerID")));
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return wines;
	    }

	    public boolean addWine(String catalogNumber, String name, String description, int productionYear,
                int price, String sweetnessLevel, String image, int manufacturerId) {
try {
 Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
 
 // Check if the manufacturer exists
 if (ManufacturerControl.getInstance().getManufacturerById(manufacturerId) == null) {
     throw new IllegalArgumentException("Manufacturer not found");
 }

 try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
      PreparedStatement stmt = conn.prepareStatement(Consts.SQL_ADD_WINE)) {

     stmt.setString(1, catalogNumber);       // CatalogNumber
     stmt.setString(2, name);               // Name
     stmt.setString(3, description);        // Description
     stmt.setInt(4, productionYear);        // ProductionYear
     stmt.setInt(5, price);                 // PricePerBottle
     stmt.setString(6, sweetnessLevel);     // SweetnessLevel
     stmt.setString(7, image);              // Image
     stmt.setInt(8, manufacturerId);        // ManufacturerID

     stmt.executeUpdate();
     return true;
 }
} catch (IllegalArgumentException e) {
 // Display a message dialog for invalid manufacturer ID
 JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
} catch (Exception e) {
 e.printStackTrace();
}
return false;
}




	    public boolean removeWine(String catalogNumber) {
	        try {
	            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
	                 PreparedStatement stmt = conn.prepareStatement(Consts.SQL_DELETE_WINE)) {

	                stmt.setString(1, catalogNumber);
	                stmt.executeUpdate();
	                return true;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return false;
	    }


	    public Wine getWineByCatalogNumber(String catalogNumber) {
	        try {
	        	Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
	                 PreparedStatement stmt = conn.prepareStatement(Consts.SQL_GET_WINE)) {

	                stmt.setString(1, catalogNumber);
	                try (ResultSet rs = stmt.executeQuery()) {
	                    if (rs.next()) {
	                        return new Wine(
	                                rs.getString("catalogNumber"),
	                                rs.getString("name"),
	                                rs.getString("description"),
	                                rs.getInt("productionYear"),
	                                rs.getInt("Price"),
	                                rs.getString("sweetnessLevel"),
	                                rs.getString("image"),
	                                rs.getInt("manufacturerID"));
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	    public boolean updateWine(String catalogNumber, String name, String description, int productionYear,
                int price, String sweetnessLevel, String image, int manufacturerId) {
try {
Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
   PreparedStatement stmt = conn.prepareStatement(Consts.SQL_UPDATE_WINE)) {

  stmt.setString(1, name);
  stmt.setString(2, description);
  stmt.setInt(3, productionYear);
  stmt.setInt(4, price);
  stmt.setString(5, sweetnessLevel);
  stmt.setString(6, image);
  stmt.setInt(7, manufacturerId);
  stmt.setString(8, catalogNumber);

  stmt.executeUpdate();
  return true;
}
} catch (Exception e) {
e.printStackTrace();
}
return false;
}

	    
	}