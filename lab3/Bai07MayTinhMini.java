package vn.edu.eaut.lab3;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Bai07MayTinhMini extends JFrame {
    private final JTextField txtA = new JTextField();
    private final JTextField txtB = new JTextField();
    private final JTextField txtResult = new JTextField();
    private final JTextArea txtHistory = new JTextArea(7, 36);

    public Bai07MayTinhMini() {
        setTitle("Bài 7 - Máy tính mini");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        inputPanel.add(new JLabel("Số thứ nhất:"));
        inputPanel.add(txtA);
        inputPanel.add(new JLabel("Số thứ hai:"));
        inputPanel.add(txtB);
        inputPanel.add(new JLabel("Kết quả:"));
        txtResult.setEditable(false);
        inputPanel.add(txtResult);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 6, 6));
        JButton btnAdd = new JButton("Cộng");
        JButton btnSub = new JButton("Trừ");
        JButton btnMul = new JButton("Nhân");
        JButton btnDiv = new JButton("Chia");
        JButton btnClear = new JButton("Clear");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnSub);
        buttonPanel.add(btnMul);
        buttonPanel.add(btnDiv);
        buttonPanel.add(btnClear);

        txtHistory.setEditable(false);
        btnAdd.addActionListener(e -> tinhToan("+"));
        btnSub.addActionListener(e -> tinhToan("-"));
        btnMul.addActionListener(e -> tinhToan("*"));
        btnDiv.addActionListener(e -> tinhToan("/"));
        btnClear.addActionListener(e -> lamMoi());

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(new JScrollPane(txtHistory), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void tinhToan(String operator) {
        try {
            double a = parseNumber(txtA.getText());
            double b = parseNumber(txtB.getText());
            double result;

            switch (operator) {
                case "+":
                    result = a + b;
                    break;
                case "-":
                    result = a - b;
                    break;
                case "*":
                    result = a * b;
                    break;
                case "/":
                    if (Math.abs(b) < 1e-9) {
                        JOptionPane.showMessageDialog(this, "Không thể chia cho 0!");
                        return;
                    }
                    result = a / b;
                    break;
                default:
                    throw new IllegalArgumentException("Phép toán không hợp lệ");
            }

            String line = String.format("%.2f %s %.2f = %.2f", a, operator, b, result);
            txtResult.setText(String.format("%.2f", result));
            txtHistory.append(line + System.lineSeparator());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Dữ liệu nhập phải là số hợp lệ!");
        }
    }

    private double parseNumber(String value) {
        return Double.parseDouble(value.trim().replace(',', '.'));
    }

    private void lamMoi() {
        txtA.setText("");
        txtB.setText("");
        txtResult.setText("");
        txtHistory.setText("");
        txtA.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai07MayTinhMini().setVisible(true));
    }
}

