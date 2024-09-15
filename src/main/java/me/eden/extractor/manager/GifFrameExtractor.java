package me.eden.extractor.manager;

import me.eden.extractor.utils.GifFrameExtractorUtils;
import me.eden.pack.ResourcePackGenerator;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GifFrameExtractor {

    public static void extractFrames(File gifFile, File outputFolder) throws IOException {
        ResourcePackGenerator resourcePackGenerator = new ResourcePackGenerator(outputFolder);
        resourcePackGenerator.create(GifFrameExtractorUtils.getFileNameWithoutExtension(gifFile.getAbsolutePath()) + " Frames");
        resourcePackGenerator.createTextures();

        try {
            String[] imageKits = new String[] {
                    "imageLeftPosition",
                    "imageTopPosition",
                    "imageWidth",
                    "imageHeight"
            };

            ImageReader imageReader = ImageIO.getImageReadersByFormatName("gif").next();
            ImageInputStream inputStream = ImageIO.createImageInputStream(gifFile);
            imageReader.setInput(inputStream, false);

            int numberOfImages = imageReader.getNumImages(true);
            BufferedImage master = null;

            resourcePackGenerator.createFont(numberOfImages);

            for (int i = 0; i < numberOfImages; i++) {
                BufferedImage image = imageReader.read(i);
                IIOMetadata metadata = imageReader.getImageMetadata(i);

                Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");
                NodeList children = tree.getChildNodes();

                for (int j = 0; j < children.getLength(); j++) {
                    Node nodeItem = children.item(j);

                    if (nodeItem.getNodeName().equals("ImageDescriptor")) {
                        Map<String, Integer> imageAttributes = new HashMap<>();

                        for (String imageKit : imageKits) {
                            NamedNodeMap attr = nodeItem.getAttributes();
                            Node attnode = attr.getNamedItem(imageKit);
                            imageAttributes.put(imageKit, Integer.valueOf(attnode.getNodeValue()));
                        }

                        if (i == 0) master = new BufferedImage(imageAttributes.get("imageWidth"), imageAttributes.get("imageHeight"), BufferedImage.TYPE_INT_ARGB);
                        if (master != null) master.getGraphics().drawImage(image, imageAttributes.get("imageLeftPosition"), imageAttributes.get("imageTopPosition"), null);
                    }
                }

                if (master != null) ImageIO.write(master, "GIF", new File(resourcePackGenerator.framesFolder, i + ".gif"));
            }
        }
        catch (IOException exception) {
            exception.fillInStackTrace();
        }
    }

}
