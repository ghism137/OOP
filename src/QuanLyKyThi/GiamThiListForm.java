package QuanLyKyThi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Form hiển thị danh sách giám thị
 */
public class GiamThiListForm extends JInternalFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private XMLDatabase database;
    
    public GiamThiListForm(List<GiamThi> list) {
        super("Danh Sách Giám Thị", true, true, true, true);
        this.database = new XMLDatabase();
        
        initComponents();
        loadData();
        setSize(800, 600);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Panel tiêu đề
        JPanel titlePanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("DANH SÁCH GIÁM THỊ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Tạo bảng
        String[] columnNames = {"Mã GT", "Họ Tên", "Đơn Vị", "Số ĐT", "Email", "Username"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshBtn = new JButton("Làm mới");
        refreshBtn.addActionListener(e -> loadData());
        
        JButton addBtn = new JButton("Thêm mới");
        addBtn.addActionListener(e -> openAddForm());
        
        JButton editBtn = new JButton("Sửa");
        editBtn.addActionListener(e -> editSelected());
        
        JButton deleteBtn = new JButton("Xóa");
        deleteBtn.addActionListener(e -> deleteSelected());
        
        JButton closeBtn = new JButton("Đóng");
        closeBtn.addActionListener(e -> setVisible(false));
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(closeBtn);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        try {
            List<GiamThi> allGiamThi = database.loadGiamThi();
            for (GiamThi gt : allGiamThi) {
                Object[] row = {
                    gt.getMaGiamThi(),
                    gt.getHoTen(),
                    gt.getDonVi(),
                    gt.getSDT(),
                    gt.getEmail(),
                    gt.getUsername()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải dữ liệu: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void openAddForm() {
        try {
            List<GiamThi> currentList = database.loadGiamThi();
            AddGiamThiForm addForm = new AddGiamThiForm(currentList);
            getDesktopPane().add(addForm); // Add to desktop pane
            addForm.setVisible(true);
            
            // Refresh data after adding, once the addForm is closed
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi mở form thêm: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn giám thị cần sửa!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String maGiamThi = (String) tableModel.getValueAt(selectedRow, 0);
        JOptionPane.showMessageDialog(this, 
            "Chức năng sửa giám thị " + maGiamThi + " đang được phát triển!", 
            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void deleteSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn giám thị cần xóa!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String maGiamThi = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa giám thị " + maGiamThi + "?", 
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                List<GiamThi> allGiamThi = database.loadGiamThi();
                allGiamThi.removeIf(gt -> gt.getMaGiamThi().equals(maGiamThi));
                database.saveGiamThi(allGiamThi);
                
                loadData();
                JOptionPane.showMessageDialog(this, 
                    "Đã xóa giám thị thành công!", 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Lỗi khi xóa giám thị: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
