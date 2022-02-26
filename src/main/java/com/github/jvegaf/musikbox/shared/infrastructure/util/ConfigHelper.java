package com.github.jvegaf.musikbox.shared.infrastructure.util;

import java.io.File;
import java.nio.file.Files;

public class ConfigHelper {


    public static String workDirectoryChecker() {
        String workingDirectory = "";
        //here, we assign the name of the OS, according to Java, to a variable...
        String OS = (System.getProperty("os.name")).toUpperCase();
        //to determine what the workingDirectory is.
        //if it is some version of Windows
        if (OS.contains("WIN"))
        {
            //it is simply the location of the "AppData" folder
            workingDirectory = System.getenv("AppData") + "\\" + "MusikBOX";
        }

        if (OS.contains("MAC")) {
            workingDirectory = System.getProperty("user.home") + "/Library/Application " + "Support"
                          + "MusikBOX";
        }
        if (OS.contains("NUX")) {
            workingDirectory = System.getProperty("user.dir") + ".MusikBOX";
        }

        File directory = new File(workingDirectory);

        if (!directory.exists()) {
            directory.mkdir();
            System.out.println("Could not find folder so created it");
        }

        return workingDirectory;

    }
}
