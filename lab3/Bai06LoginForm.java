package vn.edu.eaut.lab3;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Bai06LoginForm extends JFrame {
    private final JTextField txtUsername = new JTextField();
    private final JPasswordField txtPassword = new JPasswordField();
    private final JComboBox<String> cboRole = new JComboBox<>(new String[] {"Admin", "User"});
    private final JCheckBox chkShowPassword = new JCheckBox("Hiển thị mật khẩu");
    private final JLabel lblStatus = new JLabel("Nhập tài khoản để đăng nhập.");
    private char defaultEchoChar;

    public Bai06LoginForm() {
        setTitle("Bài 6 - Form đăng nhập");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        inputPanel.add(new JLabel("Tài khoản:"));
        inputPanel.add(txtUsername);
        inputPanel.add(new JLabel("Mật khẩu:"));
        inputPanel.add(txtPassword);
        inputPanel.add(new JLabel("Vai trò:"));
        inputPanel.add(cboRole);
        inputPanel.add(new JLabel(""));
        inputPanel.add(chkShowPassword);

        JButton btnLogin = new JButton("Đăng nhập");
        JButton btnClear = new JButton("Làm mới");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnClear);

        defaultEchoChar = txtPassword.getEchoChar();
        chkShowPassword.addActionListener(e -> hienThiMatKhau());
        btnLogin.addActionListener(e -> dangNhap());
        btnClear.addActionListener(e -> lamMoi());

        add(lblStatus, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(430, 240);
        setLocationRelativeTo(null);
    }

    private void hienThiMatKhau() {
        txtPassword.setEchoChar(chkShowPassword.isSelected() ? '\0' : defaultEchoChar);
    }

    private void dangNhap() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String role = String.valueOf(cboRole.getSelectedItem());

        boolean adminValid = "admin".equals(username) && "123456".equals(password) && "Admin".equals(role);
        boolean userValid = "user".equals(username) && "123456".equals(password) && "User".equals(role);

        if (adminValid || userValid) {
            String message = "Đăng nhập thành công. Xin chào " + username + " (" + role + ")!";
            lblStatus.setText(message);
            JOptionPane.showMessageDialog(this, message);
        } else {
            lblStatus.setText("Đăng nhập thất bại.");
            JOptionPane.showMessageDialog(this, "Sai tài khoản, mật khẩu hoặc vai trò!");
        }
    }

    private void lamMoi() {
        txtUsername.setText("");
        txtPassword.setText("");
        cboRole.setSelectedIndex(0);
        chkShowPassword.setSelected(false);
        hienThiMatKhau();
        lblStatus.setText("Nhập tài khoản để đăng nhập.");
        txtUsername.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai06LoginForm().setVisible(true));
    }
}

