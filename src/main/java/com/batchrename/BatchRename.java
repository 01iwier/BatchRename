package com.batchrename;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;

public class BatchRename extends javax.swing.JFrame {
    private JTable fileTable;
    private DefaultTableModel tableModel;
    private ArrayList<FileEntry> fileEntries;
    private ArrayList<Integer> operationHistory;
    private ArrayList<ArrayList<FileEntry>> previousFileEntries;
    
    // STARTUP
    public BatchRename() {
        fileEntries = new ArrayList<>();
        operationHistory = new ArrayList<>();
        previousFileEntries = new ArrayList<>();
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/assets/icon.png"));
        initTable();
        setLocationRelativeTo(null);
    }
    
    // HELPER METHODS
    // create file display table
    private void initTable() {
        tableModel = new DefaultTableModel(new Object[]{"Original", "Updated"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        fileTable = new JTable(tableModel);
        filePane.setViewportView(fileTable);
        fileTable.getTableHeader().setReorderingAllowed(false);
        fileTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        fileTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Check if the pressed key is Escape (key code 27)
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    // Clear the selection of the table
                    fileTable.clearSelection();
                }
            }
        });
    }
    
    // populate file table
    private void updateTable() {
        tableModel.setRowCount(0);
        for (FileEntry entry : fileEntries) {
            tableModel.addRow(new Object[] {
                entry.getOriginalName(), entry.getCurrentName()
            });
        }
        autoResizeTableColumns(fileTable);
    }
    
    // auto resize the table 
    private void autoResizeTableColumns(JTable table) {
        for (int col = 0; col < table.getColumnCount(); col++) {
            TableColumn column = table.getColumnModel().getColumn(col);
            int preferredWidth = 50;
            int maxWidth = 300;

            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
                Component comp = table.prepareRenderer(cellRenderer, row, col);
                preferredWidth = Math.max(comp.getPreferredSize().width + 10, preferredWidth);
            }

            preferredWidth = Math.min(preferredWidth, maxWidth);
            column.setPreferredWidth(preferredWidth);
        }
    }
    
    // save snapshot of files between operations
    private void saveCurrentState() {
        previousFileEntries.add(new ArrayList<>(fileEntries));
        //System.out.println(previousFileEntries.size());
    }
    
    // update name preview
    private void updateInsertPreview(String textToInsert, int position, JLabel modifiedLabel, int selectedIndex) {
        String currentName = fileEntries.get(selectedIndex).getCurrentName();
        String newName;

        if (currentName.contains(".")) {
            int maxPosition = currentName.lastIndexOf(".");
            if (position > maxPosition) {
                int paddingLength = position - maxPosition;
                String padding = " ".repeat(paddingLength);
                newName = currentName.substring(0, maxPosition) + padding + textToInsert + currentName.substring(maxPosition);
            } else {
                newName = currentName.substring(0, position) + textToInsert + currentName.substring(position);
            }
        } else {
            int maxPosition = currentName.length();
            if (position > maxPosition) {
                int paddingLength = position - maxPosition;
                String padding = " ".repeat(paddingLength);
                newName = currentName + padding + textToInsert;
            } else {
                newName = currentName.substring(0, position) + textToInsert + currentName.substring(position);
            }
        }
        modifiedLabel.setText("New: " + newName);
    }
    
    // update name preview
    private void updateReplacePreview(JTextField inputField, JTextField inputField2, JCheckBox caseCheckbox, FileEntry selectedEntry, JLabel modifiedLabel) {
        String toReplace = inputField.getText();
        String replacement = inputField2.getText();
        boolean caseSensitive = caseCheckbox.isSelected();

        String currentName = selectedEntry.getCurrentName();
        int extensionIndex = currentName.lastIndexOf('.');
        String namePart = (extensionIndex != -1) ? currentName.substring(0, extensionIndex) : currentName;
        String extensionPart = (extensionIndex != -1) ? currentName.substring(extensionIndex) : "";

        // Replace text for preview
        String updatedName;
        if (caseSensitive) {
            updatedName = namePart.replace(toReplace, replacement);
        } else {
            updatedName = namePart.replaceAll("(?i)" + Pattern.quote(toReplace), Matcher.quoteReplacement(replacement));
        }

        modifiedLabel.setText("New: " + updatedName + extensionPart);
    }
    
    // check that new name is valid
    private boolean isValidWindowsFileName(String fileName) {
    // Check for illegal characters in the file name
    String illegalChars = "\\/:*?\"<>|";
    for (char c : illegalChars.toCharArray()) {
        if (fileName.indexOf(c) != -1) {
            return false;
        }
    }

    // Check for reserved names
    String[] reservedNames = {"CON", "PRN", "AUX", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};
    for (String reservedName : reservedNames) {
        if (fileName.equalsIgnoreCase(reservedName)) {
            return false;
        }
    }

    // Ensure the file name is not too long (max 255 characters)
    if (fileName.length() > 255) {
        return false;
    }

    return true;
}

    // TESTING HELPER METHODS
    // add temporary files for testing
    public void addFiles(String originalName, String filepath) {
        fileEntries.add(new FileEntry(originalName, filepath));
    }

    // get file entries
    public ArrayList<FileEntry> getFileEntries() {
        return fileEntries;
    }


    // INIT UI
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnUndo = new javax.swing.JButton();
        filePane = new javax.swing.JScrollPane();
        filePanel = new javax.swing.JPanel();
        btnApply = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        addFiles = new javax.swing.JMenuItem();
        addFolder = new javax.swing.JMenuItem();
        insertMenu = new javax.swing.JMenu();
        insertPrefix = new javax.swing.JMenuItem();
        insertSuffix = new javax.swing.JMenuItem();
        insertAtPosition = new javax.swing.JMenuItem();
        deleteMenu = new javax.swing.JMenu();
        deleteFrom = new javax.swing.JMenuItem();
        deleteUntil = new javax.swing.JMenuItem();
        deleteAtPosition = new javax.swing.JMenuItem();
        replaceMenu = new javax.swing.JMenu();
        replaceAll = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BatchRename");
        setPreferredSize(new java.awt.Dimension(850, 600));
        setSize(new java.awt.Dimension(850, 600));

        btnUndo.setText("Undo");
        btnUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUndoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout filePanelLayout = new javax.swing.GroupLayout(filePanel);
        filePanel.setLayout(filePanelLayout);
        filePanelLayout.setHorizontalGroup(
            filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 810, Short.MAX_VALUE)
        );
        filePanelLayout.setVerticalGroup(
            filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 386, Short.MAX_VALUE)
        );

        filePane.setViewportView(filePanel);

        btnApply.setText("Apply");
        btnApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApplyActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        fileMenu.setText("File");

        addFiles.setText("Add Files");
        addFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFilesActionPerformed(evt);
            }
        });
        fileMenu.add(addFiles);

        addFolder.setText("Add Folder");
        addFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFolderActionPerformed(evt);
            }
        });
        fileMenu.add(addFolder);

        menuBar.add(fileMenu);

        insertMenu.setText("Insert");

        insertPrefix.setText("Prefix");
        insertPrefix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertPrefixActionPerformed(evt);
            }
        });
        insertMenu.add(insertPrefix);

        insertSuffix.setText("Suffix");
        insertSuffix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertSuffixActionPerformed(evt);
            }
        });
        insertMenu.add(insertSuffix);

        insertAtPosition.setText("At Position");
        insertAtPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertAtPositionActionPerformed(evt);
            }
        });
        insertMenu.add(insertAtPosition);

        menuBar.add(insertMenu);

        deleteMenu.setText("Delete");
        deleteMenu.setMinimumSize(new java.awt.Dimension(52, 22));

        deleteFrom.setText("After __");
        deleteFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteFromActionPerformed(evt);
            }
        });
        deleteMenu.add(deleteFrom);

        deleteUntil.setText("Until __");
        deleteUntil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteUntilActionPerformed(evt);
            }
        });
        deleteMenu.add(deleteUntil);

        deleteAtPosition.setText("At Position");
        deleteAtPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteAtPositionActionPerformed(evt);
            }
        });
        deleteMenu.add(deleteAtPosition);

        menuBar.add(deleteMenu);

        replaceMenu.setText("Replace");

        replaceAll.setText("All");
        replaceAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replaceAllActionPerformed(evt);
            }
        });
        replaceMenu.add(replaceAll);

        menuBar.add(replaceMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filePane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                        .addComponent(btnApply, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUndo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 116, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filePane)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUndo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnApply, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // UI CONTROL
    private void addFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFilesActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setPreferredSize(new java.awt.Dimension(750, 450));
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            saveCurrentState();
            for (File file : selectedFiles) {
                if (fileEntries.stream().noneMatch(entry -> entry.getOriginalName().equals(file.getName()))) {
                    fileEntries.add(new FileEntry(file.getName(), file.getAbsolutePath()));
                }
            }
            updateTable();
            operationHistory.add(0);
        }
    }//GEN-LAST:event_addFilesActionPerformed

    private void addFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFolderActionPerformed
        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderChooser.setPreferredSize(new java.awt.Dimension(750, 450));
        int returnValue = folderChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = folderChooser.getSelectedFile();
            File[] filesInFolder = selectedFolder.listFiles();
            if (filesInFolder != null) {
                saveCurrentState();
                for (File file : filesInFolder) {
                    if (file.isFile()) {
                        if (fileEntries.stream().noneMatch(entry -> entry.getOriginalName().equals(file.getName()))) {
                            fileEntries.add(new FileEntry(file.getName(), file.getAbsolutePath()));
                        }
                    }
                }
            }
            updateTable();
            operationHistory.add(0);
        }
    }//GEN-LAST:event_addFolderActionPerformed

    private void insertPrefixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertPrefixActionPerformed
        // check for no files added
        if (fileEntries.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Files Added.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int selectedIndex = fileTable.getSelectedRow(); // Assuming `fileTable` is your JTable
        if (selectedIndex == -1) {
            selectedIndex = 0; // Default to the first entry if nothing is selected
        }

        FileEntry selectedEntry = fileEntries.get(selectedIndex);

        // <editor-fold defaultstate="collapsed" desc="Dialog UI">
        JDialog dialog = new JDialog(this, "Add Prefix", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        // create components
        JLabel modifiedLabel = new JLabel("New: " + selectedEntry.getCurrentName());
        modifiedLabel.setFont(new Font(modifiedLabel.getFont().getName(), modifiedLabel.getFont().getStyle(), 14));
        modifiedLabel.setHorizontalAlignment(SwingConstants.LEFT);
        modifiedLabel.setPreferredSize(new Dimension(400, 20));
        modifiedLabel.setMaximumSize(new Dimension(400, 20));
        modifiedLabel.setMinimumSize(new Dimension(400, 20));

        JTextField inputField = new JTextField();
        inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputField.getPreferredSize().height));

        JButton btnAdd = new JButton("Add");
        JButton btnCancel = new JButton("Cancel");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(modifiedLabel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(inputField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        // </editor-fold> 

        // update preview of new file name
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                modifiedLabel.setText("New: " + inputField.getText() + selectedEntry.getCurrentName());
            }
        });

        // save copy and apply prefix
        btnAdd.addActionListener(e -> {
            String prefix = inputField.getText(); //.trim()?
            if (!prefix.isEmpty()) {
                saveCurrentState();
                for (FileEntry entry : fileEntries) {
                    entry.setCurrentName(prefix + entry.getCurrentName());
                }
                dialog.dispose();
                updateTable();
                operationHistory.add(1);
            } else {
                JOptionPane.showMessageDialog(dialog, "Prefix cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // dispose of dialog
        btnCancel.addActionListener(e -> dialog.dispose());

        // display dialog
        dialog.setVisible(true);
    }//GEN-LAST:event_insertPrefixActionPerformed

    private void insertSuffixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertSuffixActionPerformed
        if (fileEntries.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Files Added.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int selectedIndex = fileTable.getSelectedRow(); // Assuming `fileTable` is your JTable
        if (selectedIndex == -1) {
            selectedIndex = 0; // Default to the first entry if nothing is selected
        }

        FileEntry selectedEntry = fileEntries.get(selectedIndex);

        // <editor-fold defaultstate="collapsed" desc="Dialog UI">
        JDialog dialog = new JDialog(this, "Add Suffix", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        // create components
        JLabel modifiedLabel = new JLabel("New: " + selectedEntry.getCurrentName());
        modifiedLabel.setFont(new Font(modifiedLabel.getFont().getName(), modifiedLabel.getFont().getStyle(), 14));
        modifiedLabel.setHorizontalAlignment(SwingConstants.LEFT);
        modifiedLabel.setPreferredSize(new Dimension(400, 20));
        modifiedLabel.setMaximumSize(new Dimension(400, 20));
        modifiedLabel.setMinimumSize(new Dimension(400, 20));

        JTextField inputField = new JTextField();
        inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputField.getPreferredSize().height));

        JButton btnAdd = new JButton("Add");
        JButton btnCancel = new JButton("Cancel");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(modifiedLabel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(inputField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        // </editor-fold>

        // update preview of new file name
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String currentName = selectedEntry.getCurrentName();
                if (currentName.contains(".")) {
                    int extensionIndex = currentName.lastIndexOf(".");
                    modifiedLabel.setText("New: " + currentName.substring(0, extensionIndex) + inputField.getText() + currentName.substring(extensionIndex));
                } else {
                    modifiedLabel.setText("New: " + currentName + inputField.getText());
                }
            }
        });

        // save copy and apply suffix
        btnAdd.addActionListener(e -> {
            String suffix = inputField.getText(); //.trim()?
            if (!suffix.isEmpty()) {
                saveCurrentState();
                for (FileEntry entry : fileEntries) {
                    String currentName = entry.getCurrentName();
                    if (currentName.contains(".")) {
                        int extensionIndex = currentName.lastIndexOf(".");
                        entry.setCurrentName(currentName.substring(0, extensionIndex) + suffix + currentName.substring(extensionIndex));
                    } else {
                        entry.setCurrentName(currentName + suffix);
                    }
                }
                dialog.dispose();
                updateTable();
                operationHistory.add(1);
            } else {
                JOptionPane.showMessageDialog(dialog, "Suffix cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        // display dialog
        dialog.setVisible(true);
    }//GEN-LAST:event_insertSuffixActionPerformed

    private void insertAtPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertAtPositionActionPerformed
        if (fileEntries.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Files Added.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        final int selectedIndex;
        if (fileTable.getSelectedRow() != -1) {
            selectedIndex = fileTable.getSelectedRow();
        } else {
            selectedIndex = 0;
        }
        
        FileEntry selectedEntry = fileEntries.get(selectedIndex);

        // <editor-fold defaultstate="collapsed" desc="Dialog UI">
        JDialog dialog = new JDialog(this, "Insert Text At Position", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(300, 225);
        dialog.setLocationRelativeTo(this);

        // Create components
        JLabel modifiedLabel = new JLabel("New: " + selectedEntry.getCurrentName());
        modifiedLabel.setFont(new Font(modifiedLabel.getFont().getName(), modifiedLabel.getFont().getStyle(), 14));
        modifiedLabel.setHorizontalAlignment(SwingConstants.LEFT);
        modifiedLabel.setPreferredSize(new Dimension(400, 20));
        modifiedLabel.setMaximumSize(new Dimension(400, 20));
        modifiedLabel.setMinimumSize(new Dimension(400, 20));

        JTextField inputField = new JTextField();
        inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputField.getPreferredSize().height));
        JSpinner positionSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 251, 1));
        positionSpinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, positionSpinner.getPreferredSize().height));

        JButton btnAdd = new JButton("Add");
        JButton btnCancel = new JButton("Cancel");

        // Arrange components
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(modifiedLabel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(new JLabel("Text to Insert:"));
        inputPanel.add(inputField);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(new JLabel("Position (0-based):"));
        inputPanel.add(positionSpinner);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        // </editor-fold>

        // Update preview of new file name dynamically
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateInsertPreview(inputField.getText(), (int)positionSpinner.getValue(), modifiedLabel, selectedIndex);
            }
        });

        positionSpinner.addChangeListener(e -> {
            updateInsertPreview(inputField.getText(), (int)positionSpinner.getValue(), modifiedLabel, selectedIndex);
        });

        // Apply changes
        btnAdd.addActionListener(e -> {
            String textToInsert = inputField.getText();
            int position = (int) positionSpinner.getValue();

            if (!textToInsert.isEmpty()) {
                saveCurrentState();
                for (FileEntry entry : fileEntries) {
                    String currentName = entry.getCurrentName();

                    if (currentName.contains(".")) {
                        int extensionIndex = currentName.lastIndexOf(".");
                        int maxInsertPosition = extensionIndex;
                        if (position > maxInsertPosition) {
                            String padding = " ".repeat(position - maxInsertPosition);
                            currentName = currentName.substring(0, extensionIndex) + padding + textToInsert + currentName.substring(extensionIndex);
                        } else {
                            currentName = currentName.substring(0, position) + textToInsert + currentName.substring(position);
                        }
                    } else {
                        int maxInsertPosition = currentName.length();
                        if (position > maxInsertPosition) {
                            String padding = " ".repeat(position - maxInsertPosition);
                            currentName = currentName + padding + textToInsert;
                        } else {
                            currentName = currentName.substring(0, position) + textToInsert + currentName.substring(position);
                        }
                    }

                    entry.setCurrentName(currentName);
                }

                dialog.dispose();
                updateTable();
                operationHistory.add(1);
            } else {
                JOptionPane.showMessageDialog(dialog, "Text cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        // Display dialog
        dialog.setVisible(true);
    }//GEN-LAST:event_insertAtPositionActionPerformed

    private void deleteFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteFromActionPerformed
        if (fileEntries.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Files Added.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int selectedIndex = fileTable.getSelectedRow(); // Assuming `fileTable` is your JTable
        if (selectedIndex == -1) {
            selectedIndex = 0; // Default to the first entry if nothing is selected
        }

        FileEntry selectedEntry = fileEntries.get(selectedIndex);
        
        // <editor-fold defaultstate="collapsed" desc="Dialog UI">
        JDialog dialog = new JDialog(this, "Delete After __", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(300, 220);
        dialog.setLocationRelativeTo(this);

        // create components
        JLabel originalLabel = new JLabel("Old: " + selectedEntry.getCurrentName());
        originalLabel.setFont(new Font(originalLabel.getFont().getName(), originalLabel.getFont().getStyle(), 14));
        originalLabel.setHorizontalAlignment(SwingConstants.LEFT);
        originalLabel.setPreferredSize(new Dimension(400, 20));
        originalLabel.setMaximumSize(new Dimension(400, 20));
        originalLabel.setMinimumSize(new Dimension(400, 20));
        
        JLabel modifiedLabel = new JLabel("New: " + selectedEntry.getCurrentName());
        modifiedLabel.setFont(new Font(modifiedLabel.getFont().getName(), modifiedLabel.getFont().getStyle(), 14));
        modifiedLabel.setHorizontalAlignment(SwingConstants.LEFT);
        modifiedLabel.setPreferredSize(new Dimension(400, 20));
        modifiedLabel.setMaximumSize(new Dimension(400, 20));
        modifiedLabel.setMinimumSize(new Dimension(400, 20));

        JTextField inputField = new JTextField();
        inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputField.getPreferredSize().height));
        
        JCheckBox occurrenceCheckbox = new JCheckBox("Delete from last occurrence");
        occurrenceCheckbox.setSelected(false); // Default: Delete from first occurrence

        JButton btnAdd = new JButton("Add");
        JButton btnCancel = new JButton("Cancel");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(originalLabel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(modifiedLabel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(inputField);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(occurrenceCheckbox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        // </editor-fold>
        
        // Update preview of new file name
        Runnable updatePreview = () -> {
            String currentInput = inputField.getText();
            String originalName = selectedEntry.getCurrentName();

            // Separate base name and extension
            int dotIndex = originalName.lastIndexOf('.');
            String baseName = (dotIndex != -1) ? originalName.substring(0, dotIndex) : originalName;
            String extension = (dotIndex != -1) ? originalName.substring(dotIndex) : "";

            // Determine whether to delete from first or last occurrence
            if (!currentInput.isEmpty() && baseName.contains(currentInput)) {
                int deletionIndex = occurrenceCheckbox.isSelected()
                        ? baseName.lastIndexOf(currentInput) // Last occurrence
                        : baseName.indexOf(currentInput);   // First occurrence
                modifiedLabel.setText("New: " + baseName.substring(0, deletionIndex) + extension);
            } else {
                modifiedLabel.setText("New: " + originalName);
            }
        };
        
        // update preview of new file name
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updatePreview.run();
            }
        });
        
        // save copy and apply prefix
        btnAdd.addActionListener(e -> {
            String inputText = inputField.getText();
            if (inputText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Input cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            saveCurrentState();

            for (FileEntry entry : fileEntries) {
                String originalName = entry.getCurrentName();

                int dotIndex = originalName.lastIndexOf('.');
                String baseName = (dotIndex != -1) ? originalName.substring(0, dotIndex) : originalName;
                String extension = (dotIndex != -1) ? originalName.substring(dotIndex) : "";

                if (baseName.contains(inputText)) {
                    int deletionIndex = occurrenceCheckbox.isSelected() ? baseName.lastIndexOf(inputText) : baseName.indexOf(inputText);
                    baseName = baseName.substring(0, deletionIndex);
                }
                entry.setCurrentName(baseName + extension);
            }
            dialog.dispose();
            updateTable();
            operationHistory.add(1);
        });
        
        // update label on checkbox interaction
        occurrenceCheckbox.addItemListener(e -> updatePreview.run());

        // dispose of dialog
        btnCancel.addActionListener(e -> dialog.dispose());
        
        dialog.setVisible(true);
    }//GEN-LAST:event_deleteFromActionPerformed

    private void deleteUntilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteUntilActionPerformed
        if (fileEntries.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Files Added.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int selectedIndex = fileTable.getSelectedRow(); // Assuming `fileTable` is your JTable
        if (selectedIndex == -1) {
            selectedIndex = 0; // Default to the first entry if nothing is selected
        }

        FileEntry selectedEntry = fileEntries.get(selectedIndex);
        
        // <editor-fold defaultstate="collapsed" desc="Dialog UI">
        JDialog dialog = new JDialog(this, "Delete Until __", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(300, 180);
        dialog.setLocationRelativeTo(this);

        // create components
        JLabel originalLabel = new JLabel("Old: " + selectedEntry.getCurrentName());
        originalLabel.setFont(new Font(originalLabel.getFont().getName(), originalLabel.getFont().getStyle(), 14));
        originalLabel.setHorizontalAlignment(SwingConstants.LEFT);
        originalLabel.setPreferredSize(new Dimension(400, 20));
        originalLabel.setMaximumSize(new Dimension(400, 20));
        originalLabel.setMinimumSize(new Dimension(400, 20));
        
        JLabel modifiedLabel = new JLabel("New: " + selectedEntry.getCurrentName());
        modifiedLabel.setFont(new Font(modifiedLabel.getFont().getName(), modifiedLabel.getFont().getStyle(), 14));
        modifiedLabel.setHorizontalAlignment(SwingConstants.LEFT);
        modifiedLabel.setPreferredSize(new Dimension(400, 20));
        modifiedLabel.setMaximumSize(new Dimension(400, 20));
        modifiedLabel.setMinimumSize(new Dimension(400, 20));

        JTextField inputField = new JTextField();
        inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputField.getPreferredSize().height));

        JButton btnAdd = new JButton("Add");
        JButton btnCancel = new JButton("Cancel");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(originalLabel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(modifiedLabel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(inputField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        // </editor-fold>
        
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String currentInput = inputField.getText();
                String originalName = selectedEntry.getCurrentName();

                // Separate base name and extension
                int dotIndex = originalName.lastIndexOf('.');
                String baseName = (dotIndex != -1) ? originalName.substring(0, dotIndex) : originalName;
                String extension = (dotIndex != -1) ? originalName.substring(dotIndex) : "";

                // Update preview
                if (!currentInput.isEmpty() && baseName.contains(currentInput)) {
                    int deletionIndex = baseName.indexOf(currentInput); // First occurrence
                    modifiedLabel.setText("New: " + baseName.substring(deletionIndex) + extension);
                } else {
                    modifiedLabel.setText("New: " + originalName); // Reset if input not found
                }
            }
        });
        
        btnAdd.addActionListener(e -> {
            String currentInput = inputField.getText();
            
            if (!currentInput.isEmpty()) {
                saveCurrentState();
                for (FileEntry entry : fileEntries) {
                    String originalName = entry.getCurrentName();

                    // Separate base name and extension
                    int dotIndex = originalName.lastIndexOf('.');
                    String baseName = (dotIndex != -1) ? originalName.substring(0, dotIndex) : originalName;
                    String extension = (dotIndex != -1) ? originalName.substring(dotIndex) : "";

                    // Apply deletion logic
                    if (baseName.contains(currentInput)) {
                        int deletionIndex = baseName.indexOf(currentInput); // First occurrence
                        entry.setCurrentName(baseName.substring(deletionIndex) + extension);
                    }
                }
                dialog.dispose();
                updateTable();
                operationHistory.add(1);
            } else {
                JOptionPane.showMessageDialog(dialog, "Text cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // dispose of dialog
        btnCancel.addActionListener(e -> dialog.dispose());
        
        dialog.setVisible(true);
    }//GEN-LAST:event_deleteUntilActionPerformed

    private void btnApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApplyActionPerformed
        if (fileEntries.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Files to Apply.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int successfulRenameCount = 0;
        int failedRenameCount = 0;
        for (FileEntry entry : fileEntries) {
            String newName = entry.getCurrentName();

            if (isValidWindowsFileName(newName)) {
                File file = new File(entry.getFilePath());
                File newFile = new File(file.getParent(), newName);

                if (file.renameTo(newFile)) {
                    successfulRenameCount++;
                } else {
                    failedRenameCount++;
                }
            }
        }
        
        if (successfulRenameCount > 0) {
            JOptionPane.showMessageDialog(this, "Successfully renamed " + successfulRenameCount + " files.", "Renaming Complete", JOptionPane.INFORMATION_MESSAGE);
        }
        
        if (failedRenameCount > 0) {
            JOptionPane.showMessageDialog(this, "Failed to rename " + failedRenameCount + " files.", "Renaming Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnApplyActionPerformed

    private void btnUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUndoActionPerformed
        if (!previousFileEntries.isEmpty()) {
            fileEntries = new ArrayList<>(previousFileEntries.remove(previousFileEntries.size() - 1));
            if (operationHistory.removeLast() == 1) {
                for (FileEntry entry : fileEntries) {
                    entry.undoName();
                }
            }
            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "No changes to undo.", "Undo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnUndoActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        if (fileEntries.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Files to Remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int[] selectedRows = fileTable.getSelectedRows();

        if (selectedRows.length > 0) {
            saveCurrentState();
            operationHistory.add(0);
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                fileEntries.remove(selectedRows[i]);
            }
        } else {
            int confirmation = JOptionPane.showConfirmDialog(this, "No rows selected. Clear all entries?", "Confirm Clear", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirmation == JOptionPane.YES_OPTION) {
                saveCurrentState();
                operationHistory.add(0);
                fileEntries.clear();
            }
        }

        updateTable();
    }//GEN-LAST:event_btnClearActionPerformed

    private void replaceAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replaceAllActionPerformed
        if (fileEntries.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Files Added.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        final int selectedIndex;
        if (fileTable.getSelectedRow() != -1) {
            selectedIndex = fileTable.getSelectedRow();
        } else {
            selectedIndex = 0;
        }
        
        FileEntry selectedEntry = fileEntries.get(selectedIndex);
        
        // <editor-fold defaultstate="collapsed" desc="Dialog UI">
        JDialog dialog = new JDialog(this, "Replace All", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(450, 290);
        dialog.setLocationRelativeTo(this);

        // create components
        JLabel originalLabel = new JLabel("Old: " + selectedEntry.getCurrentName());
        originalLabel.setFont(new Font(originalLabel.getFont().getName(), originalLabel.getFont().getStyle(), 14));
        originalLabel.setHorizontalAlignment(SwingConstants.LEFT);
        originalLabel.setPreferredSize(new Dimension(400, 20));
        originalLabel.setMaximumSize(new Dimension(400, 20));
        originalLabel.setMinimumSize(new Dimension(400, 20));
        
        JLabel modifiedLabel = new JLabel("New: " + selectedEntry.getCurrentName());
        modifiedLabel.setFont(new Font(modifiedLabel.getFont().getName(), modifiedLabel.getFont().getStyle(), 14));
        modifiedLabel.setHorizontalAlignment(SwingConstants.LEFT);
        modifiedLabel.setPreferredSize(new Dimension(400, 20));
        modifiedLabel.setMaximumSize(new Dimension(400, 20));
        modifiedLabel.setMinimumSize(new Dimension(400, 20));
        
        JLabel infoLabel1 = new JLabel("Replace: ");
        JLabel infoLabel2 = new JLabel("With: ");

        JTextField inputField = new JTextField();
        inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputField.getPreferredSize().height));
        JTextField inputField2 = new JTextField();
        inputField2.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputField2.getPreferredSize().height));
        
        JCheckBox caseCheckbox = new JCheckBox("Case sensitive");
        caseCheckbox.setSelected(false);

        JButton btnAdd = new JButton("Add");
        JButton btnCancel = new JButton("Cancel");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(originalLabel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(modifiedLabel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(infoLabel1);
        inputPanel.add(inputField);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(infoLabel2);
        inputPanel.add(inputField2);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(caseCheckbox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnCancel);

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        // </editor-fold>
        
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateReplacePreview(inputField, inputField2, caseCheckbox, selectedEntry, modifiedLabel);
            }
        });

        inputField2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateReplacePreview(inputField, inputField2, caseCheckbox, selectedEntry, modifiedLabel);
            }
        });

        caseCheckbox.addActionListener(e -> updateReplacePreview(inputField, inputField2, caseCheckbox, selectedEntry, modifiedLabel));

        btnAdd.addActionListener(e -> {
            String toReplace = inputField.getText();
            String replacement = inputField2.getText();
            boolean caseSensitive = caseCheckbox.isSelected();

            if (toReplace.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Replacement text cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            saveCurrentState();
            for (FileEntry entry : fileEntries) {
                String currentName = entry.getCurrentName();
                int extensionIndex = currentName.lastIndexOf('.');
                String namePart = (extensionIndex != -1) ? currentName.substring(0, extensionIndex) : currentName;
                String extensionPart = (extensionIndex != -1) ? currentName.substring(extensionIndex) : "";

                // Replace text based on case sensitivity
                String updatedName;
                if (caseSensitive) {
                    updatedName = namePart.replace(toReplace, replacement);
                } else {
                    updatedName = namePart.replaceAll("(?i)" + Pattern.quote(toReplace), Matcher.quoteReplacement(replacement));
                }

                entry.setCurrentName(updatedName + extensionPart);
            }

            dialog.dispose();
            updateTable();
            operationHistory.add(1);
        });
        
        btnCancel.addActionListener(e -> dialog.dispose());
        
        dialog.setVisible(true);
    }//GEN-LAST:event_replaceAllActionPerformed

    private void deleteAtPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteAtPositionActionPerformed
        if (fileEntries.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Files Added.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        final int selectedIndex;
        if (fileTable.getSelectedRow() != -1) {
            selectedIndex = fileTable.getSelectedRow();
        } else {
            selectedIndex = 0;  // Fallback to the first entry if no row is selected
        }

        FileEntry selectedEntry = fileEntries.get(selectedIndex);

        // <editor-fold defaultstate="collapsed" desc="Dialog UI">
        JDialog dialog = new JDialog(this, "Delete Range", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(450, 250);
        dialog.setLocationRelativeTo(this);

        // Create components
        JLabel originalLabel = new JLabel("Old: " + selectedEntry.getCurrentName());
        originalLabel.setFont(new Font(originalLabel.getFont().getName(), originalLabel.getFont().getStyle(), 14));
        originalLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel modifiedLabel = new JLabel("New: " + selectedEntry.getCurrentName());
        modifiedLabel.setFont(new Font(modifiedLabel.getFont().getName(), modifiedLabel.getFont().getStyle(), 14));
        modifiedLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel startLabel = new JLabel("Start Position:");
        JLabel endLabel = new JLabel("End Position:");

        JSpinner startSpinner = new JSpinner(new SpinnerNumberModel(0, 0, selectedEntry.getCurrentName().length(), 1));
        JSpinner endSpinner = new JSpinner(new SpinnerNumberModel(0, 0, selectedEntry.getCurrentName().length(), 1));

        JButton btnApply = new JButton("Apply");
        JButton btnCancel = new JButton("Cancel");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(originalLabel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(modifiedLabel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(startLabel);
        inputPanel.add(startSpinner);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(endLabel);
        inputPanel.add(endSpinner);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnApply);
        buttonPanel.add(btnCancel);

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        // </editor-fold>

        // Update preview dynamically
        ChangeListener previewUpdater = e -> {
            int start = (Integer) startSpinner.getValue();
            int end = (Integer) endSpinner.getValue();
            String currentName = selectedEntry.getCurrentName();

            // Split into base name and extension
            int extensionIndex = currentName.lastIndexOf('.');
            String baseName = (extensionIndex != -1) ? currentName.substring(0, extensionIndex) : currentName;
            String extension = (extensionIndex != -1) ? currentName.substring(extensionIndex) : "";

            if (start < 0 || end > baseName.length() || start >= end) {
                modifiedLabel.setText("New: Invalid Range");
            } else {
                String updatedName = baseName.substring(0, start) + baseName.substring(end);
                modifiedLabel.setText("New: " + updatedName + extension);  // Reattach the extension
            }
        };

        startSpinner.addChangeListener(previewUpdater);
        endSpinner.addChangeListener(previewUpdater);

        btnApply.addActionListener(e -> {
            int start = (Integer) startSpinner.getValue();
            int end = (Integer) endSpinner.getValue();

            // Iterate through all file entries and apply the deletion to each file
            for (FileEntry entry : fileEntries) {
                String currentName = entry.getCurrentName();

                // Split into base name and extension
                int extensionIndex = currentName.lastIndexOf('.');
                String baseName = (extensionIndex != -1) ? currentName.substring(0, extensionIndex) : currentName;
                String extension = (extensionIndex != -1) ? currentName.substring(extensionIndex) : "";

                if (start < 0 || end > baseName.length() || start >= end) {
                    JOptionPane.showMessageDialog(dialog, "Invalid range specified.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Apply the deletion to the base name and reattach the extension
                String updatedName = baseName.substring(0, start) + baseName.substring(end);
                entry.setCurrentName(updatedName + extension);  // Set the updated name with extension
            }

            saveCurrentState();
            dialog.dispose();
            updateTable();
            operationHistory.add(1);
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }//GEN-LAST:event_deleteAtPositionActionPerformed

    // <editor-fold defaultstate="collapsed" desc="main + var declarations">
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BatchRename.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BatchRename.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BatchRename.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BatchRename.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BatchRename().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addFiles;
    private javax.swing.JMenuItem addFolder;
    private javax.swing.JButton btnApply;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnUndo;
    private javax.swing.JMenuItem deleteAtPosition;
    private javax.swing.JMenuItem deleteFrom;
    private javax.swing.JMenu deleteMenu;
    private javax.swing.JMenuItem deleteUntil;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JScrollPane filePane;
    private javax.swing.JPanel filePanel;
    private javax.swing.JMenuItem insertAtPosition;
    private javax.swing.JMenu insertMenu;
    private javax.swing.JMenuItem insertPrefix;
    private javax.swing.JMenuItem insertSuffix;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem replaceAll;
    private javax.swing.JMenu replaceMenu;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>
}