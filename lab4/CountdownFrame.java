package vn.edu.eaut.lab4;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class CountdownFrame extends JFrame {
    private final JTextField txtSeconds = new JTextField("5");
    private final JButton btnStart = new JButton("Bat dau");
    private final JLabel lblTime = new JLabel("Thoi gian con lai: ", SwingConstants.CENTER);

    public CountdownFrame() {
        setTitle("Bai 1 - Dong ho dem nguoc");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        lblTime.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 8, 8));
        inputPanel.add(new JLabel("Nhap so giay:", SwingConstants.CENTER));
        inputPanel.add(txtSeconds);
        inputPanel.add(btnStart);

        add(inputPanel, BorderLayout.NORTH);
        add(lblTime, BorderLayout.CENTER);

        btnStart.addActionListener(e -> startCountdown());

        setSize(420, 210);
        setLocationRelativeTo(null);
    }

    private void startCountdown() {
        int seconds;
        try {
            seconds = Integer.parseInt(txtSeconds.getText().trim());
            if (seconds <= 0) {
                JOptionPane.showMessageDialog(this, "So giay phai lon hon 0!");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui long nhap so nguyen hop le!");
            return;
        }

        btnStart.setEnabled(false);
        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = seconds; i >= 0; i--) {
                    publish(i);
                    Thread.sleep(1000);
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int value = chunks.get(chunks.size() - 1);
                lblTime.setText("Thoi gian con lai: " + value + " giay");
            }

            @Override
            protected void done() {
                btnStart.setEnabled(true);
                JOptionPane.showMessageDialog(CountdownFrame.this, "Hoan thanh!");
            }
        };
        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CountdownFrame frame = new CountdownFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
