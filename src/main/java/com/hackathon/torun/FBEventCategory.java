package com.hackathon.torun;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by piotr on 21.06.15.
 */
public class FBEventCategory {

    private String categoryName;
    private String[] searchTags;

    public FBEventCategory(Path categoryFile) throws IOException {
        this.categoryName = categoryFile.getFileName().toString().replace(".txt", "");
        this.searchTags = new String(Files.readAllBytes(categoryFile)).split(",");
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public String[] getSearchTags() {
        return searchTags;
    }
}
