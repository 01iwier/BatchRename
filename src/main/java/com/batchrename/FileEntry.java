package com.batchrename;
import java.util.*;

public class FileEntry {
    private String originalName;
    private String filePath;
    private ArrayList<String> nameHistory;

    public FileEntry(String originalName, String filePath) {
        this.filePath = filePath;
        this.originalName = originalName;
        this.nameHistory = new ArrayList<>();
        this.nameHistory.add(originalName);
    }

    // Getters and Setters
    public String getOriginalName() {
        return originalName;
    }

    public String getCurrentName() {
        return nameHistory.getLast();
    }
    
    public String getFilePath() {
        return filePath;
    }

    public void setCurrentName(String currentName) {
        nameHistory.add(currentName);
    }

    public void undoName() {
        if (nameHistory.size() > 1) {
            nameHistory.removeLast();
        }
    }
    
}