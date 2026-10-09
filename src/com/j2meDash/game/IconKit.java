package com.j2meDash.game;

import java.io.*;

import javax.microedition.lcdui.*;

public class IconKit {
    private Image template;
    private OutputStream os;
    public Image changeIcon(String path, int primaryColor, int secondaryColor, int glowColor) {
        try {
            template = Image.createImage(path);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ERROR] Cannot find the path specified");
            return null;
        }
        int w = template.getWidth();
        int h = template.getHeight();
        int[] data = new int[w * h];
        template.getRGB(data, 0, w, 0, 0, w, h);
        for (int i = 0; i < data.length; i++) {
            int v = data[i];
            switch (v) {
                case 0xFFFFFFFF: data[i] = 0x00000000; break;
                case 0xFF000000: break;
                case 0xFF808080: data[i] = 0xFF000000 | glowColor; break;
                case 0xFF404040: data[i] = 0xFF000000 | primaryColor; break;
                case 0xFFD0D0D0: data[i] = 0xFF000000 | secondaryColor; break;
                default: System.out.println("[ERROR] Detected invalid color layout in file: " + path); return null;
            }
        }
        System.out.println("[INFO] Changed player icon to template: " + path + " - primaryColor = " + primaryColor + " - secondaryColor = " + secondaryColor + " - glowColor = " + glowColor);
        return Image.createRGBImage(data, w, h, true);
    }
    public void exportBMPIcon(Image image, int size, String output) {
        int[] file_header = {'B','M',
                             (size & 0xFF),((size >> 8) & 0xFF), ((size >> 16) & 0xFF), ((size >> 24) & 0xFF),
                             0x00, 0x00, 0x00, 0x00,
                             0x36, 0x00, 0x00, 0x00};
        int[] info_header = {0x28, 0x00, 0x00, 0x00,
                             0x14, 0x00, 0x00, 0x00,
                             0x14, 0x00, 0x00, 0x00,
                             0x01, 0x00, 0x18, 0x00,
                             0x00, 0x00, 0x00, 0x00,
                             0xB0, 0x04, 0x00, 0x00,
                             0xC4, 0x0E, 0x00, 0x00,
                             0xC4, 0x0E, 0x00, 0x00,
                             0x00, 0x00, 0x00, 0x00,
                             0x00, 0x00, 0x00, 0x00};
        int w = image.getWidth();
        int h = image.getHeight();
        int[] argbImageData = new int[w * h];
        image.getRGB(argbImageData, 0, w, 0, 0, w, h);
        for (int i = 0; i < argbImageData.length; i++) {
            if (argbImageData[i] == 0x00000000) argbImageData[i] = 0xFFFFFFFF;
        }
        int index = 0;
        int[] bgrImageData = new int[w * h * 3];
        for (int i = 0; i < argbImageData.length; i++) {
            int blue = argbImageData[i] & 0xFF;
            int green = (argbImageData[i] >> 8) & 0xFF;
            int red = (argbImageData[i] >> 16) & 0xFF;
            bgrImageData[index] = blue;
            bgrImageData[index+1] = green;
            bgrImageData[index+2] = red;
            index += 3; 
        }
        int[] bmpImage = new int[file_header.length+info_header.length+bgrImageData.length];
        System.arraycopy(file_header, 0, bmpImage, 0, file_header.length);
        System.arraycopy(info_header, 0, bmpImage, file_header.length, info_header.length);
        System.arraycopy(bgrImageData, 0, bmpImage, file_header.length+info_header.length, bgrImageData.length);
        byte[] outputImage = new byte[bmpImage.length];
        for (int i = 0; i < bmpImage.length; i++) {
            outputImage[i] = (byte) bmpImage[i];
        }
        try {
            os = new FileOutputStream(output);
            os.write(outputImage);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("[INFO] Exported player icon to: " + output);
    }
}