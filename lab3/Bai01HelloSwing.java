package vn.edu.eaut.lab3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Bai01HelloSwing extends JFrame {
    private final JTextField txtName = new JTextField(20);
    private final JLabel lblStatus = new JLabel("Nhập tên rồi nhấn nút để hiển thị lời chào.");

    public Bai01HelloSwing() {
        setTitle("Bài 1 - Chào người dùng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        inputPanel.add(new JLabel("Nhập tên:"));
        inputPanel.add(txtName);

        JButton btnHello = new JButton("Hiển thị lời chào");
        inputPanel.add(btnHello);
        btnHello.addActionListener(e -> hienThiLoiChao());

        add(inputPanel, BorderLayout.CENTER);
        add(lblStatus, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void hienThiLoiChao() {
        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên!");
            txtName.requestFocus();
            return;
        }

        String message = "Xin chào, " + name + "!";
        lblStatus.setText(message);
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai01HelloSwing().setVisible(true));
    }
}

