package me.eden.extractor.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ImageCentering {
    private static final int WIDTH = 256;
    private static final int HEIGHT = 256;

    public static void drawCenteredImage(BufferedImage destinationImage, BufferedImage imageToDraw) {
        Graphics2D graphics2D = destinationImage.createGraphics();

        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int destinationWidth = destinationImage.getWidth();
        int destinationHeight = destinationImage.getHeight();
        int imageWidth = imageToDraw.getWidth();
        int imageHeight = imageToDraw.getHeight();

        int x = (destinationWidth - imageWidth) / 2;
        int y = (destinationHeight - imageHeight) / 2;

        graphics2D.drawImage(imageToDraw, x, y, null);
        graphics2D.dispose();
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();
        return resizedImage;
    }
}
