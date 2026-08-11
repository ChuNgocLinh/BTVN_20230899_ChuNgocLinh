package vn.edu.eaut.lab4;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class KeywordSearchFrame extends JFrame {
    private File selectedFile;
    private final JTextField txtKeyword = new JTextField("java");
    private final JButton btnChoose = new JButton("Chon file .txt");
    private final JButton btnSearch = new JButton("Tim kiem");
    private final JLabel lblFile = new JLabel("File: chua chon", SwingConstants.CENTER);
    private final JLabel lblStatus = new JLabel("So dong tim thay: 0", SwingConstants.CENTER);
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JTextArea txtResult = new JTextArea(12, 50);

    public KeywordSearchFrame() {
        setTitle("Bai 7 - Tim kiem tu khoa trong file lon");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        progressBar.setStringPainted(true);
        txtResult.setEditable(false);

        JPanel top = new JPanel(new GridLayout(3, 2, 8, 8));
        top.add(new JLabel("Tu khoa:"));
        top.add(txtKeyword);
        top.add(btnChoose);
        top.add(btnSearch);
        top.add(lblFile);
        top.add(progressBar);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(txtResult), BorderLayout.CENTER);
        add(lblStatus, BorderLayout.SOUTH);

        btnChoose.addActionListener(e -> chooseFile());
        btnSearch.addActionListener(e -> searchKeyword());

        pack();
        setLocationRelativeTo(null);
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            lblFile.setText("File: " + selectedFile.getAbsolutePath());
        }
    }

    private void searchKeyword() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Vui long chon file .txt truoc!");
            return;
        }
        String keyword = txtKeyword.getText().trim().toLowerCase(Locale.ROOT);
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long nhap tu khoa!");
            return;
        }

        btnSearch.setEnabled(false);
        txtResult.setText("");
        lblStatus.setText("Dang tim kiem...");
        progressBar.setValue(0);

        SwingWorker<Integer, String> worker = new SwingWorker<>() {
            @Override
            protected Integer doInBackground() throws Exception {
                long totalBytes = Files.size(selectedFile.toPath());
                long readBytes = 0;
                int lineNumber = 0;
                int found = 0;

                try (BufferedReader reader = Files.newBufferedReader(selectedFile.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lineNumber++;
                        readBytes += line.getBytes(StandardCharsets.UTF_8).length + 1;
                        if (line.toLowerCase(Locale.ROOT).contains(keyword)) {
                            found++;
                            publish("Dong " + lineNumber + ": " + line);
                        }
                        int progress = totalBytes == 0
                                ? 100
                                : (int) Math.min(100, (readBytes * 100) / totalBytes);
                        setProgress(progress);
                    }
                }
                return found;
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                for (String line : chunks) {
                    txtResult.append(line + System.lineSeparator());
                }
            }

            @Override
            protected void done() {
                try {
                    lblStatus.setText("So dong tim thay: " + get());
                } catch (Exception ex) {
                    lblStatus.setText("Co loi khi tim kiem");
                }
                progressBar.setValue(100);
                btnSearch.setEnabled(true);
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((int) evt.getNewValue());
            }
        });
        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KeywordSearchFrame frame = new KeywordSearchFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
