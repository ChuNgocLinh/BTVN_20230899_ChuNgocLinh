package vn.edu.eaut.lab4;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class FibonacciFrame extends JFrame {
    private final JTextField txtN = new JTextField("100");
    private final JButton btnFind = new JButton("Tim");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JTextArea txtResult = new JTextArea(5, 40);

    public FibonacciFrame() {
        setTitle("Bai 4 - Fibonacci memoization");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        progressBar.setStringPainted(true);
        txtResult.setEditable(false);
        txtResult.setLineWrap(true);
        txtResult.setWrapStyleWord(true);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        inputPanel.add(new JLabel("Nhap N:"));
        inputPanel.add(txtN);
        inputPanel.add(btnFind);
        inputPanel.add(progressBar);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(txtResult), BorderLayout.CENTER);

        btnFind.addActionListener(e -> findFibonacci());

        pack();
        setLocationRelativeTo(null);
    }

    private void findFibonacci() {
        int n;
        try {
            n = Integer.parseInt(txtN.getText().trim());
            if (n < 0) {
                JOptionPane.showMessageDialog(this, "N phai >= 0!");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui long nhap so nguyen hop le!");
            return;
        }

        btnFind.setEnabled(false);
        progressBar.setValue(0);
        txtResult.setText("Dang tinh Fibonacci...");

        SwingWorker<BigInteger, Void> worker = new SwingWorker<>() {
            @Override
            protected BigInteger doInBackground() {
                Map<Integer, BigInteger> memo = new HashMap<>();
                memo.put(0, BigInteger.ZERO);
                memo.put(1, BigInteger.ONE);
                for (int i = 2; i <= n; i++) {
                    memo.put(i, memo.get(i - 1).add(memo.get(i - 2)));
                    setProgress((int) Math.min(100, (i * 100.0) / n));
                }
                return memo.get(n);
            }

            @Override
            protected void done() {
                try {
                    BigInteger result = get();
                    txtResult.setText("Fibonacci(" + n + ") = " + result);
                } catch (Exception ex) {
                    txtResult.setText("Co loi khi tinh Fibonacci");
                }
                progressBar.setValue(100);
                btnFind.setEnabled(true);
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
            FibonacciFrame frame = new FibonacciFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
