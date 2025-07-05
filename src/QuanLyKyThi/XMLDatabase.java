package QuanLyKyThi;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class XMLDatabase {

    // Generic wrapper for lists to handle JAXB marshalling/unmarshalling
    @XmlRootElement(name = "list")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class ListWrapper<T> {
        @XmlElement(name = "item")
        private List<T> items;

        public ListWrapper() {
            items = new ArrayList<>();
        }

        public ListWrapper(List<T> items) {
            this.items = items;
        }

        public List<T> getItems() {
            return items;
        }
    }

    // Generic method to write a list of objects to an XML file
    private <T> void writeData(String filePath, List<T> data, Class<T> type) throws Exceptions.XMLDatabaseException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            JAXBContext context = JAXBContext.newInstance(ListWrapper.class, type);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            ListWrapper<T> wrapper = new ListWrapper<>(data);
            marshaller.marshal(wrapper, fos);
        } catch (Exception e) {
            throw new Exceptions.XMLDatabaseException("Error writing to file: " + filePath, e);
        }
    }

    // Generic method to read a list of objects from an XML file
    @SuppressWarnings("unchecked")
    private <T> List<T> readData(String filePath, Class<T> type) throws Exceptions.XMLDatabaseException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>(); // Return empty list if file doesn't exist
        }
        try (FileReader reader = new FileReader(file)) {
            JAXBContext context = JAXBContext.newInstance(ListWrapper.class, type);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ListWrapper<T> wrapper = (ListWrapper<T>) unmarshaller.unmarshal(reader);
            return wrapper.getItems();
        } catch (Exception e) {
            throw new Exceptions.XMLDatabaseException("Error reading from file: " + filePath, e);
        }
    }

    // Public methods for specific data types

    public void saveUsers(List<User> users) throws Exceptions.XMLDatabaseException {
        writeData("data/users.xml", users, User.class);
    }

    public List<User> loadUsers() throws Exceptions.XMLDatabaseException {
        return readData("data/users.xml", User.class);
    }

    public void saveThiSinh(List<ThiSinh> thiSinhs) throws Exceptions.XMLDatabaseException {
        writeData("data/thisinh.xml", thiSinhs, ThiSinh.class);
    }

    public List<ThiSinh> loadThiSinh() throws Exceptions.XMLDatabaseException {
        return readData("data/thisinh.xml", ThiSinh.class);
    }

    public void saveGiamThi(List<GiamThi> giamThis) throws Exceptions.XMLDatabaseException {
        writeData("data/giamthi.xml", giamThis, GiamThi.class);
    }

    public List<GiamThi> loadGiamThi() throws Exceptions.XMLDatabaseException {
        return readData("data/giamthi.xml", GiamThi.class);
    }

    public void saveKyThi(List<KyThi> kyThis) throws Exceptions.XMLDatabaseException {
        writeData("data/kythi.xml", kyThis, KyThi.class);
    }

    public List<KyThi> loadKyThi() throws Exceptions.XMLDatabaseException {
        return readData("data/kythi.xml", KyThi.class);
    }

    public void saveKetQua(List<KetQua> ketQuas) throws Exceptions.XMLDatabaseException {
        writeData("data/ketqua.xml", ketQuas, KetQua.class);
    }

    public List<KetQua> loadKetQua() throws Exceptions.XMLDatabaseException {
        return readData("data/ketqua.xml", KetQua.class);
    }

    // Method to link data after loading
    public void linkData(List<KyThi> kyThis, List<ThiSinh> thiSinhs, List<GiamThi> giamThis) {
        Map<String, ThiSinh> thiSinhMap = thiSinhs.stream().collect(Collectors.toMap(ThiSinh::getSoBaoDanh, Function.identity()));
        Map<String, GiamThi> giamThiMap = giamThis.stream().collect(Collectors.toMap(GiamThi::getMaGiamThi, Function.identity()));
        Map<String, KyThi> kyThiMap = kyThis.stream().collect(Collectors.toMap(KyThi::getMaKyThi, Function.identity()));

        for (KyThi kyThi : kyThis) {
            // Link ThiSinh in KyThi
            if (kyThi.getDanhSachThiSinh() != null) {
                List<ThiSinh> linkedThiSinhs = new ArrayList<>();
                for (ThiSinh ts : kyThi.getDanhSachThiSinh()) {
                    if (ts != null && thiSinhMap.containsKey(ts.getSoBaoDanh())) {
                        linkedThiSinhs.add(thiSinhMap.get(ts.getSoBaoDanh()));
                    }
                }
                kyThi.setDanhSachThiSinh(linkedThiSinhs);
            }

            // Link GiamThi in KyThi
            if (kyThi.getDanhSachGiamThi() != null) {
                List<GiamThi> linkedGiamThis = new ArrayList<>();
                for (GiamThi gt : kyThi.getDanhSachGiamThi()) {
                    if (gt != null && giamThiMap.containsKey(gt.getMaGiamThi())) {
                        linkedGiamThis.add(giamThiMap.get(gt.getMaGiamThi()));
                    }
                }
                kyThi.setDanhSachGiamThi(linkedGiamThis);
            }

            // Link KetQua in KyThi
            if (kyThi.getDanhSachKetQua() != null) {
                for (KetQua kq : kyThi.getDanhSachKetQua()) {
                    if (kq != null) {
                        kq.setKyThi(kyThi);
                        if (thiSinhMap.containsKey(kq.getMaThiSinh())) {
                            kq.setThiSinh(thiSinhMap.get(kq.getMaThiSinh()));
                        }
                    }
                }
            }
        }
    }

    public User findUserByUsername(String username) throws Exceptions.XMLDatabaseException {
        List<User> users = loadUsers();
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void deleteUser(String username) throws Exceptions.XMLDatabaseException {
        List<User> users = loadUsers();
        boolean removed = users.removeIf(user -> user.getUsername().equals(username));
        if (removed) {
            saveUsers(users);
        } else {
            throw new Exceptions.NotFoundException("User with username " + username + " not found.");
        }
    }

    public void updateUser(User updatedUser) throws Exceptions.XMLDatabaseException {
        List<User> users = loadUsers();
        boolean updated = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updatedUser.getUsername())) {
                users.set(i, updatedUser);
                updated = true;
                break;
            }
        }
        if (updated) {
            saveUsers(users);
        } else {
            throw new Exceptions.NotFoundException("User with username " + updatedUser.getUsername() + " not found for update.");
        }
    }
}