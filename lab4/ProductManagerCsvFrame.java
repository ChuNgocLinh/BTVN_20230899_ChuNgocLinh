package vn.edu.eaut.lab4;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class ProductManagerCsvFrame extends JFrame {
    private final JTextField txtId = new JTextField();
    private final JTextField txtName = new JTextField();
    private final JTextField txtPrice = new JTextField();
    private final JLabel lblStatus = new JLabel("Quan ly san pham bang file CSV", SwingConstants.CENTER);
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[] {"Ma SP", "Ten SP", "Don gia"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tblProducts = new JTable(tableModel);
    private final List<Product> products = new ArrayList<>();

    public ProductManagerCsvFrame() {
        setTitle("Bai 10 - Quan ly san pham bang CSV");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        progressBar.setStringPainted(true);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        inputPanel.add(new JLabel("Ma san pham:"));
        inputPanel.add(txtId);
        inputPanel.add(new JLabel("Ten san pham:"));
        inputPanel.add(txtName);
        inputPanel.add(new JLabel("Don gia:"));
        inputPanel.add(txtPrice);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 8, 8));
        JButton btnAdd = new JButton("Them");
        JButton btnUpdate = new JButton("Sua");
        JButton btnDelete = new JButton("Xoa");
        JButton btnClear = new JButton("Lam moi");
        JButton btnRead = new JButton("Doc CSV");
        JButton btnSave = new JButton("Luu CSV");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRead);
        buttonPanel.add(btnSave);

        JPanel top = new JPanel(new BorderLayout(8, 8));
        top.add(inputPanel, BorderLayout.CENTER);
        top.add(buttonPanel, BorderLayout.SOUTH);

        tblProducts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblProducts.getSelectionModel().addListSelectionListener(e -> fillSelectedProduct());

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(tblProducts), BorderLayout.CENTER);
        JPanel bottom = new JPanel(new GridLayout(2, 1, 8, 8));
        bottom.add(progressBar);
        bottom.add(lblStatus);
        add(bottom, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnClear.addActionListener(e -> clearForm());
        btnRead.addActionListener(e -> readCsv());
        btnSave.addActionListener(e -> saveCsv());

        addSampleProducts();
        refreshTable();

        setSize(760, 520);
        setLocationRelativeTo(null);
    }

    private void addProduct() {
        try {
            Product product = createProductFromForm();
            if (findById(product.id()) != -1) {
                JOptionPane.showMessageDialog(this, "Ma san pham da ton tai!");
                return;
            }
            products.add(product);
            refreshTable();
            clearForm();
            lblStatus.setText("Da them san pham");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updateProduct() {
        int row = tblProducts.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui long chon san pham can sua!");
            return;
        }
        try {
            Product product = createProductFromForm();
            int duplicated = findById(product.id());
            if (duplicated != -1 && duplicated != row) {
                JOptionPane.showMessageDialog(this, "Ma san pham da ton tai!");
                return;
            }
            products.set(row, product);
            refreshTable();
            tblProducts.setRowSelectionInterval(row, row);
            lblStatus.setText("Da cap nhat san pham");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void deleteProduct() {
        int row = tblProducts.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui long chon san pham can xoa!");
            return;
        }
        products.remove(row);
        refreshTable();
        clearForm();
        lblStatus.setText("Da xoa san pham");
    }

    private void readCsv() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = chooser.getSelectedFile();
        progressBar.setValue(0);
        lblStatus.setText("Dang doc file CSV...");

        SwingWorker<List<Product>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Product> doInBackground() throws Exception {
                List<Product> loaded = new ArrayList<>();
                List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
                for (int i = 1; i < lines.size(); i++) {
                    String line = lines.get(i).trim();
                    if (line.isEmpty()) {
                        continue;
                    }
                    String[] parts = line.split(",", 3);
                    if (parts.length == 3) {
                        loaded.add(new Product(parts[0].trim(), parts[1].trim(),
                                Double.parseDouble(parts[2].trim())));
                    }
                    setProgress((int) ((i * 100.0) / Math.max(1, lines.size() - 1)));
                    Thread.sleep(80);
                }
                return loaded;
            }

            @Override
            protected void done() {
                try {
                    products.clear();
                    products.addAll(get());
                    refreshTable();
                    lblStatus.setText("Da doc " + products.size() + " san pham");
                } catch (Exception ex) {
                    lblStatus.setText("Loi khi doc file CSV");
                }
                progressBar.setValue(100);
            }
        };
        bindProgress(worker);
        worker.execute();
    }

    private void saveCsv() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = chooser.getSelectedFile();
        progressBar.setValue(0);
        lblStatus.setText("Dang luu file CSV...");

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                List<String> lines = new ArrayList<>();
                lines.add("MaSP,TenSP,DonGia");
                for (int i = 0; i < products.size(); i++) {
                    Product product = products.get(i);
                    lines.add(product.id() + "," + product.name() + "," + product.price());
                    setProgress((int) (((i + 1) * 100.0) / Math.max(1, products.size())));
                    Thread.sleep(80);
                }
                Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
                return null;
            }

            @Override
            protected void done() {
                lblStatus.setText("Da luu file CSV: " + file.getAbsolutePath());
                progressBar.setValue(100);
            }
        };
        bindProgress(worker);
        worker.execute();
    }

    private void bindProgress(SwingWorker<?, ?> worker) {
        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((int) evt.getNewValue());
            }
        });
    }

    private Product createProductFromForm() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        if (id.isEmpty()) {
            throw new IllegalArgumentException("Ma san pham khong duoc de trong!");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Ten san pham khong duoc de trong!");
        }
        double price;
        try {
            price = Double.parseDouble(txtPrice.getText().trim().replace(',', '.'));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Don gia phai la so hop le!");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Don gia phai >= 0!");
        }
        return new Product(id, name, price);
    }

    private int findById(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).id().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Product product : products) {
            tableModel.addRow(new Object[] {
                    product.id(),
                    product.name(),
                    String.format("%.0f", product.price())
            });
        }
    }

    private void fillSelectedProduct() {
        int row = tblProducts.getSelectedRow();
        if (row < 0 || row >= products.size()) {
            return;
        }
        Product product = products.get(row);
        txtId.setText(product.id());
        txtName.setText(product.name());
        txtPrice.setText(String.valueOf(product.price()));
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtPrice.setText("");
        tblProducts.clearSelection();
        txtId.requestFocus();
    }

    private void addSampleProducts() {
        products.add(new Product("SP01", "Ban phim", 250000));
        products.add(new Product("SP02", "Chuot", 150000));
        products.add(new Product("SP03", "Man hinh", 2500000));
    }

    private record Product(String id, String name, double price) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductManagerCsvFrame frame = new ProductManagerCsvFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
