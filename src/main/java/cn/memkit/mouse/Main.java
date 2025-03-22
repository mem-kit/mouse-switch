package cn.memkit.mouse;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.*;

import static cn.memkit.mouse.Switch.loadCurse;

public class Main {
    private static final JLabel BANNER = new JLabel("Select mouse orientation:");

    static {
        BANNER.setForeground(RoundBorder.PRIMARY_BG);
    }


    public static void main(String[] args) {
        // Check if the system tray is supported
        if (!SystemTray.isSupported()) {
            System.out.println("System tray is not supported");
            return;
        }

        // Create the frame
        JFrame frame = new JFrame("Mouse Orientation Switcher");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(320, 200);
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create the label
        BANNER.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(BANNER, gbc);

        // Create the left button
        JButton leftButton = ButtonBuilder.primaryButton("Left");
        leftButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadCurse(HAND.LEFT);
                Main.BANNER.setText("Mouse Orientation: Left");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(leftButton, gbc);

        // Create the right button
        JButton rightButton = ButtonBuilder.primaryButton("Right");
        rightButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadCurse(HAND.RIGHT);
                Main.BANNER.setText("Mouse Orientation: Right");
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(rightButton, gbc);

        // Load the icon image
        Image image = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/icon.png"));

        // Set the frame icon
        frame.setIconImage(image);

        // Create the system tray icon
        SystemTray tray = SystemTray.getSystemTray();
        TrayIcon trayIcon = new TrayIcon(image, "Mouse Orientation Switcher");
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
                frame.setExtendedState(JFrame.NORMAL);
            }
        });
        //

        // Create a popup menu
        PopupMenu popup = new PopupMenu();

// Create the "About" menu item
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JEditorPane editorPane = new JEditorPane("text/html", "<html>Mouse Switcher v1.0<br>Developed by MemKit.cn<br><a href='https://github.com/mem-kit/mouse-switch'>https://github.com/mem-kit/mouse-switch</a></html>");
                editorPane.setEditable(false);
                editorPane.setOpaque(false);
                editorPane.addHyperlinkListener(new HyperlinkListener() {
                    @Override
                    public void hyperlinkUpdate(HyperlinkEvent event) {
                        if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                            try {
                                Desktop.getDesktop().browse(event.getURL().toURI());
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
                JOptionPane.showMessageDialog(frame, editorPane, "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        popup.add(aboutItem);

// Create the "Exit" menu item
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        popup.add(exitItem);

// Attach the popup menu to the tray icon
        trayIcon.setPopupMenu(popup);

        // Add the tray icon to the system tray
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

        // Add a window listener to hide the frame when minimized
        frame.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if (e.getNewState() == Frame.ICONIFIED) {
                    frame.setVisible(false);
                }
            }
        });

        // Set the frame visibility
        frame.setVisible(true);
    }
}