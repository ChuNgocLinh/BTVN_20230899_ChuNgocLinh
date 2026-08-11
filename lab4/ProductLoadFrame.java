package vn.edu.eaut.lab4;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class ProductLoadFrame extends JFrame {
    private final JButton btnLoad = new JButton("Tai san pham");
    private final JLabel lblStatus = new JLabel("Chua tai danh sach san pham", SwingConstants.CENTER);
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[] {"Ma SP", "Ten SP", "Don gia"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tblProducts = new JTable(tableModel);

    public ProductLoadFrame() {
        setTitle("Bai 9 - Mo phong tai danh sach san pham");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        progressBar.setStringPainted(true);

        JPanel top = new JPanel(new GridLayout(2, 1, 8, 8));
        top.add(btnLoad);
        top.add(progressBar);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(tblProducts), BorderLayout.CENTER);
        add(lblStatus, BorderLayout.SOUTH);

        btnLoad.addActionListener(e -> loadProducts());

        setSize(640, 390);
        setLocationRelativeTo(null);
    }

    private void loadProducts() {
        btnLoad.setEnabled(false);
        tableModel.setRowCount(0);
        progressBar.setValue(0);
        lblStatus.setText("Dang tai san pham...");

        SwingWorker<List<Product>, Product> worker = new SwingWorker<>() {
            @Override
            protected List<Product> doInBackground() throws Exception {
                List<Product> products = createProducts();
                for (int i = 0; i < products.size(); i++) {
                    Thread.sleep(400);
                    publish(products.get(i));
                    setProgress((int) (((i + 1) * 100.0) / products.size()));
                }
                return products;
            }

            @Override
            protected void process(List<Product> chunks) {
                for (Product product : chunks) {
                    tableModel.addRow(new Object[] {
                            product.id(),
                            product.name(),
                            String.format("%.0f", product.price())
                    });
                }
            }

            @Override
            protected void done() {
                try {
                    lblStatus.setText("Da tai " + get().size() + " san pham");
                } catch (Exception ex) {
                    lblStatus.setText("Loi khi tai san pham");
                }
                progressBar.setValue(100);
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

    private List<Product> createProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("SP01", "Ban phim", 250000));
        products.add(new Product("SP02", "Chuot", 150000));
        products.add(new Product("SP03", "Man hinh", 2500000));
        products.add(new Product("SP04", "Tai nghe", 350000));
        products.add(new Product("SP05", "Webcam", 650000));
        return products;
    }

    private record Product(String id, String name, double price) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductLoadFrame frame = new ProductLoadFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
