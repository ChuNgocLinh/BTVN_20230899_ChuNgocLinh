package vn.edu.eaut.lab3;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Bai04TamGiacSwing extends JFrame {
    private static final double EPS = 1e-9;

    private final JTextField txtA = new JTextField();
    private final JTextField txtB = new JTextField();
    private final JTextField txtC = new JTextField();
    private final JLabel lblResult = new JLabel("Kết quả: ");

    public Bai04TamGiacSwing() {
        setTitle("Bài 4 - Kiểm tra tam giác");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        panel.add(new JLabel("Cạnh a:"));
        panel.add(txtA);
        panel.add(new JLabel("Cạnh b:"));
        panel.add(txtB);
        panel.add(new JLabel("Cạnh c:"));
        panel.add(txtC);

        JButton btnCheck = new JButton("Kiểm tra");
        btnCheck.addActionListener(e -> kiemTraTamGiac());

        add(lblResult, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(btnCheck, BorderLayout.SOUTH);

        setSize(420, 230);
        setLocationRelativeTo(null);
    }

    private void kiemTraTamGiac() {
        try {
            double a = parseNumber(txtA.getText());
            double b = parseNumber(txtB.getText());
            double c = parseNumber(txtC.getText());
            lblResult.setText("Kết quả: " + loaiTamGiac(a, b, c));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ba cạnh phải là số hợp lệ!");
        }
    }

    private double parseNumber(String value) {
        return Double.parseDouble(value.trim().replace(',', '.'));
    }

    private String loaiTamGiac(double a, double b, double c) {
        if (a <= 0 || b <= 0 || c <= 0 || a + b <= c || a + c <= b || b + c <= a) {
            return "Không phải tam giác";
        }

        boolean deu = Math.abs(a - b) < EPS && Math.abs(b - c) < EPS;
        boolean can = Math.abs(a - b) < EPS || Math.abs(a - c) < EPS || Math.abs(b - c) < EPS;
        double[] sides = {a, b, c};
        Arrays.sort(sides);
        boolean vuong = Math.abs(sides[0] * sides[0] + sides[1] * sides[1] - sides[2] * sides[2]) < EPS;

        if (deu) {
            return "Tam giác đều";
        }
        if (vuong && can) {
            return "Tam giác vuông cân";
        }
        if (vuong) {
            return "Tam giác vuông";
        }
        if (can) {
            return "Tam giác cân";
        }
        return "Tam giác thường";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai04TamGiacSwing().setVisible(true));
    }
}

