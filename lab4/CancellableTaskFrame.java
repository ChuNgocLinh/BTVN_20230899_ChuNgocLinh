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

public class CancellableTaskFrame extends JFrame {
    private final JTextField txtN = new JTextField("500000");
    private final JButton btnStart = new JButton("Tinh");
    private final JButton btnCancel = new JButton("Huy");
    private final JLabel lblStatus = new JLabel("Nhap N va bam Tinh", SwingConstants.CENTER);
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private SwingWorker<Long, Void> currentWorker;

    public CancellableTaskFrame() {
        setTitle("Bai 6 - Bo sung chuc nang huy tac vu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        progressBar.setStringPainted(true);
        btnCancel.setEnabled(false);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        inputPanel.add(new JLabel("Nhap N:"));
        inputPanel.add(txtN);
        inputPanel.add(btnStart);
        inputPanel.add(btnCancel);

        add(inputPanel, BorderLayout.NORTH);
        add(progressBar, BorderLayout.CENTER);
        add(lblStatus, BorderLayout.SOUTH);

        btnStart.addActionListener(e -> startTask());
        btnCancel.addActionListener(e -> cancelTask());

        setSize(560, 220);
        setLocationRelativeTo(null);
    }

    private void startTask() {
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

        btnStart.setEnabled(false);
        btnCancel.setEnabled(true);
        progressBar.setValue(0);
        lblStatus.setText("Dang tinh, co the huy tac vu...");

        currentWorker = new SwingWorker<>() {
            @Override
            protected Long doInBackground() throws Exception {
                long sum = 0;
                for (int i = 2; i < n; i++) {
                    if (isCancelled()) {
                        return sum;
                    }
                    if (isPrime(i)) {
                        sum += i;
                    }
                    if (i % 1000 == 0) {
                        setProgress((int) ((i * 100.0) / n));
                        Thread.sleep(1);
                    }
                }
                return sum;
            }

            @Override
            protected void done() {
                if (isCancelled()) {
                    lblStatus.setText("Da huy tac vu");
                } else {
                    try {
                        lblStatus.setText("Tong so nguyen to nho hon " + n + " = " + get());
                        progressBar.setValue(100);
                    } catch (Exception ex) {
                        lblStatus.setText("Co loi khi tinh toan");
                    }
                }
                btnStart.setEnabled(true);
                btnCancel.setEnabled(false);
            }
        };

        currentWorker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((int) evt.getNewValue());
            }
        });
        currentWorker.execute();
    }

    private void cancelTask() {
        if (currentWorker != null && !currentWorker.isDone()) {
            currentWorker.cancel(true);
        }
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
            CancellableTaskFrame frame = new CancellableTaskFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
