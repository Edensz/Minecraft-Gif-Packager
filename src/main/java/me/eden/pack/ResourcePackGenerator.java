package me.eden.pack;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResourcePackGenerator {
    private final File parent;

    public File resourcePackFolder;
    public File minecraftFolder;
    public File framesFolder;

    public ResourcePackGenerator(File parent) {
        this.parent = parent;
    }

    public void createFont(int frames) {
        File fontFile = ResourcePackGeneratorUtils.createFile(this.minecraftFolder, "font");

        JSONObject packObject = new JSONObject();
        packObject.put("override", false);

        JSONArray providers = new JSONArray();

        for (int i = 0; i < frames; i++) {
            JSONObject provider = new JSONObject();
            provider.put("type", "bitmap");
            provider.put("file", "minecraft:frames/" + i + ".png");
            provider.put("ascent", 25);
            provider.put("height", 48);
            provider.put("chars", new JSONArray().put("\\uE00" + i));

            providers.put(provider);
        }

        packObject.put("providers", providers);

        File packFile = new File(fontFile, "default.json");
        try (FileWriter fileWriter = new FileWriter(packFile)) {
            fileWriter.write(packObject.toString(4));
        } catch (IOException exception) {
            exception.fillInStackTrace();
        }
    }

    public void createTextures() {
        File texturesFile = ResourcePackGeneratorUtils.createFile(this.minecraftFolder, "textures");
        this.framesFolder = ResourcePackGeneratorUtils.createFile(texturesFile, "frames");
    }

    public void create(String name) {
        this.resourcePackFolder = ResourcePackGeneratorUtils.createFile(this.parent, name + " Resource Pack");
        File assets = ResourcePackGeneratorUtils.createFile(this.resourcePackFolder, "assets");

        this.minecraftFolder = ResourcePackGeneratorUtils.createFile(assets, "minecraft");

        JSONObject packObject = new JSONObject();
        packObject.put("pack_format", 15);
        packObject.put("description", name + "\nMinecraft Gif Packager");

        JSONObject mainObject = new JSONObject();
        mainObject.put("pack", packObject);

        File packFile = new File(this.parent, "pack.mcmeta");

        try (FileWriter fileWriter = new FileWriter(packFile)) {
            fileWriter.write(mainObject.toString(4));
        } catch (IOException exception) {
            exception.fillInStackTrace();
        }
    }
}
