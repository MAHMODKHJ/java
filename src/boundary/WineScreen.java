package boundary;

import entity.Wine;
import control.WineControl;
import control.XMLControl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.util.List;

@SuppressWarnings("serial")
public class WineScreen extends JFrame {
    private JTextField catalogNumberField, nameField, descriptionField, priceField, productionYearField,
            manufacturerIdField, sweetnessLevelField, imageField, searchField;
    private DefaultTableModel tableModel;
    private JTable wineTable;
    private JScrollPane scrollPane; // For hiding and showing the table

    public WineScreen() {
        setTitle("Manage Wines");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the layout to null for custom component positioning
        getContentPane().setLayout(null);

        // Load the background image
       
        JLabel backgroundLabel = new JLabel(new ImageIcon("C:\\Users\\Admin\\wine background.jpg"));
        backgroundLabel.setBounds(0, 0, 900, 700);

        // Create a layered pane to manage the background and other components
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 900, 700);

        // Add the background to the lowest layer
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Add components to the layered pane
        addMenuBar();
        createForm(layeredPane);
        createTable(layeredPane);
        createButtons(layeredPane);

        // Add the layered pane to the frame
        setContentPane(layeredPane);
    }

    private void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu manageMenu = new JMenu("Manage");
        menuBar.add(manageMenu);

        JMenuItem mainMenu = new JMenuItem("Main Menu");
        mainMenu.addActionListener(e -> {
            MainScreen mainScreen = new MainScreen();
            mainScreen.setVisible(true);
            this.dispose();
        });
        manageMenu.add(mainMenu);

        JMenuItem manageManufacturers = new JMenuItem("Manage Manufacturers");
        manageManufacturers.addActionListener(e -> {
            ManufacturerScreen manufacturerScreen = new ManufacturerScreen();
            manufacturerScreen.setVisible(true);
            this.dispose();
        });
        manageMenu.add(manageManufacturers);

        JMenuItem exitApp = new JMenuItem("Exit");
        exitApp.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        manageMenu.add(exitApp);

        setJMenuBar(menuBar);
    }

    private void createForm(JLayeredPane layeredPane) {
        JLabel catalogNumberLabel = new JLabel("Catalog Number:");
        catalogNumberLabel.setBounds(50, 60, 120, 25);
        layeredPane.add(catalogNumberLabel, Integer.valueOf(1));

        catalogNumberField = new JTextField();
        catalogNumberField.setBounds(180, 60, 200, 25);
        layeredPane.add(catalogNumberField, Integer.valueOf(1));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 100, 120, 25);
        layeredPane.add(nameLabel, Integer.valueOf(1));

        nameField = new JTextField();
        nameField.setBounds(180, 100, 200, 25);
        layeredPane.add(nameField, Integer.valueOf(1));

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(50, 140, 120, 25);
        layeredPane.add(descriptionLabel, Integer.valueOf(1));

        descriptionField = new JTextField();
        descriptionField.setBounds(180, 140, 200, 25);
        layeredPane.add(descriptionField, Integer.valueOf(1));

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(50, 180, 120, 25);
        layeredPane.add(priceLabel, Integer.valueOf(1));

        priceField = new JTextField();
        priceField.setBounds(180, 180, 200, 25);
        layeredPane.add(priceField, Integer.valueOf(1));

        JLabel productionYearLabel = new JLabel("Production Year:");
        productionYearLabel.setBounds(450, 60, 120, 25);
        layeredPane.add(productionYearLabel, Integer.valueOf(1));

        productionYearField = new JTextField();
        productionYearField.setBounds(580, 60, 200, 25);
        layeredPane.add(productionYearField, Integer.valueOf(1));

        JLabel manufacturerIdLabel = new JLabel("Manufacturer ID:");
        manufacturerIdLabel.setBounds(450, 100, 120, 25);
        layeredPane.add(manufacturerIdLabel, Integer.valueOf(1));

        manufacturerIdField = new JTextField();
        manufacturerIdField.setBounds(580, 100, 200, 25);
        layeredPane.add(manufacturerIdField, Integer.valueOf(1));

        JLabel sweetnessLevelLabel = new JLabel("Sweetness Level:");
        sweetnessLevelLabel.setBounds(450, 140, 120, 25);
        layeredPane.add(sweetnessLevelLabel, Integer.valueOf(1));

        sweetnessLevelField = new JTextField();
        sweetnessLevelField.setBounds(580, 140, 200, 25);
        layeredPane.add(sweetnessLevelField, Integer.valueOf(1));

        JLabel imageLabel = new JLabel("Image:");
        imageLabel.setBounds(50, 220, 120, 25);
        layeredPane.add(imageLabel, Integer.valueOf(1));

        imageField = new JTextField();
        imageField.setBounds(180, 220, 200, 25);
        layeredPane.add(imageField, Integer.valueOf(1));

        JLabel searchLabel = new JLabel("Search by Catalog #:");
        searchLabel.setBounds(450, 220, 150, 25);
        layeredPane.add(searchLabel, Integer.valueOf(1));

        searchField = new JTextField();
        searchField.setBounds(610, 220, 170, 25);
        layeredPane.add(searchField, Integer.valueOf(1));
    }

    private void createTable(JLayeredPane layeredPane) {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Catalog Number");
        tableModel.addColumn("Name");
        tableModel.addColumn("Description");
        tableModel.addColumn("Price");
        tableModel.addColumn("Production Year");
        tableModel.addColumn("Manufacturer ID");
        tableModel.addColumn("Sweetness Level");
        tableModel.addColumn("Image");

        wineTable = new JTable(tableModel) {
            @Override
            public boolean isOpaque() {
                return false; // Make the table background transparent
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setForeground(Color.WHITE);
                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(false); // Make cells transparent
                }
                return c;
            }
        };

        // Add a MouseListener to populate fields on row click
        wineTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = wineTable.getSelectedRow();
                if (selectedRow >= 0) {
                    catalogNumberField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    descriptionField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    priceField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    productionYearField.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    manufacturerIdField.setText(tableModel.getValueAt(selectedRow, 5).toString());
                    sweetnessLevelField.setText(tableModel.getValueAt(selectedRow, 6).toString());
                    imageField.setText(tableModel.getValueAt(selectedRow, 7).toString());
                }
            }
        });

        wineTable.getTableHeader().setOpaque(false);
        wineTable.getTableHeader().setBackground(new Color(0, 0, 0, 150));
        wineTable.getTableHeader().setForeground(Color.WHITE);
        wineTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));

        scrollPane = new JScrollPane(wineTable);
        scrollPane.setBounds(50, 300, 800, 350);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        layeredPane.add(scrollPane, Integer.valueOf(1));
    }




    private void createButtons(JLayeredPane layeredPane) {
        JButton addButton = new JButton("Add");
        addButton.setBounds(10, 272, 100, 25);
        addButton.addActionListener(e -> {
        	addWine();
            clearFields();        
        });
        layeredPane.add(addButton, Integer.valueOf(1));

        JButton importWinesButton = new JButton("Import Wines");
        importWinesButton.setBounds(676,10,200,25);
        importWinesButton.addActionListener(e -> importWines());
        layeredPane.add(importWinesButton, Integer.valueOf(1));
        JButton removeButton = new JButton("Remove");
        removeButton.setBounds(165, 272, 100, 25);
        removeButton.addActionListener(e -> 
        { removeWine();
        clearFields();        
        });
        layeredPane.add(removeButton, Integer.valueOf(1));

        JButton showButton = new JButton("Show");
        showButton.setBounds(337, 272, 100, 25);
        showButton.addActionListener(e -> showTable());
        layeredPane.add(showButton, Integer.valueOf(1));

        JButton hideButton = new JButton("Hide");
        hideButton.setBounds(482, 272, 100, 25);
        hideButton.addActionListener(e -> {
        	scrollPane.setVisible(false);
        clearFields();        
        });
        
        layeredPane.add(hideButton, Integer.valueOf(1));

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(800, 220, 100, 25);
        searchButton.addActionListener(e -> searchWine());
        layeredPane.add(searchButton, Integer.valueOf(1));

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(701, 272, 100, 25);
        updateButton.addActionListener(e -> {
        updateWine();
        clearFields();        
        });
        layeredPane.add(updateButton, Integer.valueOf(1));
    }


    private void importWines() {
        try {
            XMLControl.getInstance().importWinesFromXML(XMLControl.XML_FILEPATH);
            JOptionPane.showMessageDialog(this, "Wines data imported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshWineTable(); // Refresh the table after importing
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Duplicate Data Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to import wines data. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void showTable() {
        scrollPane.setVisible(true);
        refreshWineTable();
    }

    private void refreshWineTable() {
        List<Wine> wines = WineControl.getInstance().getAllWines();
        tableModel.setRowCount(0);

        for (Wine wine : wines) {
            tableModel.addRow(new Object[]{
                    wine.getCatalogNumber(),
                    wine.getName(),
                    wine.getDescription(),
                    wine.getPrice(),
                    wine.getProductionYear(),
                    wine.getManufacturerId(),
                    wine.getSweetnessLevel(),
                    wine.getImage()
            });
        }
    }
    private void addWine() {
        String catalogNumber = catalogNumberField.getText().trim();
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String priceText = priceField.getText().trim();
        String productionYearText = productionYearField.getText().trim();
        String manufacturerIdText = manufacturerIdField.getText().trim();
        String sweetnessLevel = sweetnessLevelField.getText().trim();
        String image = imageField.getText().trim();

        // Validate input fields
        if (catalogNumber.isEmpty() || name.isEmpty() || description.isEmpty() ||
                priceText.isEmpty() || productionYearText.isEmpty() ||
                manufacturerIdText.isEmpty() || sweetnessLevel.isEmpty() || image.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int price = Integer.parseInt(priceText);
            int productionYear = Integer.parseInt(productionYearText);
            int manufacturerId = Integer.parseInt(manufacturerIdText);

            // Create Wine object
            @SuppressWarnings("unused")
			Wine wine = new Wine(catalogNumber, name, description, productionYear, price, sweetnessLevel, image, manufacturerId);

            // Use WineControl to add wine
            if (WineControl.getInstance().addWine(catalogNumber, name, description, productionYear, price, sweetnessLevel, image, manufacturerId)) {
                JOptionPane.showMessageDialog(this, "Wine added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshWineTable(); // Refresh table after adding
                clearFields(); // Clear input fields
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add wine. Catalog number may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Price, production year, and manufacturer ID must be numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeWine() {
        int selectedRow = wineTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a wine to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String catalogNumber = (String) tableModel.getValueAt(selectedRow, 0);
        if (WineControl.getInstance().removeWine(catalogNumber)) {
            JOptionPane.showMessageDialog(this, "Wine removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshWineTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to remove wine.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    

    private void searchWine() {
        String catalogNumber = searchField.getText().trim();
        if (catalogNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a catalog number to search.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Wine wine = WineControl.getInstance().getWineByCatalogNumber(catalogNumber);
        if (wine != null) {
            tableModel.setRowCount(0); // Clear the table
            tableModel.addRow(new Object[]{
                    wine.getCatalogNumber(),
                    wine.getName(),
                    wine.getDescription(),
                    wine.getPrice(),
                    wine.getProductionYear(),
                    wine.getManufacturerId(),
                    wine.getSweetnessLevel(),
                    wine.getImage()
            });

            scrollPane.setVisible(true); // Make the table visible
        } else {
            JOptionPane.showMessageDialog(this, "Wine not found.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    
    
    private void updateWine() {
        int selectedRow = wineTable.getSelectedRow(); // Get the selected row
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a wine to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String catalogNumber = catalogNumberField.getText().trim();
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String priceText = priceField.getText().trim();
        String productionYearText = productionYearField.getText().trim();
        String manufacturerIdText = manufacturerIdField.getText().trim();
        String sweetnessLevel = sweetnessLevelField.getText().trim();
        String image = imageField.getText().trim();

        if (catalogNumber.isEmpty() || name.isEmpty() || description.isEmpty() ||
                priceText.isEmpty() || productionYearText.isEmpty() ||
                manufacturerIdText.isEmpty() || sweetnessLevel.isEmpty() || image.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int price = Integer.parseInt(priceText);
            int productionYear = Integer.parseInt(productionYearText);
            int manufacturerId = Integer.parseInt(manufacturerIdText);

            if (WineControl.getInstance().updateWine(catalogNumber, name, description, productionYear, price, sweetnessLevel, image, manufacturerId)) {
                JOptionPane.showMessageDialog(this, "Wine updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshWineTable();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update wine.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Price, production year, and manufacturer ID must be numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void clearFields() {
        catalogNumberField.setText("");
        nameField.setText("");
        descriptionField.setText("");
        priceField.setText("");
        productionYearField.setText("");
        manufacturerIdField.setText("");
        sweetnessLevelField.setText("");
        imageField.setText("");
    }

   
    


    public static void main(String[] args) {
        WineScreen wineScreen = new WineScreen();
        wineScreen.setVisible(true);
    }
}
