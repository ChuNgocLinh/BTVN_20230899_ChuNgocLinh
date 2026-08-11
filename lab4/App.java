package vn.edu.eaut.lab4;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class App extends JFrame {
    public App() {
        setTitle("Lab 4 - Java SwingWorker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("LAB 4 - JAVA SWINGWORKER", SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));
        addButton(panel, "Bai 1 - Dem nguoc", CountdownFrame::new);
        addButton(panel, "Bai 2 - Tai du lieu", ProgressDemoFrame::new);
        addButton(panel, "Bai 3 - Tong so nguyen to", PrimeSumFrame::new);
        addButton(panel, "Bai 4 - Fibonacci", FibonacciFrame::new);
        addButton(panel, "Bai 5 - Dem dong file", FileLineCounterFrame::new);
        addButton(panel, "Bai 6 - Huy tac vu", CancellableTaskFrame::new);
        addButton(panel, "Bai 7 - Tim tu khoa", KeywordSearchFrame::new);
        addButton(panel, "Bai 8 - Thong ke diem CSV", StudentCsvStatsFrame::new);
        addButton(panel, "Bai 9 - Tai san pham", ProductLoadFrame::new);
        addButton(panel, "Bai 10 - Quan ly san pham", ProductManagerCsvFrame::new);
        add(panel, BorderLayout.CENTER);

        setSize(680, 360);
        setLocationRelativeTo(null);
    }

    private void addButton(JPanel panel, String text, FrameFactory factory) {
        JButton button = new JButton(text);
        button.addActionListener(e -> factory.create().setVisible(true));
        panel.add(button);
    }

    @FunctionalInterface
    private interface FrameFactory {
        JFrame create();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}
