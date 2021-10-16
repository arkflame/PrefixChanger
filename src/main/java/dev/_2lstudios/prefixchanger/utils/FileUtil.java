package dev._2lstudios.prefixchanger.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class FileUtil {
    public static String getBaseName(String fileName) {
        int pos = fileName.lastIndexOf(".");
        if (pos > 0 && pos < (fileName.length() - 1)) { // If '.' is not the first or last character.
            fileName = fileName.substring(0, pos);
        }
        return fileName;
    }

    public static String getBaseName(final File file) {
        return getBaseName(file.getName());
    }

    public static void extractFile(final File target, final String name, boolean overwrite) throws IOException {
        if (target.exists() && !overwrite) {
            return;
        }

        final InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(name);
        Files.copy(is, target.getAbsoluteFile().toPath());
        is.close();
    }

    public static void extractFile(final File target, final String name) throws IOException {
        extractFile(target, name, false);
    }
}