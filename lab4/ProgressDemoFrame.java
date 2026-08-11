package vn.edu.eaut.lab4;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class ProgressDemoFrame extends JFrame {
    private final JButton btnLoad = new JButton("Tai du lieu");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JLabel lblStatus = new JLabel("Chua tai du lieu", SwingConstants.CENTER);

    public ProgressDemoFrame() {
        setTitle("Bai 2 - Mo phong tai du lieu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        progressBar.setStringPainted(true);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.add(btnLoad);
        panel.add(progressBar);
        panel.add(lblStatus);
        add(panel, BorderLayout.CENTER);

        btnLoad.addActionListener(e -> loadData());

        setSize(480, 190);
        setLocationRelativeTo(null);
    }

    private void loadData() {
        btnLoad.setEnabled(false);
        progressBar.setValue(0);
        lblStatus.setText("Dang tai du lieu...");

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i += 10) {
                    setProgress(i);
                    Thread.sleep(1000);
                }
                return null;
            }

            @Override
            protected void done() {
                progressBar.setValue(100);
                lblStatus.setText("Tai du lieu hoan tat");
                btnLoad.setEnabled(true);
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
            ProgressDemoFrame frame = new ProgressDemoFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
