package me.eden;

import me.eden.extractor.listeners.ChangeDestinyJButtonListener;
import me.eden.extractor.listeners.ExtractFramesJButtonListener;
import me.eden.extractor.utils.GifFrameExtractorUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class Main {
    public static JLabel folderLabel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Minecraft Gif Packager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 500);
        frame.setLocation(500, 200);
        frame.setLayout(new BorderLayout());

        URL url = Main.class.getResource("/icons/icon.png");
        if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            frame.setIconImage(icon.getImage());
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 3));

        JLabel routeLabel = new JLabel("Insert GIF file route below...");
        routeLabel.setHorizontalAlignment(JTextField.CENTER);

        JTextField gifPathField = new JTextField();
        gifPathField.setHorizontalAlignment(JTextField.CENTER);

        JButton extractButton = new JButton("Extract Frames");
        extractButton.setHorizontalAlignment(JTextField.CENTER);
        extractButton.addActionListener(new ExtractFramesJButtonListener(gifPathField, frame));

        String userHome = System.getProperty("user.home");
        String documentsPath = userHome + File.separator + "Documents";
        File documentsFolder = new File(documentsPath);

        File outputFolder = new File(documentsFolder, "MinecraftGifPackager");
        if (!outputFolder.exists()) {
            boolean wasSuccessful = outputFolder.mkdirs();
            if (wasSuccessful) System.out.println("The file has been created successfully: " + outputFolder.getName());
        }

        folderLabel = new JLabel();
        folderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GifFrameExtractorUtils.changeFramesFolderDestiny(folderLabel, outputFolder);

        JButton folderSelectionButton = new JButton("Select the destination folder...");
        folderSelectionButton.setHorizontalAlignment(SwingConstants.CENTER);
        folderSelectionButton.addActionListener(new ChangeDestinyJButtonListener(folderLabel, frame));

        Component[] components = {routeLabel, gifPathField, folderLabel, folderSelectionButton, extractButton};
        for (Component component : components) {
            for (int col = 0; col < 3; col++) {
                if (col == 1) panel.add(component);
                else panel.add(new JLabel(""));
            }
        }

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

}