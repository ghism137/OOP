package QuanLyKyThi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Form hiển thị danh sách kỳ thi
 */
public class KyThiListForm extends JInternalFrame {
    
    private List<KyThi> danhSachKyThi;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnRefresh, btnEdit, btnDelete;
    
    public KyThiListForm(List<KyThi> danhSachKyThi) {
        this.danhSachKyThi = danhSachKyThi;
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        setSize(800, 600);
        setLayout(new BorderLayout());
        
        // Tạo table
        String[] columnNames = {"Mã Kỳ Thi", "Tên Kỳ Thi", "Ngày Tổ Chức", "Tình Trạng", "Số Thí Sinh", "Số Giám Thị"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép edit trực tiếp
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        
        // Thiết lập độ rộng cột
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnRefresh = new JButton("Làm Mới");
        btnEdit = new JButton("Chỉnh Sửa");
        btnDelete = new JButton("Xóa");
        
        btnRefresh.addActionListener(e -> loadData());
        btnEdit.addActionListener(e -> editKyThi());
        btnDelete.addActionListener(e -> deleteKyThi());
        
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Panel thông tin
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.add(new JLabel("Tổng số kỳ thi: " + danhSachKyThi.size()));
        add(infoPanel, BorderLayout.NORTH);
    }
    
    private void loadData() {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        
        for (KyThi kyThi : danhSachKyThi) {
            Object[] row = {
                kyThi.getMaKyThi(),
                kyThi.getTenKyThi(),
                kyThi.getNgayToChuc(),
                kyThi.getTinhTrang(),
                kyThi.getDanhSachThiSinh().size(),
                kyThi.getDanhSachGiamThi().size()
            };
            tableModel.addRow(row);
        }
        
        // Cập nhật thông tin
        Component[] components = ((JPanel)getContentPane().getComponent(2)).getComponents();
        if (components.length > 0 && components[0] instanceof JLabel) {
            ((JLabel)components[0]).setText("Tổng số kỳ thi: " + danhSachKyThi.size());
        }
    }
    
    private void editKyThi() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn kỳ thi cần chỉnh sửa!", 
                                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        KyThi kyThi = danhSachKyThi.get(selectedRow);
        
        // Tạo dialog chỉnh sửa đơn giản
        JDialog editDialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Chỉnh Sửa Kỳ Thi", true);
        editDialog.setSize(400, 300);
        editDialog.setLocationRelativeTo(this);
        editDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Các field
        JTextField txtMa = new JTextField(kyThi.getMaKyThi(), 15);
        JTextField txtTen = new JTextField(kyThi.getTenKyThi(), 15);
        JTextField txtTinhTrang = new JTextField(kyThi.getTinhTrang(), 15);
        
        txtMa.setEditable(false); // Không cho sửa mã
        
        // Thêm components
        gbc.gridx = 0; gbc.gridy = 0;
        editDialog.add(new JLabel("Mã Kỳ Thi:"), gbc);
        gbc.gridx = 1;
        editDialog.add(txtMa, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        editDialog.add(new JLabel("Tên Kỳ Thi:"), gbc);
        gbc.gridx = 1;
        editDialog.add(txtTen, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        editDialog.add(new JLabel("Tình Trạng:"), gbc);
        gbc.gridx = 1;
        editDialog.add(txtTinhTrang, gbc);
        
        // Buttons
        JPanel btnPanel = new JPanel();
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        
        btnSave.addActionListener(e -> {
            // Cập nhật thông tin (cần thêm setter methods vào class KyThi)
            JOptionPane.showMessageDialog(editDialog, "Chức năng chỉnh sửa sẽ được hoàn thiện sau!", 
                                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            editDialog.dispose();
        });
        
        btnCancel.addActionListener(e -> editDialog.dispose());
        
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        editDialog.add(btnPanel, gbc);
        
        editDialog.setVisible(true);
    }
    
    private void deleteKyThi() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn kỳ thi cần xóa!", 
                                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        KyThi kyThi = danhSachKyThi.get(selectedRow);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa kỳ thi: " + kyThi.getTenKyThi() + "?",
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            danhSachKyThi.remove(selectedRow);
            XMLDatabase database = new XMLDatabase();
            database.saveKyThi(danhSachKyThi);
            loadData();
            JOptionPane.showMessageDialog(this, "Đã xóa kỳ thi thành công!", 
                                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
