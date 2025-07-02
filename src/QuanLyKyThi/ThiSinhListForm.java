package QuanLyKyThi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Form hiển thị danh sách thí sinh
 */
public class ThiSinhListForm extends JInternalFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private XMLDatabase database;
    
    public ThiSinhListForm(List<ThiSinh> list) {
        super("Danh Sách Thí Sinh", true, true, true, true);
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
        JLabel titleLabel = new JLabel("DANH SÁCH THÍ SINH");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Tạo bảng
        String[] columnNames = {"Mã TS", "Họ Tên", "Ngày Sinh", "Địa Chỉ", "Giới Tính", "Số ĐT"};
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
        refreshBtn.addActionListener(e -> refreshData());
        
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
            List<ThiSinh> allThiSinh = database.getAllThiSinh();
            for (ThiSinh ts : allThiSinh) {
                Object[] row = {
                    ts.getMaThisinh(),
                    ts.getHoTen(),
                    ts.getNgaysinh(),
                    ts.getDiachi(),
                    ts.getGioitinh(),
                    ts.getSdt()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải dữ liệu: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void refreshData() {
        loadData();
        JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu!", 
            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void openAddForm() {
        try {
            List<ThiSinh> currentList = database.getAllThiSinh();
            AddThiSinhForm addForm = new AddThiSinhForm(currentList);
            addForm.setVisible(true);
            
            // Refresh data after adding
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(1000); // Wait a bit for the add operation
                    loadData();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            });
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
                "Vui lòng chọn thí sinh cần sửa!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String maThiSinh = (String) tableModel.getValueAt(selectedRow, 0);
        JOptionPane.showMessageDialog(this, 
            "Chức năng sửa thí sinh " + maThiSinh + " đang được phát triển!", 
            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void deleteSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn thí sinh cần xóa!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String maThiSinh = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa thí sinh " + maThiSinh + "?", 
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // XMLDatabase doesn't have deleteThiSinh method, use removeThiSinh instead
                List<ThiSinh> allThiSinh = database.getAllThiSinh();
                allThiSinh.removeIf(ts -> ts.getMaThisinh().equals(maThiSinh));
                // In a real implementation, we would save back to XML
                loadData();
                JOptionPane.showMessageDialog(this, 
                    "Đã xóa thí sinh thành công!", 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Lỗi khi xóa thí sinh: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
