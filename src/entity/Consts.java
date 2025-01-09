package entity;

import java.net.URLDecoder;

/**
 * Utility class containing constants and SQL queries for the project.
 */
public final class Consts {
    private Consts() {
        throw new AssertionError();
    }

    // Database connection string
    
    protected static final String DB_FILEPATH = getDBPath();
    

    public static final String CONN_STR = "jdbc:ucanaccess://"  + DB_FILEPATH + ";COLUMNORDER=DISPLAY";


    /*----------------------------------------- MANUFACTURERS QUERIES -----------------------------------------*/
    public static final String SQL_GET_MANUFACTURER = "SELECT * FROM Manufacturer WHERE manufacturerID = ?";
    public static final String SQL_SHOW_MANUFACTURERS = "SELECT * FROM Manufacturer";
    public static final String SQL_INSERT_MANUFACTURER = 
    	    "INSERT INTO Manufacturer (ManufacturerID, FullName, PhoneNumber, Address, Email) VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_DELETE_MANUFACTURER = 
    	    "DELETE FROM Manufacturer WHERE ManufacturerID = ?";
    public static final String SQL_UPDATE_MANUFACTURER = 
            "UPDATE Manufacturer SET Manufacturer.FullName = ?, Manufacturer.PhoneNumber = ?, " +
            "Manufacturer.Address = ?, Manufacturer.Email = ? WHERE Manufacturer.ManufacturerID = ?";

    /*----------------------------------------- WINES QUERIES -----------------------------------------------*/
    public static final String SQL_GET_WINE = "SELECT * FROM Wine WHERE catalogNumber = ?";
    public static final String SQL_SHOW_WINES = "SELECT * FROM Wine";
    public static final String SQL_ADD_WINE =
        "INSERT INTO Wine (CatalogNumber, Name, Description, ProductionYear, Price, SweetnessLevel, image, ManufacturerID) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_DELETE_WINE = 
    	    "DELETE FROM Wine WHERE CatalogNumber = ?";
    public static final String SQL_UPDATE_WINE = 
            "UPDATE Wine SET Wine.Name = ?, Wine.Description = ?, Wine.ProductionYear = ?, Wine.Price = ?, " +
            "Wine.SweetnessLevel = ?, Wine.[image] = ?, Wine.ManufacturerID = ? WHERE Wine.CatalogNumber = ?";
    private static String getDBPath() {
        try {
            String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String decoded = URLDecoder.decode(path, "UTF-8");
            if (decoded.contains(".jar")) {
                decoded = decoded.substring(0, decoded.lastIndexOf('/'));
                String dbPath = decoded + "/src/entity/ex2Acces1.accdb";
                
                return dbPath;
            } else {
                decoded = decoded.substring(0, decoded.lastIndexOf("/bin"));
                String dbPath = decoded + "/src/entity/ex2Acces1.accdb";
                
                return dbPath;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    

    }
    
   

