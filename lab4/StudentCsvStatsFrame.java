package vn.edu.eaut.lab4;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class StudentCsvStatsFrame extends JFrame {
    private File selectedFile;
    private final JButton btnChoose = new JButton("Chon CSV");
    private final JButton btnRead = new JButton("Doc va thong ke");
    private final JLabel lblFile = new JLabel("File: chua chon", SwingConstants.CENTER);
    private final JLabel lblStats = new JLabel("Thong ke: ", SwingConstants.CENTER);
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[] {"Ma SV", "Ho ten", "Diem"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tblStudents = new JTable(tableModel);

    public StudentCsvStatsFrame() {
        setTitle("Bai 8 - Doc CSV diem sinh vien va thong ke");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        progressBar.setStringPainted(true);

        JPanel top = new JPanel(new GridLayout(2, 2, 8, 8));
        top.add(btnChoose);
        top.add(btnRead);
        top.add(lblFile);
        top.add(progressBar);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(tblStudents), BorderLayout.CENTER);
        add(lblStats, BorderLayout.SOUTH);

        btnChoose.addActionListener(e -> chooseFile());
        btnRead.addActionListener(e -> readCsv());

        setSize(680, 420);
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

    private void readCsv() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Vui long chon file CSV truoc!");
            return;
        }

        btnRead.setEnabled(false);
        tableModel.setRowCount(0);
        lblStats.setText("Dang doc file CSV...");
        progressBar.setValue(0);

        SwingWorker<List<StudentScore>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<StudentScore> doInBackground() throws Exception {
                List<StudentScore> result = new ArrayList<>();
                List<String> lines = Files.readAllLines(selectedFile.toPath(), StandardCharsets.UTF_8);
                for (int i = 1; i < lines.size(); i++) {
                    String line = lines.get(i).trim();
                    if (line.isEmpty()) {
                        continue;
                    }
                    String[] parts = line.split(",", 3);
                    if (parts.length == 3) {
                        result.add(new StudentScore(parts[0].trim(), parts[1].trim(),
                                Double.parseDouble(parts[2].trim())));
                    }
                    setProgress((int) ((i * 100.0) / Math.max(1, lines.size() - 1)));
                    Thread.sleep(100);
                }
                return result;
            }

            @Override
            protected void done() {
                try {
                    List<StudentScore> students = get();
                    showStudents(students);
                } catch (Exception ex) {
                    lblStats.setText("Loi khi doc CSV");
                }
                progressBar.setValue(100);
                btnRead.setEnabled(true);
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((int) evt.getNewValue());
            }
        });
        worker.execute();
    }

    private void showStudents(List<StudentScore> students) {
        tableModel.setRowCount(0);
        double total = 0;
        StudentScore best = null;
        for (StudentScore student : students) {
            tableModel.addRow(new Object[] {
                    student.id(),
                    student.name(),
                    String.format("%.2f", student.score())
            });
            total += student.score();
            if (best == null || student.score() > best.score()) {
                best = student;
            }
        }

        if (students.isEmpty()) {
            lblStats.setText("Khong co du lieu sinh vien");
            return;
        }
        double average = total / students.size();
        lblStats.setText(String.format("Diem TB lop: %.2f | Cao nhat: %s - %.2f",
                average, best.name(), best.score()));
    }

    private record StudentScore(String id, String name, double score) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentCsvStatsFrame frame = new StudentCsvStatsFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
