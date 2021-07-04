package pers.kindem.gh.avatar;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Saver {
    public static void saveImage(BufferedImage bufferedImage, String file) {
        boolean success = false;
        try {
            ImageIO.write(bufferedImage, fileExtension(file), new File(file));
            success = true;
        } catch (IOException ignored) {
            System.out.println("failed to save image to file");
        }
    }

    private static String fileExtension(String file) {
        return file.substring(file.lastIndexOf(".") + 1);
    }
}
