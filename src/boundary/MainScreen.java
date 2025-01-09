package boundary;


import javax.swing.*;


@SuppressWarnings("serial")
public class MainScreen extends JFrame {

    public MainScreen() {
        setTitle("Main Screen with Background");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load the background image from the provided path
        ImageIcon backgroundIcon = new ImageIcon("C:\\Users\\Admin\\mainbackground.jpg");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 900, 700);

        // Create a layered pane to hold the background and components
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        // Add the background image to the lowest layer
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Add buttons
        JButton manageWinesButton = new JButton("Manage Wines");
        manageWinesButton.setBounds(300, 100, 200, 50);
        layeredPane.add(manageWinesButton, Integer.valueOf(1));

        JButton manageManufacturersButton = new JButton("Manage Manufacturers");
        manageManufacturersButton.setBounds(300, 250, 200, 50);
        layeredPane.add(manageManufacturersButton, Integer.valueOf(1));

       

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(300, 399, 200, 50);
        layeredPane.add(exitButton, Integer.valueOf(1));

        // Add action listeners
        manageWinesButton.addActionListener(e -> openManageWinesScreen());
        manageManufacturersButton.addActionListener(e -> openManageManufacturersScreen());
       
        exitButton.addActionListener(e -> System.exit(0));

        // Add the layered pane to the frame
        setContentPane(layeredPane);
    }

    private void openManageWinesScreen() {
        // Open the Manage Wines screen
        WineScreen wineScreen = new WineScreen();
        wineScreen.setVisible(true);
        dispose();
    }

    private void openManageManufacturersScreen() {
        // Open the Manage Manufacturers screen
        ManufacturerScreen manufacturerScreen = new ManufacturerScreen();
        manufacturerScreen.setVisible(true);
        dispose();
    }

   
    



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainScreen mainScreen = new MainScreen();
            mainScreen.setVisible(true);
        });
    }
}
