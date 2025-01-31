package com.batchrename;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

class BatchRenameTest {
    private BatchRename batchRename;

    @BeforeEach
    void setUp() {
        batchRename = new BatchRename();
    }

    @Test
    void testAddPrefixToFiles() {
        batchRename.addFiles("test.txt", "C:/Users/username/Documents");
        List<FileEntry> fileEntries = batchRename.getFileEntries();
        assertTrue(fileEntries.size() == 1);
    }
}
