package vn.edu.eaut.lab4;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class FileLineCounterFrame extends JFrame {
    private File selectedFile;
    private final JButton btnChoose = new JButton("Chon file");
    private final JButton btnCount = new JButton("Dem dong");
    private final JLabel lblFile = new JLabel("File: chua chon", SwingConstants.CENTER);
    private final JLabel lblResult = new JLabel("So dong: ", SwingConstants.CENTER);
    private final JProgressBar progressBar = new JProgressBar(0, 100);

    public FileLineCounterFrame() {
        setTitle("Bai 5 - Doc file lon va dem so dong");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        progressBar.setStringPainted(true);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 8, 8));
        buttonPanel.add(btnChoose);
        buttonPanel.add(btnCount);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 8, 8));
        centerPanel.add(lblFile);
        centerPanel.add(progressBar);
        centerPanel.add(lblResult);

        add(buttonPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        btnChoose.addActionListener(e -> chooseFile());
        btnCount.addActionListener(e -> countLines());

        setSize(620, 230);
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

    private void countLines() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Vui long chon file truoc!");
            return;
        }

        btnCount.setEnabled(false);
        progressBar.setValue(0);
        lblResult.setText("Dang doc file...");

        SwingWorker<Long, Void> worker = new SwingWorker<>() {
            @Override
            protected Long doInBackground() throws Exception {
                long totalBytes = Files.size(selectedFile.toPath());
                long readBytes = 0;
                long lines = 0;

                try (BufferedReader reader = Files.newBufferedReader(selectedFile.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines++;
                        readBytes += line.getBytes(StandardCharsets.UTF_8).length + 1;
                        int progress = totalBytes == 0
                                ? 100
                                : (int) Math.min(100, (readBytes * 100) / totalBytes);
                        setProgress(progress);
                    }
                }
                return lines;
            }

            @Override
            protected void done() {
                try {
                    lblResult.setText("So dong: " + get());
                } catch (Exception ex) {
                    lblResult.setText("Loi khi doc file");
                }
                progressBar.setValue(100);
                btnCount.setEnabled(true);
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
            FileLineCounterFrame frame = new FileLineCounterFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
