package me.eden.extractor.utils;

import javax.swing.*;
import java.io.File;

public class GifFrameExtractorUtils {

    public static String getFileNameWithoutExtension(String filePath) {
        File file = new File(filePath);

        String fileNameWithExtension = file.getName();

        if (fileNameWithExtension.endsWith(".gif")) {
            return fileNameWithExtension.substring(0, fileNameWithExtension.length() - 4);
        } else {
            return fileNameWithExtension;
        }
    }

    public static void changeFramesFolderDestiny(JLabel folderLabel, File file) {
        folderLabel.setText(file.getAbsolutePath());
        folderLabel.putClientProperty("selectedFolder", file);
    }

}
