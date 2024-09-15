package me.eden.pack;

import java.io.File;

public class ResourcePackGeneratorUtils {

    protected static File createFile(File parent, String name) {
        File file = new File(parent, name);

        boolean wasSuccessful = file.mkdir();
        if (wasSuccessful) System.out.println(name + "was successfully created.");

        return file;
    }

}
