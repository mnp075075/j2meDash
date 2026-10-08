package com.j2meDash.game;

import javax.microedition.lcdui.*;

public class IconChanger {
    private Image template;
    public Image changeIconColor(String path, int primaryColor, int secondaryColor, int glowColor) {
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
            int v = (data[i] & 0xFF);
            if (v == 0xFFFFFFFF) data[i] = 0x00000000;
            else if (v == 0xFF000000) continue;
            else if (v == 0xFF404040) data[i] = 0xFF000000 | glowColor;
            else if (v == 0xFF808080) data[i] = 0xFF000000 | primaryColor;
            else data[i] = 0xFF000000 | secondaryColor; 
        }
        System.out.println("[INFO] Changed player icon to template: " + path + " - primaryColor = " + primaryColor + " - secondaryColor = " + secondaryColor + " - glowColor = " + glowColor);
        return Image.createRGBImage(data, w, h, true);
    }
}