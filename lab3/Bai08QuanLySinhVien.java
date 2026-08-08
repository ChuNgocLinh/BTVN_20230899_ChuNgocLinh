package vn.edu.eaut.lab3;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Bai08QuanLySinhVien extends JFrame {
    private final JTextField txtStudentId = new JTextField();
    private final JTextField txtFullName = new JTextField();
    private final JTextField txtAverageScore = new JTextField();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[] {"Mã SV", "Họ tên", "Điểm TB", "Xếp loại"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tblStudents = new JTable(tableModel);
    private final List<Student> students = new ArrayList<>();

    public Bai08QuanLySinhVien() {
        setTitle("Bài 8 - Quản lý sinh viên");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        inputPanel.add(new JLabel("Mã sinh viên:"));
        inputPanel.add(txtStudentId);
        inputPanel.add(new JLabel("Họ tên:"));
        inputPanel.add(txtFullName);
        inputPanel.add(new JLabel("Điểm trung bình:"));
        inputPanel.add(txtAverageScore);

        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear = new JButton("Làm mới");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        JPanel topPanel = new JPanel(new BorderLayout(8, 8));
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        tblStudents.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblStudents.getSelectionModel().addListSelectionListener(e -> napDuLieuDongDangChon());

        btnAdd.addActionListener(e -> themSinhVien());
        btnUpdate.addActionListener(e -> suaSinhVien());
        btnDelete.addActionListener(e -> xoaSinhVien());
        btnClear.addActionListener(e -> lamMoi());

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(tblStudents), BorderLayout.CENTER);

        setSize(680, 420);
        setLocationRelativeTo(null);
    }

    private void themSinhVien() {
        try {
            Student student = taoSinhVienTuForm();
            if (findById(student.getStudentId()) != -1) {
                JOptionPane.showMessageDialog(this, "Mã sinh viên đã tồn tại!");
                return;
            }
            students.add(student);
            refreshTable();
            lamMoi();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void suaSinhVien() {
        int row = tblStudents.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần sửa!");
            return;
        }

        try {
            Student updated = taoSinhVienTuForm();
            int duplicatedIndex = findById(updated.getStudentId());
            if (duplicatedIndex != -1 && duplicatedIndex != row) {
                JOptionPane.showMessageDialog(this, "Mã sinh viên đã tồn tại!");
                return;
            }
            students.set(row, updated);
            refreshTable();
            tblStudents.setRowSelectionInterval(row, row);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void xoaSinhVien() {
        int row = tblStudents.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần xóa!");
            return;
        }
        students.remove(row);
        refreshTable();
        lamMoi();
    }

    private Student taoSinhVienTuForm() {
        String studentId = txtStudentId.getText().trim();
        String fullName = txtFullName.getText().trim();
        if (studentId.isEmpty()) {
            throw new IllegalArgumentException("Mã sinh viên không được để trống!");
        }
        if (fullName.isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được để trống!");
        }

        double averageScore;
        try {
            averageScore = Double.parseDouble(txtAverageScore.getText().trim().replace(',', '.'));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Điểm trung bình phải là số hợp lệ!");
        }

        if (averageScore < 0 || averageScore > 10) {
            throw new IllegalArgumentException("Điểm trung bình phải nằm trong khoảng 0 đến 10!");
        }

        return new Student(studentId, fullName, averageScore);
    }

    private int findById(String studentId) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equalsIgnoreCase(studentId)) {
                return i;
            }
        }
        return -1;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Student student : students) {
            tableModel.addRow(new Object[] {
                    student.getStudentId(),
                    student.getFullName(),
                    String.format("%.2f", student.getAverageScore()),
                    student.getRank()
            });
        }
    }

    private void napDuLieuDongDangChon() {
        int row = tblStudents.getSelectedRow();
        if (row < 0 || row >= students.size()) {
            return;
        }

        Student student = students.get(row);
        txtStudentId.setText(student.getStudentId());
        txtFullName.setText(student.getFullName());
        txtAverageScore.setText(String.valueOf(student.getAverageScore()));
    }

    private void lamMoi() {
        txtStudentId.setText("");
        txtFullName.setText("");
        txtAverageScore.setText("");
        tblStudents.clearSelection();
        txtStudentId.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bai08QuanLySinhVien().setVisible(true));
    }
}

