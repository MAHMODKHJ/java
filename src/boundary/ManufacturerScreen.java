package boundary;

import control.ManufacturerControl;
import control.XMLControl;
import entity.Manufacturer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.util.List;

@SuppressWarnings("serial")
public class ManufacturerScreen extends JFrame {
    private JTextField idField, nameField, phoneField, addressField, emailField, searchField;
    private DefaultTableModel tableModel;
    private JTable manufacturerTable;
    private JScrollPane scrollPane;

    public ManufacturerScreen() {
        setTitle("Manage Manufacturers");
        setSize(900,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load the background image
        
        JLabel backgroundLabel = new JLabel(new ImageIcon("C:\\Users\\Admin\\Manufacturer screen.jpg"));
        backgroundLabel.setBounds(0, 0, 1000, 800);

        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1000, 800);

        // Add the background to the lowest layer
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Add components on top of the background
        addMenuBar(layeredPane);
        createForm(layeredPane);
        createTable(layeredPane);
        createButtons(layeredPane);

        // Add the layered pane to the frame
        setContentPane(layeredPane);
    }

    private void addMenuBar(JLayeredPane layeredPane) {
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

        JMenuItem manageWines = new JMenuItem("Manage Wines");
        manageWines.addActionListener(e -> {
            WineScreen wineScreen = new WineScreen();
            wineScreen.setVisible(true);
            this.dispose();
        });
        manageMenu.add(manageWines);

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
        JLabel idLabel = new JLabel("Manufacturer ID:");
        idLabel.setBounds(50, 60, 150, 25);
        idLabel.setForeground(new Color(255, 255, 255)); // Adjust font color for visibility
        layeredPane.add(idLabel, Integer.valueOf(1));

        idField = new JTextField();
        idField.setBounds(200, 60, 200, 25);
        layeredPane.add(idField, Integer.valueOf(1));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 100, 150, 25);
        nameLabel.setForeground(new Color(255, 255, 255)); // Adjust font color for visibility
        layeredPane.add(nameLabel, Integer.valueOf(1));

        nameField = new JTextField();
        nameField.setBounds(200, 100, 200, 25);
        layeredPane.add(nameField, Integer.valueOf(1));

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 140, 150, 25);
        phoneLabel.setForeground(new Color(255, 255, 255)); // Adjust font color for visibility
        layeredPane.add(phoneLabel, Integer.valueOf(1));

        phoneField = new JTextField();
        phoneField.setBounds(200, 140, 200, 25);
        layeredPane.add(phoneField, Integer.valueOf(1));

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(413, 100, 150, 25);
        addressLabel.setForeground(new Color(255, 255, 255)); // Adjust font color for visibility
        layeredPane.add(addressLabel, Integer.valueOf(1));

        addressField = new JTextField();
        addressField.setBounds(507, 100, 200, 25);
        layeredPane.add(addressField, Integer.valueOf(1));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(430, 60, 150, 25);
        emailLabel.setForeground(new Color(255, 255, 255)); // Adjust font color for visibility
        layeredPane.add(emailLabel, Integer.valueOf(1));

        emailField = new JTextField();
        emailField.setBounds(507, 60, 200, 25);
        layeredPane.add(emailField, Integer.valueOf(1));

        JLabel searchLabel = new JLabel("Search by ID:");
        searchLabel.setBackground(new Color(64, 0, 64));
        searchLabel.setBounds(535, 225, 100, 25);
        searchLabel.setForeground(new Color(255, 255, 255)); // Adjust font color for visibility
        layeredPane.add(searchLabel, Integer.valueOf(1));

        searchField = new JTextField();
        searchField.setBounds(665, 225, 86, 25);
        layeredPane.add(searchField, Integer.valueOf(1));
        
        
    }

    private void createTable(JLayeredPane layeredPane) {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Address");
        tableModel.addColumn("Email");

        manufacturerTable = new JTable(tableModel) {
            @Override
            public boolean isOpaque() {
                return false; // Ensure the table background is transparent
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                // Alternate row colors for better readability
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(255, 255, 255, 180) : new Color(220, 220, 220, 180));
                } else {
                    c.setBackground(new Color(0, 0, 0, 150)); // Highlight selected row
                }

                c.setForeground(Color.WHITE); // Set text color to white for visibility

                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(false); // Ensure transparency
                }
                return c;
            }
        };

        // Add a MouseListener to populate fields on row click
        manufacturerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = manufacturerTable.getSelectedRow();
                if (selectedRow >= 0) {
                    idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    phoneField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    addressField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    emailField.setText(tableModel.getValueAt(selectedRow, 4).toString());
                }
            }
        });

        manufacturerTable.getTableHeader().setOpaque(false);
        manufacturerTable.getTableHeader().setBackground(new Color(0, 0, 0, 150));
        manufacturerTable.getTableHeader().setForeground(Color.WHITE);
        manufacturerTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));

        scrollPane = new JScrollPane(manufacturerTable);
        scrollPane.setBounds(50, 300, 800, 350); // Adjust size and position
        scrollPane.setOpaque(false); // Make scroll pane transparent
        scrollPane.getViewport().setOpaque(false); // Make viewport transparent
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove borders

        layeredPane.add(scrollPane, Integer.valueOf(1));
    }



    private void createButtons(JLayeredPane layeredPane) {
        JButton addButton = new JButton("Add");
        addButton.setBounds(10, 272, 100, 25);
        layeredPane.add(addButton, Integer.valueOf(1));
        addButton.addActionListener(e -> {
        	addManufacturer();
        	clearFields(); 
        });

        JButton importManufacturersButton = new JButton("Import Manufacturers");
        importManufacturersButton.setBounds(676, 10, 200, 25);
        importManufacturersButton.addActionListener(e -> importManufacturers());
        layeredPane.add(importManufacturersButton, Integer.valueOf(1));

        JButton removeButton = new JButton("Remove");
        removeButton.setBounds(165, 272, 100, 25);
        layeredPane.add(removeButton, Integer.valueOf(1));
        removeButton.addActionListener(e -> {
            removeManufacturer(); // Or removeWine() for the wine screen
            clearFields();        // Clear all input fields after removing
        });


        JButton searchButton = new JButton("Search");
        searchButton.setBounds(776, 225, 100, 25);
        layeredPane.add(searchButton, Integer.valueOf(1));
        searchButton.addActionListener(e -> searchManufacturer());

        JButton showButton = new JButton("Show");
        showButton.setBounds(337, 272, 100, 25);
        layeredPane.add(showButton, Integer.valueOf(1));
        showButton.addActionListener(e -> showTable());

        JButton hideButton = new JButton("Hide");
        hideButton.setBounds(482, 272, 100, 25);
        layeredPane.add(hideButton, Integer.valueOf(1));
        hideButton.addActionListener(e -> {
        	
        hideTable();
        clearFields();                // Clear all input fields
        });

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(701, 272, 100, 25);
        layeredPane.add(updateButton, Integer.valueOf(1));
        updateButton.addActionListener(e -> {
            updateManufacturer(); // Or updateWine() for the wine screen
            clearFields();        // Clear all input fields after updating
        });

    }
    
    private void importManufacturers() {
        try {
            XMLControl.getInstance().importManufacturersFromXML(XMLControl.XML_FILEPATH);
            JOptionPane.showMessageDialog(this, "Manufacturers data imported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshManufacturerTable(); // Refresh the table after importing
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Duplicate Data Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to import manufacturers data. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showTable() {
        scrollPane.setVisible(true);
        refreshManufacturerTable();
    }

    private void hideTable() {
        scrollPane.setVisible(false);
    }

    private void refreshManufacturerTable() {
        List<Manufacturer> manufacturers = ManufacturerControl.getInstance().getAllManufacturers();
        tableModel.setRowCount(0);

        for (Manufacturer m : manufacturers) {
            tableModel.addRow(new Object[]{
                    m.getManufacturerID(),
                    m.getFullName(),
                    m.getPhoneNumber(),
                    m.getAddress(),
                    m.getEmail()
            });
        }
    }

    private void addManufacturer() {
        String idText = idField.getText().trim();
        String name = nameField.getText().trim();
        String phoneText = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String email = emailField.getText().trim();

        if (idText.isEmpty() || name.isEmpty() || phoneText.isEmpty() || address.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            long phone = Long.parseLong(phoneText);
            if (ManufacturerControl.getInstance().addManufacturer(id, name, phone, address, email)) {
                JOptionPane.showMessageDialog(this, "Manufacturer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshManufacturerTable(); // Refresh table after adding
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add manufacturer. ID may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. ID must be an integer, and phone must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeManufacturer() {
        int selectedRow = manufacturerTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a manufacturer to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int manufacturerId = (int) tableModel.getValueAt(selectedRow, 0);
        if (ManufacturerControl.getInstance().removeManufacturer(manufacturerId)) {
            JOptionPane.showMessageDialog(this, "Manufacturer removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshManufacturerTable(); // Refresh table after deleting
        } else {
            JOptionPane.showMessageDialog(this, "Failed to remove manufacturer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void searchManufacturer() {
        String searchIdText = searchField.getText().trim();
        if (searchIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Manufacturer ID to search.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int searchId = Integer.parseInt(searchIdText);
            Manufacturer manufacturer = ManufacturerControl.getInstance().getManufacturerById(searchId);
            if (manufacturer != null) {
                tableModel.setRowCount(0);
                tableModel.addRow(new Object[]{
                        manufacturer.getManufacturerID(),
                        manufacturer.getFullName(),
                        manufacturer.getPhoneNumber(),
                        manufacturer.getAddress(),
                        manufacturer.getEmail()
                });
                scrollPane.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "No manufacturer found with ID: " + searchId, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Manufacturer ID format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateManufacturer() {
        String idText = idField.getText().trim();
        String name = nameField.getText().trim();
        String phoneText = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String email = emailField.getText().trim();

        if (idText.isEmpty() || name.isEmpty() || phoneText.isEmpty() || address.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            long phone = Long.parseLong(phoneText);
            if (ManufacturerControl.getInstance().updateManufacturer(id, name, (int) phone, address, email)) {
                JOptionPane.showMessageDialog(this, "Manufacturer updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshManufacturerTable(); // Refresh table after updating
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update manufacturer.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. ID must be an integer, and phone must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        phoneField.setText("");
        addressField.setText("");
        emailField.setText("");
    }


    

    public static void main(String[] args) {
        ManufacturerScreen manufacturerScreen = new ManufacturerScreen();
        manufacturerScreen.setVisible(true);
    }
}
