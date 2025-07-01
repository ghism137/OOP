package QuanLyKyThi;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class quản lý cơ sở dữ liệu XML
 */
public class XMLDatabase {
    private static final String DATA_DIR = "data/";
    private static final String USERS_FILE = DATA_DIR + "users.xml";
    private static final String KYTHI_FILE = DATA_DIR + "kythi.xml";
    private static final String THISINH_FILE = DATA_DIR + "thisinh.xml";
    private static final String GIAMTHI_FILE = DATA_DIR + "giamthi.xml";
    private static final String KETQUA_FILE = DATA_DIR + "ketqua.xml";
    
    public XMLDatabase() {
        initializeDataDirectory();
    }
    
    private void initializeDataDirectory() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        
        // Tạo file mẫu nếu chưa tồn tại
        createSampleData();
    }
    
    private void createSampleData() {
        // Tạo users.xml mẫu với đầy đủ các role
        if (!new File(USERS_FILE).exists()) {
            List<User> sampleUsers = new ArrayList<>();
            
            // Admin - Quyền cao nhất
            sampleUsers.add(new User("admin", "admin123", "Quản trị viên hệ thống", "admin@email.com", "admin"));
            
            // Giáo vụ - Quản lý kỳ thi, nhập điểm
            sampleUsers.add(new User("giaovu", "gv123", "Phòng Giáo vụ", "giaovu@email.com", "giaovu"));
            sampleUsers.add(new User("giaovu2", "gv456", "Nhân viên Giáo vụ", "giaovu2@email.com", "giaovu"));
            
            // User thường - Quyền hạn chế
            sampleUsers.add(new User("user1", "user123", "Người dùng thường", "user1@email.com", "user"));
            sampleUsers.add(new User("user2", "user456", "Nhân viên văn phòng", "user2@email.com", "user"));
            
            // Thí sinh - Chỉ xem kết quả
            sampleUsers.add(new User("thisinh1", "ts123", "Nguyễn Văn An", "thisinh1@email.com", "thisinh"));
            sampleUsers.add(new User("thisinh2", "ts456", "Trần Thị Bình", "thisinh2@email.com", "thisinh"));
            
            saveUsers(sampleUsers);
        }
    }
    
    // ============ USER MANAGEMENT ============
    
    public void saveUsers(List<User> users) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            
            Element root = doc.createElement("users");
            doc.appendChild(root);
            
            for (User user : users) {
                Element userElement = doc.createElement("user");
                
                userElement.appendChild(createElement(doc, "username", user.getUsername()));
                userElement.appendChild(createElement(doc, "password", user.getPassword()));
                userElement.appendChild(createElement(doc, "hoTen", user.getHoTen()));
                userElement.appendChild(createElement(doc, "email", user.getEmail()));
                userElement.appendChild(createElement(doc, "role", user.getRole()));
                userElement.appendChild(createElement(doc, "isActive", String.valueOf(user.isActive())));
                
                if (user.getLastLogin() != null) {
                    userElement.appendChild(createElement(doc, "lastLogin", 
                        user.getLastLogin().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
                }
                
                root.appendChild(userElement);
            }
            
            saveXMLDocument(doc, USERS_FILE);
            
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu users: " + e.getMessage());
        }
    }
    
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        
        try {
            File file = new File(USERS_FILE);
            if (!file.exists()) return users;
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            
            NodeList userNodes = doc.getElementsByTagName("user");
            
            for (int i = 0; i < userNodes.getLength(); i++) {
                Node userNode = userNodes.item(i);
                if (userNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element userElement = (Element) userNode;
                    
                    User user = new User();
                    user.setUsername(getElementValue(userElement, "username"));
                    user.setPassword(getElementValue(userElement, "password"));
                    user.setHoTen(getElementValue(userElement, "hoTen"));
                    user.setEmail(getElementValue(userElement, "email"));
                    user.setRole(getElementValue(userElement, "role"));
                    user.setActive(Boolean.parseBoolean(getElementValue(userElement, "isActive")));
                    
                    String lastLoginStr = getElementValue(userElement, "lastLogin");
                    if (lastLoginStr != null && !lastLoginStr.isEmpty()) {
                        user.setLastLogin(LocalDateTime.parse(lastLoginStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    }
                    
                    users.add(user);
                }
            }
            
        } catch (Exception e) {
            System.err.println("Lỗi khi tải users: " + e.getMessage());
        }
        
        return users;
    }
    
    // ============ KYTHI MANAGEMENT ============
    
    public void saveKyThi(List<KyThi> kyThiList) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            
            Element root = doc.createElement("kyThiList");
            doc.appendChild(root);
            
            for (KyThi kyThi : kyThiList) {
                Element kyThiElement = doc.createElement("kyThi");
                
                kyThiElement.appendChild(createElement(doc, "maKyThi", kyThi.getMaKyThi()));
                kyThiElement.appendChild(createElement(doc, "tenKyThi", kyThi.getTenKyThi()));
                kyThiElement.appendChild(createElement(doc, "ngayToChuc", kyThi.getNgayToChuc().toString()));
                kyThiElement.appendChild(createElement(doc, "tinhTrang", kyThi.getTinhTrang()));
                kyThiElement.appendChild(createElement(doc, "phiDangKy", String.valueOf(kyThi.getPhiDangKy())));
                
                root.appendChild(kyThiElement);
            }
            
            saveXMLDocument(doc, KYTHI_FILE);
            
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu kỳ thi: " + e.getMessage());
        }
    }
    
    public List<KyThi> loadKyThi() {
        List<KyThi> kyThiList = new ArrayList<>();
        
        try {
            File file = new File(KYTHI_FILE);
            if (!file.exists()) return kyThiList;
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            
            NodeList kyThiNodes = doc.getElementsByTagName("kyThi");
            
            for (int i = 0; i < kyThiNodes.getLength(); i++) {
                Node kyThiNode = kyThiNodes.item(i);
                if (kyThiNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element kyThiElement = (Element) kyThiNode;
                    
                    String maKyThi = getElementValue(kyThiElement, "maKyThi");
                    String tenKyThi = getElementValue(kyThiElement, "tenKyThi");
                    LocalDate ngayToChuc = LocalDate.parse(getElementValue(kyThiElement, "ngayToChuc"));
                    String tinhTrang = getElementValue(kyThiElement, "tinhTrang");
                    double phiDangKy = Double.parseDouble(getElementValue(kyThiElement, "phiDangKy"));
                    
                    KyThi kyThi = new KyThi(maKyThi, tenKyThi, ngayToChuc, tinhTrang, 
                                           new ArrayList<>(), new ArrayList<>(), phiDangKy);
                    kyThiList.add(kyThi);
                }
            }
            
        } catch (Exception e) {
            System.err.println("Lỗi khi tải kỳ thi: " + e.getMessage());
        }
        
        return kyThiList;
    }
    
    // ============ THISINH MANAGEMENT ============
    
    public void saveThiSinh(List<ThiSinh> thiSinhList) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            
            Element root = doc.createElement("thiSinhList");
            doc.appendChild(root);
            
            for (ThiSinh thiSinh : thiSinhList) {
                Element thiSinhElement = doc.createElement("thiSinh");
                
                thiSinhElement.appendChild(createElement(doc, "maThiSinh", thiSinh.getMaThisinh()));
                thiSinhElement.appendChild(createElement(doc, "hoTen", thiSinh.getHoTen()));
                thiSinhElement.appendChild(createElement(doc, "ngaySinh", thiSinh.getNgaysinh().toString()));
                thiSinhElement.appendChild(createElement(doc, "gioiTinh", thiSinh.getGioitinh()));
                thiSinhElement.appendChild(createElement(doc, "diaChi", thiSinh.getDiachi()));
                thiSinhElement.appendChild(createElement(doc, "sdt", thiSinh.getSdt()));
                
                root.appendChild(thiSinhElement);
            }
            
            saveXMLDocument(doc, THISINH_FILE);
            
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu thí sinh: " + e.getMessage());
        }
    }
    
    public List<ThiSinh> loadThiSinh() {
        List<ThiSinh> thiSinhList = new ArrayList<>();
        
        try {
            File file = new File(THISINH_FILE);
            if (!file.exists()) return thiSinhList;
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            
            NodeList thiSinhNodes = doc.getElementsByTagName("thiSinh");
            
            for (int i = 0; i < thiSinhNodes.getLength(); i++) {
                Node thiSinhNode = thiSinhNodes.item(i);
                if (thiSinhNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element thiSinhElement = (Element) thiSinhNode;
                    
                    String maThiSinh = getElementValue(thiSinhElement, "maThiSinh");
                    String hoTen = getElementValue(thiSinhElement, "hoTen");
                    LocalDate ngaySinh = LocalDate.parse(getElementValue(thiSinhElement, "ngaySinh"));
                    String gioiTinh = getElementValue(thiSinhElement, "gioiTinh");
                    String diaChi = getElementValue(thiSinhElement, "diaChi");
                    String sdt = getElementValue(thiSinhElement, "sdt");
                    
                    ThiSinh thiSinh = new ThiSinh(maThiSinh, hoTen, ngaySinh, gioiTinh, diaChi, sdt);
                    thiSinhList.add(thiSinh);
                }
            }
            
        } catch (Exception e) {
            System.err.println("Lỗi khi tải thí sinh: " + e.getMessage());
        }
        
        return thiSinhList;
    }
    
    // ============ GIAMTHI MANAGEMENT ============
    
    public void saveGiamThi(List<GiamThi> giamThiList) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            
            Element root = doc.createElement("giamThiList");
            doc.appendChild(root);
            
            for (GiamThi giamThi : giamThiList) {
                Element giamThiElement = doc.createElement("giamThi");
                
                giamThiElement.appendChild(createElement(doc, "maGiamThi", giamThi.getMaGiamThi()));
                giamThiElement.appendChild(createElement(doc, "hoTen", giamThi.getHoTen()));
                giamThiElement.appendChild(createElement(doc, "donVi", giamThi.getDonVi()));
                giamThiElement.appendChild(createElement(doc, "sdt", giamThi.getSDT()));
                
                root.appendChild(giamThiElement);
            }
            
            saveXMLDocument(doc, GIAMTHI_FILE);
            
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu giám thị: " + e.getMessage());
        }
    }
    
    public List<GiamThi> loadGiamThi() {
        List<GiamThi> giamThiList = new ArrayList<>();
        
        try {
            File file = new File(GIAMTHI_FILE);
            if (!file.exists()) return giamThiList;
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            
            NodeList giamThiNodes = doc.getElementsByTagName("giamThi");
            
            for (int i = 0; i < giamThiNodes.getLength(); i++) {
                Node giamThiNode = giamThiNodes.item(i);
                if (giamThiNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element giamThiElement = (Element) giamThiNode;
                    
                    String maGiamThi = getElementValue(giamThiElement, "maGiamThi");
                    String hoTen = getElementValue(giamThiElement, "hoTen");
                    String donVi = getElementValue(giamThiElement, "donVi");
                    String sdt = getElementValue(giamThiElement, "sdt");
                    
                    GiamThi giamThi = new GiamThi(maGiamThi, hoTen, donVi, sdt);
                    giamThiList.add(giamThi);
                }
            }
            
        } catch (Exception e) {
            System.err.println("Lỗi khi tải giám thị: " + e.getMessage());
        }
        
        return giamThiList;
    }
    
    // ============ KETQUA MANAGEMENT ============
    
    public void saveKetQua(List<KetQua> ketQuaList) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            
            Element root = doc.createElement("ketQuaList");
            doc.appendChild(root);
            
            for (KetQua ketQua : ketQuaList) {
                Element ketQuaElement = doc.createElement("ketQua");
                
                // Lưu thông tin thí sinh
                ketQuaElement.appendChild(createElement(doc, "maThiSinh", ketQua.getThiSinh().getMaThisinh()));
                ketQuaElement.appendChild(createElement(doc, "tenThiSinh", ketQua.getThiSinh().getHoTen()));
                
                // Lưu thông tin kỳ thi
                ketQuaElement.appendChild(createElement(doc, "maKyThi", ketQua.getKyThi().getMaKyThi()));
                ketQuaElement.appendChild(createElement(doc, "tenKyThi", ketQua.getKyThi().getTenKyThi()));
                
                // Lưu điểm số và xếp loại
                ketQuaElement.appendChild(createElement(doc, "diem", String.valueOf(ketQua.getDiem())));
                ketQuaElement.appendChild(createElement(doc, "xepLoai", ketQua.getXepLoai()));
                
                // Lưu trạng thái bài thi
                ketQuaElement.appendChild(createElement(doc, "trangThai", ketQua.getTrangThai().name()));
                ketQuaElement.appendChild(createElement(doc, "nguoiCham", ketQua.getNguoiCham()));
                ketQuaElement.appendChild(createElement(doc, "ghiChu", ketQua.getGhiChu()));
                
                // Lưu thời gian
                if (ketQua.getThoiGianBatDauThi() != null) {
                    ketQuaElement.appendChild(createElement(doc, "thoiGianBatDauThi", ketQua.getThoiGianBatDauThi().toString()));
                }
                if (ketQua.getThoiGianNopBai() != null) {
                    ketQuaElement.appendChild(createElement(doc, "thoiGianNopBai", ketQua.getThoiGianNopBai().toString()));
                }
                if (ketQua.getThoiGianCham() != null) {
                    ketQuaElement.appendChild(createElement(doc, "thoiGianCham", ketQua.getThoiGianCham().toString()));
                }
                
                root.appendChild(ketQuaElement);
            }
            
            saveXMLDocument(doc, KETQUA_FILE);
            
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu kết quả: " + e.getMessage());
        }
    }
    
    public List<KetQua> loadKetQua() {
        List<KetQua> ketQuaList = new ArrayList<>();
        
        try {
            File file = new File(KETQUA_FILE);
            if (!file.exists()) return ketQuaList;
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            
            NodeList ketQuaNodes = doc.getElementsByTagName("ketQua");
            
            // Load danh sách thí sinh và kỳ thi để tạo đối tượng KetQua
            List<ThiSinh> thiSinhList = loadThiSinh();
            List<KyThi> kyThiList = loadKyThi();
            
            for (int i = 0; i < ketQuaNodes.getLength(); i++) {
                Node ketQuaNode = ketQuaNodes.item(i);
                if (ketQuaNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element ketQuaElement = (Element) ketQuaNode;
                    
                    String maThiSinh = getElementValue(ketQuaElement, "maThiSinh");
                    String maKyThi = getElementValue(ketQuaElement, "maKyThi");
                    double diem = Double.parseDouble(getElementValue(ketQuaElement, "diem"));
                    
                    // Tìm thí sinh và kỳ thi tương ứng
                    ThiSinh thiSinh = timThiSinhTheoMa(thiSinhList, maThiSinh);
                    KyThi kyThi = timKyThiTheoMa(kyThiList, maKyThi);
                    
                    if (thiSinh != null && kyThi != null) {
                        KetQua ketQua = new KetQua(thiSinh, kyThi, diem);
                        
                        // Load trạng thái bài thi
                        String trangThaiStr = getElementValue(ketQuaElement, "trangThai");
                        if (!trangThaiStr.isEmpty()) {
                            try {
                                KetQua.TrangThaiBaiThi trangThai = KetQua.TrangThaiBaiThi.valueOf(trangThaiStr);
                                ketQua.setTrangThai(trangThai);
                            } catch (IllegalArgumentException e) {
                                // Nếu không parse được thì để mặc định
                                System.err.println("Trạng thái không hợp lệ: " + trangThaiStr);
                            }
                        }
                        
                        // Load thông tin người chấm và ghi chú
                        ketQua.setNguoiCham(getElementValue(ketQuaElement, "nguoiCham"));
                        ketQua.setGhiChu(getElementValue(ketQuaElement, "ghiChu"));
                        
                        // Load thời gian
                        String thoiGianBatDau = getElementValue(ketQuaElement, "thoiGianBatDauThi");
                        if (!thoiGianBatDau.isEmpty()) {
                            try {
                                ketQua.setThoiGianBatDauThi(LocalDateTime.parse(thoiGianBatDau));
                            } catch (Exception e) {
                                System.err.println("Lỗi parse thời gian bắt đầu: " + e.getMessage());
                            }  
                        }
                        
                        String thoiGianNop = getElementValue(ketQuaElement, "thoiGianNopBai");
                        if (!thoiGianNop.isEmpty()) {
                            try {
                                ketQua.setThoiGianNopBai(LocalDateTime.parse(thoiGianNop));
                            } catch (Exception e) {
                                System.err.println("Lỗi parse thời gian nộp bài: " + e.getMessage());
                            }
                        }
                        
                        String thoiGianChamStr = getElementValue(ketQuaElement, "thoiGianCham");
                        if (!thoiGianChamStr.isEmpty()) {
                            try {
                                ketQua.setThoiGianCham(LocalDateTime.parse(thoiGianChamStr));
                            } catch (Exception e) {
                                System.err.println("Lỗi parse thời gian chấm: " + e.getMessage());
                            }
                        }
                        
                        ketQuaList.add(ketQua);
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println("Lỗi khi tải kết quả: " + e.getMessage());
        }
        
        return ketQuaList;
    }
    
    // Helper methods
    private String tinhXepLoai(double diem) {
        if (diem >= 9.0) return "Xuất sắc";
        else if (diem >= 8.0) return "Giỏi";
        else if (diem >= 6.5) return "Khá";
        else if (diem >= 5.0) return "Trung bình";
        else return "Yếu";
    }
    
    private ThiSinh timThiSinhTheoMa(List<ThiSinh> thiSinhList, String maThiSinh) {
        for (ThiSinh ts : thiSinhList) {
            if (ts.getMaThisinh().equals(maThiSinh)) {
                return ts;
            }
        }
        return null;
    }
    
    private KyThi timKyThiTheoMa(List<KyThi> kyThiList, String maKyThi) {
        for (KyThi kt : kyThiList) {
            if (kt.getMaKyThi().equals(maKyThi)) {
                return kt;
            }
        }
        return null;
    }

    // ============ PUBLIC ACCESSOR METHODS ============
    
    /**
     * Lấy tất cả kỳ thi
     */
    public List<KyThi> getAllKyThi() {
        return loadKyThi();
    }
    
    /**
     * Lấy tất cả thí sinh
     */
    public List<ThiSinh> getAllThiSinh() {
        return loadThiSinh();
    }
    
    /**
     * Lấy tất cả giám thị
     */
    public List<GiamThi> getAllGiamThi() {
        return loadGiamThi();
    }
    
    /**
     * Lấy tất cả kết quả
     */
    public List<KetQua> getAllKetQua() {
        return loadKetQua();
    }
    
    /**
     * Lấy tất cả users
     */
    public List<User> getAllUsers() {
        return loadUsers();
    }

    // ============ UTILITY METHODS ============
    
    private Element createElement(Document doc, String tagName, String value) {
        Element element = doc.createElement(tagName);
        element.setTextContent(value != null ? value : "");
        return element;
    }
    
    private String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return "";
    }
    
    private void saveXMLDocument(Document doc, String filePath) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }
}
