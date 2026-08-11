package vn.edu.eaut.lab4;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class PrimeSumFrame extends JFrame {
    private final JTextField txtN = new JTextField("100000");
    private final JButton btnCalculate = new JButton("Tinh");
    private final JLabel lblResult = new JLabel("Ket qua: ", SwingConstants.CENTER);
    private final JProgressBar progressBar = new JProgressBar(0, 100);

    public PrimeSumFrame() {
        setTitle("Bai 3 - Tinh tong so nguyen to");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        progressBar.setStringPainted(true);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        inputPanel.add(new JLabel("Nhap N:"));
        inputPanel.add(txtN);
        inputPanel.add(btnCalculate);
        inputPanel.add(progressBar);

        add(inputPanel, BorderLayout.NORTH);
        add(lblResult, BorderLayout.CENTER);

        btnCalculate.addActionListener(e -> calculatePrimeSum());

        setSize(540, 190);
        setLocationRelativeTo(null);
    }

    private void calculatePrimeSum() {
        int n;
        try {
            n = Integer.parseInt(txtN.getText().trim());
            if (n <= 2) {
                JOptionPane.showMessageDialog(this, "N phai lon hon 2!");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui long nhap so nguyen hop le!");
            return;
        }

        btnCalculate.setEnabled(false);
        progressBar.setValue(0);
        lblResult.setText("Dang tinh...");

        SwingWorker<Long, Void> worker = new SwingWorker<>() {
            @Override
            protected Long doInBackground() {
                long sum = 0;
                for (int i = 2; i < n; i++) {
                    if (isPrime(i)) {
                        sum += i;
                    }
                    setProgress((int) ((i * 100.0) / n));
                }
                return sum;
            }

            @Override
            protected void done() {
                try {
                    long result = get();
                    lblResult.setText("Tong cac so nguyen to nho hon " + n + " = " + result);
                } catch (Exception ex) {
                    lblResult.setText("Co loi khi tinh toan");
                }
                progressBar.setValue(100);
                btnCalculate.setEnabled(true);
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((int) evt.getNewValue());
            }
        });
        worker.execute();
    }

    private boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PrimeSumFrame frame = new PrimeSumFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
