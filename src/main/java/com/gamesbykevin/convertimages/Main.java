package com.gamesbykevin.convertimages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    public static final String[] EXTENSIONS = {"png", "jpg", "jpeg", "gif"};
    public static final String DELIMITER = "\\.";
    public static final String DIR_GRAY = "\\gray\\";
    public static final String DIR_BLACK = "\\black\\";
    public static final String FORMAT = "png";

    public static void main(String[] args) {

        Main main = new Main();

        if (args.length < 1) {
            //check current directory if no args provided
            main.convertDir(".");
        } else {
            for (String path : args) {
                main.convertDir(path);
            }
        }
    }

    private void convertDir(String path) {
        convertDir(new File(path));
    }

    private void convertDir(File folder) {

        File[] files = folder.listFiles();

        for (File file : files) {

            if (file.isDirectory()) {
                convertDir(file);
            } else {
                if (!isValid(file))
                    continue;

                try {
                    String srcPath = file.getAbsolutePath();
                    String filename = getFilename(srcPath);
                    srcPath = srcPath.replace(filename, "");
                    srcPath = srcPath.replace("\\.\\", "");

                    //convert image to black/white and gray scale
                    BufferedImage src = ImageIO.read(file);

                    try {
                        BufferedImage imageGray  = convert(src, BufferedImage.TYPE_BYTE_GRAY);
                        write(imageGray, srcPath + DIR_GRAY, filename);
                    } catch (Exception ex1) {
                        ex1.printStackTrace();
                    }

                    try {
                        BufferedImage imageBlack = convert(src, BufferedImage.TYPE_BYTE_BINARY);
                        write(imageBlack, srcPath + DIR_BLACK, filename);
                    } catch (Exception ex2) {
                        ex2.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void write(BufferedImage image, String dir, String filename) {
        try {
            new File(dir).mkdirs();
            ImageIO.write(image, FORMAT, new File(dir + filename));
            System.out.println(dir + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BufferedImage convert(BufferedImage src, int type) {
        BufferedImage converted = new BufferedImage(src.getWidth(), src.getHeight(), type);
        Graphics2D graphics = converted.createGraphics();
        graphics.drawImage(src, 0, 0, converted.getWidth(), converted.getHeight(), null);

        BufferedImage result = new BufferedImage(converted.getWidth(), converted.getHeight(), BufferedImage.TYPE_INT_ARGB);
        graphics = result.createGraphics();
        graphics.drawImage(converted, 0, 0, result.getWidth(), result.getHeight(), null);

        return result;
    }

    private String getFilename(String path) {
        String[] data = path.split("\\\\");
        return data[data.length - 1];
    }

    private boolean isValid(File file) {
        String[] data = file.getAbsolutePath().split(DELIMITER);
        if (data.length < 1)
            return false;
        for (String extension : EXTENSIONS) {
            if (data[data.length - 1].equalsIgnoreCase(extension))
                return true;
        }
        return false;
    }
}