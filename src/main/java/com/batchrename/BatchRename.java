package com.batchrename;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
        tableModel = new DefaultTableModel(new Object[]{"Original", "Updated"}, 0);
        fileTable = new JTable(tableModel);
        filePane.setViewportView(fileTable);
        fileTable.getTableHeader().setReorderingAllowed(false);
        fileTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
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
    }
    
    // update updated name preview
    private void updatePreview(String textToInsert, int position, JLabel modifiedLabel) {
        String currentName = fileEntries.getFirst().getCurrentName();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BatchRename");
        setPreferredSize(new java.awt.Dimension(850, 500));
        setSize(new java.awt.Dimension(850, 500));

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

        deleteFrom.setText("From __ On");
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

        menuBar.add(deleteMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filePane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnApply, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUndo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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

        // <editor-fold defaultstate="collapsed" desc="Dialog UI">
        JDialog dialog = new JDialog(this, "Add Prefix", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        // create components
        JLabel modifiedLabel = new JLabel("New: " + fileEntries.getFirst().getCurrentName());
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
                modifiedLabel.setText("New: " + inputField.getText() + fileEntries.getFirst().getCurrentName());
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

        btnCancel.addActionListener(e -> dialog.dispose());

        // display dialog
        dialog.setVisible(true);
    }//GEN-LAST:event_insertPrefixActionPerformed

    private void insertSuffixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertSuffixActionPerformed
        if (fileEntries.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Files Added.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // <editor-fold defaultstate="collapsed" desc="Dialog UI">
        JDialog dialog = new JDialog(this, "Add Suffix", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        // create components
        JLabel modifiedLabel = new JLabel("New: " + fileEntries.getFirst().getCurrentName());
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
                String currentName = fileEntries.getFirst().getCurrentName();
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

        // <editor-fold defaultstate="collapsed" desc="Dialog UI">
        JDialog dialog = new JDialog(this, "Insert Text At Position", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(300, 225);
        dialog.setLocationRelativeTo(this);

        // Create components
        JLabel modifiedLabel = new JLabel("New: " + fileEntries.getFirst().getCurrentName());
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
                updatePreview(inputField.getText(), (int)positionSpinner.getValue(), modifiedLabel);
            }
        });

        positionSpinner.addChangeListener(e -> {
            updatePreview(inputField.getText(), (int)positionSpinner.getValue(), modifiedLabel);
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
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteFromActionPerformed

    private void deleteUntilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteUntilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteUntilActionPerformed

    private void btnApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApplyActionPerformed
        // TODO add your handling code here:
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
        saveCurrentState();
        fileEntries.clear();
        operationHistory.clear();
        updateTable();
    }//GEN-LAST:event_btnClearActionPerformed

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
    // End of variables declaration//GEN-END:variables
    // </editor-fold>
}
