package control;

import entity.Wine;
import entity.Consts;

import org.w3c.dom.*;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;
import java.net.URLDecoder;


public class XMLControl {
    private static XMLControl instance;
    public static final String XML_FILEPATH = getxmlPath();

    private XMLControl() {}

    public static XMLControl getInstance() {
        if (instance == null)
            instance = new XMLControl();
        return instance;
    }

    public void importWinesFromXML(String path) {
        try {
            Document doc = DocumentBuilderFactory.newInstance()
                                .newDocumentBuilder().parse(new File(path));
            doc.getDocumentElement().normalize();
            NodeList nl = doc.getElementsByTagName("Wine");

            int errors = 0;
            for (int i = 0; i < nl.getLength(); i++) {
                if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) nl.item(i);
                    String catalogNumber = el.getElementsByTagName("CatalogNumber").item(0).getTextContent();

                    // Check if the wine already exists
                    if (WineControl.getInstance().getWineByCatalogNumber(catalogNumber) != null) {
                        throw new IllegalArgumentException("Wine with CatalogNumber " + catalogNumber + " already exists.");
                    }

                    try {
                        Wine wine = new Wine(
                            catalogNumber,
                            el.getElementsByTagName("Name").item(0).getTextContent(),
                            el.getElementsByTagName("Description").item(0).getTextContent(),
                            Integer.parseInt(el.getElementsByTagName("ProductionYear").item(0).getTextContent()),
                            Integer.parseInt(el.getElementsByTagName("Price").item(0).getTextContent()),
                            el.getElementsByTagName("SweetnessLevel").item(0).getTextContent(),
                            el.getElementsByTagName("Image").item(0).getTextContent(),
                            Integer.parseInt(el.getElementsByTagName("ManufacturerID").item(0).getTextContent())
                        );

                        // Attempt to insert the wine
                        if (!WineControl.getInstance().addWine(
                                wine.getCatalogNumber(), wine.getName(), wine.getDescription(),
                                wine.getProductionYear(), wine.getPrice(), wine.getSweetnessLevel(),
                                wine.getImage(), wine.getManufacturerId())) {
                            errors++;
                        }
                    } catch (NumberFormatException e) {
                        errors++;
                    }
                }
            }

            System.out.println((errors == 0) ? "Wines data imported successfully!" : 
                String.format("Wines data imported with %d errors!", errors));
        } catch (IllegalArgumentException e) {
            // Handle duplicate import exception
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Duplicate Data Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importManufacturersFromXML(String path) {
        try {
            Document doc = DocumentBuilderFactory.newInstance()
                                .newDocumentBuilder().parse(new File(path));
            doc.getDocumentElement().normalize();
            NodeList nl = doc.getElementsByTagName("Manufacturer");

            int errors = 0;
            for (int i = 0; i < nl.getLength(); i++) {
                if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) nl.item(i);
                    int manufacturerId = Integer.parseInt(el.getElementsByTagName("ID").item(0).getTextContent());
                    String fullName = el.getElementsByTagName("Name").item(0).getTextContent();
                    String phone = el.getElementsByTagName("Phone").item(0).getTextContent();
                    String address = el.getElementsByTagName("Address").item(0).getTextContent();
                    String email = el.getElementsByTagName("Email").item(0).getTextContent();

                    // Log data being processed
                    

                    // Check if the manufacturer already exists
                    if (ManufacturerControl.getInstance().getManufacturerById(manufacturerId) != null) {
                        throw new IllegalArgumentException("Manufacturer with ID " + manufacturerId + " already exists.");
                    }

                    // Add manufacturer to the database
                    if (!ManufacturerControl.getInstance().addManufacturer(
                            manufacturerId, fullName, Long.parseLong(phone), address, email)) {
                        errors++;
                        System.out.println("Failed to add Manufacturer: " + manufacturerId);
                    } else {
                        System.out.println("Manufacturer added successfully: " + manufacturerId);
                    }
                }
            }

            System.out.println((errors == 0) ? "Manufacturers imported successfully!" :
                    String.format("Manufacturers imported with %d errors.", errors));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Duplicate Data Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    


    private static String getxmlPath() {
        try {
            String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String decoded = URLDecoder.decode(path, "UTF-8");
            if (decoded.contains(".jar")) {
                decoded = decoded.substring(0, decoded.lastIndexOf('/'));
                String dbPath = decoded + "/database/data.xml";
                
                return dbPath;
            } else {
                decoded = decoded.substring(0, decoded.lastIndexOf("/bin"));
                String dbPath = decoded + "/src/entity/data.xml";
                
                return dbPath;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    

    
}
